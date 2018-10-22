package dao;

import java.util.List;

import domain.Yuanxi;

public interface YuanxiDao {
	public List<Yuanxi> findYuanxiByName(String YuanxiName);

	/**
	 * 增加一个院系
	 * 
	 * @param yuanxi院系实体
	 * @return 增加结果
	 */
	public boolean addYuanxi(Yuanxi yuanxi);

	/**
	 * 删除院系
	 * 
	 * @return 删除结果
	 */
	public boolean deleteYuanxiByname(Yuanxi yuanxi);

	/**
	 * 按名字查找院系
	 * @param name
	 * @return
	 */
	public Yuanxi findByName(String name);
	
	/**
	 * 更新院系信息
	 * 
	 * @param yuanxi
	 * @return 更新结果
	 */
	public boolean updateYuanxi(Yuanxi yuanxi);
   
	/**
	 * 查找所有院系
	 * @return
	 */
	public List<Yuanxi> findAllYuanxi();
	
	/**
	 * 查询总数
	 * @return
	 */
	public int count();
	/**
	 * 分页查询院系
	 * @param pageNum 页码
	 * @param rowCount 每页数据条数
	 * @return
	 */
	public List<Yuanxi> findInPage(int pageNum,int rowCount);
}
