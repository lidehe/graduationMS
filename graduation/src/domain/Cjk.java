package domain;

public class Cjk {
	private int id;
	private String xuehao;
	private int yx = 0;
	private int zy = 0;
	private int oDj = 0;
	private int year = 0;
	private int kt = 0;
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
	public int getoDj() {
		return oDj;
	}
	public void setoDj(int oDj) {
		this.oDj = oDj;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getKt() {
		return kt;
	}
	public void setKt(int kt) {
		this.kt = kt;
	}
	public Cjk() {
		super();
	}
	public Cjk(String xuehao, int yx, int zy, int oDj, int year, int kt) {
		super();
		this.xuehao = xuehao;
		this.yx = yx;
		this.zy = zy;
		this.oDj = oDj;
		this.year = year;
		this.kt = kt;
	}
}
