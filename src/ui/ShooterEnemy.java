package ui;

import javax.swing.ImageIcon;

public class ShooterEnemy extends Enemy {

    public ShooterEnemy(int x, int y) {
        super(x, y, 1, 1);
        this.image = new ImageIcon("src/images/shooter_chicken.png").getImage();
    }

    @Override
    public void move() {

    }
}