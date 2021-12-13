package co.yunchao.client.views;

import com.almasb.fxgl.scene.Scene;
import com.almasb.fxgl.scene.SubScene;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import org.jetbrains.annotations.NotNull;

import static com.almasb.fxgl.dsl.FXGL.*;

public class PlayModal extends SubScene {
    TextField codeField;

    public PlayModal(){
        var playMenu = new Group();
        var enterName = new EnterNameAction();

        var banner = texture("enterRoom/yellow_banner.png", getAppWidth(), 500);
        banner.setLayoutY((getAppHeight() / 2.0)-(banner.getHeight() / 2));

        var orSep = texture("enterRoom/Or.png");
        orSep.setLayoutY(-180);

        var enterCodeBtn = texture("enterName/textField.png");
        enterCodeBtn.setTranslateY(-50);

        var backBtn = texture("mainResources/backBtn.png");
        backBtn.setTranslateX((getAppWidth()/2.0) - (backBtn.getWidth() / 2.0));
        backBtn.setTranslateY((getAppHeight()/2.0) + 220);

        backBtn.setOnMouseClicked( e -> getSceneService().popSubScene());

        codeField = new TextField("");
        codeField.setMaxWidth(300);
        codeField.getStylesheets().add("/css/style.css");
        codeField.getStyleClass().add("text-field");
        codeField.setLayoutY((getAppHeight() / 2.0) - ((codeField.getHeight() / 2)-92));

        var confirmBtn = Button.create("enterName/confirmBtn", () -> {
            System.out.println(codeField.getText());
            getSceneService().popSubScene();
            getSceneService().pushSubScene(enterName);
        });

        confirmBtn.setLayoutY((getAppHeight() / 2.0) - ((confirmBtn.getBoundsInLocal().getHeight() / 2)-130));
        confirmBtn.setLayoutX((getAppWidth()/2.0) - ((confirmBtn.getBoundsInLocal().getWidth()/2.0)-200));
        confirmBtn.setVisible(false);

        codeField.setLayoutX((getAppWidth()/2.0) - ((confirmBtn.getBoundsInLocal().getWidth()/2.0)+200));

        codeField.setOnKeyTyped(ev -> {
            String txt = codeField.getText();
            if (txt.length() > 0) {
                try {
                    Integer.parseInt(txt);
                } catch (NumberFormatException e) {
                    codeField.deletePreviousChar();
                } finally {
                    if (codeField.getText().length() > 4) {
                        codeField.deletePreviousChar();
                    }
                }
            }
            confirmBtn.setVisible(codeField.getText().length() == 4);
        });

        var createBtn = Button.create("enterRoom/createRoom", () -> {
            getSceneService().popSubScene();
            getSceneService().pushSubScene(enterName);
        });
        createBtn.setTranslateY(-300);

        playMenu.getChildren().addAll(createBtn, orSep, enterCodeBtn);

        playMenu.setLayoutY((getAppHeight() / 2.0)+(playMenu.getBoundsInLocal().getHeight() / 3));
        playMenu.setLayoutX((getAppWidth() / 2.0)-((playMenu.getBoundsInLocal().getWidth() / 2)-10));

        getContentRoot().getChildren().addAll(banner, playMenu, confirmBtn, codeField, backBtn);
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
