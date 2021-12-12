package co.yunchao.client.views;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.effect.GaussianBlur;

import java.util.HashMap;

import static com.almasb.fxgl.dsl.FXGL.play;
import static com.almasb.fxgl.dsl.FXGL.texture;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class PlayButtonAction {

    private final HashMap<String, Node> buttons;
    private final Group group;
    TextArea codeField;

    public PlayButtonAction(){
        group = new Group();

        var banner = texture("/enterRoom/yellow_banner.png", getAppWidth(), 500);
        banner.setLayoutY((getAppHeight() / 2.0)-(banner.getHeight() / 2));

        var createBtn = texture("/enterRoom/createRoom.png");
        createBtn.setTranslateY(-300);
        var orSep = texture("/enterRoom/Or.png");
        orSep.setLayoutY(-180);
        var enterCode = texture("/enterName/textField.png");
        enterCode.setTranslateY(-50);

        var confirmBtn = texture("/enterName/confirmBtn.png");
        confirmBtn.setLayoutY((getAppHeight() / 2.0) - ((confirmBtn.getHeight() / 2)-130));
        confirmBtn.setLayoutX((getAppWidth()/2.0) - ((confirmBtn.getWidth()/2.0)-200));

        codeField = new TextArea("Enter Room");
        codeField.setMaxWidth(380);
        codeField.getStylesheets().add("/css/style.css");
        codeField.setLayoutY((getAppHeight() / 2.0) - ((codeField.getHeight() / 2)-95));
        codeField.setLayoutX((getAppWidth()/2.0) - ((confirmBtn.getWidth()/2.0)+200));

        Group playMenu = new Group(createBtn, orSep, enterCode);
        Group enterPane = new Group(codeField, confirmBtn);

        getGameScene().getContentRoot().getChildren().forEach(node -> node.setEffect(new GaussianBlur()));
        playMenu.setLayoutY((getAppHeight() / 2.0)+(playMenu.getBoundsInLocal().getHeight() / 3));
        playMenu.setLayoutX((getAppWidth() / 2.0)-(playMenu.getBoundsInLocal().getWidth() / 2));

        buttons = new HashMap<>() {{
            put("create", createBtn);
            put("confirm", confirmBtn);
        }};

        var enterNameAc = new enterNameAction();

        buttons.forEach((s, btn) -> {
            btn.setOnMouseClicked(e -> {
                banner.setVisible(false);
                playMenu.setVisible(false);
                enterPane.setVisible(false);
                enterNameAc.getGroup().setVisible(true);

                System.out.println(this.codeField.getText());
                play("Clicked.wav");
            });
        });

        group.getChildren().addAll(banner, playMenu, enterPane, enterNameAc.getGroup());
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
