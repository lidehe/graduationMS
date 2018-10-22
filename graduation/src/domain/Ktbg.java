package domain;

/**
 * 开题报告
 * 
 * @author DaMoTou
 *
 */
public class Ktbg {
	private int id;
	private String xuehao;
	private int zy;
	private int kt;
	private String gonghao;
	private int status = 0;
	private int sh = 0;
	private String fileName = "";
	private int year;

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

	public int getZy() {
		return zy;
	}

	public void setZy(int zy) {
		this.zy = zy;
	}

	public int getKt() {
		return kt;
	}

	public void setKt(int kt) {
		this.kt = kt;
	}

	public String getGonghao() {
		return gonghao;
	}

	public void setGonghao(String gonghao) {
		this.gonghao = gonghao;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getSh() {
		return sh;
	}

	public void setSh(int sh) {
		this.sh = sh;
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

	public Ktbg() {
		super();
	}

	public Ktbg(String xuehao, int zy, int kt, String gonghao, int status, int sh, String fileName, int year) {
		super();
		this.xuehao = xuehao;
		this.zy = zy;
		this.kt = kt;
		this.gonghao = gonghao;
		this.status = status;
		this.sh = sh;
		this.fileName = fileName;
		this.year = year;
	}
}
