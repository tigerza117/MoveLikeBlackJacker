package co.yunchao.client.views;

import co.yunchao.base.enums.ChipType;
import co.yunchao.client.controllers.PlayerController;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.ui.FontType;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import static com.almasb.fxgl.dsl.FXGL.*;

public class BetSection extends Group {
    private final Text balanceText;
    ProgressBar progress;
    PlayerController player;

    Node confirmBtn;
    Node standBtn;
    Node hitBtn;
    Node doubleBtn;
    Node minBtn;
    Node maxBtn;
    Node clearBtn;
    Node chip1BetBtn;
    Node chip2BetBtn;
    Node chip3BetBtn;
    Group chipSection;

    BetSection(Table table, PlayerController player) {
        this.player = player;
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

        Text timeText = FXGL.getUIFactoryService().newText("time", Color.WHITE, FontType.GAME, 20);

        var textureBalance = texture("bet_section/balance_box.png");
        var textureChipSection = texture("bet_section/chip_section.png");
        confirmBtn = Button.create("bet_section/confirm_btn", player::confirmBet);
        standBtn = Button.create("bet_section/stand_btn", player::stand);
        hitBtn = Button.create("bet_section/hit_btn", player::hit);
        doubleBtn = Button.create("bet_section/double_btn", player::doubleDown);
        minBtn = Button.create("bet_section/min_btn", () -> {
            player.stackCurrentBetStage(ChipType.CHIP_SMALL);
        });
        maxBtn = Button.create("bet_section/max_btn", () -> {
            player.stackCurrentBetStage(ChipType.CHIP_LARGE, 4);
        });
        clearBtn = Button.create("bet_section/clear_btn", player::canConfirmBet);
        chip1BetBtn = Button.create("bet_section/chip1_bet_btn", () -> {
            player.stackCurrentBetStage(ChipType.CHIP_SMALL);
        });
        chip2BetBtn = Button.create("bet_section/chip2_bet_btn", () -> {
            player.stackCurrentBetStage(ChipType.CHIP_MEDIUM);
        });
        chip3BetBtn = Button.create("bet_section/chip3_bet_btn", () -> {
            player.stackCurrentBetStage(ChipType.CHIP_LARGE);
        });

        var disableGroup = new Group();
        var timerGroup = new Group();
        var topGroup = new Group();
        var bottomGroup = new Group();
        chipSection = new Group();
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
        disableGroup.setTranslateY(62);

        getChildren().addAll(bottomGroup, disableGroup, timerGroup); //remove and add something to test
        setLayoutX(32);
        setLayoutY(842);
        prefWidth(1855);
        prefHeight(202);
    }

    public void disable(Node node, boolean disable) {
        ColorAdjust desaturate = new ColorAdjust();
        desaturate.setSaturation(-1);
        if (disable) {
            node.setEffect(desaturate);
        } else {
            node.setEffect(null);
        }
        node.setDisable(disable);
    }

    public void update() {
        System.out.println("Update balance " + player.getBalance());
        balanceText.setText(player.getBalance() + "$");
        disable(confirmBtn, !player.canConfirmBet());
        disable(standBtn, !player.canStand());
        disable(hitBtn, !player.canHit());
        disable(doubleBtn, !player.canDoubleDown());
        disable(chipSection, !player.canStackCurrentBetStage(ChipType.CHIP_SMALL.getAmount()) && !player.canStackCurrentBetStage(ChipType.CHIP_SMALL.getAmount()) && !player.canStackCurrentBetStage(ChipType.CHIP_MEDIUM.getAmount()));
        disable(chip1BetBtn, !player.canStackCurrentBetStage(ChipType.CHIP_SMALL.getAmount()));
        disable(chip2BetBtn, !player.canStackCurrentBetStage(ChipType.CHIP_MEDIUM.getAmount()));
        disable(chip3BetBtn, !player.canStackCurrentBetStage(ChipType.CHIP_LARGE.getAmount()));
        disable(clearBtn, !player.canConfirmBet());
        disable(minBtn, !player.canStackCurrentBetStage(ChipType.CHIP_SMALL.getAmount()));
        disable(maxBtn, !player.canStackCurrentBetStage(ChipType.CHIP_LARGE.getAmount() * 4));
    }

    public void setProgress(double percent) {
        progress.setProgress(percent);
    }
}
