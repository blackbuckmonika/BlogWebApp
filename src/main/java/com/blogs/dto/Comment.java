package com.blogs.dto;

import java.util.Date;

public class Comment {
	private int id;
	private String commentText;
	private Date created;
	private boolean isDeleted;
	public Comment(){
		
	}
	
	public Comment(String commentText) {
		super();
		this.commentText = commentText;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", commentText=" + commentText + ", created=" + created + ", isDeleted="
				+ isDeleted + "]";
	}	
	

}
