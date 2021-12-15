package co.yunchao.client.controllers;

import co.yunchao.base.models.Game;
import co.yunchao.base.models.Player;
import co.yunchao.client.net.Network;
import co.yunchao.client.net.NetworkEngine;
import co.yunchao.client.views.DisconnectedModal;
import co.yunchao.client.views.LoadingModal;
import co.yunchao.client.views.Table;
import co.yunchao.net.packets.DataPacket;
import co.yunchao.net.packets.PlayerJoinPacket;
import co.yunchao.net.packets.ProtocolInfo;
import javafx.util.Duration;

import java.util.ArrayList;

import static com.almasb.fxgl.dsl.FXGL.*;

public class GameController extends Game {
    private final Table view;
    private final PlayerController playerController;
    private ArrayList<PlayerController> players;
    private Network network;

    public GameController(String id, PlayerController playerController) {
        super(id);
        this.view = new Table(this);
        this.players = new ArrayList<>();
        this.playerController = playerController;
    }

    public void setNetwork(Network network) {
        this.network = network;
        NetworkEngine.setNetwork(network);
    }

    public void start() {
        runOnce(() -> {
            getSceneService().pushSubScene(new LoadingModal());
            try {
                setNetwork(new Network(this));
                view.render();
                getSceneService().popSubScene();
            } catch (Exception e) {
                getGameController().gotoMainMenu();
                getSceneService().pushSubScene(new DisconnectedModal("Connection fail."));
            }
        }, Duration.seconds(0));
    }

    public void close() {
        if (network != null) {
            try {
                network.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void handler(DataPacket packet) {
        switch (packet.pid()) {
            case ProtocolInfo.DISCONNECT_PACKET:
            case ProtocolInfo.JOIN_ROOM_PACKET:
            case ProtocolInfo.PLAYER_LEAVE_PACKET:
            case ProtocolInfo.PLAYER_BET_STACK_PACKET:
            case ProtocolInfo.PLAYER_ACTION_PACKET:
            case ProtocolInfo.PLAYER_METADATA_PACKET:
            case ProtocolInfo.GAME_METADATA_PACKET:
            case ProtocolInfo.CARD_TOGGLE_FLIP_PACKET:
            case ProtocolInfo.CARD_SPAWN_PACKET:
            case ProtocolInfo.CARD_DE_SPAWN_PACKET:
            case ProtocolInfo.CHIP_SPAWN_PACKET:
            case ProtocolInfo.CHIP_DE_SPAWN_PACKET:
                break;
            case ProtocolInfo.PLAYER_JOIN_PACKET:
                PlayerJoinPacket playerJoinPacket = (PlayerJoinPacket) packet;
                System.out.println("Player " + playerJoinPacket.id + " has been join the game.");
                break;
            default:
                System.out.println("Unknown packet");
        }
    }

    @Override
    public void putPacket(DataPacket packet) {
        network.putPacket(packet);
    }

    @Override
    public boolean join(Player player) {
        return false;
    }

    @Override
    public void leave(Player player) {

    }
}
