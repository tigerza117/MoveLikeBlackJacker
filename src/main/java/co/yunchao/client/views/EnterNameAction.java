package co.yunchao.client.views;

import com.almasb.fxgl.scene.Scene;
import com.almasb.fxgl.scene.SubScene;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import org.jetbrains.annotations.NotNull;

import static com.almasb.fxgl.dsl.FXGL.*;

public class EnterNameAction extends SubScene {
    TextField nameField;

    public EnterNameAction(){
        var namePane = texture("enterName/namePanel.png", getAppWidth(), 306);
        namePane.setLayoutY((getAppHeight() / 2.0)-(namePane.getHeight() / 2));

        var textField = texture("enterName/textField.png");
        textField.setLayoutY((getAppHeight() / 2.0)-((textField.getHeight() / 2)-50));
        textField.setLayoutX((getAppWidth()/2.0) - (textField.getWidth()/2.0));

        var confirmBtn = Button.create("enterName/confirmBtn", () -> {
            getGameController().startNewGame();
            System.out.println(nameField.getText());
        });

        var BackBtn = Button.create("mainResources/backBtn", () -> {
            play("Clicked.wav");
            getSceneService().popSubScene();
        });

        confirmBtn.setLayoutY((getAppHeight() / 2.0) - ((confirmBtn.getBoundsInLocal().getHeight() / 2)-50));
        confirmBtn.setLayoutX((getAppWidth()/2.0) - ((confirmBtn.getBoundsInLocal().getWidth() /2.0)-200));
        BackBtn.setLayoutY((getAppHeight() / 2.0) - ((confirmBtn.getBoundsInLocal().getHeight() / 2)-160));
        BackBtn.setLayoutX((getAppWidth()/2.0) - ((confirmBtn.getBoundsInLocal().getWidth() /2.0)+30));

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

        Group enterPane = new Group(textField, nameField, confirmBtn, BackBtn);

        getContentRoot().getChildren().addAll(namePane, enterPane);
    }

    @Override
    public void onEnteredFrom(@NotNull Scene prevState) {
        super.onEnteredFrom(prevState);
        prevState.getContentRoot().setEffect(new GaussianBlur());
    }

    @Override
    public void onExitingTo(@NotNull Scene nextState) {
        super.onExitingTo(nextState);
        nextState.getContentRoot().setEffect(null);
    }
}
