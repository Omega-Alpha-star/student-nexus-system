package student.nexus.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/student_nexus";

    private static final String USER = "Your_username"; //Use locale database, you must put your database username
    private static final String PASSWORD = "Your_password"; // Same here, put your database password

    public static Connection getConnection () throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
