package domain;
/**
 * 评优日志
 */
import java.sql.Timestamp;

public class Pyrz {
	private int id;
	private int lw = 0;
	private int gx = 0;
	private String xuehao;
	private int zuhao = 0;
	private String gonghao;
	private Timestamp time;
	private String text;
	private float fs = 0;
	private int year = 0;
	private int xy = 0;
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
	public int getGx() {
		return gx;
	}
	public void setGx(int gx) {
		this.gx = gx;
	}
	public String getXuehao() {
		return xuehao;
	}
	public void setXuehao(String xuehao) {
		this.xuehao = xuehao;
	}
	public int getZuhao() {
		return zuhao;
	}
	public void setZuhao(int zuhao) {
		this.zuhao = zuhao;
	}
	public String getGonghao() {
		return gonghao;
	}
	public void setGonghao(String gonghao) {
		this.gonghao = gonghao;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public float getFs() {
		return fs;
	}
	public void setFs(float fs) {
		this.fs = fs;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getXy() {
		return xy;
	}
	public void setXy(int xy) {
		this.xy = xy;
	}
	public Pyrz() {
		super();
	}
	public Pyrz(int lw, int gx, String xuehao, int zuhao, String gonghao, Timestamp time, String text, float fs,
			int year, int xy) {
		super();
		this.lw = lw;
		this.gx = gx;
		this.xuehao = xuehao;
		this.zuhao = zuhao;
		this.gonghao = gonghao;
		this.time = time;
		this.text = text;
		this.fs = fs;
		this.year = year;
		this.xy = xy;
	}
}