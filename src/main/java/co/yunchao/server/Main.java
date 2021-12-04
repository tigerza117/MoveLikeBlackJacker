package co.yunchao.server;

import network.ycc.raknet.RakNet;
import network.ycc.raknet.client.RakNetClient;
import network.ycc.raknet.pipeline.UserDataCodec;
import network.ycc.raknet.server.RakNetServer;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;

import javax.swing.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) {
        try {
            final EventLoopGroup ioGroup = new NioEventLoopGroup();
            final EventLoopGroup childGroup = new DefaultEventLoopGroup();
            final InetSocketAddress localhost = new InetSocketAddress("localhost", 31747);
            final String helloWorld = "Hello Ing";

            final String[] resultStr = new String[1];
            final Channel serverChannel = new ServerBootstrap()
                    .group(ioGroup, childGroup)
                    .channel(RakNetServer.CHANNEL)
                    .option(RakNetServer.PROTOCOL_VERSION, 10)
                    .childHandler(new ChannelInitializer<Channel>() {
                        protected void initChannel(Channel ch) {
                            ch.pipeline().addLast(UserDataCodec.NAME, new UserDataCodec(0xFE));
                            ch.pipeline().addLast(new SimpleChannelInboundHandler<ByteBuf>() {
                                protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) {
                                    resultStr[0] = msg.readCharSequence(msg.readableBytes(),
                                            StandardCharsets.UTF_8).toString();
                                    System.out.println(resultStr[0]); //"Hello world!"
                                }
                            });
                        }
                    }).bind(localhost).sync().channel();

            final Channel clientChannel = new Bootstrap()
                    .group(ioGroup)
                    .channel(RakNetClient.CHANNEL)
                    .option(RakNetClient.PROTOCOL_VERSION, 10)
                    .handler(new ChannelInitializer<Channel>() {
                        protected void initChannel(Channel ch) {
                            ch.pipeline().addLast(UserDataCodec.NAME, new UserDataCodec(0xFE));
                        }
                    }).connect(localhost).sync().channel();



            JFrame f = new JFrame();
            JButton btn = new JButton("Sent By TIGER");
            btn.addActionListener(e -> {
                try {
                    final ByteBuf helloWorldBuf = Unpooled.buffer();
                    helloWorldBuf.writeCharSequence(helloWorld, StandardCharsets.UTF_8);
                    clientChannel.writeAndFlush(helloWorldBuf).sync();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            f.add(btn);
            f.pack();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
