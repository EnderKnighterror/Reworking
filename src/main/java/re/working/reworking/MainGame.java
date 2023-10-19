package re.working.reworking;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class MainGame extends Application {

    private final CoinAnimation coinAnimation1 = new CoinAnimation();
    private final CoinAnimation coinAnimation2 = new CoinAnimation();
    private final GameLogic gameLogic = new GameLogic();
    private final Leaderboard leaderboard = new Leaderboard();
    private String currentUser;

    @Override
    public void start(Stage primaryStage) {
        VBox mainLayout = new VBox(20);
        mainLayout.setAlignment(Pos.CENTER);

        HBox coinBox = new HBox(20);
        coinBox.getChildren().addAll(coinAnimation1.getCoin(), coinAnimation2.getCoin());
        coinBox.setAlignment(Pos.CENTER);

        HBox buttonsBox = new HBox(20);
        Button headsButton = new Button("Guess Heads");
        Button tailsButton = new Button("Guess Tails");

        headsButton.setOnAction(e -> playGame(true));
        tailsButton.setOnAction(e -> playGame(false));

        buttonsBox.getChildren().addAll(headsButton, tailsButton);
        buttonsBox.setAlignment(Pos.CENTER);

        mainLayout.getChildren().addAll(coinBox, buttonsBox, leaderboard.getScoreView());

        Scene scene = new Scene(mainLayout, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Two-Up Game");
        primaryStage.show();
    }
    public MainGame(String username) {
        this.currentUser = username;
    }

    private void playGame(boolean guessedHeads) {
        if (gameLogic.checkGuess(guessedHeads)) {
            leaderboard.addScore(currentUser, 1);
        }
    }

    public void launchGame() {
        Stage gameStage = new Stage();
        start(gameStage);
    }
}
