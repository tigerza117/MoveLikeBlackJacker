package co.yunchao.client.views;

import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.TextField;

import java.util.HashMap;

import static com.almasb.fxgl.dsl.FXGL.texture;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppHeight;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppWidth;

public class EnterNameAction {

    private static Group group;
    private final HashMap<String, Node> buttons;
    TextField nameField;

    public EnterNameAction(){
        group = new Group();

        var namePane = texture("enterName/namePanel.png", getAppWidth(), 306);
        namePane.setLayoutY((getAppHeight() / 2.0)-(namePane.getHeight() / 2));

        var textField = texture("enterName/textField.png");
        textField.setLayoutY((getAppHeight() / 2.0)-((textField.getHeight() / 2)-50));
        textField.setLayoutX((getAppWidth()/2.0) - (textField.getWidth()/2.0));

        var confirmBtn = Button.create("enterName/confirmBtn", () -> {
            FXGL.getGameController().startNewGame();
            System.out.println(nameField.getText());
        });
        confirmBtn.setLayoutY((getAppHeight() / 2.0) - ((confirmBtn.getBoundsInLocal().getHeight() / 2)-50));
        confirmBtn.setLayoutX((getAppWidth()/2.0) - ((confirmBtn.getBoundsInLocal().getWidth() /2.0)-200));

        nameField = new TextField("Your Name");
        nameField.setMaxWidth(380);
        nameField.getStylesheets().add("css/style.css");
        nameField.getStyleClass().add("text-field");
        nameField.setLayoutY((getAppHeight() / 2.0) - ((nameField.getHeight() / 2)-14));
        nameField.setLayoutX((getAppWidth()/2.0) - ((confirmBtn.getBoundsInLocal().getWidth() /2.0)+200));

        nameField.setOnKeyTyped(ev -> {
            String txt = nameField.getText();
            if (txt.length() > 0) {
                System.out.println(txt.matches("^[a-zA-Z0-9_]*$"));
                if (!txt.matches("^[a-zA-Z0-9_]*$")) {
                    nameField.deletePreviousChar();
                }
                if (nameField.getText().length() > 10) {
                    nameField.deletePreviousChar();
                }
            }
            confirmBtn.setVisible(nameField.getText().length() > 3);
        });

        Group enterPane = new Group(textField, nameField, confirmBtn);

        buttons = new HashMap<>() {{
            put("confirm", confirmBtn);
        }};

        group.getChildren().addAll(namePane, enterPane);
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
