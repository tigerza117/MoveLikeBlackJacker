package co.yunchao.server.net;

import co.yunchao.net.packets.*;
import co.yunchao.server.controllers.Player;
import co.yunchao.server.controllers.Server;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;

public class NetworkHandler extends SimpleChannelInboundHandler<DataPacket> {
    private final HashMap<Channel , Player> players = new HashMap<>();
    private final Server server;

    NetworkHandler(Server server) {
        this.server = server;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("Connect channel > " + ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DataPacket packet) throws Exception {
        var player = players.get(ctx.channel());
        System.out.println("Receiver channel > " + ctx.channel() + " > " + packet.getClass());
        switch (packet.pid()) {
            case ProtocolInfo.LOGIN_PACKET:
                LoginPacket loginPacket = (LoginPacket) packet;
                if (player != null) {
                    player.kick("Player exit join.");
                }
                players.put(ctx.channel(), new Player(loginPacket.name, ctx.channel(), server));
                break;
            case ProtocolInfo.DISCONNECT_PACKET:
                if (player != null) {
                    player.close();
                    ctx.close();
                }
                break;
            default:
                if (player != null) {
                    player.handler(packet);
                }
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        System.out.println("Disconnect channel > " + ctx.channel());
        players.remove(ctx.channel());
    }
}
