package dao;

import java.util.List;

import domain.Dbjsz;

public class DbjszDaoImpl extends BaseDAO<Dbjsz>{

	/**
	 * 添加答辩教师
	 * @param dbjsz
	 */
	public void add(Dbjsz dbjsz){
		super.save(dbjsz);
	}
	/**
	 * 删除答辩教师组
	 */
	public void delete(Dbjsz dbjsz){
		super.delete(dbjsz);
	}
	
	/**
	 * 根据dbzl的id，查找同一组的教师
	 * @param dbzId
	 * @return
	 */
	public List<Dbjsz> findByDbzId(int dbzId){
		String hql="from Dbjsz where zh=? ";
		Object [] param=new Object[]{dbzId};
		return super.find(hql, param);
	}
	
	
}
