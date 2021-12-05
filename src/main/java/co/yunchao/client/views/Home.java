package co.yunchao.client.views;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.MenuItem;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.LoadingScene;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.ui.UI;
import com.almasb.fxgl.ui.UIController;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getAssetLoader;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;

public class Home extends GameApplication  {

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(1080);
        settings.setHeightFromRatio(16/9.0);
        settings.setMainMenuEnabled(true);
        settings.setGameMenuEnabled(true);

        settings.setSceneFactory(new SceneFactory() {
            @NotNull
            @Override
            public FXGLMenu newMainMenu() {
                return new MainMenu();
            }

            @NotNull
            @Override
            public FXGLMenu newGameMenu() {
                return new MainMenu();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
