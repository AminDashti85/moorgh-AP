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
    private Cell[][] grid;

    private long lastShootTime = 0;
    private long lastEnemyShootTime = 0;

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
        this.grid = new Cell[5][8];

        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 8; col++) {
                int startX = (int)gridX + (col * 80);
                int startY = (int)gridY + (row * 60);
                Enemy initialEnemy;

                if (row == 0) initialEnemy = new ShooterEnemy(startX, startY);
                else if (row == 1) initialEnemy = new ZigzagEnemy(startX, startY);
                else if (row == 2) initialEnemy = new FastEnemy(startX, startY);
                else initialEnemy = new NormalEnemy(startX, startY);

                grid[row][col] = new Cell(startX, startY, 2, initialEnemy);
            }
        }

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.drawImage(plane.getImage(), plane.getX(), plane.getY(), 50, 50, null);

                g.setColor(Color.YELLOW);
                for (Bullet bullet : bullets) {
                    g.fillRect(bullet.getX(), bullet.getY(), 6, 15);
                }

                for (int row = 0; row < 5; row++) {
                    for (int col = 0; col < 8; col++) {
                        Cell cell = grid[row][col];
                        if (cell.hasEnemy()) {
                            Enemy e = cell.getCurrentEnemy();
                            g.drawImage(e.getImage(), e.getX(), e.getY(), 40, 40, null);
                        }
                    }
                }

                for (Egg egg : eggs) {
                    g.drawImage(egg.getImage(), egg.getX(), egg.getY(), 15, 20, null);
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

                if (plane.getLives() <= 0) {
                    g.setFont(new Font("Arial", Font.BOLD, 40));
                    g.setColor(Color.RED);
                    g.drawString("GAME OVER", 280, 300);
                }
            }
        };

        panel.setBounds(0, 0, 800, 600);
        panel.setBackground(Color.BLACK);
        panel.setFocusable(true);

        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (plane.getLives() <= 0) return;
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
                if (plane.getLives() <= 0) {
                    return;
                }

                if (leftPressed) plane.moveLeft();
                if (rightPressed) plane.moveRight();

                long currentTime = System.currentTimeMillis();

                if (spacePressed) {
                    if (currentTime - lastShootTime >= 300) {
                        bullets.add(new Bullet(plane.getX() + 22, plane.getY()));
                        lastShootTime = currentTime;
                    }
                }

                if (currentTime - lastEnemyShootTime >= 3000) {
                    ArrayList<Enemy> activeEnemies = new ArrayList<>();
                    for (int row = 0; row < 5; row++) {
                        for (int col = 0; col < 8; col++) {
                            if (grid[row][col].hasEnemy()) {
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
                    egg.move();

                    Rectangle eggRect = new Rectangle(egg.getX(), egg.getY(), 15, 20);

                    if (eggRect.intersects(planeRect)) {
                        plane.decreaseLife();
                        explosions.add(new Explosion(plane.getX(), plane.getY()));
                        eggs.remove(i);
                        i--;
                        continue;
                    }

                    if (egg.getY() > 600) {
                        eggs.remove(i);
                        i--;
                    }
                }

                for (int i = 0; i < bullets.size(); i++) {
                    Bullet b = bullets.get(i);
                    b.move();
                    boolean bulletHit = false;

                    Rectangle bulletRect = new Rectangle(b.getX(), b.getY(), 6, 15);

                    for (int row = 0; row < 5; row++) {
                        for (int col = 0; col < 8; col++) {
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

                                        cell.decreaseCounter();
                                        cell.setCurrentEnemy(null);
                                    }
                                    break;
                                }
                            }
                        }
                        if (bulletHit) break;
                    }

                    if (bulletHit || b.getY() < 0) {
                        bullets.remove(i);
                        i--;
                    }
                }

                boolean hitEdge = false;
                if (gridMovingRight) {
                    gridX += gridSpeed;
                    if (gridX + (7 * 80) + 40 >= 780) {
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

                for (int row = 0; row < 5; row++) {
                    for (int col = 0; col < 8; col++) {
                        Cell cell = grid[row][col];
                        cell.setX((int)gridX + (col * 80));
                        cell.setY((int)gridY + (row * 60));

                        if (cell.hasEnemy()) {
                            Enemy enemy = cell.getCurrentEnemy();
                            enemy.setX(cell.getX());
                            enemy.setY(cell.getY());
                            enemy.move();
                        }
                    }
                }

                panel.repaint();
            }
        });
        timer.start();
    }

    public JPanel getPanel() {
        return panel;
    }
}