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

public class MainMenu extends FXGLMenu {
    private final List<Node> buttons = new ArrayList<>();
    private Music music;
    private final Options options = new Options();
    private final LeaveButtonAction leaveAction = new LeaveButtonAction();
    private final PlayButtonAction playAction = new PlayButtonAction();

    public MainMenu() {
        super(MenuType.MAIN_MENU);

        var bg = texture("/mainResources/background.png", getAppWidth(), getAppHeight());
        var logo = texture("/mainResources/homeLogo.png", 400, 412);
        logo.setLayoutX(759);
        logo.setLayoutY(71);
        var body = createBody();

        getContentRoot().getChildren().addAll(bg, logo, body, options.getGroup(),
                leaveAction.getGroup(), playAction.getGroup());
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
        var playBtn = createButton("/mainResources/play_btn", () -> {
            System.out.println("Play");
            playAction.render();
            play("Play_Button.wav");
        });
        var optionBtn = createButton("/mainResources/option_btn", () -> {
            System.out.println("Options");
            options.render();
            play("Clicked.wav");
        });
        var quitBtn = createButton("/mainResources/quit_btn", () -> {
            System.out.println("Leave Game");
            leaveAction.render();
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
