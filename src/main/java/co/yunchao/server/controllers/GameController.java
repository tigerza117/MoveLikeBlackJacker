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
    private boolean playerWinable = false;
    private boolean playerGotBlackJack = false;
    private boolean playerDraw = false;
    private int roundCount = 0;
    private PlayerController playerCon;
    private DealerController dealerCon;
    private final int playerTimer = 20;

    public GameController(Player player){
        this.dealer = player;
    }

    GameController(ArrayList<Player> players) {
        this.players = players;
        boolean first = true;
        for(Player player : players){
            if(first){
                dealerCon = new DealerController(player);
                first = false;
            }
            else {
                playerCon = new PlayerController(player);
            }
        }
        this.Initial();
    }

    public void Initial() {
        this.deck.generateCards();
        for(Player player : this.players){
            playerCon = new PlayerController(player);
            if(!this.playerCon.getPlayerBet()){
                for (int i = 0; i < 2; i++) {
                    player.pickUpCard(this.deck);
                }
            }
        }
        for(Player player: this.players){
            System.out.println(player.getInventory().getPoint());
        }

        //อาจจะไม่ใช้ไปใช้ playerCheckWin เเทน เก็บไว้เผิ่อเฉยๆ
        for(Player player: this.players){
            if(dealerCon.CheckDealerBlackJack()) {
                if (playerCon.CheckPlayerBlackJack()) {
                    playerWinable = true;
                    playerGotBlackJack = true;
                } else {
                    playerWinable = false;
                }
            }
            else if(dealerCon.CheckDealer5Card()){
                if (playerCon.CheckPlayerBlackJack()){
                    playerWinable = true;
                    playerGotBlackJack = true;
                }
                else playerWinable = playerCon.CheckPlayer5Card();
            }
            else if(player.getInventory().getPoint() < 21 && dealerCon.getPoint() < player.getInventory().getPoint() && playerCon.getPlayerStand() == true){
                playerWinable = true;
            }
            else if(player.getInventory().getPoint() < 21 && dealerCon.getPoint() == player.getInventory().getPoint() && playerCon.getPlayerStand() == true){
                playerDraw = true;
                playerWinable = false;
            }
            else{
                playerWinable = false;
            }
        }

        for(Player player: this.players){
            Thread t = new Thread(player);
            t.start();
            System.out.println(player.getInventory().getPoint());
        }

    }
    // สรุปสุดท้ายใครชนะไม่ชนะ
    public void endResult(){
        for(Player player: this.players){
            playerCheckWin(player);
            if (playerWinable){
                //ชนะปกติ
            }
            else if (playerGotBlackJack){
                //ชนะเเบบได้ Bonus
            }
            else if (playerDraw){
                //ได้เงินที่ลงคืน
            }
            else{
                //โดนกิน
            }
            playerWinable = false;
            playerDraw = false;
            playerGotBlackJack = false;
        }
    }

    public void checkRound(){

    }

    public void checkHit(){
        for(Player player: this.players){
            if(player.getInventory().getPoint() != 21 && player.getInventory().getPoint() < 21){
                this.playerCon.setPlayerHit(true);
            }
        }
    }

    public void checkDoubleDown(){
        for(Player player: this.players){
            if(player.getInventory().getPoint() != 21){
                this.playerCon.setPlayerDoubledown(true);
            }
        }
    }

    public boolean playerCheckWin(Player player) {
            if(dealerCon.CheckDealer5Card()){
                if(playerCon.CheckPlayer5Card()){
                    playerWinable = true;
                    return true;
                }
                else if(playerCon.CheckPlayerBlackJack()){
                    playerGotBlackJack = true;
                    return true;
                }
            }
            else if(dealerCon.CheckDealerBlackJack()) {
                if (playerCon.CheckPlayerBlackJack()) {
                    playerGotBlackJack = true;
                    return true;
                }
        }
            else if(player.getInventory().getPoint() < 21 && dealerCon.getPoint() < player.getInventory().getPoint() && playerCon.getPlayerStand()){
                return playerWinable = true;
            }
            else if(player.getInventory().getPoint() < 21 && dealerCon.getPoint() == player.getInventory().getPoint() && playerCon.getPlayerStand()){
                return playerDraw = true;
            }
        return playerWinable = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //ingame control
        //...
    }

    public int getPlayerTimer() {
        return playerTimer;
    }

    @Override
    public void run() {

    }
}

