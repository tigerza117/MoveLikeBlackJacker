package co.yunchao.client.views;

import javafx.scene.Group;

import static com.almasb.fxgl.dsl.FXGL.*;

public class ChipEntity extends Group {
    public ChipEntity(String name) {
        double width = 42;
        double height = 42;
        var image = texture("bet_section/" + name + "_call_bet.png", width, height);
        getChildren().add(image);
    }

    public void spawn() {
        setVisible(true);
        play("Cards_Action.wav");
    }

    public void deSpawn() {
        setVisible(false);
    }
}
