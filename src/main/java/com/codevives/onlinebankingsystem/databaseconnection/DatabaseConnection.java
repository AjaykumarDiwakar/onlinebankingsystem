package com.codevives.onlinebankingsystem.databaseconnection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
public static Connection provideConnection() {
	Connection connection=null;
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		String url="jdbc:mysql://localhost:3306/bankingsystem";
		String name="root";
		String password="radheyshyam@12";
		connection=DriverManager.getConnection(url,name,password);
		
	}
	catch(Exception e) {
		e.printStackTrace();
	}
	return connection;
}
}
