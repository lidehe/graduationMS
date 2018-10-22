package domain;
/**
 * 账号类
 * @author DaMoTou
 *
 */
public class Account {
	
	private int id;
	private String account;
	private String password;
	private int type = 0;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Account() {
		super();
	}
	public Account(String account, String password, int type) {
		super();
		this.account = account;
		this.password = password;
		this.type = type;
	}
}
