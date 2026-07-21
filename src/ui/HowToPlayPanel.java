package ui;

import javax.swing.*;
import java.awt.*;

public class HowToPlayPanel {
    private JPanel panel;

    public HowToPlayPanel(GameMain gameMain) {
        panel = new JPanel();
        panel.setBounds(0, 0, 800, 600);
        panel.setBackground(Color.DARK_GRAY);
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("How to Play");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setBounds(320, 30, 200, 40);
        panel.add(titleLabel);

        JTextArea instructions = new JTextArea();
        instructions.setEditable(false);
        instructions.setFont(new Font("Arial", Font.PLAIN, 18));
        instructions.setBackground(new Color(240, 240, 240));
        instructions.setMargin(new Insets(10, 10, 10, 10));

        String text = "CONTROLS:\n"
                + "  W / Up Arrow    : Move Up\n"
                + "  S / Down Arrow  : Move Down\n"
                + "  A / Left Arrow  : Move Left\n"
                + "  D / Right Arrow : Move Right\n"
                + "  Space           : Shoot\n"
                + "  P               : Pause / Resume\n"
                + "  Esc             : Exit to Menu\n\n"
                + "RULES:\n"
                + "  - Destroy all chickens to advance to the next level.\n"
                + "  - Dodge the eggs and enemies, or you will lose a life.\n"
                + "  - Collect Power-Ups (Add Fire, Rapid Fire, Shield, etc.).\n"
                + "  - Defeat the Bosses at Level 4 and Level 8 to win the game!";

        instructions.setText(text);

        JScrollPane scrollPane = new JScrollPane(instructions);
        scrollPane.setBounds(150, 100, 500, 350);
        panel.add(scrollPane);

        JButton backButton = new JButton("Back");
        backButton.setBounds(350, 480, 100, 40);
        backButton.addActionListener(e -> gameMain.showPanel(gameMain.getMainMenu().getPanel()));
        panel.add(backButton);
    }

    public JPanel getPanel() {
        return panel;
    }
}