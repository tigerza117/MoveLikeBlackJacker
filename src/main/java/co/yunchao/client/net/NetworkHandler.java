package co.yunchao.client.net;

import co.yunchao.client.controllers.GameController;
import co.yunchao.client.controllers.PlayerController;
import co.yunchao.net.packets.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.text.SimpleDateFormat;

public class NetworkHandler extends SimpleChannelInboundHandler<DataPacket> {
    private final GameController gameController;

    public NetworkHandler(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DataPacket packet) throws Exception {
        gameController.handler(packet);
    }
}
