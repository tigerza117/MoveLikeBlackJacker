package co.yunchao.client.views;

import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.GaussianBlur;

import java.util.HashMap;

import static com.almasb.fxgl.dsl.FXGL.texture;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class PlayButtonAction {

    private final HashMap<String, Node> buttons;
    private final Group group;

    public PlayButtonAction(){
        group = new Group();

        var banner = texture("/enterRoom/yellow_banner.png", getAppWidth(), 500);
        banner.setLayoutY((getAppHeight() / 2.0)-(banner.getHeight() / 2));

        var createBtn = texture("/enterRoom/createRoom.png");
        createBtn.setTranslateY(-300);
        var orSep = texture("/enterRoom/Or.png");
        orSep.setLayoutY(-180);
        var enterCodeBtn = texture("/enterRoom/enterNum.png");
        enterCodeBtn.setTranslateY(-50);

        createBtn.setOnMouseClicked( e -> FXGL.getGameController().startNewGame());

        Group playMenu = new Group(createBtn, orSep, enterCodeBtn);

        getGameScene().getContentRoot().getChildren().forEach(node -> node.setEffect(new GaussianBlur()));
        playMenu.setLayoutY((getAppHeight() / 2.0)+(playMenu.getBoundsInLocal().getHeight() / 3));
        playMenu.setLayoutX((getAppWidth() / 2.0)-(playMenu.getBoundsInLocal().getWidth() / 2));

        buttons = new HashMap<>() {{
            put("create", createBtn);
            put("enterRoom", enterCodeBtn);
        }};

        group.getChildren().addAll(banner, playMenu);
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
