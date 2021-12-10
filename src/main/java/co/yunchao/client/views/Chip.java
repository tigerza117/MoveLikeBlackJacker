package co.yunchao.client.views;

import javafx.scene.Group;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGL.play;

public class Chip {
    private final Group group;

    public Chip(String name) {
        double width = 42;
        double height = 42;
        var image = texture(name + "_call_bet.png", width, height);
        group = new Group(image);
        group.setVisible(false);
    }

    public void spawn() {
        group.setVisible(true);
        play("Cards_Action.wav");
    }

    public void deSpawn() {
        group.setVisible(false);
    }

    public Group getGroup() {
        return group;
    }
}
