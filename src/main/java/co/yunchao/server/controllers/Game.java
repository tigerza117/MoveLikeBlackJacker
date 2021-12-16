package co.yunchao.server.controllers;

import co.yunchao.base.models.Deck;
import co.yunchao.base.enums.GameState;
import co.yunchao.base.enums.Result;
import co.yunchao.base.models.Offset;
import co.yunchao.net.packets.*;

import java.util.*;

public class Game extends co.yunchao.base.models.Game implements Runnable {
    private final HashMap<UUID,Player> players;
    private final Deck deck;
    private final Thread thread;
    private final Player dealer;
    private int tick = 20;
    private int maxTick = 5;
    private boolean isRunning = true;
    private int maxPlayer;
    private final Queue<Offset> seatOffset = new LinkedList<>(List.of(
            new Offset( 432,294),
            new Offset( 729,377),
            new Offset( 1024,375),
            new Offset( 1319,293)
    ));

    public Game(String id) {
        super(id);
        this.players = new HashMap<>();
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
        players.values().forEach(player -> {
            player.putPacket(packet);
        });
    }

    public boolean join(Player player) {
        if (this.players.size() >= 5) return false;
        System.out.println("Room > " + getId() + " > Player " + player.getName() +" has been join" );
        player.setGame(this);
        if (!player.isDealer()) {
            var offset = seatOffset.poll();
            if (offset == null) {
                return false;
            }
            player.setOffset(offset);
        }
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
            packet.offsetX = player.getOffset().getX();
            packet.offsetY = player.getOffset().getY();
            putPacket(packet);
        }
        player.setBalance(5000);
        this.players.put(player.getId(), player);
        for (Player pl : players.values()) {
            PlayerJoinPacket playerJoinPacket = new PlayerJoinPacket();
            playerJoinPacket.id = pl.getId();
            playerJoinPacket.name = pl.getName();
            playerJoinPacket.isDealer = pl.isDealer();
            playerJoinPacket.offsetX = pl.getOffset().getX();
            playerJoinPacket.offsetY = pl.getOffset().getY();
            player.putPacket(playerJoinPacket);

            PlayerMetadataPacket playerMetadataPacket = new PlayerMetadataPacket();
            playerMetadataPacket.id = pl.getId();
            playerMetadataPacket.name = pl.getName();
            playerMetadataPacket.chips = pl.getBalance();
            playerMetadataPacket.state = pl.getState();
            playerMetadataPacket.isDealer = pl.isDealer();
            playerMetadataPacket.currentBetStage = pl.getCurrentBetStage();
            player.putPacket(playerMetadataPacket);

            pl.sendData(player);
        }
        return true;
    }

    public void leave(Player player) {
        broadcastPlayerLeave(player);
        player.setGame(null);
        seatOffset.add(player.getOffset());
        this.players.remove(player.getId());
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
        packet.currentPlayerTurn = getPlayerTurn();
        putPacket(packet);
    }

    public Deck getDeck() {
        return this.deck;
    }

    public int countPlayers() {
        return players.size() - (getState().equals(GameState.IN_GAME) ? 1 : 0);
    }

    @Override
    public void setPlayerTurn(UUID playerTurn) {
        super.setPlayerTurn(playerTurn);
        broadcastGameState();
    }

    @Override
    public void run() {
        try {
            while (isRunning) {
                var clonedPlayer = players.values();
                broadcastGameState();
                System.out.println(getState() + " > " + tick);
                if (countPlayers() < 1 && !getState().equals(GameState.WAITING)) {
                    System.out.println("No one in game");
                    Thread.sleep(1000);
                    break;
                }
                switch (getState()) {
                    case WAITING:
                        if (countPlayers() > 0) {
                            this.tick = 30;
                            this.maxTick = 30;
                            setState(GameState.BET);
                        }
                        break;
                    case BET:
                        if (this.tick == 0) {
                            for (Player player : players.values()) {
                                player.skip();
                            }
                            setState(GameState.HAND_OUT);
                        } else {
                            this.tick--;
                        }
                        break;
                    case HAND_OUT:
                        for (int i = 0; i < 2; i++) {
                            for (Iterator<Player> it = players.values().iterator(); it.hasNext();) {
                                Player player = it.next();
                                if (!player.isDealer() && player.isOnline() && player.isReady()) {
                                    player.pickUpCard();
                                    Thread.sleep(1000);
                                }
                            }
                            dealer.pickUpCard();
                            Thread.sleep(1000);
                        }
                        if (dealer.isWining()) {
                            setState(GameState.PAY_OUT);
                        } else {
                            setState(GameState.IN_GAME);
                            this.tick = 15;
                            this.maxTick = 15;
                        }
                        break;
                    case IN_GAME:
                        for (Player player : clonedPlayer) {
                            if (!player.isDealer() && player.isOnline()) {
                                this.tick = 15;
                                this.maxTick = 15;
                                while (this.tick != 0) {
                                    System.out.println(player.getName() + "[" + player.getInventory().getPoint() + "] Turn > " + player.getState());
                                    switch (player.getState()) {
                                        case BUST:
                                            System.out.println("BUST!!!");
                                        case SKIP:
                                        case IDLE:
                                        case WINING:
                                        case DOUBLE:
                                        case STAND:
                                            this.tick = 0;
                                            break;
                                        case HIT:
                                        case READY:
                                            setPlayerTurn(player.getId());
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
                                        continue;
                                    }
                                    try {
                                        broadcastGameState();
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                        setPlayerTurn(dealer.getId());
                        while (dealer.getInventory().getPoint() < 17) {
                            System.out.println("====================================================");
                            System.out.println("Before pickup point " + dealer.getInventory().getPoint());
                            System.out.println("====================================================");
                            dealer.getInventory().getCards().forEach(card -> {
                                System.out.println("+++ " + card.getName() + ":" + card.getNumber() + "[" + card.getPoint() + "]" + " +++");
                            });
                            dealer.pickUpCard();
                            Thread.sleep(1000);
                            System.out.println("====================================================");
                            System.out.println("After pickup "+ dealer.getInventory().getPoint());
                            System.out.println("====================================================");
                            dealer.getInventory().getCards().forEach(card -> {
                                System.out.println(card.getName());
                            });
                        }
                        setState(GameState.PAY_OUT);
                        setPlayerTurn(UUID.randomUUID());
                        break;
                    case PAY_OUT:
                        for (Player player : clonedPlayer) {
                            if (!player.isDealer()) {
                                var result = player.getResult(dealer);
                                var ratio = 0.0;
                                if (!result.equals(Result.LOSE) && !player.isBust()) {
                                    System.out.println(player.getName() + " " + result.name() + " Wining");
                                    switch (result) {
                                        case BLACKJACK:
                                            ratio = 2.5;
                                            break;
                                        case DRAW:
                                            ratio = 1;
                                            break;
                                        case DEALER_BUST:
                                        case Card5:
                                        case HIGH_POINT:
                                            ratio = 2;
                                            break;
                                        default:
                                            ratio = 0;
                                    }
                                }

                                player.getReward(ratio);
                                this.tick = 5;
                                setState(GameState.WAITING);
                            }
                        }
                        Thread.sleep(10000);
                        for (Player player : clonedPlayer) {
                            player.reset();
                        }
                        break;
                }
                broadcastGameState();
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

