package service;

import java.util.List;

import domain.Byfs;
import domain.Cjbd;
import domain.Cjk;
import domain.Cjrz;
import domain.Dbjsz;
import domain.Dbpsb;
import domain.Dbpsbx;
import domain.Dbzl;
import domain.Gzzj;
import domain.Jdxcg;
import domain.Jdxcgs;
import domain.Jsgx;
import domain.Kt;
import domain.Ktbg;
import domain.Lw;
import domain.Lwcg;
import domain.Lws;
import domain.Notice;
import domain.Notices;
import domain.Pybd;
import domain.Pypsb;
import domain.Pypsbx;
import domain.Pyrz;
import domain.Rws;
import domain.Ssjl;
import domain.Student;
import domain.Sxcj;
import domain.Sxcjx;
import domain.Teacher;
import domain.Xsz;
import domain.Yuanxi;
import domain.Yxlw;
import domain.Yxlwz;
import domain.Zdlspsb;
import domain.Zdlspsbx;
import domain.Zf;
import domain.Zhuanye;
import domain.Zpjl;
import domain.Zqxj;
import domain.middle.Sxcjfs;
import net.sf.json.JSONObject;

public interface TeacherService {
	/**
	 * 添加一个教师
	 * 
	 * @param teacher
	 */
	public void addTeacher(Teacher teacher);

	/**
	 * 根据教师工号查找他所属的角色关系，可能不止一个
	 * 
	 * @param number
	 * @return
	 */
	public List<Jsgx> findTeacherJsgxByNumber(String number);

	public Teacher getTeacherByAccount(String account);// 这个是查找今年系统里的教师！！！请教师登陆后的查询，使用此method

	public Teacher getTeacherByAccountYear(String account);// 根据学生的年份，来查找他的指导老师；请学生部分的功能调用此method

	public Teacher getProfessionPrincipal(int zy);// 根据学生（的年级），确定这个学生的专业负责人

	public List<Jsgx> getTeacherJses(Teacher teacher);// 得到老师所有的角色关系

	public List<Student> getTeacherStudentsEsc(Teacher teacher);// 得到老师带的所有学生

	public Lw getStudentLw(Student student);

	public List<Kt> getTeacherAllKts(Teacher teacher);

	public int shKt(Teacher teacher, int id);

	public int deleteKt(Teacher teacher, int id);

	public List<Zhuanye> zdlsGetStudentAllZy(Teacher teacher);// 指导老师获取带的学生全部的专业
	
	public List<Byfs> zdlsGetStudentAllByfs(Teacher teacher);

	public int teacherAddKt(Teacher teacher, int zy, String name, String text, int xzrs, int bylx);

	public Teacher getZhuanyeFzr(Zhuanye zy);

	public List<Rws> getTeacherAllRwsOrderKt(Teacher teacher);

	public Rws getOneRws(int kt);

	public List<Integer> getTeacherKtStudents(List<Kt> kts);

	public List<Rws> getTeacherAllRws(Teacher teacher);

	public int createRwsRecordByTeacher(int kt, Teacher teacher);

	public void updateRwsStatus(int id, String file);

	public List<Student> getTeachersStudentOrderXuehao(Teacher teacher);

	public List<Ktbg> getTeachersStudentKtbgOrderXuehao(Teacher teacher);

	public List<Jdxcg> getTeachersStudentJdxcgsOrderXuehao(Teacher teacher);

	public Lw TeacherGetStudentLw(Student student);

	public List<Lw> teacherGetStudentLwsOrderXuehao(Teacher teacher);

	public List<Lwcg> teacherGetStudentLwcgsOrderXuehao(Teacher teacher);

	public Zhuanye teacherGetZy(int id);

	public Kt teacherGetKt(int id);

	public Yuanxi teacherGetYx(int id);

	public void teacherAddZdlspsb(List<Object> forms, Teacher teacher, Student student);

	public List<Byfs> teacherGeaAllStudentByfs(List<Student> students);

	public Dbpsb teacherGetStudentDbpsb(Teacher teacher, String xuehao);

	public Pypsb teacherGetStudentPypsb(Teacher teacher, String xuehao);

	public List<Dbjsz> getDbjsById(int bdz);

	public void addDbpsb(List<Object> forms, Teacher teacher, String xuehao);

	public void addPypsb(List<Object> forms, Teacher teacher, String xuehao);

	public List<Dbzl> teacherGetAllDbz(Teacher teacher);

	public Teacher teacherGetDbzz(int zh);

	public List<Teacher> teacherGetCommonDbzTeacher(int zh);

	public List<Student> teacherGetDbzStudents(int zh);

	public void shKtbg(int id);

	public List<Kt> zyfzrGetAllKts(int zy);

	public void zyfzrUpdateKt(int id, int sh);

	public List<Rws> zyfzrgetKtAllRwsOrderByKt(int zy);

	public Zpjl zyfzrGetZpjl(Teacher teacher);

	public void addZpjl(Zpjl zpjl);

	public void calcuScoreFirst(Teacher teacher);

	public List<Yuanxi> getAllYx();

	public List<Zhuanye> getZys(int yx);

	public List<Student> getZyStudents(int zy);

	public List<Zhuanye> getAllZys();

	public List<Student> searchStudentByNumberLike(String key);

	public Sxcj teacherGetSxPsb(Teacher teacher, String xuehao);

	public void addSxcj(List<Object> forms, Teacher teacher, Student student);

	public List<Student> getZyStudentOrderByNumber(int zy);

	public List<Zf> getZyStudentZfOrderByNumber(int zy);

	public List<Sxcjfs> getZyStudentSxcjOrderByNumber(List<Student> students);

	public Teacher getTeacherBYStudentYear(String account);// 根据学生来找对应账号的老师，查看以往数据的时候用这个

	public void zyfzrUpdateSxcj(List<Object> forms, Teacher teacher, String xuehao);

	public void addCjrz(Cjrz cjrz);

	public List<Yxlw> getYxlwsOrderXuehaoInBd(Teacher teacher);

	public List<Yxlwz> getZyxlwsOrderXuehaoInBd(Teacher teacher);
	
	public List<Yxlw> getYxSYxlwsORderXuehao(Teacher teacher);
	
	public List<Yxlwz> getYxsYxlwzsOrderZuhao(Teacher teacher);

	public List<Pyrz> getTeacherPyrz(Teacher teacher);

	public List<Pyrz> getTeacherZpyrz(Teacher teacher);
	
	public List<Pyrz> getYxTeacherPyrzOrderbyXuehao(Teacher teacher);
	
	public List<Pyrz> getYxTeacherPyrzOrderZuhao(Teacher teacher);

	public Yxlw checkStudent(String xuehao);
	
	public Yxlw checkYxStudent(String xuehao);
	
	public Pyrz checkPyed(String xuehao, String gonghao);
	
	public Pyrz checkYxPred(String xuehao,String gonghao);

	public Cjrz checkCjed(String xuehao, String gonghao);

	public void addXiaoGePyrz(Pyrz rz);
	
	public void addXiaoZuPyrz(Pyrz rz);
	
	public void addYxGePyrz(Pyrz rz);
	
	public void addYxZuPyrz(Pyrz rz);

	public Yuanxi getYuanxiByTeacher(Teacher teacher);

	public List<Student> getYuanxiStudents(int yx);

	public JSONObject calcuStudentSecond(Teacher teacher);

	public Sxcj getStudentSxcj(Student student);

	public Zdlspsb getStudentZdlspsb(Student student);

	public List<Dbpsb> getStudentDbpsbs(Student student, int dbxId);

	public List<Pypsb> getStudentPypsbs(Student student, int pyxId);

	public void updateSxcj(List<Object> forms, Student student);

	public void updateStudentZf(Student student);

	public void updateZdlspsb(List<Object> forms, Student student);

	public Dbpsb getPsb(int id);

	public void updateDbpsb(List<Object> forms, Teacher dbls, Student student);

	public Pypsb getPyPsb(int id);

	public void updatePypsb(List<Object> forms, Teacher dbls, Student student);

	public List<Notice> getYxNotices(Teacher teacher);

	public List<Notice> getZyNotices(Teacher teacher);

	public List<Notice> getZdNotices(Teacher teacher);

	public void releaseNotice(Notice notice);

	public void teacherUpdateDbpsb(List<Object> forms, Teacher teacher, String xuehao);

	public void teacherupdatePypsb(List<Object> forms, Teacher teacher, String xuehao);

	public Zf checkZfRecord(String xuehao);

	public int teacherupdateZdlspsb(List<Object> forms, String gonghao, Student student);

	public int teacherUpdateSxcj(List<Object> forms, Teacher teacher, Student student);

	public void cjryUpdateCjrz(Cjrz cjrz);

	public Rws teacherGetRwsById(int id);

	public List<Jdxcgs> teacherStudentJdxcgss(String xuehao);

	public int countStudentjdxcgWj(String xuehao);

	public int countStudentLwWj(String xuehao);

	public int addNotice(Notice notice);

	public int addNotices(Notices notices);

	public void updateNotices(Notices notices);

	public void updateNotice(Notice notice);

	public int getOneKtStudents(int kt);

	public void updateKtInfo(Kt kt);

	public List<Boolean> checkNewMessage(List<Student> students, Teacher teacher);

	public List<Ssjl> getSsjls(int page, String xuehao);

	public String addSsjl(String gonghao, String xuehao, String text);

	public List<Lws> getStudentLws(String xuehao);

	public List<Lw> getTeachersStudentLwsOrderXuehao(Teacher teacher);

	public List<Zdlspsbx> getZdlspsb();

	public List<Sxcjx> getSxcjx();

	public boolean checkZdlspsb(Teacher teacher, String xuehao);

	public boolean checkSxcj(Teacher teacher, String xuehao);

	public List<Zdlspsb> getTeacherZdlspsb(String gonghao, String xuehao);

	public List<Sxcj> getSxcjpsb(String gonghao, String xuehao);

	public List<Pypsbx> getPypsbxOrderId();

	public List<Dbpsbx> getDbpsbxOrderId();

	public boolean checkPypsb(Teacher teacher, String xuehao);

	public boolean checkDbpsb(Teacher teacher, String xuehao);

	public List<Pypsb> getOnePypsbOrderXId(String gonghao, String xuehao);

	public List<Dbpsb> getOneDbpsbOrderXId(String gonghao, String xuehao);

	public int calculateOneStudent(Student student, int answer);

	public Gzzj getYxGzzj(int yx);

	public int yzUploadGzzjGetId(Teacher teacher);

	public void updateGzzj(Gzzj gzzj);

	public List<Cjbd> getOneTeacherCjBd(Teacher teacher);

	/**
	 * 教师总数
	 * 
	 * @return
	 */
	public int count();

	/**
	 * 分页查询教师
	 * 
	 * @param pageNum
	 * @param rowCount
	 * @return
	 */
	public List<Teacher> pageSearch(int pageNum,int rowCount);
	
	/**
	 * 按院系ID查找教师
	 * @param yuanxi
	 * @return
	 */
	public List<Teacher> findTeacherByYuanxi(int YuanxiID);
	
	/**
	 * 根据工号查找教师
	 * @param number
	 * @return
	 */
    public Teacher findTeacherByNuber(String number);
	
    /**
	 * 根据名字模糊搜索教师
	 * @param name
	 * @return
	 */
	public List<Teacher> vagueSearchByName(String name);
    
    /**
     * 根据角色查找教职工
     * @param jsId
     * @return
     */
    public List<Teacher> findTeacherByJsId(int jsId);
    
   
    
	public List<Pybd> getOneTeacherPyBd(Teacher teacher);
   
	/**
	 * 未分组教师查询--根据院系ID找到该院系没有分答辩组的老师
	 * @return
	 */
	public List<Teacher> findUnGroupedTeacherByYuanxiId(int yuanxiId);
	/**
	 * 是否分组判断--根据工号判断是否分组了
	 * @param number
	 * @return
	 */
	public boolean isGroupedByNumber(String number);
	

	public List<Student> getOneZyCjStudents(int zy);

	public Cjk getOneCjObject(String xuehao);

	public Yxlwz getXiaoYxlwzByZh(int zuhao);
	
	public Yxlwz getYxYxlwzByZh(int zuhao);

	public List<Xsz> getOneGroups(int zuhao);

	public Pyrz getOneZuXiaoRz(int zuhao, String gonghao);
	
	public Pyrz getOnezuYxRz(int zuhao, String gonghao);
	
	public boolean isYxlw(Student student);
	
	public void addGeYxlw(Yxlw yxlw);
	
	public void addZuYxlw(Yxlwz yxlwz);
	
	public List<Yuanxi> getNowAllYuanxi();
	
	public List<Integer> getYxsZrs(List<Yuanxi> yxs);
	
	public int getYxZrs(Yuanxi yuanxi);
	
	public List<Integer> getYxsJsRws(List<Yuanxi> yxs);
	
	public int getyxJsRws(Yuanxi yuanxi);
	
	public List<Integer> getYxsTjKtbg(List<Yuanxi> yxs);
	
	public int getYxTjKtbg(Yuanxi yuanxi);
	
	public List<Integer> getYxsTjLw(List<Yuanxi> yxs);
	
	public int getYxTjLw(Yuanxi yuanxi);
	
	public List<Integer> getYxsDb(List<Yuanxi> yxs);
	
	public int getYxDb(Yuanxi yuanxi);
	
	public List<Integer> getYxsXt(List<Yuanxi> yxs);
	
	public int getYxXt(Yuanxi yuanxi);
	
	public List<Integer> getOneYxCj(int yx);
	
	public List<Gzzj> getNowGzzj();
	
	public Gzzj getOneYxGzzj(int yx);
	
	public List<Zqxj> getNowZqxj();
	
	public Zqxj getOneYxZqxj(int yx);
	
	public List<Yxlw> getAllYxlwOrderFs();
	
	public List<Yxlwz> getAllYxlwzOrderFs();
	
	public void updateTeacher(Teacher teacher);
	
	/**
	 * 查找某院系没有分配某角色的教师
	 * 在分配教师角色的时候，如果某个教师已经分配了某个角色，那么当筛选条件再次为这个院系、这个角色的时候，Ta就不该出现在候选列表里
	 * 比如计算机学院的王晓鹏已经被分配为指导老师，那么当再次选择“指导老师、计算机学院”为筛选条件的时候，王晓鹏就不该出现在候选列表里
	 * @param yuanxiId
	 * @param jsId
	 * @param nowPage
	 * @return
	 */
	public List<Teacher> pageFindTeacherByTeacherAndJsgx(int yuanxiId,int jsId,int nowPage);
	/**
	 * 计算某院系没有分配某角色的教师数量
	 */
	public int CountTeacherByTeacherAndJsgx(int yuanxiId, int jsId);
	
	/**
	 * 查找某院系没有分配某角色的教师数量
	 * 协同上一个方法进行分页
	 */
	public int CountPageFindTeacherByTeacherAndJsgx(int yuanxiId,int jsId);
	
	/**
	 * 分页查找某一院系下担任某一角色的所有教师
	 * 如在进行师生匹配的时候，筛选出计算机学院的所有指导老师
	 * @param yuanxiId
	 * @param jsId
	 * @param nowPage
	 * @return
	 */
	public List<Teacher> findTeacherByYuanxiAndJs(int yuanxiId,int jsId,int nowPage);
	/**
	 * 计算查找某一院系下担任某一角色的所有教师数量
	 */
	public int CountfindTeacherByYuanxiAndJs(int yuanxiId,int jsId);
	/**
	 * 不分页查找某一院系下担任某一角色的所有教师
	 * 如在进行师生匹配的时候，筛选出计算机学院的所有指导老师
	 * @param yuanxiId
	 * @param jsId
	 * @return
	 */
	 public List<Teacher> findTeacherByYuanxiAndJs(int yuanxiId,int jsId);
	 
	 /**
	  * 按院系分页查询教师
	  * @param yuanxiId
	  * @return
	  */
	 public List<Teacher> pageFindTeacherByYuanxi(int yuanxiId,int nowPage);
	 /**
	  * 按院系计算教师数量
	  */
	 public int CountTeacherByYuanxi(int yuanxiId);
	 
	 /**
	  * 根据工号查一个老师带的学生数量
	  * @param number
	  * @return
	  */
	 public int countStudentOfTeacher(String number);
	 
	 public boolean checkZdlspsb();
	 
	 public boolean checkSxcjX();
	 
	 public boolean checkDbPyPsb();
	 
	 public boolean newMessage(Teacher teacher);
	 
	 public List<Zf> getZyZfs(int yx);
	 
}
