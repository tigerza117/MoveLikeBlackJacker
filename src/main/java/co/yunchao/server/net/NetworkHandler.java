package co.yunchao.server.net;

import co.yunchao.net.packets.*;
import co.yunchao.server.controllers.GameController;
import co.yunchao.server.models.Player;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class NetworkHandler extends SimpleChannelInboundHandler<DataPacket> {
    private HashMap<Channel ,Player> players = new HashMap<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("Connect channel > " + ctx.channel());
        players.put(ctx.channel(), new Player("", null));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DataPacket packet) throws Exception {
        var player = players.get(ctx.channel());
        if (player != null) {
            player.getPlayerController().handler(packet);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        System.out.println("Disconnect channel > " + ctx.channel());
        players.remove(ctx.channel());
    }
}
