package co.yunchao.client.controllers;

import co.yunchao.base.enums.ChipType;
import co.yunchao.base.enums.PlayAction;
import co.yunchao.base.models.Player;
import co.yunchao.client.views.Seat;
import co.yunchao.net.packets.DataPacket;
import co.yunchao.net.packets.PlayerActionPacket;
import co.yunchao.net.packets.PlayerBetStackPacket;

import java.util.UUID;

public class PlayerController extends Player {
    private Seat seat;
    private GameController gameController;

    public PlayerController(UUID id, String name, boolean isDealer) {
        super(id, name, isDealer);
    }

    public GameController getGame() {
        return (GameController) super.getGame();
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
        setGame(gameController);
    }

    public void sit(Seat seat) {
        this.seat = seat;
        seat.sit();
    }

    public Seat getSeat() {
        return seat;
    }

    @Override
    public void setName(String name) {
        super.setName(name);
        seat.setName(name);
    }

    @Override
    public void setCurrentBetStage(int currentBetStage) {
        super.setCurrentBetStage(currentBetStage);
        getSeat().setBetStack(currentBetStage > 0 ? currentBetStage + "$" : "");
    }

    @Override
    public void handler(DataPacket packet) {

    }

    @Override
    public void putPacket(DataPacket packet) {
        gameController.putPacket(packet);
    }

    @Override
    public void close() {
        getSeat().close();
    }

    @Override
    public void skip() {
        //Call in side server
    }

    @Override
    public void confirmBet() {
        callAction(PlayAction.CONFIRM_BET);
    }

    @Override
    public void hit() {
        callAction(PlayAction.HIT);
    }

    @Override
    public void stand() {
        callAction(PlayAction.STAND);
    }

    @Override
    public void doubleDown() {
        callAction(PlayAction.DOUBLE);
    }

    @Override
    public void clearBet() {
        callAction(PlayAction.CLEAR_BET);
    }

    @Override
    public void stackCurrentBetStage(ChipType chipType, int amount) {
        PlayerBetStackPacket packet = new PlayerBetStackPacket();
        packet.type = chipType;
        packet.amount = amount;
        putPacket(packet);
    }

    private void callAction(PlayAction action) {
        PlayerActionPacket packet = new PlayerActionPacket();
        packet.action = action;
        putPacket(packet);
    }
}
