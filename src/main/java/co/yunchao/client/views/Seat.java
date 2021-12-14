package co.yunchao.client.views;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.Texture;
import com.almasb.fxgl.ui.FontType;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import static com.almasb.fxgl.dsl.FXGL.*;

import java.util.ArrayList;

public class Seat extends Group {
    private final ArrayList<CardEntity> cardEntities;
    private final ArrayList<ChipEntity> chipEntityBet;
    private final Text textName;
    private final Text textBetTotal;
    private final Texture textureIcon;
    private final Texture textureDealerScore;
    private final Text textDealerScore;
    private boolean isDealer = false;

    Seat(double offsetX, double offsetY) {
        this.textName = FXGL.getUIFactoryService().newText("", Color.WHITE, FontType.GAME, 16);
        this.textBetTotal = FXGL.getUIFactoryService().newText("", Color.WHITE, FontType.GAME, 28);
        this.cardEntities = new ArrayList<>();
        this.chipEntityBet = new ArrayList<>();
        this.textureIcon = FXGL.texture("player_icon.png");
        this.textureDealerScore = FXGL.texture("Dealer Score.png");
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

        getGameScene().getContentRoot().getChildren().add(this);
    }

    public void addCard(CardEntity cardEntity) {
        var bounds = cardEntity.getBoundsInLocal();
        var size = cardEntities.size();
        cardEntity.setLayoutX((bounds.getWidth() * size * 0.6));
        cardEntity.setLayoutY(65 + (bounds.getHeight() * size * -0.1));

        getChildren().add(cardEntity);
        cardEntities.add(cardEntity);
        cardEntity.spawn();
    }

    public void addChipBet(ChipEntity chipEntity) {
        var size = chipEntityBet.size();
        chipEntity.setTranslateX(63);
        chipEntity.setLayoutY(216 - (6 * size));
        getChildren().add(chipEntity);
        chipEntityBet.add(chipEntity);
        chipEntity.spawn();
    }

    public void clearCard() {
        cardEntities.forEach(CardEntity::deSpawn);
        chipEntityBet.forEach(ChipEntity::deSpawn);
    }

    public void sit() {
        textDealerScore.setText("9/19");
        textureDealerScore.setVisible(true);
        textDealerScore.setVisible(true);

        if (!isDealer) {
            textName.setText("TIGER");
            textName.setVisible(true);
            textBetTotal.setText("0$");
            textBetTotal.setVisible(true);
            textureIcon.setVisible(true);
        }
    }

    public boolean isDealer() {
        return isDealer;
    }

    public void setIsDealer(boolean isDealer) {
        this.isDealer = isDealer;
    }
}
