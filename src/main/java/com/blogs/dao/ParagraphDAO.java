package com.blogs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.blogs.dto.Paragraph;

public class ParagraphDAO {
	public ArrayList<Paragraph> getParagraphs(Connection con, int blogId) throws SQLException {
		ArrayList<Paragraph> paragraphList = new ArrayList<Paragraph>();

		try {
			String query = "SELECT * FROM blog_data where blog_id= ? and isDeleted = 0;";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, blogId);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Paragraph paraObj = new Paragraph();
				paraObj.setId(rs.getInt("id"));
				paraObj.setParagraphText(rs.getString("paragraph"));
				paragraphList.add(paraObj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return paragraphList;
	}

	/**
	 * @param trainingModuleSurveyDTO
	 */
	public Paragraph insertParagraph(Connection con, Paragraph paragraph, int blogId) {
		String query = "";
		PreparedStatement pstmt = null;
		try {
			// Use prepared statements to protected against SQL injection
			// attacks
			// Set the query and use a preparedStatement
			query = "insert into blog_data (paragraph,blog_id)" + "values (?,?)";
			pstmt = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(2, blogId);
			pstmt.setString(1, paragraph.getParagraphText());
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				paragraph.setId(rs.getInt(1));
			}
			return paragraph;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param trainingModuleSurveyDTO
	 */
	public void updateParagraph(Connection con, Paragraph paragraph) {
		String query = "";
		PreparedStatement pstmt = null;
		try {
			// Set the query and use a preparedStatement
			query = "update blog_data set paragraph=? where id=?;";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, paragraph.getId());
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean deleteParagraph(Connection con,int paragraphId,int blogId){
		String query = "";
		PreparedStatement pstmt = null;
		int queryParameter = 0;
		try {
			// Set the query and use a preparedStatement
			if(paragraphId != 0){
				query = "update blog_data set isDeleted=1 where id=?;";
				queryParameter = paragraphId;
			}else if (blogId != 0){
				query = "update blog_data set isDeleted=1 where blog_id=?;";
				queryParameter = blogId;
			}
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, queryParameter);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
}
