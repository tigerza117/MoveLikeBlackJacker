package co.yunchao.client.views;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;

public class GameMenu extends FXGLMenu {
    private final OptionsModal optionsModal = new OptionsModal();

    public GameMenu() {
        super(MenuType.GAME_MENU);
    }

    /*@Override
    public void onCreate() {
        getGameScene().getContentRoot().getChildren().forEach(n -> n.setEffect(new GaussianBlur()));
        getRoot().getChildren().add(optionsModal.getGroup());
        optionsModal.render();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getGameScene().getContentRoot().getChildren().forEach(n -> n.setEffect(null));
        getRoot().getChildren().remove(optionsModal.getGroup());
    }*/
}
