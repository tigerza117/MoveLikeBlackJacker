package co.yunchao.server.controllers;

import co.yunchao.base.models.Deck;
import co.yunchao.base.models.Player;

import java.util.ArrayList;

public class GameController implements Runnable {
    private ArrayList<Player> players;
    private Player dealer;
    private Deck deck = new Deck();
    private PlayerController playerCon;
    private DealerController dealerCon;
    private int amount; //get from view
    private boolean playerWinable = false;
    private boolean playerGotBlackJack = false;
    private boolean playerDraw = false;
    private int playerTimer = 20;
    private int playRound = 0;
    private Thread thread;
    public GameController(Player player){
        this.dealer = player;
    }

    public GameController(ArrayList<Player> players) {
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
        for(Player player: this.players){
            player.getInventory().clearCard();
        }
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
            else if(player.getInventory().getPoint() < 21 && dealerCon.getPoint() < player.getInventory().getPoint() && playerCon.getPlayerStand()){
                playerWinable = true;
            }
            else if(player.getInventory().getPoint() < 21 && dealerCon.getPoint() == player.getInventory().getPoint() && playerCon.getPlayerStand()){
                playerDraw = true;
                playerWinable = false;
            }
            else{
                playerWinable = false;
            }
        }
        thread = new Thread(this);
        thread.start();
    }

    public void checkRound(){
        for(int i = 0; i < 4; i++){
            if(this.playerCon.IsPlayerAlreadyAction()){
                playRound++;
                this.playerTimer = 20;
            }
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

    public void checkStand(){
        if(this.getPlayer().getInventory().getPoint() <= 21){
            this.playerCon.getPlayer();
        }
    }

    public void playerCheckWin(Player player) {
            if(playerCon.CheckPlayerBust()){
                return;
        }
            else if(dealerCon.CheckDealerBust()){
                if(!playerCon.CheckPlayerBust())
                    playerWinable = true;
                    return;
        }
            else if(dealerCon.CheckDealer5Card()){
                if(playerCon.CheckPlayer5Card()){
                    playerWinable = true;
                    return;
                }
                else if(playerCon.CheckPlayerBlackJack()){
                    playerGotBlackJack = true;
                    return;
                }
            }
            else if(dealerCon.CheckDealerBlackJack()) {
                if (playerCon.CheckPlayerBlackJack()) {
                    playerGotBlackJack = true;
                    return;
                }
        }
            else if(player.getInventory().getPoint() < 21 && dealerCon.getPoint() < player.getInventory().getPoint() && playerCon.getPlayerStand()){
                playerWinable = true;
                return;
            }
            else if(player.getInventory().getPoint() < 21 && dealerCon.getPoint() == player.getInventory().getPoint() && playerCon.getPlayerStand()){
                playerDraw = true;
                return;
            }
        playerWinable = false;
    }

    public void setPlayerTimer(int time){
        this.playerTimer = time;
    }

    public int getPlayerTimer() {
        return playerTimer;
    }

    public int getPlayRound(){
        return this.playRound;
    }

    public Player getPlayer(){
        return this.players.get(this.playRound);
    }

    public PlayerController getPlayerController(){
        return this.playerCon;
    }

    @Override
    public void run() {
        try{
            while (this.playerTimer != 0 && !this.playerCon.IsPlayerAlreadyAction()){
                System.out.println(this.playerTimer);
                this.playerTimer--;
                Thread.sleep(1000);
            }
            thread.wait();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        } {

        }
    }
}

