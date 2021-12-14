package co.yunchao.client.views;

import javafx.scene.Group;
import javafx.scene.Node;

import static com.almasb.fxgl.dsl.FXGL.*;

public class CardEntity extends Group {
    public CardEntity(String name) {
        double width = 84;
        double height = 122;
        var front = texture("card/Card_Deck-" + name + ".png", width, height);
        var back = texture("card/Card_Back.png", width, height);
        getChildren().addAll(front, back);
        toggleFlip();
    }

    public void spawn() {
        play("Cards_Action.wav");
        getGameScene().getContentRoot().getChildren().add(this);
    }

    public void deSpawn() {
        getGameScene().getContentRoot().getChildren().remove(this);
    }

    public void toggleFlip() {
        Node n = getChildren().get(0);
        getChildren().remove(0);
        getChildren().add(n);
    }
}
