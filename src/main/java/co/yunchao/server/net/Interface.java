package co.yunchao.server.net;

import co.yunchao.net.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import network.ycc.raknet.pipeline.UserDataCodec;
import network.ycc.raknet.server.RakNetServer;

import java.net.InetSocketAddress;

public class Interface {
    final EventLoopGroup ioGroup = new NioEventLoopGroup();
    final Channel channel;

    public Interface(InetSocketAddress adder) throws InterruptedException {
        channel = new ServerBootstrap()
                .group(ioGroup)
                .channel(RakNetServer.CHANNEL)
                .option(RakNetServer.PROTOCOL_VERSION, 10)
                .childHandler(new ChannelInitializer<Channel>() {
                    protected void initChannel(Channel ch) {
                        ch.pipeline().addLast(UserDataCodec.NAME, new UserDataCodec(0xFE));
                        ch.pipeline()
                                .addLast(new PacketEncoder())
                                .addLast(new PacketDecoder())
                                .addLast(new NetworkHandler());
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
