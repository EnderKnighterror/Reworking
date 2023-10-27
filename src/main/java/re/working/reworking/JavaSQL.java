package re.working.reworking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JavaSQL {

    // environment variables would be used in a production application
    private static final String SQLurl = "jdbc:mysql://localhost:3306/twoupremastered?autoReconnect=true&useSSL=false";
    private static final String SQLuser = "root";
    private static final String SQLpassword = "root";

    public static User getUserByUsername(String authUsername) {
        User user = null;
        String sql = "SELECT * FROM twoupremastered.users WHERE username = ?";

        try (
                Connection myConn = DriverManager.getConnection(SQLurl, SQLuser, SQLpassword);
                PreparedStatement pstmt = myConn.prepareStatement(sql)
        ) {
            pstmt.setString(1, authUsername);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String User_id = rs.getString("User_id");
                    String username = rs.getString("username");
                    String passwordHash = rs.getString("passwordHash");

                    user = new User(User_id, username, passwordHash);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;
    }


    public static void addUser(User user) {
        String sql = "INSERT INTO twoupremastered.users (username, passwordHash) VALUES (?, ?)";

        try (
                Connection myConn = DriverManager.getConnection(SQLurl, SQLuser, SQLpassword);
                PreparedStatement pstmt = myConn.prepareStatement(sql)
        ) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPasswordHash());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("User added successfully!");
            } else {
                System.out.println("Failed to add user.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean checkDuplicateUser(String username) {
        boolean isDuplicate = false;
        // Query to count how many users have the same username
        String sql = "SELECT COUNT(*) FROM twoupremastered.users WHERE username = ?";

        try (
                Connection myConn = DriverManager.getConnection(SQLurl, SQLuser, SQLpassword);
                PreparedStatement pstmt = myConn.prepareStatement(sql)
        ) {
            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Retrieve the count
                    int count = rs.getInt(1);
                    if (count > 0) {
                        // If count is greater than 0, it means a user with the same username already exists
                        isDuplicate = true;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return isDuplicate;
    }

    public static void saveScore(String userId, int newScore) {
        int existingScore = getScoreByUserId(userId);

        if (existingScore > 0) {
            // Update the existing score
            String sqlUpdate = "UPDATE twoupremastered.leaderboard SET score = ? WHERE user_id = ?";

            try (
                    Connection myConn = DriverManager.getConnection(SQLurl, SQLuser, SQLpassword);
                    PreparedStatement pstmt = myConn.prepareStatement(sqlUpdate)
            ) {
                pstmt.setInt(1, existingScore + newScore);
                pstmt.setString(2, userId);
                pstmt.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            // Insert a new entry for the user
            String sqlInsert = "INSERT INTO twoupremastered.leaderboard (user_id, score) VALUES (?, ?)";

            try (
                    Connection myConn = DriverManager.getConnection(SQLurl, SQLuser, SQLpassword);
                    PreparedStatement pstmt = myConn.prepareStatement(sqlInsert)
            ) {
                pstmt.setString(1, userId);
                pstmt.setInt(2, newScore);
                pstmt.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static List<UserScore> getTopScores(int limit) {
        List<UserScore> topScores = new ArrayList<>();

        String sql = "SELECT u.username, l.score FROM twoupremastered.leaderboard l " +
                "JOIN twoupremastered.users u ON l.user_id = u.User_id " +
                "ORDER BY l.score DESC LIMIT ?";

        try (
                Connection myConn = DriverManager.getConnection(SQLurl, SQLuser, SQLpassword);
                PreparedStatement pstmt = myConn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, limit);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String username = rs.getString("username");
                    int score = rs.getInt("score");
                    topScores.add(new UserScore(username, score));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return topScores;
    }

    public static class UserScore {
        private final String username;
        private final int score;

        public UserScore(String username, int score) {
            this.username = username;
            this.score = score;
        }

        public String getUsername() {
            return username;
        }

        public int getScore() {
            return score;
        }
    }
    public static int getScoreByUserId(String userId) {
        String sql = "SELECT score FROM twoupremastered.leaderboard WHERE user_id = ?";
        int existingScore = 0;

        try (
                Connection myConn = DriverManager.getConnection(SQLurl, SQLuser, SQLpassword);
                PreparedStatement pstmt = myConn.prepareStatement(sql)
        ) {
            pstmt.setString(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    existingScore = rs.getInt("score");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return existingScore;
    }

}