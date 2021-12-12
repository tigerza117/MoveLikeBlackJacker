package co.yunchao.server.controllers;

import co.yunchao.base.models.Deck;
import co.yunchao.base.enums.GameState;
import co.yunchao.base.enums.PlayerInGameState;
import co.yunchao.base.enums.Result;
import co.yunchao.server.models.Player;

import java.util.ArrayList;

public class GameController implements Runnable {

    private final ArrayList<Player> players;
    private final Deck deck;
    private final Thread thread;
    private final Player dealer;
    private int tick = 20;
    private int playerTurnIndex = 0;
    private GameState state;

    public GameController() {
        this.players = new ArrayList<>();
        this.deck = new Deck();
        this.thread = new Thread(this);
        this.dealer = new Player("Dealer", this, true);
        this.state = GameState.WAITING;
        this.Initial();
    }

    public void Initial() {
        for(Player player: this.players){
            player.getInventory().clearCard();
        }
        this.deck.generateCards();
        for(Player player : this.players){
            var con = player.getPlayerController();
            for (int i = 0; i < 2; i++) {
                player.pickUpCard(this.deck);
            }
            con.stackCurrentBetStage(50);
            con.confirmBet();
        }

        for(Player player: this.players){
            System.out.println(player.getInventory().getPoint());
        }
        thread.start();
    }

    public void playerJoin(Player player) {
        this.players.add(player);
    }

    public Deck getDeck(){
        return this.deck;
    }

    private boolean paused = false;

    private synchronized void checkPaused(){
        try{
            while(paused){
                this.wait();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public synchronized void pauseThread(){
        this.paused = !this.paused;
        if(!this.paused) this.notify();
    }

    @Override
    public void run() {
        try {
            while (true) {
                checkPaused();
                System.out.println(state + " > " + tick);
                switch (state) {
                    case WAITING:
                        if (this.players.size() > 0) {
                            this.tick = 15;
                            this.state = GameState.BET;
                        }
                        break;
                    case BET:
                        if (this.tick == 0) {
                            dealer.reset();
                            players.add(dealer);
                            players.forEach(player -> {
                                player.getPlayerController().skip();
                                //clear card
                            });
                            this.state = GameState.HAND_OUT;
                        } else {
                            this.tick--;
                        }
                        break;
                    case HAND_OUT:
                        for (int i = 0; i < 2; i++) {
                            players.forEach(player -> {
                                if (player.isReady()) {
                                    player.pickUpCard(deck);
                                }
                            });
                        }
                        if (dealer.getPlayerController().getState() == PlayerInGameState.WINING) {
                            //flip card dealer
                            this.state = GameState.PAY_OUT;
                        } else {
                            this.state = GameState.IN_GAME;
                            this.playerTurnIndex = 0;
                            this.tick = 15;
                        }
                        break;
                    case IN_GAME:
                        if (players.size() == playerTurnIndex) {
                            this.state = GameState.PAY_OUT;
                        } else {
                            var player = this.players.get(playerTurnIndex);
                            var controller = player.getPlayerController();
                            if (this.tick != 0) {
                                if (player.isDealer()) {
                                    while (player.getInventory().getPoint() < 17) {
                                        player.pickUpCard(this.deck);
                                    }
                                }
                                System.out.println(player.getName() + "[" + player.getInventory().getPoint() + "] Turn > " + controller.getState());
                                switch (controller.getState()) {
                                    case BUST:
                                        System.out.println("BUST!!!");
                                        //Remove card
                                        //Remove bet state
                                    case SKIP:
                                    case WINING:
                                    case DOUBLE:
                                    case STAND:
                                        this.tick = 0;
                                        break;
                                    case HIT:
                                    case READY:
                                        this.tick--;
                                        if (player.isDealer()) {
                                            this.tick = 0;
                                        }
                                        break;
                                }
                                if (tick == 0) {
                                    if (controller.getState() == PlayerInGameState.READY) {
                                        controller.stand();
                                    }
                                    this.playerTurnIndex++;
                                    this.tick = 15;
                                    continue;
                                }
                            }
                        }
                        break;
                    case PAY_OUT:
                        players.remove(dealer);
                        players.forEach(player -> {
                            var result = player.getPlayerController().getResult(dealer);
                            var ratio = 0.0;
                            if (result != Result.LOSE) {
                                System.out.println(player.getName() + " Wining");
                                switch (result) {
                                    case BLACKJACK:
                                        ratio = 2.5;
                                        break;
                                    case DRAW:
                                        ratio = 1;
                                        break;
                                    case Card5:
                                    case HIGH_POINT:
                                        ratio = 2;
                                        break;
                                    default:
                                        ratio = 0;
                                }
                            }

                            player.getPlayerController().getReward(ratio);
                            player.reset();
                            this.tick = 5;
                            state = GameState.WAITING;
                        });
                        break;
                }
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public GameState getState() {
        return state;
    }

    public boolean isInGame() {
        return state == GameState.IN_GAME;
    }

    public Player getPlayerInTurn() {
        if (isInGame()) {
            return this.players.get(playerTurnIndex);
        }
        return null;
    }

}

