package co.yunchao.client.views;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.scene.GameScene;
import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.core.asset.AssetType;
import javafx.scene.Node;

import java.util.ArrayList;

import static com.almasb.fxgl.dsl.FXGL.*;

public class Table {
    private ArrayList<Node> players = new ArrayList<Node>();
    private final Music music;

    public Table() {
        music = getAssetLoader().load(AssetType.MUSIC, "in-game_bg.mp3");
    }

    public void render() {
        var bg = texture("game_background.png", getAppWidth(), getAppHeight());
        var seat = new Seat( 432,293);
        for (String c : new String[]{"03", "04", "05", "55"}) {
            var card = new Card(c);
            seat.addCard(card);
        }
        getAudioPlayer().loopMusic(music);
        play("Cards_Shuffle.wav");
        play("Checked_Button.wav");
    }

    public void close() {
        getAudioPlayer().stopMusic(music);
    }
}
