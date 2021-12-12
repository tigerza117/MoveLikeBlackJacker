package co.yunchao.server.controllers;

import co.yunchao.net.packets.*;
import co.yunchao.server.models.Player;
import co.yunchao.base.enums.GameState;
import co.yunchao.base.enums.PlayerInGameState;
import co.yunchao.base.enums.Result;

import java.util.ArrayList;

public class PlayerController {
    private final GameController gameController;
    private ArrayList<Integer> betRange = new ArrayList<Integer>();
    private final Player player;
    private PlayerInGameState state;
    private int currentBetStage = 0;

    public PlayerController(Player player, GameController gameController){
        this.player = player;
        this.gameController = gameController;
        this.state = PlayerInGameState.IDLE;
        this.betRange.add(1000);
        this.betRange.add(250);
        this.betRange.add(100);
    }

    public void getReward(double ratio) {
        var reward = this.getCurrentBetStage() * ratio;
        this.player.setChips(this.player.getChips() + (reward));
        log("get reward " + reward + "$");
    }

    public void pickUpCard() {
        var inv = getPlayer().getInventory();
        inv.addCard(gameController.getDeck().pickTopCard());
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
            if (!getPlayer().isDealer()) {
                this.state = PlayerInGameState.SKIP;
                currentBetStage = 0;
                log("Skip success");
            } else {
                this.state = PlayerInGameState.READY;
            }
        }
    }

    public void confirmBet() {
        if (this.state == PlayerInGameState.IDLE && gameController.getState() == GameState.BET && currentBetStage > 0) {
            this.player.setChips(this.player.getChips() - currentBetStage);
            this.state = PlayerInGameState.READY;
            log("Confirm bet success " + currentBetStage + "$");
        }
    }

    public void hit(){
        if((this.state == PlayerInGameState.READY || this.state == PlayerInGameState.HIT) && gameController.isInGame()){
            this.state = PlayerInGameState.HIT;
            this.pickUpCard();
            log("Hit success");
        }
    }

    public void stand(){
        if((this.state == PlayerInGameState.READY || this.state == PlayerInGameState.HIT) && gameController.isInGame()){
            this.state = PlayerInGameState.STAND;
            log("Stand success");
        }
    }

    public void doubleDown(){
        if (this.state == PlayerInGameState.READY && gameController.isInGame()) {
            this.state = PlayerInGameState.DOUBLE;
            this.pickUpCard();
            log("Double down success");
        }
    }

    public Result getResult(Player dealer) {
        var inv = player.getInventory();
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
        if (player.getChips() - (currentBetStage + amount) >= 0) {
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

    public Player getPlayer() {
        return this.player;
    }

    public void log(String out) {
        System.out.println("Player " + getPlayer().getName() + " > " + out);
    }

    public void reset() {
        this.state = PlayerInGameState.IDLE;
        this.currentBetStage = 0;
    }

    public void handler(DataPacket packet) {
        System.out.println("Player packet handler receive!");
        switch (packet.pid()) {
            case ProtocolInfo.LOGIN_PACKET:
                LoginPacket loginPacket = (LoginPacket) packet;
                this.player.setName(loginPacket.getName());
                this.player.setId(loginPacket.getId());
                System.out.println("Player " + player.getName() + " has been join.");
                break;
            case ProtocolInfo.DISCONNECT_PACKET:
                DisconnectPacket disconnectPacket = (DisconnectPacket) packet;
                System.out.println("Player " + player.getName() + " has been disconnected for reason " + disconnectPacket.getMessage());
                break;
            default:
                System.out.println("Unknown packet");
        }
    }
}


