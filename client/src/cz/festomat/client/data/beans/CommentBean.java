package cz.festomat.client.data.beans;

import java.util.Date;

public class CommentBean {

	private String author;
	private Date time;
	private String text;
	
	public CommentBean(String author, Date time, String text) {
		super();
		this.author = author;
		this.time = time;
		this.text = text;
	}
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}
