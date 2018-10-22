package service;

import java.util.List;

import domain.Byfs;
import domain.Jdxcg;
import domain.Jdxcgs;
import domain.Kt;
import domain.Ktbg;
import domain.Lw;
import domain.Lwcg;
import domain.Lws;
import domain.Notice;
import domain.Notices;
import domain.Rws;
import domain.Ssjl;
import domain.Student;
import net.sf.json.JSONArray;

public interface StudentService {
	public Student getStudentByAccount(String account);

	public List<Byfs> getAllByfs();

	public Byfs getStudentByfs(int id);

	public void updateStudent(Student student);

	public List<Student> getGroupStudents(String xuehao);

	public void deleteGroup(String xuehao);

	public int createGroup(Student student);

	public int addStudentToGroup(String number, Student student);

	public int removeFromGroup(String number);

	public List<Kt> getTeacherKt(Student student);

	public int chooseKt(int ktId, Student student);

	public Kt getStudentKt(int id);

	public int addKtFromStudent(String name, String text, Student student);

	public Rws getKtRwsByStudent(Student student);

	public Ktbg getStudentKtbg(Student student);

	public int studentKtbg(Student student);

	public void updataKtbgStatus(Ktbg ktbg);

	public Jdxcg getStudentJdxcg(Student student);

	public int studentJdxcg(Student student);

	public void updateJdxcgStatus(Jdxcg jdxcg);

	public Lwcg getStudentLwcg(Student student);

	public Lw getStudentLw(Student student);

	public int studentLw(Student student);

	public int studentLwcg(Student student);

	public void updateLwcgStatus(Lwcg lwcg);

	public void updateLwStatus(Lw lw);

	public List<Notice> getXiNotice();

	public List<Notice> getYxNotice(Student student);

	public List<Notice> getZyNotice(Student student);

	public List<Notice> getZdNotice(Student student);

	public Notice getNotice(int id);

	public Jdxcgs getJdxcgsById(int id);

	public List<Jdxcgs> getJdxcgs(Student student);

	public int getJdxcgsId(Student student);
	
	public int getLwsId(Student student);

	public Jdxcgs getJdxcgs(int id);

	public void updateJdxcgs(Jdxcgs jdxcgs);
	
	public void deleteNotices(Notices notices);
	/**
	 * 学生总数
	 * @return
	 */
	public int count();
	/**
	 * 分页查询学生
	 * @param pageNum
	 * @param rowCount
	 * @return
	 */
	public List<Student> pageSearch(int pageNum,int rowCount);

	public List<Notices> getNoticesAll(int notice);

	public Notices getOneNotices(int id);

	public boolean hasNewMessage(Student student);

	public int caculateMessage(String xuehao);

	public List<Ssjl> getSsjls(int page, String xuehao);
	
	public String addjl(Student student,String text);
	
	public int getStudentJdxcgsNum(Student student);
	
	public int getStudentLwsNum(Student student);
	
	public Lws getStudentLws(int id);
	
	public void updateLws(Lws lws);
	
	/**
	 * 按照院系和学号尾号统计筛选出来的学生数量，为论文抽检规则筛选准备数据
	 * @param yuanxiId
	 * @param tailNumber 尾号数组，如[1,2,3]
	 * @return
	 */
	public int countStudentByYuanxiAndTailNumber(int yuanxiId, String[] tailNumber);
	/**
	 * 按照院系和学号尾号筛选学生
	 * @param yuanxiId
	 * @param tailNumber 尾号数组，如[1,2,3]
	 * @return
	 */
	public List<Student> findStudentByYuanxiAndTailNumber(int yuanxiId, String[] tailNumber);
	
	/**
	 * 查找某院系所有学生
	 * @param yuanxiId
	 * @return
	 */
	public List<Student> findStudentByYuanxiId(int yuanxiId);
	
	/**
	 * 查找某院系未绑定教师的学生
	 * @param yuanxiId
	 * @return
	 */
			
	public List<Student> findUnbindedStudentByYuanxi(int yuanxiId);
	
	/**
	 * 根据专业ID找学生
	 * @param zhuanyeId
	 * @return
	 */
	public List<Student> findStudentByZhuanyeId(int zhuanyeId);
	
	/**
	 * 根据专业id和班级id找未分配教师的学生
	 * 未分配的学生的指导老师为NULL
	 * @param zhuanyeId banjiId
	 * @return
	 */
	public List<Student> findUnbindedStudentByZhuanyeId(int zhuanyeId,int banji,int nowPage);
	/**
	 * 根据专业id、班级计算未分配教师的学生数量
	 * @return
	 */
	public int CountUnbindedStudentByZhuanyeId(int zhuanyeId,int banji);
	
	/**
	 * 根据学号，解除该学生与教师的绑定
	 * @param number
	 */
	public void cancelBindByStudentNumber(String number);
	
	/**
	 * 根据学号找学生
	 * @param number
	 * @return
	 */
	public Student findStudentByNumber(String number);
	
	/**
	 * 根据名字模糊找学生
	 * @param number
	 * @return
	 */
	public List<Student> vagueSearchByName(String name);
	/**
	 * 根据指导教师工号找带的学生
	 * @param number
	 * @return
	 */
	public List<Student> findBindedByTeacherNumber(String number);
	
	public int getStudentZuhao(Student student);
	
	/**
	 * 按照院系、专业分页查询学生，查询信息使用
	 * @param yuanxiId
	 * @param nowPage
	 * @return
	 */
	public List<Student> pageFindStudentByYuanxi(int yuanxiId,int zhuanyeId,int nowPage);
	/**
	 * 按照院系、专业计算学生数量，查询信息使用
	 * @return
	 */
	public int CountStudentByYuanxi(int yuanxiId,int zhuanyeId);
	
	/**
	 * 分页  按照院系、专业、班级的次序筛选最终成绩
	 * 如果zhuanyeId为0，表示查询整个院系的成绩
	 * 如果 banji为空，表示查询整个专业的成绩
	 * @param yuanxiId
	 * @param zhuanyeId
	 * @param banji
	 * @param nowPage
	 * @return
	 */
	public  JSONArray pageFindStudentFinalScoreByYuanxiZhuanyeBanji(int yuanxiId,int zhuanyeId,String banji,int nowPage);
	/**
	 * 计算按照院系、专业、班级的次序最终成绩数量
	 * 如果zhuanyeId为0，表示查询整个院系的成绩
	 * 如果 banji为空，表示查询整个专业的成绩
	 * @param yuanxiId
	 * @param zhuanyeId
	 * @param banji
	 * @param nowPage
	 * @return
	 */
	public  int CountStudentFinalScoreByYuanxiZhuanyeBanji(int yuanxiId,int zhuanyeId,String banji);
	
	/**
	 * 按照院系、专业、班级的次序筛选最终成绩
	 * 如果zhuanyeId为0，表示查询整个院系的成绩
	 * 如果 banji为空，表示查询整个专业的成绩
	 * @param yuanxiId
	 * @param zhuanyeId
	 * @param banji
	 * @return
	 */
	public  List<String[]> ExportStudentFinalScoreByYuanxiZhuanyeBanji(int yuanxiId,int zhuanyeId,String banji);
	
}
