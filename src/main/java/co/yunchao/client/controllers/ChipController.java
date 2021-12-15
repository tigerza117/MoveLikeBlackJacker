package co.yunchao.client.controllers;

import co.yunchao.base.enums.ChipType;
import co.yunchao.base.models.Chip;
import co.yunchao.client.views.ChipEntity;

import java.util.UUID;

public class ChipController extends Chip {
    private final ChipEntity view;

    public ChipController(UUID id, ChipType type) {
        super(id, type);
        this.view = new ChipEntity(type.name());
    }

    public void spawn() {
        view.spawn();
    }

    public void deSpawn() {
        view.deSpawn();
    }
}
