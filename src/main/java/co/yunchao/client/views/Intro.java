package co.yunchao.client.views;

import com.almasb.fxgl.app.scene.IntroScene;
import javafx.scene.CacheHint;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;

public class Intro extends IntroScene {
    MediaView viewer;

    public Intro() {
        setBackgroundColor(Color.BLACK);
    }

    @Override
    public void startIntro() {
        try {
            var file = getClass().getClassLoader().getResource("video/intro.mp4");
            if (file != null) {
                Media media = new Media(file.toString());
                MediaPlayer player = new MediaPlayer(media);
                player.setAutoPlay(true);
                player.setOnPlaying(() -> {
                    viewer = new MediaView(player);
                    player.setOnEndOfMedia(this::clear);
                    getContentRoot().getChildren().add(viewer);
                });
                player.setOnError(this::clear);

            }
        } catch (Exception e) {
            e.printStackTrace();
            clear();
        }
    }

    public void clear() {
        getController().gotoMainMenu();
    }
}
