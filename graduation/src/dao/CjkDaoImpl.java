package dao;

import java.util.List;

import domain.Cjk;

public class CjkDaoImpl extends BaseDAO<Cjk> implements CjkDao {

	@Override
	public void save(Cjk cjk) {
		super.save(cjk);

	}

	@Override
	public Cjk findById(int id) {
		String hql="from Cjk where id = ? ";
		Object [] param={id};
		return super.get(hql, param);
	}

	@Override
	public Cjk findByStudentNumber(String number) {
		String hql="from Cjk where xuehao = ? ";
		Object [] param={number};
		return super.get(hql, param);
	}

	@Override
	public List<Cjk> findByYxId(int yxId) {
		String hql="from Cjk where yx = ? ";
		Object [] param={yxId};
		return super.find(hql, param);
	}

	@Override
	public List<Cjk> findByDj(int dj) {
		String hql="from Cjk where oDj = ? ";
		Object [] param={dj};
		return super.find(hql, param);
	}

	@Override
	public int count() {
		String hql="select count(*) from Cjk";
		return super.count(hql);
	}

	@Override
	public List<Cjk> findInPage(int pageNum, int rowCount) {
		int total=count();
		String hql="from Cjk";
		return super.findInPage(hql,total, pageNum, rowCount);
	}

}
