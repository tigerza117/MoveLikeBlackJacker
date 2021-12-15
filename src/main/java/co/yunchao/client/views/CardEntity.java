package co.yunchao.client.views;

import javafx.scene.Group;
import javafx.scene.Node;

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
        setVisible(true);
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
