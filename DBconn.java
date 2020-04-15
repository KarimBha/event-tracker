/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxsample;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DBconn {

    private Connection dbconnection;
    //boolean UserVerify;

    public DBconn() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");
        java.sql.Connection conn;
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/luteciahotel", "root", "Karimb2002");
        dbconnection = conn;
    }

    /**
     *
     * @param username
     * @param password
     * @return
     * @throws SQLException
     */
    public boolean UserVerify(String username, String password) throws SQLException {
        String authenticate = "Select * from luteciahotel.login where Username='" + username + "' and Password=" + "'" + password + "'";

        PreparedStatement pst = dbconnection.prepareStatement(authenticate);

        ResultSet rs;
        try {
            rs = pst.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBconn.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    public int UserIdentify(String username) throws SQLException {
        String identify = "Select department.id from login, department where login.deptID = department.id and login.Username ='" + username + "'";

        PreparedStatement pst = dbconnection.prepareStatement(identify);
        int columnValue;
        columnValue = 0;
        ResultSet rs;
        rs = pst.executeQuery();

        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        if (rs.next() == true) {
            for (int i = 1; i <= columnsNumber; i++) {

                columnValue = rs.getInt(i);
            }

        }
        return columnValue;

    }
    @FXML
    public Label lblusertaken;
    @FXML
    public Label lbluseradded;

    public boolean AddUser(String username, String password, int dept) throws SQLException {
        try {
            String usernametaken = "Select Username from login";
            PreparedStatement statement = dbconnection.prepareStatement(usernametaken);
            ResultSet result;
            result = statement.executeQuery();
            ResultSetMetaData rsmd = result.getMetaData();
            Boolean usertaken = false;
            while (result.next() && usertaken == false) {
                if (result.getString(1).equals(username)) {
                    usertaken = true;

                }
            }
            if (usertaken == true) {
                return false;
            } else {
                int EmployeeID = 0;
                String setID = "Select EmployeeID from login ORDER BY EmployeeID ASC";

                PreparedStatement pst = dbconnection.prepareStatement(setID);
                ResultSet rs;
                rs = pst.executeQuery();

                while (rs.next()) {
                    EmployeeID = rs.getInt(1) + 1;
                }
                rs.close();

                String adduser = "insert into login (EmployeeID, Username, Password, deptID) values(?, ?, ?, ?)";
                PreparedStatement preparedStmt = dbconnection.prepareStatement(adduser);
                preparedStmt.setInt(1, EmployeeID);
                preparedStmt.setString(2, username);
                preparedStmt.setString(3, password);
                preparedStmt.setInt(4, dept);
                preparedStmt.execute();
                return true;

            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return false;
    }

    public boolean AddEvent(String eventname, String eventdetails, String eventdate, int eventdepartment) throws SQLException {
        try {
            int idEvents = 0;
            String getidevent = "Select events.idEvents from events ORDER BY idEvents ASC";
            PreparedStatement pst = dbconnection.prepareStatement(getidevent);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                idEvents = rs.getInt(1) + 1;
            }
            String addevents = "Insert into events (idEvents, deptID, Event, Date, Details) values(?, ?, ?, ?, ?)";
            PreparedStatement p = dbconnection.prepareStatement(addevents);
            p.setInt(1, idEvents);
            p.setInt(2, eventdepartment);
            p.setString(3, eventname);
            p.setString(4, eventdate);
            p.setString(5, eventdetails);
            p.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean ConfirmEvent(String eventid) throws SQLException {
        try {
            String eventexists = "Select idEvents from events where events.idEvents ='" + eventid + "'";
            PreparedStatement pst = dbconnection.prepareStatement(eventexists);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String insertconfirmation = "Update events set confirmed = confirmed + 1 where idEvents ='" + eventid + "'";
                PreparedStatement p = dbconnection.prepareStatement(insertconfirmation);
                p.execute();
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public ObservableList<Attendance> GetAttendance() throws SQLException {

        String getattedance = "Select idEvents, Event, Confirmed from events ORDER BY Date ASC";
        PreparedStatement p = dbconnection.prepareStatement(getattedance);
        ResultSet rs = p.executeQuery();
        ObservableList<Attendance> att = FXCollections.observableArrayList();
        while (rs.next()) {
            att.add(new Attendance(rs.getString(1), rs.getString(2), rs.getString(3)));
        }
        return att;

    }

    public ObservableList<Event> GetEvent(int dept) throws SQLException {
        ObservableList<Event> data = FXCollections.observableArrayList();
        String eventquery = "Select idEvents, Event, Date, Details from events where events.deptID = '" + dept + "'";
        PreparedStatement pst = dbconnection.prepareStatement(eventquery);
        ResultSet rs;
        rs = pst.executeQuery();
        while (rs.next()) {
            data.add(new Event(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
        }
        return data;
    }

    public boolean DeleteEvent(String eventid) throws SQLException {
        try {
            String delete = "DELETE from events WHERE events.idEvents = '" + eventid + "'";
            PreparedStatement pst = dbconnection.prepareStatement(delete);
            pst.execute();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public float[] GetPerformance() throws SQLException {
        try {
            int i;
            int counter[] = new int[5];
            int total = 0;
            float average[] = new float[5];
            int eventcount = 0;
            for (i = 0; i < 5; i++) {
                counter[i] = 0;
                String employeenum = "Select username from login where deptID='" + i + "'";
                PreparedStatement pst = dbconnection.prepareStatement(employeenum);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    counter[i] = counter[i] + 1;
                }
                String countconfirmed = "Select confirmed from events where deptID='" + i + "'";
                PreparedStatement p = dbconnection.prepareStatement(countconfirmed);
                ResultSet r = p.executeQuery();
                while (r.next()) {
                    total = total + r.getInt(1);
                    eventcount = eventcount + 1;
                }
                if (eventcount == 0 || counter[i] == 0 || total == 0) {
                    average[i] = 0;
                } else {
                    average[i] = ((total / eventcount) / counter[i]) * 100;
                }
            }
            return average;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }
}
