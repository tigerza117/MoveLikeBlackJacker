package co.yunchao.client.controllers;

import co.yunchao.client.views.Intro;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.*;
import javafx.scene.input.KeyCode;
import org.jetbrains.annotations.NotNull;

import static com.almasb.fxgl.dsl.FXGL.*;

public class MainController extends GameApplication {

    private GameController gameController;
    private MainMenuController mainMenuController;
    private GameMenuController gameMenuController;
    private LoadingController loadingController;
    private StartupController startupController;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(1920);
        settings.setHeightFromRatio(16/9.0);
        settings.setMainMenuEnabled(true);
        settings.setGameMenuEnabled(true);
        settings.setIntroEnabled(false);
        settings.setFullScreenAllowed(true);
        settings.setDeveloperMenuEnabled(true);
        settings.setScaleAffectedOnResize(true);
        settings.set3D(true);
        settings.setPauseMusicWhenMinimized(false);
        settings.setFontGame("Graduate.ttf");
        settings.setFontText("Graduate.ttf");
        settings.setFontMono("Graduate.ttf");
        settings.setManualResizeEnabled(false);
        settings.setPreserveResizeRatio(false);
        settings.setTitle("Legendary Blackjack");
        settings.setVersion("1.0");
        settings.setSceneFactory(new SceneFactory() {
            @NotNull
            @Override
            public FXGLMenu newMainMenu() {
                mainMenuController = new MainMenuController();
                return mainMenuController.getView();
            }

            @NotNull
            @Override
            public FXGLMenu newGameMenu() {
                gameMenuController = new GameMenuController();
                return gameMenuController.getView();
            }

            @NotNull
            @Override
            public LoadingScene newLoadingScene() {
                loadingController = new LoadingController();
                return loadingController.getView();
            }

            @NotNull
            @Override
            public StartupScene newStartup(int width, int height) {
                startupController = new StartupController();
                return startupController.getView();
            }

            @NotNull
            @Override
            public IntroScene newIntro() {
                return new Intro();
            }
        });
    }

    @Override
    protected void onPreInit() {
        super.onPreInit();
        getPrimaryStage().setWidth(1280);
        getPrimaryStage().setHeight(720);
        getPrimaryStage().setMaxWidth(1920);
        getPrimaryStage().setMaxHeight(1080);
        getPrimaryStage().setFullScreen(false);
    }

    @Override
    protected void initInput() {
        // Press F to trigger loading scene
        onKeyDown(KeyCode.F, () -> {
            getGameController().gotoLoading(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });
    }

    @Override
    protected void initGame() {
        gameController = new GameController();
        try {
            Thread.sleep(1000);
            gameController.Start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initUI() {

    }
}
