package javafxsample;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

public class Javafxsample extends Application {

    public static void main(String[] args) {
        launch(args);

    }
  

    @Override
    public void start(Stage primaryStage) throws IOException {
          
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));

        Scene login = new Scene(root);

        primaryStage.setScene(login);
        primaryStage.setTitle("PORTAL LOGIN");
        primaryStage.show();
    }

}
