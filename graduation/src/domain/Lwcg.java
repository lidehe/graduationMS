package domain;

public class Lwcg {
	private int id;
	private String xuehao;
	private String gonghao;
	private int yx = 0;
	private int zy = 0;
	private int status = 0;
	private String fileName = "";
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
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
	public Lwcg() {
		super();
	}
	public Lwcg(String xuehao, String gonghao, int yx, int zy, int status, String fileName, int year, int kt) {
		super();
		this.xuehao = xuehao;
		this.gonghao = gonghao;
		this.yx = yx;
		this.zy = zy;
		this.status = status;
		this.fileName = fileName;
		this.year = year;
		this.kt = kt;
	}
}
