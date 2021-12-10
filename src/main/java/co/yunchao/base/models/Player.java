package co.yunchao.base.models;

import co.yunchao.server.controllers.GameController;
import co.yunchao.server.controllers.PlayerController;

public class Player{
    private final Inventory inventory;
    private double chips;
    private final String name;

    public Player(String name, int chips){
        this.name = name;
        this.chips = chips;
        this.inventory = new Inventory(this);
    }

    public void pickUpCard(Deck deck) {
        this.inventory.addCard(deck.pickTopCard());
    }

    public void setChips(double chip){this.chips = chip;}

    public double getChips() {
        return chips;
    }

    public String getName() {
        return name;
    }

    public Inventory getInventory() {
        return inventory;
    }

}
