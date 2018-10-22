package domain;
/**
 * 指导老师评审表
 */

public class Zdlspsb {
	private int id;
	private String gonghao;
	private String xuehao;
	private int zdlspsbx = 0;
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
	public int getZdlspsbx() {
		return zdlspsbx;
	}
	public void setZdlspsbx(int zdlspsbx) {
		this.zdlspsbx = zdlspsbx;
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
	public Zdlspsb() {
		super();
	}
	public Zdlspsb(String gonghao, String xuehao, int zdlspsbx, float fs, String text) {
		super();
		this.gonghao = gonghao;
		this.xuehao = xuehao;
		this.zdlspsbx = zdlspsbx;
		this.fs = fs;
		this.text = text;
	}
	
}