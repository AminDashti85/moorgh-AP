package ui;

import javax.swing.ImageIcon;
import java.awt.Image;

public class Bullet {
    private int x;
    private int y;
    private Image image;

    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
        this.image = new ImageIcon("src/images/shot.png").getImage();
    }

    public void move() {
        this.y -= 10;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public Image getImage() { return image; }
}