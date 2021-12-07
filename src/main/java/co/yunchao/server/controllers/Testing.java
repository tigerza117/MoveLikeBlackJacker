package co.yunchao.server.controllers;

import co.yunchao.base.models.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class Testing implements ActionListener {
    ArrayList<Player> players = new ArrayList<>();
    String[] names = new String[]{"Tiger", "Ing", "Top", "Kaem", "Meg"};
    private GameController gm;
    private PlayerController pc;
    private HashMap<String ,JButton> btn = new HashMap<String, JButton>(){{
        put("start", new JButton("Start"));
        put("hit", new JButton("Hit"));
        put("stand", new JButton("Stand"));
        put("dbd", new JButton("Double Down"));
        put("bet", new JButton("Bet"));
    }};
    private JFrame fr = new JFrame();
    Testing(){
        for(JButton b: btn.values()){
            b.addActionListener(this);
        }
        fr.setLayout(new GridLayout(3,2));
        fr.add(btn.get("start"));
        fr.add(btn.get("hit"));
        fr.add(btn.get("stand"));
        fr.add(btn.get("dbd"));
        fr.add(btn.get("bet"));
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setVisible(true);
        fr.pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(btn.get("start"))){
            for (String name : names) {
                players.add(new Player(name, 1000));
            }
            gm = new GameController(players);
        }
        else if(e.getSource().equals(btn.get("bet"))){
            pc.setPlayerBet(true);
        }
    }

    public static void main(String[] args) {
        new Testing();
    }
}
