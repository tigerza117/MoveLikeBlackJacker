package co.yunchao.client.views;

import co.yunchao.client.controllers.GameController;
import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.core.asset.AssetType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.Texture;
import com.almasb.fxgl.ui.FontType;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import static com.almasb.fxgl.dsl.FXGL.*;

public class Table extends Group {
    private final Music music;
    Text room_id;
    private final BetSection betSection;
    private final GameController gameController;

    public Table(GameController gameController) {
        this.gameController = gameController;
        this.music = getAssetLoader().load(AssetType.MUSIC, "in-game_bg.mp3");

        Texture bg = texture("bet_section/game_background.png", getAppWidth(), getAppHeight());
        Texture roomID = texture("enter_play/room_pane.png");
        roomID.setLayoutX(30);

        room_id = FXGL.getUIFactoryService().newText("ARCTIC505", Color.BLACK, FontType.GAME, 22);
        room_id.setLayoutY(85);
        room_id.setWrappingWidth(roomID.getWidth()+60);
        room_id.setTextAlignment(TextAlignment.CENTER);

        getChildren().addAll(bg, roomID, room_id);

        betSection = new BetSection(this);

        getChildren().add(betSection);
    }

    public void setRoomID(String id) {
        this.room_id.setText(id);
    }

    public void render() {
        getGameScene().getContentRoot().getChildren().add(this);
        getAudioPlayer().loopMusic(music);
    }

    public void close() {
        getGameScene().getContentRoot().getChildren().remove(this);
        getAudioPlayer().stopMusic(music);
        getAudioPlayer().onMainLoopPausing();
        gameController.close();
    }

    public BetSection getBetSection() {
        return betSection;
    }
}
