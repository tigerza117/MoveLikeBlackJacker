package co.yunchao.client.net;

import co.yunchao.base.models.Player;
import co.yunchao.client.controllers.GameController;
import co.yunchao.client.controllers.PlayerController;
import co.yunchao.net.Network;
import co.yunchao.net.handler.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import network.ycc.raknet.client.RakNetClient;
import network.ycc.raknet.pipeline.UserDataCodec;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

public class Interface {
    private final EventLoopGroup ioGroup = new NioEventLoopGroup();
    private Channel channel;

    public Interface(GameController gameController, InetSocketAddress adder) throws Exception {
        try {
            channel = new Bootstrap()
                    .group(ioGroup)
                    .channel(RakNetClient.CHANNEL)
                    .option(RakNetClient.PROTOCOL_VERSION, 10)
                    .option(RakNetClient.RETRY_DELAY_NANOS, TimeUnit.NANOSECONDS.convert(10, TimeUnit.MILLISECONDS))
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                    .handler(new ChannelInitializer<Channel>() {
                        protected void initChannel(Channel ch) {
                            ch.pipeline().addLast(UserDataCodec.NAME, new UserDataCodec(0xFE));
                            ch.pipeline()
                                    .addLast(new PacketEncoder())
                                    .addLast(new PacketDecoder())
                                    .addLast(new NetworkHandler(gameController));
                        }
                    }).connect(adder).sync().channel();
        }  catch (Exception e) {
            ioGroup.shutdownGracefully();
            throw e;
        }
    }

    public Channel getChannel() {
        return channel;
    }

    public void shutdown() throws InterruptedException {
        System.out.println("shutdown");
        if (channel.isActive()) {
            channel.close().sync();
        }
        if (!ioGroup.isShutdown()) {
            ioGroup.shutdownGracefully();
        }
    }
}
