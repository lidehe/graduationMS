package domain;
/**
 * 专业
 * @author DaMoTou
 *
 */
public class Zhuanye {
	private int id;
	private String name;
	private int yx = 0;
	private String dm = "";
	private int year = 0;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getYx() {
		return yx;
	}
	public void setYx(int yx) {
		this.yx = yx;
	}
	public String getDm() {
		return dm;
	}
	public void setDm(String dm) {
		this.dm = dm;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public Zhuanye() {
		super();
	}
	public Zhuanye(String name, int yx, String dm, int year) {
		super();
		this.name = name;
		this.yx = yx;
		this.dm = dm;
		this.year = year;
	}
}
