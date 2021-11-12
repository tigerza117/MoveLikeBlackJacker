package co.yunchao.models;

import java.util.ArrayList;

public class Inventory {
    private final ArrayList<Card> cards;
    private final Player player;

    public Inventory(Player player) {
        this.cards = new ArrayList<>();
        this.player = player;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public Player getPlayer() {
        return player;
    }

    public void addCard(Card card) {
        System.out.printf("[Player]%s got: %s-%s card\n", player.getName(), card.getName(), card.getSuit());
        this.cards.add(card);
    }

    public void clearCard() {
        this.cards.clear();
    }

    public int getPoint() {
        ArrayList<Card> aceStack = new ArrayList<>();
        int points = 0;
        for (Card card: this.cards) {
            if (card.getPoint() == 101) {
                aceStack.add(card);
            } else {
                points += card.getPoint();
            }
        }
        for (Card card: aceStack.stream().toList()) {
            if (points + 10 + aceStack.size() > 21) {
                points++;
            } else {
                points+=10;
            }
            aceStack.remove(card);
        }
        return points;
    }
}
