package ui;

import javax.swing.ImageIcon;

public class BossLevel8 extends Boss {
    private double exactX;
    private double exactY;
    private boolean movingRight = true;
    private boolean movingDown = true;

    public BossLevel8(int x, int y) {
        super(x, y, 100);
        this.exactX = x;
        this.exactY = y;
        this.image = new ImageIcon("src/images/boss8.png").getImage();
    }

    @Override
    public void move() {
        if (movingRight) {
            exactX += 2.5;
            if (exactX > 600) movingRight = false;
        } else {
            exactX -= 2.5;
            if (exactX < 50) movingRight = true;
        }

        if (movingDown) {
            exactY += 1.5;
            if (exactY > 150) movingDown = false;
        } else {
            exactY -= 1.5;
            if (exactY < 20) movingDown = true;
        }

        this.x = (int) exactX;
        this.y = (int) exactY;
    }
}