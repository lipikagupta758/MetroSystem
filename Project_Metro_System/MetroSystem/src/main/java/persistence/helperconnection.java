package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class helperconnection {
	public static Connection getConnection() {
		final String DB_NAME = "jdbc:mysql://127.0.0.1:3306/metro_system";
	    final String USER_NAME = "root";
	    final String USER_PASSWORD = "root";
        try {
            return DriverManager.getConnection(DB_NAME, USER_NAME, USER_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
