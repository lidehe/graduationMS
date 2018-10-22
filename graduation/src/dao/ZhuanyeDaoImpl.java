package dao;

import java.util.List;

import domain.Zhuanye;

public class ZhuanyeDaoImpl extends BaseDAO<Zhuanye> implements ZhuanyeDao {

	@Override
	public void addZhuanye(Zhuanye zhuanye) {
		super.save(zhuanye);
	}

	@Override
	public void deleteZhuanye(Zhuanye zhuanye) {
		super.delete(zhuanye);
	}

	@Override
	public void updateZhuanye(Zhuanye zhuanye) {
		super.update(zhuanye);
	}

	@Override
	public Zhuanye findZhuanyeById(int id) {
		String hql = "from Zhuanye where id =? ";
		Object[] param = new Object[] { id };
		return super.get(hql, param);
	}

	@Override
	public List<Zhuanye> findAllZhuanye() {
		String hql = "from Zhuanye";
		return super.find(hql);
	}

	@Override
	public List<Zhuanye> findByYuanxiId(int yuanxiId) {
		String hql = "from Zhuanye where yx =? ";
		Object[] param = new Object[] { yuanxiId };
		return super.find(hql, param);
	}

	@Override
	public Zhuanye findZhuanyeByName(String name) {
		String hql = "from Zhuanye where name =? ";
		Object[] param = new Object[] { name };
		return super.get(hql, param);
	}

	@Override
	public int count() {
		String hql="select count(*) from Zhuanye";
		return super.count(hql);
	}

	@Override
	public List<Zhuanye> findInPage(int pageNum, int rowCount) {
		int total=count();
		String hql="from Zhuanye";
		return super.findInPage(hql, total, pageNum, rowCount);
	}

}
