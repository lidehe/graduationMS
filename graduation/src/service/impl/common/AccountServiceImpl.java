package service.impl.common;

import java.util.ArrayList;
import java.util.List;

import dao.AccountDao;
import domain.Account;
import factory.DaoFactory;
import factory.JWCFactory;
import service.AccountService;

public class AccountServiceImpl implements AccountService {
    private AccountDao accountDao=JWCFactory.getAccountDao();
	@Override
	public int login(String account, String password) {
		// TODO Auto-generated method stub
		String hql = "from Account a where a.account = ? ";
		Object[] param = {account};
		List<Account> list = new ArrayList<Account>();
		list = DaoFactory.getAccountDaoInstance().find(hql,param);
		if(list.size() == 0){
			return 3;
			//未找到用户
		}else{
			if(list.get(0).getPassword().equals(password)){
				return list.get(0).getType();
				//登录成功，0老师1学生
			}
			return 2;
			//密码错误
		}
	}

	@Override
	public Account getAccountBYNumber(String account) {
		// TODO Auto-generated method stub
		String hql = " from Account a where a.account = ? ";
		Object[] param = { account };
		Account account2 = DaoFactory.getAccountDaoInstance().get(hql, param);
		return account2;
	}

	@Override
	public void updateAccount(Account account) {
		// TODO Auto-generated method stub
		DaoFactory.getAccountDaoInstance().update(account);
	}

	@Override
	public void saveAccount(Account account) {
		Account account2=null;
		account2=getAccountBYNumber(account.getAccount());
		if(account2==null)//近当当前账户不存在才会保存进去
		accountDao.save(account);
	}

	@Override
	public void deleteAccount(Account account) {
		// TODO Auto-generated method stub
		accountDao.delete(account);
	}
	
	

}
