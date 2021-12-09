package co.yunchao.client.views;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.core.asset.AssetType;
import javafx.beans.binding.Bindings;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGL.getAudioPlayer;

public class MainMenu extends FXGLMenu {
    private final List<Node> buttons = new ArrayList<>();
    private Music music;

    public MainMenu() {
        super(MenuType.MAIN_MENU);

        var bg = texture("background.png", getAppWidth(), getAppHeight());
        var logo = texture("homeLogo.png", 400, 412);
        logo.setLayoutX(759);
        logo.setLayoutY(71);
        var body = createBody();

        getContentRoot().getChildren().addAll(bg, logo, body);
    }

    private int animIndex = 0;

    @Override
    public void onCreate() {
        buttons.forEach(btn -> {
            animationBuilder(this)
                    .delay(Duration.seconds(animIndex * 0.1))
                    .interpolator(Interpolators.BACK.EASE_OUT())
                    .translate(btn)
                    .from(new Point2D(-200, 0))
                    .to(new Point2D(0, 0))
                    .buildAndPlay();
            animationBuilder(this)
                    .delay(Duration.seconds(animIndex * 0.1))
                    .fadeIn(btn)
                    .buildAndPlay();

            animIndex++;
        });

        music = getAssetLoader().load(AssetType.MUSIC, "main_menu_bg.mp3");

        getAudioPlayer().loopMusic(music);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getAudioPlayer().stopAllSoundsAndMusic();
        getAudioPlayer().onMainLoopPausing();
        getGameScene().clearEffect();
    }

    private Node createBody() {
        var playBtn = createButton("play_btn", () -> {
            var banner = texture("yellow_banner.png", getAppWidth(), 500);
            banner.setLayoutY((getAppHeight() / 2.0)-(banner.getHeight() / 2));

            var createBtn = createModalButton("createRoom", this::fireNewGame);
            createBtn.setTranslateY(-300);
            var orSep = texture("Or.png");
            orSep.setLayoutY(-180);
            var enterCodeBtn = createModalButton("enterNum", this::fireResume);
            enterCodeBtn.setTranslateY(-50);

            Group modal = new Group(createBtn, orSep, enterCodeBtn);

            getContentRoot().getChildren().forEach(node -> node.setEffect(new GaussianBlur()));
            modal.setLayoutY((getAppHeight() / 2.0)+(modal.getBoundsInLocal().getHeight() / 3));
            modal.setLayoutX((getAppWidth() / 2.0)-(modal.getBoundsInLocal().getWidth() / 2));
            getContentRoot().getChildren().addAll(banner, modal);
            play("Play_Button.wav");
        });
        var optionBtn = createButton("option_btn", () -> {
            fireNewGame();
            play("Clicked.wav");
        });
        var quitBtn = createButton("quit_btn", () -> {
            fireExit();
            play("Clicked.wav");
        });

        Group group = new Group(playBtn, optionBtn, quitBtn);

        int i = 0;
        for (Node n : group.getChildren()) {
            n.setLayoutY((n.getBoundsInLocal().getHeight() * (i*1.2)));
            n.scaleXProperty().bind(
                    Bindings.when(n.hoverProperty()).then(1.1).otherwise(1)
            );
            n.scaleYProperty().bind(
                    Bindings.when(n.hoverProperty()).then(1.1).otherwise(1)
            );
            i++;
        }

        group.setLayoutY((getAppHeight() / 2.0)+(group.getBoundsInLocal().getHeight() / 3));
        group.setLayoutX((getAppWidth() / 2.0)-(group.getBoundsInLocal().getWidth() / 2));

        return group;
    }

    /**
     * Creates a new button with given name that performs given action on click/press.
     *
     * @param action button action
     * @return new button
     */
    protected Node createButton(String file, Runnable action) {
        var bg = texture(file+".png", 674.54, 83);

        var btn = new StackPane(bg);

        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setOnMouseClicked(e -> action.run());
        
        buttons.add(btn);

        btn.setTranslateX(-200);
        btn.setCache(true);
        btn.setCacheHint(CacheHint.SPEED);

        return btn;
    }
    protected Node createModalButton(String file, Runnable action) {
        var bg = texture(file+".png");

        var modalBtn = new StackPane(bg);

        modalBtn.setAlignment(Pos.CENTER_LEFT);
        modalBtn.setOnMouseClicked(e -> action.run());

        buttons.add(modalBtn);

        modalBtn.setCache(true);
        modalBtn.setCacheHint(CacheHint.SPEED);

        return modalBtn;
    }
}
