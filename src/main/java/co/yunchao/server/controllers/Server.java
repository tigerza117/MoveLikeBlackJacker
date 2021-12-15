package co.yunchao.server.controllers;

import java.util.*;

public class Server implements Runnable {
    private final HashMap<String ,Game> games;
    private final Queue<Player> queue;
    private final ArrayList<Player> players;

    public Server() {
        this.games = new HashMap<>();
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
                    Game game = newGame();
                    game.join(player);
                }
                Thread.sleep(200);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Game newGame() {
        Random rand = new Random();
        while (true) {
            rand.setSeed(System.currentTimeMillis());
            int idNumber = rand.nextInt(10000);
            String id = String.format("%04d", idNumber);
            if (!games.containsKey(id)) {
                Game game = new Game(id);
                games.put(id, game);
                return game;
            }
        }
    }

    public void join(Player player, String roomId) {
        System.out.println("Join > " + player.getId() + " <> " + roomId);
        if (roomId.equals("000A")) {
            players.add(player);
            enqueue(player);
        } else {
            if (!games.containsKey(roomId)) {
                player.kick("Room not found.");
            } else {
                if (!games.get(roomId).join(player)) {
                    player.kick("Room full please try again.");
                }
            }
        }
    }

    public void leave(Player player) {
        players.remove(player);
        queue.remove(player);
    }

    private void enqueue(Player player) {
        queue.add(player);
    }
}
