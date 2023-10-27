package re.working.reworking;

public class User {
    private String User_id;
    private String username;
    private String passwordHash;

    @Override
    public String toString() {
        return "User{" +
                "User_id=" + User_id +
                ", username='" + username + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                '}';
    }

    public User(String User_id, String username, String passwordHash) {
        this.User_id = User_id;
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public User(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public String getUser_id() {
        return User_id;
    }

    public void setUser_id(String User_id) {
        this.User_id = User_id;
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
