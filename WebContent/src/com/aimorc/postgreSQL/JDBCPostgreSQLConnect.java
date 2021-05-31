package com.aimorc.postgreSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCPostgreSQLConnect {

	public final static String DB_DRIVER_CLASS = "org.postgresql.Driver";
	public final static String DB_URL = "jdbc:postgresql://localhost:5432/product_development";
	public final static String DB_USERNAME = "postgres";
	public final static String DB_PASSWORD = "1234";

	public static Connection getConnection() throws ClassNotFoundException, SQLException {

		Connection connection = null;

		// load the Driver Class
		Class.forName(DB_DRIVER_CLASS);

		// create the connection now
		connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

		System.out.println("DB Connection created successfully");
		return connection;
	}
}
	
	

