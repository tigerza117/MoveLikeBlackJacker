package co.yunchao.client.views;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.*;

public class GameMenu extends FXGLMenu {
    private final List<Node> buttons = new ArrayList<>();
    private final Node body;

    public GameMenu() {
        super(MenuType.GAME_MENU);
        body = createBody();
    }

    @Override
    public void onCreate() {
        getGameScene().getContentRoot().getChildren().forEach(n -> n.setEffect(new GaussianBlur()));
        getContentRoot().getChildren().add(body);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getGameScene().getContentRoot().getChildren().forEach(n -> n.setEffect(null));
        getContentRoot().getChildren().remove(body);
    }

    private Node createBody() {
        var platBtn = createButton("play_btn", () -> {
            fireNewGame();
            play("Play_Button.wav");
        });

        Group group = new Group(platBtn);
        return group;
    }

    /**
     * Creates a new button with given name that performs given action on click/press.
     *
     * @param action button action
     * @return new button
     */
    private Node createButton(String file, Runnable action) {
        var bg = texture(file+".png", 674.54, 83);

        var btn = new StackPane(bg);

        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setOnMouseClicked(e -> action.run());

        buttons.add(btn);
        btn.setCache(true);
        btn.setCacheHint(CacheHint.SPEED);

        return btn;
    }
}
