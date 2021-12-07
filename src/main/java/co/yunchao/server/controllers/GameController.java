package co.yunchao.server.controllers;

import co.yunchao.base.models.Card;
import co.yunchao.base.models.Deck;
import co.yunchao.base.models.Player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.annotation.Native;
import java.util.ArrayList;

public class GameController implements ActionListener, Runnable {
    private ArrayList<Player> players;
    private Player dealer;
    private Card card;
    private Deck deck = new Deck();
    private int amount; //get from view
    private boolean playerWinable = true;
    private int roundCount = 0;
    private PlayerController playerCon;
    private DealerController dealerCon;
    private final int playerTimer = 20;
    GameController(Player player){
        this.dealer = player;
    }
    GameController(ArrayList<Player> players) {
        this.players = players;
        for(Player player : players){
            playerCon = new PlayerController(player);
        }
        this.Initial();
    }

    public void Initial() {
        this.deck.generateCards();
        for(Player player : this.players){
            playerCon = new PlayerController(player);
            if(this.playerCon.getPlayerBet() == false){
                for (int i = 0; i < 2; i++) {
                    player.pickUpCard(this.deck);
                }
            }
        }
        for(Player player: players){
            System.out.println(player.getInventory().getPoint());
        }
    }

    public void checkRound(){

    }

    public void checkHit(){
        for(Player player: players){
            if(player.getInventory().getPoint() != 21 || player.getInventory().getPoint() < 21){
                this.playerCon.setPlayerHit(true);
            }
        }
    }

    public void checkDoubleDown(){
        for(Player player: players){
            if(player.getInventory().getPoint() != 21){
                this.playerCon.setPlayerDoubledown(true);
            }
        }
    }

    public boolean playerCheckWin() {
        for(Player player: players){
            if(player.getInventory().getPoint() <= 21 && dealerCon.getPoint() < player.getInventory().getPoint() && playerCon.getPlayerStand() == true){
                return playerWinable = true;
            }
        }
        return playerWinable = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //ingame control
        //...
    }

    @Override
    public void run() {

    }
}

