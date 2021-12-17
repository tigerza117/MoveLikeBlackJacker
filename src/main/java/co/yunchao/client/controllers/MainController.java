package co.yunchao.client.controllers;

import co.yunchao.client.net.NetworkEngine;
import co.yunchao.client.views.Intro;
import co.yunchao.client.views.OptionsModal;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.*;
import javafx.scene.input.KeyCode;
import org.jetbrains.annotations.NotNull;

import static com.almasb.fxgl.dsl.FXGL.*;

public class MainController extends GameApplication {

    private GameController gameController;
    private MainMenuController mainMenuController;
    private LoadingController loadingController;
    private StartupController startupController;
    private final MainController mainController = this;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(1920);
        settings.setFullScreenFromStart(true);
        settings.setHeightFromRatio(16 / 9.0);
        settings.setMainMenuEnabled(true);
        settings.setGameMenuEnabled(false);
        settings.setIntroEnabled(true);
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
        settings.setAppIcon("main/home_logo.png");
        settings.setSceneFactory(new SceneFactory() {
            @NotNull
            @Override
            public FXGLMenu newMainMenu() {
                mainMenuController = new MainMenuController(mainController);
                return mainMenuController.getView();
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
        settings.addEngineService(NetworkEngine.class);
    }

    @Override
    protected void onPreInit() {
        super.onPreInit();
        //getPrimaryStage().setWidth(1920);
        //getPrimaryStage().setHeight(1080);
        getPrimaryStage().setFullScreen(true);
        //OptionsModal.getFullHD_btn().setEffect(OptionsModal.getGlow());
        OptionsModal.setFullScreenCheck("0");
        getPrimaryStage().setMaxWidth(1920);
        getPrimaryStage().setMaxHeight(1080);
        //getPrimaryStage().setFullScreen(false);
        getPrimaryStage().centerOnScreen();
    }

    @Override
    protected void initInput() {
        // Press F to trigger loading scene
        onKeyDown(KeyCode.F, () -> getGameController().gotoLoading(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
    }

    @Override
    protected void initGame() {
        super.initGame();
        gameController.start();
    }

    @Override
    protected void initUI() {
        super.initUI();
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }
}
