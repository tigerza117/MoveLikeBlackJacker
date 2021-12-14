package co.yunchao.client.controllers;

import co.yunchao.base.enums.ChipType;
import co.yunchao.base.models.Chip;

import java.util.UUID;

public class ChipController extends Chip {
    public ChipController(UUID id, ChipType type) {
        super(id, type);
    }
}
