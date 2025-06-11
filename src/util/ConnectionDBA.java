package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDBA {

	private static final String URL = "jdbc:mysql://127.0.0.1:3306/SmartCount_P1_DBA";
	private static final String USER = "usuario_SmartCountAcces";       
	private static final String PASSWORD = "SmartCountV1"; 
	
	 public static Connection getConnectionDBA() throws SQLException {
	        return DriverManager.getConnection(URL, USER, PASSWORD);
	    }
}
