package re.working.reworking;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



import java.util.List;

public class Leaderboard extends Application {

    private LeaderboardPane leaderboardPane;

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        Button showLeaderboardBtn = new Button("Show Leaderboard");
        leaderboardPane = new LeaderboardPane(); // our custom pane
        leaderboardPane.setVisible(false); // hide it initially

        showLeaderboardBtn.setOnAction(event -> {
            leaderboardPane.refreshLeaderboard(10); // refresh and show top 10 scores
            leaderboardPane.setVisible(true); // make it visible
        });

        root.getChildren().addAll(showLeaderboardBtn, leaderboardPane);

        Scene scene = new Scene(root, 300, 500);
        primaryStage.setTitle("Main Game Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
