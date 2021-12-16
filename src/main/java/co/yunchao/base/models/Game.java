package co.yunchao.base.models;

import co.yunchao.base.enums.GameState;
import co.yunchao.net.packets.DataPacket;

import java.util.UUID;

public abstract class Game {
    private GameState state;
    private String id;
    private UUID playerTurn = UUID.randomUUID();

    public Game(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public GameState getState() {
        return state;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isPlayerTurn(Player player) {
        return playerTurn.equals(player.getId());
    }

    public void setPlayerTurn(UUID playerTurn) {
        this.playerTurn = playerTurn;
    }

    public UUID getPlayerTurn() {
        return playerTurn;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public boolean isInBet() {
        return getState().equals(GameState.BET);
    }

    public boolean isInGame() {
        return getState().equals(GameState.IN_GAME);
    }

    public abstract void putPacket(DataPacket packet);
}
