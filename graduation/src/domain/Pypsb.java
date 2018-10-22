package domain;
/**
 * 评阅评审表
 */

public class Pypsb {
	private int id;
	private String gonghao;
	private String xuehao;
	private int pypsbx = 0;
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
	public int getPypsbx() {
		return pypsbx;
	}
	public void setPypsbx(int pypsbx) {
		this.pypsbx = pypsbx;
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
	public Pypsb() {
		super();
	}
	public Pypsb(String gonghao, String xuehao, int pypsbx, float fs, String text) {
		super();
		this.gonghao = gonghao;
		this.xuehao = xuehao;
		this.pypsbx = pypsbx;
		this.fs = fs;
		this.text = text;
	}
}
