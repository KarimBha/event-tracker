/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxsample;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author nitos
 */
public class AdmineventspageController implements Initializable {

    @FXML
    private Label lblviewevent;
    private Button btnlogout;
    private Button btnmainpage;
    private Button btndelete;
    @FXML
    private TableView<Attendance> ATable;
    @FXML
    private TableColumn<Attendance, String> tbEvent;
    @FXML
    private TableColumn<Attendance, String> tbConfirmed;
    @FXML
    private TableColumn<Attendance, String> tbEventID;
    @FXML
    private Label lblselectevent;
    @FXML
    private BarChart<?, ?> barchart;
    @FXML
    private CategoryAxis xaxis;
    @FXML
    private NumberAxis yaxis;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.lblselectevent.setVisible(false);
        try {
            tbEventID.setCellValueFactory(
                    new PropertyValueFactory<Attendance, String>("eventid")
            );
            tbEvent.setCellValueFactory(
                    new PropertyValueFactory<Attendance, String>("event")
            );
            tbConfirmed.setCellValueFactory(
                    new PropertyValueFactory<Attendance, String>("confirmed")
            );
            ObservableList<Attendance> att = FXCollections.observableArrayList();
            @SuppressWarnings("LocalVariableHidesMemberVariable")
            DBconn y = new DBconn();
            att = y.GetAttendance();
            ATable.setItems(att);
            DBconn x = new DBconn();
            float avg[] = x.GetPerformance();

            XYChart.Series series = new XYChart.Series();
            series.setName("Attendance Rate %");
            series.getData().add(new XYChart.Data("Admin", avg[0]));
            series.getData().add(new XYChart.Data("F&B", avg[1]));
            series.getData().add(new XYChart.Data("Reception", avg[2]));
            series.getData().add(new XYChart.Data("Housekeeping", avg[3]));
            series.getData().add(new XYChart.Data("accounting and finance", avg[4]));
            barchart.getData().addAll(series);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AdmineventspageController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    Openmenu x = new Openmenu();

    public void logout(ActionEvent event) throws IOException {
        x.logout();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    public void mainpage(ActionEvent event) throws IOException, ClassNotFoundException {
        String FXML = "adminpage";
        x.menu(FXML);
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    public void delete(ActionEvent event) throws SQLException, ClassNotFoundException {
        try {
            Attendance att = ATable.getSelectionModel().getSelectedItem();
            String eventid = att.getEventid();
            DBconn y = new DBconn();
            Boolean deleted = y.DeleteEvent(eventid);
            this.lblselectevent.setVisible(false);
            @SuppressWarnings("UnusedAssignment")
            ObservableList<Attendance> data = FXCollections.observableArrayList();
            data = y.GetAttendance();
            ATable.setItems(data);
        } catch (ClassNotFoundException | SQLException e) {
            this.lblselectevent.setVisible(true);

        }
    }
}
