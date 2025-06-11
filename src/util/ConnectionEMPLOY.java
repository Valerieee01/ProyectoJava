package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionEMPLOY {

	private static final String URL = "jdbc:mysql://localhost:3306/SmartCount_P1_DBA"; 
	private static final String USER = "usuario_SmartCountEMPLOY";       
	private static final String PASSWORD = "employ"; 
	
	 public static Connection getConnectionADMIN() throws SQLException {
	        return DriverManager.getConnection(URL, USER, PASSWORD);
	    }
}
