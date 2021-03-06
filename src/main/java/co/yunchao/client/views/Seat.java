package co.yunchao.client.views;

import co.yunchao.base.enums.ScoreColorType;
import co.yunchao.client.controllers.PlayerController;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.Texture;
import com.almasb.fxgl.ui.FontType;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;

import static com.almasb.fxgl.dsl.FXGL.getGameScene;

public class Seat extends Group {
    private final ArrayList<CardEntity> cardEntities;
    private final ArrayList<ChipEntity> chipEntityBet;
    private final Text textName;
    private final Text textBetTotal;
    private final Text balance;
    private final Texture textureIcon;
    private final Texture scorePane;
    private final Text textEachScore;
    private boolean isDealer = false;
    private final PlayerController player;

    public Seat(PlayerController player) {
        this.player = player;
        this.textName = FXGL.getUIFactoryService().newText("", Color.WHITE, FontType.GAME, 16);
        this.textBetTotal = FXGL.getUIFactoryService().newText("", Color.WHITE, FontType.GAME, 28);
        this.cardEntities = new ArrayList<>();
        this.chipEntityBet = new ArrayList<>();
        this.textureIcon = FXGL.texture("bet_section/player_icon.png");
        this.balance = FXGL.getUIFactoryService().newText("", Color.WHITE, FontType.GAME, 22);
        this.textEachScore = FXGL.getUIFactoryService().newText("", Color.WHITE, FontType.GAME, 16);
        this.scorePane = FXGL.texture("bet_section/score_pane.png");

        setTranslateX(player.getOffset().getX());
        setTranslateY(player.getOffset().getY());

        scorePane.setVisible(false);

        textEachScore.setText("");
        textEachScore.setTranslateY(25);
        textEachScore.setWrappingWidth(scorePane.getWidth());
        textEachScore.setTextAlignment(TextAlignment.CENTER);

        textureIcon.setTranslateX(43);
        textureIcon.setTranslateY(295);
        textureIcon.setVisible(false);

        textBetTotal.setText("");
        textBetTotal.setTranslateY(240);
        textBetTotal.setTranslateX(143);
        textBetTotal.setVisible(false);

        textName.setTranslateX(textureIcon.getTranslateX() - 25);
        textName.setTranslateY(textureIcon.getTranslateY() + textureIcon.getHeight() + 25);
        textName.setWrappingWidth(textureIcon.getWidth() + 50);
        textName.setTextAlignment(TextAlignment.CENTER);
        textName.setVisible(false);

        balance.setText("");
        balance.setTranslateX(textureIcon.getTranslateX() - 50);
        balance.setTranslateY(435);
        balance.setWrappingWidth(textureIcon.getWidth() + 100);
        balance.setTextAlignment(TextAlignment.CENTER);
        balance.setVisible(false);

        getChildren().addAll(textName, textureIcon, textBetTotal, balance, scorePane, textEachScore);
    }

    public void addCard(CardEntity cardEntity) {
        var bounds = cardEntity.getBoundsInLocal();
        var size = cardEntities.size();
        cardEntity.setLayoutX((bounds.getWidth() * size * 0.6));
        cardEntity.setLayoutY(65 + (bounds.getHeight() * size * -0.1));

        getChildren().add(cardEntity);
        cardEntities.add(cardEntity);
    }

    public void addChipBet(ChipEntity chipEntity) {
        var size = chipEntityBet.size();
        chipEntity.setTranslateX(63);
        chipEntity.setLayoutY(216 - (6 * size));
        getChildren().add(chipEntity);
        chipEntityBet.add(chipEntity);
    }

    public void removeCard(CardEntity cardEntity) {
        cardEntities.remove(cardEntity);
    }

    public void removeChip(ChipEntity chip) {
        chipEntityBet.remove(chip);
    }

    public void setName(String name) {
        this.textName.setText(name);
    }

    public void sit() {
        if (!isDealer) {
            textName.setVisible(true);
            textBetTotal.setVisible(true);
            textureIcon.setVisible(true);
            balance.setVisible(true);
        }
        getGameScene().getContentRoot().getChildren().add(this);
    }

    public void close() {
        cardEntities.clear();
        chipEntityBet.clear();
        getGameScene().getContentRoot().getChildren().remove(this);
    }

    public boolean isDealer() {
        return isDealer;
    }

    public void setIsDealer(boolean isDealer) {
        this.isDealer = isDealer;
    }

    public void setBetStack(String text) {
        textBetTotal.setText(text);
    }

    public void myTurn() {
        DropShadow playerGlow = new DropShadow();
        playerGlow.setRadius(80);
        playerGlow.setOffsetX(0.0);
        playerGlow.setOffsetY(5.0);
        playerGlow.setColor(Color.rgb(239, 184, 32));
        playerGlow.setSpread(0.5);
        textureIcon.setEffect(playerGlow);
    }

    public void notMyTurn() {
        textureIcon.setEffect(null);
    }

    public void updateBalance() {
        balance.setText(player.getBalance() + "$");
    }

    public void setScore(String text, ScoreColorType colorType) {
        textEachScore.setText(text);
        textEachScore.setFill(Color.valueOf(colorType.name()));
        scorePane.setVisible(!text.isBlank());
    }
}
