/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxsample;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author nitos
 */
class Openmenu {

    public void menu(String FXML) throws IOException, ClassNotFoundException {
        try {
            Stage mainmenu = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource(FXML + ".fxml"));
            Scene main = new Scene(root);
            mainmenu.setScene(main);
            mainmenu.setTitle("ADMIN PAGE");
            mainmenu.show();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void logout() throws IOException {
        Javafxsample restart = new Javafxsample();
        Stage primaryStage = new Stage();
        restart.start(primaryStage);
    }

}
