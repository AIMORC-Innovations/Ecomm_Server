package com.aimorc.ajax;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCPostgreSQLConnect {

	public final static String DB_DRIVER_CLASS = "org.postgresql.Driver";
	public final static String DB_URL = "jdbc:postgresql://localhost:5432/Product_Development";
	public final static String DB_USERNAME = "postgres";
	public final static String DB_PASSWORD = "1234567";

	public static Connection getConnection() throws ClassNotFoundException, SQLException {

		Connection connection = null;

		// load the Driver Class
		Class.forName(DB_DRIVER_CLASS);

		// create the connection now
		connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

		return connection;
	}
}

/*
 * private final static Connection closeconnection() { Connection connection =
 * null; PreparedStatement preparedStatement = null; ResultSet resultSet = null;
 * PreparedStatement passwordstatment = null; ResultSet passwordresultset =
 * null;
 * 
 * finally { // Step 3: Closing database connection try {
 * 
 * if (null != resultSet) { resultSet.close(); }
 * 
 * if (null != preparedStatement) { // cleanup resources, once after processing
 * preparedStatement.close(); }
 * 
 * if (null != passwordstatment) { passwordstatment.close(); }
 * 
 * if (null != passwordresultset) { passwordresultset.close(); }
 * 
 * if (null != connection) { // and then finally close connection
 * connection.close(); } } catch (SQLException sqlex) { sqlex.printStackTrace();
 * } }
 * 
 * }
 */
/*
 * 2nd function finally { // Step 3: Closing database connection try {
 * 
 * if (null != resultSet) { resultSet.close(); }
 * 
 * if (null != preparedStatement) { // cleanup resources, once after processing
 * preparedStatement.close(); } if (null != connection) { // and then finally
 * close connection connection.close(); } } catch (SQLException sqlex) {
 * sqlex.printStackTrace(); } }
 */
/*
 * 3rd function finally { // Step 3: Closing database connection try { if (null
 * != connection) { // cleanup resources, once after processing
 * prepStatement.close();
 * 
 * // and then finally close connection connection.close(); } } catch
 * (SQLException sqlex) { sqlex.printStackTrace(); } }
 */
/*
 * 4th function finally { // Step 3: Closing database connection try {
 * 
 * if (null != resultSet) { resultSet.close(); }
 * 
 * if (null != preparedStatement) { // cleanup resources, once after processing
 * preparedStatement.close(); }
 * 
 * if (null != connection) { // and then finally close connection
 * connection.close();
 * 
 * } } catch (SQLException sqlex) { sqlex.printStackTrace(); } }
 */
