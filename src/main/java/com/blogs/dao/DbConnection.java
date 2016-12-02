package com.blogs.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/db_blog";
	private static final String DB_USER = "blog_user";
	private static final String DB_PASSWORD = "blog_user";

	public Connection getConnection() throws Exception {
		try {
			String connectionURL = DB_CONNECTION;
			Connection connection = null;
			Class.forName(DB_DRIVER).newInstance();
			connection = DriverManager.getConnection(connectionURL, DB_USER, DB_PASSWORD);
			return connection;
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}

}
