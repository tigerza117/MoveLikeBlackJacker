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
    private ConfirmAction onConfirm;

    public PlayModal() {
        Rectangle shadow = new Rectangle();
        shadow.setHeight(getAppHeight());
        shadow.setWidth(getAppWidth());
        shadow.setFill(Color.BLACK);
        shadow.setOpacity(0.65);

        var playMenu = new Group();
        var setName = new EnterNameAction();

        var banner = texture("enter_play/yellow_banner.png", getAppWidth(), 500);
        banner.setLayoutY((getAppHeight() / 2.0)-(banner.getHeight() / 2));

        var orSep = texture("enter_play/or_separate.png");
        orSep.setLayoutY(-180);

        var enterCodeBtn = texture("enter_name/text_field.png");
        enterCodeBtn.setTranslateY(-50);

        var backBtn = Button.create("main/back_btn", this::close);
        backBtn.setTranslateX((getAppWidth()/2.0) - (backBtn.getBoundsInLocal().getWidth() / 2.0));
        backBtn.setTranslateY((getAppHeight()/2.0) + 220);

        codeField = new TextField("");
        codeField.setMaxWidth(300);
        codeField.getStylesheets().add("/css/style.css");
        codeField.getStyleClass().add("text-field");
        codeField.setLayoutY((getAppHeight() / 2.0) - ((codeField.getHeight() / 2)-92));

        var confirmBtn = Button.create("enter_name/confirm_btn", () -> {
            setName.setOnConfirm((name -> {
                onConfirm.confirm(codeField.getText(), name);
            }));
            getSceneService().popSubScene();
            getSceneService().pushSubScene(setName);
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

        var createBtn = Button.create("enter_play/create_room_btn", () -> {
            setName.setOnConfirm((name -> {
                onConfirm.confirm("000A", name);
            }));
            getSceneService().popSubScene();
            getSceneService().pushSubScene(setName);
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

    public void setOnConfirm(ConfirmAction confirm) {
        this.onConfirm = confirm;
    }

    public interface ConfirmAction {
        void confirm(String roomId, String name);
    }
}
