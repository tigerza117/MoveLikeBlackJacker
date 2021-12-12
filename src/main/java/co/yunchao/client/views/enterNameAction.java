package co.yunchao.client.views;

import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.Group;
import javafx.scene.Node;

import java.util.HashMap;

import static com.almasb.fxgl.dsl.FXGL.texture;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class enterNameAction {

    private static Group group;
    private final HashMap<String, Node> buttons;

    public enterNameAction(){
        group = new Group();

        var namePane = texture("/enterName/namePanel.png", getAppWidth(), 306);
        namePane.setLayoutY((getAppHeight() / 2.0)-(namePane.getHeight() / 2));

        var nameField = texture("/enterName/textField.png");
        nameField.setLayoutY((getAppHeight() / 2.0)-((nameField.getHeight() / 2)-50));
        nameField.setLayoutX((getAppWidth()/2.0) - (nameField.getWidth()/2.0));

        var confirmBtn = texture("/enterName/confirmBtn.png");
        confirmBtn.setLayoutY((getAppHeight() / 2.0) - ((confirmBtn.getHeight() / 2)-50));
        confirmBtn.setLayoutX((getAppWidth()/2.0) - ((confirmBtn.getWidth()/2.0)-200));

        confirmBtn.setOnMouseClicked( e -> FXGL.getGameController().startNewGame());

        buttons = new HashMap<>() {{
            put("confirm", confirmBtn);
        }};

        group.getChildren().addAll(namePane, nameField, confirmBtn);
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
