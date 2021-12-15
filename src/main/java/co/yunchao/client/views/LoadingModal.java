package co.yunchao.client.views;

import com.almasb.fxgl.scene.SubScene;

import static com.almasb.fxgl.dsl.FXGL.*;

public class LoadingModal extends SubScene {
    public LoadingModal() {
        var bg = texture("main/loading_background.png", getAppWidth(), getAppHeight());

        getRoot().getChildren().addAll(bg);
    }
}

