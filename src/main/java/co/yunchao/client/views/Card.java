package co.yunchao.client.views;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.scene.GameScene;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;

public class Card {
    private final Group group;


    Card(String name) {
        double width = 84;
        double height = 122;
        var front = texture("card/Card_Deck-" + name + ".png", width, height);
        var back = texture("card/Card_Back.png", width, height);
        group = new Group(front, back);
        group.setOnMouseClicked(e -> toggleFlip());
    }

    public void spawn() {
        group.setVisible(true);
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
