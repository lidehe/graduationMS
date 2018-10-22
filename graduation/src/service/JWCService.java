package service;

import java.util.List;

import domain.Byfs;
import domain.Cjbd;
import domain.Cjk;
import domain.Cjrz;
import domain.Dbpsbx;
import domain.Js;
import domain.Jsgx;
import domain.Kt;
import domain.Lw;
import domain.Notice;
import domain.Pypsbx;
import domain.Student;
import domain.Sxcjx;
import domain.Teacher;
import domain.Yuanxi;
import domain.Yxlw;
import domain.Yxlwx;
import domain.Yxlwz;
import domain.Zdlspsbx;
import domain.Zhuanye;
import domain.Zqxj;
import net.sf.json.JSONArray;

/**
 * 教务处
 * 
 * @author DaMoTou
 *
 */
public interface JWCService {

	
	//师生信息导入*******************************************************************************************
	/**
	 * 导入教师、学生、院系 信息
	 * 
	 * @param path教务处老师上传的文件路径
	 * @param
	 * @return
	 */
	public boolean importInformation(String path, String type);

	/**
	 * 添加学生
	 * @param student
	 */
	public void addStudent(Student student);
	
	public List<Student> findAllStudent();
	
	/**
	 * 添加教师
	 * @param teacher
	 */
	public void addTeacher(Teacher teacher);
	
	
	// 管理院系信息 包括增删改查*******************************************************************************************

	/**
	 * 根据院系名字模糊查询院系
	 * 
	 * @param YuanxiName
	 *            院系名字包含的字符
	 * @return 查到的所有院系
	 */
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
	 * @return 删除结果
	 */
	public boolean deleteYuanxiByname(Yuanxi yuanxi);

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
	 * 院系总数
	 * @return
	 */
	public int yxcount();
	
	/**
	 * 分页查询院系
	 * @param pageNum
	 * @param rowCount
	 * @return
	 */
	public List<Yuanxi> findInPage(int pageNum,int rowCount);
	
	/**
	 * 按照专业id找班级
	 * @param zhuanyeId
	 * @return
	 */
	public List<String> findBanjiByZhuanyeId(int zhuanyeId);
	// 角色设定*******************************************************************************************
	/**
	 * 保存角色
	 * @param js
	 */
	public void addJs(Js js);
	/**
	 * 查找所有角色
	 * @return
	 */
	public List<Js> findAllJs();
	
	/**
	 * 根据角色ID找角色
	 * @param id
	 * @return
	 */
	public Js findJsById(int id);
	
	/**
	 * 添加角色关系
	 * 
	 * @param jsgx
	 */
	public void addJsgx(Jsgx jsgx);

	/**
	 * 更新角色关系
	 * 
	 * @param jsgx
	 */
	public void updateJsgx(Jsgx jsgx);

	/**
	 * 删除角色关系
	 * 
	 * @param jsgx
	 */
	public void deleteJsgx(Jsgx jsgx);

	/**
	 * 根据教师工号和角色id找到角色关系
	 * 
	 * @param number
	 * @param JsId
	 * @return
	 */
	public Jsgx findJsgxByNumberAndJsId(String number, int JsId);

	/**
	 * 找出所有角色关系
	 * 
	 * @return
	 */
	public List<Jsgx> findAllJsgx();

	/**
	 * 根据角色id找角色关系
	 * 
	 * @param JsId
	 *            角色ID
	 * @return
	 */
	public List<Jsgx> findJsgxByJsID(int JsId);
	
	
	/**
	 * 根据工号查找一个老师的所有 "角色关系"，可能不止一个
	 * @param number
	 * @return
	 */
	public List<Jsgx> findJsgxByTeacherNumber(String number);
	
	/**
	 * 根据工号查找一个老师的所有 "角色"，注意和角色关系区分，可能不止一个
	 * @param number
	 * @return
	 */
	public List<Js> findJsByTeacherNumber(String number);

	// 毕业方式类型管理*******************************************************************************************
	/**
	 * 添加毕业方式
	 * 
	 * @param byfs
	 */
	public void addByfs(Byfs byfs);

	/**
	 * 删除毕业方式
	 * 
	 * @param byfs
	 */
	public void deleteByfs(Byfs byfs);

	/**
	 * 查询所有毕业方式
	 * 
	 * @return
	 */
	public List<Byfs> findAllByfs();

	/**
	 * 根据ID查询毕业方式
	 * 
	 * @param id
	 * @return
	 */
	public Byfs findByfsByID(int id);
	
	/**
	 * 根据名字查询毕业方式
	 * 
	 * @param name
	 * @return
	 */
	public Byfs findByfsByName(String name);


	/**
	 * 更新毕业方式信息
	 * 
	 * @param byfs
	 */
	public void updateByfs(Byfs byfs);
//*************专业***********专业专业*********专业专业*************专业专业*********************专业专业*****************************************************************
	/**
	 * 专业
	 * 
	 * @param zhuanye
	 */
	public void addZhuanye(Zhuanye zhuanye);

	public void deleteZhuanye(Zhuanye zhuanye);

	public void updateZhuanye(Zhuanye zhuanye);

	public Zhuanye findZhuanyeById(int id);

	public List<Zhuanye> findAllZhuanye();
    
	public List<Zhuanye> findByYuanxiId(int yuanxiId);
	
	public int zycount();
	
	public List<Zhuanye> pageSearchZhuanYe(int pageNum,int rowCount);
	
	
	//抽检日志管理*******************************************************************************************
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
	public Cjrz findCjrzById(int id);
	/**
	 * 获取所有抽检日志
	 * @return
	 */
	public List<Cjrz> findAllCjrz();
	/**
	 * 根据教师工号查找抽检日志
	 * @param gonghao
	 * @return
	 */
	public List<Cjrz> findCjrzByGonghao(String gonghao);
	
	/**
	 * 根据学生学号查找该同学所有抽检成绩
	 * @param xuehao
	 * @return
	 */
	public List<Cjrz> findByXuehao(String xuehao);
	
	//论文信息获取****************************************************
	/**
	 * 根据id获取论文
	 * @param id
	 * @return
	 */
	public Lw findLwById(int id);
	
	//优秀论文信息获取****************************************************
	/**
	 * 获取所有优秀论文
	 * @return
	 */
	public List<Yxlw> findAllYxlw();
	
	//抽检库操作**********************************************************************************************************
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
	public int countCjk();
	
	/**
	 * 分页查询抽检库记录
	 * @param pageNum
	 * @param rowCount
	 * @return
	 */
	public List<Cjk> findCjkInPage(int pageNum, int rowCount);
	
//课题管理***************************************************************************课题管理***************************************************************************
	public List<Kt> findAllKt();
	
//抽检绑定管理*********************************抽检绑定管理**************************************
	/**
	 * 添加抽检绑定
	 */
	public void saveCjbd(Cjbd cjbd);
	/**
	 * 删除抽检绑定
	 */
	public void deleteCjbd(Cjbd cjbd);
	
	/**
	 * 按照工号和院系ID查询抽检绑定记录
	 * @param gonghao
	 * @param yxId
	 * @return
	 */
	public Cjbd findCjbdByGonghaoAndYxId(String gonghao,int yxId);
	
	/**
	 * 根据工号查找一个老师绑定的所有抽检绑定记录
	 * @param gonghao
	 * @return
	 */
	public List<Cjbd> findCjbdByGonghao(String gonghao);
	/**
	 * 查询所有抽检绑定记录
	 * @return
	 */
	public List<Cjbd> findAllCjbd();
	
	/**
	 * 根据工号批量删除抽检绑定记录
	 * @param gonghao
	 */
	public void batchDeleteCjbdByGonghao(String gonghao);
	
	//**********优秀论文**********************************优秀论文**********************************优秀论文**********************************
	/**
	 * 增加一个论文数量限制
	 */
	public void save(Yxlwx yxlwx);
	
	/**
	 * 更新论文数量限制
	 */
	public void update(Yxlwx yxlwx);
	
	/**
	 * 按照院系和类型查询数量限制，类型有个人（0）和小组（1）
	 * @param yxId
	 * @param type
	 * @return
	 */
	public Yxlwx findByYxAndType(int yxId,int type);
	
	/**
	 * 根据Id查找个人优秀论文
	 * @param id
	 * @return
	 */
	public Yxlw  findPersonalYxlwById(int id);
	
	/**
	 * 根据院系Id查找该院系还没有推荐到学校里的个人优秀论文，用于向校推荐，即该院系的status为0的个人优秀论文
	 * @param yuanx
	 */
	public List<Yxlw> findYxlwByYuanxiId(int yuanxiId);
	
	/**
	 * 根据状态查询个人优秀论文
	 * @param status
	 * @return
	 */
	public List<Yxlw> findYxlwByStatus(int status);
	/**
	 * 根据院系和状态查询个人优秀论文
	 * @param yuanxiId
	 * @param status
	 * @return
	 */
	public List<Yxlw> findYxlwByYuanxiAndStatus(int yuanxiId,int status);
	/**
	 * 根据院系和状态查询小组优秀论文
	 * @param yuanxiId
	 * @param status
	 * @return
	 */
	public  List<Yxlwz> findYxlwzByYuanxiAndStatus(int yuanxiId,int status);
	
	/**
	 * 根据状态查询小组优秀论文
	 * @param yuanxiId
	 * @param status
	 * @return
	 */
	public List<Yxlwz> findYxlwzByStatus(int status);
	
	/**
	 * 更改个人优秀论文状态
	 * @param xuehao
	 * @param status
	 */
	public void changePersonalYxlwStatus(String xuehao ,int status);
	
	/**
	 * 更改小组优秀论文状态
	 * @param int id
	 * @param status
	 */
	public void changeGroupYxlwStatus(int id ,int status);
	
	//********************中期小结*****************************************************中期小结********************************
	public void saveZqxj(Zqxj zqxj);
	
	public void deleteZqxj(Zqxj zqxj);
	
	public void updateZqxj(Zqxj zqxj);
	
	/**
	 * 根据Id找总结
	 * @param id
	 * @return
	 */
	public Zqxj findZqxjById(int id);
	
	/**
	 * 根据院系ID和小结类型查找
	 * @param yuanxiId
	 * @param type   类型有中期小结（zqxj）和最后总结（zhzj）
	 * @return
	 */
	public Zqxj findZqxjByYuanxiIdAndType(int yuanxiId,String type);
	
	/**
	 * 查找所有中期小结
	 * @return
	 */
	public List<Zqxj> findAllZqxj();
	
	/**
	 * 获取所有最终总结
	 * @return
	 */
	public List<Zqxj> findAllZhzj();
	
//************************公告管理*******************************公告管理***********************公告管理************************
	/**
	 * 保存公告
	 * @param notice
	 */
	public void saveNotice(Notice notice);
	
	/**
	 * 按删除公告
	 * @param notice
	 */
	public void deleteNotice(Notice notice);
	
	/**
	 * 查找所有学校公告，也即是类型为3的公告
	 * @return
	 */
	public List<Notice> findAllXiaoNotice();
	
	/**
	 * 按id找公告
	 * @param id
	 * @return
	 */
	public Notice findNoticeById(int id);
	
	/**
	 * 根据院系Id找到某个院系的所有公告
	 * @param yuanxiId
	 * @return
	 */
	public List<Notice> findAllYuanxiNoticeByYuanxiId(int yuanxiId);

//~~~~~~~~~~~~~~~~~~~成绩表管理~~~~~~~~~~~~~~~~~~~~~~~~~~~成绩表管理~~~~~~~~~~~~~~~~~~~~~~~~~~~~~成绩表管理~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	
	/**
	 * 评阅评审表
	 */
	public boolean addPypsbx(Pypsbx pypsbx);
	
	public void deletePypsbx(Pypsbx pypsbx);
	
	public void updatePypsbx(Pypsbx pypsbx);
	
	public Pypsbx findPypsbxByName(String name);
	
	public List<Pypsbx> findAllPypsbx();
	/**
	 * 答辩评审表
	 */
	public boolean addDbpsbx(Dbpsbx Dbpsbx);
	
	public void deleteDbpsbx(Dbpsbx Dbpsbx);
	
	public void updateDbpsbx(Dbpsbx Dbpsbx);
	
	public Dbpsbx findDbpsbxByName(String name);
	public List<Dbpsbx> findAllDbpsbx();
	
	/**
	 * 指导老师评审表
	 */
	public boolean addZdlspsbx(Zdlspsbx Zdlspsbx);
	
	public void deleteZdlspsbx(Zdlspsbx Zdlspsbx);
	
	public void updateZdlspsbx(Zdlspsbx Zdlspsbx);
	
	public Zdlspsbx findZdlspsbxByName(String name);
	public List<Zdlspsbx> findAllZdlspsbx();
	
	/**
	 * 实习成绩表
	 */
	public boolean addSxcjx(Sxcjx Sxcjx);
	
	public void deleteSxcjx(Sxcjx Sxcjx);
	
	public void updateSxcjx(Sxcjx Sxcjx);
	
	public Sxcjx findSxcjxByName(String name);
	public List<Sxcjx> findAllSxcjx();
	
	
//~~~~~~~~~~~~~~~~~~~统计信息~~~~~~~~~~~~~~~~~~~~~~~~~~~统计信息管理~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	

	/**
	 * 获取课题分布统计数据
	 * 如果传过来的yuanxiId=0,表示查询全校的
	 * @param yuanxiId
	 * @return
	 */
	public JSONArray getKtDistribute(int yuanxiId);
	
	/**
	 * 获取毕设进度统计数据
	 * 如果传过来的yuanxiId=0,表示查询全校的
	 * @param yuanxiId
	 * @return
	 */
	public JSONArray getSchedule(int yuanxiId);
	
	/**
	 * 获取答辩状态统计数据，比如，可以看看有哪些人不能答辩等信息
	 * 如果传过来的yuanxiId=0,表示查询全校的
	 * @param yuanxiId
	 * @return
	 */
	public JSONArray getAnswerState(int yuanxiId);
	
	/**
	 * 获取毕设总分统计数据
	 * 如果传过来的yuanxiId=0,表示查询全校的
	 * @param yuanxiId
	 * @return
	 */
	public JSONArray getBSZF(int yuanxiId);
	
	/**
	 * 获取论文抽检成绩统计数据
	 * @param yuanxiId
	 * @return
	 */
	public JSONArray getCheckResult(int yuanxiId);
	
	/**
	 * 获取推送省里优秀论文统计数据
	 * 如果传过来的yuanxiId=0,表示查询全校的
	 * @param yuanxiId
	 * @return
	 */
	public JSONArray getShengYXLW(int yuanxiId);
	/**
	 * 分页查询学生新消息
	 * @param yuanxiId
	 * @return
	 */
	public JSONArray pageFindStudent(int nowPage, int row, int yuanxiId, String preOrNext);
	/**
	 * 查找师生交流信息
	 * @param studentNumber
	 * @return
	 */
	public JSONArray findSSJL(String studentNumber);
	
	
}
