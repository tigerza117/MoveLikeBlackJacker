package co.yunchao.client.controllers;

import co.yunchao.client.listener.MainMenuListener;
import co.yunchao.client.views.LeaveModal;
import co.yunchao.client.views.MainMenu;
import co.yunchao.client.views.OptionsModal;
import co.yunchao.client.views.PlayModal;
import com.almasb.fxgl.app.scene.FXGLMenu;

import java.util.UUID;

import static com.almasb.fxgl.dsl.FXGL.getGameController;
import static com.almasb.fxgl.dsl.FXGL.getSceneService;

public class MainMenuController implements MainMenuListener {
    private final MainMenu view;
    private final OptionsModal optionsModal = new OptionsModal();
    private final LeaveModal leaveModal = new LeaveModal(() -> getGameController().exit());
    private final PlayModal playModal = new PlayModal();
    private final MainController mainController;

    public MainMenuController(MainController mainController) {
        this.mainController = mainController;
        view = new MainMenu();
        view.addListener(this);
        playModal.setOnConfirm(((roomId, name) -> {
            PlayerController player = new PlayerController(UUID.randomUUID(), name, false);
            GameController gameController = new GameController(roomId, player);
            mainController.setGameController(gameController);
            getGameController().startNewGame();
        }));
    }

    public FXGLMenu getView() {
        return view;
    }

    @Override
    public void clickPlay() {
        getSceneService().pushSubScene(playModal);
    }

    @Override
    public void clickOption() {
        getSceneService().pushSubScene(optionsModal);
    }

    @Override
    public void clickLeave() {
        getSceneService().pushSubScene(leaveModal);
    }
}
