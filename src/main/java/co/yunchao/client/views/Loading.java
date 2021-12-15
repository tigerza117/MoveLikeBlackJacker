package co.yunchao.client.views;

import com.almasb.fxgl.app.scene.LoadingScene;

import static com.almasb.fxgl.dsl.FXGL.*;

public class Loading extends LoadingScene {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public Loading() {
        var bg = texture("main/loading_background.png", getAppWidth(), getAppHeight());

        getContentRoot().getChildren().addAll(bg);
    }
}
