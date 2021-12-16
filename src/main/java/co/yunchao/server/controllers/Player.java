package co.yunchao.server.controllers;

import co.yunchao.base.enums.ChipType;
import co.yunchao.base.enums.Result;
import co.yunchao.base.enums.ScoreColorType;
import co.yunchao.base.models.Chip;
import co.yunchao.base.models.Inventory;
import co.yunchao.net.packets.*;
import co.yunchao.base.enums.PlayerInGameState;
import io.netty.channel.Channel;

import java.util.UUID;

public class Player extends co.yunchao.base.models.Player {
    private Server server;
    private Channel channel;
    private final Inventory inventory;
    private String currentScore;
    private ScoreColorType currentColorType;

    public Player(UUID uuid, String name, Channel channel, Server server) {
        this(uuid, name, false);
        this.channel = channel;
        this.server = server;
    }

    public Player(UUID uuid, String name, boolean isDealer) {
        super(uuid, name, isDealer);
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
    public void setBalance(double chip) {
        super.setBalance(chip);
        updateMetadata();
    }

    public Game getGame() {
        return (Game) super.getGame();
    }

    @Override
    public void handler(DataPacket packet) {
        switch (packet.pid()) {
            case ProtocolInfo.JOIN_ROOM_PACKET:
                JoinRoomPacket joinRoomPacket = (JoinRoomPacket) packet;
                getServer().join(this, joinRoomPacket.roomId);
                break;
            case ProtocolInfo.PLAYER_ACTION_PACKET:
                PlayerActionPacket actionPacket = (PlayerActionPacket) packet;
                if (getGame() != null) {
                    switch (actionPacket.action) {
                        case CLEAR_BET:
                            clearBet();
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
                    stackCurrentBetStage(playerBetStackPacket.type, playerBetStackPacket.amount);
                }
                break;
            default:
                System.out.println("Unknown packet");
        }
    }

    @Override
    public void putPacket(DataPacket packet) {
        if (channel != null) {
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
        var reward = getCurrentBetStage() * ratio;
        log("get reward " + reward + "$ from " + getBalance() + " to " + getBalance() + reward);
        setBalance(getBalance() + reward);
    }

    @Override
    public void skip() {
        if(!isReady()){
            if (!isDealer()) {
                if (getCurrentBetStage() > 0) {
                    confirmBet();
                    setState(PlayerInGameState.READY);
                } else {
                    setState(PlayerInGameState.SKIP);
                }
                log("Skip success");
            } else {
                setState(PlayerInGameState.READY);
            }
        }
    }

    @Override
    public void confirmBet() {
        if (canConfirmBet()) {
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
            setState(PlayerInGameState.DOUBLE);
            pickUpCard();
            setBalance(getBalance() - getCurrentBetStage());
            setCurrentBetStage(getCurrentBetStage() * 2);
            getInventory().getChips().forEach((chip) -> {
                getInventory().putChip(new Chip(chip.getType()));
            });
            log("Double down success");
        }
    }

    @Override
    public void clearBet() {
        System.out.println("Call!");
        if (canConfirmBet()) {
            getInventory().clearChips();
            setBalance(getBalance() + getCurrentBetStage());
            setCurrentBetStage(0);
        }
    }

    @Override
    public void stackCurrentBetStage(ChipType chipType, int amount) {
        int total = chipType.getAmount() * amount;
        if (canStackCurrentBetStage(total)) {
            for (int i = 0; i < amount; i++) {
                getInventory().putChip(new Chip(chipType));
            }
            setBalance(getBalance() - total);
            setCurrentBetStage(getCurrentBetStage() + total);
            log("stack bet " + total + "$ total bet " + getCurrentBetStage() + "$");
        }
    }

    public void updateMetadata() {
        Game game = getGame();
        if (game != null) {
            PlayerMetadataPacket packet = new PlayerMetadataPacket();
            packet.id = getId();
            packet.name = getName();
            packet.chips = getBalance();
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
        pickUpCard(true);
    }

    public void pickUpCard(boolean flip) {
        var inv = getInventory();
        var card = getGame().getDeck().pickTopCard();
        card.setFlip(flip);
        inv.putCard(card);
        if (inv.isBlackJack()) {
            setState(PlayerInGameState.WINING);
            setScore("BlackJack");
        } else if(inv.isBust()) {
            setState(PlayerInGameState.BUST);
            setScore("Bust", ScoreColorType.RED);
        } else {
            if (isDealer() && inv.getCards().size() == 2) {
                setScore(inv.getCards().get(0).getPoint() + " + ?");
            } else {
                setScore(inv.getPoint() + "");
            }
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
        setScore("");
    }

    public void sendData(Player player) {
        {
            SetScorePacket packet = new SetScorePacket();
            packet.playerId = getId();
            packet.text = currentScore;
            packet.colorType = currentColorType;
            player.putPacket(packet);
        }
        getInventory().getCards().forEach(card -> {
            CardSpawnPacket packet = new CardSpawnPacket();
            packet.playerId = getId();
            packet.id = card.getId();
            packet.number = card.getNumber();
            packet.suit = card.getSuit();
            packet.flip = card.isFlip();
            player.putPacket(packet);
        });
        getInventory().getChips().forEach(chip -> {
            ChipSpawnPacket packet = new ChipSpawnPacket();
            packet.playerId = player.getId();
            packet.id = chip.getId();
            packet.type = chip.getType();
            player.putPacket(packet);
        });
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

    public void playSound(String name) {
        PlaySoundPacket packet = new PlaySoundPacket();
        packet.name = name;
        putPacket(packet);
    }

    public void stopSound() {
        StopSoundPacket packet = new StopSoundPacket();
        putPacket(packet);
    }

    public Server getServer() {
        return server;
    }

    public void setScore(String text) {
        setScore(text, ScoreColorType.GOLD);
    }

    public void setScore(String text, ScoreColorType colorType) {
        Game game = getGame();
        currentScore = text;
        currentColorType = colorType;
        if (game != null) {
            SetScorePacket packet = new SetScorePacket();
            packet.playerId = getId();
            packet.text = text;
            packet.colorType = colorType;
            game.putPacket(packet);
        }
    }
}


