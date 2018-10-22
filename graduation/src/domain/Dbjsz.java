package domain;
/**
 * 答辩教师组
 * @author DaMoTou
 *
 */
public class Dbjsz {
	private int id;
	private int zh;
	private String gonghao;
	private int year;
	private int yx = 0;
	private int zz = 0;
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
	public String getGonghao() {
		return gonghao;
	}
	public void setGonghao(String gonghao) {
		this.gonghao = gonghao;
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
	public int getZz() {
		return zz;
	}
	public void setZz(int zz) {
		this.zz = zz;
	}
	public Dbjsz() {
		super();
	}
	public Dbjsz(int zh, String gonghao, int year, int yx, int zz) {
		super();
		this.zh = zh;
		this.gonghao = gonghao;
		this.year = year;
		this.yx = yx;
		this.zz = zz;
	}
}
