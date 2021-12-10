package co.yunchao.client.controllers;

import co.yunchao.client.views.Card;
import co.yunchao.client.views.Chip;
import co.yunchao.client.views.Table;

public class GameController {
    private final Table view;

    public GameController() {
        view = new Table();
    }

    public void Start() {
        view.render();
        view.getSeats().forEach((s, seat) -> {
            seat.sit();
            seat.addCard(new Card("05"));
            seat.addCard(new Card("55"));
            seat.addChipBet(new Chip("chip1"));
            seat.addChipBet(new Chip("chip2"));
            seat.addChipBet(new Chip("chip3"));
        });
    }
}
