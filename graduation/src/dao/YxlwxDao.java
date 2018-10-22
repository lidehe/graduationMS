package dao;

import domain.Yxlwx;

public class YxlwxDao extends BaseDAO<Yxlwx>{

	/**
	 * 增加一个论文数量限制
	 */
	public void save(Yxlwx yxlwx){
		super.save(yxlwx);
	}
	
	/**
	 * 更新论文数量限制
	 */
	public void update(Yxlwx yxlwx){
		super.update(yxlwx);
	}
	
	/**
	 * 按照院系和类型查询，类型有个人（0）和小组（1）
	 * @param yxId
	 * @param type
	 * @return
	 */
	public Yxlwx findByYxAndType(int yxId,int type){
		String hql="from Yxlwx where yx = ? and gx = ? ";
		Object [] param={yxId,type};
		return super.get(hql, param);
	}
	
	
}
