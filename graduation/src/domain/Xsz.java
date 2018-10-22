package domain;
/**
 * 学生组
 * @author DaMoTou
 *
 */
public class Xsz {
	private int id;
	private int zh = 0;
	private String xuehao;
	private int year;
	private int yx = 0;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getZh() {
		return zh;
	}
	public void setZh(int zh) {
		this.zh = zh;
	}
	public String getXuehao() {
		return xuehao;
	}
	public void setXuehao(String xuehao) {
		this.xuehao = xuehao;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getYx() {
		return yx;
	}
	public void setYx(int yx) {
		this.yx = yx;
	}
	public Xsz() {
		super();
	}
	public Xsz(int zh, String xuehao, int year, int yx) {
		super();
		this.zh = zh;
		this.xuehao = xuehao;
		this.year = year;
		this.yx = yx;
	}
}
