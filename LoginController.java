/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxsample;

import java.io.IOException;
import java.sql.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private Label lblStatus;
    @FXML
    private TextField txtUserName;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnlogin;

    DBconn identify;
   
    String FXML;

    public void logincheck(ActionEvent event) throws ClassNotFoundException, IOException, SQLException {

        String username = txtUserName.getText();
        String password = txtPassword.getText();

        DBconn connection = new DBconn();
        if (connection.UserVerify(username, password) == true) {
            this.identify = new DBconn();
            MainMenuController connect = new MainMenuController();
            int dept;
            dept = identify.UserIdentify(username);
            if (dept == 0) {
                this.FXML = "adminpage";
                Openmenu x = new Openmenu();
                x.menu(FXML);
                ((Node) (event.getSource())).getScene().getWindow().hide();
            } else {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("MainMenu.fxml"));
                    Parent MainMenuParent = loader.load();
                    Scene MainMenuScene = new Scene(MainMenuParent);
                    MainMenuController controller = loader.getController();
                    controller.initdata(dept);
                    Stage window = new Stage();
                    window.setScene(MainMenuScene);
                    window.show();
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println(e);
                }
            }

        } else if (connection.UserVerify(username, password) == false) {
            txtUserName.setText("");
            txtPassword.setText("");
            txtUserName.setPromptText("Incorrect");
            txtPassword.setPromptText("Incorrect");
            lblStatus.setText("Invalid Credentials");
        }

    }

}
