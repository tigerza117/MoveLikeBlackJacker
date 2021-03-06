package co.yunchao.client.controllers;

import co.yunchao.base.models.Game;
import co.yunchao.base.models.Offset;
import co.yunchao.client.net.Interface;
import co.yunchao.client.net.NetworkEngine;
import co.yunchao.client.views.DisconnectedModal;
import co.yunchao.client.views.LoadingModal;
import co.yunchao.client.views.Seat;
import co.yunchao.client.views.Table;
import co.yunchao.net.packets.*;
import com.almasb.fxgl.scene.Scene;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.UUID;

import static com.almasb.fxgl.dsl.FXGL.*;

public class GameController extends Game {
    private final Table view;
    private final PlayerController playerController;
    private final HashMap<UUID, PlayerController> players;
    private final HashMap<UUID, ChipController> chips;
    private final HashMap<UUID, CardController> cards;
    public static Scene scene;
    private boolean alreadyRender = false;

    private Interface network;

    public GameController(String id, PlayerController playerController) {
        super(id);
        this.view = new Table(this, playerController);
        this.players = new HashMap<>();
        this.chips = new HashMap<>();
        this.cards = new HashMap<>();
        this.playerController = playerController;
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

    @Override
    public void setId(String id) {
        super.setId(id);
        view.setRoomID(id);
    }

    public void handler(DataPacket packet) {
        switch (packet.pid()) {
            case ProtocolInfo.DISCONNECT_PACKET: {
                DisconnectPacket disconnectPacket = (DisconnectPacket) packet;
                getGameController().gotoMainMenu();
                if (disconnectPacket.showDialog)
                    getSceneService().pushSubScene(new DisconnectedModal(disconnectPacket.message));
                break;
            }
            case ProtocolInfo.PLAYER_JOIN_PACKET: {
                PlayerJoinPacket playerJoinPacket = (PlayerJoinPacket) packet;
                PlayerController player = playerController;
                player.setGameController(this);
                play("Sparkle_Pop_01.wav");
                if (!playerJoinPacket.id.equals(playerController.getId())) {
                    player = new PlayerController(playerJoinPacket.id, playerJoinPacket.name, playerJoinPacket.isDealer);
                }
                if (!player.isDealer()) {
                    player.setOffset(new Offset(playerJoinPacket.offsetX, playerJoinPacket.offsetY));
                    player.sit(new Seat(player));
                } else {
                    player.setOffset(new Offset(876, 40));
                    Seat seat = new Seat(player);
                    seat.setIsDealer(true);
                    player.sit(seat);
                }
                System.out.println("Player " + player.getName() + " has been join the game.");
                players.put(player.getId(), player);
                break;
            }
            case ProtocolInfo.PLAYER_LEAVE_PACKET: {
                PlayerLeavePacket playerLeavePacket = (PlayerLeavePacket) packet;
                PlayerController player = players.get(playerLeavePacket.id);
                if (player != null) {
                    player.close();
                    play("Player_Leave.wav");
                }
                break;
            }
            case ProtocolInfo.PLAYER_METADATA_PACKET: {
                PlayerMetadataPacket playerMetadataPacket = (PlayerMetadataPacket) packet;
                PlayerController player = players.get(playerMetadataPacket.id);
                if (player != null) {
                    player.setState(playerMetadataPacket.state);
                    player.setBalance(playerMetadataPacket.chips);
                    player.setName(playerMetadataPacket.name);
                    player.setCurrentBetStage(playerMetadataPacket.currentBetStage);
                    player.getSeat().updateBalance();
                    if (player.getId().equals(playerController.getId())) {
                        view.update();
                    }
                }
                break;
            }
            case ProtocolInfo.GAME_METADATA_PACKET: {
                GameMetadataPacket gameMetadataPacket = (GameMetadataPacket) packet;
                setState(gameMetadataPacket.state);
                setId(gameMetadataPacket.id);
                setPlayerTurn(gameMetadataPacket.currentPlayerTurn);
                view.getBetSection().setProgress((gameMetadataPacket.tick * 1.0) / (gameMetadataPacket.maxTick * 1.0));
                if (!alreadyRender) {
                    this.alreadyRender = true;
                    view.render();
                    getSceneService().popSubScene();
                }
                players.forEach((uuid, player) -> {
                    if (!player.isDealer()) {
                        if (gameMetadataPacket.currentPlayerTurn.equals(player.getId())) {
                            player.getSeat().myTurn();
                        } else {
                            player.getSeat().notMyTurn();
                        }
                    }
                });
                view.update();
                break;
            }
            case ProtocolInfo.CARD_TOGGLE_FLIP_PACKET: {
                CardToggleFlipPacket cardToggleFlipPacket = (CardToggleFlipPacket) packet;
                CardController card = cards.get(cardToggleFlipPacket.id);
                if (card != null) {
                    card.toggleFlip();
                }
                break;
            }
            case ProtocolInfo.CARD_SPAWN_PACKET: {
                CardSpawnPacket cardSpawnPacket = (CardSpawnPacket) packet;
                PlayerController player = players.get(cardSpawnPacket.playerId);
                var card = new CardController(cardSpawnPacket.id, cardSpawnPacket.number, cardSpawnPacket.suit, cardSpawnPacket.flip, player.getSeat());
                cards.put(card.getId(), card);
                card.spawn();
                break;
            }
            case ProtocolInfo.CARD_DE_SPAWN_PACKET: {
                CardDeSpawnPacket cardDeSpawnPacket = (CardDeSpawnPacket) packet;
                CardController card = cards.get(cardDeSpawnPacket.id);
                if (card != null) {
                    card.deSpawn();
                    cards.remove(card.getId());
                }
                break;
            }
            case ProtocolInfo.CHIP_SPAWN_PACKET: {
                ChipSpawnPacket chipSpawnPacket = (ChipSpawnPacket) packet;
                PlayerController player = players.get(chipSpawnPacket.playerId);
                if (player != null) {
                    var chip = new ChipController(chipSpawnPacket.id, chipSpawnPacket.type, player.getSeat());
                    chips.put(chip.getId(), chip);
                    chip.spawn();
                }
                break;
            }
            case ProtocolInfo.CHIP_DE_SPAWN_PACKET: {
                ChipDeSpawnPacket chipDeSpawnPacket = (ChipDeSpawnPacket) packet;
                ChipController chip = chips.get(chipDeSpawnPacket.id);
                if (chip != null) {
                    chip.deSpawn();
                    chips.remove(chip.getId());
                }
                break;
            }
            case ProtocolInfo.PLAY_SOUND_PACKET: {
                PlaySoundPacket playSoundPacket = (PlaySoundPacket) packet;
                play(playSoundPacket.name);
                break;
            }
            case ProtocolInfo.STOP_SOUND_PACKET: {
                getAudioPlayer().stopAllSounds();
                break;
            }
            case ProtocolInfo.SET_SCORE_PACKET: {
                SetScorePacket setScorePacket = (SetScorePacket) packet;
                PlayerController player = players.get(setScorePacket.playerId);
                if (player != null) {
                    player.getSeat().setScore(setScorePacket.text, setScorePacket.colorType);
                }
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
