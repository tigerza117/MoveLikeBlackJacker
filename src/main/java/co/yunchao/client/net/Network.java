package co.yunchao.client.net;

import co.yunchao.base.models.Player;
import co.yunchao.net.packets.Packet;

import java.net.InetSocketAddress;

public class Network {

    private Interface inf;

    public Network(Player player) {
        try {
            final InetSocketAddress localhost = new InetSocketAddress("localhost", 31747);

            this.inf = new Interface(player, localhost);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void putPacket(Packet packet) {
        this.inf.getChannel().writeAndFlush(packet);
    }
}
