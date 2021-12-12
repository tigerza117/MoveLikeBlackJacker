package co.yunchao.server.controllers;

import co.yunchao.net.packets.*;
import co.yunchao.base.enums.GameState;
import co.yunchao.base.enums.PlayerInGameState;
import co.yunchao.base.enums.Result;

import java.util.ArrayList;

public class Player extends co.yunchao.base.models.Player {
    private final Game game;
    private PlayerInGameState state;
    private int currentBetStage = 0;
    private boolean isDealer;

    public Player(String name, Game game) {
        super(name, 1000);
        this.game = game;
    }

    public Player(String name, Game game, boolean isDealer) {
        this(name, game);
        this.isDealer = isDealer;
    }

    public void getReward(double ratio) {
        var reward = this.getCurrentBetStage() * ratio;
        this.setChips(this.getChips() + (reward));
        log("get reward " + reward + "$");
    }

    public void pickUpCard() {
        var inv = getInventory();
        inv.addCard(game.getDeck().pickTopCard());
        if (inv.isBlackJack()) {
            this.state = PlayerInGameState.WINING;
            log("is wining");
        } else if(inv.isBust()) {
            this.state = PlayerInGameState.BUST;
            log("is bust");
        }
    }

    public void skip() {
        if(this.state != PlayerInGameState.READY){
            if (!isDealer()) {
                this.state = PlayerInGameState.SKIP;
                currentBetStage = 0;
                log("Skip success");
            } else {
                this.state = PlayerInGameState.READY;
            }
        }
    }

    public void confirmBet() {
        if (this.state == PlayerInGameState.IDLE && game.getState() == GameState.BET && currentBetStage > 0) {
            this.setChips(getChips() - currentBetStage);
            this.state = PlayerInGameState.READY;
            log("Confirm bet success " + currentBetStage + "$");
        }
    }

    public void hit(){
        if((this.state == PlayerInGameState.READY || this.state == PlayerInGameState.HIT) && game.isInGame()){
            this.state = PlayerInGameState.HIT;
            this.pickUpCard();
            log("Hit success");
        }
    }

    public void stand(){
        if((this.state == PlayerInGameState.READY || this.state == PlayerInGameState.HIT) && game.isInGame()){
            this.state = PlayerInGameState.STAND;
            log("Stand success");
        }
    }

    public void doubleDown(){
        if (this.state == PlayerInGameState.READY && game.isInGame()) {
            this.state = PlayerInGameState.DOUBLE;
            this.pickUpCard();
            log("Double down success");
        }
    }

    public Result getResult(Player dealer) {
        var inv = getInventory();
        var dealerInv = dealer.getInventory();
        if (inv.isBust()) {
            return Result.BUST;
        } else if (dealerInv.isBust()) {
            return Result.DEALER_BUST;
        } else if (inv.isBlackJack()) {
            return Result.BLACKJACK;
        } else if (inv.is5Card()) {
            return Result.Card5;
        } else if (inv.getPoint() > dealerInv.getPoint()) {
            return Result.HIGH_POINT;
        } else if (inv.getPoint() == dealerInv.getPoint()) {
            return Result.DRAW;
        }
        return Result.LOSE;
    }

    public void stackCurrentBetStage(int amount) {
        if (getChips() - (currentBetStage + amount) >= 0) {
            this.currentBetStage += amount;
            log("stack bet " + amount + "$ total bet " + currentBetStage + "$");
        }
    }

    public int getCurrentBetStage() {
        return this.currentBetStage;
    }

    public PlayerInGameState getState() {
        return state;
    }

    public void log(String out) {
        System.out.println("Player " + getName() + " > " + out);
    }

    public void reset() {
        this.state = PlayerInGameState.IDLE;
        this.currentBetStage = 0;
        getInventory().clearCard();
    }

    public void handler(DataPacket packet) {
        switch (packet.pid()) {
            case ProtocolInfo.LOGIN_PACKET:
                LoginPacket loginPacket = (LoginPacket) packet;
                setName(loginPacket.getName());
                setId(loginPacket.getId());
                System.out.println("Player " + getName() + " has been join.");
                break;
            case ProtocolInfo.DISCONNECT_PACKET:
                DisconnectPacket disconnectPacket = (DisconnectPacket) packet;
                System.out.println("Player " + getName() + " has been disconnected for reason " + disconnectPacket.getMessage());
                break;
            default:
                System.out.println("Unknown packet");
        }
    }

    public Game getGame() {
        return game;
    }

    public boolean isDealer() {
        return isDealer;
    }

    public boolean isReady() {
        return state.equals(PlayerInGameState.READY);
    }
}


