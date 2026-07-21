package ui;

public abstract class Boss extends Enemy {
    protected int maxHp;
    protected int currentHp;

    public Boss(int x, int y, int hp) {
        super(x, y, hp, 1);
        this.maxHp = hp;
        this.currentHp = hp;
    }

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);
        this.currentHp -= damage;
    }

    public int getMaxHp() { return maxHp; }
    public int getCurrentHp() { return currentHp; }
}