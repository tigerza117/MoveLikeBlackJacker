package co.yunchao.client.views;

import com.almasb.fxgl.animation.Interpolators;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;

public class CardEntity extends Group {
    public CardEntity(String name, boolean flip) {
        double width = 84;
        double height = 122;
        var front = texture("card/" + name.toLowerCase() + ".png", width, height);
        var back = texture("card/card_back.png", width, height);
        getChildren().addAll(front, back);
        if (flip) {
            toggleFlip();
        }
    }

    public void spawn() {
        play("Cards_Action.wav");
        var animation = animationBuilder(getGameScene())
                .interpolator(Interpolators.LINEAR.EASE_OUT())
                .duration(Duration.seconds(1))
                .translate(this)
                .from(new Point2D((getAppWidth() / 2.0 + getBoundsInLocal().getWidth() /2) - getLayoutX(), -(getLayoutY() + getBoundsInLocal().getHeight())))
                .to(new Point2D(getTranslateX(), getTranslateY()));
        setVisible(true);
        animation.buildAndPlay();
    }

    public void deSpawn() {
        setVisible(false);
    }

    public void toggleFlip() {
        Node n = getChildren().get(0);
        getChildren().remove(0);
        getChildren().add(n);
    }
}
