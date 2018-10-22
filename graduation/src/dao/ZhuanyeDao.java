package dao;

import java.util.List;

import domain.Zhuanye;

public interface ZhuanyeDao {

	/**
	 * 添加专业
	 * @param zhuanye
	 */
	public void addZhuanye(Zhuanye zhuanye);
	
	/**
	 * 删除专业
	 * @param zhuanye
	 */
	public void deleteZhuanye(Zhuanye zhuanye);
	
	/**
	 * 更新专业信息
	 * @param zhuanye
	 */
	public void updateZhuanye(Zhuanye zhuanye);
	
	/**
	 * 根据id找专业
	 * @param id
	 * @return
	 */
	public Zhuanye findZhuanyeById(int id);
	
	/**
	 * 根据名字找专业
	 * @param name
	 * @return
	 */
	public Zhuanye findZhuanyeByName(String name);
	
	/**
	 * 找到所有专业
	 * @return
	 */
	public List<Zhuanye> findAllZhuanye();
	
	/**
	 * 根据院系找专业
	 * @param yuanxiId
	 * @return
	 */
	public List<Zhuanye> findByYuanxiId(int yuanxiId);
	
	/**
	 * 查找数据总数
	 * @return
	 */
	public int count();
	
	/**
	 * 分页查询
	 * @param pageNum
	 * @param rowCount
	 * @return
	 */
	public List<Zhuanye> findInPage(int pageNum,int rowCount);
}
