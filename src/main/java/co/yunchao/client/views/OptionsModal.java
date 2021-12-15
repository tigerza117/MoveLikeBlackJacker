package co.yunchao.client.views;

import com.almasb.fxgl.scene.Scene;
import com.almasb.fxgl.scene.SubScene;
import javafx.animation.FadeTransition;
import javafx.scene.Group;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.NotNull;

import static com.almasb.fxgl.dsl.FXGL.*;

public class OptionsModal extends SubScene {
    FadeTransition fade;

    public OptionsModal(){
        Rectangle shadow = new Rectangle();
        shadow.setHeight(getAppHeight());
        shadow.setWidth(getAppWidth());
        shadow.setFill(Color.BLACK);
        shadow.setOpacity(0.65);

        var banner = texture("options/option_pane.png", getGameScene().getAppWidth(), 684);
        banner.setLayoutY((getAppHeight() / 2.0)-(banner.getHeight() / 2));

        CheckBox fullScreen = new CheckBox();
        Slider masterVol = new Slider(0, 100, 0);
        Slider mscVol = new Slider(0, 100, 0);
        Slider sfxVol = new Slider(0, 100, 0);
        fullScreen.getStyleClass().add("big-check-box");

        var fullHD_btn = Button.create("options/full_hd", () -> {
            System.out.println("FULL HD RESOLUTION SELECTED!");
        });
        var HD_btn = Button.create("options/hd_btn", () -> {
            System.out.println("HD RESOLUTION SELECTED!");
        });
        var SD_btn = Button.create("options/sd_btn", () -> {
            System.out.println("SD RESOLUTION SELECTED!");
        });
        var saveBtn = Button.create("options/save_btn", () -> {
            System.out.println("master vol. : " + (int) masterVol.getValue());
            System.out.println("music vol. : " + (int) mscVol.getValue());
            System.out.println("sfx vol. : " + (int) sfxVol.getValue());
            System.out.println("Full screen toggle : " + fullScreen.isSelected());
            close();
        });

        fullHD_btn.setTranslateX(100);
        fullHD_btn.setTranslateY(-242);
        HD_btn.setTranslateX(340);
        HD_btn.setTranslateY(-230);
        SD_btn.setTranslateX(550);
        SD_btn.setTranslateY(-230);

        fullScreen.setTranslateY((getAppHeight()/2.0) - 120);
        fullScreen.setTranslateX((getAppWidth()/2.0) - 200);

        masterVol.setTranslateY(-40);
        masterVol.setPrefWidth(630);
        mscVol.setTranslateY(40);
        mscVol.setPrefWidth(630);
        sfxVol.setTranslateY(120);
        sfxVol.setPrefWidth(630);

        var resolutionText = texture("options/resolution.png");
        resolutionText.setLayoutY(-220);
        var fullScreenText = texture("options/full_text.png");
        fullScreenText.setLayoutY(-120);
        var masText = texture("options/master_text.png");
        masText.setLayoutY(-40);
        var mscText = texture("options/music_text.png");
        mscText.setLayoutY(40);
        var sfxText = texture("options/sfx_text.png");
        sfxText.setLayoutY(120);

        var separateLine = texture("options/separate_line.png");
        separateLine.setLayoutX((getAppWidth()/2.0)-250);
        separateLine.setLayoutY(310);

        Group text = new Group(resolutionText, fullScreenText, masText, mscText, sfxText);
        text.setLayoutY((getAppHeight() / 3.0)+(text.getBoundsInLocal().getHeight() / 1.8));
        text.setLayoutX((getAppWidth() / 4.0)-(text.getBoundsInLocal().getWidth() / 2.0));

        Group sliders = new Group(masterVol, mscVol, sfxVol);
        sliders.setTranslateX(110);

        Group options = new Group(fullHD_btn, HD_btn, SD_btn);

        fullScreen.getStylesheets().add("/css/style.css");

        saveBtn.setLayoutY((getAppHeight() / 2.0)+(saveBtn.getBoundsInLocal().getHeight() / 2)+180);
        saveBtn.setLayoutX((getAppWidth() / 2.0)-(saveBtn.getBoundsInLocal().getWidth() / 3));

        options.setLayoutY((getAppHeight() / 2.0)+(options.getBoundsInLocal().getHeight() / 3));
        options.setLayoutX((getAppWidth() / 2.0)-(options.getBoundsInLocal().getWidth() / 2));

        sliders.setLayoutY((getAppHeight() / 2.0)+(options.getBoundsInLocal().getHeight() / 3));
        sliders.setLayoutX((getAppWidth() / 2.0)-(options.getBoundsInLocal().getWidth() / 2));

        Group group = new Group(shadow, banner, options, text, fullScreen, sliders, saveBtn, separateLine);
        fade = SubSceneAnimation.fade(group);
        getContentRoot().getChildren().addAll(group);
        getContentRoot().setCursor(CursorCall.getCursor());
    }

    @Override
    public void onEnteredFrom(@NotNull Scene prevState) {
        super.onEnteredFrom(prevState);
        fade.playFromStart();
    }

    public void close() {
        fade.setRate(-1);
        fade.play();
        fade.setOnFinished(event -> {
            getSceneService().popSubScene();
            fade.setOnFinished(null);
        });
    }
}
