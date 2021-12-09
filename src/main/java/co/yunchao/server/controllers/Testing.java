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
    String[] names = new String[]{"Tiger", "Ing", "Top", "Kaem"};
    private GameController gm;
    private PlayerController pc;
    private HashMap<String ,JButton> btn = new HashMap<String, JButton>(){{
        put("start", new JButton("Start"));
        put("hit", new JButton("Hit"));
        put("stand", new JButton("Stand"));
        put("dbd", new JButton("Double Down"));
        put("bet", new JButton("Bet"));
        put("next", new JButton("next"));
    }};
    private JFrame fr = new JFrame();
    Testing(){
        for(JButton b: btn.values()){
            b.addActionListener(this);
        }
        fr.setLayout(new GridLayout(3,3));
        fr.add(btn.get("start"));
        fr.add(btn.get("hit"));
        fr.add(btn.get("stand"));
        fr.add(btn.get("dbd"));
        fr.add(btn.get("bet"));
        fr.add(btn.get("next"));
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
            this.gm = new GameController(players);
            System.out.println(this.gm.getPlayer().getName());
        }
        else if(e.getSource().equals(btn.get("hit"))){
            this.gm.checkHit();
            this.gm.getPlayer().pickUpCard(this.gm.getDeck());
        }
        else if(e.getSource().equals(btn.get("dbd"))){
            this.gm.checkDoubleDown();
            this.gm.getPlayer().pickUpCard(this.gm.getDeck());
            this.gm.checkStand();
            System.out.println((this.gm.getPlayer().getName()));
        }
        else if(e.getSource().equals(btn.get("stand"))){
            this.gm.checkStand();
            this.gm.getPlayerController().setPlayerStand(true);
            this.gm.nextRound();
            System.out.println(this.gm.getPlayer().getName());
        }
    }

    public static void main(String[] args) {
        new Testing();
    }
}
