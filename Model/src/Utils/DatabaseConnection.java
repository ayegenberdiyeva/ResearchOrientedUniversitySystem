package src.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseConnection {
//    private static final Properties prop = new Properties();

//    private static final String URL = prop.getProperty("jdbc:postgresql://localhost:5432/OOPProject");
//    private static final String USER = prop.getProperty("aminayegenberdiyeva");
//    private static final String PASSWORD = prop.getProperty("yegnbb");

    private static final String URL = ("jdbc:postgresql://localhost:5432/OOPProject");
    private static final String USER = ("aminayegenberdiyeva");
    private static final String PASSWORD = ("yegnbb");

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

