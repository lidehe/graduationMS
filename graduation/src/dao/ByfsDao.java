package dao;

import java.util.List;

import domain.Byfs;

/**
 * 毕业方式
 * @author DaMoTou
 *
 */
public interface ByfsDao {

	/**
	 * 添加毕业方式
	 * @param byfs
	 */
	public void addByfs(Byfs byfs);
	/**
	 * 删除毕业方式
	 * @param byfs
	 */
	public void deleteByfs(Byfs byfs);
	/**
	 * 查询所有毕业方式
	 * @return
	 */
	public List<Byfs> findAllByfs();
	/**
	 * 根据ID查询毕业方式
	 * @param id
	 * @return
	 */
	public Byfs findByfsByID(int id);
	
	/**
	 * 根据名字查找毕业方式
	 * @param name
	 * @return
	 */
	public Byfs findyByName(String name);
	/**
	 * 更新毕业方式信息
	 * @param byfs
	 */
	public void updateByfs(Byfs byfs);
}
