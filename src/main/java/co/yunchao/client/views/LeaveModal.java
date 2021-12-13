package co.yunchao.client.views;

import com.almasb.fxgl.scene.Scene;
import com.almasb.fxgl.scene.SubScene;
import javafx.animation.FadeTransition;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.NotNull;

import static com.almasb.fxgl.dsl.FXGL.*;

public class LeaveModal extends SubScene {
    FadeTransition fade;

    public LeaveModal(){

        Rectangle shadow = new Rectangle();
        shadow.setHeight(getAppHeight());
        shadow.setWidth(getAppWidth());
        shadow.setFill(Color.BLACK);
        shadow.setOpacity(0.65);

        var quitPane = texture("leaveGame/leavePane.png", getAppWidth(), 290);
        quitPane.setLayoutY((getAppHeight() / 2.0)-(quitPane.getHeight() / 2));

        var yesBtn = Button.create("leaveGame/yesBtn", () -> getGameController().exit());
        var noBtn = Button.create("leaveGame/noBtn", () -> {
            play("Clicked.wav");
            close();
        });
        noBtn.setLayoutX(200);

        Group leaveMenu = new Group(yesBtn, noBtn);

        leaveMenu.setLayoutY((getAppHeight() / 2.0)+(leaveMenu.getBoundsInLocal().getHeight() / 3.5));
        leaveMenu.setLayoutX((getAppWidth() / 2.0)-(leaveMenu.getBoundsInLocal().getWidth() / 2));

        Group group = new Group(shadow, quitPane, leaveMenu);
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
