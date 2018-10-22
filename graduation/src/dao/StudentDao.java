package dao;

import java.util.List;

import domain.Student;

/**
 * 学生
 * @author DaMoTou
 *
 */
public interface StudentDao {

	/**
	 * 添加学生
	 * @param student
	 */
	public void add(Student student);
	/**
	 * 删除学生
	 * @param student
	 */
	public void Delete(Student student);
	/**
	 * 更新学生信息
	 * @param student
	 */
	public void update(Student student);
	
	/**
	 * 根据学号找学生
	 * @param number
	 * @return
	 */
	public Student findByNumber(String number);
	
	/**
	 * 根据名字模糊找学生
	 * @param number
	 * @return
	 */
	public List<Student> vagueSearchByName(String name);
	
	/**
	 * 根据院系ID找学生
	 * @param yuanxiID
	 * @return
	 */
	public List<Student> findByYuanxi(int yuanxiID);
	/**
	 * 根据班级找学生
	 * @param bj
	 * @return
	 */
	public List<Student> findByClass(String bj);
	/**
	 * 根据专业找学生
	 * @param zy
	 * @return
	 */
	public List<Student> findByZY(int zy);
	/**
	 * 根据ID找学生
	 * @param id
	 * @return
	 */
	public Student findById(int id);
	/**
	 * 查找所有答辩结果
	 * @return
	 */
	public List<Student> findAll();
	
	/**
	 * 数据总数
	 * @return
	 */
	public int count();
	
	/**
	 * 分页查询
	 * @param pageNum
	 * @param rowCount
	 * @return
	 */
	public List<Student> findInPage(int pageNum,int rowCount);
	
	/**
	 * 根据教师工号查找该指导教师带的学生
	 * @param teacherNumber
	 * @return
	 */
	public List<Student> findByTeacherNumber(String teacherNumber);
}
