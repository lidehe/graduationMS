package domain;

public class Cjbd {
	private int id;
	private String gonghao;
	private int yx = 0;
	private int year = 0;
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
	public int getYx() {
		return yx;
	}
	public void setYx(int yx) {
		this.yx = yx;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public Cjbd() {
		super();
	}
	public Cjbd(String gonghao, int yx, int year) {
		super();
		this.gonghao = gonghao;
		this.yx = yx;
		this.year = year;
	}
	@Override
	public String toString() {
		return "Cjbd [id=" + id + ", gonghao=" + gonghao + ", yx=" + yx + ", year=" + year + "]";
	}
	
	
}
