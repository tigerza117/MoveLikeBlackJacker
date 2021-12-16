package co.yunchao.client.views;

import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

import static com.almasb.fxgl.dsl.FXGL.play;
import static com.almasb.fxgl.dsl.FXGL.texture;

public class OptionsBtn {
    protected static Node create(String file, Runnable action) {
        return create(file, "Clicked", action);
    }

    protected static Node create(String file, String music, Runnable action) {
        var bg = texture(file+".png");

        var btn = new StackPane(bg);

        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setOnMouseClicked(event -> {
            play(music + ".wav");
            action.run();
        });

        btn.setOnMouseEntered( e -> btn.setCursor(CursorCall.getCursorClick()));

        btn.setCache(true);
        btn.setCacheHint(CacheHint.SPEED);

        return btn;
    }
}
