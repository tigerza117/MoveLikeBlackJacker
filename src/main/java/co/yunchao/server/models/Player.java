package co.yunchao.server.models;

import co.yunchao.server.controllers.GameController;
import co.yunchao.server.controllers.PlayerController;
import co.yunchao.server.enums.PlayerInGameState;

public class Player extends co.yunchao.base.models.Player {

    private final GameController gameController;
    private final PlayerController playerController;
    private boolean isDealer;

    public Player(String name, GameController gameController) {
        super(name, 1000);
        this.gameController = gameController;
        playerController = new PlayerController(this, gameController);
    }

    public Player(String name, GameController gameController, boolean isDealer) {
        this(name, gameController);
        this.isDealer = isDealer;
    }

    public GameController getGameController() {
        return gameController;
    }

    public PlayerController getPlayerController() {
        return playerController;
    }

    public boolean isDealer() {
        return isDealer;
    }

    public boolean isReady() {
        return playerController.getState().equals(PlayerInGameState.READY);
    }

    public void reset() {
        getInventory().clearCard();
        getPlayerController().reset();
    }
}
