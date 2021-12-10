package co.yunchao.client.views;

import com.almasb.fxgl.animation.Interpolators;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;

public class Card {
    private final Group group;

    public Card(String name) {
        double width = 84;
        double height = 122;
        var front = texture("card/Card_Deck-" + name + ".png", width, height);
        var back = texture("card/Card_Back.png", width, height);
        group = new Group(front, back);
        group.setVisible(false);
        getGameScene().getContentRoot().getChildren().add(group);
        toggleFlip();
    }

    public void spawn() {
        var animation = animationBuilder(getGameScene())
                .interpolator(Interpolators.LINEAR.EASE_OUT())
                .duration(Duration.seconds(1))
                .translate(group)
                .from(new Point2D((getAppWidth() / 2.0 + group.getBoundsInLocal().getWidth() /2) - group.getLayoutX(), -(group.getLayoutY() + group.getBoundsInLocal().getHeight())))
                .to(new Point2D(group.getTranslateX(), group.getTranslateY()));

        group.setVisible(true);
        animation.buildAndPlay();
        play("Cards_Action.wav");
    }

    public void toggleFlip() {
        Node n = group.getChildren().get(0);
        group.getChildren().remove(0);
        group.getChildren().add(n);
    }

    public void deSpawn() {
        group.setVisible(false);
    }

    public Group getGroup() {
        return group;
    }
}
