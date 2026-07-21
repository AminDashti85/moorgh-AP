package ui;

import audio.SoundManager;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;

public class GameMain {

    private JFrame frame;
    private MainMenu mainMenu;
    private LoginPanel loginPanel;
    private SettingsPanel settingsPanel;
    private HighScorePanel highScorePanel;
    private HowToPlayPanel howToPlayPanel;
    private SoundManager soundManager;
    private String loggedInUsername = null;

    public GameMain() {
        frame = new JFrame("Chicken Invaders Clone");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().setPreferredSize(new Dimension(800, 600));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        soundManager = new SoundManager();
        mainMenu = new MainMenu(this);
        loginPanel = new LoginPanel(this);
        settingsPanel = new SettingsPanel(this);
        highScorePanel = new HighScorePanel(this);
        howToPlayPanel = new HowToPlayPanel(this);

        showPanel(mainMenu.getPanel());
        frame.setVisible(true);
    }

    public void showPanel(JPanel panel) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();
        panel.requestFocusInWindow();
    }

    public MainMenu getMainMenu() { return mainMenu; }
    public LoginPanel getLoginPanel() { return loginPanel; }
    public SettingsPanel getSettingsPanel() { return settingsPanel; }
    public HighScorePanel getHighScorePanel() { return highScorePanel; }
    public HowToPlayPanel getHowToPlayPanel() { return howToPlayPanel; }
    public SoundManager getSoundManager() { return soundManager; }
    public String getLoggedInUsername() { return loggedInUsername; }
    public void setLoggedInUsername(String username) { this.loggedInUsername = username; }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GameMain();
            }
        });
    }
}