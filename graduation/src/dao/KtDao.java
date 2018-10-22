package dao;

import java.util.List;

import domain.Kt;

public class KtDao extends BaseDAO<Kt>{

	/**
	 * 查找所有课题
	 * @return
	 */
	public List<Kt> findAllKt(){
		String hql="from Kt";
		return super.find(hql);
		
	}
}
