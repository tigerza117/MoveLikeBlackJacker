package co.yunchao.base.models;

import co.yunchao.base.enums.CardSuit;

import java.util.*;

public class Deck {
    private final ArrayList<Card> cards;

    public Deck() {
        this.cards = new ArrayList<>();
    }

    public Card pickTopCard() {
        Card card = cards.get(cards.size()-1);
        cards.remove(card);
        return card;
    }

    public void generateCards() {
        this.cards.clear();
        for (int i = 1; i <= 13; i++) {
            for (CardSuit suit : CardSuit.values()) {
                cards.add(new Card(i, suit));
            }
        }
        Collections.shuffle(this.cards);
    }
}
