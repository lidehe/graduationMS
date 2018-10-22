package domain;

/**
 * 院系
 * @author DaMoTou
 *
 */
public class Yuanxi {
	private int id;
	private String name;
	private int year;
	private int status = 0;
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
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Yuanxi() {
		super();
	}
	public Yuanxi(String name, int year, int status) {
		super();
		this.name = name;
		this.year = year;
		this.status = status;
	}
	public Yuanxi(int id, String name, int year, int status) {
		super();
		this.id = id;
		this.name = name;
		this.year = year;
		this.status = status;
	}
	
}
