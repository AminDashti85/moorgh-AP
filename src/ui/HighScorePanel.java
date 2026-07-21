package ui;

import database.DatabaseManager;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class HighScorePanel {
    private JPanel panel;
    private GameMain gameMain;
    private JTextArea scoreArea;

    public HighScorePanel(GameMain gameMain) {
        this.gameMain = gameMain;
        panel = new JPanel();
        panel.setBounds(0, 0, 800, 600);
        panel.setBackground(Color.DARK_GRAY);
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("High Scores");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setBounds(320, 30, 200, 40);
        panel.add(titleLabel);

        scoreArea = new JTextArea();
        scoreArea.setEditable(false);
        scoreArea.setFont(new Font("Monospaced", Font.BOLD, 18));
        scoreArea.setBackground(new Color(240, 240, 240));

        JScrollPane scrollPane = new JScrollPane(scoreArea);
        scrollPane.setBounds(150, 100, 500, 350);
        panel.add(scrollPane);

        JButton backButton = new JButton("Back");
        backButton.setBounds(350, 480, 100, 40);
        backButton.addActionListener(e -> gameMain.showPanel(gameMain.getMainMenu().getPanel()));
        panel.add(backButton);
    }

    public void loadScores() {
        DatabaseManager db = new DatabaseManager();
        ArrayList<String[]> scores = db.getHighScores();
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%-20s %-15s %-15s\n", "Username", "High Score", "Level"));
        sb.append("====================================================\n");

        for (String[] row : scores) {
            sb.append(String.format("%-20s %-15s %-15s\n", row[0], row[1], row[2]));
        }
        scoreArea.setText(sb.toString());
    }

    public JPanel getPanel() {
        return panel;
    }
}