package domain;

import java.util.Date;

public class Notice {
	private int id;
	private String title;
	private String text;
	private String gonghao;
	private int yx;
	private int zy;
	private int type;
	private int year;
	private Date time;
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
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getGonghao() {
		return gonghao;
	}
	public void setGonghao(String gonghao) {
		this.gonghao = gonghao;
	}
	public int getYx() {
		return yx;
	}
	public void setYx(int yx) {
		this.yx = yx;
	}
	public int getZy() {
		return zy;
	}
	public void setZy(int zy) {
		this.zy = zy;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public Notice() {
		super();
	}
	public Notice(String title, String text, String gonghao, int yx, int zy, int type, int year, Date time) {
		super();
		this.title = title;
		this.text = text;
		this.gonghao = gonghao;
		this.yx = yx;
		this.zy = zy;
		this.type = type;
		this.year = year;
		this.time = time;
	}
}
