package co.yunchao.base.models;

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

    public Card getFirstCard(){
        return cards.get(0);
    }

    public Card getSecondCard(){
        return cards.get(1);
    }

    public Player getPlayer() {
        return player;
    }

    public void addCard(Card card) {
        this.cards.add(card);
        System.out.printf("%s -> got: %s-%s card total point %d\n", player.getName(), card.getName(), card.getSuit(), getPoint());
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
        for (Object obj: aceStack.toArray()) {
            Card card = (Card) obj;
            if (points + 11 + aceStack.size() > 21) {
                points+=aceStack.size();
                break;
            } else {
                points+=11;
            }
            aceStack.remove(card);
        }
        return points;
    }

    public boolean isBlackJack(){
        return getPoint() == 21 && getCards().size() == 2;
    }

    public boolean is5Card(){
        return getCards().size() == 5 && getPoint() <= 21;
    }

    public boolean isBust(){
        return getPoint() > 21;
    }
}
