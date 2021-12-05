package co.yunchao.server.net;

import co.yunchao.net.packets.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class NetworkHandler extends SimpleChannelInboundHandler<Packet> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("Connect channel > " + ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        if (packet instanceof ConnectPacket) {
            System.out.println("Connect -> " + ctx.channel());
        } else if (packet instanceof DisconnectPacket) {
            System.out.println("Disconnect -> " + ctx.channel());
        } else {
            System.out.println("Unknown packet");
        }
    }
}
