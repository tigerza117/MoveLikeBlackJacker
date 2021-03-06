package co.yunchao.server.net;

import co.yunchao.net.Network;
import co.yunchao.net.handler.PacketDecoder;
import co.yunchao.net.handler.PacketEncoder;
import co.yunchao.server.controllers.Server;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import network.ycc.raknet.pipeline.UserDataCodec;
import network.ycc.raknet.server.RakNetServer;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

public class Interface {
    final EventLoopGroup ioGroup = new NioEventLoopGroup();
    final Channel channel;
    final Network network = new Network();

    public Interface(InetSocketAddress adder, Server server) throws InterruptedException {
        channel = new ServerBootstrap()
                .group(ioGroup)
                .channel(RakNetServer.CHANNEL)
                .option(RakNetServer.PROTOCOL_VERSION, 10)
                .option(RakNetServer.RETRY_DELAY_NANOS, TimeUnit.NANOSECONDS.convert(10, TimeUnit.MILLISECONDS))
                .childHandler(new ChannelInitializer<Channel>() {
                    protected void initChannel(Channel ch) {
                        ch.pipeline().addLast(UserDataCodec.NAME, new UserDataCodec(0xFE));
                        ch.pipeline()
                                .addLast(new PacketEncoder())
                                .addLast(new PacketDecoder())
                                .addLast(new NetworkHandler(server));
                    }
                }).bind(adder).sync().channel();
    }

    public Channel getChannel() {
        return channel;
    }

    public void shutdown() {
        ioGroup.shutdownGracefully();
    }
}
