package domain;

/**
 * 优秀论文数量限制
 * @author DaMoTou
 *
 */
public class Yxlwx {
	private int id;
	private int yx = 0;
	private int sl = 0;
	private int gx = 0;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getYx() {
		return yx;
	}
	public void setYx(int yx) {
		this.yx = yx;
	}
	public int getSl() {
		return sl;
	}
	public void setSl(int sl) {
		this.sl = sl;
	}
	public int getGx() {
		return gx;
	}
	public void setGx(int gx) {
		this.gx = gx;
	}
	public Yxlwx() {
		super();
	}
	public Yxlwx(int yx, int sl, int gx) {
		super();
		this.yx = yx;
		this.sl = sl;
		this.gx = gx;
	}
}
