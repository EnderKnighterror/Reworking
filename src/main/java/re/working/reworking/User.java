package re.working.reworking;

public class User {
    private String userId;
    private String username;
    private String passwordHash;

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                '}';
    }

    public User(String userId, String username, String passwordHash) {
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public User(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }






}
