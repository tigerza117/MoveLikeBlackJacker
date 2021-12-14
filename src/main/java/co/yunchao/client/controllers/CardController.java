package co.yunchao.client.controllers;

import co.yunchao.base.enums.CardSuit;
import co.yunchao.base.models.Card;
import co.yunchao.client.views.CardEntity;

public class CardController extends Card {
    private final CardEntity view;

    public CardController(int number, CardSuit suit) {
        super(number, suit);
        this.view = new CardEntity(getName());
    }

    public void spawn() {
        view.spawn();
    }

    public void toggleFlip() {
        view.toggleFlip();
    }

    public void deSpawn() {
        view.deSpawn();
    }
}
