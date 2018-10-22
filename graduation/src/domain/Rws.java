package domain;

/**
 * 任务书
 * 
 * @author DaMoTou
 *
 */
public class Rws {
	private int id;
	private String gonghao;
	private int kt;
	private int zy;
	private int status = 0;
	private String fileName = "";
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

	public int getKt() {
		return kt;
	}

	public void setKt(int kt) {
		this.kt = kt;
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

	public Rws() {
		super();
	}

	public Rws(String gonghao, int kt, int zy, int status, String fileName, int year) {
		super();
		this.gonghao = gonghao;
		this.kt = kt;
		this.zy = zy;
		this.status = status;
		this.fileName = fileName;
		this.year = year;
	}
}
