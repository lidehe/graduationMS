package domain;

public class Sxcjx {
	private int id;
	private String text;
	private int mf = 0;
	private int status = 0;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getMf() {
		return mf;
	}
	public void setMf(int mf) {
		this.mf = mf;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Sxcjx() {
		super();
	}
	public Sxcjx(String text, int mf, int status) {
		super();
		this.text = text;
		this.mf = mf;
		this.status = status;
	}
}
