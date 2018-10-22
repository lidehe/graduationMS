package domain;
/**
 * 论文
 * @author DaMoTou
 *
 */
public class Lw {
	private int id;
	private String xuehao;
	private String gonghao;
	private int yx = 0;
	private int zy = 0;
	private int year = 0;
	private int status = 0;
	private int kt = 0;
	private String fileName = "";
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
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getKt() {
		return kt;
	}
	public void setKt(int kt) {
		this.kt = kt;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Lw() {
		super();
	}
	public Lw(String xuehao, String gonghao, int yx, int zy, int year, int status, int kt, String fileName) {
		super();
		this.xuehao = xuehao;
		this.gonghao = gonghao;
		this.yx = yx;
		this.zy = zy;
		this.year = year;
		this.status = status;
		this.kt = kt;
		this.fileName = fileName;
	}
}
