package co.yunchao.client.views;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.Texture;
import com.almasb.fxgl.ui.FontType;
import javafx.scene.Group;
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
    private final Texture textureIcon;
    private final Texture textureDealerScore;
    private final Text textDealerScore;
    private boolean isDealer = false;

    public Seat(double offsetX, double offsetY) {
        this.textName = FXGL.getUIFactoryService().newText("", Color.WHITE, FontType.GAME, 16);
        this.textBetTotal = FXGL.getUIFactoryService().newText("", Color.WHITE, FontType.GAME, 28);
        this.cardEntities = new ArrayList<>();
        this.chipEntityBet = new ArrayList<>();
        this.textureIcon = FXGL.texture("bet_section/player_icon.png");
        this.textureDealerScore = FXGL.texture("bet_section/dealer_score.png");
        this.textDealerScore = FXGL.getUIFactoryService().newText("", Color.WHITE, FontType.GAME, 22);

        setTranslateX(offsetX);
        setTranslateY(offsetY);

        textureIcon.setTranslateX(43);
        textureIcon.setTranslateY(295);
        textureIcon.setVisible(false);

        textBetTotal.setText("");
        textBetTotal.setTranslateY(240);
        textBetTotal.setTranslateX(143);
        textBetTotal.setVisible(false);

        textName.setTranslateX(textureIcon.getTranslateX());
        textName.setTranslateY(textureIcon.getTranslateY() + textureIcon.getHeight() + 25);
        textName.setWrappingWidth(textureIcon.getWidth());
        textName.setTextAlignment(TextAlignment.CENTER);
        textName.setVisible(false);

        textureDealerScore.setVisible(false);

        textDealerScore.setTranslateY(textureDealerScore.getHeight() / 1.5);
        textDealerScore.setVisible(false);
        textDealerScore.setWrappingWidth(textureDealerScore.getWidth());
        textDealerScore.setTextAlignment(TextAlignment.CENTER);

        getChildren().addAll(textName, textureIcon, textureDealerScore, textDealerScore, textBetTotal);
    }

    public void addCard(CardEntity cardEntity) {
        var bounds = cardEntity.getBoundsInLocal();
        var size = cardEntities.size();
        cardEntity.setTranslateX((bounds.getWidth() * size * 0.6));
        cardEntity.setTranslateY(65 + (bounds.getHeight() * size * -0.1));

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
        textDealerScore.setVisible(true);

        if (!isDealer) {
            textName.setVisible(true);
            textBetTotal.setVisible(true);
            textureIcon.setVisible(true);
        }

        getGameScene().getContentRoot().getChildren().add(this);
    }

    public void close() {
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

    public void setScore(String text) {
        textDealerScore.setText(text);
        textureDealerScore.setVisible(true);
    }

    public void clearScore() {
        textDealerScore.setText("");
        textureDealerScore.setVisible(false);
    }
}
