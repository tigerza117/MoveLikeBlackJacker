package co.yunchao.client.controllers;

import co.yunchao.client.views.Loading;
import co.yunchao.client.views.MainMenu;
import co.yunchao.client.views.Startup;
import co.yunchao.client.views.Table;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.*;
import javafx.scene.input.KeyCode;
import org.jetbrains.annotations.NotNull;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class MainController extends GameApplication {

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(1920);
        settings.setHeightFromRatio(16/9.0);
        settings.setMainMenuEnabled(true);
        settings.setGameMenuEnabled(true);
        settings.setIntroEnabled(false);
        settings.setFullScreenAllowed(true);
        settings.setDeveloperMenuEnabled(true);
        settings.set3D(true);
        settings.setFontGame("Graduate.ttf");
        settings.setFontText("Graduate.ttf");
        settings.setFontMono("Graduate.ttf");
        settings.setSceneFactory(new SceneFactory() {
            @NotNull
            @Override
            public FXGLMenu newMainMenu() {
                return new MainMenu();
            }

            @NotNull
            @Override
            public FXGLMenu newGameMenu() {
                return new GameMenu();
            }

            @NotNull
            @Override
            public LoadingScene newLoadingScene() {
                return new Loading();
            }

            @NotNull
            @Override
            public StartupScene newStartup(int width, int height) {
                return new Startup(width, height);
            }

            @NotNull
            @Override
            public IntroScene newIntro() {
                return super.newIntro();
            }
        });
    }

    @Override
    protected void initInput() {
        // Press F to trigger loading scene
        onKeyDown(KeyCode.F, () -> {
            getGameController().gotoLoading(() -> {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            return null;
        });
    }

    @Override
    protected void initGame() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initUI() {
        Table table = new Table();
        table.render(getGameScene());
    }

    public void CreateRoom() {
        //Statement crate room call to server to create room
    }

    public void JoinByID() {

    }
}
