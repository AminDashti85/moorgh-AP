package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GamePanel {

    private JPanel panel;
    private GameMain gameMain;
    private Timer timer;

    private Plane plane;
    private ArrayList<Bullet> bullets;
    private ArrayList<Explosion> explosions;
    private ArrayList<Egg> eggs;
    private ArrayList<PowerUp> powerUps;
    private Cell[][] grid;
    private Boss currentBoss = null;

    private long lastShootTime = 0;
    private long lastEnemyShootTime = 0;
    private long freezeEndTime = 0;

    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean spacePressed = false;

    private double gridX = 50;
    private double gridY = 50;
    private double gridSpeed = 1.0;
    private boolean gridMovingRight = true;

    private int score = 0;
    private int level = 1;

    public GamePanel(GameMain gameMain) {
        this.gameMain = gameMain;
        this.plane = new Plane(370, 480);
        this.bullets = new ArrayList<>();
        this.explosions = new ArrayList<>();
        this.eggs = new ArrayList<>();
        this.powerUps = new ArrayList<>();

        loadLevel();

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                if (plane.hasShield()) {
                    g.setColor(new Color(0, 255, 255, 100));
                    g.fillOval(plane.getX() - 10, plane.getY() - 10, 70, 70);
                }

                g.drawImage(plane.getImage(), plane.getX(), plane.getY(), 50, 50, null);

                g.setColor(Color.YELLOW);
                for (Bullet bullet : bullets) {
                    g.fillRect(bullet.getX(), bullet.getY(), 6, 15);
                }

                if (currentBoss != null) {
                    g.drawImage(currentBoss.getImage(), currentBoss.getX(), currentBoss.getY(), 150, 150, null);

                    g.setColor(Color.RED);
                    g.fillRect(200, 10, 400, 15);
                    g.setColor(Color.GREEN);
                    int hpWidth = (int)((currentBoss.getCurrentHp() / (double)currentBoss.getMaxHp()) * 400);
                    if (hpWidth < 0) hpWidth = 0;
                    g.fillRect(200, 10, hpWidth, 15);
                    g.setColor(Color.WHITE);
                    g.drawRect(200, 10, 400, 15);
                } else {
                    for (int row = 0; row < grid.length; row++) {
                        for (int col = 0; col < grid[0].length; col++) {
                            Cell cell = grid[row][col];
                            if (cell.hasEnemy()) {
                                Enemy e = cell.getCurrentEnemy();
                                g.drawImage(e.getImage(), e.getX(), e.getY(), 40, 40, null);
                            }
                        }
                    }
                }

                for (Egg egg : eggs) {
                    g.drawImage(egg.getImage(), egg.getX(), egg.getY(), 15, 20, null);
                }

                for (PowerUp pu : powerUps) {
                    g.drawImage(pu.getImage(), pu.getX(), pu.getY(), 30, 30, null);
                }

                for (Explosion exp : explosions) {
                    g.drawImage(exp.getImage(), exp.getX(), exp.getY(), 40, 40, null);
                }

                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.BOLD, 14));
                g.drawString("Score: " + score, 10, 20);
                g.drawString("Level: " + level, 120, 20);
                g.drawString("Lives: " + plane.getLives(), 220, 20);
                g.drawString("Fire Power: " + plane.getFirePower(), 320, 20);

                if (plane.hasRapidFire()) {
                    g.setColor(Color.ORANGE);
                    g.drawString("RAPID FIRE!", 450, 20);
                }
                if (System.currentTimeMillis() < freezeEndTime) {
                    g.setColor(Color.CYAN);
                    g.drawString("FROZEN!", 550, 20);
                }

                if (plane.getLives() <= 0) {
                    g.setFont(new Font("Arial", Font.BOLD, 40));
                    g.setColor(Color.RED);
                    g.drawString("GAME OVER", 280, 300);
                } else if (level > 8) {
                    g.setFont(new Font("Arial", Font.BOLD, 40));
                    g.setColor(Color.GREEN);
                    g.drawString("YOU WIN!", 300, 300);
                }
            }
        };

        panel.setBounds(0, 0, 800, 600);
        panel.setBackground(Color.BLACK);
        panel.setFocusable(true);

        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (plane.getLives() <= 0 || level > 8) return;
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_LEFT) leftPressed = true;
                if (key == KeyEvent.VK_RIGHT) rightPressed = true;
                if (key == KeyEvent.VK_SPACE) spacePressed = true;
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_LEFT) leftPressed = false;
                if (key == KeyEvent.VK_RIGHT) rightPressed = false;
                if (key == KeyEvent.VK_SPACE) spacePressed = false;
            }
        });

        timer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (plane.getLives() <= 0 || level > 8) {
                    return;
                }

                long currentTime = System.currentTimeMillis();
                boolean isFrozen = currentTime < freezeEndTime;

                if (leftPressed) plane.moveLeft();
                if (rightPressed) plane.moveRight();

                if (spacePressed) {
                    long shootCooldown = plane.hasRapidFire() ? 100 : 300;
                    if (currentTime - lastShootTime >= shootCooldown) {
                        int fp = plane.getFirePower();
                        int spacing = 15;
                        int startOffsetX = -((fp - 1) * spacing) / 2;

                        for (int i = 0; i < fp; i++) {
                            bullets.add(new Bullet(plane.getX() + 22 + startOffsetX + (i * spacing), plane.getY()));
                        }
                        lastShootTime = currentTime;
                    }
                }

                if (currentBoss != null) {
                    if (!isFrozen) {
                        currentBoss.move();
                        if (currentTime - lastEnemyShootTime >= 1500) {
                            int cx = currentBoss.getX() + 75;
                            int cy = currentBoss.getY() + 75;

                            if (currentBoss instanceof BossLevel4) {
                                eggs.add(new Egg(cx, cy + 75, 0, 4, false));
                                eggs.add(new Egg(cx, cy - 75, 0, -4, false));
                                eggs.add(new Egg(cx - 75, cy, -4, 0, false));
                                eggs.add(new Egg(cx + 75, cy, 4, 0, false));
                            } else {
                                eggs.add(new Egg(cx, cy + 75, 0, 5, true));
                                eggs.add(new Egg(cx, cy - 75, 0, -5, true));
                                eggs.add(new Egg(cx - 75, cy, -5, 0, true));
                                eggs.add(new Egg(cx + 75, cy, 5, 0, true));
                                eggs.add(new Egg(cx + 50, cy + 50, 4, 4, true));
                                eggs.add(new Egg(cx - 50, cy + 50, -4, 4, true));
                                eggs.add(new Egg(cx + 50, cy - 50, 4, -4, true));
                                eggs.add(new Egg(cx - 50, cy - 50, -4, -4, true));
                            }
                            lastEnemyShootTime = currentTime;
                        }
                    }
                } else if (!isFrozen && currentTime - lastEnemyShootTime >= 3000) {
                    ArrayList<Enemy> activeEnemies = new ArrayList<>();
                    for (int row = 0; row < grid.length; row++) {
                        for (int col = 0; col < grid[0].length; col++) {
                            if (grid[row][col].hasEnemy() && !grid[row][col].isSpawning()) {
                                activeEnemies.add(grid[row][col].getCurrentEnemy());
                            }
                        }
                    }
                    if (!activeEnemies.isEmpty()) {
                        int randomIndex = (int) (Math.random() * activeEnemies.size());
                        Enemy shooter = activeEnemies.get(randomIndex);

                        if (shooter instanceof ShooterEnemy) {
                            eggs.add(new Egg(shooter.getX() + 12, shooter.getY() + 30, 0, 5, true));
                        } else {
                            eggs.add(new Egg(shooter.getX() + 12, shooter.getY() + 30, 0, 4, false));
                        }
                        lastEnemyShootTime = currentTime;
                    }
                }

                for (int i = 0; i < explosions.size(); i++) {
                    Explosion exp = explosions.get(i);
                    exp.update();
                    if (exp.isFinished()) {
                        explosions.remove(i);
                        i--;
                    }
                }

                Rectangle planeRect = new Rectangle(plane.getX(), plane.getY(), 50, 50);

                for (int i = 0; i < eggs.size(); i++) {
                    Egg egg = eggs.get(i);
                    if (!isFrozen) egg.move();

                    Rectangle eggRect = new Rectangle(egg.getX(), egg.getY(), 15, 20);

                    if (eggRect.intersects(planeRect)) {
                        if (!plane.hasShield()) {
                            plane.decreaseLife();
                            explosions.add(new Explosion(plane.getX(), plane.getY()));
                        }
                        eggs.remove(i);
                        i--;
                        continue;
                    }

                    if (egg.getY() > 600 || egg.getY() < 0 || egg.getX() < 0 || egg.getX() > 800) {
                        eggs.remove(i);
                        i--;
                    }
                }

                for (int i = 0; i < powerUps.size(); i++) {
                    PowerUp pu = powerUps.get(i);
                    pu.move();
                    Rectangle puRect = new Rectangle(pu.getX(), pu.getY(), 30, 30);

                    if (puRect.intersects(planeRect)) {
                        switch (pu.getType()) {
                            case PowerUp.ADD_FIRE:
                                plane.increaseFirePower();
                                break;
                            case PowerUp.RAPID_FIRE:
                                plane.activateRapidFire(8000);
                                break;
                            case PowerUp.EXTRA_LIFE:
                                plane.addLife();
                                break;
                            case PowerUp.SHIELD:
                                plane.activateShield(10000);
                                break;
                            case PowerUp.FREEZE:
                                freezeEndTime = currentTime + 3000;
                                break;
                        }
                        powerUps.remove(i);
                        i--;
                        continue;
                    }

                    if (pu.getY() > 600) {
                        powerUps.remove(i);
                        i--;
                    }
                }

                for (int i = 0; i < bullets.size(); i++) {
                    Bullet b = bullets.get(i);
                    b.move();
                    boolean bulletHit = false;
                    Rectangle bulletRect = new Rectangle(b.getX(), b.getY(), 6, 15);

                    if (currentBoss != null) {
                        Rectangle bossRect = new Rectangle(currentBoss.getX(), currentBoss.getY(), 150, 150);
                        if (bulletRect.intersects(bossRect)) {
                            currentBoss.takeDamage(1);
                            bulletHit = true;
                            explosions.add(new Explosion(b.getX() - 15, b.getY() - 15));

                            if (currentBoss.getCurrentHp() <= 0) {
                                explosions.add(new Explosion(currentBoss.getX() + 50, currentBoss.getY() + 50));
                                score += 500;
                                currentBoss = null;
                            }
                        }
                    } else {
                        for (int row = 0; row < grid.length; row++) {
                            for (int col = 0; col < grid[0].length; col++) {
                                Cell cell = grid[row][col];
                                if (cell.hasEnemy()) {
                                    Enemy enemy = cell.getCurrentEnemy();
                                    Rectangle enemyRect = new Rectangle(enemy.getX(), enemy.getY(), 40, 40);

                                    if (bulletRect.intersects(enemyRect)) {
                                        enemy.takeDamage(1);
                                        bulletHit = true;

                                        if (enemy.isDead()) {
                                            explosions.add(new Explosion(enemy.getX(), enemy.getY()));

                                            if (enemy instanceof NormalEnemy) score += 10;
                                            else if (enemy instanceof FastEnemy) score += 15;
                                            else if (enemy instanceof ZigzagEnemy) score += 20;
                                            else if (enemy instanceof ShooterEnemy) score += 25;

                                            if (Math.random() < 0.20) {
                                                int type = (int)(Math.random() * 5);
                                                powerUps.add(new PowerUp(enemy.getX(), enemy.getY(), type));
                                            }

                                            cell.decreaseCounter();

                                            if (cell.getCounter() > 0) {
                                                Enemy newEnemy;
                                                if (enemy instanceof NormalEnemy) newEnemy = new NormalEnemy(0, 0);
                                                else if (enemy instanceof FastEnemy) newEnemy = new FastEnemy(0, 0);
                                                else if (enemy instanceof ZigzagEnemy) newEnemy = new ZigzagEnemy(0, 0);
                                                else newEnemy = new ShooterEnemy(0, 0);

                                                int startX = (Math.random() < 0.5) ? -40 : 800;
                                                int startY = -40;
                                                cell.spawnNewEnemy(newEnemy, startX, startY);
                                            } else {
                                                cell.setCurrentEnemy(null);
                                            }
                                        }
                                        break;
                                    }
                                }
                            }
                            if (bulletHit) break;
                        }
                    }

                    if (bulletHit || b.getY() < 0) {
                        bullets.remove(i);
                        i--;
                    }
                }

                if (currentBoss == null && !isFrozen) {
                    boolean hitEdge = false;
                    if (gridMovingRight) {
                        gridX += gridSpeed;
                        if (gridX + (grid[0].length * 80) + 40 >= 780) {
                            hitEdge = true;
                            gridMovingRight = false;
                        }
                    } else {
                        gridX -= gridSpeed;
                        if (gridX <= 20) {
                            hitEdge = true;
                            gridMovingRight = true;
                        }
                    }

                    if (hitEdge) {
                        gridY += 20;
                    }

                    for (int row = 0; row < grid.length; row++) {
                        for (int col = 0; col < grid[0].length; col++) {
                            Cell cell = grid[row][col];
                            cell.setX((int)gridX + (col * 80));
                            cell.setY((int)gridY + (row * 60));

                            if (cell.hasEnemy()) {
                                if (cell.isSpawning()) {
                                    double dx = cell.getX() - cell.getCurrentX();
                                    double dy = cell.getY() - cell.getCurrentY();
                                    double dist = Math.sqrt(dx * dx + dy * dy);

                                    if (dist < 5) {
                                        cell.setSpawning(false);
                                        Enemy enemy = cell.getCurrentEnemy();
                                        enemy.setX(cell.getX());
                                        enemy.setY(cell.getY());
                                    } else {
                                        Enemy enemy = cell.getCurrentEnemy();
                                        double speed = (enemy instanceof FastEnemy) ? 8.0 : 4.0;
                                        cell.setCurrentX(cell.getCurrentX() + (dx / dist) * speed);
                                        cell.setCurrentY(cell.getCurrentY() + (dy / dist) * speed);
                                        enemy.setX((int)cell.getCurrentX());
                                        enemy.setY((int)cell.getCurrentY());
                                    }
                                } else {
                                    Enemy enemy = cell.getCurrentEnemy();
                                    enemy.setX(cell.getX());
                                    enemy.setY(cell.getY());
                                    enemy.move();
                                }
                            }
                        }
                    }
                }

                boolean levelComplete = true;
                if (level == 4 || level == 8) {
                    if (currentBoss != null) levelComplete = false;
                } else {
                    for (int row = 0; row < grid.length; row++) {
                        for (int col = 0; col < grid[0].length; col++) {
                            if (grid[row][col].getCounter() > 0 || grid[row][col].hasEnemy() || grid[row][col].isSpawning()) {
                                levelComplete = false;
                                break;
                            }
                        }
                        if (!levelComplete) break;
                    }
                }

                if (levelComplete) {
                    if (level != 4 && level != 8) score += 200;
                    level++;
                    if (level <= 8) {
                        loadLevel();
                    }
                }

                panel.repaint();
            }
        });
        timer.start();
    }

    private void loadLevel() {
        gridX = 50;
        gridY = 50;
        gridMovingRight = true;
        bullets.clear();
        eggs.clear();
        powerUps.clear();
        explosions.clear();

        if (level == 4) {
            grid = new Cell[0][0];
            currentBoss = new BossLevel4(325, 50);
            gridSpeed = 0;
        } else if (level == 8) {
            grid = new Cell[0][0];
            currentBoss = new BossLevel8(325, 50);
            gridSpeed = 0;
        } else {
            currentBoss = null;
            grid = new Cell[5][8];
            int initialCounter = (level >= 5) ? 3 : (level == 3 ? 3 : 2);
            gridSpeed = 1.0 + (level * 0.2);

            for (int row = 0; row < 5; row++) {
                for (int col = 0; col < 8; col++) {
                    int startX = (int)gridX + (col * 80);
                    int startY = (int)gridY + (row * 60);
                    Enemy initialEnemy;

                    if (level >= 5) {
                        if (row == 0 || row == 1) initialEnemy = new ShooterEnemy(startX, startY);
                        else if (row == 2) initialEnemy = new ZigzagEnemy(startX, startY);
                        else initialEnemy = new FastEnemy(startX, startY);
                    } else {
                        if (row == 0) initialEnemy = new ShooterEnemy(startX, startY);
                        else if (row == 1) initialEnemy = new ZigzagEnemy(startX, startY);
                        else if (row == 2) initialEnemy = new FastEnemy(startX, startY);
                        else initialEnemy = new NormalEnemy(startX, startY);
                    }

                    grid[row][col] = new Cell(startX, startY, initialCounter, initialEnemy);
                }
            }
        }
    }

    public JPanel getPanel() {
        return panel;
    }
}