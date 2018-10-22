package service.impl.student;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import domain.Teacher;
import domain.Xsz;
import domain.Xszl;
import domain.Year;
import factory.DaoFactory;
import factory.JWCFactory;
import factory.ServiceFactory;
import net.sf.json.JSONArray;
import service.StudentService;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

import dao.StudentDao;
import utils.IuimSesssionFactory;

public class StudentServiceImpl implements StudentService {
	private StudentDao studentDao = JWCFactory.getStudentDao();

	@Override
	public Student getStudentByAccount(String account) {

		String hql = "from Student a where a.number = ? ";
		Object[] param = { account };
		List<Student> list = new ArrayList<Student>();
		list = DaoFactory.getStudentDaoInstance().find(hql, param);
		if (list.size() > 0)
			return list.get(0);
		return null;
	}

	@Override
	public List<Byfs> getAllByfs() {

		String hql = "from Byfs ";
		List<Byfs> list = new ArrayList<Byfs>();
		list = DaoFactory.getByfsDaoInstance().find(hql);
		return list;
	}

	@Override
	public Byfs getStudentByfs(int id) {

		if (id == -1 || id == 0) {
			String hql = "from Byfs ";
			return DaoFactory.getByfsDaoInstance().find(hql).get(0);
		}
		String hql = "from Byfs b where b.id = ? ";
		Object[] param = { id };
		Byfs byfs = DaoFactory.getByfsDaoInstance().get(hql, param);
		if (byfs.getLiu() == null)
			byfs.setLiu("空");
		if (byfs.getBa() == null)
			byfs.setBa("空");
		if (byfs.getShiyi() == null)
			byfs.setShiyi("空");
		return byfs;
	}

	@Override
	public void updateStudent(Student student) {

		DaoFactory.getStudentDaoInstance().update(student);
	}

	// 联合查询范例
	@SuppressWarnings("unchecked")
	@Override
	public List<Student> getGroupStudents(String xuehao) {

		List<Student> groupStudents = new ArrayList<Student>();
		Xsz xsz;
		String hql = "from Xsz x where x.xuehao = ? ";
		Object[] param = { xuehao };
		xsz = DaoFactory.getXszDaoInstance().get(hql, param);
		if (xsz == null)
			return null;
		else {
			String hql1 = " from Student s where s.number in ( select xuehao from Xsz x where x.zh = ? ) ";
			Session session = null;
			session = IuimSesssionFactory.getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();
			Query query = session.createQuery(hql1);
			query.setInteger(0, xsz.getZh());
			transaction.commit();
			groupStudents = query.list();
			session.close();
			return groupStudents;
		}
	}

	@Override
	public void deleteGroup(String xuehao) {

		Xsz xsz;
		String hql = "from Xsz x where x.xuehao = ? ";
		Object[] param = { xuehao };
		xsz = DaoFactory.getXszDaoInstance().get(hql, param);
		Xszl xszl = DaoFactory.getXszlDaoInstance().get(Xszl.class, xsz.getZh());
		DaoFactory.getXszDaoInstance().delete(xsz);
		DaoFactory.getXszlDaoInstance().delete(xszl);
	}

	// sql范例
	@Override
	public int createGroup(Student student) {

		Student student2 = getStudentByAccount(student.getNumber());
		if (student2.getIsgroup() == 1)
			return 0;// 已经提前被人弄进小组了
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Xszl xszl = new Xszl(student.getNumber() + " " + df.format(new Date()));
		DaoFactory.getXszlDaoInstance().save(xszl);
		student.setIsgroup(1);
		updateStudent(student);
		Xsz xsz = new Xsz(xszl.getId(), student.getNumber(), student.getYear(), student.getYx());
		DaoFactory.getXszDaoInstance().save(xsz);
		return 1;
	}

	@Override
	public int addStudentToGroup(String number, Student student) {
		String hql = " from Xsz x where x.xuehao = ? ";
		Object[] param = { student.getNumber() };
		Xsz xiaozu = DaoFactory.getXszDaoInstance().get(hql, param);
		Student grouper = getStudentByAccount(number);
		if (grouper.getIsgroup() == 1)
			return 0;
		grouper.setIsgroup(1);
		updateStudent(grouper);
		Xsz addZu = new domain.Xsz(xiaozu.getZh(), grouper.getNumber(), grouper.getYear(), grouper.getYx());
		DaoFactory.getXszDaoInstance().save(addZu);
		return 1;
	}

	@Override
	public int removeFromGroup(String number) {

		Student grouper = getStudentByAccount(number);
		if (grouper.getIsgroup() == 0)
			return 0;
		grouper.setIsgroup(0);
		updateStudent(grouper);
		String hql = " from Xsz x where x.xuehao = ? ";
		Object[] param = { number };
		Xsz xiaozu = DaoFactory.getXszDaoInstance().get(hql, param);
		DaoFactory.getXszDaoInstance().delete(xiaozu);
		return 1;
	}

	@Override
	public List<Kt> getTeacherKt(Student student) {

		Teacher zdls = ServiceFactory.getTeacherServiceInstance().getTeacherByAccountYear(student.getTeacher());
		String hql = " from Kt k where k.gonghao = ? AND k.zy = ? AND k.year = ? AND k.bylx = ? AND (k.fzrsh = 1 AND k.shuxing = 1 OR k.shuxing = 0) ORDER BY k.shuxing DESC  ";
		Object[] param = { zdls.getNumber(), student.getZy(), student.getYear(), student.getType() };
		List<Kt> kts = DaoFactory.getKtDaoInstance().find(hql, param);
		return kts;
	}

	@Override
	public int chooseKt(int ktId, Student student) {

		String hql = "from Kt k where k.id = ? ";
		Object[] param = { ktId };
		Kt kt = DaoFactory.getKtDaoInstance().get(hql, param);
		if (kt == null || kt.getFzrsh() != 1)
			return 0;
		String hql1 = "from Student s where s.kt = ? ";
		Object[] param1 = { ktId };
		List<Student> students = DaoFactory.getStudentDaoInstance().find(hql1, param1);
		if (kt.getXzrs() <= students.size())
			return 0;
		student.setKt(ktId);
		student.setStage(6);
		updateStudent(student);
		return 1;
	}

	@Override
	public Kt getStudentKt(int id) {

		String hql = " from Kt k where k.id = ? ";
		Object[] param = { id };
		Kt kt = DaoFactory.getKtDaoInstance().get(hql, param);
		return kt;
	}

	@Override
	public int addKtFromStudent(String name, String text, Student student) {

		Teacher zdls = ServiceFactory.getTeacherServiceInstance().getTeacherByAccountYear(student.getTeacher());
		Teacher fzr = ServiceFactory.getTeacherServiceInstance().getProfessionPrincipal(student.getZy());
		if (fzr == null)
			return 0;
		Kt kt = new Kt(fzr.getNumber(), zdls.getNumber(), 0, 0, name, text, 0, student.getYx(), student.getZy(), 1,
				student.getYear(), student.getType());
		DaoFactory.getKtDaoInstance().save(kt);
		return 1;
	}

	@Override
	public Rws getKtRwsByStudent(Student student) {

		String hql = " from Rws r where r.kt = ? and r.gonghao = ? ";
		Object[] param = { student.getKt(), student.getTeacher() };
		Rws rws = DaoFactory.getRwsDaoInstance().get(hql, param);
		return rws;
	}

	@Override
	public Ktbg getStudentKtbg(Student student) {

		String hql = " from Ktbg k where k.xuehao = ? ";
		Object[] param = { student.getNumber() };
		Ktbg ktbg = DaoFactory.getKtbgDaoInstance().get(hql, param);
		return ktbg;
	}

	@Override
	public int studentKtbg(Student student) {

		Ktbg ktbg = getStudentKtbg(student);
		if (ktbg != null)
			return ktbg.getId();
		ktbg = new Ktbg(student.getNumber(), student.getZy(), student.getKt(), student.getTeacher(), 0, 0, "",
				student.getYear());
		DaoFactory.getKtbgDaoInstance().save(ktbg);
		return ktbg.getId();
	}

	@Override
	public void updataKtbgStatus(Ktbg ktbg) {

		DaoFactory.getKtbgDaoInstance().update(ktbg);
	}

	@Override
	public Jdxcg getStudentJdxcg(Student student) {

		String hql = "from Jdxcg j where j.xuehao = ? ";
		Object[] param = { student.getNumber() };
		Jdxcg jdxcg = DaoFactory.getJdxcgDaoInstance().get(hql, param);
		return jdxcg;
	}

	@Override
	public int studentJdxcg(Student student) {

		Jdxcg jdxcg = getStudentJdxcg(student);
		if (jdxcg != null)
			return jdxcg.getId();
		jdxcg = new Jdxcg(student.getNumber(), 0, "");
		DaoFactory.getJdxcgDaoInstance().save(jdxcg);
		return jdxcg.getId();
	}

	@Override
	public void updateJdxcgStatus(Jdxcg jdxcg) {

		DaoFactory.getJdxcgDaoInstance().update(jdxcg);
	}

	@Override
	public String addjl(Student student, String text) {

		Timestamp time = new Timestamp(System.currentTimeMillis());
		Ssjl ssjl = new Ssjl();
		ssjl.setReader(student.getTeacher());
		ssjl.setStatus(1);
		ssjl.setText(text);
		ssjl.setTime(time);
		ssjl.setWriter(student.getNumber());
		DaoFactory.getSsjlDaoInstance().save(ssjl);
		return time.toString();
	}

	@Override
	public Lw getStudentLw(Student student) {

		String hql = "from Lw l where l.xuehao = ? ";
		Object[] param = { student.getNumber() };
		Lw lw = DaoFactory.getLwDaoInstance().get(hql, param);
		return lw;
	}

	@Override
	public Lwcg getStudentLwcg(Student student) {

		String hql = "from Lwcg l where l.xuehao = ? ";
		Object[] param = { student.getNumber() };
		Lwcg lwcg = DaoFactory.getLwcgDaoInstance().get(hql, param);
		return lwcg;
	}

	@Override
	public int studentLw(Student student) {

		Lw lw = getStudentLw(student);
		if (lw != null)
			return lw.getId();
		lw = new Lw(student.getNumber(), student.getTeacher(), student.getYx(), student.getZy(), student.getYear(), 0,
				student.getKt(), "");
		DaoFactory.getLwDaoInstance().save(lw);
		return lw.getId();
	}

	@Override
	public int studentLwcg(Student student) {

		Lwcg lwcg = getStudentLwcg(student);
		if (lwcg != null)
			return lwcg.getId();
		lwcg = new Lwcg(student.getNumber(), student.getTeacher(), student.getYx(), student.getZy(), 0, "",
				student.getYear(), student.getKt());
		DaoFactory.getLwcgDaoInstance().save(lwcg);
		return lwcg.getId();
	}

	@Override
	public void updateLwcgStatus(Lwcg lwcg) {

		DaoFactory.getLwcgDaoInstance().update(lwcg);
	}

	@Override
	public void updateLwStatus(Lw lw) {

		DaoFactory.getLwDaoInstance().update(lw);
	}

	@Override
	public List<Notice> getXiNotice() {

		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String hql = " from Notice n where n.year = ? AND n.type = 3 ORDER BY n.id DESC ";
		Object[] param = { year.getYear() };
		List<Notice> notices = DaoFactory.getNoticeDaoInstance().find(hql, param);
		return notices;
	}

	@Override
	public List<Notice> getYxNotice(Student student) {

		String hql = " from Notice n where n.year = ? AND n.type = ? AND n.yx = ? ORDER BY n.id DESC ";
		Object[] param = { student.getYear(), 2, student.getYx() };
		List<Notice> notices = DaoFactory.getNoticeDaoInstance().find(hql, param);
		return notices;
	}

	@Override
	public List<Notice> getZyNotice(Student student) {

		String hql = " from Notice n where n.year = ? AND n.type = ? AND n.zy = ? ORDER BY n.id DESC ";
		Object[] param = { student.getYear(), 1, student.getZy() };
		List<Notice> notices = DaoFactory.getNoticeDaoInstance().find(hql, param);
		return notices;
	}

	@Override
	public List<Notice> getZdNotice(Student student) {

		String hql = " from Notice n where n.year = ? AND n.type = ? AND n.gonghao = ? ORDER BY n.id DESC ";
		Object[] param = { student.getYear(), 0, student.getTeacher() };
		List<Notice> notices = DaoFactory.getNoticeDaoInstance().find(hql, param);
		return notices;
	}

	@Override
	public Notice getNotice(int id) {

		Notice notice = DaoFactory.getNoticeDaoInstance().get(Notice.class, id);
		return notice;
	}

	@Override
	public Jdxcgs getJdxcgsById(int id) {

		Jdxcgs jdxcgs = DaoFactory.getJdxcgsDaoInstance().get(Jdxcgs.class, id);
		return jdxcgs;
	}

	@Override
	public List<Jdxcgs> getJdxcgs(Student student) {

		String hql = " from Jdxcgs j where j.xuehao = ? AND j.status = 1 ";
		Object[] param = { student.getNumber() };
		List<Jdxcgs> jdxcgs = DaoFactory.getJdxcgsDaoInstance().find(hql, param);
		return jdxcgs;
	}

	@Override
	public int getJdxcgsId(Student student) {

		Jdxcg jdxcg = getStudentJdxcg(student);
		Jdxcgs jdxcgs = new Jdxcgs(student.getNumber(), jdxcg.getId(), 0, "", "");
		DaoFactory.getJdxcgsDaoInstance().save(jdxcgs);
		return jdxcgs.getId();
	}

	@Override
	public int getLwsId(Student student) {

		Lw lw = getStudentLw(student);
		Lws lws = new Lws(lw.getId(), 0, "", "", student.getNumber(), student.getYear());
		DaoFactory.getLwsDaoInstance().save(lws);
		return lws.getId();
	}

	@Override
	public Jdxcgs getJdxcgs(int id) {

		Jdxcgs jdxcgs = DaoFactory.getJdxcgsDaoInstance().get(Jdxcgs.class, id);
		return jdxcgs;
	}

	@Override
	public void updateJdxcgs(Jdxcgs jdxcgs) {

		jdxcgs.setStatus(1);
		DaoFactory.getJdxcgsDaoInstance().update(jdxcgs);
	}

	@Override
	public List<Student> pageSearch(int pageNum, int rowCount) {
		return studentDao.findInPage(pageNum, rowCount);
	}

	@Override
	public int count() {
		return studentDao.count();
	}

	public List<Notices> getNoticesAll(int notice) {
		String hql = " from Notices n where n.noticeId = ? and n.status = 1";
		Object[] param = { notice };
		List<Notices> notices = new ArrayList<Notices>();
		notices = DaoFactory.getNoticesDaoInstance().find(hql, param);
		return notices;
	}

	@Override
	public Notices getOneNotices(int id) {
		String hql = " from Notices n where n.id = ? ";
		Object[] param = { id };
		Notices notices = DaoFactory.getNoticesDaoInstance().get(hql, param);
		return notices;
	}

	@Override
	public boolean hasNewMessage(Student student) {
		String hql = " from Ssjl s where s.reader = ? AND s.status = 3 ";
		Object[] param = { student.getNumber() };
		List<Ssjl> ssjls = DaoFactory.getSsjlDaoInstance().find(hql, param);
		if (ssjls != null && ssjls.size() >= 1) {
			return true;
		} else
			return false;
	}

	@Override
	public int caculateMessage(String xuehao) {
		String hql = " from Ssjl s where s.reader = ? OR s.writer = ? ";
		Object[] param = { xuehao, xuehao };
		List<Ssjl> ssjls = DaoFactory.getSsjlDaoInstance().find(hql, param);
		if (ssjls == null || ssjls.size() < 0)
			return 0;
		return ssjls.size();
	}

	@Override
	public List<Ssjl> getSsjls(int page, String xuehao) {
		String hql = " select * from tb_ssjl s where s.va_writer = '" + xuehao + "' or s.va_reader = '" + xuehao
				+ "' order by id DESC limit " + (page - 1) * 10 + ",10 ";
		Session session = null;
		session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createSQLQuery(hql).addEntity(Ssjl.class);
		transaction.commit();
		@SuppressWarnings("unchecked")
		List<Ssjl> ssjls = query.list();
		session.close();
		for (Ssjl ssjl : ssjls) {// 设置为已读
			if (ssjl.getStatus() == 3){
				ssjl.setStatus(2);
				DaoFactory.getSsjlDaoInstance().update(ssjl);
			}
		}
		return ssjls;
	}

	@Override
	public int getStudentJdxcgsNum(Student student) {
		String hql = " from Jdxcgs j where j.xuehao = ? and j.status = 1 ";
		Object[] param = { student.getNumber() };
		List<Jdxcgs> jdxcgs = DaoFactory.getJdxcgsDaoInstance().find(hql, param);
		if (jdxcgs == null || jdxcgs.size() == 0)
			return 0;
		else
			return jdxcgs.size();
	}

	@Override
	public int getStudentLwsNum(Student student) {
		String hql = " from Lws l where l.xuehao = ? and l.status = 1 ";
		Object[] param = { student.getNumber() };
		List<Lws> lws = DaoFactory.getLwsDaoInstance().find(hql, param);
		if (lws == null || lws.size() == 0)
			return 0;
		else
			return lws.size();
	}

	@Override
	public Lws getStudentLws(int id) {
		Lws lws = DaoFactory.getLwsDaoInstance().get(Lws.class, id);
		return lws;
	}

	@Override
	public void updateLws(Lws lws) {
		DaoFactory.getLwsDaoInstance().update(lws);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int countStudentByYuanxiAndTailNumber(int yuanxiId, String[] tailNumber) {
		// SELECT COUNT(*) FROM tb_student WHERE va_yxid= 1 AND
		// RIGHT(va_number,1)= 1 OR RIGHT(va_number,1)= 2"
		String hql = "";
		if (tailNumber.length == 1)
			hql = "SELECT COUNT(*) FROM tb_student WHERE va_yxid= " + yuanxiId + " AND ( RIGHT(va_number,1)= "
					+ tailNumber[0] + " )";
		if (tailNumber.length == 2)
			hql = "SELECT COUNT(*) FROM tb_student WHERE va_yxid= " + yuanxiId + " AND ( RIGHT(va_number,1)= "
					+ tailNumber[0] + " OR RIGHT(va_number,1)= " + tailNumber[1] + " )";
		if (tailNumber.length == 3)
			hql = "SELECT COUNT(*) FROM tb_student WHERE va_yxid= " + yuanxiId + " AND ( RIGHT(va_number,1)= "
					+ tailNumber[0] + " OR RIGHT(va_number,1)= " + tailNumber[1] + " OR RIGHT(va_number,1)= "
					+ tailNumber[2] + " )";

		Session session = null;
		session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		System.out.println(hql);
		Query query = session.createSQLQuery(hql);
		transaction.commit();
		List<BigInteger> result = new ArrayList<>();
		result = (List) query.list();
		// System.out.println(query.list().toString());
		session.close();
		return result.get(0).intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Student> findStudentByYuanxiAndTailNumber(int yuanxiId, String[] tailNumber) {
		List<Student> students = new ArrayList<>();
		String hql = "";
		if (tailNumber.length == 1)
			hql = "SELECT * FROM tb_student WHERE va_yxid= " + yuanxiId + " AND ( RIGHT(va_number,1)= " + tailNumber[0]
					+ " )";
		if (tailNumber.length == 2)
			hql = "SELECT * FROM tb_student WHERE va_yxid= " + yuanxiId + " AND ( RIGHT(va_number,1)= " + tailNumber[0]
					+ " OR RIGHT(va_number,1)= " + tailNumber[1] + " )";
		if (tailNumber.length == 3)
			hql = "SELECT * FROM tb_student WHERE va_yxid= " + yuanxiId + " AND ( RIGHT(va_number,1)= " + tailNumber[0]
					+ " OR RIGHT(va_number,1)= " + tailNumber[1] + " OR RIGHT(va_number,1)= " + tailNumber[2] + " )";

		Session session = null;
		session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		System.out.println(hql);
		Query query = session.createSQLQuery(hql).addEntity(Student.class);
		transaction.commit();
		students = query.list();
		session.close();
		return students;
	}

	@Override
	public List<Student> findStudentByYuanxiId(int yuanxiId) {
		return studentDao.findByYuanxi(yuanxiId);
	}

	@Override
	public List<Student> findUnbindedStudentByYuanxi(int yuanxiId) {
		List<Student> result = new ArrayList<>();
		List<Student> temps = new ArrayList<>();
		temps = findStudentByYuanxiId(yuanxiId);
		if (temps.size() > 0) {
			for (Student student : temps) {
				if (null == student.getTeacher()) {
					result.add(student);
				}
			}
		}
		return result;
	}

	@Override
	public void cancelBindByStudentNumber(String number) {
		Student student = studentDao.findByNumber(number);
		student.setTeacher(null);
		studentDao.update(student);
	}

	@Override
	public List<Student> findStudentByZhuanyeId(int zhuanyeId) {
		return studentDao.findByZY(zhuanyeId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Student> findUnbindedStudentByZhuanyeId(int zhuanyeId, int banji, int nowPage) {
		List<Student> students = new ArrayList<>();
		String sql = "";
		if (banji == 0) {
			sql = "SELECT * FROM tb_student AS stu WHERE stu.va_zyid =" + zhuanyeId
					+ "  AND  stu.va_teacher IS NULL LIMIT " + (nowPage - 1) * 10 + ",10 ";
		} else {
			sql = "SELECT * FROM tb_student AS stu WHERE stu.va_zyid =" + zhuanyeId + " AND  stu.va_class =" + banji
					+ "  AND  stu.va_teacher IS NULL LIMIT " + (nowPage - 1) * 10 + ",10 ";
		}
		Session session = null;
		session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createSQLQuery(sql).addEntity(Student.class);
		transaction.commit();
		students = query.list();
		session.close();
		return students;
	}

	@Override
	public int CountUnbindedStudentByZhuanyeId(int zhuanyeId, int banji) {
		int result = 0;
		String sql = "";
		if (banji == 0) {
			sql = "SELECT COUNT(*) FROM tb_student AS stu WHERE stu.va_zyid =" + zhuanyeId
					+ "  AND  stu.va_teacher IS NULL ";
		} else {
			sql = "SELECT COUNT(*) FROM tb_student AS stu WHERE stu.va_zyid =" + zhuanyeId + " And stu.va_class="
					+ banji + "  AND  stu.va_teacher IS NULL ";
		}

		Session session = null;
		session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		transaction.commit();
		Object object = session.createSQLQuery(sql).uniqueResult();
		if (object != null) {
			BigInteger b = (BigInteger) object;
			result = b.intValue();
		}
		session.close();
		return result;
	}

	@Override
	public Student findStudentByNumber(String number) {
		return studentDao.findByNumber(number);
	}

	@Override
	public List<Student> vagueSearchByName(String name) {
		List<Student> students = new ArrayList<>();
		students = studentDao.vagueSearchByName(name);
		return students;
	}

	@Override
	public List<Student> findBindedByTeacherNumber(String number) {
		return studentDao.findByTeacherNumber(number);
	}

	public int getStudentZuhao(Student student) {
		String hql = " from Xsz x where x.xuehao = ? ";
		Object[] param = { student.getNumber() };
		Xsz xsz = DaoFactory.getXszDaoInstance().get(hql, param);
		if (xsz == null) {
			return 0;
		}
		return xsz.getZh();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Student> pageFindStudentByYuanxi(int yuanxiId, int zhuanyeId, int nowPage) {
		String sql = "SELECT * FROM tb_student AS stu WHERE stu.va_yxid =" + yuanxiId + "   AND stu.va_zyid= "
				+ zhuanyeId + " ORDER BY stu.va_number LIMIT " + (nowPage - 1) * 10 + ",10";
		Session session = null;
		session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createSQLQuery(sql).addEntity(Student.class);
		transaction.commit();
		List<Student> students = new ArrayList<>();
		students = query.list();
		session.close();
		return students;
	}

	@Override
	public int CountStudentByYuanxi(int yuanxiId, int zhuanyeId) {
		int result = 0;
		String sql = "SELECT COUNT(*) FROM tb_student AS stu WHERE stu.va_yxid =" + yuanxiId + "   AND stu.va_zyid= "
				+ zhuanyeId + " ORDER BY stu.va_number ";
		Session session = null;
		session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		transaction.commit();
		Object object = session.createSQLQuery(sql).uniqueResult();
		if (object != null) {
			BigInteger b = (BigInteger) object;
			result = b.intValue();
		}
		session.close();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONArray pageFindStudentFinalScoreByYuanxiZhuanyeBanji(int yuanxiId, int zhuanyeId, String banji,
			int nowPage) {
		JSONArray jsonArray = null;
		List<Object> result = new ArrayList<>();
		String sql = "";
		if (zhuanyeId == 0) {
			sql = "SELECT zhuanye.va_name AS zhuanyeName,stu.va_class AS stuClass,stu.va_number AS stuNumber ,stu.va_name AS stuName ,zf.va_zf AS stuZf FROM tb_student AS stu,tb_zhuanye AS zhuanye,tb_zf AS zf WHERE stu.va_yxid ="
					+ yuanxiId
					+ " AND stu.va_zyid=zhuanye.id AND stu.va_number =zf.va_xsxh  ORDER BY stu.va_zyid,stu.va_class,stu.va_number LIMIT  "
					+ (nowPage - 1) * 10 + ",10 ";
		} else {
			if (banji.equals("0")) {
				sql = "SELECT zhuanye.va_name AS zhuanyeName,stu.va_class AS stuClass,stu.va_number AS stuNumber ,stu.va_name AS stuName ,zf.va_zf AS stuZf FROM tb_student AS stu,tb_zhuanye AS zhuanye,tb_zf AS zf WHERE stu.va_yxid ="
						+ yuanxiId + " AND stu.va_zyid =" + zhuanyeId
						+ "  AND stu.va_zyid=zhuanye.id AND stu.va_number =zf.va_xsxh  ORDER BY stu.va_zyid,stu.va_class,stu.va_number LIMIT  "
						+ (nowPage - 1) * 10 + ",10 ";
			} else {
				sql = "SELECT zhuanye.va_name AS zhuanyeName,stu.va_class AS stuClass,stu.va_number AS stuNumber ,stu.va_name AS stuName ,zf.va_zf AS stuZf FROM tb_student AS stu,tb_zhuanye AS zhuanye,tb_zf AS zf WHERE stu.va_yxid ="
						+ yuanxiId + "  AND stu.va_zyid =" + zhuanyeId + " AND stu.va_class =" + banji
						+ "   AND stu.va_zyid=zhuanye.id AND stu.va_number =zf.va_xsxh  ORDER BY stu.va_zyid,stu.va_class,stu.va_number LIMIT  "
						+ (nowPage - 1) * 10 + ",10 ";
			}
		}

		Session session = null;
		session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		result = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		transaction.commit();
		session.close();
		int all = CountStudentFinalScoreByYuanxiZhuanyeBanji(yuanxiId, zhuanyeId, banji);
		int totalPage = 0;
		if (all % 10 == 0) {
			totalPage = all / 10;
		} else {
			totalPage = all / 10 + 1;
		}
		String jsonStr = "[";
		if (result.size() > 0) {
			String pageStr = "{nowPage:'" + nowPage + "',totalPage:'" + totalPage + "'},";
			jsonStr += pageStr;
			for (Object o : result) {
				Map map = (Map) o;
				String tempStr = "{ zhuanyeName:'" + map.get("zhuanyeName") + "',stuClass:'" + map.get("stuClass")
						+ "',stuNumber:'" + map.get("stuNumber") + "',stuName:'" + map.get("stuName") + "',stuZf:'"
						+ map.get("stuZf") + "'},";
				jsonStr += tempStr;
			}
			jsonStr = jsonStr.substring(0, jsonStr.length() - 1);
		}
		jsonStr += "]";
		jsonArray = JSONArray.fromObject(jsonStr);
		return jsonArray;
	}

	@Override
	public int CountStudentFinalScoreByYuanxiZhuanyeBanji(int yuanxiId, int zhuanyeId, String banji) {
		int result = 0;
		String sql = "";
		if (zhuanyeId == 0) {
			sql = "SELECT COUNT(*) AS allStu   FROM tb_student AS stu,tb_zhuanye AS zhuanye,tb_zf AS zf WHERE stu.va_yxid ="
					+ yuanxiId
					+ " AND stu.va_zyid=zhuanye.id AND stu.va_number =zf.va_xsxh  ORDER BY stu.va_zyid,stu.va_class,stu.va_number ";
		} else {
			if (banji.equals("0")) {
				sql = "SELECT  COUNT(*) AS allStu    FROM tb_student AS stu,tb_zhuanye AS zhuanye,tb_zf AS zf WHERE stu.va_yxid ="
						+ yuanxiId + " AND stu.va_zyid =" + zhuanyeId
						+ "  AND stu.va_zyid=zhuanye.id AND stu.va_number =zf.va_xsxh  ORDER BY stu.va_zyid,stu.va_class,stu.va_number ";
			} else {
				sql = "SELECT  COUNT(*) AS allStu    FROM tb_student AS stu,tb_zhuanye AS zhuanye,tb_zf AS zf WHERE stu.va_yxid ="
						+ yuanxiId + "  AND stu.va_zyid =" + zhuanyeId + " AND stu.va_class =" + banji
						+ "   AND stu.va_zyid=zhuanye.id AND stu.va_number =zf.va_xsxh  ORDER BY stu.va_zyid,stu.va_class,stu.va_number ";
			}
		}
		Session session = null;
		session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Object object = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list()
				.get(0);
		transaction.commit();
		session.close();
		Map map = (Map) object;
		BigInteger b = (BigInteger) map.get("allStu");
		result = b.intValue();
		return result;
	}

	@Override
	public List<String[]> ExportStudentFinalScoreByYuanxiZhuanyeBanji(int yuanxiId, int zhuanyeId, String banji) {
		JSONArray jsonArray = null;
		List<String[]> strings = new ArrayList<>();
		List<Object> result = new ArrayList<>();
		String sql = "";
		if (zhuanyeId == 0) {
			sql = "SELECT zhuanye.va_name AS zhuanyeName,stu.va_class AS stuClass,stu.va_number AS stuNumber ,stu.va_name AS stuName ,zf.va_zf AS stuZf FROM tb_student AS stu,tb_zhuanye AS zhuanye,tb_zf AS zf WHERE stu.va_yxid ="
					+ yuanxiId
					+ " AND stu.va_zyid=zhuanye.id AND stu.va_number =zf.va_xsxh  ORDER BY stu.va_zyid,stu.va_class,stu.va_number  ";
		} else {
			if (banji.equals("0")) {
				sql = "SELECT zhuanye.va_name AS zhuanyeName,stu.va_class AS stuClass,stu.va_number AS stuNumber ,stu.va_name AS stuName ,zf.va_zf AS stuZf FROM tb_student AS stu,tb_zhuanye AS zhuanye,tb_zf AS zf WHERE stu.va_yxid ="
						+ yuanxiId + " AND stu.va_zyid =" + zhuanyeId
						+ "  AND stu.va_zyid=zhuanye.id AND stu.va_number =zf.va_xsxh  ORDER BY stu.va_zyid,stu.va_class,stu.va_number  ";
			} else {
				sql = "SELECT zhuanye.va_name AS zhuanyeName,stu.va_class AS stuClass,stu.va_number AS stuNumber ,stu.va_name AS stuName ,zf.va_zf AS stuZf FROM tb_student AS stu,tb_zhuanye AS zhuanye,tb_zf AS zf WHERE stu.va_yxid ="
						+ yuanxiId + "  AND stu.va_zyid =" + zhuanyeId + " AND stu.va_class =" + banji
						+ "   AND stu.va_zyid=zhuanye.id AND stu.va_number =zf.va_xsxh  ORDER BY stu.va_zyid,stu.va_class,stu.va_number  ";
			}
		}

		Session session = null;
		session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		result = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		transaction.commit();
		session.close();
		int all = CountStudentFinalScoreByYuanxiZhuanyeBanji(yuanxiId, zhuanyeId, banji);
		if (result.size() > 0) {
			String[] title = { "专业", "班级", "学号", "姓名", "毕设总分" };
			strings.add(title);
			for (Object o : result) {
				Map map = (Map) o;
				String[] s = { map.get("zhuanyeName").toString(), map.get("stuClass").toString(),
						map.get("stuNumber").toString(), map.get("stuName").toString(), map.get("stuZf").toString() };
				strings.add(s);
			}
		}

		return strings;
	}

	@Override
	public void deleteNotices(Notices notices) {
		DaoFactory.getNoticesDaoInstance().delete(notices);

	}

}
