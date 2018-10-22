package domain;

import java.util.Date;

/**
 * 毕业方式
 * @author DaMoTou
 *
 */
public class Byfs {
	private int id;
	private String name;
	private String yi;
	private String er;
	private String san;
	private String si;
	private String wu;
	private String liu;
	private String qi;
	private String ba;
	private String jiu;
	private String shi;
	private String shiyi;
	private String shier;
	private String shisan;
	private String shisi;
	private int isPass1 = 0;//模块2是否被省略,0是省略，1是不省略
	private int isPass2 = 0;//模块3是否被省略
	private int isPass3 = 0;//模块4是否被省略
	private Date start1;
	private Date end1;
	private Date start2;
	private Date end2;
	private Date start3;
	private Date end3;
	private Date start4;
	private Date end4;
	private Date start5;
	private Date end5;
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

	public String getYi() {
		return yi;
	}

	public void setYi(String yi) {
		this.yi = yi;
	}

	public String getEr() {
		return er;
	}

	public void setEr(String er) {
		this.er = er;
	}

	public String getSan() {
		return san;
	}

	public void setSan(String san) {
		this.san = san;
	}

	public String getSi() {
		return si;
	}

	public void setSi(String si) {
		this.si = si;
	}

	public String getWu() {
		return wu;
	}

	public void setWu(String wu) {
		this.wu = wu;
	}

	public String getLiu() {
		return liu;
	}

	public void setLiu(String liu) {
		this.liu = liu;
	}

	public String getQi() {
		return qi;
	}

	public void setQi(String qi) {
		this.qi = qi;
	}

	public String getBa() {
		return ba;
	}

	public void setBa(String ba) {
		this.ba = ba;
	}

	public String getJiu() {
		return jiu;
	}

	public void setJiu(String jiu) {
		this.jiu = jiu;
	}

	public String getShi() {
		return shi;
	}

	public void setShi(String shi) {
		this.shi = shi;
	}

	public String getShiyi() {
		return shiyi;
	}

	public void setShiyi(String shiyi) {
		this.shiyi = shiyi;
	}

	public String getShier() {
		return shier;
	}

	public void setShier(String shier) {
		this.shier = shier;
	}

	public String getShisan() {
		return shisan;
	}

	public void setShisan(String shisan) {
		this.shisan = shisan;
	}

	public String getShisi() {
		return shisi;
	}

	public void setShisi(String shisi) {
		this.shisi = shisi;
	}

	public int getIsPass1() {
		return isPass1;
	}

	public void setIsPass1(int isPass1) {
		this.isPass1 = isPass1;
	}

	public int getIsPass2() {
		return isPass2;
	}

	public void setIsPass2(int isPass2) {
		this.isPass2 = isPass2;
	}

	public int getIsPass3() {
		return isPass3;
	}

	public void setIsPass3(int isPass3) {
		this.isPass3 = isPass3;
	}

	public Date getStart1() {
		return start1;
	}

	public void setStart1(Date start1) {
		this.start1 = start1;
	}

	public Date getEnd1() {
		return end1;
	}

	public void setEnd1(Date end1) {
		this.end1 = end1;
	}

	public Date getStart2() {
		return start2;
	}

	public void setStart2(Date start2) {
		this.start2 = start2;
	}

	public Date getEnd2() {
		return end2;
	}

	public void setEnd2(Date end2) {
		this.end2 = end2;
	}

	public Date getStart3() {
		return start3;
	}

	public void setStart3(Date start3) {
		this.start3 = start3;
	}

	public Date getEnd3() {
		return end3;
	}

	public void setEnd3(Date end3) {
		this.end3 = end3;
	}

	public Date getStart4() {
		return start4;
	}

	public void setStart4(Date start4) {
		this.start4 = start4;
	}

	public Date getEnd4() {
		return end4;
	}

	public void setEnd4(Date end4) {
		this.end4 = end4;
	}

	public Date getStart5() {
		return start5;
	}

	public void setStart5(Date start5) {
		this.start5 = start5;
	}

	public Date getEnd5() {
		return end5;
	}

	public void setEnd5(Date end5) {
		this.end5 = end5;
	}

	public Byfs() {
		super();
	}

	
	public Byfs(String name, String yi, String er, String san, String si, String wu, String liu, String qi, String ba,
			String jiu, String shi, String shiyi, String shier, String shisan, String shisi, int isPass1, int isPass2,
			int isPass3, Date start1, Date end1, Date start2, Date end2, Date start3, Date end3, Date start4, Date end4,
			Date start5, Date end5) {
		super();
		this.name = name;
		this.yi = yi;
		this.er = er;
		this.san = san;
		this.si = si;
		this.wu = wu;
		this.liu = liu;
		this.qi = qi;
		this.ba = ba;
		this.jiu = jiu;
		this.shi = shi;
		this.shiyi = shiyi;
		this.shier = shier;
		this.shisan = shisan;
		this.shisi = shisi;
		this.isPass1 = isPass1;
		this.isPass2 = isPass2;
		this.isPass3 = isPass3;
		this.start1 = start1;
		this.end1 = end1;
		this.start2 = start2;
		this.end2 = end2;
		this.start3 = start3;
		this.end3 = end3;
		this.start4 = start4;
		this.end4 = end4;
		this.start5 = start5;
		this.end5 = end5;
	}

	@Override
	public String toString() {
		return "Byfs [id=" + id + ", name=" + name + ", yi=" + yi + ", er=" + er + ", san=" + san + ", si=" + si
				+ ", wu=" + wu + ", liu=" + liu + ", qi=" + qi + ", ba=" + ba + ", jiu=" + jiu + ", shi=" + shi
				+ ", shiyi=" + shiyi + ", shier=" + shier + ", shisan=" + shisan + ", shisi=" + shisi + ", isPass1="
				+ isPass1 + ", isPass2=" + isPass2 + ", isPass3=" + isPass3 + ", start1=" + start1 + ", end1=" + end1
				+ ", start2=" + start2 + ", end2=" + end2 + ", start3=" + start3 + ", end3=" + end3 + ", start4="
				+ start4 + ", end4=" + end4 + ", start5=" + start5 + ", end5=" + end5 + "]";
	}

	
}
