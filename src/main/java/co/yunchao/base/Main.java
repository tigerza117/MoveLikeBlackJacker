package co.yunchao.base;

import co.yunchao.base.models.Deck;
import co.yunchao.base.models.Player;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Deck d = new Deck();
        d.generateCards();
        ArrayList<Player> players = new ArrayList<>();
        for (String name : new String[]{"Tiger", "Ing", "Top", "Kaem", "Meg"}) {
            players.add(new Player(name, 1000));
        }

        System.out.println("===============================================================================");
        for (Player player: players) {
            System.out.println("[Player]"+player.getName()+" have "+player.getInventory().getPoint()+" point");
        }
    }
}