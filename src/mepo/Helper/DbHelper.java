
package mepo.Helper;

import mepo.Components.MyNotify;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DbHelper {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String CONN_STRING = "jdbc:mysql://localhost/meepo";

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
        } catch (SQLException e){
                MyNotify.MyNotifyAlertError(" THERE ARE SOME PROBLEMS LIKES CONNECTION, DATABASE OR SOMETHING ELSE.... PLEASE TRY AGAIN LATER !!");
            }
            return null;
        }
    }

