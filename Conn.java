package airlinemanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conn implements AutoCloseable {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/airlinemanagementsystem";
    private static final String USER = "root";
    private static final String PASS = "King@2002";

    public Connection conn;

    public Conn() {
        try {
          
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establish the connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found. Include it in your library path.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Connection Failed! Check output console");
            e.printStackTrace();
        }
    }
    
    // Implement AutoCloseable's close method
    @Override
    public void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Failed to close the connection.");
                e.printStackTrace();
            }
        }
    }
}
