package co.yunchao.base.models;

import co.yunchao.base.enums.ChipType;
import co.yunchao.base.enums.PlayerInGameState;
import co.yunchao.net.packets.DataPacket;
import co.yunchao.server.controllers.Game;

import java.util.UUID;

public abstract class Player{
    private final UUID id;
    private final boolean isDealer;
    private String name;
    private double chips;
    private PlayerInGameState state = PlayerInGameState.IDLE;
    private int currentBetStage = 0;
    private Game game;

    public Player(UUID id, String name, boolean isDealer){
        this.id = id;
        this.name = name;
        this.isDealer = isDealer;
    }

    public Player(String name, boolean isDealer){
        this.id = UUID.randomUUID();
        this.name = name;
        this.isDealer = isDealer;
    }

    public void setChips(double chip){
        this.chips = chip;
    }

    public double getChips() {
        return chips;
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlayerInGameState getState() {
        return state;
    }

    public void setState(PlayerInGameState state) {
        this.state = state;
    }

    public boolean isDealer() {
        return isDealer;
    }

    public int getCurrentBetStage() {
        return currentBetStage;
    }

    public void setCurrentBetStage(int currentBetStage) {
        this.currentBetStage = currentBetStage;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public boolean isReady() {
        return state.equals(PlayerInGameState.READY);
    }

    public boolean isHit() {
        return state.equals(PlayerInGameState.HIT);
    }

    public boolean isIdle() {
        return state.equals(PlayerInGameState.IDLE);
    }

    public boolean isBet() {
        return state.equals(PlayerInGameState.IDLE);
    }

    public boolean isStan() {
        return state.equals(PlayerInGameState.STAND);
    }

    public boolean isDouble() {
        return state.equals(PlayerInGameState.DOUBLE);
    }

    public boolean isWining() {
        return state.equals(PlayerInGameState.WINING);
    }

    public boolean isSkip() {
        return state.equals(PlayerInGameState.SKIP);
    }

    public boolean isBust() {
        return state.equals(PlayerInGameState.BUST);
    }

    public boolean canHit() {
        return (isReady() || isHit()) && game.isInGame();
    }

    public boolean canStand() {
        return canHit();
    }

    public boolean canDoubleDown() {
        return isReady() && game.isInGame();
    }

    public boolean canConfirmBet() {
        return isIdle() && isBet() && getCurrentBetStage() > 0;
    }

    public boolean canStackCurrentBetStage(int amount) {
        return getChips() - (getCurrentBetStage() + amount) >= 0;
    }

    public abstract void handler(DataPacket packet);

    public abstract void putPacket(DataPacket packet);

    public abstract void close();

    public abstract void skip();

    public abstract void confirmBet();

    public abstract void hit();

    public abstract void stand();

    public abstract void doubleDown();

    public abstract void stackCurrentBetStage(ChipType chipType);
}
