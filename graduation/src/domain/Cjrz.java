package domain;

import java.sql.Timestamp;

/**
 * 抽检日志
 * @author DaMoTou
 *
 */
public class Cjrz {
	private int id;
	private String xuehao;
	private String gonghao;
	private Timestamp time;
	private String text;
	private int dj = 0;
	private int year = 0;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getXuehao() {
		return xuehao;
	}
	public void setXuehao(String xuehao) {
		this.xuehao = xuehao;
	}
	public String getGonghao() {
		return gonghao;
	}
	public void setGonghao(String gonghao) {
		this.gonghao = gonghao;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getDj() {
		return dj;
	}
	public void setDj(int dj) {
		this.dj = dj;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public Cjrz() {
		super();
	}
	public Cjrz(String xuehao, String gonghao, Timestamp time, String text, int dj, int year) {
		super();
		this.xuehao = xuehao;
		this.gonghao = gonghao;
		this.time = time;
		this.text = text;
		this.dj = dj;
		this.year = year;
	}
}