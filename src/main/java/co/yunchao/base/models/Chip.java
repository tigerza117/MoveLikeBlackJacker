package co.yunchao.base.models;

import co.yunchao.base.enums.ChipType;

import java.util.UUID;

public class Chip {
    private UUID id;
    private final ChipType type;

    public Chip(UUID id, ChipType type) {
        this(type);
        this.id = id;
    }

    public Chip(ChipType type) {
        this.type = type;
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public ChipType getType() {
        return type;
    }

    public int getPoint() {
        return type.getAmount();
    }
}
