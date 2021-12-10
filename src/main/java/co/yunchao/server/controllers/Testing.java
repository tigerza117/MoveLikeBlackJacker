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
    String[] names = new String[]{"Romeo", "Juliet", "Cykablyat", "Anjing", "Dealer"};
    private GameController gm;
    private PlayerController pc;
    private int betStage;
    private HashMap<String ,JButton> btn = new HashMap<String, JButton>(){{
        put("start", new JButton("Start"));
        put("hit", new JButton("Hit"));
        put("stand", new JButton("Stand"));
        put("dbd", new JButton("Double Down"));
        put("bet", new JButton("Bet"));
        put("next", new JButton("next"));
    }};
    private HashMap<String, JButton> betbtn = new HashMap<String, JButton>(){{
        put("500", new JButton("500"));
        put("100", new JButton("100"));
        put("25", new JButton("25"));
    }};
    private JFrame fr = new JFrame();
    Testing(){
        for(JButton b: btn.values()){
            b.addActionListener(this);
        }
        for(JButton b: betbtn.values()){
            b.addActionListener(this);
        }
        fr.setLayout(new GridLayout(3,3));
        fr.add(btn.get("start"));
        fr.add(btn.get("hit"));
        fr.add(btn.get("stand"));
        fr.add(btn.get("dbd"));
        fr.add(btn.get("bet"));
        fr.add(btn.get("next"));
        fr.add(betbtn.get("500"));
        fr.add(betbtn.get("100"));
        fr.add(betbtn.get("25"));
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setVisible(true);
        fr.setLocationRelativeTo(null);
        fr.pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(btn.get("start"))){
            for (String name : names) {
                players.add(new Player(name, 5000));
            }
            this.gm = new GameController(players);
            System.out.println(this.gm.getPlayer().getName());
        }
        else if(e.getSource().equals(btn.get("hit"))){
            this.gm.checkHit();
            this.gm.getPlayer().pickUpCard(this.gm.getDeck());
        }
        else if(e.getSource().equals(betbtn.get("500"))){
            this.gm.getPlayerControls().get(this.gm.getPlayRound()).stackCurrentBetStage(500);
            System.out.println("Player will bet = " + this.gm.getPlayerControls().get(this.gm.getPlayRound()).getCurrentBetStage());
        }
        else if(e.getSource().equals(betbtn.get("100"))){
            this.gm.getPlayerControls().get(this.gm.getPlayRound()).stackCurrentBetStage(100);
            System.out.println("Player will bet = " + this.gm.getPlayerControls().get(this.gm.getPlayRound()).getCurrentBetStage());
        }
        else if(e.getSource().equals(betbtn.get("25"))){
            this.gm.getPlayerControls().get(this.gm.getPlayRound()).stackCurrentBetStage(25);
            betStage = this.gm.getPlayerControls().get(this.gm.getPlayRound()).getCurrentBetStage();;
            System.out.println("Player will bet = " + this.gm.getPlayerControls().get(this.gm.getPlayRound()).getCurrentBetStage());
        }
        else if(e.getSource().equals(btn.get("bet"))){
            this.gm.getPlayerControls().get(this.gm.getPlayRound()).bet();
            this.gm.getPlayerControls().get(this.gm.getPlayRound()).setCurrentBetStage(0);
            System.out.println(this.gm.getPlayerControls().get(this.gm.getPlayRound()).getPlayer().getChips());
            System.out.println("BETTTTTT");
            System.out.println("Chip : " + this.gm.getPlayerControls().get(this.gm.getPlayRound()).getPlayer().getChips());
        }
        else if(e.getSource().equals(btn.get("dbd"))){
            this.gm.getPlayer().pickUpCard(this.gm.getDeck());
            if(!this.gm.CheckLast()) {
                System.out.println("PlayRound" + this.gm.getPlayRound());
                this.gm.checkDoubleDown();
                this.gm.nextRound();
                System.out.println((this.gm.getPlayer().getName()));
            }
            else{
                this.gm.LastStand();
            }
        }
        else if(e.getSource().equals(btn.get("stand"))){
            if(!this.gm.CheckLast()) {
                this.gm.checkStand();
                this.gm.nextRound();
                System.out.println("Player before you have stand. Now it's your turn " + this.gm.getPlayer().getName());
            }
            else{
                this.gm.LastStand();
            }
        }
    }

    public static void main(String[] args) {
        new Testing();
    }
}
