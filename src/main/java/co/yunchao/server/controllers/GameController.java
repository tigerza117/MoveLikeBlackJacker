package co.yunchao.server.controllers;

import co.yunchao.base.models.Deck;
import co.yunchao.base.models.Player;

import java.util.ArrayList;

public class GameController implements Runnable {
    private ArrayList<Player> players;
    private ArrayList<PlayerController> playerControls = new ArrayList<PlayerController>();
    private Deck deck = new Deck();
    private PlayerController playerCon;
    private DealerController dealerCon;
    private boolean playerWinnable = false;
    private boolean playerGotBlackJack = false;
    private boolean playerDraw = false;
    private int playerTimer = 20;
    private int playRound = 0;
    private boolean gameEnd = false;
    private Thread thread;

    public GameController(ArrayList<Player> players) {
        this.players = players;
        boolean first = true;
        for(Player player : players){
            this.playerControls.add(new PlayerController(player));
            if(players.indexOf(player) == 4){
                this.dealerCon = new DealerController(player);
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
            this.playerCon.setCurrentBetStage(50);
            this.playerCon.bet();
        }

        for(Player player: this.players){
            System.out.println(player.getInventory().getPoint());
        }
        thread = new Thread(this);
        thread.start();
    }

    // สรุปสุดท้ายใครชนะไม่ชนะ
    public void endResult(){
        this.setPlayRound(0);
        for(PlayerController playerConCheck: this.getPlayerControls()){
            if (this.playerWinnable){
                //ชนะปกติ
                playerConCheck.addChip(2);
            }
            else if (playerGotBlackJack){
                //ชนะเเบบได้ Bonus
                playerConCheck.addChip(2.5);
            }
            else if (playerDraw){
                //เสมอ
                playerConCheck.addChip(1);
            }
            else{
                //แพ้
                playerConCheck.addChip(0);
            }
            playerDraw = false;
            playerGotBlackJack = false;
            this.playRound++;
        }
    }

    public synchronized void checkHit(){
        if(this.getPlayer().getInventory().getPoint() != 21 && this.getPlayer().getInventory().getPoint() < 21){
            this.playerCon.setPlayerHit(true);
        }
    }

    public synchronized void checkDoubleDown(){
        if(this.getPlayer().getInventory().getPoint() != 21 && this.getPlayer().getInventory().getPoint() < 21){
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

    public void playerCheckWin() {
        for(PlayerController playerConCheck: this.getPlayerControls()){
            if(playerConCheck.CheckPlayerBust()){
                playerConCheck.setPlayerWinnable(false);
            }
            else if(dealerCon.CheckDealerBust()){
                if(!playerConCheck.CheckPlayerBust())
                    playerConCheck.setPlayerWinnable(true);
            }
            else if(dealerCon.CheckDealer5Card()){
                if(playerConCheck.CheckPlayer5Card()){
                    playerConCheck.setPlayerWinnable(true);
                }
                else if(playerConCheck.CheckPlayerBlackJack()){
                    playerConCheck.setPlayerWinnable(true);
                }
            }
            else if(dealerCon.CheckDealerBlackJack()) {
                if (playerConCheck.CheckPlayerBlackJack()) {
                    playerConCheck.setPlayerWinnable(true);
                }
            }
            else if(playerConCheck.getPlayer().getInventory().getPoint() < 21 && dealerCon.getPoint() < playerConCheck.getPlayer().getInventory().getPoint() && playerConCheck.getPlayerStand()){
                playerConCheck.setPlayerWinnable(true);
            }
            else if(playerConCheck.getPlayer().getInventory().getPoint() < 21 && dealerCon.getPoint() == playerConCheck.getPlayer().getInventory().getPoint() && playerConCheck.getPlayerStand()){
                this.playerDraw = true;
            }
            playerConCheck.setPlayerWinnable(false);
        }
    }

    public synchronized void nextRound (){
        if(this.getPlayerConWithIndex().getPlayerStand()){
            this.getPlayerConWithIndex().setPlayerHit(false);
            this.getPlayerConWithIndex().setPlayerDoubledown(false);
            this.getPlayerConWithIndex().setPlayerBet(false);
        }
    }

    public synchronized boolean CheckLast(){
        if(this.playRound + 1 == this.players.size() - 1){
            return true;
        }
        this.gameEnd = true;
        return false;
    }

    public void setPlayRound(int playRound){
        this.playRound = playRound;
    }

    public void setPlayerTimer(int time){
        this.playerTimer = time;
    }

    public int getPlayerTimer() {
        return playerTimer;
    }

    public boolean getPlayerWinnable(){
        return playerWinnable;
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

    public Player getPlayerFromPlayerCon(){
        return this.getPlayerConWithIndex().getPlayer();
    }

    public PlayerController getPlayerConWithIndex(){
        return this.playerControls.get(this.getPlayRound());
    }

    public PlayerController getPlayerController(){
        return this.playerCon;
    }

    public ArrayList<PlayerController> getPlayerControls(){
        return this.playerControls;
    }

    public void addChip(double ratio){
        this.getPlayerFromPlayerCon().setChips(this.getPlayerFromPlayerCon().getChips() + (int) (this.getPlayerConWithIndex().getCurrentBetStage() * ratio));
    }

    @Override
    public void run() {
        try{
            if(this.playerTimer != 0 && !this.playerCon.getPlayerStand() && !this.getPlayerConWithIndex().CheckPlayerBust()){
                while (true){
                    if(this.getPlayerConWithIndex().CheckPlayerBlackJack()){
                        System.out.println("This Player got BlackJack.");
                        this.getPlayerController().setPlayerStand(true);
                        this.setPlayerTimer(20);
                        this.playRound++;
                        System.out.println(this.getPlayer().getName() + " Turn.");
                    }
                    else if(this.CheckLast() && this.getPlayerConWithIndex().CheckPlayerBlackJack()){
                        System.out.println("This Player got BlackJack.");
                        this.getPlayerController().setPlayerStand(true);
                        System.out.println("Break");
                        this.endResult();
                        break;
                    }
                    else if(this.playerTimer != 0 && !this.getPlayerConWithIndex().CheckPlayerBust() && !this.getPlayerConWithIndex().CheckPlayer5Card()){
                        System.out.println(this.getPlayerConWithIndex().getPlayer().getName() + " point " + this.getPlayerConWithIndex().getPlayer().getInventory().getPoint());
                    System.out.println(this.playerTimer);
                    this.playerTimer--;
                    Thread.sleep(1000);
                    this.nextRound();
                    }
                    else if(this.CheckLast() && (this.playerTimer == 0 || this.getPlayerConWithIndex().CheckPlayerBust()
                            || this.getPlayerConWithIndex().CheckPlayer5Card())){
                        this.getPlayerController().setPlayerStand(true);
                        this.playerCheckWin();
                        this.endResult();
                        for(Player player: players){
                            System.out.println(player.getChips());
                            System.out.println(this.getPlayerController().getPlayerWinnable());
                        }
                        System.out.println("Break");
                        break;
                    }
                    else if(!this.CheckLast() && this.getPlayerConWithIndex().CheckPlayer5Card()){
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

