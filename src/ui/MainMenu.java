package ui;

import javax.swing.*;
import java.awt.*;

public class MainMenu {
    private JPanel panel;
    private JLabel statusLabel;
    private GameMain gameMain;

    public MainMenu(GameMain gameMain) {
        this.gameMain = gameMain;
        panel = new JPanel();
        panel.setBounds(0, 0, 800, 600);
        panel.setBackground(Color.DARK_GRAY);
        panel.setLayout(null);

        statusLabel = new JLabel("Not logged in");
        statusLabel.setBounds(20, 20, 300, 30);
        statusLabel.setForeground(Color.YELLOW);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(statusLabel);

        JButton newGameButton = new JButton("New Game");
        newGameButton.setBounds(300, 150, 200, 50);

        JButton highScoresButton = new JButton("High Scores");
        highScoresButton.setBounds(300, 220, 200, 50);

        JButton settingsButton = new JButton("Settings");
        settingsButton.setBounds(300, 290, 200, 50);

        JButton howToPlayButton = new JButton("How to Play");
        howToPlayButton.setBounds(300, 360, 200, 50);

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(300, 430, 200, 50);

        newGameButton.addActionListener(e -> {
            if (gameMain.getLoggedInUsername() == null) {
                gameMain.showPanel(gameMain.getLoginPanel().getPanel());
            } else {
                GamePanel gamePanel = new GamePanel(gameMain);
                gameMain.showPanel(gamePanel.getPanel());
                gamePanel.getPanel().requestFocusInWindow();
            }
        });

        highScoresButton.addActionListener(e -> {
            gameMain.getHighScorePanel().loadScores();
            gameMain.showPanel(gameMain.getHighScorePanel().getPanel());
        });

        settingsButton.addActionListener(e -> {
            gameMain.getSettingsPanel().loadSettings();
            gameMain.showPanel(gameMain.getSettingsPanel().getPanel());
        });

        howToPlayButton.addActionListener(e -> {
            gameMain.showPanel(gameMain.getHowToPlayPanel().getPanel());
        });

        exitButton.addActionListener(e -> System.exit(0));

        panel.add(newGameButton);
        panel.add(highScoresButton);
        panel.add(settingsButton);
        panel.add(howToPlayButton);
        panel.add(exitButton);
    }

    public void updateLoginStatus() {
        if (gameMain.getLoggedInUsername() != null) {
            statusLabel.setText("Logged in as: " + gameMain.getLoggedInUsername());
            statusLabel.setForeground(Color.GREEN);
        } else {
            statusLabel.setText("Not logged in");
            statusLabel.setForeground(Color.YELLOW);
        }
    }

    public JPanel getPanel() {
        return panel;
    }
}