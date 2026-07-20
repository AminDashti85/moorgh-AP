package ui;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Egg {
    private double x;
    private double y;
    private double speedX;
    private double speedY;
    private Image image;

    public Egg(double x, double y, double speedX, double speedY, boolean isBullet) {
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
        if (isBullet) {
            this.image = new ImageIcon("src/images/shot.png").getImage();
        } else {
            this.image = new ImageIcon("src/images/egg.png").getImage();
        }
    }

    public void move() {
        this.x += speedX;
        this.y += speedY;
    }

    public int getX() { return (int) x; }
    public int getY() { return (int) y; }
    public Image getImage() { return image; }
}