package co.yunchao.server.controllers;

import co.yunchao.base.models.Deck;
import co.yunchao.base.enums.GameState;
import co.yunchao.base.enums.Result;
import co.yunchao.net.packets.*;

import java.util.ArrayList;

public class Game extends co.yunchao.base.models.Game implements Runnable {
    private final ArrayList<Player> players;
    private final Deck deck;
    private final Thread thread;
    private final Player dealer;
    private int tick = 20;
    private int playerTurnIndex = 0;

    public Game(String id) {
        super(id);
        this.players = new ArrayList<>();
        this.deck = new Deck();
        this.thread = new Thread(this);
        this.dealer = new Player("Dealer",true);
        setState(GameState.WAITING);
        for(Player player: this.players){
            player.getInventory().clearCards();
        }
        this.deck.generateCards();
        thread.start();
    }

    @Override
    public void putPacket(DataPacket packet) {
        players.forEach(player -> {
            player.putPacket(packet);
        });
    }

    @Override
    public synchronized boolean join(co.yunchao.base.models.Player player) {
        if (this.players.size() < 4) return false;
        Player pl = (Player) player;
        broadcastPlayerJoin(pl);
        player.setGame(this);
        this.players.add(pl);
        return true;
    }

    @Override
    public synchronized void leave(co.yunchao.base.models.Player player) {
        Player pl = (Player) player;
        broadcastPlayerLeave(pl);
        player.setGame(null);
        this.players.remove(pl);
    }

    public void broadcastPlayerJoin(Player player) {
        PlayerJoinPacket packet = new PlayerJoinPacket();
        packet.id = player.getId();
        packet.name = player.getName();
        putPacket(packet);

        GameMetadataPacket gameMetadataPacket = new GameMetadataPacket();
        gameMetadataPacket.id = getId();
        gameMetadataPacket.state = getState();
        gameMetadataPacket.tick = tick;
        player.putPacket(gameMetadataPacket);

        players.forEach(pl -> {
            PlayerMetadataPacket playerMetadataPacket = new PlayerMetadataPacket();
            playerMetadataPacket.id = pl.getId();
            playerMetadataPacket.name = pl.getName();
            playerMetadataPacket.chips = pl.getChips();
            playerMetadataPacket.state = pl.getState();
            playerMetadataPacket.isDealer = pl.isDealer();
            playerMetadataPacket.currentBetStage = pl.getCurrentBetStage();
            player.putPacket(playerMetadataPacket);
        });
    }

    public void broadcastPlayerLeave(Player player) {
        PlayerLeavePacket packet = new PlayerLeavePacket();
        packet.id = player.getId();
        packet.name = player.getName();
        putPacket(packet);
    }

    public void broadcastGameState() {
        GameMetadataPacket packet = new GameMetadataPacket();
        packet.id = getId();
        packet.state = getState();
        packet.tick = tick;
        putPacket(packet);
    }

    public Deck getDeck() {
        return this.deck;
    }

    public int countPlayers() {
        return players.size();
    }

    private boolean paused = false;

    private synchronized void checkPaused() {
        try{
            while(paused){
                this.wait();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public synchronized void pauseThread() {
        this.paused = !this.paused;
        if(!this.paused) this.notify();
    }

    @Override
    public void run() {
        try {
            while (true) {
                checkPaused();
                broadcastGameState();
                System.out.println(getState() + " > " + tick);
                switch (getState()) {
                    case WAITING:
                        if (this.players.size() > 0) {
                            this.tick = 15;
                            setState(GameState.BET);
                        }
                        break;
                    case BET:
                        if (this.tick == 0) {
                            dealer.reset();
                            players.add(dealer);
                            //clear card
                            players.forEach(Player::skip);
                            setState(GameState.HAND_OUT);
                        } else {
                            this.tick--;
                        }
                        break;
                    case HAND_OUT:
                        for (int i = 0; i < 2; i++) {
                            players.forEach(player -> {
                                if (player.isReady()) {
                                    player.pickUpCard();
                                }
                            });
                        }
                        if (dealer.isWining()) {
                            //flip card dealer
                            setState(GameState.PAY_OUT);
                        } else {
                            setState(GameState.IN_GAME);
                            this.playerTurnIndex = 0;
                            this.tick = 15;
                        }
                        break;
                    case IN_GAME:
                        if (players.size() == playerTurnIndex) {
                            setState(GameState.PAY_OUT);
                        } else {
                            var player = this.players.get(playerTurnIndex);
                            if (this.tick != 0) {
                                if (player.isDealer()) {
                                    while (player.getInventory().getPoint() < 17) {
                                        player.pickUpCard();
                                    }
                                }
                                System.out.println(player.getName() + "[" + player.getInventory().getPoint() + "] Turn > " + player.getState());
                                switch (player.getState()) {
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
                                    if (player.isReady()) {
                                        player.stand();
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
                            var result = player.getResult(dealer);
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

                            player.getReward(ratio);
                            player.reset();
                            this.tick = 5;
                            setState(GameState.WAITING);
                        });
                        break;
                }
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

