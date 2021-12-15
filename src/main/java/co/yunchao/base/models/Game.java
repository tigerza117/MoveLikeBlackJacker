package co.yunchao.base.models;

import co.yunchao.base.enums.GameState;
import co.yunchao.net.packets.DataPacket;

public abstract class Game {
    private GameState state;
    private String id;

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

    public void setState(GameState state) {
        this.state = state;
    }

    public boolean isInGame() {
        return getState().equals(GameState.IN_GAME);
    }

    public abstract void putPacket(DataPacket packet);
}
