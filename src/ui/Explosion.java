package ui;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Explosion {
    private int x;
    private int y;
    private int lifeTimer;
    private Image image;

    public Explosion(int x, int y) {
        this.x = x;
        this.y = y;
        this.lifeTimer = 15;
        this.image = new ImageIcon("src/images/Explosion.png").getImage();
    }

    public void update() {
        lifeTimer--;
    }

    public boolean isFinished() {
        return lifeTimer <= 0;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public Image getImage() { return image; }
}