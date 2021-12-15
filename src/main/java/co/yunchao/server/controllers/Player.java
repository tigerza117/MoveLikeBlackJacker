package co.yunchao.server.controllers;

import co.yunchao.base.enums.ChipType;
import co.yunchao.base.enums.Result;
import co.yunchao.base.models.Chip;
import co.yunchao.base.models.Inventory;
import co.yunchao.net.packets.*;
import co.yunchao.base.enums.PlayerInGameState;
import io.netty.channel.Channel;

public class Player extends co.yunchao.base.models.Player {
    private Server server;
    private Channel channel;
    private final Inventory inventory;

    public Player(String name, Channel channel, Server server) {
        this(name, false);
        this.channel = channel;
        this.server = server;
    }

    public Player(String name, boolean isDealer) {
        super(name, isDealer);
        this.inventory = new Inventory(this);
    }

    @Override
    public void setState(PlayerInGameState state) {
        super.setState(state);
        updateMetadata();
    }

    @Override
    public void setCurrentBetStage(int currentBetStage) {
        super.setCurrentBetStage(currentBetStage);
        updateMetadata();
    }

    @Override
    public void setChips(double chip) {
        super.setChips(chip);
        updateMetadata();
    }

    @Override
    public void handler(DataPacket packet) {
        System.out.println("Handle packet > " + packet.getClass());
        switch (packet.pid()) {
            case ProtocolInfo.JOIN_ROOM_PACKET:
                JoinRoomPacket joinRoomPacket = (JoinRoomPacket) packet;
                System.out.println("can join ?");
                getServer().join(this, joinRoomPacket.roomId);
                break;
            case ProtocolInfo.PLAYER_ACTION_PACKET:
                PlayerActionPacket actionPacket = (PlayerActionPacket) packet;
                if (getGame() != null) {
                    switch (actionPacket.action) {
                        case CONFIRM_BET:
                            confirmBet();
                        case STAND:
                            stand();
                            break;
                        case HIT:
                            hit();
                            break;
                        case DOUBLE:
                            doubleDown();
                            break;
                        default:
                            System.out.println("Unknown action");
                    }
                }
                break;
            case ProtocolInfo.PLAYER_BET_STACK_PACKET:
                PlayerBetStackPacket playerBetStackPacket = (PlayerBetStackPacket) packet;
                if (getGame() != null) {
                    stackCurrentBetStage(playerBetStackPacket.type);
                }
                break;
            default:
                System.out.println("Unknown packet");
        }
    }

    @Override
    public void putPacket(DataPacket packet) {
        if (channel != null) {
            System.out.println("Send channel > " + packet.getClass());
            channel.writeAndFlush(packet);
        }
    }

    @Override
    public void close() {
        getServer().leave(this);
        reset();
        if (getGame() != null) {
            getGame().leave(this);
        }
        System.out.println("Player " + getName() + " has been disconnected");
    }

    public void getReward(double ratio) {
        var reward = this.getCurrentBetStage() * ratio;
        this.setChips(this.getChips() + (reward));
        log("get reward " + reward + "$");
    }

    @Override
    public void skip() {
        if(!isReady()){
            if (!isDealer()) {
                setState(PlayerInGameState.SKIP);
                setCurrentBetStage(0);
                log("Skip success");
            } else {
                setState(PlayerInGameState.READY);
            }
        }
    }

    @Override
    public void confirmBet() {
        if (canConfirmBet()) {
            this.setChips(getChips() - getCurrentBetStage());
            setState(PlayerInGameState.READY);
            log("Confirm bet success " + getCurrentBetStage() + "$");
        }
    }

    @Override
    public void hit() {
        if (canHit()){
            setState(PlayerInGameState.HIT);
            this.pickUpCard();
            log("Hit success");
        }
    }

    @Override
    public void stand() {
        if (canStand()) {
            setState(PlayerInGameState.STAND);
            log("Stand success");
        }
    }

    public void doubleDown() {
        if (canDoubleDown()) {
            this.setState(PlayerInGameState.DOUBLE);
            this.pickUpCard();
            log("Double down success");
        }
    }

    @Override
    public void stackCurrentBetStage(ChipType chipType) {
        int amount = chipType.getAmount();
        if (canStackCurrentBetStage(amount)) {
            getInventory().putChip(new Chip(chipType));
            setCurrentBetStage(getCurrentBetStage() + amount);
            log("stack bet " + amount + "$ total bet " + getCurrentBetStage() + "$");
        }
    }

    public void updateMetadata() {
        Game game = getGame();
        if (game != null) {
            PlayerMetadataPacket packet = new PlayerMetadataPacket();
            packet.id = getId();
            packet.name = getName();
            packet.chips = getChips();
            packet.state = getState();
            packet.isDealer = isDealer();
            packet.currentBetStage = getCurrentBetStage();
            game.putPacket(packet);
        }
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void pickUpCard() {
        var inv = getInventory();
        inv.putCard(getGame().getDeck().pickTopCard());
        if (inv.isBlackJack()) {
            setState(PlayerInGameState.WINING);
            log("is wining");
        } else if(inv.isBust()) {
            setState(PlayerInGameState.BUST);
            log("is bust");
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

    public void log(String out) {
        System.out.println("Player " + getName() + " > " + out);
    }

    public void reset() {
        setState(PlayerInGameState.IDLE);
        setCurrentBetStage(0);
        getInventory().clearCards();
        getInventory().clearChips();
    }

    public void kick(String message) {
        kick(message, false);
    }

    public void kick(String message, boolean showMsg) {
        var pk = new DisconnectPacket();
        pk.message = message;
        pk.showDialog = showMsg;
        putPacket(pk);
    }

    public Server getServer() {
        return server;
    }
}


