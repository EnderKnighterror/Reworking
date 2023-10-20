package re.working.reworking;

import java.sql.*;

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
                    String userId = rs.getString("userId");
                    String username = rs.getString("username");
                    String passwordHash = rs.getString("passwordHash");

                    user = new User(userId, username, passwordHash);
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


}