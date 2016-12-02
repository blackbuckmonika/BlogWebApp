package com.blogs.model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.blogs.dao.AbstractDAOHelper;
import com.blogs.dao.BlogDAO;
import com.blogs.dao.CommentDAO;
import com.blogs.dao.DbConnection;
import com.blogs.dao.ParagraphDAO;
import com.blogs.dto.Blog;
import com.blogs.dto.Comment;
import com.blogs.dto.Paragraph;

@Service("blogManager")
public class BlogManager extends AbstractDAOHelper {
	public static final int DEFAULT_NO_OF_BLOGS = 5;

	public ArrayList<Blog> getBlogs(int pageStart, int noOfResult) {
		Connection con = null;
		try {
			ArrayList<Blog> blogList = new ArrayList<Blog>();
			DbConnection db = new DbConnection();
			con = db.getConnection();
			BlogDAO blogDAO = new BlogDAO();
			blogList = blogDAO.getBlogs(con, pageStart, noOfResult);
			return blogList;
		} catch (Exception e) {
			e.printStackTrace();
			this.cleanUp(con);
		} finally {
			this.closeStreams(con, null, null);
		}
		return null;
	}

	public ArrayList<Blog> getBlogs(int pageStart) {
		Connection con = null;
		try {
			ArrayList<Blog> blogList = new ArrayList<Blog>();
			DbConnection db = new DbConnection();
			con = db.getConnection();
			BlogDAO blogDAO = new BlogDAO();
			blogList = blogDAO.getBlogs(con, pageStart, DEFAULT_NO_OF_BLOGS);
			return blogList;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closeStreams(con, null, null);
		}
		return null;
	}

	public Blog addBlog(String title, String pText) {
		Connection con = null;
		Blog blogObj = new Blog();
		try {
			blogObj.setTitle(title);
			DbConnection db = new DbConnection();
			con = db.getConnection();
			BlogDAO blogDAO = new BlogDAO();
			blogObj = blogDAO.addBlog(con, blogObj);
			List<Paragraph> pList = addParagraphToBlog(pText, blogObj.getId());
			if (pList != null) {
				blogObj.setParagraphs(pList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closeStreams(con, null, null);
		}
		return blogObj;
	}

	public List<Paragraph> addParagraphToBlog(String pText, int blogId) {
		Connection con = null;
		List<Paragraph> pList = new ArrayList<Paragraph>();

		try {
			DbConnection db = new DbConnection();
			con = db.getConnection();
			ParagraphDAO pDAO = new ParagraphDAO();
			CommentDAO cDAO = new CommentDAO();
			String[] pTextList = pText.split("\n\n");
			for (String pStr : pTextList) {
				String[] commentList = pStr.split("\\^\\^");
				pStr = commentList[0];
				commentList = Arrays.copyOfRange(commentList, 1, commentList.length);
				for (String i : commentList) {
					System.out.println("comment list obj" + i);
				}
				Paragraph paragraph = new Paragraph();
				paragraph.setParagraphText(pStr);
				paragraph = pDAO.insertParagraph(con, paragraph, blogId);
				pList.add(paragraph);
				List<Comment> comments = null;
				if (commentList != null) {
					comments = new ArrayList<Comment>();
					for (String cStr : commentList) {
						Comment c = new Comment(cStr);
						comments.add(c);
					}
					cDAO.addComments(con, comments, paragraph.getId());
				}
				paragraph.setComments(comments);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closeStreams(con, null, null);
		}
		return pList;

	}

	public Blog getBlogById(int id) {
		Connection con = null;
		Blog blogObj = null;
		try {
			DbConnection db = new DbConnection();
			con = db.getConnection();
			BlogDAO blogDAO = new BlogDAO();
			blogObj = blogDAO.getBlogById(con, id);
			if (blogObj != null) {
				ArrayList<Paragraph> pList = this.getBlogParagraphsWithComment(con, id);
				blogObj.setParagraphs(pList);
			}
			return blogObj;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closeStreams(con, null, null);
		}
		return null;
	}

	public ArrayList<Paragraph> getBlogParagraphsWithComment(Connection con, int id) {
		try {
			ParagraphDAO paragraphDAO = new ParagraphDAO();
			ArrayList<Paragraph> pList = paragraphDAO.getParagraphs(con, id);
			CommentDAO commentDAO = new CommentDAO();
			for (Paragraph p : pList) {
				p.setComments(commentDAO.getComments(con, p.getId()));
			}
			return pList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean deleteBlogById(int id) {
		boolean isSuccessful = false;
		try {
			Connection con = (new DbConnection()).getConnection();
			BlogDAO blogDAO = new BlogDAO();
			isSuccessful = blogDAO.deleteBlog(con, id);
			if (isSuccessful) {
				ParagraphDAO pDAO = new ParagraphDAO();
				isSuccessful = pDAO.deleteParagraph(con, 0, id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isSuccessful;
	}
}
