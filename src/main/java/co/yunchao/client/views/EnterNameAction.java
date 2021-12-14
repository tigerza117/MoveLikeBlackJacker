package co.yunchao.client.views;

import com.almasb.fxgl.scene.Scene;
import com.almasb.fxgl.scene.SubScene;
import javafx.animation.FadeTransition;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.NotNull;

import static com.almasb.fxgl.dsl.FXGL.*;

public class EnterNameAction extends SubScene {
    TextField nameField;
    FadeTransition fade;
    private ConfirmAction onConfirm;

    public EnterNameAction(){
        Rectangle shadow = new Rectangle();
        shadow.setHeight(getAppHeight());
        shadow.setWidth(getAppWidth());
        shadow.setFill(Color.BLACK);
        shadow.setOpacity(0.65);

        var namePane = texture("enterName/namePanel.png", getAppWidth(), 306);
        namePane.setLayoutY((getAppHeight() / 2.0)-(namePane.getHeight() / 2));

        var textField = texture("enterName/textField.png");
        textField.setLayoutY((getAppHeight() / 2.0)-((textField.getHeight() / 2)-50));
        textField.setLayoutX((getAppWidth()/2.0) - (textField.getWidth()/2.0));

        var confirmBtn = Button.create("enterName/confirmBtn", () -> {
            onConfirm.confirm(nameField.getText());
        });

        var BackBtn = Button.create("mainResources/backBtn", () -> {
            close();
        });

        confirmBtn.setLayoutY((getAppHeight() / 2.0) - ((confirmBtn.getBoundsInLocal().getHeight() / 2)-50));
        confirmBtn.setLayoutX((getAppWidth()/2.0) - ((confirmBtn.getBoundsInLocal().getWidth() /2.0)-200));
        BackBtn.setLayoutY((getAppHeight() / 2.0) - ((confirmBtn.getBoundsInLocal().getHeight() / 2)-160));
        BackBtn.setLayoutX((getAppWidth()/2.0) - ((confirmBtn.getBoundsInLocal().getWidth() /2.0)+30));

        nameField = new TextField("");
        nameField.setPromptText("Your Name");
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

        Group enterPane = new Group(textField, nameField, confirmBtn, BackBtn);

        Group group = new Group(shadow, namePane, enterPane);
        fade = SubSceneAnimation.fade(group);
        getContentRoot().getChildren().addAll(group);
    }

    @Override
    public void onEnteredFrom(@NotNull Scene prevState) {
        super.onEnteredFrom(prevState);
        fade.playFromStart();
    }

    public void close() {
        fade.setRate(-1);
        fade.play();
        fade.setOnFinished(event -> {
            getSceneService().popSubScene();
            fade.setOnFinished(null);
        });
    }

    public void setOnConfirm(ConfirmAction confirm) {
        this.onConfirm = confirm;
    }

    protected interface ConfirmAction {
        void confirm(String name);
    }
}
