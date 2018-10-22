package domain;


public class Sxcj {
	private int id;
	private String gonghao;
	private String xuehao;
	private int sxcjx = 0;
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
	public int getSxcjx() {
		return sxcjx;
	}
	public void setSxcjx(int sxcjx) {
		this.sxcjx = sxcjx;
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
	public Sxcj() {
		super();
	}
	public Sxcj(String gonghao, String xuehao, int sxcjx, float fs, String text) {
		super();
		this.gonghao = gonghao;
		this.xuehao = xuehao;
		this.sxcjx = sxcjx;
		this.fs = fs;
		this.text = text;
	}
}
