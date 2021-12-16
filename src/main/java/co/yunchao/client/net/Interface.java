package co.yunchao.client.net;

import co.yunchao.base.models.Player;
import co.yunchao.client.controllers.GameController;
import co.yunchao.client.controllers.PlayerController;
import co.yunchao.net.Network;
import co.yunchao.net.handler.*;
import co.yunchao.net.packets.DataPacket;
import co.yunchao.net.packets.DisconnectPacket;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import network.ycc.raknet.client.RakNetClient;
import network.ycc.raknet.pipeline.UserDataCodec;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

public class Interface {
    private final EventLoopGroup ioGroup = new NioEventLoopGroup();
    private final Channel channel;
    private final Network network = new Network();
    private onHandler onHandler;

    public Interface() throws Exception {
        final InetSocketAddress adder = new InetSocketAddress("localhost", 7856);
        NetworkHandler handler = new NetworkHandler(this);
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
                                    .addLast(handler);
                        }
                    }).connect(adder).sync().channel();
        }  catch (Exception e) {
            ioGroup.shutdownGracefully();
            throw e;
        }
    }

    public void setOnHandler(onHandler onHandler) {
        this.onHandler = onHandler;
    }

    protected onHandler getOnHandler() {
        return onHandler;
    }

    public void putPacket(DataPacket packet) {
        try {
            channel.writeAndFlush(packet).sync();
        } catch (InterruptedException e) {
            System.out.println("Fail to send packet");
        }
    }

    public void shutdown() throws InterruptedException {
        if (channel.isActive()) {
            channel.writeAndFlush(new DisconnectPacket());
            channel.close().sync();
        }
        if (!ioGroup.isShutdown()) {
            ioGroup.shutdownGracefully();
        }
    }

    public interface onHandler {
        void run(DataPacket packet);
    }
}
