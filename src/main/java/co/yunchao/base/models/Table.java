package co.yunchao.base.models;

import java.util.ArrayList;

public class Table {
    private final ArrayList<Player> players;
    private final Deck deck;

    public Table() {
        this.players = new ArrayList<>();
        this.deck = new Deck();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Deck getDeck() {
        return deck;
    }
}
