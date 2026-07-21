package ui;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.io.File;

public class Plane {
    private int x;
    private int y;
    private int lives = 3;
    private int firePower = 1;
    private long rapidFireEndTime = 0;
    private long shieldEndTime = 0;
    private Image image;

    public Plane(int x, int y) {
        this.x = x;
        this.y = y;
        String path = "src/images/airplane.png";
        if (!new File(path).exists()) {
            path = "images/plane.png";
        }
        this.image = new ImageIcon(path).getImage();
    }

    public void moveUp() { if (y > 0) y -= 5; }
    public void moveDown() { if (y < 510) y += 5; }
    public void moveLeft() { if (x > 0) x -= 5; }
    public void moveRight() { if (x < 730) x += 5; }

    public int getX() { return x; }
    public int getY() { return y; }
    public Image getImage() { return image; }
    public int getLives() { return lives; }

    public void decreaseLife() {
        if (!hasShield()) {
            lives--;
        }
    }

    public void addLife() {
        if (lives < 5) {
            lives++;
        }
    }

    public int getFirePower() { return firePower; }

    public void increaseFirePower() {
        firePower++;
    }

    public void activateRapidFire(long duration) {
        rapidFireEndTime = System.currentTimeMillis() + duration;
    }

    public boolean hasRapidFire() {
        return System.currentTimeMillis() < rapidFireEndTime;
    }

    public void activateShield(long duration) {
        shieldEndTime = System.currentTimeMillis() + duration;
    }

    public boolean hasShield() {
        return System.currentTimeMillis() < shieldEndTime;
    }
}