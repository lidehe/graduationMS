package dao;

import java.util.List;

import domain.Byfs;

public class ByfsDaoImpl extends BaseDAO<Byfs> implements ByfsDao {

	@Override
	public void addByfs(Byfs byfs) {
		super.save(byfs);

	}

	@Override
	public void deleteByfs(Byfs byfs) {
        super.delete(byfs);
	}

	@Override
	public List<Byfs> findAllByfs() {
		String hql="from Byfs ";
		return super.find(hql);
	}

	@Override
	public Byfs findByfsByID(int id) {
		String hql="from Byfs where id=? ";
		Object[] object=new Object[]{id};
		return super.get(hql, object);
	}

	@Override
	public void updateByfs(Byfs byfs) {
		super.update(byfs);

	}

	@Override
	public Byfs findyByName(String name) {
		String hql="from Byfs where name=? ";
		Object[] object=new Object[]{name};
		return super.get(hql, object);
	}

}
