package co.yunchao.client.views;

import javafx.scene.Group;
import javafx.scene.Node;

import java.util.HashMap;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class LeaveModal {
    private final Group group;

    public LeaveModal(){
        group = new Group();

        var quitPane = texture("/leaveGame/leavePane.png", getAppWidth(), 290);
        quitPane.setLayoutY((getAppHeight() / 2.0)-(quitPane.getHeight() / 2));

        var yesBtn = Button.create("/leaveGame/yesBtn", () -> getGameController().exit());
        var noBtn = Button.create("/leaveGame/noBtn", this::close);
        noBtn.setLayoutX(200);

        Group leaveMenu = new Group(yesBtn, noBtn);

        leaveMenu.setLayoutY((getAppHeight() / 2.0)+(leaveMenu.getBoundsInLocal().getHeight() / 3.5));
        leaveMenu.setLayoutX((getAppWidth() / 2.0)-(leaveMenu.getBoundsInLocal().getWidth() / 2));
        
        group.getChildren().addAll(quitPane, leaveMenu);
        group.setVisible(false);
    }

    public void render() {
        group.setVisible(true);
    }

    public void close() {
        group.setVisible(false);
    }

    public Group getGroup() {
        return group;
    }
}
