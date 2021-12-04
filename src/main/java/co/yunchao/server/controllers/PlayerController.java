package co.yunchao.server.controllers;

import co.yunchao.base.models.Deck;
import co.yunchao.base.models.Inventory;
import co.yunchao.base.models.Player;

public class PlayerController {
    private boolean playerStand = false;
    private boolean playerDoubledown = false;
    private boolean playerSplit = false;
    private boolean playerHit = false;
    private boolean playerBet = false;
    private Player player;
    private Inventory inv;
    private Deck deck;
    PlayerController(Player player, Inventory inv){
        this.player = player;
        this.inv = inv;
    }

    public void pickUpCard(Deck deck) {
        this.inv.addCard(deck.pickTopCard());
    }

    public int bet(int amount){ //get amount from view (set later)
        this.player.setChips(player.getChips() - amount);
        this.playerBet = true;
        this.playerDoubledown = true;
        this.playerHit = true;
        this.playerStand = true;
        return amount;
        //change view (Enable some buttons)
    }

    public void hit(){
        if((this.playerHit && this.playerBet) == true){
            this.pickUpCard(deck);

        }
    }

    public void stand() {
        if((this.playerStand && this.playerBet) == true){
            //winable
        }
    }

    public void doubleDown(int amount){ //same as bet
        if((this.playerDoubledown && this.playerBet) == true){
            this.bet(amount);
            this.hit();
            this.playerHit = false;
            this.playerStand = true;
            this.playerDoubledown = false;
        }
    }

    public void split(){
        if(this.playerSplit == true){
            //action
        }
    }


}


