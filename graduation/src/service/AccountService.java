package service;

import domain.Account;

public interface AccountService {
	public int login(String account,String password);
	
	public Account getAccountBYNumber(String account);
	
	public void updateAccount(Account account);
	
	public void saveAccount(Account account);
	
	public void deleteAccount(Account account);
}
