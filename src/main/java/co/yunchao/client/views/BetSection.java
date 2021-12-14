package co.yunchao.client.views;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.ui.FontType;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Group;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;

public class BetSection extends Group {
    private final Text balanceText;

    BetSection(Table table) {
        balanceText = FXGL.getUIFactoryService().newText("0$", Color.WHITE, FontType.GAME, 52);

        var optionBtn = Button.create("in_game_option_btn", () -> {
            System.out.println("Leave Game");
            getSceneService().pushSubScene(new OptionsModal());
        });
        var leaveBtn = Button.create("leave_btn", () -> {
            System.out.println("Leave Game");
            getSceneService().pushSubScene(new LeaveModal(() -> {
                getGameController().gotoMainMenu();
                table.close();
            }));
        });

        ProgressBar progress = new ProgressBar();
        IntegerProperty seconds = new SimpleIntegerProperty();
        progress.progressProperty().bind(seconds.divide(60.0));
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(seconds, 60)),
                new KeyFrame(Duration.minutes(1), e-> System.out.println("Minute over"), new KeyValue(seconds, 0))
        );
        timeline.setCycleCount(1);
        timeline.play();
        progress.setPrefWidth(703);
        progress.getStylesheets().add("css/style.css");
        progress.getStyleClass().add("progress-bar");

        Text time = FXGL.getUIFactoryService().newText("time", Color.WHITE, FontType.GAME, 20);
        var textureBalance = texture("balance_box.png");
        var confirmBtn = texture("confirm_btn.png");
        var standBtn = texture("stand_btn.png");
        var hitBtn = texture("hit_btn.png");
        var doubleBtn = texture("double_btn.png");
        var textureChipSection = texture("chip_section.png");
        var minBtn = texture("min_btn.png");
        var maxBtn = texture("max_btn.png");
        var clearBtn = texture("clear_btn.png");
        var chip1BetBtn = texture("chip1_bet_btn.png");
        var chip2BetBtn = texture("chip2_bet_btn.png");
        var chip3BetBtn = texture("chip3_bet_btn.png");

        var disableGroup = new Group();
        var timerGroup = new Group();
        var topGroup = new Group();
        var bottomGroup = new Group();
        var chipSection = new Group();
        bottomGroup.setTranslateY(62);
        topGroup.setTranslateX(718);
        chipSection.setTranslateX(718);

        leaveBtn.setTranslateY(76);
        textureBalance.setTranslateX(186);
        balanceText.setTranslateX(186);
        balanceText.setTranslateY(textureBalance.getHeight() - balanceText.getBoundsInLocal().getHeight() + 25);
        confirmBtn.setTranslateX(496);
        standBtn.setTranslateX(1152);
        hitBtn.setTranslateX(1391);
        doubleBtn.setTranslateX(1630);
        balanceText.setWrappingWidth(textureBalance.getWidth());
        balanceText.setTextAlignment(TextAlignment.CENTER);
        chip1BetBtn.setTranslateY(15);
        chip1BetBtn.setTranslateX(19);
        chip2BetBtn.setTranslateY(15);
        chip2BetBtn.setTranslateX(155);
        chip3BetBtn.setTranslateY(15);
        chip3BetBtn.setTranslateX(291);
        maxBtn.setTranslateX(143);
        clearBtn.setTranslateX(286);
        progress.setTranslateY(35);
        progress.setTranslateX((getAppWidth()/2.0)+190);
        time.setTranslateY(25);
        time.setTranslateX((getAppWidth()/2.0)+515);

        timerGroup.getChildren().addAll(time, progress);
        chipSection.getChildren().addAll(textureChipSection, chip1BetBtn, chip2BetBtn, chip3BetBtn);
        topGroup.getChildren().addAll(minBtn, maxBtn, clearBtn);
        bottomGroup.getChildren().addAll(optionBtn, leaveBtn, textureBalance, confirmBtn, chipSection, standBtn, hitBtn, doubleBtn, balanceText);
        disableGroup.getChildren().addAll(topGroup, chipSection, standBtn, hitBtn, doubleBtn);

        topGroup.setTranslateY(-60); //set to show grayscale test
        ColorAdjust desaturate = new ColorAdjust();
        desaturate.setSaturation(-1);
        disableGroup.setEffect(desaturate);
        disableGroup.setTranslateY(62);

        getChildren().addAll(bottomGroup, disableGroup, timerGroup); //remove and add something to test
        setLayoutX(32);
        setLayoutY(842);
        prefWidth(1855);
        prefHeight(202);
    }

    public void setBalance(int number) {
        balanceText.setText(number + "$");
    }
}
