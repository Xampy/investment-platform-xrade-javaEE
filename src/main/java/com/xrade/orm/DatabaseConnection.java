package com.xrade.orm;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;



public class DatabaseConnection {
	
	/*private final String url = "jdbc:mysql://localhost:3306/xrade";
	private final String user = "root";
	private final String pass = "";
	private static Connection connection;
	
	public DatabaseConnection(){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			DatabaseConnection.connection = DriverManager.getConnection(url, user, pass);
		}catch (SQLException e){
			e.printStackTrace();
			System.exit(0);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Connection  getInstance() {
		if (DatabaseConnection.connection == null){
			return (new DatabaseConnection()).connection;
		}
		return DatabaseConnection.connection;
	}*/
	

	private static DataSource dataSource;
	private static Connection connection;
	
	private static final String  JNDI_LOOKUP_SERVICE = "java:/comp/env/jdbc/blueshipDB";
	
	
	public DatabaseConnection(){
		try {
			Context context = new InitialContext();
			connection = ( (DataSource) context.lookup(JNDI_LOOKUP_SERVICE)).getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Connection  getInstance() {
		if(DatabaseConnection.connection == null)
			new DatabaseConnection();
		
		return DatabaseConnection.connection;
	}
}
