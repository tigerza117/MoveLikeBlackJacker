package co.yunchao.client.views;

import com.almasb.fxgl.app.scene.IntroScene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class Intro extends IntroScene {
    MediaView viewer;

    public Intro() {
        var file = getClass().getClassLoader().getResource("hello.mp4");
        if (file != null) {
            Media media = new Media(file.toString());
            viewer = new MediaView(new MediaPlayer(media));

            getRoot().getChildren().add(viewer);
        }
    }

    @Override
    public void startIntro() {
        if (viewer != null) {
            var player = viewer.getMediaPlayer();
            player.play();
            player.setOnEndOfMedia(() -> {
                getRoot().getChildren().remove(viewer);
                getController().gotoMainMenu();
            });
        }
    }
}
