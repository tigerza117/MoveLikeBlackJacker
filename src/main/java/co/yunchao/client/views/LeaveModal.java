package co.yunchao.client.views;

import com.almasb.fxgl.scene.Scene;
import javafx.scene.Group;
import com.almasb.fxgl.scene.SubScene;
import javafx.scene.effect.GaussianBlur;
import org.jetbrains.annotations.NotNull;

import static com.almasb.fxgl.dsl.FXGL.*;

public class LeaveModal extends SubScene {

    public LeaveModal(){

        var quitPane = texture("leaveGame/leavePane.png", getAppWidth(), 290);
        quitPane.setLayoutY((getAppHeight() / 2.0)-(quitPane.getHeight() / 2));

        var yesBtn = Button.create("leaveGame/yesBtn", () -> getGameController().exit());
        var noBtn = Button.create("leaveGame/noBtn", () -> getSceneService().popSubScene());
        noBtn.setLayoutX(200);

        Group leaveMenu = new Group(yesBtn, noBtn);

        leaveMenu.setLayoutY((getAppHeight() / 2.0)+(leaveMenu.getBoundsInLocal().getHeight() / 3.5));
        leaveMenu.setLayoutX((getAppWidth() / 2.0)-(leaveMenu.getBoundsInLocal().getWidth() / 2));

        getContentRoot().getChildren().addAll(quitPane, leaveMenu);
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
