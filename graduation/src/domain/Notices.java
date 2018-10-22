package domain;

public class Notices {
	private int id;
	private int noticeId = 0;
	private String fileName = "";
	private String url = "";
	private int year = 0;
	private int status = 0;
	public Notices() {
		super();
	}
	public Notices(int noticeId, String fileName, String url, int year, int status) {
		super();
		this.noticeId = noticeId;
		this.fileName = fileName;
		this.url = url;
		this.year = year;
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(int noticeId) {
		this.noticeId = noticeId;
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
}
