package co.yunchao.client.controllers;

import co.yunchao.client.views.Seat;

public class PlayerController {
    private final Seat seat;

    public PlayerController(Seat seat) {
        this.seat = seat;
    }

    private void sit() {
        seat.sit();
    }

    public Seat getSeat() {
        return seat;
    }
}
