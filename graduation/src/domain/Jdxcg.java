package domain;
/**
 * 阶段性成果
 * @author DaMoTou
 *
 */
public class Jdxcg {
	private int id;
	private String xuehao;
	private int status = 0;
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
	public Jdxcg() {
		super();
	}
	public Jdxcg(String xuehao, int status, String fileName) {
		super();
		this.xuehao = xuehao;
		this.status = status;
		this.fileName = fileName;
	}
}
