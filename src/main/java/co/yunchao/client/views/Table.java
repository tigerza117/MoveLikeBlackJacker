package co.yunchao.client.views;

import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.core.asset.AssetType;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.Group;

import java.util.HashMap;

import static com.almasb.fxgl.dsl.FXGL.*;

public class Table {
    private final Music music;
    private final Texture bg;
    private final BetSection betSection;
    private final Group group;
    private final HashMap<String, Seat> seats;

    public Table() {
        group = new Group();
        getGameScene().getContentRoot().getChildren().add(group);
        music = getAssetLoader().load(AssetType.MUSIC, "in-game_bg.mp3");
        bg = texture("game_background.png", getAppWidth(), getAppHeight());
        group.getChildren().add(bg);
        seats = new HashMap<>(){{
            put("dealer", new Seat(876, 40));
            put("player1", new Seat( 432,294));
            put("player2", new Seat( 729,377));
            put("player3", new Seat( 1024,375));
            put("player4", new Seat( 1319,293));
        }};
        seats.get("dealer").setIsDealer(true);

        betSection = new BetSection();
    }

    public void render() {
        group.setVisible(true);
        betSection.getGroup().setVisible(true);
    }

    public void close() {
        getAudioPlayer().stopMusic(music);
        getAudioPlayer().onMainLoopPausing();
    }

    public BetSection getBetSection() {
        return betSection;
    }

    public Group getGroup() {
        return group;
    }

    public HashMap<String, Seat> getSeats() {
        return seats;
    }
}
