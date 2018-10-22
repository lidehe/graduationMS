package domain;
/**
 * 优秀论文小组的
 * @author DaMoTou
 *
 */
public class Yxlwz {
	private int id;
	private int zuhao;
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
	public int getZuhao() {
		return zuhao;
	}
	public void setZuhao(int zuhao) {
		this.zuhao = zuhao;
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
	public Yxlwz() {
		super();
	}
	public Yxlwz(int zuhao, int yx, int zy, int status, float yfs, float xfs, int year, int pm) {
		super();
		this.zuhao = zuhao;
		this.yx = yx;
		this.zy = zy;
		this.status = status;
		this.yfs = yfs;
		this.xfs = xfs;
		this.year = year;
		this.pm = pm;
	}
}