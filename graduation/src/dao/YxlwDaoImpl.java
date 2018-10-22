package dao;

import java.util.List;

import domain.Yxlw;

public class YxlwDaoImpl extends BaseDAO<Yxlw> {

	public void add(Yxlw yxlw){
		super.save(yxlw);
	}
	
	/**
	 * 更新
	 */
	public void update(Yxlw yxlw){
		super.update(yxlw);
	}
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 */
	public Yxlw findById(int id){
		String hql="from Yxlw where id = ?";
		Object [] param={id};
		return super.get(hql,param);
	}
	
	public Yxlw findByXuehao(String xuehao){
		String hql="from Yxlw where xuehao = ?";
		Object [] param={xuehao};
		return super.get(hql,param);
	}
	
	/**
	 * 查找该院系的还未向校优秀论文
	 * @param yuanxiId
	 * @return
	 */
	public List<Yxlw> findYxlwByYuanxiId(int yuanxiId){
		String hql="from Yxlw where yx = ? and status = 0 ";
		Object [] param={yuanxiId};
		return super.find(hql,param);
	}
	
	
	
	/**
	 * 查找所有
	 * @return
	 */
	public List<Yxlw> findAll(){
		String hql="from Yxlw";
		return super.find(hql);
	}
}
