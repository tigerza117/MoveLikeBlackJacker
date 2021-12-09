package co.yunchao.client.views;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.Texture;
import com.almasb.fxgl.ui.FontType;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
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

    Seat(double offsetX, double offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.textName = FXGL.getUIFactoryService().newText("RESUME", Color.WHITE, FontType.GAME, 24.0);
        this.cards = new ArrayList<>();
        this.textureIcon = FXGL.texture("player_icon.png");
        this.textureDealerScore = FXGL.texture("player_icon.png");
        this.textDealerScore = FXGL.getUIFactoryService().newText("", Color.WHITE, FontType.GAME, 24.0);

        textName.setTranslateX(offsetX);
        textName.setTranslateY(offsetY - 75);
        textName.setMouseTransparent(true);
        textName.setVisible(false);
        textureIcon.setTranslateX(offsetX);
        textureIcon.setTranslateY(offsetY - 50);
        textureIcon.setVisible(false);
        textureDealerScore.setVisible(false);
        textDealerScore.setVisible(false);

        getGameScene().getContentRoot().getChildren().addAll(textName, textureIcon);
        site();
    }

    public void addCard(Card card) {
        var bounds = card.getGroup().getBoundsInLocal();
        var size = cards.size();
        card.getGroup().setLayoutX(offsetX + (bounds.getWidth() * size * 0.6));
        card.getGroup().setLayoutY(offsetY + (bounds.getHeight() * size * -0.1));
        cards.add(card);
        card.spawn();
    }

    public void clearCard() {
        cards.forEach(Card::deSpawn);
    }

    public void site() {
        textName.setText("Tiger");
        textName.setVisible(true);
        textureIcon.setVisible(true);
    }

    public void stand() {

    }
}
