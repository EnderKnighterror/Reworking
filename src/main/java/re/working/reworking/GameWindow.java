package re.working.reworking;

import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GameWindow {
    public String selectedRadioBtn = "";
    public Button playGame;
    public Button reset;

    public void display(Stage primaryStage, User currentUser) {
        primaryStage.setTitle("Two Up Game - Rework");

        // coin textures
        Image coinHeadsTexture = new Image(getClass().getResourceAsStream("/coin_4.png"));
        Image coinTailsTexture = new Image(getClass().getResourceAsStream("/coin_0.png"));

        // cylinder material
        PhongMaterial coin1material = new PhongMaterial();
        PhongMaterial coin2material = new PhongMaterial();

        // create a cylinder with the dimensions of a coin
        Cylinder coin1Cylinder = CoinAnimation.coinModel(coin1material, coinHeadsTexture, 200, 200, 100);
        Cylinder coin2Cylinder = CoinAnimation.coinModel(coin2material, coinHeadsTexture, 500, 200, 100);

        // create an animation loop for the cylinder
        Timeline coin1Timeline = CoinAnimation.createSpinningTimeline(
                coin1Cylinder,
                coin1material,
                coinHeadsTexture,
                coinTailsTexture
        );
        coin1Timeline.play();

        Timeline coin2Timeline = CoinAnimation.createSpinningTimeline(
                coin2Cylinder,
                coin2material,
                coinHeadsTexture,
                coinTailsTexture
        );
        coin2Timeline.play();

        // ===== GUI Elements ===== //

        Label gameState = customLabel("Result of Game", 300,50);


        // select a bet radio button group
        ToggleGroup tg = new ToggleGroup();
        RadioButton r1 = customRadioBtn("Heads Heads", 200, 350);
        RadioButton r2 = customRadioBtn("Tails Tails", 400, 350);

        r1.setToggleGroup(tg);
        r2.setToggleGroup(tg);

        r1.setOnAction(e -> {
            selectedRadioBtn = r1.getText();
            playGame.setDisable(false);
        });
        r2.setOnAction(e -> {
            selectedRadioBtn = r2.getText();
            playGame.setDisable(false);
        });

        // button to play a game of two-up
        playGame = customBtn("Play Game",300,400);
        playGame.setDisable(true);
        playGame.setOnAction(e -> {
            reset.setDisable(false);
            r1.setDisable(true);
            r2.setDisable(true);
            String currentBet = getCurrentBet();
            Coin coin1 = new Coin();
            Coin coin2 = new Coin();
            coin1.flip();
            coin2.flip();
            Image coin1Texture = CoinAnimation.coinTexture(coin1);
            Image coin2Texture = CoinAnimation.coinTexture(coin2);

            // add playersName input gui
            String gameResult = CoinAnimation.handleGame(coin1, coin2, currentBet, currentUser.getUsername());

            if (gameResult.equals("HH Flip Again")||(gameResult.equals("TT Flip Again"))) {
                playGame.setText("Flip Again");
            } else {
                playGame.setDisable(true);
            }

            if (gameResult.equals("No Player")) {
                gameState.setText("Please Enter Players Name");
                tg.selectToggle(null);
                r1.setDisable(false);
                r2.setDisable(false);
                reset.setDisable(true);
            }

            switch (gameResult) {
                case "HH", "HH Lose", "TT", "TT Lose", "HH Flip Again", "TT Flip Again" -> {
                    CoinAnimation.handleCoinAnimationStop(
                            coin1Timeline,
                            coin2Timeline,
                            coin1Cylinder,
                            coin2Cylinder,
                            coin1material,
                            coin2material,
                            coin1Texture,
                            coin2Texture
                    );
                    gameState.setText(gameResult);
                }
            }
        });

        // resets the application to default state
        reset = customBtn("reset",300,500);
        reset.setDisable(true);
        reset.setOnAction(e -> {
            coin1Timeline.play();
            coin2Timeline.play();
            tg.selectToggle(null);
            gameState.setText("");
            playGame.setText("Play Game");
            playGame.setDisable(true);
            reset.setDisable(true);
            r1.setDisable(false);
            r2.setDisable(false);
        });

        // menu bar for user settings / Preferences
        MenuBar menuBar = new MenuBar();
        menuBar.setLayoutX(0);
        menuBar.setLayoutY(0);
        menuBar.setMinHeight(30);

        Menu prefMenu = new Menu("Preferences");
        Menu fontSizeMenuItem = new Menu("Font Size");

        MenuItem fontSizeSmall = new MenuItem("Small");
        fontSizeSmall.setOnAction(e -> {
            setFontBySize(10, gameState, r1, r2, playGame, reset);
        });

        MenuItem fontSizeMedium = new MenuItem("Medium");
        fontSizeMedium.setOnAction(e -> {
            setFontBySize(12, gameState, r1, r2, playGame, reset);
        });

        MenuItem fontSizeLarge = new MenuItem("Large");
        fontSizeLarge.setOnAction(e -> {
            setFontBySize(15, gameState, r1, r2, playGame, reset);
        });

        MenuItem leaderboardMenu = new MenuItem("LeaderBoard");
        leaderboardMenu.setOnAction(e -> {
            LeaderboardPane lbPane = new LeaderboardPane(); // Create the leaderboard pane
            Stage n = new Stage();
            lbPane.display(n); // Show the leaderboard inside a new window
        });


        prefMenu.getItems().addAll(fontSizeMenuItem, leaderboardMenu);
        fontSizeMenuItem.getItems().addAll(fontSizeSmall, fontSizeMedium, fontSizeLarge);
        menuBar.getMenus().addAll(prefMenu);

        // create a group called root and add everything
        Group root = new Group();
        root.getChildren().addAll(
                menuBar,
                gameState,
                coin1Cylinder,
                coin2Cylinder,
                playGame,
                reset,
                r1,
                r2
        );

        // add the Group "root" to the Scene "scene" then add Scene to the Stage and show the stage
        Scene scene = new Scene(root, 700, 600, true);
        scene.setFill(Color.SLATEGREY);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }
    //GUI TEMPLATES
    private static Label customLabel(String labelTxt, int posX, int posY) {
        Label label = new Label();
        label.setAlignment(Pos.CENTER);
        label.setStyle("-fx-background-color: Silver; -fx-padding: 10;");
        label.setText(labelTxt);
        label.setLayoutX(posX);
        label.setLayoutY(posY);
        label.setMinSize(100, 40);
        return label;
    }
    private static TextField customTextField(int posX, int posY) {
        TextField textField = new TextField();
        textField.setLayoutX(posX);
        textField.setLayoutY(posY);
        textField.setMinSize(200, 40);
        return textField;
    }

    private static Button customBtn(String btnText, int posX, int posY) {
        Button button = new Button(btnText);
        button.setLayoutX(posX);
        button.setLayoutY(posY);
        button.setMinSize(100, 40);
        return button;
    }

    private static RadioButton customRadioBtn(String btnText, int posX, int posY) {
        RadioButton r1 = new RadioButton(btnText);
        r1.setLayoutX(posX);
        r1.setLayoutY(posY);
        return r1;
    }

    private String getCurrentBet() {
        return selectedRadioBtn;
    }

    private void setFontBySize(Integer size, Label gs, RadioButton rb1, RadioButton rb2, Button pg, Button rb) {
        Font font = new Font(size);
        gs.setFont(font);
        rb1.setFont(font);
        rb2.setFont(font);
        pg.setFont(font);
        rb.setFont(font);

    }
}