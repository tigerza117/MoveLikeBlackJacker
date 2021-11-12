package co.yunchao.models;

import java.io.*;

public class Card implements Serializable {
    private final String name;
    private final String suit;
    private final int point;

    private final int HIGH_ACE_POINT = 11;
    private final int LOW_ACE_POINT = 1;

    public Card(int number, String suit){
        this.point = switch(number) {
            case 1 -> 101;
            case 11,12,13 -> 10;
            default -> number;
        };
        this.name = switch (number) {
            case 1 -> "Ace";
            case 2 -> "Two";
            case 3 -> "Three";
            case 4 -> "Four";
            case 5 -> "Five";
            case 6 -> "Six";
            case 7 -> "Seven";
            case 8 -> "Eight";
            case 9 -> "Nine";
            case 10 -> "Ten";
            case 11 -> "Jack";
            case 12 -> "Queen";
            case 13 -> "King";
            default -> "Unknown";
        };
        this.suit = suit;
    }

    //setter-getter section

    public int getPoint(){
        return point;
    }

    public String getName() {
        return name;
    }

    public String getSuit() {
        return suit;
    }
}
