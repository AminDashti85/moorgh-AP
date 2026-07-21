package ui;

import database.DatabaseManager;
import database.User;
import javax.swing.*;
import java.awt.*;

public class SettingsPanel {
    private JPanel panel;
    private JCheckBox bgmCheck;
    private JCheckBox shotCheck;
    private JCheckBox crashCheck;
    private JCheckBox gameOverCheck;
    private GameMain gameMain;

    public SettingsPanel(GameMain gameMain) {
        this.gameMain = gameMain;
        panel = new JPanel();
        panel.setBounds(0, 0, 800, 600);
        panel.setBackground(Color.DARK_GRAY);
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Sound Settings");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(310, 50, 200, 40);
        panel.add(titleLabel);

        bgmCheck = new JCheckBox("Background Music");
        bgmCheck.setBounds(300, 150, 250, 30);
        bgmCheck.setBackground(Color.DARK_GRAY);
        bgmCheck.setForeground(Color.WHITE);

        shotCheck = new JCheckBox("Shot Sound");
        shotCheck.setBounds(300, 200, 250, 30);
        shotCheck.setBackground(Color.DARK_GRAY);
        shotCheck.setForeground(Color.WHITE);

        crashCheck = new JCheckBox("Crash / Explosion Sound");
        crashCheck.setBounds(300, 250, 250, 30);
        crashCheck.setBackground(Color.DARK_GRAY);
        crashCheck.setForeground(Color.WHITE);

        gameOverCheck = new JCheckBox("Game Over / Win Sound");
        gameOverCheck.setBounds(300, 300, 250, 30);
        gameOverCheck.setBackground(Color.DARK_GRAY);
        gameOverCheck.setForeground(Color.WHITE);

        JButton saveButton = new JButton("Save");
        saveButton.setBounds(300, 380, 100, 40);

        JButton backButton = new JButton("Back");
        backButton.setBounds(410, 380, 100, 40);

        saveButton.addActionListener(e -> saveSettings());
        backButton.addActionListener(e -> gameMain.showPanel(gameMain.getMainMenu().getPanel()));

        panel.add(bgmCheck);
        panel.add(shotCheck);
        panel.add(crashCheck);
        panel.add(gameOverCheck);
        panel.add(saveButton);
        panel.add(backButton);
    }

    public void loadSettings() {
        String username = gameMain.getLoggedInUsername();
        if (username != null) {
            DatabaseManager db = new DatabaseManager();
            User user = db.getUser(username);
            if (user != null) {
                String[] parts = user.getSoundSettings().split(",");
                if (parts.length == 4) {
                    bgmCheck.setSelected(parts[0].equals("1"));
                    shotCheck.setSelected(parts[1].equals("1"));
                    crashCheck.setSelected(parts[2].equals("1"));
                    gameOverCheck.setSelected(parts[3].equals("1"));
                }
            }
        } else {
            bgmCheck.setSelected(true);
            shotCheck.setSelected(true);
            crashCheck.setSelected(true);
            gameOverCheck.setSelected(true);
        }
    }

    private void saveSettings() {
        String settings = (bgmCheck.isSelected() ? "1" : "0") + "," +
                (shotCheck.isSelected() ? "1" : "0") + "," +
                (crashCheck.isSelected() ? "1" : "0") + "," +
                (gameOverCheck.isSelected() ? "1" : "0");

        gameMain.getSoundManager().updateSettings(settings);

        String username = gameMain.getLoggedInUsername();
        if (username != null) {
            DatabaseManager db = new DatabaseManager();
            db.updateSoundSettings(username, settings);
            JOptionPane.showMessageDialog(panel, "Settings saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(panel, "You must be logged in to save settings permanently.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    public JPanel getPanel() {
        return panel;
    }
}