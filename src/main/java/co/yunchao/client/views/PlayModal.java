package co.yunchao.client.views;

import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;

import static com.almasb.fxgl.dsl.FXGL.texture;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class PlayModal {

    private final Group group;
    TextField codeField;

    public PlayModal(){
        group = new Group();
        var playMenu = new Group();
        var enterNameAc = new enterNameAction();

        var banner = texture("/enterRoom/yellow_banner.png", getAppWidth(), 500);
        banner.setLayoutY((getAppHeight() / 2.0)-(banner.getHeight() / 2));

        var orSep = texture("/enterRoom/Or.png");
        orSep.setLayoutY(-180);

        var enterCodeBtn = texture("/enterName/textField.png");
        enterCodeBtn.setTranslateY(-50);

        codeField = new TextField("Enter Room");
        codeField.setMaxWidth(380);
        codeField.getStylesheets().add("/css/style.css");
        codeField.getStyleClass().add("text-field");
        codeField.setLayoutY((getAppHeight() / 2.0) - ((codeField.getHeight() / 2)-92));

        var confirmBtn = Button.create("/enterName/confirmBtn", () -> {
            banner.setVisible(false);
            playMenu.setVisible(false);
            codeField.setVisible(false);
            enterNameAc.getGroup().setVisible(true);
        });

        confirmBtn.setLayoutY((getAppHeight() / 2.0) - ((confirmBtn.getBoundsInLocal().getHeight() / 2)-130));
        confirmBtn.setLayoutX((getAppWidth()/2.0) - ((confirmBtn.getBoundsInLocal().getWidth()/2.0)-200));

        codeField.setLayoutX((getAppWidth()/2.0) - ((confirmBtn.getBoundsInLocal().getWidth()/2.0)+200));

        var createBtn = Button.create("/enterRoom/createRoom", () -> {
            banner.setVisible(false);
            playMenu.setVisible(false);
            codeField.setVisible(false);
            confirmBtn.setVisible(false);
            enterNameAc.getGroup().setVisible(true);
        });
        createBtn.setTranslateY(-300);

        playMenu.getChildren().addAll(createBtn, orSep, enterCodeBtn);

        getGameScene().getContentRoot().getChildren().forEach(node -> node.setEffect(new GaussianBlur()));
        playMenu.setLayoutY((getAppHeight() / 2.0)+(playMenu.getBoundsInLocal().getHeight() / 3));
        playMenu.setLayoutX((getAppWidth() / 2.0)-(playMenu.getBoundsInLocal().getWidth() / 2));

        group.getChildren().addAll(banner, playMenu, confirmBtn, codeField, enterNameAc.getGroup());

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
