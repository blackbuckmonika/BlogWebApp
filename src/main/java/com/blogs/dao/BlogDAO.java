package com.blogs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.blogs.dto.Blog;

public class BlogDAO {

	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	/**
	 * 
	 * @param con
	 * @param pageStart
	 * @param noOfPage
	 * @return blog list of length @noOfPages starting from @pageStart
	 * @throws SQLException
	 */
	public ArrayList<Blog> getBlogs(Connection con, int pageStart, int noOfPage) throws SQLException {
		ArrayList<Blog> blogList = new ArrayList<Blog>();
		try {
			String query = "SELECT * FROM blog limit ?,?;";

			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, pageStart);
			stmt.setInt(2, noOfPage);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Blog blogObj = new Blog();
				blogObj.setId(rs.getInt("id"));
				blogObj.setTitle(rs.getString("title"));
				blogObj.setCreated(rs.getDate("created"));
				blogList.add(blogObj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return blogList;
	}

	/**
	 * 
	 * @param con
	 * @param pageStart
	 * @return returns returns list of Blog 5 at once starting from @pageStart
	 * @throws SQLException
	 */
	public ArrayList<Blog> getBlogs(Connection con, int pageStart) throws SQLException {
		ArrayList<Blog> blogList = new ArrayList<Blog>();
		try {
			String query = "SELECT * FROM blog where isDeleted=0 limit ?,?;";

			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, pageStart);
			//return by default 5 result
			stmt.setInt(2, 5);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Blog blogObj = new Blog();
				blogObj.setId(rs.getInt("id"));
				blogObj.setTitle(rs.getString("title"));
				blogObj.setCreated(rs.getDate("created"));
				blogList.add(blogObj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return blogList;
	}

	/**
	 * This function return an blog for given id
	 * 
	 * @param con
	 * @param id
	 * @return Blog
	 * @throws SQLException
	 */
	public Blog getBlogById(Connection con, int id) throws SQLException {
		Blog blogObj = null;

		try {
			String query = "SELECT * FROM blog where id=? and isDeleted=0;";

			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				blogObj = new Blog();
				blogObj.setId(rs.getInt("id"));
				blogObj.setTitle(rs.getString("title"));
				blogObj.setCreated(rs.getDate("created"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return blogObj;
	}

	/**
	 * Add an blog Object
	 * @param con
	 * @param blogObj
	 * @return
	 * @throws SQLException
	 */
	public Blog addBlog(Connection con, Blog blogObj) throws SQLException {

		String query = "insert into blog (title,created) values (?,?);";
		Date curr_date = new Date();
		try {
			PreparedStatement stmt = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, blogObj.getTitle());
			stmt.setString(2, dateFormat.format(curr_date));
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				System.out.println(rs.first());
				blogObj.setId(rs.getInt(1));
				blogObj.setCreated(curr_date);
				return blogObj;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean deleteBlog(Connection con,int id){
		String query = "UPDATE blog isDeleted = 1 where id= ?;";
		try {
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}
	
}