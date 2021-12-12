package co.yunchao.client.views;

import co.yunchao.client.listener.MainMenuListener;
import co.yunchao.client.listener.ViewListener;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.core.asset.AssetType;
import javafx.scene.Group;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.*;

public class MainMenu extends FXGLMenu {
    private final List<MainMenuListener> listeners = new ArrayList<>();
    private final List<ViewListener> viewListeners = new ArrayList<>();
    private final List<Node> buttons = new ArrayList<>();
    private final Group group;

    public MainMenu() {
        super(MenuType.MAIN_MENU);

        var bg = texture("mainResources/background.png", getAppWidth(), getAppHeight());
        var logo = texture("mainResources/homeLogo.png", 400, 412);
        logo.setLayoutX(759);
        logo.setLayoutY(71);
        var body = createBody();

        group = new Group(bg, logo, body);

        getContentRoot().getChildren().addAll(group);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        viewListeners.forEach(viewListener -> viewListener.open(group));

        Music music = getAssetLoader().load(AssetType.MUSIC, "main_menu_bg.mp3");

        getAudioPlayer().loopMusic(music);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewListeners.forEach(viewListener -> viewListener.close(group));
        getAudioPlayer().stopAllSoundsAndMusic();
        getAudioPlayer().onMainLoopPausing();
        getGameScene().clearEffect();
    }

    public void addListener(MainMenuListener listener) {
        listeners.add(listener);
    }

    public void addViewListener(ViewListener listener) {
        viewListeners.add(listener);
    }

    public Group getGroup() {
        return group;
    }

    private Node createBody() {
        var playBtn = Button.create("/mainResources/play_btn", "Play_Button", () -> listeners.forEach(MainMenuListener::clickPlay));
        var optionBtn = Button.create("/mainResources/option_btn", () -> listeners.forEach(MainMenuListener::clickOption));
        var leaveBtn = Button.create("/mainResources/quit_btn", () -> listeners.forEach(MainMenuListener::clickLeave));
        Group group = new Group(playBtn, optionBtn, leaveBtn);

        int i = 0;
        for (Node n : group.getChildren()) {
            n.setLayoutY((n.getBoundsInLocal().getHeight() * (i*1.2)));
            i++;
        }

        group.setLayoutY((getAppHeight() / 3.0)+(group.getBoundsInLocal().getHeight() / 2));
        group.setLayoutX((getAppWidth() / 2.0)-(group.getBoundsInLocal().getWidth() / 2));

        return group;
    }
}
