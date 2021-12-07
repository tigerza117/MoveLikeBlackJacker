package co.yunchao.server.controllers;

import co.yunchao.base.models.Deck;
import co.yunchao.base.models.Inventory;
import co.yunchao.base.models.Player;

public class DealerController {
    private Player player;
    private Deck deck;
    private Inventory inv;
    private int hitPoint;
    private int round = 0;
    DealerController(Player player){
        this.player = player;
    }
    public void hit(){
        if(this.player.getInventory().getPoint() <= 16 && round % 4 == 0){
            this.inv.addCard(this.deck.pickTopCard());
        }
    }

    public int getPoint(){
        return this.player.getInventory().getPoint();
    }
    public boolean CheckDealerBlackJack(){
        if(this.player.getInventory().getPoint() == 21){
            return true;
        }
        return false;
    }
}
