package co.yunchao.client.controllers;

import co.yunchao.client.listener.MainMenuListener;
import co.yunchao.client.listener.ViewListener;
import co.yunchao.client.views.LeaveModal;
import co.yunchao.client.views.MainMenu;
import co.yunchao.client.views.OptionsModal;
import co.yunchao.client.views.PlayModal;
import com.almasb.fxgl.app.scene.FXGLMenu;
import javafx.scene.Group;

import static com.almasb.fxgl.dsl.FXGL.*;

public class MainMenuController implements MainMenuListener, ViewListener {
    private final MainMenu view;
    private final OptionsModal optionsModal = new OptionsModal();
    private final LeaveModal leaveModal = new LeaveModal();
    private final PlayModal playModal = new PlayModal();

    public MainMenuController() {
        view = new MainMenu();
        view.addListener(this);
        view.addViewListener(this);
        view.getGroup().getChildren().addAll(optionsModal.getGroup(), leaveModal.getGroup());
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
        optionsModal.render();
    }

    @Override
    public void clickLeave() {
        leaveModal.render();
    }

    @Override
    public void open(Group group) {

    }

    @Override
    public void close(Group group) {

    }
}
