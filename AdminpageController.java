package javafxsample;

import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

public class AdminpageController implements Initializable {

    @FXML
    private Button btnlougout;
    @FXML
    private TextField txtUserName;
    @FXML
    private TextField txtPassword;
    @FXML
    private Button btnadduser;
    @FXML
    private Button btnaddevent;
    @FXML
    private Button btnviewevents;
    @FXML
    private ChoiceBox deptchoicebox;
    @FXML
    private Label lblusertaken;
    @FXML
    private Label lbluseradded;
    @FXML
    private TextField txteventname;
    @FXML
    private TextField txteventdetails;
    @FXML
    private ChoiceBox deptchoicebx;
    @FXML
    private DatePicker datepicker;
    @FXML
    private Label lbleventadded;
    @FXML
    private Label lbleventerror;
    @FXML
    private Label lblusererror;
    @FXML
    private Label lbladduser;
    @FXML
    private Label lbladdevent;
    @FXML
    private Button viewadminevents;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> deptchoicelist = FXCollections.observableArrayList(
                "Admin", "F&B", "Housekeeping", "Reception", "Accounting and Finance");
        deptchoicebox.setItems(deptchoicelist);
        deptchoicebx.setItems(deptchoicelist);
        this.lblusertaken.setVisible(false);
        this.lbluseradded.setVisible(false);
        this.lbleventadded.setVisible(false);
        this.lbleventerror.setVisible(false);
        this.lblusererror.setVisible(false);
    }

    public void adduser(ActionEvent event) throws SQLException, ClassNotFoundException {

        String username = this.txtUserName.getText();
        String password = this.txtPassword.getText();
        String department = (String) deptchoicebox.getValue();
        if (username.isEmpty() || password.isEmpty() || department.isEmpty()) {
            this.lblusererror.setVisible(true);
        } else {
            int dept = 99;
            switch (department) {
                case "Admin":
                    dept = 0;
                    break;
                case "F&B":
                    dept = 1;
                    break;
                case "Reception":
                    dept = 2;
                    break;
                case "Housekeeping":
                    dept = 3;
                    break;
                case "Accounting and Finance":
                    dept = 4;
                    break;
                default:
                    break;
            }

            DBconn x = new DBconn();
            Boolean added = x.AddUser(username, password, dept);
            if (added == true) {
                this.lbluseradded.setVisible(true);
                this.lblusertaken.setVisible(false);
                this.lblusererror.setVisible(false);
                txtUserName.setText("");
                txtPassword.setText("");
                deptchoicebox.setValue(null);
            } else {
                this.lblusertaken.setVisible(true);
                this.txtUserName.setText("");
            }
        }

    }

    public void addevent(ActionEvent event) throws ClassNotFoundException, SQLException {
        DBconn x = new DBconn();

        String eventname = txteventname.getText();
        String eventdetails = txteventdetails.getText();
        String eventdepartment = (String) deptchoicebx.getValue();
        LocalDate localdate = datepicker.getValue();
        LocalDate today = LocalDate.now();
        if (eventname.isEmpty() || eventdetails.isEmpty() || eventdepartment.isEmpty() || localdate == null) {
               this.lbleventerror.setVisible(true);
            }
        else{
        if (today.compareTo(localdate) < 0 || today.compareTo(localdate) == 0) {
            
                String eventdate = localdate.toString();
                int dept = 99;
                switch (eventdepartment) {
                    case "Admin":
                        dept = 0;
                        break;
                    case "F&B":
                        dept = 1;
                        break;
                    case "Reception":
                        dept = 2;
                        break;
                    case "Housekeeping":
                        dept = 3;
                        break;
                    case "Accounting and Finance":
                        dept = 4;
                        break;
                    default:
                        break;
                }

                boolean addevent = x.AddEvent(eventname, eventdetails, eventdate, dept);
                if (addevent == true) {
                    this.lbleventadded.setVisible(true);
                    this.lbleventerror.setVisible(false);
                    txteventname.setText("");
                    txteventdetails.setText("");
                    deptchoicebx.setValue(null);
                    datepicker.setValue(null);
                }
            }
         else {
            //https://stackoverflow.com/questions/39512621/change-javafx-tableview-font-size
            JTextPane jtp = new JTextPane();
            jtp.setPreferredSize(new Dimension(100, jtp.getPreferredSize().height));
            jtp.setPreferredSize(new Dimension( 350, jtp.getPreferredSize().width));
            jtp.setFont(new Font("Arial", Font.BOLD, 25));
            jtp.setText("Date of event must not be before today");
            JOptionPane.showMessageDialog(null, jtp,
                    "DATE ERROR",
                    JOptionPane.ERROR_MESSAGE);
        }
        }
    }
    Openmenu x = new Openmenu();

    public void logout(ActionEvent event) throws IOException {
        x.logout();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    public void vieweventspage(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
        String FXML = "admineventspage";
        x.menu(FXML);
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    public void viewadminevents(ActionEvent event) throws IOException, ClassNotFoundException {
        Openmenu x = new Openmenu();
        String FXML = "AdminEvents";
        x.menu(FXML);
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

}
