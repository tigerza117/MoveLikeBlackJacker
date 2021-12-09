package co.yunchao.client.controllers;

import co.yunchao.client.views.Loading;
import com.almasb.fxgl.app.scene.LoadingScene;

public class LoadingController {
    private final LoadingScene view;

    public LoadingController() {
        view = new Loading();
    }

    public LoadingScene getView() {
        return view;
    }
}
