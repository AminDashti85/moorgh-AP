package ui;

public class Cell {
    private int x;
    private int y;
    private int counter;
    private Enemy currentEnemy;

    public Cell(int x, int y, int counter, Enemy currentEnemy) {
        this.x = x;
        this.y = y;
        this.counter = counter;
        this.currentEnemy = currentEnemy;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }

    public int getCounter() { return counter; }
    public void decreaseCounter() { this.counter--; }

    public Enemy getCurrentEnemy() { return currentEnemy; }
    public void setCurrentEnemy(Enemy currentEnemy) { this.currentEnemy = currentEnemy; }

    public boolean hasEnemy() {
        return currentEnemy != null;
    }
}