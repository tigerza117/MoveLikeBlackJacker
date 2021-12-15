package co.yunchao.client.views;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.scene.SubScene;
import com.almasb.fxgl.ui.FontType;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import static com.almasb.fxgl.dsl.FXGL.*;

public class DisconnectedModal extends SubScene {

    public DisconnectedModal(String msg) {
        var disPane = texture("disconnect/disconnect_pane.png", getAppWidth(), 290);
        disPane.setLayoutY((getAppHeight() / 2.0)-(disPane.getHeight() / 2));

        var okBtn = Button.create("disconnect/ok_btn", () -> getSceneService().popSubScene());
        okBtn.setLayoutY((getAppHeight() / 2.0)-((okBtn.getBoundsInLocal().getHeight() / 2)-50));
        okBtn.setLayoutX((getAppWidth() / 2.0)-(okBtn.getBoundsInLocal().getWidth() / 2));

        Text errorMsg = FXGL.getUIFactoryService().newText(msg, Color.WHITE, FontType.GAME, 16);
        errorMsg.setWrappingWidth(disPane.getWidth());
        errorMsg.setTextAlignment(TextAlignment.CENTER);
        errorMsg.setTranslateY(535);

        getContentRoot().getChildren().addAll(disPane, okBtn, errorMsg);
    }
}
