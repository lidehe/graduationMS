package domain;
/**
 * 角色关系
 * @author DaMoTou
 *
 */
public class Jsgx {
	private int id;
	private String gonghao;
	private int js;
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
	public int getJs() {
		return js;
	}
	public void setJs(int js) {
		this.js = js;
	}
	public Jsgx() {
		super();
	}
	public Jsgx(String gonghao, int js) {
		super();
		this.gonghao = gonghao;
		this.js = js;
	}
}
