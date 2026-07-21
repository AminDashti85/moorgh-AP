package ui;

import javax.swing.ImageIcon;

public class BossLevel4 extends Boss {
    private boolean movingRight = true;
    private double exactX;

    public BossLevel4(int x, int y) {
        super(x, y, 50);
        this.exactX = x;
        this.image = new ImageIcon("src/images/boss1.png").getImage();
    }

    @Override
    public void move() {
        if (movingRight) {
            exactX += 1.5;
            if (exactX > 600) movingRight = false;
        } else {
            exactX -= 1.5;
            if (exactX < 50) movingRight = true;
        }
        this.x = (int) exactX;
    }
}