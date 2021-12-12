package co.yunchao.client.controllers;

import co.yunchao.client.net.Network;
import co.yunchao.client.views.Table;
import co.yunchao.net.packets.ConnectPacket;
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
        network.putPacket(new ConnectPacket());
        view.render();
        view.getSeats().forEach((s, seat) -> {
            seat.sit();
        });
    }

    public void handlerPlayerJoin(PlayerJoinPacket packet) {
        System.out.println("Player " + packet.getId() + " has been join");
    }

    public void handlerPlayerLevel(PlayerJoinPacket packet) {

    }

    public void handlerPlayerAction(PlayerJoinPacket packet) {

    }
}
