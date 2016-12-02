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
import java.util.List;

import com.blogs.dto.Comment;

public class CommentDAO {
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	public ArrayList<Comment> getComments(Connection con, int paragraphId) throws SQLException {
		ArrayList<Comment> commentList = new ArrayList<Comment>();
		String query = "";
		try {
			query = "SELECT * FROM blog_comment where paragraph_id=?";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, paragraphId);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Comment commentObj = new Comment();
				commentObj.setId(rs.getInt("id"));
				commentObj.setCommentText(rs.getString("commentText"));
				commentObj.setCreated(rs.getDate("created"));
				commentList.add(commentObj);
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return commentList;
	}

	public Comment addComment(Connection con, Comment comment, int paragraphId) throws SQLException {
		String query = "";

		try {
			query = "insert into blog_comment (commentText,paragraph_id,created) values(?,?,?);";
			PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(2, paragraphId);
			stmt.setString(1, comment.getCommentText());
			stmt.setString(3, dateFormat.format(new Date()));
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				comment.setId(rs.getInt("id"));
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return comment;
	}

	public List<Comment> addComments(Connection con, List<Comment> comments, int paragraphId) throws SQLException {
		String query = "";
		con.setAutoCommit(false);
		Date curr_date = new Date();
		try {
			query = "insert into blog_comment (commentText,paragraph_id,created) values(?,?,?);";
			PreparedStatement stmt = con.prepareStatement(query);
			for (Comment c : comments) {
				stmt.setInt(2, paragraphId);
				stmt.setString(1, c.getCommentText());
				stmt.setString(3, dateFormat.format(curr_date));
				stmt.addBatch();
				c.setCreated(curr_date);
			}
			stmt.executeBatch();
			con.commit();
		} catch (SQLException se) {
			System.out.println(se.getMessage());
			con.rollback();
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return comments;
	}
}
