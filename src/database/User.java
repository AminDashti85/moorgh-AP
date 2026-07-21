package database;

public class User {
    private String username;
    private int highScore;
    private int lastLevel;
    private String soundSettings;

    public User(String username, int highScore, int lastLevel, String soundSettings) {
        this.username = username;
        this.highScore = highScore;
        this.lastLevel = lastLevel;
        this.soundSettings = soundSettings;
    }

    public String getUsername() { return username; }
    public int getHighScore() { return highScore; }
    public int getLastLevel() { return lastLevel; }
    public String getSoundSettings() { return soundSettings; }
}