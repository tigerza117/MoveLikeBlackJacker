package co.yunchao.client;

import co.yunchao.client.controllers.MainController;
import com.almasb.fxgl.app.GameApplication;

public class Application {


    public static void main(String[] args) {
        GameApplication.launch(MainController.class, args);
    }
}
