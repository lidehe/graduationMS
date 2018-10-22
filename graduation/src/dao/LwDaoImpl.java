package dao;

import domain.Lw;

public class LwDaoImpl extends BaseDAO<Lw> {

	public Lw findById(int id){
		String hql="from LW where id=? ";
		Object [] param=new Object[]{id};
		return super.get(hql, param);
	}
}
