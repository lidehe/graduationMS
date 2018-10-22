package dao;

import java.util.List;

import domain.Zqxj;

public class ZqxjDaoImpl extends BaseDAO<Zqxj>{

	public void save(Zqxj zqxj){
		super.save(zqxj);
	}
	
	public void updateZqxj(Zqxj zqxj){
		super.update(zqxj);
	}
	
	public void deleteZqxj(Zqxj zqxj){
		super.delete(zqxj);
	}
	
	/**
	 * 根据Id找总结
	 * @param id
	 * @return
	 */
	public Zqxj findById(int id){
		String hql="from Zqxj where id = ? ";
		Object[] param={id};
		return super.get(hql, param);
	}
	
	/**
	 * 根据院系ID和小结类型查找
	 * @param yuanxiId
	 * @param type   类型有中期小结（zqxj）和最后总结（zhzj）
	 * @return
	 */
	public Zqxj findByYuanxiIdAndType(int yuanxiId,String type){
		String hql="from Zqxj where yx = ? and url = ? ";
		Object[] param={yuanxiId,type};
		return super.get(hql, param);
	}
	
	/**
	 * 查找所有中期小结
	 * @return
	 */
	public List<Zqxj> findAllZqxj(){
		String hql="from Zqxj where url = 'zqxj' ";
		return super.find(hql);
	}
	
	/**
	 * 获取所有最终总结
	 * @return
	 */
	public List<Zqxj> findAllZhzj(){
		String hql="from Zqxj where url = 'zhzj' ";
		return super.find(hql);
	}
}
