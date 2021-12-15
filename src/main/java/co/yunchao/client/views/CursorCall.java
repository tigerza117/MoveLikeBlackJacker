package co.yunchao.client.views;

import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;

public class CursorCall {
    static Image im = new Image("assets/ui/cursors/gold_waiting.png");
    static Cursor cursor = new ImageCursor(im);

    public static Cursor getCursor() {
        return cursor;
    }
}
