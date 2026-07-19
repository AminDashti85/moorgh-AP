package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel {

    private JPanel panel;
    private GameMain gameMain;
    private Timer timer;

    private int playerX = 370;
    private int playerY = 480;

    public GamePanel(GameMain gameMain) {
        this.gameMain = gameMain;

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.CYAN);
                g.fillRect(playerX, playerY, 50, 50);
            }
        };

        panel.setBounds(0, 0, 800, 600);
        panel.setBackground(Color.BLACK);
        panel.setFocusable(true);

        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();

                if (key == KeyEvent.VK_LEFT) {
                    playerX -= 15;
                } else if (key == KeyEvent.VK_RIGHT) {
                    playerX += 15;
                }

                if (playerX < 0) playerX = 0;
                if (playerX > 730) playerX = 730;
            }
        });

        timer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.repaint();
            }
        });
        timer.start();
    }

    public JPanel getPanel() {
        return panel;
    }
}