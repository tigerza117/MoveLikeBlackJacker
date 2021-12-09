package co.yunchao.client.controllers;

import co.yunchao.client.views.Startup;
import com.almasb.fxgl.app.scene.StartupScene;

import static com.almasb.fxgl.dsl.FXGL.*;

public class StartupController {
    private final StartupScene view;

    public StartupController() {
        view = new Startup(getAppWidth(), getAppHeight());
    }

    public StartupScene getView() {
        return view;
    }
}
