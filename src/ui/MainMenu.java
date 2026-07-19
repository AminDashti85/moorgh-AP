package ui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu {
    private JPanel panel;

    public MainMenu(GameMain gameMain) {
        panel = new JPanel();
        panel.setBounds(0, 0, 800, 600);
        panel.setBackground(Color.DARK_GRAY);
        panel.setLayout(null);

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

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameMain.showPanel(gameMain.getLoginPanel().getPanel());
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        panel.add(newGameButton);
        panel.add(highScoresButton);
        panel.add(settingsButton);
        panel.add(howToPlayButton);
        panel.add(exitButton);
    }

    public JPanel getPanel() {
        return panel;
    }
}