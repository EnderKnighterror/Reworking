package re.working.reworking;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserManager {

    private static final String FILE_NAME = "users.txt";
    private static boolean isLoggedIn = false;
    private static String loggedInUser = null;

    public static boolean authenticate(String username, String password) {
        Map<String, String> users = getUsersFromFile();
        String hashedPassword = HashUtil.hashPassword(password);
        if (users.containsKey(username) && users.get(username).equals(hashedPassword)) {
            isLoggedIn = true;
            loggedInUser = username;
            return true;
        }
        return false;
    }
    public static String getLoggedInUser(){
        return loggedInUser;
    }
    public static boolean isUserLoggedIn() {
        return isLoggedIn;
    }
    public static void register(String username, String password) {
        ensureFileExists();
        String hashedPassword = HashUtil.hashPassword(password);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(username + ":" + hashedPassword);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to file");
            e.printStackTrace();
        }
    }

    private static Map<String, String> getUsersFromFile() {
        ensureFileExists();
        Map<String, String> users = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                users.put(parts[0], parts[1]);
            }
        } catch (IOException e) {
            System.out.println("Error reading from file");
            e.printStackTrace();
        }
        return users;
    }
    private static void ensureFileExists() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Error creating users.txt");
                e.printStackTrace();
            }
        }
    }
}
