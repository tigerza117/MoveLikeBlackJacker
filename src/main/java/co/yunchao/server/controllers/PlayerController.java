package co.yunchao.server.controllers;

import co.yunchao.base.models.Deck;
import co.yunchao.base.models.Inventory;
import co.yunchao.base.models.Player;

public class PlayerController {
    private Player player;
    private Deck deck;
    private Inventory inv;
    private boolean playerStand = false;
    private boolean playerDoubledown = false;
    private boolean playerHit = false;
    private boolean playerBet = false;
    private boolean playerEndround = false;

    public PlayerController(Player player){
        this.player = player;
    }

    public void pickUpCard(Deck deck) {
        this.inv.addCard(deck.pickTopCard());
    }

    public int bet(int amount){ //get amount from view (set later)
        this.player.setChips(this.player.getChips() - amount);
        this.playerBet = true;
        return this.player.getChips();
    }

    public void hit(){
        if(this.playerHit && this.playerBet){
            this.pickUpCard(deck);
        }
    }

    public void stand(){
        if((this.player.getInventory().getPoint() <= 21 && this.playerEndround)){
            //click btn
            this.playerStand = true;
        }
    }

    public void doubleDown(int amount){ //same as bet
        this.bet(amount);
        if(this.player.getInventory().getPoint() <= 21 && this.player.getInventory().getCards().size() == 2){
            this.playerStand = true;
        }
    }

    public boolean actionControl(){
        return this.getPlayerDoubledown() || this.getPlayerHit();
    }
    public boolean CheckPlayerBlackJack(){
        return this.player.getInventory().getPoint() == 21 && this.player.getInventory().getCards().size() == 2;
    }

    public boolean IsPlayerAlreadyAction(){
        return this.playerStand || this.playerHit;
    }

    public boolean CheckPlayer5Card(){
        return this.player.getInventory().getCards().size() == 5 && this.player.getInventory().getPoint() <= 21;
    }

    public boolean CheckPlayerBust(){
        return this.player.getInventory().getPoint() > 21;
    }


    public void setPlayerBet(boolean playerBet) {
        this.playerBet = playerBet;
    }

    public void setPlayerDoubledown(boolean playerDoubledown) {
        this.playerDoubledown = playerDoubledown;
    }

    public void setPlayerHit(boolean playerHit) {
        this.playerHit = playerHit;
    }

    public void setPlayerStand(boolean playerStand) {
        this.playerStand = playerStand;
    }

    public boolean getPlayerStand(){
        return this.playerStand;
    }

    public boolean getPlayerHit(){
        return this.playerHit;
    }

    public boolean getPlayerDoubledown(){
        return this.playerDoubledown;
    }

    public boolean getPlayerBet(){
        return this.playerBet;
    }

    public Player getPlayer() {
        return this.player;
    }
}


