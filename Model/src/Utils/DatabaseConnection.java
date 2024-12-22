package src.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseConnection {
    private static final Properties prop = new Properties();
    private static final String URL = prop.getProperty("DB_URL");
    private static final String USER = prop.getProperty("DB_USER");
    private static final String PASSWORD = prop.getProperty("DB_PASSWORD");

    public static Connection getConnection() throws SQLException {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            if (conn!=null) {
                System.out.println("Database connection established");
            } else {
                System.out.println("Database connection not established");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver not found. Include it in your library path.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
        }
        return conn;
    }

//    public void createTable(Connection conn, String tableName){
//        Statement stmt;
//        try {
//            String query = "CREATE TABLE " + tableName + " ("
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
}

