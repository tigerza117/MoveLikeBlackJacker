package co.yunchao.client.controllers;

import co.yunchao.base.enums.ChipType;
import co.yunchao.base.models.Chip;
import co.yunchao.client.views.ChipEntity;
import co.yunchao.client.views.Seat;

import java.util.UUID;

public class ChipController extends Chip {
    private final ChipEntity view;
    private final Seat seat;

    public ChipController(UUID id, ChipType type, Seat seat) {
        super(id, type);
        this.seat = seat;
        this.view = new ChipEntity(type.name());
        seat.addChipBet(view);
    }

    public void spawn() {
        view.spawn();
    }

    public void deSpawn() {
        view.deSpawn();
        seat.removeChip(view);
    }
}
