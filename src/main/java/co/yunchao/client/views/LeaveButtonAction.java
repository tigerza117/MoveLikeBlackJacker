package co.yunchao.client.views;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.GaussianBlur;

import java.util.HashMap;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class LeaveButtonAction {

    private final HashMap<String, Node> buttons;
    private final Group group;

    public LeaveButtonAction(){
        group = new Group();

        var quitPane = texture("/leaveGame/leavePane.png", getAppWidth(), 290);
        quitPane.setLayoutY((getAppHeight() / 2.0)-(quitPane.getHeight() / 2));

        var yesBtn = texture("/leaveGame/yesBtn.png");
        yesBtn.setOnMouseClicked(yes -> {
            System.exit(0);
            play("Clicked.wav");
        });

        var noBtn = texture("/leaveGame/noBtn.png");
        noBtn.setOnMouseClicked(no -> quitPane.dispose());
        noBtn.setLayoutX(200);

        buttons = new HashMap<>() {{
            put("yes", yesBtn);
            put("no", noBtn);
        }};

        Group leaveMenu = new Group(yesBtn, noBtn);

        getGameScene().getContentRoot().getChildren().forEach(node -> node.setEffect(new GaussianBlur()));
        leaveMenu.setLayoutY((getAppHeight() / 2.0)+(leaveMenu.getBoundsInLocal().getHeight() / 3.5));
        leaveMenu.setLayoutX((getAppWidth() / 2.0)-(leaveMenu.getBoundsInLocal().getWidth() / 2));
        
        group.getChildren().addAll(quitPane, leaveMenu);
        group.setVisible(false);
    }

    public HashMap<String, Node> getButtons() {
        return buttons;
    }

    public void render() {
        group.setVisible(true);
    }

    public Group getGroup() {
        return group;
    }
}
