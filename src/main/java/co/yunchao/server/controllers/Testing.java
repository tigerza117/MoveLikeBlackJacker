package co.yunchao.server.controllers;

import co.yunchao.server.models.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class Testing {
    String[] names = new String[]{"Romeo", "Juliet", "Cykablyat", "Anjing"};
    private GameController gm;

    private HashMap<String ,JButton> btn = new HashMap<String, JButton>(){{
        put("hit", new JButton("Hit"));
        put("stand", new JButton("Stand"));
        put("dbd", new JButton("Double Down"));
        put("bet", new JButton("Bet"));
        put("skip", new JButton("Skip"));
    }};
    private HashMap<String, JButton> betbtn = new HashMap<String, JButton>(){{
        put("500", new JButton("500"));
        put("100", new JButton("100"));
        put("25", new JButton("25"));
    }};

    Testing(){

        var btn = new JButton("Start");
        btn.addActionListener(e -> {
            this.gm = new GameController();
            for (String name : names) {
                var player = new Player(name, this.gm);
                gm.playerJoin(player);
                var f = new JFrame();
                f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                f.setLayout(new FlowLayout());

                var hitBtn = new JButton("Hit");
                hitBtn.addActionListener(e1 -> {
                    player.getPlayerController().hit();
                });
                var standBtn = new JButton("Stand");
                standBtn.addActionListener(e1 -> {
                    player.getPlayerController().stand();
                });
                var dbdBtn = new JButton("Double Down");
                dbdBtn.addActionListener(e1 -> {
                    player.getPlayerController().doubleDown();
                });
                var betBtn = new JButton("Bet");
                betBtn.addActionListener(e1 -> {
                    player.getPlayerController().confirmBet();
                });
                var skipBtn = new JButton("Skip");
                skipBtn.addActionListener(e1 -> {
                    player.getPlayerController().skip();
                });

                var bet1 = new JButton("500");
                var bet2 = new JButton("100");
                var bet3 = new JButton("50");

                bet1.addActionListener(e1 -> {
                    player.getPlayerController().stackCurrentBetStage(500);
                });
                bet2.addActionListener(e1 -> {
                    player.getPlayerController().stackCurrentBetStage(100);
                });
                bet3.addActionListener(e1 -> {
                    player.getPlayerController().stackCurrentBetStage(50);
                });

                f.add(new JLabel(player.getName()));
                f.add(bet1);
                f.add(bet2);
                f.add(bet3);
                f.add(hitBtn);
                f.add(standBtn);
                f.add(dbdBtn);
                f.add(betBtn);
                f.add(skipBtn);

                f.setVisible(true);
                f.setLocationRelativeTo(null);
                f.pack();
            }
        });
        var fr = new JFrame();
        fr.add(btn);
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setVisible(true);
        fr.setLocationRelativeTo(null);
        fr.pack();
    }

    public static void main(String[] args) {
        new Testing();
    }
}
