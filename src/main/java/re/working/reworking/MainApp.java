package re.working.reworking;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {
    private Stage loginStage;
    private Stage registerStage;

    @Override
    public void start(Stage primaryStage) {
        this.loginStage = primaryStage;

        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");
        VBox loginLayout = new VBox(10, loginButton, registerButton);
        Scene loginScene = new Scene(loginLayout, 200, 150);

        //handle register
        registerButton.setOnAction(e -> openRegisterWindow());

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

        //Hide login window
        loginStage.hide();

        //show register stage
        registerStage.showAndWait();

        //show login window after register window is closed
        loginStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
