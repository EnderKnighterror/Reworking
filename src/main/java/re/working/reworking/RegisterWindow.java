package re.working.reworking;

import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;

public class RegisterWindow {

    public void display(Stage primaryStage) {
        primaryStage.setTitle("Register");


        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label infoLabel = new Label("");
        GridPane.setConstraints(infoLabel, 1, 4);

        Label usernameLabel = new Label("Username");
        GridPane.setConstraints(usernameLabel, 0, 0);

        TextField usernameInput = new TextField();
        GridPane.setConstraints(usernameInput, 1, 0);

        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 1);

        PasswordField passwordInput = new PasswordField();
        GridPane.setConstraints(passwordInput, 1, 1);

        Button registerButton = new Button("Register New User");
        GridPane.setConstraints(registerButton, 1, 2);
        registerButton.setOnAction(e -> {
            String registeringUsername = usernameInput.getText().trim();
            String registeringPassword = passwordInput.getText().trim();

            if (isEmpty(registeringUsername) || isEmpty(registeringPassword)) {
                infoLabel.setText("Username and password are required!");

            } else {
                if (JavaSQL.checkDuplicateUser(registeringUsername)) {
                    infoLabel.setText("Username already in use");
                } else {
                    String hashedPassword = HashUtil.hashPassword(registeringPassword);
                    User registeringUser = new User(registeringUsername, hashedPassword);

                    try {
                        JavaSQL.addUser(registeringUser);
                        infoLabel.setText("User added successfully!");
                        primaryStage.close();
                        LoginWindow lw = new LoginWindow();
                        Stage n = new Stage();
                        lw.display(n);
                    } catch (RuntimeException ex) {
                        infoLabel.setText("Failed to register. Database error");
                        ex.printStackTrace();
                    }
                }
            }
        });

        Button closeButton = new Button("Close");
        GridPane.setConstraints(closeButton, 1, 3);
        closeButton.setOnAction(e -> primaryStage.close());

        grid.getChildren().addAll(
                usernameLabel,
                usernameInput,
                passwordLabel,
                passwordInput,
                registerButton,
                closeButton,
                infoLabel
        );

        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.showAndWait();
    }

    private static boolean isEmpty(String str) {
        return Objects.equals(str, "");
    }
}
