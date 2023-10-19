package re.working.reworking;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import java.io.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javafx.util.Pair;


import java.io.PrintWriter;

public class Leaderboard {

    private final ObservableList<String> userScores;
    private final String filename = "Score.txt";

    public Leaderboard() {

        this.userScores = FXCollections.observableArrayList();
        loadScoresFromFile();
    }

    public void addScore(String username, int scoreIncrement) {
        int existingScore = 0;
        String userEntry = null;
        for (String entry : userScores) {
            String[] parts = entry.split(":");
            if (parts[0].equals(username)) {
                existingScore += Integer.parseInt(parts[1]);
                userEntry = entry;
                break;
            }
        }
        if (userEntry !=null) {
            userScores.remove(userEntry);
        }
        int newScore = existingScore + scoreIncrement;
        userScores.add(username + ":" + newScore);
        saveScoresToFile();
        userScores.sort((a, b) -> Integer.parseInt(b.split(":")[1]) - Integer.parseInt(a.split(":")[1]));
    }

    public ListView<String> getScoreView() {
        return new ListView<>(userScores);
    }
    private void saveScoresToFile() {
        try (PrintWriter out = new PrintWriter(new FileWriter(filename))) {
            for (String entry : userScores) {
                out.println(entry);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadScoresFromFile() {
        File file = new File(filename);
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                List<String> loadedEntries = br.lines()
                        .filter(line -> line.contains(":") && line.split(":").length == 2)
                        .collect(Collectors.toList());

                userScores.addAll(loadedEntries);
                userScores.sort((a, b) -> Integer.parseInt(b.split(":")[1]) - Integer.parseInt(a.split(":")[1]));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

