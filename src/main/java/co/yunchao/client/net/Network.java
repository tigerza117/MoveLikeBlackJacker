package co.yunchao.client.net;

import co.yunchao.client.controllers.GameController;
import co.yunchao.net.packets.DataPacket;

import java.net.InetSocketAddress;

public class Network {

    private Interface inf;

    public Network(GameController gameController) {
        try {
            final InetSocketAddress localhost = new InetSocketAddress("localhost", 31747);

            this.inf = new Interface(gameController, localhost);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void putPacket(DataPacket packet) {
        this.inf.getChannel().writeAndFlush(packet);
    }
}
