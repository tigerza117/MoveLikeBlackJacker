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

public class Seat {
    private final Group group;
    private final ArrayList<Card> cards;
    private final ArrayList<Chip> chipBet;
    private final Text textName;
    private final Text textBetTotal;
    private final Texture textureIcon;
    private final Texture textureDealerScore;
    private final Text textDealerScore;
    private boolean isDealer = false;

    Seat(double offsetX, double offsetY) {
        this.group = new Group();
        this.textName = FXGL.getUIFactoryService().newText("", Color.WHITE, FontType.GAME, 16);
        this.textBetTotal = FXGL.getUIFactoryService().newText("", Color.WHITE, FontType.GAME, 28);
        this.cards = new ArrayList<>();
        this.chipBet = new ArrayList<>();
        this.textureIcon = FXGL.texture("player_icon.png");
        this.textureDealerScore = FXGL.texture("Dealer Score.png");
        this.textDealerScore = FXGL.getUIFactoryService().newText("", Color.WHITE, FontType.GAME, 22);

        group.setTranslateX(offsetX);
        group.setTranslateY(offsetY);

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

        group.getChildren().addAll(textName, textureIcon, textureDealerScore, textDealerScore, textBetTotal);

        getGameScene().getContentRoot().getChildren().addAll(group);
    }

    public void addCard(Card card) {
        var cardGroup = card.getGroup();
        var bounds = cardGroup.getBoundsInLocal();
        var size = cards.size();
        cardGroup.setLayoutX((bounds.getWidth() * size * 0.6));
        cardGroup.setLayoutY(65 + (bounds.getHeight() * size * -0.1));
        group.getChildren().add(cardGroup);
        cards.add(card);
        card.spawn();
    }

    public void addChipBet(Chip chip) {
        var size = chipBet.size();
        var chipGroup = chip.getGroup();
        chipGroup.setTranslateX(63);
        chipGroup.setLayoutY(216 - (6 * size));
        group.getChildren().add(chipGroup);
        chipBet.add(chip);
        chip.spawn();
    }

    public void clearCard() {
        cards.forEach(Card::deSpawn);
        chipBet.forEach(Chip::deSpawn);
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
