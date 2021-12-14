package co.yunchao.client.controllers;

import co.yunchao.base.enums.ChipType;
import co.yunchao.base.models.Player;
import co.yunchao.client.views.Seat;
import co.yunchao.net.packets.DataPacket;

import java.util.UUID;

public class PlayerController extends Player {
    private Seat seat;

    public PlayerController(UUID id, String name, boolean isDealer) {
        super(id, name, isDealer);
    }

    private void sit() {
        seat.sit();
    }

    public Seat getSeat() {
        return seat;
    }

    @Override
    public void handler(DataPacket packet) {
    }

    @Override
    public void putPacket(DataPacket packet) {

    }

    @Override
    public void close() {

    }

    @Override
    public void skip() {

    }

    @Override
    public void confirmBet() {

    }

    @Override
    public void hit() {

    }

    @Override
    public void stand() {

    }

    @Override
    public void doubleDown() {

    }

    @Override
    public void stackCurrentBetStage(ChipType chipType) {

    }
}
