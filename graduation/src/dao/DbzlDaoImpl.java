package dao;

import java.util.List;

import domain.Dbzl;

public class DbzlDaoImpl extends BaseDAO<Dbzl> {

	/**
	 * 保存答辩组
	 * @param dbzl
	 */
	public void add(Dbzl dbzl){
		super.save(dbzl);
	}
	/**
	 * 删除答辩组
	 */
	public void delete(Dbzl dbzl){
		super.delete(dbzl);
	}
	/**
	 * 按id查答辩组
	 * @param id
	 * @return
	 */
	public Dbzl findById(int id){
		String hql="from Dbzl where id=? ";
		Object [] param=new Object[]{id};
		return super.get(hql, param);
	}
	
	/**
	 * 按名字查答辩组
	 * @param name
	 * @return
	 */
	public Dbzl findByName(String  name){
		String hql="from Dbzl where name=? ";
		Object [] param=new Object[]{name};
		return super.get(hql, param);
	}
	/**
	 * 根据院系id找该院系的答辩组
	 * @param yuanxiId
	 * @return
	 */
	public List<Dbzl> findByYuanxiId(int yuanxiId){
		String hql="from Dbzl where yx = ? ";
		Object [] param=new Object[]{yuanxiId};
		return super.find(hql, param);
	}
	/**
	 * 查所有答辩组
	 * @return
	 */
	public List<Dbzl> findAll(){
		String hql="from Dbzl";
		return super.find(hql);
	}
}
