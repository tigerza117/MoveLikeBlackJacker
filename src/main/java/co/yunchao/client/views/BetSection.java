package co.yunchao.client.views;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.ui.FontType;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.HashMap;

import static com.almasb.fxgl.dsl.FXGL.*;

public class BetSection {
    private final Group group;
    private final Text balanceText;
    private final OptionsModal optionsAction = new OptionsModal();
    private final LeaveModal leaveAction = new LeaveModal();
    private final HashMap<String, Node> buttons;

    BetSection() {
        group = new Group();
        balanceText = FXGL.getUIFactoryService().newText("0$", Color.WHITE, FontType.GAME, 52);

        var optionBtn = texture("in_game_option_btn.png");
        var leaveBtn = texture("leave_btn.png");
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

        buttons = new HashMap<>(){{
            put("option", optionBtn);
            put("leave", leaveBtn);
            put("confirm", confirmBtn);
            put("stand", confirmBtn);
            put("hit", hitBtn);
            put("double", doubleBtn);
            put("bet_min", minBtn);
            put("bet_max", maxBtn);
            put("bet_clear", clearBtn);
            put("bet_chip_1", chip1BetBtn);
            put("bet_chip_2", chip2BetBtn);
            put("bet_chip_3", chip3BetBtn);
        }};

        optionBtn.setOnMouseClicked(event -> {
            System.out.println("Leave Game");
            play("Clicked.wav");
            }
        );

        leaveBtn.setOnMouseClicked(event -> {
            System.out.println("Leave Game");
            play("Clicked.wav");
            }
        );

        var disableGroup = new Group();
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

        chipSection.getChildren().addAll(textureChipSection, chip1BetBtn, chip2BetBtn, chip3BetBtn);
        topGroup.getChildren().addAll(minBtn, maxBtn, clearBtn);
        bottomGroup.getChildren().addAll(optionBtn, leaveBtn, textureBalance, confirmBtn, chipSection, standBtn, hitBtn, doubleBtn, balanceText);
        disableGroup.getChildren().addAll(topGroup, chipSection, standBtn, hitBtn, doubleBtn);

        topGroup.setTranslateY(-80); //set to show grayscale test
        ColorAdjust desaturate = new ColorAdjust();
        desaturate.setSaturation(-1);
        disableGroup.setEffect(desaturate);
        disableGroup.setTranslateY(62);

        group.getChildren().addAll(bottomGroup, disableGroup); //remove and add something to test
        group.setLayoutX(32);
        group.setLayoutY(842);
        group.prefWidth(1855);
        group.prefHeight(202);
        group.setVisible(false);
        getGameScene().getContentRoot().getChildren().add(group);
    }

    public void setBalance(int number) {
        balanceText.setText(number + "$");
    }

    public HashMap<String, Node> getButtons() {
        return buttons;
    }

    public Group getGroup() {
        return group;
    }
}
