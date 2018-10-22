package domain;

public class Lws {
	private int id;
	private int lw = 0;
	private int status = 0;
	private String fileName = "";
	private String url = "";
	private String xuehao = "";
	private int year = 0;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLw() {
		return lw;
	}
	public void setLw(int lw) {
		this.lw = lw;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
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
	public Lws() {
		super();
	}
	public Lws(int lw, int status, String fileName, String url, String xuehao, int year) {
		super();
		this.lw = lw;
		this.status = status;
		this.fileName = fileName;
		this.url = url;
		this.xuehao = xuehao;
		this.year = year;
	}
}
