package ui;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.io.File;

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
        loadImage();
    }

    private void loadImage() {
        String path = "src/images/";
        switch (type) {
            case ADD_FIRE:
                path += "add_shot.png";
                break;
            case RAPID_FIRE:
                path += "fast_shot.png";
                break;
            case EXTRA_LIFE:
                path += "heal.png";
                break;
            case SHIELD:
                path += "sheild.png";
                break;
            case FREEZE:
                path += "freeze.png";
                break;
        }

        if (!new File(path).exists()) {
            path = path.replace("src/", "");
        }

        image = new ImageIcon(path).getImage();
    }

    public void move() {
        y += 2;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getType() { return type; }
    public Image getImage() { return image; }
}