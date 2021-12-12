package co.yunchao.test;

import co.yunchao.net.Network;
import co.yunchao.net.handler.PacketDecoder;
import co.yunchao.net.handler.PacketEncoder;
import co.yunchao.net.packets.DataPacket;
import co.yunchao.net.packets.GameMetadataPacket;
import co.yunchao.net.packets.LoginPacket;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import network.ycc.raknet.client.RakNetClient;
import network.ycc.raknet.pipeline.UserDataCodec;
import network.ycc.raknet.server.RakNetServer;

import java.net.InetSocketAddress;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class TestPacket {
    public static void main(String[] args) {
        EventLoopGroup ioGroup = new NioEventLoopGroup();
        InetSocketAddress adder = new InetSocketAddress("localhost", 7777);
        Network network = new Network();

        try {
            var serverChannel = new ServerBootstrap()
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
                                    .addLast(new SimpleChannelInboundHandler<DataPacket>() {
                                        @Override
                                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                            super.channelActive(ctx);
                                            System.out.println("Connect channel > " + ctx.channel());
                                        }

                                        @Override
                                        protected void channelRead0(ChannelHandlerContext ctx, DataPacket msg) throws Exception {
                                            System.out.println("Server > got packet " + msg.getClass());
                                            var pk = new GameMetadataPacket();
                                            ctx.channel().writeAndFlush(pk);
                                        }
                                    });
                        }
                    }).bind(adder).sync().channel();

            var clientChannel = new Bootstrap()
                    .group(ioGroup)
                    .channel(RakNetClient.CHANNEL)
                    .option(RakNetClient.PROTOCOL_VERSION, 10)
                    .option(RakNetClient.RETRY_DELAY_NANOS, TimeUnit.NANOSECONDS.convert(10, TimeUnit.MILLISECONDS))
                    .handler(new ChannelInitializer<Channel>() {
                        protected void initChannel(Channel ch) {
                            ch.pipeline().addLast(UserDataCodec.NAME, new UserDataCodec(0xFE));
                            ch.pipeline()
                                    .addLast(new PacketEncoder())
                                    .addLast(new PacketDecoder())
                                    .addLast(new SimpleChannelInboundHandler<DataPacket>() {

                                        @Override
                                        protected void channelRead0(ChannelHandlerContext ctx, DataPacket msg) throws Exception {
                                            System.out.println("Client > got packet " + msg.getClass());
                                        }
                                    });
                        }
                    }).connect(adder).sync().channel();

            var pk = new LoginPacket();
            pk.setName("TIGER");
            pk.setId(UUID.randomUUID());
            clientChannel.writeAndFlush(pk).sync();

            serverChannel.close().sync();
            clientChannel.close().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
