package domain;
/*
 * 总评记录表
 */

public class Zpjl {
	private int id;
	private String gonghao;
	private int zy = 0;
	private String text;
	private int year;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGonghao() {
		return gonghao;
	}
	public void setGonghao(String gonghao) {
		this.gonghao = gonghao;
	}
	public int getZy() {
		return zy;
	}
	public void setZy(int zy) {
		this.zy = zy;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public Zpjl() {
		super();
	}
	public Zpjl(String gonghao, int zy, String text, int year) {
		super();
		this.gonghao = gonghao;
		this.zy = zy;
		this.text = text;
		this.year = year;
	}
}
