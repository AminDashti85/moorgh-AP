package ui;

import java.awt.Image;
import javax.swing.ImageIcon;

public class PowerUp {
    public static final int ADD_FIRE = 0;
    public static final int RAPID_FIRE = 1;
    public static final int EXTRA_LIFE = 2;
    public static final int SHIELD = 3;
    public static final int FREEZE = 4;

    private int x;
    private int y;
    private int type;
    private Image image;

    public PowerUp(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.image = new ImageIcon("src/images/powerup_" + type + ".png").getImage();
    }

    public void move() {
        this.y += 2;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getType() { return type; }
    public Image getImage() { return image; }
}