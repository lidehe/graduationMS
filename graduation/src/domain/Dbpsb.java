package domain;
/**
 * 答辩评审表
 */

public class Dbpsb {
	private int id;
	private String gonghao;
	private String xuehao;
	private int dbpsbx = 0;
	private float fs = 0;
	private String text = "";
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
	public String getXuehao() {
		return xuehao;
	}
	public void setXuehao(String xuehao) {
		this.xuehao = xuehao;
	}
	public int getDbpsbx() {
		return dbpsbx;
	}
	public void setDbpsbx(int dbpsbx) {
		this.dbpsbx = dbpsbx;
	}
	public float getFs() {
		return fs;
	}
	public void setFs(float fs) {
		this.fs = fs;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Dbpsb(String gonghao, String xuehao, int dbpsbx, float fs, String text) {
		super();
		this.gonghao = gonghao;
		this.xuehao = xuehao;
		this.dbpsbx = dbpsbx;
		this.fs = fs;
		this.text = text;
	}
	public Dbpsb() {
		super();
	}
}