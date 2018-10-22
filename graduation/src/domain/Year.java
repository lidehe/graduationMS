package domain;
/**
 * 年
 * @author DaMoTou
 *
 */
public class Year {
	private int id;
	private int year;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public Year() {
		super();
	}
	public Year(int year) {
		super();
		this.year = year;
	}
}
