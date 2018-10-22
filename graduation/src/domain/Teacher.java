package domain;
/**
 * 老师
 * @author DaMoTou
 *
 */
public class Teacher {
	private int id;
	private String number;
	private String name;
	private int sex = 0;
	private String identityN;
	private int yx = 0;
	private String tel;
	private String email;
	private int year;
	private int fzr = 0;
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
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getFzr() {
		return fzr;
	}
	public void setFzr(int fzr) {
		this.fzr = fzr;
	}
	public Teacher() {
		super();
	}
	public Teacher(String number, String name, int sex, String identityN, int yx, String tel, String email, int year,
			int fzr) {
		super();
		this.number = number;
		this.name = name;
		this.sex = sex;
		this.identityN = identityN;
		this.yx = yx;
		this.tel = tel;
		this.email = email;
		this.year = year;
		this.fzr = fzr;
	}
	@Override
	public String toString() {
		return "Teacher [id=" + id + ", number=" + number + ", name=" + name + ", sex=" + sex + ", identityN="
				+ identityN + ", yx=" + yx + ", tel=" + tel + ", email=" + email + ", year=" + year + ", fzr=" + fzr
				+ "]";
	}
	
}
