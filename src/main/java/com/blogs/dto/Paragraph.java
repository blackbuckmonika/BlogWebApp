package com.blogs.dto;

import java.util.List;

public class Paragraph {
	private int id;
	private String paragraphText;
	private List<Comment> comments;
	private boolean isDeleted;
	public Paragraph(){
	}

	public Paragraph(String paragraphText, List<Comment> comments) {
		this.paragraphText = paragraphText;
		this.comments = comments;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getParagraphText() {
		return paragraphText;
	}
	public void setParagraphText(String paragraphText) {
		this.paragraphText = paragraphText;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public String toString() {
		return "Paragraph [id=" + id + ", paragraphText=" + paragraphText + ", comments=" + comments + ", isDeleted="
				+ isDeleted + "]";
	}

}
