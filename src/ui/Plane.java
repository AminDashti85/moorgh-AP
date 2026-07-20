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

    private long shieldEndTime = 0;
    private long rapidFireEndTime = 0;

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
        if (!hasShield()) {
            this.lives--;
        }
    }

    public void addLife() {
        if (this.lives < 5) {
            this.lives++;
        }
    }

    public void increaseFirePower() {
        this.firePower++;
    }

    public void activateShield(long durationMillis) {
        this.shieldEndTime = System.currentTimeMillis() + durationMillis;
    }

    public void activateRapidFire(long durationMillis) {
        this.rapidFireEndTime = System.currentTimeMillis() + durationMillis;
    }

    public boolean hasShield() {
        return System.currentTimeMillis() < shieldEndTime;
    }

    public boolean hasRapidFire() {
        return System.currentTimeMillis() < rapidFireEndTime;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public Image getImage() { return image; }
    public int getLives() { return lives; }
    public int getFirePower() { return firePower; }
}