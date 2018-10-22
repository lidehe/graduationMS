package domain;
/**
 * 课题
 * @author DaMoTou
 *
 */
public class Kt {
	private int id;
	private String fzrgonghao;
	private String gonghao;
	private int fzrsh = 0;
	private int sh = 0;
	private String name;
	private String text;
	private int shuxing = 0;
	private int yx = 0;
	private int zy = 0;
	private int xzrs = 1;
	private int year = 0;
	private int bylx = 0;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFzrgonghao() {
		return fzrgonghao;
	}
	public void setFzrgonghao(String fzrgonghao) {
		this.fzrgonghao = fzrgonghao;
	}
	public String getGonghao() {
		return gonghao;
	}
	public void setGonghao(String gonghao) {
		this.gonghao = gonghao;
	}
	public int getFzrsh() {
		return fzrsh;
	}
	public void setFzrsh(int fzrsh) {
		this.fzrsh = fzrsh;
	}
	public int getSh() {
		return sh;
	}
	public void setSh(int sh) {
		this.sh = sh;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getShuxing() {
		return shuxing;
	}
	public void setShuxing(int shuxing) {
		this.shuxing = shuxing;
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
	public int getXzrs() {
		return xzrs;
	}
	public void setXzrs(int xzrs) {
		this.xzrs = xzrs;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getBylx() {
		return bylx;
	}
	public void setBylx(int bylx) {
		this.bylx = bylx;
	}
	public Kt() {
		super();
	}
	public Kt(String fzrgonghao, String gonghao, int fzrsh, int sh, String name, String text, int shuxing, int yx,
			int zy, int xzrs, int year, int bylx) {
		super();
		this.fzrgonghao = fzrgonghao;
		this.gonghao = gonghao;
		this.fzrsh = fzrsh;
		this.sh = sh;
		this.name = name;
		this.text = text;
		this.shuxing = shuxing;
		this.yx = yx;
		this.zy = zy;
		this.xzrs = xzrs;
		this.year = year;
		this.bylx = bylx;
	}
}
