package re.working.reworking;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserManager {

    private static final String FILE_NAME = "users.txt";
    private static boolean isLoggedIn = false;

    public static boolean authenticate(String username, String password) {
        Map<String, String> users = getUsersFromFile();
        if (users.containsKey(username) && users.get(username).equals(password)) {
            isLoggedIn = true;
            return true;
        }
        return false;
    }
    public static boolean isUserLoggedIn() {
        return isLoggedIn;
    }
    public static void register(String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(username + ":" + password);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<String, String> getUsersFromFile() {
        Map<String, String> users = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                users.put(parts[0], parts[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
}
