package ui;
import javax.swing.*;


public class GameMain {

    private JFrame frame;
    private MainMenu mainMenu;
    private LoginPanel loginPanel;

    public GameMain() {
        frame = new JFrame("Chicken Invaders - Amin Dashti - 40413010");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setLayout(null);

        mainMenu = new MainMenu(this);
        loginPanel = new LoginPanel(this);

        frame.add(mainMenu.getPanel());
        frame.setVisible(true);
    }

    public void showPanel(JPanel panel) {
        frame.getContentPane().removeAll();
        frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }

    public MainMenu getMainMenu() {
        return mainMenu;
    }

    public LoginPanel getLoginPanel() {
        return loginPanel;
    }

    public static void main(String[] args) {
        new GameMain();
    }
}