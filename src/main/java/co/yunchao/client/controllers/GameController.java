package co.yunchao.client.controllers;

import co.yunchao.client.net.Network;
import co.yunchao.client.views.Table;
import co.yunchao.net.packets.DisconnectPacket;
import co.yunchao.net.packets.LoginPacket;
import co.yunchao.net.packets.PlayerJoinPacket;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.UUID;

import static com.almasb.fxgl.dsl.FXGL.*;

public class GameController {
    private final Table view;
    private final ArrayList<PlayerController> playerControllers;
    private final Network network;

    public GameController() {
        this.network = new Network(this);
        var pk = new LoginPacket();
        pk.setName("TIGER");
        pk.setId(UUID.randomUUID());
        network.putPacket(pk);
        getGameTimer().runOnceAfter(() -> {
            // code to run once after 1 second
            var pkD = new DisconnectPacket();
            pkD.setMessage("Room full please try again.");
            pkD.setShowDialog(true);
            network.putPacket(pkD);
        }, Duration.seconds(1));
        this.view = new Table();
        this.playerControllers = new ArrayList<>();
    }

    public void Start() {
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
