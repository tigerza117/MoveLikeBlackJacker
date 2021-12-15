package co.yunchao.client.views;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.ui.FontType;
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
    Duration time;
    Double timeDecrease;
    Text timeText;
    ProgressBar progress;

    BetSection(Table table) {
        balanceText = FXGL.getUIFactoryService().newText("0$", Color.WHITE, FontType.GAME, 52);

        var optionBtn = Button.create("bet_section/in_game_option_btn", () -> {
            System.out.println("Options");
            getSceneService().pushSubScene(new OptionsModal());
        });
        var leaveBtn = Button.create("bet_section/leave_btn", () -> {
            System.out.println("Leave Game");
            getSceneService().pushSubScene(new LeaveModal(() -> {
                getGameController().gotoMainMenu();
                table.close();
            }));
        });

        progress = new ProgressBar();
        progress.setPrefWidth(703);
        progress.getStylesheets().add("css/style.css");
        progress.getStyleClass().add("progress-bar");

        timeText = FXGL.getUIFactoryService().newText("time", Color.WHITE, FontType.GAME, 20);

        var textureBalance = texture("bet_section/balance_box.png");
        var confirmBtn = Button.create("bet_section/confirm_btn", () -> System.out.println("Confirm"));
        var standBtn = Button.create("bet_section/stand_btn", () -> System.out.println("Stand"));
        var hitBtn = Button.create("bet_section/hit_btn", () -> System.out.println("Hit"));
        var doubleBtn = Button.create("bet_section/double_btn", () -> System.out.println("Double"));
        var textureChipSection = texture("bet_section/chip_section.png");
        var minBtn = Button.create("bet_section/min_btn", () -> System.out.println("Min Bet"));
        var maxBtn = Button.create("bet_section/max_btn", () -> System.out.println("Max Bet"));
        var clearBtn = Button.create("bet_section/clear_btn", () -> System.out.println("Clear"));
        var chip1BetBtn = Button.create("bet_section/chip1_bet_btn", () -> System.out.println("Chip 25$"));
        var chip2BetBtn = Button.create("bet_section/chip2_bet_btn", () -> System.out.println("Chip 100$"));
        var chip3BetBtn = Button.create("bet_section/chip3_bet_btn", () -> System.out.println("Chip 500$"));

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
        timeText.setTranslateY(25);
        timeText.setTranslateX((getAppWidth()/2.0)+515);

        timerGroup.getChildren().addAll(timeText, progress);
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

    public void setProgress(double percent) {
        progress.setProgress(percent);
    }
}
