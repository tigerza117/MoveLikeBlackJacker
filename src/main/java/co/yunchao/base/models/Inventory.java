package co.yunchao.base.models;

import co.yunchao.net.packets.*;

import java.util.ArrayList;

public class Inventory {
    private final ArrayList<Card> cards;
    private final ArrayList<Chip> chips;
    private final Player player;

    public Inventory(Player player) {
        this.cards = new ArrayList<>();
        this.chips = new ArrayList<>();
        this.player = player;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public ArrayList<Chip> getChips() {
        return chips;
    }

    public void putCard(Card card) {
        if (player.getGame() != null) {
            CardSpawnPacket packet = new CardSpawnPacket();
            packet.playerId = player.getId();
            packet.id = card.getId();
            packet.number = card.getNumber();
            packet.suit = card.getSuit();
            packet.flip = card.isFlip();
            player.getGame().putPacket(packet);
        }
        this.cards.add(card);
    }

    public void putChip(Chip chip) {
        if (player.getGame() != null) {
            ChipSpawnPacket packet = new ChipSpawnPacket();
            packet.playerId = player.getId();
            packet.id = chip.getId();
            packet.type = chip.getType();
            player.getGame().putPacket(packet);
        }
        this.chips.add(chip);
    }

    public void clearCards() {
        if (player.getGame() != null) {
            cards.forEach(card -> {
                CardDeSpawnPacket packet = new CardDeSpawnPacket();
                packet.id = card.getId();
                player.getGame().putPacket(packet);
            });
        }
        this.cards.clear();
    }

    public void clearChips() {
        if (player.getGame() != null) {
            chips.forEach(chip -> {
                ChipDeSpawnPacket packet = new ChipDeSpawnPacket();
                packet.id = chip.getId();
                player.getGame().putPacket(packet);
            });
        }
        this.chips.clear();
    }

    public void toggleFlipCard(Card card) {
        card.setFlip(!card.isFlip());
        if (player.getGame() != null) {
            CardToggleFlipPacket packet = new CardToggleFlipPacket();
            packet.id = card.getId();
            player.getGame().putPacket(packet);
        }
    }

    public int getPoint() {
        ArrayList<Card> aceStack = new ArrayList<>();
        int points = 0;
        for (Card card : this.cards) {
            if (card.getPoint() == 101) {
                aceStack.add(card);
            } else {
                points += card.getPoint();
            }
        }
        for (Object obj : aceStack.toArray()) {
            Card card = (Card) obj;
            if (points + 11 + (aceStack.size() - 1) > 21) {
                points += aceStack.size();
                break;
            } else {
                points += 11;
            }
            aceStack.remove(card);
        }
        return points;
    }

    public boolean isBlackJack() {
        return getPoint() == 21 && getCards().size() == 2;
    }

    public boolean isBust() {
        return getPoint() > 21;
    }
}
