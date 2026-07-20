package ui;

public class Bullet {
    private int x;
    private int y;
    private int speed = 10;

    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move() {
        this.y -= speed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}