package co.yunchao.client.views;

import co.yunchao.client.listener.MainMenuListener;
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
    private final List<Node> buttons = new ArrayList<>();
    private final Group group;

    public MainMenu() {
        super(MenuType.MAIN_MENU);

        var bg = texture("main/background.png", getAppWidth(), getAppHeight());
        var logo = texture("main/home_logo.png", 400, 412);
        logo.setLayoutX(759);
        logo.setLayoutY(71);
        var body = createBody();

        group = new Group(bg, logo, body);
        getContentRoot().setCursor(CursorCall.getCursor());
        getGameScene().setCursor(CursorCall.getCursor());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Music music = getAssetLoader().load(AssetType.MUSIC, "main_menu_bg.mp3");
        getSettings().globalSoundVolumeProperty().setValue((OptionsModal.getMasterVol().getValue()/100)*(OptionsModal.getSfxVol().getValue()/100));
        getSettings().globalMusicVolumeProperty().setValue((OptionsModal.getMasterVol().getValue()/100)*(OptionsModal.getMscVol().getValue()/100));
        getAudioPlayer().loopMusic(music);

        System.out.println(OptionsModal.getMasterVol().getValue() + " " + OptionsModal.getMscVol().getValue());

        getContentRoot().getChildren().add(group);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getAudioPlayer().stopAllSoundsAndMusic();
        getAudioPlayer().onMainLoopPausing();
        getContentRoot().getChildren().remove(group);
    }

    public void addListener(MainMenuListener listener) {
        listeners.add(listener);
    }

    public Group getGroup() {
        return group;
    }

    private Node createBody() {
        var playBtn = Button.create("main/play_btn", "Play_Button", () -> listeners.forEach(MainMenuListener::clickPlay));
        var optionBtn = Button.create("main/option_btn", () -> listeners.forEach(MainMenuListener::clickOption));
        var leaveBtn = Button.create("main/quit_btn", () -> listeners.forEach(MainMenuListener::clickLeave));
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
