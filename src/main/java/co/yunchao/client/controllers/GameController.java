package co.yunchao.client.controllers;

import co.yunchao.base.models.Game;
import co.yunchao.client.net.Interface;
import co.yunchao.client.net.NetworkEngine;
import co.yunchao.client.views.*;
import co.yunchao.net.packets.*;
import com.almasb.fxgl.scene.Scene;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

import static com.almasb.fxgl.dsl.FXGL.*;

public class GameController extends Game {
    private final Table view;
    private final PlayerController playerController;
    private HashMap<UUID, PlayerController> players;
    public static HashMap<UUID, ChipController> chips;
    public static HashMap<UUID, CardController> cards;
    private final Queue<Seat> seats;
    private final Seat dealerSeat = new Seat(876, 40);
    public static Scene scene;
    private boolean alreadyRender = false;

    private Interface network;

    public GameController(String id, PlayerController playerController) {
        super(id);
        this.view = new Table(this);
        this.players = new HashMap<>();
        this.playerController = playerController;
        seats = new LinkedList<>();
        seats.add(new Seat( 432,294));
        seats.add(new Seat( 729,377));
        seats.add(new Seat( 1024,375));
        seats.add(new Seat( 1319,293));
    }

    public void setNetwork(Interface network) {
        this.network = network;
        NetworkEngine.setNetwork(network);
    }

    public void start() {
        runOnce(() -> {
            getSceneService().pushSubScene(new LoadingModal());
            try {
                setNetwork(new Interface());
                network.setOnHandler(this::handler);
                LoginPacket packet = new LoginPacket();
                packet.name = playerController.getName();
                packet.id = playerController.getId();
                putPacket(packet);
                JoinRoomPacket joinRoomPacket = new JoinRoomPacket();
                joinRoomPacket.roomId = getId();
                putPacket(joinRoomPacket);
            } catch (Exception e) {
                getGameController().gotoMainMenu();
                getSceneService().pushSubScene(new DisconnectedModal("Connection fail."));
            }
        }, Duration.seconds(0));
    }

    public void close() {
        if (network != null) {
            try {
                network.shutdown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void handler(DataPacket packet) {
        switch (packet.pid()) {
            case ProtocolInfo.DISCONNECT_PACKET: {
                DisconnectPacket disconnectPacket = (DisconnectPacket) packet;
                getGameController().gotoMainMenu();
                if (disconnectPacket.showDialog) getSceneService().pushSubScene(new DisconnectedModal(disconnectPacket.message));
                break;
            }
            case ProtocolInfo.PLAYER_JOIN_PACKET: {
                PlayerJoinPacket playerJoinPacket = (PlayerJoinPacket) packet;
                PlayerController player = playerController;
                Seat seat = seats.poll();
                System.out.println("Player " + playerJoinPacket.id + " has been join the game.");
                if (playerJoinPacket.id != playerController.getId()) {
                    player = new PlayerController(playerJoinPacket.id, playerJoinPacket.name, playerJoinPacket.isDealer);
                }
                if (seat != null && !player.isDealer()) {
                    player.sit(seat);
                    players.put(player.getId(), player);
                    System.out.println("Seat success");
                } else if (player.isDealer()) {
                    player.sit(dealerSeat);
                }
                break;
            }
            case ProtocolInfo.PLAYER_LEAVE_PACKET: {
                PlayerLeavePacket playerLeavePacket = (PlayerLeavePacket) packet;
                PlayerController player = players.get(playerLeavePacket.id);
                player.close();
                break;
            }
            case ProtocolInfo.PLAYER_METADATA_PACKET: {
                PlayerMetadataPacket playerMetadataPacket = (PlayerMetadataPacket) packet;
                PlayerController player = players.get(playerMetadataPacket.id);
                player.setState(playerMetadataPacket.state);
                player.setChips(playerMetadataPacket.chips);
                player.setName(playerMetadataPacket.name);
                player.setCurrentBetStage(playerMetadataPacket.currentBetStage);
                break;
            }
            case ProtocolInfo.GAME_METADATA_PACKET: {
                GameMetadataPacket gameMetadataPacket = (GameMetadataPacket) packet;
                this.setState(gameMetadataPacket.state);
                if (!alreadyRender) {
                    this.alreadyRender = true;
                    view.render();
                    getSceneService().popSubScene();
                }
            }
            case ProtocolInfo.CARD_TOGGLE_FLIP_PACKET: {
                break;
            }
            case ProtocolInfo.CARD_SPAWN_PACKET: {
                CardSpawnPacket cardSpawnPacket = (CardSpawnPacket) packet;
                PlayerController player = players.get(cardSpawnPacket.playerId);
                //var card = new CardEntity();
                break;
            }
            case ProtocolInfo.CARD_DE_SPAWN_PACKET: {
                CardDeSpawnPacket cardDeSpawnPacket = (CardDeSpawnPacket) packet;
                break;
            }
            case ProtocolInfo.CHIP_SPAWN_PACKET: {
                ChipSpawnPacket chipSpawnPacket = (ChipSpawnPacket) packet;
                break;
            }
            case ProtocolInfo.CHIP_DE_SPAWN_PACKET: {
                ChipDeSpawnPacket chipDeSpawnPacket = (ChipDeSpawnPacket) packet;
                break;
            }
            default:
                System.out.println("Unknown packet");
        }
    }

    @Override
    public void putPacket(DataPacket packet) {
        network.putPacket(packet);
    }
}
