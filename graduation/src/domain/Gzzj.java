package domain;
/**
 * 工作总结
 */
import java.sql.Timestamp;

public class Gzzj {
	private int id;
	private String gonghao;
	private int yx = 0;
	private int year = 0;
	private Timestamp time;
	private String fileName;
	private String url;
	private int status = 0;
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
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Gzzj() {
		super();
	}
	public Gzzj(String gonghao, int yx, int year, Timestamp time, String fileName, String url, int status) {
		super();
		this.gonghao = gonghao;
		this.yx = yx;
		this.year = year;
		this.time = time;
		this.fileName = fileName;
		this.url = url;
		this.status = status;
	}
}