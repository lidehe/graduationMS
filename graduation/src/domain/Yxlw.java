package domain;
/**
 * 优秀论文
 * @author DaMoTou
 *
 */
public class Yxlw {
	private int id;
	private String xuehao;
	private int lw = 0;
	private int yx = 0;
	private int zy = 0;
	private int status = 0;
	private float yfs = 0;
	private float xfs = 0;
	private int year = 0;
	private int pm = 0;
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
	public int getLw() {
		return lw;
	}
	public void setLw(int lw) {
		this.lw = lw;
	}
	public int getYx() {
		return yx;
	}
	public void setYx(int yx) {
		this.yx = yx;
	}
	public int getZy() {
		return zy;
	}
	public void setZy(int zy) {
		this.zy = zy;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public float getYfs() {
		return yfs;
	}
	public void setYfs(float yfs) {
		this.yfs = yfs;
	}
	public float getXfs() {
		return xfs;
	}
	public void setXfs(float xfs) {
		this.xfs = xfs;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getPm() {
		return pm;
	}
	public void setPm(int pm) {
		this.pm = pm;
	}
	public Yxlw() {
		super();
	}
	public Yxlw(String xuehao, int lw, int yx, int zy, int status, float yfs, float xfs, int year, int pm) {
		super();
		this.xuehao = xuehao;
		this.lw = lw;
		this.yx = yx;
		this.zy = zy;
		this.status = status;
		this.yfs = yfs;
		this.xfs = xfs;
		this.year = year;
		this.pm = pm;
	}
}