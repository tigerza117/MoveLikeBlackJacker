package co.yunchao.client.views;

import com.almasb.fxgl.scene.Scene;
import com.almasb.fxgl.scene.SubScene;
import javafx.animation.FadeTransition;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.NotNull;

import static com.almasb.fxgl.dsl.FXGL.*;

public class OptionsModal extends SubScene {
    static DropShadow glow;
    FadeTransition fade;
    CheckBox fullScreen;
    static Node fullHD_btn;
    static Node HD_btn;
    static Node SD_btn;
    Node saveBtn;
    static Slider masterVol = new Slider(0, 100, 50);
    static Slider mscVol = new Slider(0, 100, 50);
    static Slider sfxVol = new Slider(0, 100, 50);
    static String fullScreenCheck = "2";

    public OptionsModal(){
        Rectangle shadow = new Rectangle();
        shadow.setHeight(getAppHeight());
        shadow.setWidth(getAppWidth());
        shadow.setFill(Color.BLACK);
        shadow.setOpacity(0.65);

        glow = new DropShadow();
        glow.setRadius(40);
        glow.setOffsetX(0.0);
        glow.setOffsetY(0.0);
        glow.setColor(Color.rgb(239, 184, 32));
        glow.setSpread(.6);

        var banner = texture("options/option_pane.png", getGameScene().getAppWidth(), 684);
        banner.setLayoutY((getAppHeight() / 2.0)-(banner.getHeight() / 2));

        fullScreen = new CheckBox();
        fullScreen.getStyleClass().add("big-check-box");

        fullHD_btn = Button.create("options/full_hd", () -> {
            System.out.println("FULL HD RESOLUTION SELECTED!");
            fullHD_btn.setEffect(glow);
            HD_btn.setEffect(null);
            SD_btn.setEffect(null);
            fullScreenCheck = "1";
            fullHD_btn.effectProperty().setValue(glow);
        });
        HD_btn = Button.create("options/hd_btn", () -> {
            System.out.println("HD RESOLUTION SELECTED!");
            HD_btn.setEffect(glow);
            fullHD_btn.setEffect(null);
            SD_btn.setEffect(null);
            fullScreenCheck = "2";
        });
        SD_btn = Button.create("options/sd_btn", () -> {
            System.out.println("SD RESOLUTION SELECTED!");
            SD_btn.setEffect(glow);
            fullHD_btn.setEffect(null);
            HD_btn.setEffect(null);
            fullScreenCheck = "3";
        });


        saveBtn = Button.create("options/save_btn", () -> {
            System.out.println("master vol. : " + (int) masterVol.getValue());
            System.out.println("music vol. : " + (int) mscVol.getValue());
            System.out.println("sfx vol. : " + (int) sfxVol.getValue());
            System.out.println("Full screen toggle : " + fullScreen.isSelected());
            getSettings().globalMusicVolumeProperty().setValue((masterVol.getValue()/100)*(mscVol.getValue()/100));
            getSettings().globalSoundVolumeProperty().setValue((masterVol.getValue()/100)*(sfxVol.getValue()/100));
            if(fullScreenCheck.equals("1") && !fullScreen.isSelected()){
                getPrimaryStage().setWidth(1920);
                getPrimaryStage().setHeight(1080);
                getPrimaryStage().setFullScreen(false);
                fullScreen.setSelected(false);
            }
            else if(fullScreenCheck.equals("2") && !fullScreen.isSelected()){
                getPrimaryStage().setWidth(1280);
                getPrimaryStage().setHeight(720);
                getPrimaryStage().setFullScreen(false);
                fullScreen.setSelected(false);
            }
            else if(fullScreenCheck.equals("3") && !fullScreen.isSelected()){
                getPrimaryStage().setWidth(640);
                getPrimaryStage().setHeight(480);
                getPrimaryStage().setFullScreen(false);
                fullScreen.setSelected(false);
            }
            else if(fullScreen.isSelected()){
                getPrimaryStage().setFullScreen(fullScreen.isSelected());
                fullHD_btn.setEffect(null);
                HD_btn.setEffect(null);
                SD_btn.setEffect(null);
                fullScreenCheck = "0";
            }
            else if(!fullScreen.isSelected()){
                fullScreenCheck = "2";
                getPrimaryStage().setWidth(1280);
                getPrimaryStage().setHeight(720);
                getPrimaryStage().setFullScreen(false);
                fullScreen.setSelected(false);
                HD_btn.setEffect(glow);
            }
            close();
        });

        fullHD_btn.setTranslateX(100);
        HD_btn.setTranslateX(330);
        SD_btn.setTranslateX(540);

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
        options.setTranslateY(-230);

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
    
    public static Slider getMasterVol() {
        return masterVol;
    }

    public static Slider getMscVol() {
        return mscVol;
    }

    public static Slider getSfxVol() {
        return sfxVol;
    }

    public static Node getFullHD_btn() {
        return fullHD_btn;
    }

    public static Node getHD_btn() {
        return HD_btn;
    }

    public static Node getSD_btn() {
        return SD_btn;
    }

    public static DropShadow getGlow() {
        return glow;
    }
}
