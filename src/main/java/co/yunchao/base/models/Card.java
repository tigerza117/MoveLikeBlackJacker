package co.yunchao.base.models;

import java.io.*;

public class Card implements Serializable {
    private final String name;
    private final String suit;
    private final int point;

    private final int HIGH_ACE_POINT = 11;
    private final int LOW_ACE_POINT = 1;

    public Card(int number, String suit){
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
