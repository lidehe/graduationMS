package dao;

import java.util.List;

import domain.Yuanxi;

public class YuanxiDaoImpl extends BaseDAO<Yuanxi> implements YuanxiDao {
    
	@Override
	public List<Yuanxi> findYuanxiByName(String YuanxiName) {
		String hql="from Yuanxi where name like '"+YuanxiName+"'";
		return super.find(hql);
	}

	@Override
	public boolean addYuanxi(Yuanxi yuanxi) {
		super.save(yuanxi);
		return false;
	}

	@Override
	public boolean deleteYuanxiByname(Yuanxi yuanxi) {
		super.delete(yuanxi);;
		return false;
	}

	@Override
	public boolean updateYuanxi(Yuanxi yuanxi) {
		super.update(yuanxi);
		return false;
	}

	@Override
	public List<Yuanxi> findAllYuanxi() {
		String hql="from Yuanxi";
		return super.find(hql);
	}

	@Override
	public Yuanxi findByName(String name) {
		String hql="from Yuanxi where name=? ";
		Object [] param=new Object[]{name};
		return super.get(hql, param);
	}

	
	@Override
	public int count() {
		String hql="select count(*) from Yuanxi";
		return super.count(hql);
	}

	@Override
	public List<Yuanxi> findInPage( int pageNum,int rowCount) {
		int total=count();
		String hql="from Yuanxi";
		return super.findInPage(hql,total, pageNum, rowCount);
	}

}
