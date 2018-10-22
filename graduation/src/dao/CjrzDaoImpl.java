package dao;

import java.util.List;

import domain.Cjrz;

public class CjrzDaoImpl extends BaseDAO<Cjrz> implements CjrzDao {

	@Override
	public void addCjrz(Cjrz cjrz) {
		super.save(cjrz);
	}

	@Override
	public void deleteCjrz(Cjrz cjrz) {
        super.delete(cjrz);
	}

	@Override
	public Cjrz findById(int id) {
		String hql="from Cjrz where id=? ";
		Object [] param=new Object[]{id};
		return super.get(hql, param);
	}

	@Override
	public List<Cjrz> findAll() {
		String hql="from Cjrz";
		return super.find(hql);
	}

	@Override
	public List<Cjrz> findByGonghao(String gonghao) {
		String hql="from Cjrz where gonghao=? ";
		Object [] param=new Object[]{gonghao};
		return super.find(hql, param);
	}

	@Override
	public List<Cjrz> findByXuehao(String xuehao) {
		String hql="from Cjrz where xuehao=? ";
		Object [] param=new Object[]{xuehao};
		return super.find(hql, param);
	}

}
