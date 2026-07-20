package ui;

public class Cell {
    private int x;
    private int y;
    private int counter;
    private Enemy currentEnemy;
    private boolean spawning;
    private double currentX;
    private double currentY;

    public Cell(int x, int y, int counter, Enemy currentEnemy) {
        this.x = x;
        this.y = y;
        this.counter = counter;
        this.currentEnemy = currentEnemy;
        this.spawning = false;
        if (currentEnemy != null) {
            this.currentX = x;
            this.currentY = y;
        }
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }

    public int getCounter() { return counter; }
    public void decreaseCounter() { this.counter--; }

    public Enemy getCurrentEnemy() { return currentEnemy; }

    public void setCurrentEnemy(Enemy currentEnemy) {
        this.currentEnemy = currentEnemy;
        this.spawning = false;
    }

    public boolean hasEnemy() {
        return currentEnemy != null;
    }

    public boolean isSpawning() { return spawning; }
    public void setSpawning(boolean spawning) { this.spawning = spawning; }

    public double getCurrentX() { return currentX; }
    public double getCurrentY() { return currentY; }

    public void setCurrentX(double currentX) { this.currentX = currentX; }
    public void setCurrentY(double currentY) { this.currentY = currentY; }

    public void spawnNewEnemy(Enemy newEnemy, int startX, int startY) {
        this.currentEnemy = newEnemy;
        this.currentX = startX;
        this.currentY = startY;
        this.spawning = true;
    }
}