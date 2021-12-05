package co.yunchao.client.views;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class Home extends GameApplication {

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Legendary BlackJack");
        settings.setHeight(720);
        settings.setWidth(1280);
    }
    protected void initMainMenu(Pane mainMenuRoot){
        Rectangle homeBg = new Rectangle(1280, 720);

        Button playBtn = new Button("Play");
        Button optBtn = new Button("Options");
        Button qBtn = new Button("Quit");

        VBox btnBox = new VBox(50, playBtn,optBtn,qBtn);
        btnBox.setTranslateX(400);
        btnBox.setTranslateY(200);

        mainMenuRoot.getChildren().addAll(homeBg, btnBox);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
