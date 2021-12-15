package co.yunchao.base.models;

public class Offset {
    int x = 0;
    int y = 0;

    public Offset(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int offsetX) {
        this.x = offsetX;
    }

    public void setY(int offsetY) {
        this.y = offsetY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
