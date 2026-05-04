package in.gadgethub.ulility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {

    private static Connection conn;

    public static void openConnection(String dburl, String username, String password) {
        if (conn == null) {
            try {
                    conn = DriverManager.getConnection(dburl, username, password);
                System.out.println("GadgetHub ! Connection opened !");

            } catch (SQLException ex) {
                System.out.println("Error in opening connection !");
                ex.printStackTrace();
            }
        }

    }

    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("connection closed !");
            } catch (SQLException ex) {
                System.out.println("Error in closing the connection");
                ex.printStackTrace();
            }
        }
    }

    public static Connection provideconnection() {
        return conn;
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                System.out.println("Error in Closing Resultset !");
                ex.printStackTrace();

            }
        }
    }

    public static void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException ex) {
                System.out.println("Error in closing statement !");
                ex.printStackTrace();
            }
        }
    }
}
