package ui;

import javax.swing.ImageIcon;

public class ZigzagEnemy extends Enemy {

    private boolean movingRight = true;
    private int startX;


    public ZigzagEnemy(int x, int y) {
        super(x, y, 1, 1);
        this.startX = x;
        this.image = new ImageIcon("src/images/zigzag_chicken.png").getImage();
    }

    @Override
    public void move() {
        if (movingRight) {
            this.x += 2;
            if (this.x > startX + 30) movingRight = false;
        } else {
            this.x -= 2;
            if (this.x < startX - 30) movingRight = true;
        }
    }

    @Override
    public void setX(int x) {
        super.setX(x);
        this.startX = x;
    }
}