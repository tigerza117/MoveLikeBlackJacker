package co.yunchao.client.views;

import com.almasb.fxgl.app.scene.StartupScene;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public class Startup extends StartupScene {
    public Startup(int appWidth, int appHeight) {
        super(appWidth, appHeight);

        Rectangle bg = new Rectangle(appWidth, appHeight);

        getContentRoot().getChildren().addAll(new StackPane(bg));
    }
}
