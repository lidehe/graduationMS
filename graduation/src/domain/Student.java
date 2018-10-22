package domain;
/**
 * 学生
 * @author DaMoTou
 *
 */
public class Student {
	private int id;
	private String number;
	private String name;
	private int sex = 0;
	private String identityN;
	private int yx = 0;
	private int zy = 0;
	private String bj;
	private String tel;
	private String teacher;
	private int dbz = 0;
	private int isgroup = 0;
	private int stage = 0;
	private int type = -1;
	private int kt = 0;
	private int ktbg = 0;
	private int jdxcg = 0;
	private int bs = 0;
	private int answer = 0;
	private int year = 0;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getIdentityN() {
		return identityN;
	}
	public void setIdentityN(String identityN) {
		this.identityN = identityN;
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
	public String getBj() {
		return bj;
	}
	public void setBj(String bj) {
		this.bj = bj;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	public int getDbz() {
		return dbz;
	}
	public void setDbz(int dbz) {
		this.dbz = dbz;
	}
	public int getIsgroup() {
		return isgroup;
	}
	public void setIsgroup(int isgroup) {
		this.isgroup = isgroup;
	}
	public int getStage() {
		return stage;
	}
	public void setStage(int stage) {
		this.stage = stage;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getKt() {
		return kt;
	}
	public void setKt(int kt) {
		this.kt = kt;
	}
	public int getKtbg() {
		return ktbg;
	}
	public void setKtbg(int ktbg) {
		this.ktbg = ktbg;
	}
	public int getJdxcg() {
		return jdxcg;
	}
	public void setJdxcg(int jdxcg) {
		this.jdxcg = jdxcg;
	}
	public int getBs() {
		return bs;
	}
	public void setBs(int bs) {
		this.bs = bs;
	}
	public int getAnswer() {
		return answer;
	}
	public void setAnswer(int answer) {
		this.answer = answer;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public Student() {
		super();
	}
	public Student(String number, String name, int sex, String identityN, int yx, int zy, String bj, String tel,
			String teacher, int dbz, int isgroup, int stage, int type, int kt, int ktbg, int jdxcg, int bs, int answer,
			int year) {
		super();
		this.number = number;
		this.name = name;
		this.sex = sex;
		this.identityN = identityN;
		this.yx = yx;
		this.zy = zy;
		this.bj = bj;
		this.tel = tel;
		this.teacher = teacher;
		this.dbz = dbz;
		this.isgroup = isgroup;
		this.stage = stage;
		this.type = type;
		this.kt = kt;
		this.ktbg = ktbg;
		this.jdxcg = jdxcg;
		this.bs = bs;
		this.answer = answer;
		this.year = year;
	}
}
