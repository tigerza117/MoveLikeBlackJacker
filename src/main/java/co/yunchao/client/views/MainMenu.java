package co.yunchao.client.views;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import javafx.beans.binding.Bindings;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.*;

public class MainMenu extends FXGLMenu {
    private final List<Node> buttons = new ArrayList<>();

    public MainMenu() {
        super(MenuType.GAME_MENU);

        var bg = texture("background.png", getAppWidth(), getAppHeight());
        var logo = texture("homeLogo.png", 280, 288.4);
        logo.setLayoutX((getAppWidth() / 2.0)-(logo.getWidth() / 2));
        logo.setLayoutY(20);
        var body = createBody();

        getContentRoot().getChildren().addAll(bg, logo, body);
    }

    private int animIndex = 0;

    @Override
    public void onCreate() {
        int i = 0;
        buttons.forEach(btn -> {
            animationBuilder(this)
                    .delay(Duration.seconds(i * 0.1))
                    .interpolator(Interpolators.BACK.EASE_OUT())
                    .translate(btn)
                    .from(new Point2D(-200, 0))
                    .to(new Point2D(0, 0))
                    .buildAndPlay();
            animationBuilder(this)
                    .delay(Duration.seconds(i * 0.1))
                    .fadeIn(btn)
                    .buildAndPlay();

            animIndex++;
        });
        play("main_menu_bg.mp3");
    }

    private Node createBody() {
        var platBtn = createButton("play_btn", this::fireNewGame);
        var optionBtn = createButton("option_btn", this::fireNewGame);
        var quitBtn = createButton("quit_btn", this::fireExit);

        Group group = new Group(platBtn, optionBtn, quitBtn);

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
    private Node createButton(String file, Runnable action) {
        var bg = texture(file+".png", 674.54/2, 83/2);

        var btn = new StackPane(bg);

        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setOnMouseClicked(e -> action.run());

        buttons.add(btn);

        btn.setTranslateX(-200);
        btn.setCache(true);
        btn.setCacheHint(CacheHint.SPEED);

        return btn;
    }
}
