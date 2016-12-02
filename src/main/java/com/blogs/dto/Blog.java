package com.blogs.dto;

import java.util.Date;
import java.util.List;

public class Blog {
	private int id;
	private String title;
	private Date created;
	private boolean isDeleted;

	private List<Paragraph> paragraphs;
	private List<Author> authors;
	private List<Tag> tags;

	public Blog(){
		
	}
	
	public Blog(String title, List<Paragraph> paragraphs) {
		this.title = title;
		this.paragraphs = paragraphs;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public List<Paragraph> getParagraphs() {
		return paragraphs;
	}
	public void setParagraphs(List<Paragraph> paragraphs) {
		this.paragraphs = paragraphs;
	}
	public List<Author> getAuthors() {
		return authors;
	}
	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}
	public List<Tag> getTags() {
		return tags;
	}
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public String toString() {
		return "Blog [id=" + id + ", title=" + title + ", created=" + created + ", isDeleted=" + isDeleted
				+ ", paragraphs=" + paragraphs + ", authors=" + authors + ", tags=" + tags + "]";
	}
	
}
