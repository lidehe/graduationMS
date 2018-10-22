package dao;

import java.util.List;

import domain.Cjrz;

public interface CjrzDao {

	/**
	 * 添加抽检日志
	 * @param cjrz
	 */
	public void addCjrz(Cjrz cjrz);
	
	/**
	 * 删除抽检日志
	 * @param cjrz
	 */
	public void deleteCjrz(Cjrz cjrz);
	/**
	 * 根据id查询抽检日志
	 * @param id
	 * @return
	 */
	public Cjrz findById(int id);
	/**
	 * 获取所有抽检日志
	 * @return
	 */
	public List<Cjrz> findAll();
	/**
	 * 根据教师工号查找抽检日志
	 * @param gonghao
	 * @return
	 */
	public List<Cjrz> findByGonghao(String gonghao);
	
	/**
	 * 根据学生学号查找该同学所有抽检成绩
	 * @param xuehao
	 * @return
	 */
	public List<Cjrz> findByXuehao(String xuehao);
}
