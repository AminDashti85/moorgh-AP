package ui;

import javax.swing.ImageIcon;
import java.awt.Image;

public class Plane {
    private int x;
    private int y;
    private int speed;
    private int lives;
    private int firePower;
    private Image image;

    public Plane(int x, int y) {
        this.x = x;
        this.y = y;
        this.speed = 5;
        this.lives = 3;
        this.firePower = 1;
        this.image = new ImageIcon("src/images/airplane.png").getImage();
    }

    public void moveLeft() {
        this.x -= speed;
        if (this.x < 0) this.x = 0;
    }

    public void moveRight() {
        this.x += speed;
        if (this.x > 730) this.x = 730;
    }

    public void decreaseLife() {
        this.lives--;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public Image getImage() { return image; }
    public int getLives() { return lives; }
    public int getFirePower() { return firePower; }
}