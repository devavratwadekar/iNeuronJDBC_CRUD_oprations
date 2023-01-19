package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JdbcUtil {
	private JdbcUtil() {}
	
	static {
		//step1-loading and register the driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	}
	
	public static Connection getJdbcConnection() throws SQLException, IOException {
		
		//take the data from properties file
		FileInputStream fis = new FileInputStream("properties\\application.properties");
		Properties properties = new Properties();
		properties.load(fis);
		
		// step 2 Establish connection with Database
		Connection connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("userName"), 
								properties.getProperty("passWord"));
//		System.out.println("CONNECTION object created....");
		return connection;
	}

	public static void CleanUp(Connection con, Statement statement, ResultSet resultSet) throws SQLException {
		//step6-closing the resources
		if (con != null) {
			con.close();
		}
		if (statement != null) {
			statement.close();
		}
		if (resultSet != null) {
			resultSet.close();
		}
	}
}
