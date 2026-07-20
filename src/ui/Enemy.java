package ui;

import java.awt.Image;

public abstract class Enemy {
    protected int x;
    protected int y;
    protected int hp;
    protected int speed;
    protected Image image;

    public Enemy(int x, int y, int hp, int speed) {
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.speed = speed;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }

    public Image getImage() { return image; }

    public void takeDamage(int damage) {
        this.hp -= damage;
    }

    public boolean isDead() {
        return hp <= 0;
    }

    public abstract void move();
}