package co.yunchao.client.net;

import co.yunchao.net.packets.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.text.SimpleDateFormat;

public class NetworkHandler extends SimpleChannelInboundHandler<Packet> {
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        if (packet instanceof JoinRoomResponse) {
            JoinRoomResponse joinRoomResponse = (JoinRoomResponse) packet;
            System.out.println("Join Room > " + joinRoomResponse.getId());
        }
    }
}
