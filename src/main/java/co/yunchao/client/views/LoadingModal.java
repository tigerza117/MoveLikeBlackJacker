package co.yunchao.client.views;

import com.almasb.fxgl.scene.SubScene;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;

import static com.almasb.fxgl.dsl.FXGL.*;

public class LoadingModal extends SubScene {

    public LoadingModal() {
        System.out.println("boi loadmodal");
        var bg = texture("main/loading_background.png", getAppWidth(), getAppHeight());
        getContentRoot().setCursor(CursorCall.getCursor());

        getRoot().getChildren().addAll(bg);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}

