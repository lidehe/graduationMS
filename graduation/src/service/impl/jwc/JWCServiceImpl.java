package service.impl.jwc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

import dao.ByfsDao;
import dao.CjbdDao;
import dao.CjkDao;
import dao.CjrzDao;
import dao.DbpsbxDao;
import dao.JsDao;
import dao.JsgxDao;
import dao.KtDao;
import dao.LwDaoImpl;
import dao.NoticeDao;
import dao.PypsbxDao;
import dao.StudentDao;
import dao.SxcjxDao;
import dao.TeacherDao;
import dao.YuanxiDao;
import dao.YxlwDaoImpl;
import dao.YxlwxDao;
import dao.YxlwzDao;
import dao.ZdlspsbxDao;
import dao.ZhuanyeDao;
import dao.ZqxjDaoImpl;
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
import factory.JWCFactory;
import net.sf.json.JSONArray;
import service.JWCService;
import utils.IuimSesssionFactory;

public class JWCServiceImpl implements JWCService {
	
	StudentDao studentDao = JWCFactory.getStudentDao();
	TeacherDao teacherDao = JWCFactory.geTeacherDao();
	YuanxiDao yuanxiDao = JWCFactory.getYuanxiDaoImpl();
	ByfsDao byfsDao = JWCFactory.getByfsDao();
	JsgxDao jsgxDao = JWCFactory.getJsgxDao();
	ZhuanyeDao zhuanyeDao = JWCFactory.getZhuanyeDao();
	CjrzDao cjrzDao = JWCFactory.getCjrzDao();
	JsDao jsDao = JWCFactory.getJsDao();
	LwDaoImpl lwDao = JWCFactory.getLwDao();
	YxlwDaoImpl yxlwDao = JWCFactory.getYxlwDao();
	CjkDao cjkDao = JWCFactory.getCjkDao();
	KtDao ktDao = JWCFactory.getKtDao();
	CjbdDao cjbdDao = JWCFactory.getCjbdDao();
	YxlwxDao yxlwxDao = JWCFactory.getYxlwxDao();
	YxlwzDao yxlwzDao = JWCFactory.getYxlwzDao();
	ZqxjDaoImpl zqxjDao = JWCFactory.getZqxjDao();
	NoticeDao noticeDao = new NoticeDao();
	PypsbxDao pypsbxDao = JWCFactory.getPypsbxDao();
	DbpsbxDao dbpsbxDao = JWCFactory.getDbpsbxDao();
	ZdlspsbxDao zdlspsbxDao = JWCFactory.getZdlspsbxDao();
	SxcjxDao sxcjxDao = JWCFactory.getSxcjxDao();

	public void addStudent(Student student) {
		Student student2 = studentDao.findByNumber(student.getNumber());
		if (student2 == null) {// 如果该学号的学生不存在，就可以添加，如果存在就不添加
			studentDao.add(student);
		}
	}

	@Override
	public List<Student> findAllStudent() {
		return studentDao.findAll();
	}

	@Override
	public void addTeacher(Teacher teacher) {
		Teacher teacher2 =  teacherDao.findTeacherByNum(teacher.getNumber());
		if (teacher2 == null) {
			teacherDao.addTeacher(teacher);
		}

	}

	@Override
	public boolean importInformation(String path, String type) {
		return false;
	}

	@Override
	public List<Yuanxi> findYuanxiByName(String YuanxiName) {
		return yuanxiDao.findYuanxiByName(YuanxiName);
	}

	@Override
	public boolean addYuanxi(Yuanxi yuanxi) {
		Yuanxi yuanxi2 =  yuanxiDao.findByName(yuanxi.getName());
		if (yuanxi2 == null) {
			yuanxiDao.addYuanxi(yuanxi);
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteYuanxiByname(Yuanxi yuanxi) {
		yuanxiDao.deleteYuanxiByname(yuanxi);
		return false;
	}

	@Override
	public boolean updateYuanxi(Yuanxi yuanxi) {
		yuanxiDao.updateYuanxi(yuanxi);
		return false;
	}

	@Override
	public List<Yuanxi> findAllYuanxi() {
		return yuanxiDao.findAllYuanxi();
	}

	@Override
	public int yxcount() {
		return yuanxiDao.count();
	}

	@Override
	public List<Yuanxi> findInPage(int pageNum, int rowCount) {
		return yuanxiDao.findInPage(pageNum, rowCount);
	}

	@Override
	public void addByfs(Byfs byfs) {
		byfsDao.addByfs(byfs);

	}

	@Override
	public void deleteByfs(Byfs byfs) {
		byfsDao.deleteByfs(byfs);

	}

	@Override
	public List<Byfs> findAllByfs() {
		return byfsDao.findAllByfs();
	}

	@Override
	public Byfs findByfsByID(int id) {
		return byfsDao.findByfsByID(id);
	}

	@Override
	public Byfs findByfsByName(String name) {
		return byfsDao.findyByName(name);
	}

	@Override
	public void updateByfs(Byfs byfs) {
		byfsDao.updateByfs(byfs);

	}

	@Override
	public List<Js> findAllJs() {
		return jsDao.findAllJs();
	}

	@Override
	public Js findJsById(int id) {
		return jsDao.findJsById(id);
	}

	@Override
	public void addJsgx(Jsgx jsgx) {
		Jsgx jsgx2 = jsgxDao.findJsgxByNumberAndJsId(jsgx.getGonghao(), jsgx.getJs());
		if (jsgx2 == null) {// 如果这个角色关系已经存在，即该职工已经绑定了某个角色，就不要再次绑定了
			jsgxDao.addJsgx(jsgx);
		}
	}

	@Override
	public void updateJsgx(Jsgx jsgx) {
		jsgxDao.updateJsgx(jsgx);
	}

	@Override
	public void deleteJsgx(Jsgx jsgx) {
		jsgxDao.deleteJsgx(jsgx);
	}

	@Override
	public Jsgx findJsgxByNumberAndJsId(String number, int JsId) {
		return jsgxDao.findJsgxByNumberAndJsId(number, JsId);
	}

	@Override
	public List<Jsgx> findAllJsgx() {
		return jsgxDao.findAllJsgx();
	}

	@Override
	public List<Jsgx> findJsgxByJsID(int JsId) {
		return jsgxDao.findJsgxByJsID(JsId);
	}

	@Override
	public List<Jsgx> findJsgxByTeacherNumber(String number) {
		return jsgxDao.findJsgxByTeacherNumber(number);
	}

	@Override
	public List<Js> findJsByTeacherNumber(String number) {
		List<Jsgx> jsgxList = new ArrayList<>();
		jsgxList = findJsgxByTeacherNumber(number);
		List<Js> jsList = new ArrayList<>();
		if (!jsgxList.isEmpty())
			for (int i = 0; i < jsgxList.size(); i++) {
				Js js = new Js();
				js = findJsById(jsgxList.get(i).getJs());
				jsList.add(js);
			}
		return jsList;
	}

	@Override
	public void addZhuanye(Zhuanye zhuanye) {
		Zhuanye zhuanye2 =  zhuanyeDao.findZhuanyeByName(zhuanye.getName());
		if (zhuanye2 == null) {// 仅当该名字的专业不存在时才可以添加
			zhuanyeDao.addZhuanye(zhuanye);
		}
	}

	@Override
	public void deleteZhuanye(Zhuanye zhuanye) {
		zhuanyeDao.deleteZhuanye(zhuanye);
	}

	@Override
	public void updateZhuanye(Zhuanye zhuanye) {
		zhuanyeDao.updateZhuanye(zhuanye);

	}

	@Override
	public Zhuanye findZhuanyeById(int id) {
		return zhuanyeDao.findZhuanyeById(id);
	}

	@Override
	public List<Zhuanye> findAllZhuanye() {
		return zhuanyeDao.findAllZhuanye();
	}

	@Override
	public List<Zhuanye> findByYuanxiId(int yuanxiId) {
		return zhuanyeDao.findByYuanxiId(yuanxiId);
	}

	@Override
	public void addCjrz(Cjrz cjrz) {
		cjrzDao.addCjrz(cjrz);
	}

	@Override
	public void deleteCjrz(Cjrz cjrz) {
		cjrzDao.deleteCjrz(cjrz);
	}

	@Override
	public Cjrz findCjrzById(int id) {
		return cjrzDao.findById(id);
	}

	@Override
	public List<Cjrz> findAllCjrz() {
		return cjrzDao.findAll();
	}

	@Override
	public List<Cjrz> findCjrzByGonghao(String gonghao) {
		return cjrzDao.findByGonghao(gonghao);
	}

	@Override
	public Lw findLwById(int id) {
		return lwDao.findById(id);
	}

	@Override
	public List<Yxlw> findAllYxlw() {
		return yxlwDao.findAll();
	}

	@Override
	public List<Zhuanye> pageSearchZhuanYe(int pageNum, int rowCount) {
		return zhuanyeDao.findInPage(pageNum, rowCount);
	}

	@Override
	public int zycount() {
		return zhuanyeDao.count();
	}

	@Override
	public void save(Cjk cjk) {
		Cjk cjk2 = cjkDao.findByStudentNumber(cjk.getXuehao());
		if (cjk2 == null) {
			cjkDao.save(cjk);
		}

	}

	@Override
	public Cjk findById(int id) {
		return cjkDao.findById(id);
	}

	@Override
	public Cjk findByStudentNumber(String number) {
		return cjkDao.findByStudentNumber(number);
	}

	@Override
	public List<Cjk> findByYxId(int yxId) {
		return cjkDao.findByYxId(yxId);
	}

	@Override
	public List<Cjk> findByDj(int dj) {
		return cjkDao.findByDj(dj);
	}

	@Override
	public int countCjk() {
		return cjkDao.count();
	}

	@Override
	public List<Cjk> findCjkInPage(int pageNum, int rowCount) {
		return cjkDao.findInPage(pageNum, rowCount);
	}

	@Override
	public List<Kt> findAllKt() {
		return ktDao.findAllKt();
	}

	@Override
	public List<Cjrz> findByXuehao(String xuehao) {
		return cjrzDao.findByXuehao(xuehao);
	}

	@Override
	public void saveCjbd(Cjbd cjbd) {
		Cjbd cjbd2 =  cjbdDao.findByGonghaoAndYxId(cjbd.getGonghao(), cjbd.getYx());
		if (cjbd2 == null)// 仅当这个绑定不存在，才能建立绑定
			cjbdDao.save(cjbd);

	}

	@Override
	public void deleteCjbd(Cjbd cjbd) {
		cjbdDao.delete(cjbd);

	}

	@Override
	public Cjbd findCjbdByGonghaoAndYxId(String gonghao, int yxId) {
		return cjbdDao.findByGonghaoAndYxId(gonghao, yxId);
	}

	@Override
	public List<Cjbd> findCjbdByGonghao(String gonghao) {
		return cjbdDao.findByGonghao(gonghao);
	}

	@Override
	public List<Cjbd> findAllCjbd() {
		return cjbdDao.findAll();
	}

	@Override
	public void batchDeleteCjbdByGonghao(String gonghao) {
		String sql = "DELETE  FROM tb_cjbd WHERE va_gonghao = " + gonghao + " ";
		Session session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createSQLQuery(sql);
		query.executeUpdate();
		transaction.commit();
		session.close();
	}

	@Override
	public void save(Yxlwx yxlwx) {
		Yxlwx yxlwx2 = yxlwxDao.findByYxAndType(yxlwx.getYx(), yxlwx.getGx());
		if (yxlwx2 == null) {
			yxlwxDao.save(yxlwx);
		}
	}

	@Override
	public void update(Yxlwx yxlwx) {
		Yxlwx yxlwx2 = yxlwxDao.findByYxAndType(yxlwx.getYx(), yxlwx.getGx());
		if (yxlwx2 == null) {// 如果更新数量的时候发现没有，那么直接保存它
			yxlwxDao.save(yxlwx);
		} else {// 如果能查到原始数据，就更新他
			yxlwx2.setSl(yxlwx.getSl());
			yxlwxDao.update(yxlwx2);
		}

	}

	@Override
	public Yxlwx findByYxAndType(int yxId, int type) {
		return yxlwxDao.findByYxAndType(yxId, type);
	}


	
	@Override
	public List<Yxlw> findYxlwByStatus(int status) {
		List<Yxlw> result = new ArrayList<>();
		String sql = "SELECT * FROM tb_yxlw  WHERE va_status = "+status+"   ORDER BY va_yx  ASC ,va_xfs DESC  , va_yfs DESC ";
		Session session = null;
		session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createSQLQuery(sql).addEntity(Yxlw.class);
		result = query.list();
		transaction.commit();
		session.close();
		return result;
	}

	@Override
	public List<Yxlw> findYxlwByYuanxiAndStatus(int yuanxiId, int status) {
		List<Yxlw> result = new ArrayList<>();
		String sql = "SELECT * FROM tb_yxlw  WHERE va_yx = "+yuanxiId+" AND va_status = "+status+"  ORDER BY va_yx  ASC ,va_xfs DESC  , va_yfs DESC ";
		Session session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createSQLQuery(sql).addEntity(Yxlw.class);
		result = query.list();
		transaction.commit();
		session.close();
		return result;
	}

	@Override
	public List<Yxlwz> findYxlwzByYuanxiAndStatus(int yuanxiId, int status) {
		List<Yxlwz> result = new ArrayList<>();
		String sql = "SELECT * FROM tb_yxlwz  WHERE va_yx = "+yuanxiId+" AND va_status = "+status+" ORDER BY va_yx  ASC ,va_xfs DESC  , va_yfs DESC ";
		Session session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createSQLQuery(sql).addEntity(Yxlwz.class);
		result = query.list();
		transaction.commit();
		session.close();
		return result;
	}

	@Override
	public List<Yxlwz> findYxlwzByStatus(int status) {
		List<Yxlwz> result = new ArrayList<>();
		String sql = "SELECT * FROM tb_yxlwz  WHERE  va_status = "+status+" ORDER BY va_yx  ASC ,va_xfs DESC  , va_yfs DESC ";
		Session session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createSQLQuery(sql).addEntity(Yxlwz.class);
		result = query.list();
		transaction.commit();
		session.close();
		return result;
	}


	@Override
	public void changePersonalYxlwStatus(String xuehao, int status) {
		Yxlw yxlw=yxlwDao.findByXuehao(xuehao);
		yxlw.setStatus(status);
		yxlwDao.update(yxlw);
		
	}

	@Override
	public void changeGroupYxlwStatus(int id, int status) {
		Yxlwz yxlwz=yxlwzDao.findById(id);
		yxlwz.setStatus(status);
		yxlwzDao.update(yxlwz);
		
	}

	@Override
	public Yxlw findPersonalYxlwById(int id) {
		return yxlwDao.findById(id);
	}


	@Override
	public List<Yxlw> findYxlwByYuanxiId(int yuanxiId) {
		return yxlwDao.findYxlwByYuanxiId(yuanxiId);
	}

	
	@Override
	public void saveZqxj(Zqxj zqxj) {
		Zqxj zqxj2 = findZqxjByYuanxiIdAndType(zqxj.getYx(), zqxj.getUrl());
		if (zqxj2 != null) {
			zqxj2.setFileName(zqxj.getFileName());
			updateZqxj(zqxj2);
		} else {
			zqxjDao.save(zqxj);
		}
	}

	@Override
	public void deleteZqxj(Zqxj zqxj) {
		zqxjDao.deleteZqxj(zqxj);

	}

	@Override
	public void updateZqxj(Zqxj zqxj) {
		zqxjDao.update(zqxj);
	}

	@Override
	public Zqxj findZqxjById(int id) {
		return zqxjDao.findById(id);
	}

	@Override
	public Zqxj findZqxjByYuanxiIdAndType(int yuanxiId, String type) {
		return zqxjDao.findByYuanxiIdAndType(yuanxiId, type);
	}

	@Override
	public List<Zqxj> findAllZqxj() {
		return zqxjDao.findAllZqxj();
	}

	@Override
	public List<Zqxj> findAllZhzj() {
		return zqxjDao.findAllZhzj();
	}

	@Override
	public void saveNotice(Notice notice) {
		noticeDao.save(notice);

	}

	@Override
	public void deleteNotice(Notice notice) {
		noticeDao.delete(notice);
	}

	@Override
	public List<Notice> findAllXiaoNotice() {
		return noticeDao.findAllXiaoNotice();
	}

	@Override
	public Notice findNoticeById(int id) {
		return noticeDao.findById(id);
	}

	@Override
	public List<Notice> findAllYuanxiNoticeByYuanxiId(int yuanxiId) {
		return noticeDao.findAllYuanxiNoticeByYuanxiId(yuanxiId);
	}

	@Override
	public void addJs(Js js) {
		jsDao.save(js);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public JSONArray getKtDistribute(int yuanxiId) {
		JSONArray jsonArray = null;
		List<Object> result = new ArrayList<>();
		String sql = "";
		if (yuanxiId == 0) {
			sql = "SELECT yuanxi.va_name AS yuanxiName, kt.id AS ktid,kt.va_name AS ktName,COUNT(*) AS stuCount FROM tb_student AS stu ,tb_yuanxi AS yuanxi,tb_kt AS kt WHERE stu.va_yxid=yuanxi.id AND stu.va_ktid=kt.id GROUP BY stu.va_yxid,stu.va_ktid";
		} else {
			sql = "SELECT yuanxi.va_name AS yuanxiName, kt.id AS ktid,kt.va_name AS ktName,COUNT(*) AS stuCount FROM tb_student AS stu ,tb_yuanxi AS yuanxi,tb_kt AS kt WHERE stu.va_yxid=yuanxi.id AND stu.va_ktid=kt.id AND yuanxi.id= "
					+ yuanxiId + " GROUP BY stu.va_yxid,stu.va_ktid";
		}
		Session session =IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		result = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		String jsonStr = "[";
		if (!result.isEmpty()) {
			for (Object o : result) {
				Map map = (Map) o;
				String tempStr = "{ yuanxiName:'" + map.get("yuanxiName") + "',ktName:'" + map.get("ktName")
						+ "',stuCount:'" + map.get("stuCount") + "'},";
				jsonStr += tempStr;
			}
			jsonStr = jsonStr.substring(0, jsonStr.length() - 1);
		}
		jsonStr += "]";
		jsonArray = JSONArray.fromObject(jsonStr);
		transaction.commit();
		session.close();
		return jsonArray;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public JSONArray getSchedule(int yuanxiId) {
		JSONArray jsonArray = null;
		List<Object> result = new ArrayList<>();
		String sql = "";
		if (yuanxiId == 0) {
			sql = "SELECT yuanxi.va_name AS yuanxiName , stu.va_stage AS jinDu,COUNT(stu.va_stage) AS stuCount,(COUNT(stu.va_stage) / (SELECT COUNT(*) FROM tb_student WHERE tb_student.va_yxid=yuanxi.id)) AS percent   FROM tb_student AS stu,tb_yuanxi AS yuanxi WHERE stu.va_yxid=yuanxi.id  GROUP BY stu.va_yxid,stu.va_yxid,stu.va_stage";
		} else {
			sql = "SELECT yuanxi.va_name AS yuanxiName , stu.va_stage AS jinDu,COUNT(stu.va_stage) AS stuCount,(COUNT(stu.va_stage) / (SELECT COUNT(*) FROM tb_student WHERE tb_student.va_yxid=yuanxi.id)) AS percent   FROM tb_student AS stu,tb_yuanxi AS yuanxi WHERE stu.va_yxid=yuanxi.id AND yuanxi.id=1 AND yuanxi.id="
					+ yuanxiId + " GROUP BY stu.va_yxid,stu.va_yxid,stu.va_stage";
		}
		Session session =IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		result = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		String jsonStr = "[";
		if (!result.isEmpty()) {
			for (Object o : result) {
				Map map = (Map) o;
				String tempStr = "{ yuanxiName:'" + map.get("yuanxiName") + "',jinDu:'" + map.get("jinDu")
						+ "',stuCount:'" + map.get("stuCount") + "',percent:'" + map.get("percent") + "'},";
				jsonStr += tempStr;
			}
			jsonStr = jsonStr.substring(0, jsonStr.length() - 1);
		}
		jsonStr += "]";
		jsonArray = JSONArray.fromObject(jsonStr);
		transaction.commit();
		session.close();
		return jsonArray;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public JSONArray getAnswerState(int yuanxiId) {
		Map<Integer, String> states = new HashMap<>();
		states.put(0, "尚未答辩");
		states.put(1, "不参与初次答辩");
		states.put(2, "初次答辩未通过");
		states.put(3, "本届内未通过答辩");
		states.put(4, "答辩通过");
		JSONArray jsonArray = null;
		List<Object> result = new ArrayList<>();
		String sql = "";
		if (yuanxiId == 0) {
			sql = "SELECT yuanxi.va_name AS yuanxiName,stu.va_answer AS answerState, COUNT(*) AS stuCount FROM tb_student AS stu ,tb_yuanxi AS yuanxi WHERE stu.va_yxid=yuanxi.id  GROUP BY stu.va_yxid,stu.va_answer";
		} else {
			sql = "SELECT yuanxi.va_name AS yuanxiName,stu.va_answer AS answerState, COUNT(*) AS stuCount FROM tb_student AS stu ,tb_yuanxi AS yuanxi WHERE stu.va_yxid=yuanxi.id AND yuanxi.id="
					+ yuanxiId + " GROUP BY stu.va_yxid,stu.va_answer";
		}
		Session session =  IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		result = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		String jsonStr = "[";
		if (!result.isEmpty()) {
			for (Object o : result) {
				Map map = (Map) o;
				String tempStr = "{ yuanxiName:'" + map.get("yuanxiName") + "',answerState:'"
						+ states.get(map.get("answerState")) + "',stuCount:'" + map.get("stuCount") + "'},";
				jsonStr += tempStr;
			}
			jsonStr = jsonStr.substring(0, jsonStr.length() - 1);
		}
		jsonStr += "]";
		jsonArray = JSONArray.fromObject(jsonStr);
		transaction.commit();
		session.close();
		return jsonArray;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public JSONArray getBSZF(int yuanxiId) {
		JSONArray jsonArray = null;
		List<Object> result = new ArrayList<>();
		String sql = "";
		if (yuanxiId == 0) {
			sql = "SELECT yuanxi.va_name AS yuanxiName, zf.va_dj AS dengJi, COUNT(*) AS stuCount FROM tb_yuanxi AS yuanxi,tb_zf AS zf WHERE zf.va_yx=yuanxi.id GROUP BY zf.va_yx,zf.va_dj";
		} else {
			sql = "SELECT yuanxi.va_name AS yuanxiName, zf.va_dj AS dengJi, COUNT(*) AS stuCount FROM tb_yuanxi AS yuanxi,tb_zf AS zf WHERE zf.va_yx=yuanxi.id AND yuanxi.id="
					+ yuanxiId + " GROUP BY zf.va_yx,zf.va_dj";
		}
		Session session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		result = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		String jsonStr = "[";
		if (!result.isEmpty()) {
			for (Object o : result) {
				Map map = (Map) o;
				String tempStr = "{ yuanxiName:'" + map.get("yuanxiName") + "',dengJi:'" + map.get("dengJi")
						+ "',stuCount:'" + map.get("stuCount") + "'},";
				jsonStr += tempStr;
			}
			jsonStr = jsonStr.substring(0, jsonStr.length() - 1);
		}
		jsonStr += "]";
		jsonArray = JSONArray.fromObject(jsonStr);
		transaction.commit();
		session.close();
		return jsonArray;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public JSONArray getCheckResult(int yuanxiId) {
		JSONArray jsonArray = null;
		List<Object> result = new ArrayList<>();
		String sql = "";
		if (yuanxiId == 0) {
			sql = "SELECT  yuanxi.va_name AS yuanxiName,cjk.va_dj AS dengJi,COUNT(*) AS stuCount FROM tb_cjk AS cjk,tb_yuanxi AS yuanxi WHERE cjk.va_yx=yuanxi.id GROUP BY cjk.va_yx,cjk.va_dj";
		} else {
			sql = "SELECT  yuanxi.va_name AS yuanxiName,cjk.va_dj AS dengJi,COUNT(*) AS stuCount FROM tb_cjk AS cjk,tb_yuanxi AS yuanxi WHERE cjk.va_yx=yuanxi.id AND yuanxi.id="
					+ yuanxiId + "  GROUP BY cjk.va_yx,cjk.va_dj";
		}

		Session session =  IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		result = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		String jsonStr = "[";
		if (!result.isEmpty()) {
			for (Object o : result) {
				Map map = (Map) o;
				String tempStr = "{ yuanxiName:'" + map.get("yuanxiName") + "',dengJi:'" + map.get("dengJi")
						+ "',stuCount:'" + map.get("stuCount") + "'},";
				jsonStr += tempStr;
			}
			jsonStr = jsonStr.substring(0, jsonStr.length() - 1);
		}
		jsonStr += "]";
		jsonArray = JSONArray.fromObject(jsonStr);
		transaction.commit();
		session.close();
		return jsonArray;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public JSONArray getShengYXLW(int yuanxiId) {
		JSONArray jsonArray = null;
		List<Object> result = new ArrayList<>();
		String sql = "";
		if (yuanxiId == 0) {
			sql = "SELECT yxlw.va_xuehao AS stuNumber, stu.va_name AS stuName ,yx.va_name AS yuanxiName,zhuanye.va_name AS zhuanyeName,stu.va_class AS stuClass,lw.va_filename AS lwName FROM tb_yxlw AS yxlw,tb_yuanxi AS yx,tb_lw AS lw,tb_zhuanye AS zhuanye,tb_student AS stu WHERE yx.id=yxlw.va_yx AND zhuanye.id=yxlw.id AND lw.id=yxlw.va_lw AND stu.va_number=yxlw.va_xuehao AND yxlw.va_status=3";
		} else {
			sql = "SELECT yxlw.va_xuehao AS stuNumber, stu.va_name AS stuName ,yx.va_name AS yuanxiName,zhuanye.va_name AS zhuanyeName ,stu.va_class AS stuClass,lw.va_filename AS lwName FROM tb_yxlw AS yxlw,tb_yuanxi AS yx,tb_lw AS lw,tb_zhuanye AS zhuanye,tb_student AS stu WHERE yx.id=yxlw.va_yx AND zhuanye.id=yxlw.id AND lw.id=yxlw.va_lw AND stu.va_number=yxlw.va_xuehao AND yxlw.va_status=3 AND yx.id ="
					+ yuanxiId + " ";
		}
		Session session =  IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		result = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		String jsonStr = "[";
		if (!result.isEmpty()) {
			for (Object o : result) {
				Map map = (Map) o;
				String tempStr = "{ stuNumber:'" + map.get("stuNumber") + "',stuName:'" + map.get("stuName")
						+ "',yuanxiName:'" + map.get("yuanxiName") + "',zhuanyeName:'" + map.get("zhuanyeName")
						+ "',stuClass:'" + map.get("stuClass") + "',lwName:'" + map.get("lwName") + "'},";
				jsonStr += tempStr;
			}
			jsonStr = jsonStr.substring(0, jsonStr.length() - 1);
		}
		jsonStr += "]";
		jsonArray = JSONArray.fromObject(jsonStr);
		transaction.commit();
		session.close();
		return jsonArray;
	}

	/**
	 * 查询某个院系的学生总量
	 * 
	 * @param yuanxiId
	 * @return
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	public int countStudentByYuanxi(int yuanxiId) {
		int count = 0;
		String sql = "SELECT COUNT(*) AS stuCount FROM tb_student WHERE va_yxid =  " + yuanxiId + " ";
		List<Object> result = new ArrayList<>();
		Session session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Object object = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list()
				.get(0);
		transaction.commit();
		session.close();
		Map map = (Map) object;
		String countstr = map.get("stuCount").toString();
		count = Integer.valueOf(countstr);
		return count;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public JSONArray pageFindStudent(int nowPage, int row, int yuanxiId, String preOrNext) {
		JSONArray jsonArray = null;
		if (yuanxiId != 0) {
			int count = 0;// 该院系学生信息总行数
			count = countStudentByYuanxi(yuanxiId);
			int totalPage = 0;
			// 计算总页数***********************************************************************
			if (count % row == 0) {// 如果能整除，则总数除以每页行数就是总页数
				totalPage = count / row;
			} else {// 如果能整除，则总数除以每页行数就是总页数+1
				totalPage = count / row + 1;
			}
			// 设置页码**************************************************************************
			if (preOrNext.equals("initial")) {
				nowPage = 1;
			} else if (preOrNext.equals("next")) {// 如果是查询下一页
				if (nowPage < totalPage) {
					nowPage += 1;
				}
			} else if (preOrNext.equals("pre")) {// 如果是查询上一页
				if (nowPage > 1) {
					nowPage -= 1;
				}
			}
			String sql = "SELECT stu.va_number AS stuNumber,stu.va_name AS stuName,zhuanye.va_name AS zhuanyeName ,stu.va_class AS stuClass FROM tb_student AS stu ,tb_zhuanye AS zhuanye WHERE stu.va_zyid =zhuanye.id AND stu.va_yxid= "
					+ yuanxiId + "  LIMIT " + (--nowPage) * row + " , " + row;
			List<Object> result = new ArrayList<>();
			Session session =IuimSesssionFactory.getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();
			// 下面这样写，可以使得从sql查询到的List<Object>中以键值对的形式获取查到的数据
			result = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			String jsonStr = "[{nowPage:'" + (++nowPage) + "',totalPage:'" + totalPage + "'},";
			if (!result.isEmpty()) {
				for (Object o : result) {
					Map map = (Map) o;
					String tempStr = "{ stuNumber:'" + map.get("stuNumber") + "',stuName:'" + map.get("stuName")
							+ "',zhuanyeName:'" + map.get("zhuanyeName") + "',stuClass:'" + map.get("stuClass") + "'},";
					jsonStr += tempStr;
				}
				jsonStr = jsonStr.substring(0, jsonStr.length() - 1);
			}
			jsonStr += "]";
			jsonArray = JSONArray.fromObject(jsonStr);
			transaction.commit();
			session.close();
		} else {
			String jsonStr = "[]";
			jsonArray = JSONArray.fromObject(jsonStr);
		}

		return jsonArray;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public JSONArray findSSJL(String studentNumber) {
		JSONArray jsonArray = null;
		List<Object> result = new ArrayList<>();
		String sql = "";
		if (studentNumber != null && studentNumber.length() > 0) {
			sql = "SELECT jl.va_writer AS writer,jl.va_reader AS reader,jl.va_text AS content ,jl.va_time AS jlTime  FROM tb_ssjl AS jl ,tb_student AS stu WHERE (stu.va_number=jl.va_writer OR stu.va_number=jl.va_reader) AND stu.va_number="
					+ studentNumber + "  ORDER BY jl.va_time ASC";
			Session session =  IuimSesssionFactory.getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();
			result = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			String jsonStr = "[";
			if (!result.isEmpty()) {
				for (Object o : result) {
					Map map = (Map) o;
					if (map.get("writer").equals(studentNumber)) {// 如果提问方式学生
						Student student = studentDao.findByNumber(studentNumber);
						Teacher teacher = teacherDao.findTeacherByNum(map.get("reader").toString());
						String tempStr = "{ writer:'" + student.getName() + "',reader:'" + teacher.getName() + "老师"
								+ "',content:'" + map.get("content") + "',jlTime:'" + map.get("jlTime") + "'},";
						jsonStr += tempStr;
					} else {// 如果提问方式教师
						Student student = studentDao.findByNumber(studentNumber);
						Teacher teacher = teacherDao.findTeacherByNum(map.get("writer").toString());
						String tempStr = "{ writer:'" + teacher.getName() + "老师" + "',reader:'" + student.getName()
								+ "',content:'" + map.get("content") + "',jlTime:'" + map.get("jlTime") + "'},";
						jsonStr += tempStr;
					}
				}
				jsonStr = jsonStr.substring(0, jsonStr.length() - 1);
			}
			jsonStr += "]";
			jsonArray = JSONArray.fromObject(jsonStr);
			transaction.commit();
			session.close();
		} else {
			String jsonStr = "[]";
			jsonArray = JSONArray.fromObject(jsonStr);
		}
		return jsonArray;
	}

	@Override
	public boolean addPypsbx(Pypsbx pypsbx) {
		Pypsbx pypsbx2 =pypsbxDao.findByName(pypsbx.getText());
		if (pypsbx2 == null) {
			pypsbxDao.save(pypsbx);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void deletePypsbx(Pypsbx pypsbx) {
		pypsbxDao.delete(pypsbx);
	}

	@Override
	public void updatePypsbx(Pypsbx pypsbx) {
		pypsbxDao.update(pypsbx);

	}

	@Override
	public Pypsbx findPypsbxByName(String name) {
		Pypsbx pypsbx = pypsbxDao.findByName(name);
		return pypsbx;
	}

	@Override
	public boolean addDbpsbx(Dbpsbx Dbpsbx) {
		Dbpsbx Dbpsbx2 =  dbpsbxDao.findByName(Dbpsbx.getText());
		if (Dbpsbx2 == null) {
			dbpsbxDao.save(Dbpsbx);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void deleteDbpsbx(Dbpsbx Dbpsbx) {
		dbpsbxDao.delete(Dbpsbx);

	}

	@Override
	public void updateDbpsbx(Dbpsbx Dbpsbx) {
		dbpsbxDao.update(Dbpsbx);

	}

	@Override
	public Dbpsbx findDbpsbxByName(String name) {
		return dbpsbxDao.findByName(name);
	}

	@Override
	public boolean addZdlspsbx(Zdlspsbx Zdlspsbx) {
		Zdlspsbx Zdlspsbx2 =  zdlspsbxDao.findByName(Zdlspsbx.getText());
		if (Zdlspsbx2 == null) {
			zdlspsbxDao.save(Zdlspsbx);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void deleteZdlspsbx(Zdlspsbx Zdlspsbx) {
		zdlspsbxDao.delete(Zdlspsbx);

	}

	@Override
	public void updateZdlspsbx(Zdlspsbx Zdlspsbx) {
		zdlspsbxDao.update(Zdlspsbx);

	}

	@Override
	public Zdlspsbx findZdlspsbxByName(String name) {
		return zdlspsbxDao.findByName(name);
	}

	@Override
	public boolean addSxcjx(Sxcjx Sxcjx) {
		Sxcjx Sxcjx2 =  sxcjxDao.findByName(Sxcjx.getText());
		if (Sxcjx2 == null) {
			sxcjxDao.save(Sxcjx);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void deleteSxcjx(Sxcjx Sxcjx) {
		sxcjxDao.delete(Sxcjx);

	}

	@Override
	public void updateSxcjx(Sxcjx Sxcjx) {
		sxcjxDao.update(Sxcjx);

	}

	@Override
	public Sxcjx findSxcjxByName(String name) {
		return sxcjxDao.findByName(name);
	}
	@Override
	public List<Pypsbx> findAllPypsbx() {
		return pypsbxDao.findAll();
	}

	@Override
	public List<Dbpsbx> findAllDbpsbx() {
		return dbpsbxDao.findAll();
	}

	@Override
	public List<Zdlspsbx> findAllZdlspsbx() {
		return zdlspsbxDao.findAll();
	}

	@Override
	public List<Sxcjx> findAllSxcjx() {
		return sxcjxDao.findAll();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<String> findBanjiByZhuanyeId(int zhuanyeId) {
        List<String> banjis=new ArrayList<>();
		String sql = "SELECT  DISTINCT(stu.va_class) AS banji FROM tb_student AS stu WHERE stu.va_zyid = "+zhuanyeId+"  ";
		List<Object> result = new ArrayList<>();
		Session session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		// 下面这样写，可以使得从sql查询到的List<Object>中以键值对的形式获取查到的数据
		result = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		transaction.commit();
		session.close();
		if (!result.isEmpty()) {
			for (Object o : result) {
				Map map = (Map) o;
				String banji =  map.get("banji").toString();
				banjis.add(banji);
			}
		}
		return banjis;
	}

}
