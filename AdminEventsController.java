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
import javafx.scene.control.*;

/**
 * FXML Controller class THIS CLASS IS FULLY INHERITED FROM SUPER CLASS
 *
 * @author nitos
 */
public class AdminEventsController extends MainMenuController implements Initializable {

    @FXML
    private Button btnmainpage;
    @FXML
    private Button btnlogout;
    @FXML
    private Label lblselectdept;
    @FXML
    private ChoiceBox deptchoicebox;
    @FXML
    private Button btnloadevents;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> deptchoicelist = FXCollections.observableArrayList(
                "Admin", "F&B", "Housekeeping", "Reception", "Accounting and Finance");
        deptchoicebox.setItems(deptchoicelist);
        try {
            super.initialize(url, rb);
            super.initdata(0);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AdminEventsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void confirmevent(ActionEvent event) throws SQLException, ClassNotFoundException {
        super.confirm(event);
    }

    public void adminlogout(ActionEvent event) throws IOException {
        super.logout(event);
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    public void mainmenu(ActionEvent event) throws IOException, ClassNotFoundException {
        Openmenu f = new Openmenu();
        String FXML = "adminpage";
        f.menu(FXML);
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
    public void deptevents(ActionEvent event) throws ClassNotFoundException, SQLException{
        try{
        String department = (String) deptchoicebox.getValue();
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
        super.initdata(dept);
        lblselectdept.setText("Select department to view events");
        } catch (Exception e){
            lblselectdept.setText("No department selected");
        }
    }
}
