package co.yunchao.client.controllers;

import co.yunchao.client.views.GameMenu;
import com.almasb.fxgl.app.scene.FXGLMenu;

public class GameMenuController {
    private final FXGLMenu view;

    public GameMenuController() {
        view = new GameMenu();
    }

    public FXGLMenu getView() {
        return view;
    }
}
