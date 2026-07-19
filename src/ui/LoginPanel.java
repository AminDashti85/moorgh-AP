package ui;

import database.DatabaseManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel {

    private JPanel panel;
    private DatabaseManager dbManager;

    public LoginPanel(GameMain gameMain) {
        dbManager = new DatabaseManager();

        panel = new JPanel();
        panel.setBounds(0, 0, 800, 600);
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(250, 200, 100, 30);

        JTextField userField = new JTextField();
        userField.setBounds(350, 200, 200, 30);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(250, 250, 100, 30);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(350, 250, 200, 30);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(250, 320, 140, 40);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(410, 320, 140, 40);

        JButton backButton = new JButton("Back to Menu");
        backButton.setBounds(330, 380, 140, 40);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passField.getPassword());

                if (dbManager.loginUser(username, password)) {
                    GamePanel gamePanel = new GamePanel(gameMain);
                    gameMain.showPanel(gamePanel.getPanel());
                    gamePanel.getPanel().requestFocusInWindow();
                } else {
                    JOptionPane.showMessageDialog(panel, "Invalid Username or Password!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passField.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Fields cannot be empty!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (dbManager.registerUser(username, password)) {
                    JOptionPane.showMessageDialog(panel, "Registration Successful! You can now login.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(panel, "Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameMain.showPanel(gameMain.getMainMenu().getPanel());
            }
        });

        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(loginButton);
        panel.add(registerButton);
        panel.add(backButton);
    }

    public JPanel getPanel() {
        return panel;
    }
}