package co.yunchao.server.controllers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class Server implements Runnable {
    private LinkedList<Game> games;
    private Queue<Player> queue;
    private ArrayList<Player> players;

    public Server() {
        this.games = new LinkedList<>();
        this.queue = new LinkedList<>();
        this.players = new ArrayList<>();
    }

    private boolean paused = false;

    private synchronized void checkPaused(){
        try{
            while(paused){
                this.wait();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public synchronized void pauseThread(){
        this.paused = !this.paused;
        if(!this.paused) this.notify();
    }

    @Override
    public synchronized void run() {
        try {
            while (true) {
                checkPaused();
                var player = queue.poll();
                if (player != null) {

                }
                Thread.sleep(200);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void join(Player player) {
        players.add(player);
    }

    public void enqueue(Player player) {
        queue.add(player);
    }
}
