package com.blogs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AbstractDAOHelper {

	/**
	 * 
	 * @param connection
	 * @param resultSet
	 * @param statement
	 */
	public void closeStreams(Connection connection, ResultSet resultSet,
			PreparedStatement statement) {
		try {
			if (null != connection) {
				connection.close();
			}
			if (null != resultSet) {
				resultSet.close();
			}
			if (null != statement) {
				statement.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param connection
	 * @param e
	 */
	public void cleanUp(Connection connection) {
		try {
			if (null != connection)
			{
			connection.rollback();
			this.closeStreams(connection, null, null);
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
	}

}
