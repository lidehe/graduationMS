package dao;

import java.util.List;

import domain.Cjk;

public interface CjkDao {

	/**\
	 * 添加抽检库记录
	 * @param cjk
	 */
	public void save(Cjk cjk);
	
	/**
	 * 根据ID找一条抽检库记录
	 * @param id
	 * @return
	 */
	public Cjk findById(int id);
	
	/**
	 * 根据学生学号查询抽检库记录
	 * @param number
	 * @return
	 */
	public Cjk findByStudentNumber(String number);
	
	/**
	 * 根据院系查询多条抽检库记录
	 * @param yxId
	 * @return
	 */
	public List<Cjk> findByYxId(int yxId);
	
	/**
	 * 根据打分等级查询多条抽检库记录
	 * @param dj
	 * @return
	 */
	public List<Cjk> findByDj(int dj);
	
	/**
	 * 统计总数
	 * @return
	 */
	public int count();
	
	/**
	 * 分页查询抽检库记录
	 * @param pageNum
	 * @param rowCount
	 * @return
	 */
	public List<Cjk> findInPage(int pageNum, int rowCount);
	
}
