package re.working.reworking;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameWindow extends Application {
    private final CoinAnimation coinAnimation1 = new CoinAnimation();
    private final CoinAnimation coinAnimation2 = new CoinAnimation();
    private final GameLogic gameLogic = new GameLogic();
    private final Leaderboard leaderboard = new Leaderboard();
    private String currentUser;
    private final Button playButton = new Button("Play Game");
    private Stage loginStage;
    private Stage gameStage;
    private Stage registerStage;
    private final LoginWindow loginWindow = new LoginWindow();
    private final RegisterWindow registerWindow = new RegisterWindow();

    @Override
    public void start(Stage primaryStage) {
        this.loginStage = primaryStage;

        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");
        VBox loginLayout = new VBox(10, loginButton, registerButton);
        Scene loginScene = new Scene(loginLayout, 200, 150);

        loginButton.setOnAction(e -> {
            if (loginWindow.display()) {
                currentUser = ();
                launchGame();
            }
        });

        registerButton.setOnAction(e -> {
            registerWindow.display();
            // Here, you can decide if you want to automatically launch the game after registration.
        });

        loginStage.setTitle("Login");
        loginStage.setScene(loginScene);
        loginStage.show();
    }

    private void openRegisterWindow() {
        // check if register stage is already open
        if (registerStage != null && registerStage.isShowing()) {
            registerStage.requestFocus();
            return;
        }

        Button registerButton = new Button("Register Now");
        VBox registerLayout = new VBox(10, registerButton);
        Scene registerScene = new Scene(registerLayout, 200, 150);

        registerStage = new Stage();
        registerStage.setTitle("Register");
        registerStage.setScene(registerScene);

        // Hide login window
        loginStage.hide();

        // Show register stage
        registerStage.showAndWait();

        // Show login window after register window is closed
        loginStage.show();

        registerButton.setOnAction(e -> launchGame());
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void display(Stage primaryStage) {
        primaryStage.setTitle("Two-Up Game Remastered");

        GridPane mainLayout = new GridPane();
        mainLayout.setHgap(10);
        mainLayout.setVgap(10);

        mainLayout.add(coinAnimation1.getCoin(), 0, 0);
        mainLayout.add(coinAnimation2.getCoin(), 1, 0);

        ToggleGroup group = new ToggleGroup();
        RadioButton headsRadio = new RadioButton("Heads-Heads");
        RadioButton tailsRadio = new RadioButton("Tails-Tails");

        headsRadio.setToggleGroup(group);
        tailsRadio.setToggleGroup(group);

        headsRadio.setSelected(true);

        playButton.setOnAction(e -> {
            coinAnimation1.startAnimation();
            coinAnimation2.startAnimation();

            boolean guessedBothHeads = headsRadio.isSelected();
            gameLogic.checkGuess(guessedBothHeads, coinAnimation1, coinAnimation2);
        });

        mainLayout.add(headsRadio, 0, 1);
        mainLayout.add(tailsRadio, 1, 1);
        mainLayout.add(playButton, 0, 2, 2, 1); // spans two columns

        Scene scene = new Scene(mainLayout, 600, 600, true);
        scene.setFill(Color.SLATEGREY);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void playGame(boolean guessedHeads) {
        if (gameLogic.checkGuess(guessedHeads)) {
            leaderboard.addScore(currentUser, 1);
        }
    }

    public void launchGame() {
        if (gameStage == null) {
            gameStage = new Stage();
        }
        display(gameStage);
    }
}
