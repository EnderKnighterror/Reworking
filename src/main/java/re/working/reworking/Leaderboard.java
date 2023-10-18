package re.working.reworking;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import java.io.*;
import java.util.List;
import java.util.stream.Collectors;


import java.io.PrintWriter;

public class Leaderboard {

    private final ObservableList<Integer> scores;
    private final String filename = "Score.txt";

    public Leaderboard() {

        this.scores = FXCollections.observableArrayList();
        loadScoresFromFile();
    }

    public void addScore(int score) {
        scores.add(score);
        scores.sort((a, b) -> b - a); // Descending order
        saveScoresToFile();
    }


    public ListView<Integer> getScoreView() {
        return new ListView<>(scores);
    }
    private void saveScoresToFile() {
        try (PrintWriter out = new PrintWriter(new FileWriter(filename))) {
            for (int score : scores) {
                out.println(score);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadScoresFromFile() {
        File file = new File(filename);
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                List<Integer> loadScores = br.lines()
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
                scores.addAll(loadScores);
                scores.sort((a, b) -> b - a);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

