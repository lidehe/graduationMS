package dao;

import java.util.List;

import domain.Teacher;

/**
 * 教师
 * @author DaMoTou
 *
 */
public interface TeacherDao {

	/**
	 * 添加一个教师
	 * @param teacher
	 */
	public void addTeacher(Teacher teacher);
	
	/**
	 * 删除一个教师
	 * @param teacher
	 */
	public void deleteTeacher(Teacher teacher);
	
	/**
	 * 更新教师信息
	 * @param teacher
	 */
	public void updateTeacher(Teacher teacher);
	
	/**
	 * 按院系ID查找教师
	 * @param yuanxi
	 * @return
	 */
	public List<Teacher> findTeacherByYuanxi(int YuanxiID);
	
	/**
	 * 根据工号查找一个教师
	 * @param num 工号
	 * @return
	 */
	public Teacher findTeacherByNum(String num);
	
	/**
	 * 根据名字模糊搜索教师
	 * @param name
	 * @return
	 */
	public List<Teacher> vagueSearchByName(String name);
	
	public Teacher findTeahcerById(int id);
	
	/**
	 * 查数据总数
	 * @return
	 */
	public int count();
	
	/**
	 * 分页查询
	 * @param pageNum
	 * @param rowCount
	 * @return
	 */
	public List<Teacher> findInPage(int pageNum,int rowCount);
	
	/**
	 * 按照院系ID分页搜索教师----------有错误
	 * @param pageNum
	 * @param rowCount
	 * @param yuanxiId
	 * @return
	 * 
	 * 
	 */
	public List<Teacher> findInPageByParam(int pageNum,int rowCount,int yuanxiId);
}
