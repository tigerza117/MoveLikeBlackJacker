package co.yunchao.client.views;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.ui.FontType;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class DisconnectedModal {
    private final Group group;
    Text errorMsg;

    public DisconnectedModal(){
        group = new Group();

        var disPane = texture("disconnect/disconnectPane.png", getAppWidth(), 290);
        disPane.setLayoutY((getAppHeight() / 2.0)-(disPane.getHeight() / 2));

        var okBtn = Button.create("disconnect/okBtn", () -> getGameController().exit());
        okBtn.setLayoutY((getAppHeight() / 2.0)-((okBtn.getBoundsInLocal().getHeight() / 2)-50));
        okBtn.setLayoutX((getAppWidth() / 2.0)-(okBtn.getBoundsInLocal().getWidth() / 2));

        this.errorMsg = FXGL.getUIFactoryService().newText("TIGER KICKED TOP OUT OF THE PLANET", Color.WHITE, FontType.GAME, 16);
        errorMsg.setWrappingWidth(disPane.getWidth());
        errorMsg.setTextAlignment(TextAlignment.CENTER);
        errorMsg.setTranslateY(535);

        group.getChildren().addAll(disPane, okBtn, errorMsg);
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
