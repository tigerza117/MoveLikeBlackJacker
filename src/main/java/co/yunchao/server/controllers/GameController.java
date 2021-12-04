package co.yunchao.server.controllers;

import co.yunchao.base.models.Card;
import co.yunchao.base.models.Deck;
import co.yunchao.base.models.Player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.annotation.Native;
import java.util.ArrayList;

public class GameController implements ActionListener {
    private ArrayList<Player> players;
    private Card card;
    private Deck deck;
    private int amount; //get from view
    private boolean playerWinable = true;
    GameController() {

    }

    /*public void split(){
        if(player.getInventory().getCards().indexOf(0) == player.getInventory().getCards().indexOf(1) || player.getInventory().getCards()){

        }
    }*/

    public void Initial() {
        this.deck.generateCards();
        for (int i = 0; i < 2; i++) {
            for (Player player: players) {
                player.pickUpCard(this.deck);
            }
        }
    }



    public boolean playerCheckWin(Player player) {
        //check dealer
        if(player.getInventory().getPoint() > 21){
            this.playerWinable = false;
            return false;
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //ingame control
        //...
    }
}

