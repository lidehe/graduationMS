package dao;

import java.util.List;

import domain.Cjbd;

public class CjbdDao extends BaseDAO<Cjbd>{

	/**
	 * 添加抽检绑定
	 */
	public void save(Cjbd cjbd){
		super.save(cjbd);
	}
	/**
	 * 删除抽检绑定
	 */
	public void delete(Cjbd cjbd){
		super.delete(cjbd);
	}
	
	/**
	 * 按照工号和院系ID查询抽检绑定记录
	 * @param gonghao
	 * @param yxId
	 * @return
	 */
	public Cjbd findByGonghaoAndYxId(String gonghao,int yxId){
		String hql="from Cjbd where gonghao = ? and yx = ? ";
		Object [] param={gonghao,yxId};
		return super.get(hql, param);
	}
	
	/**
	 * 根据工号查找一个老师绑定的所有抽检绑定记录
	 * @param gonghao
	 * @return
	 */
	public List<Cjbd> findByGonghao(String gonghao){
		String hql="from Cjbd where gonghao = ? ";
		Object [] param={gonghao};
		return super.find(hql, param);
	}
	/**
	 * 查询所有抽检绑定记录
	 * @return
	 */
	public List<Cjbd> findAll(){
		String hql="from Cjbd  ";
		return super.find(hql);
	}
	
}
