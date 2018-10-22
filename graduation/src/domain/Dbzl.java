package domain;

import java.util.Date;

/**
 * 答辩组
 * @author DaMoTou
 *
 */
public class Dbzl {
	private int id;
	private String name;
	private int yx;
	private int zy=0;
	private int type = 0;
	private Date start;
	private Date end;
	private String ms;
	private String sj;
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
	public int getZy() {
		return zy;
	}
	public void setZy(int zy) {
		this.zy = zy;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public String getMs() {
		return ms;
	}
	public void setMs(String ms) {
		this.ms = ms;
	}
	public String getSj() {
		return sj;
	}
	public void setSj(String sj) {
		this.sj = sj;
	}
	public Dbzl() {
		super();
	}
	public Dbzl(String name, int yx, int zy, int type, Date start, Date end, String ms, String sj) {
		super();
		this.name = name;
		this.yx = yx;
		this.zy = zy;
		this.type = type;
		this.start = start;
		this.end = end;
		this.ms = ms;
		this.sj = sj;
	}
	
}
