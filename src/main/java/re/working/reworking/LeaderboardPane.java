package re.working.reworking;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class LeaderboardPane extends VBox {

    public void display(Stage stage) {
        Scene scene = new Scene(this, 300, 500); // Set dimensions as you like
        stage.setTitle("Leaderboard");
        stage.setScene(scene);
        stage.show();
    }

    public LeaderboardPane() {
        this(10); // Default to showing top 10 scores
    }

    public LeaderboardPane(int topN) {
        super(10); // Spacing between items
        refreshLeaderboard(topN);
    }

    public void refreshLeaderboard(int topN) {
        this.getChildren().clear(); // Clear previous leaderboard entries

        List<JavaSQL.UserScore> topScores = JavaSQL.getTopScores(topN);
        for (JavaSQL.UserScore userScore : topScores) {
            Label scoreLabel = new Label(userScore.getUsername() + ": " + userScore.getScore());
            this.getChildren().add(scoreLabel);
        }
    }
}

