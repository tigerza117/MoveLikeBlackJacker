package co.yunchao.server.controllers;

import co.yunchao.base.models.Deck;
import co.yunchao.base.enums.GameState;
import co.yunchao.base.enums.Result;
import co.yunchao.net.packets.*;

import java.util.ArrayList;
import java.util.UUID;

public class Game extends co.yunchao.base.models.Game implements Runnable {
    private final ArrayList<Player> players;
    private final Deck deck;
    private final Thread thread;
    private final Player dealer;
    private int tick = 20;
    private int maxTick = 5;
    private int playerTurnIndex = 0;

    public Game(String id) {
        super(id);
        this.players = new ArrayList<>();
        this.deck = new Deck();
        this.thread = new Thread(this);
        this.dealer = new Player(UUID.randomUUID(),"Dealer",true);
        dealer.setGame(this);
        setState(GameState.WAITING);
        this.deck.generateCards();
        thread.start();
        join(dealer);
    }

    @Override
    public void putPacket(DataPacket packet) {
        players.forEach(player -> {
            player.putPacket(packet);
        });
    }

    public boolean join(Player player) {
        if (this.players.size() >= 5) return false;
        System.out.println("Room > " + getId() + " > Player " + player.getName() +" has been join" );
        player.setGame(this);
        player.setBalance(5000);
        {
            GameMetadataPacket packet = new GameMetadataPacket();
            packet.id = getId();
            packet.state = getState();
            packet.tick = tick;
            player.putPacket(packet);
        }
        {
            PlayerJoinPacket packet = new PlayerJoinPacket();
            packet.id = player.getId();
            packet.name = player.getName();
            packet.isDealer = player.isDealer();
            putPacket(packet);
        }
        this.players.add(player);
        players.forEach(pl -> {
            PlayerJoinPacket playerJoinPacket = new PlayerJoinPacket();
            playerJoinPacket.id = pl.getId();
            playerJoinPacket.name = pl.getName();
            playerJoinPacket.isDealer = pl.isDealer();
            player.putPacket(playerJoinPacket);

            PlayerMetadataPacket playerMetadataPacket = new PlayerMetadataPacket();
            playerMetadataPacket.id = pl.getId();
            playerMetadataPacket.name = pl.getName();
            playerMetadataPacket.chips = pl.getBalance();
            playerMetadataPacket.state = pl.getState();
            playerMetadataPacket.isDealer = pl.isDealer();
            playerMetadataPacket.currentBetStage = pl.getCurrentBetStage();
            player.putPacket(playerMetadataPacket);
        });
        return true;
    }

    public void leave(Player player) {
        broadcastPlayerLeave(player);
        player.setGame(null);
        this.players.remove(player);
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
        packet.maxTick = maxTick;
        putPacket(packet);
    }

    public Deck getDeck() {
        return this.deck;
    }

    public int countPlayers() {
        return players.size() - (getState().equals(GameState.IN_GAME) ? 1 : 0);
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
                if (countPlayers() < 1 && !getState().equals(GameState.WAITING)) {
                    System.out.println("No one in game");
                    Thread.sleep(1000);
                    continue;
                }
                switch (getState()) {
                    case WAITING:
                        if (countPlayers() > 0) {
                            this.tick = 5;
                            setState(GameState.BET);
                        }
                        break;
                    case BET:
                        if (this.tick == 0) {
                            dealer.reset();
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
                            this.playerTurnIndex = 1;
                            this.tick = 15;
                        }
                        break;
                    case IN_GAME:
                        if (players.size() == playerTurnIndex) {
//                            while (dealer.getInventory().getPoint() < 17) {
//                                dealer.pickUpCard();
//                            }
                            setState(GameState.PAY_OUT);
                        } else {
                            var player = this.players.get(playerTurnIndex);
                            if (this.tick != 0) {
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
                                    this.tick = 5;
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

