package co.yunchao.client.controllers;

import co.yunchao.base.enums.CardSuit;
import co.yunchao.base.models.Card;
import co.yunchao.client.views.CardEntity;
import co.yunchao.client.views.Seat;

import java.util.UUID;

public class CardController extends Card {
    private final CardEntity view;
    private final Seat seat;

    public CardController(UUID id, int number, CardSuit suit, boolean flip, Seat seat) {
        super(id, number, suit);
        this.seat = seat;
        this.view = new CardEntity(getName(), flip);
        seat.addCard(view);
    }

    public void spawn() {
        view.spawn();
    }

    public void toggleFlip() {
        view.toggleFlip();
    }

    public void deSpawn() {
        view.deSpawn();
        seat.removeCard(view);
    }
}
