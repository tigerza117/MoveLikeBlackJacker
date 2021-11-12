package co.yunchao.models;

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
            String []suits = new String[]{"Diamonds", "Spades", "Clubs", "Hearts"};
            for (String suit : suits) {
                cards.add(new Card(i, suit));
            }
        }
        Collections.shuffle(this.cards);
    }
}
