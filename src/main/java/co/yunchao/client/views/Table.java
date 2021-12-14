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

import java.util.HashMap;

import static com.almasb.fxgl.dsl.FXGL.*;

public class Table extends Group {
    private final Music music;
    Text room_id;
    private final BetSection betSection;
    private final HashMap<String, Seat> seats;
    private final GameController gameController;

    public Table(GameController gameController) {
        this.gameController = gameController;
        this.music = getAssetLoader().load(AssetType.MUSIC, "in-game_bg.mp3");

        Texture bg = texture("game_background.png", getAppWidth(), getAppHeight());
        Texture roomID = texture("enterRoom/roomID.png");
        roomID.setLayoutX(30);

        room_id = FXGL.getUIFactoryService().newText("ARCTIC505", Color.BLACK, FontType.GAME, 22);
        room_id.setLayoutY(85);
        room_id.setWrappingWidth(roomID.getWidth()+60);
        room_id.setTextAlignment(TextAlignment.CENTER);

        getChildren().addAll(bg, roomID, room_id);
        seats = new HashMap<>(){{
            put("dealer", new Seat(876, 40));
            put("player1", new Seat( 432,294));
            put("player2", new Seat( 729,377));
            put("player3", new Seat( 1024,375));
            put("player4", new Seat( 1319,293));
        }};
        seats.get("dealer").setIsDealer(true);

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

    public HashMap<String, Seat> getSeats() {
        return seats;
    }
}
