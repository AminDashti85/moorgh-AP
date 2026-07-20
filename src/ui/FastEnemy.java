package ui;

import javax.swing.ImageIcon;

public class FastEnemy extends Enemy {

    public FastEnemy(int x, int y) {
        super(x, y, 1, 2);
        this.image = new ImageIcon("src/images/fast_chicken.png").getImage();
    }

    @Override
    public void move() {

    }
}