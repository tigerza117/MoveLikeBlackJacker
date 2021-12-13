package co.yunchao.client.views;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;

class SubSceneAnimation {
    protected static FadeTransition fade(Node node) {
        FadeTransition fadeIn = new FadeTransition(new Duration(500), node);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.setCycleCount(1);
        fadeIn.setAutoReverse(true);
        fadeIn.playFromStart();
        return fadeIn;
    }
}
