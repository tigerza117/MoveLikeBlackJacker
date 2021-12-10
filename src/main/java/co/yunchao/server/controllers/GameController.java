package co.yunchao.server.controllers;

import co.yunchao.base.models.Deck;
import co.yunchao.base.models.Player;

import java.util.ArrayList;

public class GameController implements Runnable {
    private ArrayList<Player> players;
    private ArrayList<PlayerController> playerControls = new ArrayList<PlayerController>();
    private Player dealer;
    private Deck deck = new Deck();
    private PlayerController playerCon;
    private DealerController dealerCon;
    private int amount; //get from view
    private boolean playerWinnable = false;
    private boolean playerGotBlackJack = false;
    private boolean playerDraw = false;
    private int playerTimer = 20;
    private int playRound = 0;
    private int betStage = 0;
    private boolean gameEnd = false;
    private Thread thread;
    public GameController(Player player){
        this.dealer = player;
    }

    public GameController(ArrayList<Player> players) {
        this.players = players;
        boolean first = true;
        for(Player player : players){
            this.playerControls.add(new PlayerController(player));
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
        /*for(Player player: this.players){
            if(dealerCon.CheckDealerBlackJack()) {
                if (playerCon.CheckPlayerBlackJack()) {
                    playerWinnable = true;
                    playerGotBlackJack = true;
                } else {
                    playerWinnable = false;
                }
            }
            else if(dealerCon.CheckDealer5Card()){
                if (playerCon.CheckPlayerBlackJack()){
                    playerWinnable = true;
                    playerGotBlackJack = true;
                }
                else playerWinnable = playerCon.CheckPlayer5Card();
            }
            else if(player.getInventory().getPoint() < 21 && dealerCon.getPoint() < player.getInventory().getPoint() && playerCon.getPlayerStand()){
                playerWinnable = true;
            }
            else if(player.getInventory().getPoint() < 21 && dealerCon.getPoint() == player.getInventory().getPoint() && playerCon.getPlayerStand()){
                playerDraw = true;
                playerWinnable = false;
            }
            else{
                playerWinnable = false;
            }
        }*/

        thread = new Thread(this);
        thread.start();

        /*while(!gameEnd){
            if(this.getPlayerControls().get(this.getPlayRound()).CheckPlayerBust()){
                this.playerWinnable = false;
                //disable ทุกฟังก์ชั่น
            }
        }*/
    }

    // สรุปสุดท้ายใครชนะไม่ชนะ
    public void endResult(){
        for(Player player: this.players){
            playerCheckWin(player);
            if (playerWinnable){
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
            playerWinnable = false;
            playerDraw = false;
            playerGotBlackJack = false;
        }
    }

    public synchronized void checkHit(){
        if(this.getPlayer().getInventory().getPoint() != 21 && this.getPlayer().getInventory().getPoint() < 21){
            this.playerCon.setPlayerHit(true);
        }
    }

    public synchronized void checkDoubleDown(){
        if(this.getPlayer().getInventory().getPoint() != 21){
            this.getPlayerController().setPlayerDoubledown(true);
            this.getPlayerController().setPlayerStand(true);
            this.setPlayerTimer(20);
            this.playRound++;
        }
    }

    public synchronized void checkStand(){
        if(this.getPlayer().getInventory().getPoint() <= 21){
            this.getPlayerController().setPlayerStand(true);
            this.setPlayerTimer(20);
            this.playRound++;
        }
    }
    public synchronized void LastStand(){
        this.getPlayerController().setPlayerStand(true);
        this.setPlayerTimer(0);
    }

    public void playerCheckWin(Player player) {
        if(playerCon.CheckPlayerBust()){
            this.playerWinnable = false;
        }
        else if(dealerCon.CheckDealerBust()){
            if(!playerCon.CheckPlayerBust())
                playerWinnable = true;
        }
        else if(dealerCon.CheckDealer5Card()){
            if(playerCon.CheckPlayer5Card()){
                playerWinnable = true;
            }
            else if(playerCon.CheckPlayerBlackJack()){
                playerGotBlackJack = true;
            }
        }
        else if(dealerCon.CheckDealerBlackJack()) {
            if (playerCon.CheckPlayerBlackJack()) {
                playerGotBlackJack = true;
            }
        }
        else if(player.getInventory().getPoint() < 21 && dealerCon.getPoint() < player.getInventory().getPoint() && playerCon.getPlayerStand()){
            playerWinnable = true;
        }
        else if(player.getInventory().getPoint() < 21 && dealerCon.getPoint() == player.getInventory().getPoint() && playerCon.getPlayerStand()){
            playerDraw = true;
        }
        playerWinnable = false;
    }

    public synchronized void nextRound (){
        if(this.playerControls.get(playRound).getPlayerStand()){
            this.playerControls.get(playRound).setPlayerHit(false);
            this.playerControls.get(playRound).setPlayerDoubledown(false);
            this.playerControls.get(playRound).setPlayerBet(false);
        }
    }

    public synchronized boolean CheckLast(){
        if(this.playRound + 1 == this.players.size()){
            return true;
        }
        return false;
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

    public Deck getDeck(){
        return this.deck;
    }

    public Player getPlayer(){
        return this.players.get(this.playRound);
    }

    public PlayerController getPlayerController(){
        return this.playerCon;
    }

    public ArrayList<PlayerController> getPlayerControls(){
        return this.playerControls;
    }

    @Override
    public void run() {
        try{
            if(this.playerTimer != 0 && !this.playerCon.getPlayerStand() && !this.playerControls.get(this.getPlayRound()).CheckPlayerBust()){
                while (true){
                    if(this.playerControls.get(this.getPlayRound()).CheckPlayerBlackJack()){
                        System.out.println("This Player got BlackJack.");
                        this.getPlayerController().setPlayerStand(true);
                        this.setPlayerTimer(20);
                        this.playRound++;
                        System.out.println(this.getPlayer().getName() + " Turn.");
                    }
                    else if(this.CheckLast() && this.playerControls.get(this.getPlayRound()).CheckPlayerBlackJack()){
                        System.out.println("This Player got BlackJack.");
                        this.getPlayerController().setPlayerStand(true);
                        System.out.println("Break");
                        break;
                    }
                    else if(this.playerTimer != 0 && !this.playerControls.get(this.getPlayRound()).CheckPlayerBust() && !this.playerControls.get(this.getPlayRound()).CheckPlayer5Card()){
                        System.out.println(this.playerControls.get(this.getPlayRound()).getPlayer().getName() + " point " + this.playerControls.get(this.getPlayRound()).getPlayer().getInventory().getPoint());
                    System.out.println(this.playerTimer);
                    this.playerTimer--;
                    Thread.sleep(1000);
                    this.nextRound();
                    }
                    else if(this.CheckLast() && (this.playerTimer == 0 || this.playerControls.get(this.getPlayRound()).CheckPlayerBust() || this.playerControls.get(this.getPlayRound()).CheckPlayer5Card())){
                        this.getPlayerController().setPlayerStand(true);
                        System.out.println("Break");
                        break;
                    }
                    else if(!this.CheckLast() && this.playerControls.get(this.getPlayRound()).CheckPlayer5Card()){
                        System.out.println("This Player got 5 cards.");
                        this.getPlayerController().setPlayerStand(true);
                        this.setPlayerTimer(20);
                        this.playRound++;
                        System.out.println(this.getPlayer().getName() + " Turn.");
                    }
                    else if(!this.CheckLast()){
                        System.out.println("This Player Bust or Time out!");
                        this.getPlayerController().setPlayerStand(true);
                        this.setPlayerTimer(20);
                        this.playRound++;
                        System.out.println(this.getPlayer().getName() + " Turn.");
                    }
                }
            }
            else{
                this.thread.wait();
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        } {

        }
    }
}

