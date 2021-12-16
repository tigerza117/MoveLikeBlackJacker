package co.yunchao.base.models;

import co.yunchao.base.enums.ChipType;
import co.yunchao.base.enums.PlayerInGameState;
import co.yunchao.net.packets.DataPacket;

import java.util.UUID;

public abstract class Player {
    private final UUID id;
    private final boolean isDealer;
    private String name;
    private double balance;
    private PlayerInGameState state = PlayerInGameState.IDLE;
    private int currentBetStage = 0;
    private Game game;
    private boolean isOnline = true;
    private Offset offset;

    public Player(UUID id, String name, boolean isDealer){
        this(id, name, isDealer, new Offset(0,0));
    }

    public Player(UUID id, String name, boolean isDealer, Offset offset){
        this.id = id;
        this.name = name;
        this.isDealer = isDealer;
        this.offset = offset;
    }

    public void setBalance(double chip){
        this.balance = chip;
    }

    public double getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

    public void setOffset(Offset offset) {
        this.offset = offset;
    }

    public Offset getOffset() {
        return offset;
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

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
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
        return (isReady() || isHit()) && getGame() != null && game.isInGame();
    }

    public boolean canStand() {
        return canHit();
    }

    public boolean canDoubleDown() {
        return isReady() && getGame() != null  && game.isInGame();
    }

    public boolean canConfirmBet() {
        return isIdle() && isBet() && (getCurrentBetStage() > 0) && getGame() != null && getGame().isInBet();
    }

    public boolean canStackCurrentBetStage(int amount) {
        var totalAfter = getCurrentBetStage() + amount;
        return isIdle() && ((getBalance() - amount) >= 0) && (totalAfter <= 2000) && getGame() != null && getGame().isInBet();
    }

    public void stackCurrentBetStage(ChipType chipType) {
        stackCurrentBetStage(chipType, 1);
    }

    public abstract void handler(DataPacket packet);

    public abstract void putPacket(DataPacket packet);

    public abstract void close();

    public abstract void skip();

    public abstract void confirmBet();

    public abstract void hit();

    public abstract void stand();

    public abstract void doubleDown();

    public abstract void clearBet();

    public abstract void stackCurrentBetStage(ChipType chipType, int amount);
}
