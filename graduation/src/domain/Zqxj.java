package domain;
/**
 * 中期小结
 */
import java.sql.Timestamp;

public class Zqxj {
	private int id;
	private int yx = 0;
	private String gonghao;
	private int year;
	private Timestamp time;
	private String fileName;
	private String url;
	private int status = 0;
	private String text = "";
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getYx() {
		return yx;
	}
	public void setYx(int yx) {
		this.yx = yx;
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
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Zqxj() {
		super();
	}
	public Zqxj(int yx, String gonghao, int year, Timestamp time, String fileName, String url, int status, String text) {
		super();
		this.yx = yx;
		this.gonghao = gonghao;
		this.year = year;
		this.time = time;
		this.fileName = fileName;
		this.url = url;
		this.status = status;
		this.text = text;
	}
}