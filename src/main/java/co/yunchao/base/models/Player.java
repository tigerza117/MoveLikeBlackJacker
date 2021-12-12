package co.yunchao.base.models;

import java.util.UUID;

public class Player{
    private final Inventory inventory;
    private double chips;
    private String name;
    private UUID id;

    public Player(String name){
        this.name = name;
        this.inventory = new Inventory(this);
    }

    public void pickUpCard(Deck deck) {
        this.inventory.addCard(deck.pickTopCard());
    }

    public void setChips(double chip){
        this.chips = chip;
    }

    public double getChips() {
        return chips;
    }

    public String getName() {
        return name;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
