package co.yunchao.client.net;

import co.yunchao.base.models.Player;
import co.yunchao.net.handler.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import network.ycc.raknet.client.RakNetClient;
import network.ycc.raknet.pipeline.UserDataCodec;

import java.net.InetSocketAddress;

public class Interface {
    final EventLoopGroup ioGroup = new NioEventLoopGroup();
    final Channel channel;

    public Interface(Player player, InetSocketAddress adder) throws InterruptedException {
        channel = new Bootstrap()
                .group(ioGroup)
                .channel(RakNetClient.CHANNEL)
                .option(RakNetClient.PROTOCOL_VERSION, 10)
                .handler(new ChannelInitializer<Channel>() {
                    protected void initChannel(Channel ch) {
                        ch.pipeline().addLast(UserDataCodec.NAME, new UserDataCodec(0xFE));
                        ch.pipeline()
                                .addLast(new PacketEncoder())
                                .addLast(new PacketDecoder())
                                .addLast(new NetworkHandler(player));
                    }
                }).connect(adder).sync().channel();
    }

    public Channel getChannel() {
        return channel;
    }

    public void shutdown() {
        ioGroup.shutdownGracefully();
    }
}
