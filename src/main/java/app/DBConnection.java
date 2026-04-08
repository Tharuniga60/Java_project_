package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
// Database connection details for java_project
private static final String HOST = "localhost";
private static final String PORT = "3306";
private static final String DATABASE = "java_project";
private static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE + "?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";
private static final String USER = "Tharuniga";
private static final String PASS = "Tharuniga123!@#";


static {
try {
Class.forName("com.mysql.cj.jdbc.Driver");
} catch (ClassNotFoundException e) {
e.printStackTrace();
}
}


public static Connection getConnection() throws SQLException {
return DriverManager.getConnection(URL, USER, PASS);
}
}