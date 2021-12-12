package co.yunchao.client.views;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.GaussianBlur;

import java.util.HashMap;

import static com.almasb.fxgl.dsl.FXGL.texture;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class PlayModal {
    private final Group group;

    public PlayModal(){
        group = new Group();
        var playMenu = new Group();
        var enterNameAc = new enterNameAction();

        var banner = texture("/enterRoom/yellow_banner.png", getAppWidth(), 500);
        banner.setLayoutY((getAppHeight() / 2.0)-(banner.getHeight() / 2));

        var createBtn = Button.create("/enterRoom/createRoom", () -> {
            banner.setVisible(false);
            playMenu.setVisible(false);
            enterNameAc.getGroup().setVisible(true);
        });
        createBtn.setTranslateY(-300);

        var orSep = texture("/enterRoom/Or.png");
        orSep.setLayoutY(-180);

        var enterCodeBtn = Button.create("/enterRoom/enterNum", () -> {
            banner.setVisible(false);
            playMenu.setVisible(false);
            enterNameAc.getGroup().setVisible(true);
        });
        enterCodeBtn.setTranslateY(-50);

        playMenu.getChildren().addAll(createBtn, orSep, enterCodeBtn);

        getGameScene().getContentRoot().getChildren().forEach(node -> node.setEffect(new GaussianBlur()));
        playMenu.setLayoutY((getAppHeight() / 2.0)+(playMenu.getBoundsInLocal().getHeight() / 3));
        playMenu.setLayoutX((getAppWidth() / 2.0)-(playMenu.getBoundsInLocal().getWidth() / 2));

        group.getChildren().addAll(banner, playMenu, enterNameAc.getGroup());
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
