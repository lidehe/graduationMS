package domain;
/**
 * 总分
 * @author DaMoTou
 *
 */
public class Zf {
	private int id;
	private String xuehao;
	private int year;
	private int yx = 0;
	private int zy = 0;
	private float zf = 0;
	private int dj = 0;
	private float lw = 0;
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
	public int getZy() {
		return zy;
	}
	public void setZy(int zy) {
		this.zy = zy;
	}
	public float getZf() {
		return zf;
	}
	public void setZf(float zf) {
		this.zf = zf;
	}
	public int getDj() {
		return dj;
	}
	public void setDj(int dj) {
		this.dj = dj;
	}
	public float getLw() {
		return lw;
	}
	public void setLw(float lw) {
		this.lw = lw;
	}
	public Zf() {
		super();
	}
	public Zf(String xuehao, int year, int yx, int zy, float zf, int dj, float lw) {
		super();
		this.xuehao = xuehao;
		this.year = year;
		this.yx = yx;
		this.zy = zy;
		this.zf = zf;
		this.dj = dj;
		this.lw = lw;
	}
}
