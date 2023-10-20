package re.working.reworking;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginWindow {

    public void display(Stage primaryStage) {
        primaryStage.setTitle("Login");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(10);

        Label infoLabel = new Label("");
        GridPane.setConstraints(infoLabel, 1, 3);

        Label usernameLabel = new Label("Username");
        GridPane.setConstraints(usernameLabel, 0, 0);

        TextField usernameField = new TextField();
        GridPane.setConstraints(usernameField, 1, 0);

        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 1);

        PasswordField passwordField = new PasswordField();
        GridPane.setConstraints(passwordField, 1, 1);

        Button loginButton = new Button("Login");
        GridPane.setConstraints(loginButton, 1, 2);
        loginButton.setOnAction(e -> {
            String authUsername = usernameField.getText().trim();
            String authPassword = passwordField.getText().trim();

            // handle auth
            try {
                User authUser = JavaSQL.getUserByUsername(authUsername);

                if (authUser == null) {
                    infoLabel.setText("No user found.");
                } else {
                    Boolean isMatch = HashUtil.checkPassword(authPassword, authUser.getPasswordHash());

                    if (isMatch) {
                        // Successfully authenticated; can transition to another window if needed
                        System.out.println("Successful auth of user");
                        primaryStage.close();
                        GameWindow gw = new GameWindow();
                        Stage n = new Stage();
                        gw.display(n, authUser);
                    } else {
                        infoLabel.setText("Invalid Password");
                    }
                }
            } catch (RuntimeException ex) {
                infoLabel.setText("Auth Error");
                ex.printStackTrace();
            }
        });

        Button registerButton = new Button("Register");
        GridPane.setConstraints(registerButton, 1, 3);
        registerButton.setOnAction(e -> {
            primaryStage.close();
            RegisterWindow rw = new RegisterWindow();
            Stage n = new Stage();
            rw.display(n);
        });

        grid.getChildren().addAll(
                usernameLabel,
                usernameField,
                passwordLabel,
                passwordField,
                loginButton,
                registerButton,
                infoLabel
        );

        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
