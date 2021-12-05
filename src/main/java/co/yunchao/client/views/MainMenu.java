package co.yunchao.client.views;

import com.almasb.fxgl.animation.Animation;
import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import javafx.beans.binding.StringBinding;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.*;
import static javafx.beans.binding.Bindings.createStringBinding;
import static javafx.beans.binding.Bindings.when;

public class MainMenu extends FXGLMenu {
    private List<Node> buttons = new ArrayList<>();
    private List<Animation<?>> animations = new ArrayList<>();

    private int animIndex = 0;

    public MainMenu() {
        super(MenuType.GAME_MENU);

        var bg = texture("background.png", getAppWidth(), getAppHeight());

        var homeLogo = texture("homeLogo.png", 280, 288.4);
        homeLogo.setLayoutX(getAppWidth()-((getAppWidth()/2.0)+140));
        homeLogo.setLayoutY(20);

        var body = createBody();

        getContentRoot().getChildren().addAll(bg, homeLogo, body);
    }


    @Override
    protected void onUpdate(double tpf) {
        super.onUpdate(tpf);
        animations.forEach(animation -> {
            animation.onUpdate(tpf);
        });
    }

    @Override
    public void onCreate() {
        animIndex = 0;

        buttons.forEach(btn -> {
            //btn.setOpacity(1);
        });
        animations.forEach(animation -> {
            animation.stop();
            animation.start();
        });
    }

    private Node createBody() {
        double midY = getAppHeight() / 2.0;

        double distance = midY - 25;

        var btnContinue = createActionButton(localizedStringProperty("menu.continue"), this::fireContinue);
        var btn1 = createActionButton(localizedStringProperty("menu.newGame"), this::fireNewGame);
        var btn2 = createActionButton(createStringBinding(() -> "PLACEHOLDER 1"), this::fireNewGame);
        var btn3 = createActionButton(createStringBinding(() -> "PLACEHOLDER 2"), this::fireNewGame);
        var btn4 = createActionButton(createStringBinding(() -> "PLACEHOLDER 3"), this::fireNewGame);
        var btn5 = createActionButton(createStringBinding(() -> "PLACEHOLDER 4"), this::fireNewGame);
        var btn6 = createActionButton(createStringBinding(() -> "PLACEHOLDER 5"), this::fireNewGame);
        var btn7 = createActionButton(localizedStringProperty("menu.exit"), this::fireExit);

        Group group = new Group(btnContinue, btn1, btn2, btn3, btn4, btn5, btn6, btn7);

        double dtheta = Math.PI / (group.getChildren().size() - 1);
        double angle = Math.PI / 2;

        int i = 0;
        for (Node n : group.getChildren()) {

            Point2D vector = new Point2D(Math.cos(angle), -Math.sin(angle))
                    .normalize()
                    .multiply(distance)
                    .add(0, midY);

            n.setLayoutX(vector.getX() - (i == 0 || i == 7 ? 0 : 100));
            n.setLayoutY(vector.getY());

            angle -= dtheta;

            // slightly hacky way to get a nice looking radial menu
            // we assume that there are 8 items
            if (i == 0 || i == group.getChildren().size() - 2) {
                angle -= dtheta / 2;
            } else if (i == 2 || i == 4) {
                angle += dtheta / 4;
            } else if (i == 3) {
                angle += dtheta / 2;
            }

            i++;
        }

        return group;
    }

    /**
     * Creates a new button with given name that performs given action on click/press.
     *
     * @param name  button name (with binding)
     * @param action button action
     * @return new button
     */
    private Node createActionButton(StringBinding name, Runnable action) {
        var bg = new Rectangle(200, 50);
        bg.setEffect(new BoxBlur());

        var text = getUIFactoryService().newText(name);
        text.setTranslateX(15);
        text.setFill(Color.BLACK);

        var btn = new StackPane(bg, text);

        bg.fillProperty().bind(when(btn.hoverProperty())
                .then(Color.LIGHTGREEN)
                .otherwise(Color.DARKGRAY)
        );

        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setOnMouseClicked(e -> action.run());

        // clipping
        buttons.add(btn);
        animations.add(animationBuilder(this)
                .delay(Duration.seconds(animIndex * 0.1))
                .interpolator(Interpolators.BACK.EASE_OUT())
                .translate(btn)
                .from(new Point2D(-200, 0))
                .to(new Point2D(0, 0))
                .build());
        animations.add(animationBuilder(this)
                .delay(Duration.seconds(animIndex * 0.1))
                .fadeIn(btn)
                .build());

        Rectangle clip = new Rectangle(200, 50);
        clip.translateXProperty().bind(btn.translateXProperty().negate());

        btn.setTranslateX(-200);
        btn.setClip(clip);
        btn.setCache(true);
        btn.setCacheHint(CacheHint.SPEED);

        return btn;
    }
}