package domain;

import java.sql.Timestamp;

public class Ssjl {
	private int id;
	private String writer;
	private String reader;
	private String text;
	private Timestamp time;
	private int status = 0;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getReader() {
		return reader;
	}
	public void setReader(String reader) {
		this.reader = reader;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Ssjl() {
		super();
	}
	public Ssjl(String writer, String reader, String text, Timestamp time, int status) {
		super();
		this.writer = writer;
		this.reader = reader;
		this.text = text;
		this.time = time;
		this.status = status;
	}
}
