package co.yunchao.client.views;

import com.almasb.fxgl.scene.SubScene;

import static com.almasb.fxgl.dsl.FXGL.*;

public class LoadingModal extends SubScene {

    public LoadingModal() {
        System.out.println("boi loadmodal");
        var bg = texture("main/loading_background.png", getAppWidth(), getAppHeight());

        getRoot().getChildren().addAll(bg);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}

