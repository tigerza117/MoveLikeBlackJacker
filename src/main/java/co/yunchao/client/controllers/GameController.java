package co.yunchao.client.controllers;

import co.yunchao.client.net.Network;
import co.yunchao.client.views.Table;
import co.yunchao.net.packets.DataPacket;
import co.yunchao.net.packets.ProtocolInfo;
import co.yunchao.net.packets.PlayerJoinPacket;

import java.util.ArrayList;

public class GameController {
    private final Table view;
    private final ArrayList<PlayerController> playerControllers;
    private final Network network;

    public GameController() {
        this.network = new Network(this);
        this.view = new Table();
        this.playerControllers = new ArrayList<>();
    }

    public void Start() {
        view.render();
        view.getSeats().forEach((s, seat) -> {
            seat.sit();
        });
    }

    public void handler(DataPacket packet) {
        switch (packet.pid()) {
            case ProtocolInfo.DISCONNECT_PACKET:
                break;
            case ProtocolInfo.PLAYER_JOIN_PACKET:
                PlayerJoinPacket playerJoinPacket = (PlayerJoinPacket) packet;
                /*System.out.println("Player " + playerJoinPacket.id + " has been join the game.");*/
                break;
            default:
                System.out.println("Unknown packet");
        }
    }
}
