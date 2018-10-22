package utils;

/**
 * 一个临时的类，用于把取得的数据中，前段需要展示的部分保存，然后发送到前段
 * 可以灵活组织数据，而且减少不必要的网络数据
 * 提供了不同参数数量的构造方法，可以自己选择需要的目标数目的构造方法
 * @author DaMoTou
 *
 */
public class TempClass {

	private int id;
	private String param1;
	private String param2;
	private String param3;
	private String param4;
	private String param5;
	private String param6;
	private String param7;
	
	
	
	
	public TempClass() {
		super();
	}




	public TempClass(String param1, String param2, String param3) {
		super();
		this.param1 = param1;
		this.param2 = param2;
		this.param3 = param3;
	}




	public TempClass(String param1, String param2, String param3, String param4) {
		super();
		this.param1 = param1;
		this.param2 = param2;
		this.param3 = param3;
		this.param4 = param4;
	}




	public TempClass(String param1, String param2, String param3, String param4, String param5) {
		super();
		this.param1 = param1;
		this.param2 = param2;
		this.param3 = param3;
		this.param4 = param4;
		this.param5 = param5;
	}




	public TempClass(String param1, String param2, String param3, String param4, String param5, String param6) {
		super();
		this.param1 = param1;
		this.param2 = param2;
		this.param3 = param3;
		this.param4 = param4;
		this.param5 = param5;
		this.param6 = param6;
	}




	public TempClass(String param1, String param2, String param3, String param4, String param5, String param6,
			String param7) {
		super();
		this.param1 = param1;
		this.param2 = param2;
		this.param3 = param3;
		this.param4 = param4;
		this.param5 = param5;
		this.param6 = param6;
		this.param7 = param7;
	}




	public int getId() {
		return id;
	}




	public void setId(int id) {
		this.id = id;
	}




	public String getParam1() {
		return param1;
	}




	public void setParam1(String param1) {
		this.param1 = param1;
	}




	public String getParam2() {
		return param2;
	}




	public void setParam2(String param2) {
		this.param2 = param2;
	}




	public String getParam3() {
		return param3;
	}




	public void setParam3(String param3) {
		this.param3 = param3;
	}




	public String getParam4() {
		return param4;
	}




	public void setParam4(String param4) {
		this.param4 = param4;
	}




	public String getParam5() {
		return param5;
	}




	public void setParam5(String param5) {
		this.param5 = param5;
	}




	public String getParam6() {
		return param6;
	}




	public void setParam6(String param6) {
		this.param6 = param6;
	}




	public String getParam7() {
		return param7;
	}




	public void setParam7(String param7) {
		this.param7 = param7;
	}




	@Override
	public String toString() {
		return "TempClass [id=" + id + ", param1=" + param1 + ", param2=" + param2 + ", param3=" + param3 + ", param4="
				+ param4 + ", param5=" + param5 + ", param6=" + param6 + ", param7=" + param7 + "]";
	}
	
	
}
