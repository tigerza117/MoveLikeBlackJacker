package co.yunchao.base.models;

import co.yunchao.base.enums.CardSuit;

import java.io.*;
import java.util.UUID;

public class Card implements Serializable {
    private final UUID id;
    private final CardSuit suit;
    private final String name;
    private final int number;
    private final int point;
    private boolean flip = false;

    private final int HIGH_ACE_POINT = 11;
    private final int LOW_ACE_POINT = 1;

    public Card(int number, CardSuit suit){
        this.id = UUID.randomUUID();
        this.number = number;
        switch (number) {
            case 1:
                number = 101;
                break;
            case 11:
            case 12:
            case 13:
                number = 10;
                break;
        }

        String name = "Ace";

        String[] names = new String[]{"Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King"};

        if (number <= names.length) {
            name = names[number - 1];
        }

        this.name = name;
        this.point = number;
        this.suit = suit;
    }

    public int getPoint() {
        return point;
    }

    public String getName() {
        return name;
    }

    public CardSuit getSuit() {
        return suit;
    }

    public UUID getId() {
        return id;
    }

    public void setFlip(boolean flip) {
        this.flip = flip;
    }

    public boolean isFlip() {
        return flip;
    }

    public int getNumber() {
        return number;
    }
}
