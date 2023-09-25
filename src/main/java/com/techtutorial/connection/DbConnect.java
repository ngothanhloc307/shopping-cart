package com.techtutorial.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnect {
	
	private static Connection connection = null;
	
	public static Connection getConnection() throws ClassNotFoundException,SQLException{
		if(connection == null) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/shopping_cart","root","root");
			System.out.println("connected");
		}
		return connection;
	}
}
 