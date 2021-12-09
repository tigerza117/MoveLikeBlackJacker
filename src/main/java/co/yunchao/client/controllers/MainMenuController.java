package co.yunchao.client.controllers;

import co.yunchao.client.views.MainMenu;
import com.almasb.fxgl.app.scene.FXGLMenu;

public class MainMenuController {

    private final FXGLMenu view;

    public MainMenuController() {
        view = new MainMenu();
    }

    public FXGLMenu getView() {
        return view;
    }
}
