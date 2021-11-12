package co.yunchao.models;

public class Player {
    private final Inventory inventory;
    private final int chips;
    private final String name;

    public Player(String name, int chips){
        this.name = name;
        this.chips = chips;
        this.inventory = new Inventory(this);
    }

    public int getChips() {
        return chips;
    }

    public String getName() {
        return name;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void pickUpCard(Deck deck) {
        this.inventory.addCard(deck.pickTopCard());
    }
}
