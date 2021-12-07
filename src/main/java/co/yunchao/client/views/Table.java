package co.yunchao.client.views;

import com.almasb.fxgl.app.scene.GameScene;

import static com.almasb.fxgl.dsl.FXGL.texture;

public class Table {
    public Table() {

    }

    public void render(GameScene scene) {
        var bg = texture("game_background.png", scene.getAppWidth(), scene.getAppHeight());

        scene.getRoot().getChildren().addAll(bg);
    }
}
