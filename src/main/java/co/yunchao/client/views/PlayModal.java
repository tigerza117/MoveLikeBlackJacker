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

public class PlayModal extends SubScene {
    TextField codeField;
    FadeTransition fade;

    public PlayModal(){
        Rectangle shadow = new Rectangle();
        shadow.setHeight(getAppHeight());
        shadow.setWidth(getAppWidth());
        shadow.setFill(Color.BLACK);
        shadow.setOpacity(0.65);

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

        backBtn.setOnMouseClicked( e -> {
            close();
            play("Clicked.wav");
        });

        codeField = new TextField("");
        codeField.setMaxWidth(300);
        codeField.getStylesheets().add("/css/style.css");
        codeField.getStyleClass().add("text-field");
        codeField.setLayoutY((getAppHeight() / 2.0) - ((codeField.getHeight() / 2)-92));

        var confirmBtn = Button.create("enterName/confirmBtn", () -> {
            System.out.println(codeField.getText());
            getSceneService().popSubScene();
            getSceneService().pushSubScene(enterName);
            play("Clicked.wav");
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

        Group group = new Group(shadow, banner, playMenu, confirmBtn, codeField, backBtn);
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
}
