package co.yunchao.client.views;

import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.play;
import static com.almasb.fxgl.dsl.FXGL.texture;

public class Button {
    protected static Node create(String file, Runnable action) {
        return create(file, "Clicked", action);
    }

    protected static Node create(String file, String music, Runnable action) {
        var bg = texture(file + ".png");

        var btn = new StackPane(bg);

        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setOnMouseClicked(event -> {
            play(music + ".wav");
            action.run();
        });

        ScaleTransition linearTransition = new ScaleTransition(new Duration(50), btn);
        linearTransition.setFromX(btn.getScaleX());
        linearTransition.setToX(1.1 * btn.getScaleX());
        linearTransition.setFromY(btn.getScaleY());
        linearTransition.setToY(1.1 * btn.getScaleY());
        linearTransition.setFromZ(btn.getScaleZ());
        linearTransition.setToZ(1.1 * btn.getScaleZ());
        linearTransition.setCycleCount(1);
        linearTransition.setAutoReverse(true);

        btn.setOnMouseEntered(e -> {
            linearTransition.playFromStart();
            btn.setCursor(CursorCall.getCursorClick());
        });

        btn.setOnMouseExited(e -> {
            linearTransition.setRate(-1);
            linearTransition.play();
        });

        btn.setCache(true);
        btn.setCacheHint(CacheHint.SPEED);

        return btn;
    }
}
