package co.yunchao.client.views;

import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;

public class CursorCall {
    static Image golden = new Image("assets/ui/cursors/gold_cursor.png");
    static Image goldenWaiting = new Image("assets/ui/cursors/gold_waiting.png");
    static Image goldenClick = new Image("assets/ui/cursors/gold_click.png");

    public static Cursor getCursor() {
        return new ImageCursor(golden);
    }

    public static Cursor getCursorWaiting() {
        return new ImageCursor(goldenWaiting);
    }

    public static Cursor getCursorClick() {
        return new ImageCursor(goldenClick);
    }
}
