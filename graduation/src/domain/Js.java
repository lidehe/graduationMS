package domain;
/**
 * 角色
 * @author DaMoTou
 *我们规定1就是指导老师
 *	2就是专业负责人
 *	3就是教学秘书
 *	4就是院长
 *	5就是管理员
 *	6就是评优老师
 *	7就是抽检老师
 *	8就是答辩组成员
 *	9就是抽检领导
 */
public class Js {
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
	public Js() {
		super();
	}
	public Js(String name) {
		super();
		this.name = name;
	}
}
