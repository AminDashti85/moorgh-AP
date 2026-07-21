package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseManager {

    private static final String URL = "jdbc:sqlite:game.db";

    public DatabaseManager() {
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {

            String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                    "username TEXT PRIMARY KEY," +
                    "password TEXT NOT NULL," +
                    "high_score INTEGER DEFAULT 0," +
                    "last_level INTEGER DEFAULT 1," +
                    "sound_settings TEXT DEFAULT '1,1,1,1'" +
                    ");";
            stmt.execute(createUsersTable);

            String createHistoryTable = "CREATE TABLE IF NOT EXISTS history (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT," +
                    "score INTEGER," +
                    "level_reached INTEGER," +
                    "play_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "sound_settings TEXT," +
                    "FOREIGN KEY(username) REFERENCES users(username)" +
                    ");";
            stmt.execute(createHistoryTable);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean registerUser(String username, String password) {
        String sql = "INSERT INTO users(username, password) VALUES(?, ?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean loginUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            return false;
        }
    }

    public User getUser(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getString("username"),
                        rs.getInt("high_score"),
                        rs.getInt("last_level"),
                        rs.getString("sound_settings")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveGameRecord(String username, int score, int levelReached, String soundSettings) {
        String insertHistory = "INSERT INTO history(username, score, level_reached, sound_settings) VALUES(?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(insertHistory)) {
            pstmt.setString(1, username);
            pstmt.setInt(2, score);
            pstmt.setInt(3, levelReached);
            pstmt.setString(4, soundSettings);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        User user = getUser(username);
        if (user != null) {
            if (score >= user.getHighScore()) {
                String updateScore = "UPDATE users SET high_score = ?, last_level = ? WHERE username = ?";
                try (Connection conn = DriverManager.getConnection(URL);
                     PreparedStatement pstmt = conn.prepareStatement(updateScore)) {
                    pstmt.setInt(1, score);
                    pstmt.setInt(2, levelReached);
                    pstmt.setString(3, username);
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void updateSoundSettings(String username, String settings) {
        String sql = "UPDATE users SET sound_settings = ? WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, settings);
            pstmt.setString(2, username);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String[]> getHighScores() {
        ArrayList<String[]> highScores = new ArrayList<>();
        String sql = "SELECT h.username, h.score, h.level_reached " +
                "FROM history h " +
                "JOIN (SELECT username, MAX(score) as max_score FROM history GROUP BY username) m " +
                "ON h.username = m.username AND h.score = m.max_score " +
                "GROUP BY h.username " +
                "ORDER BY h.score DESC, h.level_reached DESC LIMIT 10";
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String[] row = {
                        rs.getString("username"),
                        String.valueOf(rs.getInt("score")),
                        String.valueOf(rs.getInt("level_reached"))
                };
                highScores.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return highScores;
    }
}