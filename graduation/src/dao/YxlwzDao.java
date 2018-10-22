package dao;

import java.util.List;

import domain.Yxlwz;

public class YxlwzDao extends BaseDAO<Yxlwz>{

	public void save(Yxlwz yxlwz){
		super.save(yxlwz);
	}
	
	public void update(Yxlwz yxlwz){
		super.update(yxlwz);
	}
	
	public Yxlwz findById(int id){
		String hql="from Yxlwz where id = ?";
		Object [] param={id};
		return super.get(hql,param);
	}
	
	/**
	 * 查找该院系还未推荐到学校的小组论文
	 * @param yuanxiId
	 * @return
	 */
    public List<Yxlwz> findYxlwzByYuanxiID(int yuanxiId) {
    	String hql="from Yxlwz where yx = ? and status = 0 ";
		Object [] param={yuanxiId};
		return super.find(hql,param);
	}
}
