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
    private Cell[][] grid;

    private long lastShootTime = 0;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean spacePressed = false;

    private double gridX = 50;
    private double gridY = 30;
    private double gridSpeed = 1.0;
    private boolean gridMovingRight = true;

    public GamePanel(GameMain gameMain) {
        this.gameMain = gameMain;
        this.plane = new Plane(370, 480);
        this.bullets = new ArrayList<>();
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
            }
        };

        panel.setBounds(0, 0, 800, 600);
        panel.setBackground(Color.BLACK);
        panel.setFocusable(true);

        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
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
                if (leftPressed) plane.moveLeft();
                if (rightPressed) plane.moveRight();

                if (spacePressed) {
                    long currentTime = System.currentTimeMillis();
                    if (currentTime - lastShootTime >= 300) {
                        bullets.add(new Bullet(plane.getX() + 22, plane.getY()));
                        lastShootTime = currentTime;
                    }
                }

                for (int i = 0; i < bullets.size(); i++) {
                    Bullet b = bullets.get(i);
                    b.move();
                    if (b.getY() < 0) {
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