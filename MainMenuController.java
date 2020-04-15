/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxsample;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


/**
 * FXML Controller class
 *
 * @author nitos
 */
public class MainMenuController implements Initializable {

    @FXML
    private Button btnlogout;
    @FXML
    private Label lblWelcome;
    @FXML
    private Label lblconfirmed;
    @FXML
    private Button btnconfirm;
    @FXML
    private TableView<Event> EventTable;
    @FXML
    private TableColumn<Event, String> tbEvent;
    @FXML
    private TableColumn<Event, String> tbDate;
    @FXML
    private TableColumn<Event, String> tbDetail;
    @FXML
    private TableColumn<Event, String> tbEventID;

    @SuppressWarnings("Convert2Diamond")
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.lblconfirmed.setVisible(false);
        tbEventID.setCellValueFactory(
                new PropertyValueFactory<Event, String>("eventid")
        );
        tbEvent.setCellValueFactory(
                new PropertyValueFactory<Event, String>("event")
        );
        tbDate.setCellValueFactory(
                new PropertyValueFactory<Event, String>("date")
        );
        tbDetail.setCellValueFactory(
                new PropertyValueFactory<Event, String>("details")
        );

    }

    public void initdata(int dept) throws ClassNotFoundException, SQLException {
        DBconn x = new DBconn();
        ObservableList<Event> data = FXCollections.observableArrayList();
        data = x.GetEvent(dept);
        EventTable.setItems(data);

        String department = null;
        switch (dept) {
            case 0:
                department = "Admin";
                break;
            case 1:
                department = "F&B";
                break;
            case 2:
                department = "Reception";
                break;
            case 3:
                department = "Housekeeping";
                break;
            case 4:
                department = "Accounting and Finance";
                break;
            default:
                break;
        }
        this.lblWelcome.setText("Welcome to " + department + "'s event page");

    }

    public void logout(ActionEvent event) throws IOException {
        Openmenu x = new Openmenu();
        x.logout();
        ((Node) (event.getSource())).getScene().getWindow().hide();

    }

    public void confirm(ActionEvent event) throws SQLException, ClassNotFoundException {
        try {
            DBconn x = new DBconn();
            Event ev = EventTable.getSelectionModel().getSelectedItem();
            
            String eventid = ev.getEventid();
            if (x.ConfirmEvent(eventid) == true) {
                this.lblconfirmed.setText("Event Confirmed");
                this.lblconfirmed.setVisible(true);

            } else {
                this.lblconfirmed.setText("Event does not exist");
                this.lblconfirmed.setVisible(true);
            }
        } catch (ClassNotFoundException | SQLException e) {
            this.lblconfirmed.setText("Select an event from the table above");
            this.lblconfirmed.setVisible(true);
        }
    }

}
