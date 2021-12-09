package co.yunchao.client.views;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.Texture;
import com.almasb.fxgl.ui.FontType;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import static com.almasb.fxgl.dsl.FXGL.*;

import java.util.ArrayList;

public class Seat {
    private final ArrayList<Card> cards;
    private final double offsetX;
    private final double offsetY;
    private final Text textName;
    private final Texture textureIcon;
    private final Texture textureDealerScore;
    private final Text textDealerScore;
    private boolean isDealer = false;

    Seat(double offsetX, double offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.textName = FXGL.getUIFactoryService().newText("", Color.WHITE, FontType.GAME, 16);
        this.cards = new ArrayList<>();
        this.textureIcon = FXGL.texture("player_icon.png");
        this.textureDealerScore = FXGL.texture("Dealer Score.png");
        this.textDealerScore = FXGL.getUIFactoryService().newText("", Color.WHITE, FontType.GAME, 22);

        textureIcon.setTranslateX(offsetX + 43);
        textureIcon.setTranslateY(offsetY + 295);
        textureIcon.setVisible(false);

        textName.setTranslateX(textureIcon.getTranslateX());
        textName.setTranslateY(textureIcon.getTranslateY() + textureIcon.getHeight() + 30);
        textName.setWrappingWidth(textureIcon.getWidth());
        textName.setTextAlignment(TextAlignment.CENTER);
        textName.setVisible(false);

        textureDealerScore.setTranslateX(offsetX);
        textureDealerScore.setTranslateY(offsetY);
        textureDealerScore.setVisible(false);

        textDealerScore.setTranslateX(textureDealerScore.getTranslateX());
        textDealerScore.setTranslateY(textureDealerScore.getTranslateY() + textureDealerScore.getHeight() / 1.5);
        textDealerScore.setVisible(false);
        textDealerScore.setWrappingWidth(textureDealerScore.getWidth());
        textDealerScore.setTextAlignment(TextAlignment.CENTER);

        getGameScene().getContentRoot().getChildren().addAll(textName, textureIcon, textureDealerScore, textDealerScore);
    }

    public void addCard(Card card) {
        var bounds = card.getGroup().getBoundsInLocal();
        var size = cards.size();
        card.getGroup().setLayoutX(offsetX + (bounds.getWidth() * size * 0.6));
        card.getGroup().setLayoutY(offsetY + 65 + (bounds.getHeight() * size * -0.1));
        cards.add(card);
        card.spawn();
    }

    public void clearCard() {
        cards.forEach(Card::deSpawn);
    }

    public void sit() {
        textDealerScore.setText("9/19");
        textureDealerScore.setVisible(true);
        textDealerScore.setVisible(true);

        if (!isDealer) {
            textName.setText("TIGER");
            textName.setVisible(true);
            textureIcon.setVisible(true);
        }
    }

    public boolean isDealer() {
        return isDealer;
    }

    public void setIsDealer(boolean isDealer) {
        this.isDealer = isDealer;
    }

    public double getOffsetX() {
        return offsetX;
    }

    public double getOffsetY() {
        return offsetY;
    }
}
