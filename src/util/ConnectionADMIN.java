package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionADMIN {

	private static final String URL = "jdbc:mysql://127.0.0.1:3306/SmartCount_P1_DBA"; 
	private static final String USER = "usuario_SmartCountADMIN";       
	private static final String PASSWORD = "admin"; 
	
	 public static Connection getConnectionADMIN() throws SQLException {
	        return DriverManager.getConnection(URL, USER, PASSWORD);
	    }
}
