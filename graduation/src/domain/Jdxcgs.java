package domain;

public class Jdxcgs {
	private int id;
	private String xuehao;
	private int jdxcg = 0;
	private int status = 0;
	private String fileName = "";
	private String url = "";
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
	public int getJdxcg() {
		return jdxcg;
	}
	public void setJdxcg(int jdxcg) {
		this.jdxcg = jdxcg;
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
	public Jdxcgs() {
		super();
	}
	public Jdxcgs(String xuehao, int jdxcg, int status, String fileName, String url) {
		super();
		this.xuehao = xuehao;
		this.jdxcg = jdxcg;
		this.status = status;
		this.fileName = fileName;
		this.url = url;
	}
}
