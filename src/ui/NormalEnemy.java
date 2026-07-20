package ui;

import javax.swing.ImageIcon;

public class NormalEnemy extends Enemy {

    public NormalEnemy(int x, int y) {
        super(x, y, 1, 1);
        this.image = new ImageIcon("src/images/normal_chicken.png").getImage();
    }

    @Override
    public void move() {

    }
}