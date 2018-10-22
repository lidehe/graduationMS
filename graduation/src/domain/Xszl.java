package domain;
/**
 * 小组信息
 * @author DaMoTou
 *
 */
public class Xszl {
	private int id;
	private String name;
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
	public Xszl() {
		super();
	}
	public Xszl(String name) {
		super();
		this.name = name;
	}
}
