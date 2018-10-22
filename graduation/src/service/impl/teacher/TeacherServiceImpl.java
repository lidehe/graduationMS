package service.impl.teacher;

import dao.JsgxDao;
import dao.TeacherDao;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
import domain.Year;
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
import factory.DaoFactory;
import factory.JWCFactory;
import factory.ServiceFactory;
import factory.TeacherDaoFactory;
import net.sf.json.JSONObject;
import service.TeacherService;
import utils.DengJi;
import utils.FloatUtil;
import utils.IuimSesssionFactory;
import utils.SetStudentStage;

public class TeacherServiceImpl implements TeacherService {

	private TeacherDao teacherDao = TeacherDaoFactory.getTeacherDaoInstance();
	private JsgxDao jsgxDao = JWCFactory.getJsgxDao();

	@Override
	public void addTeacher(Teacher teacher) {

		teacherDao.addTeacher(teacher);
	}

	public List<Jsgx> findTeacherJsgxByNumber(String number) {
		return jsgxDao.findJsgxByTeacherNumber(number);
	}

	@Override
	public List<Teacher> vagueSearchByName(String name) {
		List<Teacher> teachers = new ArrayList<>();
		teachers = teacherDao.vagueSearchByName(name);
		return teachers;
	}

	@Override
	public Teacher getTeacherByAccount(String account) {

		String hql = "from Teacher a where a.number = ? ";
		Object[] param = { account };
		Teacher teacher;
		teacher = DaoFactory.getTeacherDaoInstance().get(hql, param);
		return teacher;
	}

	@Override
	public Teacher getTeacherByAccountYear(String account) {

		String hql = "from Teacher a where a.number = ? ";
		Object[] param = { account };
		Teacher teacher;
		teacher = DaoFactory.getTeacherDaoInstance().get(hql, param);
		return teacher;
	}

	@Override
	public Teacher getProfessionPrincipal(int zy) {

		String hql = " from Teacher t where t.fzr = ? ";
		Object[] param = { zy };
		Teacher teacher = DaoFactory.getTeacherDaoInstance().get(hql, param);
		return teacher;
	}

	@Override
	public List<Jsgx> getTeacherJses(Teacher teacher) {

		String hql = " from Jsgx j where j.gonghao = ? ";
		Object[] param = { teacher.getNumber() };
		List<Jsgx> jses = DaoFactory.getJsgxDaoInstance().find(hql, param);
		return jses;
	}

	@Override
	public List<Student> getTeacherStudentsEsc(Teacher teacher) {

		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String hql = "select * from tb_student s where s.va_teacher = '" + teacher.getNumber() + "' and s.va_year = "
				+ year.getYear() + " order by s.va_stage ";
		Session session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createSQLQuery(hql).addEntity(Student.class);
		transaction.commit();
		@SuppressWarnings("unchecked")
		List<Student> students = query.list();
		session.close();
		return students;
	}

	@Override
	public Lw getStudentLw(Student student) {

		if (student == null || student.getBs() == 0) {
			return null;
		}
		Lw lw = DaoFactory.getLwDaoInstance().get(Lw.class, student.getBs());
		return lw;
	}

	@Override
	public List<Kt> getTeacherAllKts(Teacher teacher) {

		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String hql = "select * from tb_kt k where k.va_zdls = '" + teacher.getNumber() + "' AND k.va_year = "
				+ year.getYear() + " order by k.id ";
		Session session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createSQLQuery(hql).addEntity(Kt.class);
		transaction.commit();
		@SuppressWarnings("unchecked")
		List<Kt> kts = query.list();
		session.close();
		return kts;
	}

	@Override
	public int shKt(Teacher teacher, int id) {

		if (id == 0)
			return 0;
		Kt kt = DaoFactory.getKtDaoInstance().get(Kt.class, id);
		if (kt == null)
			return 0;// kt不存在
		if (!kt.getGonghao().equals(teacher.getNumber()))
			return -1;// 这个课题不是这个老师的
		kt.setSh(1);
		DaoFactory.getKtDaoInstance().update(kt);
		return 1;
	}

	@Override
	public int deleteKt(Teacher teacher, int id) {

		if (id == 0)
			return -2;
		Kt kt = DaoFactory.getKtDaoInstance().get(Kt.class, id);
		if (kt == null)
			return -2;// kt不存在
		if (!kt.getGonghao().equals(teacher.getNumber()))
			return -1;// 这个课题不是这个老师的
		String hql = " from Student s where s.kt = ? ";
		Object[] param = { id };
		List<Kt> kts = DaoFactory.getKtDaoInstance().find(hql, param);
		if (kts.size() > 0)
			return -3;// 有学生选择了，无法删除
		DaoFactory.getKtDaoInstance().delete(kt);
		Rws rws = getOneRws(kt.getId());
		if (rws != null && rws.getStatus() == 1) {
			DaoFactory.getRwsDaoInstance().delete(rws);
			return rws.getId();// 返回一个id，告诉servlet，要删除这个rws
		}
		return 0;
	}

	@Override
	public List<Zhuanye> zdlsGetStudentAllZy(Teacher teacher) {

		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String hql = " from Zhuanye z where z.id in ( select zy from Student s where s.year = ? AND s.teacher = ? group by zy ) ";
		Object[] param = { year.getYear(), teacher.getNumber() };
		List<Zhuanye> zys = DaoFactory.getZhuanyeDaoInstance().find(hql, param);
		return zys;
	}

	@Override
	public List<Byfs> zdlsGetStudentAllByfs(Teacher teacher) {
		// String hql = " from Byfs b where b.id in ( select type from Student s where
		// s.year = ? AND s.teacher = ? group by type ) ";
		String hql = " from Byfs b ";
		List<Byfs> byfs = DaoFactory.getByfsDaoInstance().find(hql);
		return byfs;
	}

	@Override
	public int teacherAddKt(Teacher teacher, int zy, String name, String text, int xzrs, int bylx) {

		if (zy == 0)
			return -1;
		Zhuanye zhuanye = DaoFactory.getZhuanyeDaoInstance().get(Zhuanye.class, zy);
		Teacher zyfzr = getZhuanyeFzr(zhuanye);
		if (zhuanye == null)
			return -1;
		if (zyfzr == null)
			return -1;
		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		Kt kt = new Kt(zyfzr.getNumber(), teacher.getNumber(), 0, 1, name, text, 1, zhuanye.getYx(), zhuanye.getId(),
				xzrs, year.getYear(), bylx);
		DaoFactory.getKtDaoInstance().save(kt);
		return 1;
	}

	@Override
	public Teacher getZhuanyeFzr(Zhuanye zy) {

		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String hql = "from Teacher t where t.fzr = ? AND t.year = ? ";
		Object[] param = { zy.getId(), year.getYear() };
		Teacher teacher = DaoFactory.getTeacherDaoInstance().get(hql, param);
		return teacher;
	}

	public List<Rws> getTeacherAllRwsOrderKt(Teacher teacher) {
		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String hql = "select * from tb_rws r where r.va_zdls = '" + teacher.getNumber() + "' AND r.va_year = "
				+ year.getYear() + " order by r.va_kt ";
		Session session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createSQLQuery(hql).addEntity(Rws.class);
		transaction.commit();
		@SuppressWarnings("unchecked")
		List<Rws> rws = query.list();
		session.close();
		return rws;
	}

	@Override
	public List<Rws> zyfzrgetKtAllRwsOrderByKt(int zy) {

		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String hql = "select * from tb_rws r where r.va_zy = " + zy + " AND r.va_year = " + year.getYear()
				+ " order by r.va_kt ";
		Session session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createSQLQuery(hql).addEntity(Rws.class);
		transaction.commit();
		@SuppressWarnings("unchecked")
		List<Rws> rws = query.list();
		session.close();
		return rws;
	}

	@Override
	public Rws getOneRws(int kt) {
		String hql = " from Rws r where r.kt = ? ";
		Object[] param = { kt };
		Rws rws = DaoFactory.getRwsDaoInstance().get(hql, param);
		return rws;
	}

	@Override
	public List<Integer> getTeacherKtStudents(List<Kt> kts) {

		List<Integer> number = new ArrayList<>();
		for (Kt kt : kts) {
			number.add(getOneKtStudents(kt.getId()));
		}
		return number;
	}

	@Override
	public int getOneKtStudents(int kt) {
		String hql = " select count(*) from Student s where s.kt = ? ";
		Object[] param = { kt };
		return DaoFactory.getStudentDaoInstance().count(hql, param);
	}

	@Override
	public List<Rws> getTeacherAllRws(Teacher teacher) {

		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String hql = "select * from tb_rws r where r.va_zdls = '" + teacher.getNumber() + "' AND r.va_year = "
				+ year.getYear() + " order by r.va_kt ";
		Session session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createSQLQuery(hql).addEntity(Rws.class);
		transaction.commit();
		@SuppressWarnings("unchecked")
		List<Rws> rws = query.list();
		session.close();
		return rws;
	}

	@Override
	public int createRwsRecordByTeacher(int kt, Teacher teacher) {

		if (kt == 0)
			return -1;
		Kt k = DaoFactory.getKtDaoInstance().get(Kt.class, kt);
		if (!k.getGonghao().equals(teacher.getNumber()))
			return -2;// 不是这个老师的
		String hql = " from Rws r where r.kt = ? ";
		Object[] param = { kt };
		Rws rws = DaoFactory.getRwsDaoInstance().get(hql, param);
		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		if (rws == null) {
			rws = new Rws(teacher.getNumber(), kt, k.getZy(), 0, "", year.getYear());
			DaoFactory.getRwsDaoInstance().save(rws);
			return rws.getId();
		} else
			return rws.getId();
	}

	@Override
	public void updateRwsStatus(int id, String file) {

		Rws rws = DaoFactory.getRwsDaoInstance().get(Rws.class, id);
		rws.setStatus(1);
		rws.setFileName(file);
		DaoFactory.getRwsDaoInstance().update(rws);
	}

	@Override
	public List<Student> getTeachersStudentOrderXuehao(Teacher teacher) {

		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String hql = "select * from tb_student s where s.va_teacher = '" + teacher.getNumber() + "' and s.va_year = "
				+ year.getYear() + " order by s.va_number ";
		Session session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createSQLQuery(hql).addEntity(Student.class);
		transaction.commit();
		@SuppressWarnings("unchecked")
		List<Student> students = query.list();
		session.close();
		return students;
	}

	@Override
	public List<Ktbg> getTeachersStudentKtbgOrderXuehao(Teacher teacher) {

		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String hql = "select * from tb_ktbg k where k.va_ls = '" + teacher.getNumber() + "' and k.va_year = "
				+ year.getYear() + " order by k.va_xs ";
		Session session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createSQLQuery(hql).addEntity(Ktbg.class);
		transaction.commit();
		@SuppressWarnings("unchecked")
		List<Ktbg> ktbgs = query.list();
		session.close();
		return ktbgs;
	}

	@Override
	public List<Jdxcg> getTeachersStudentJdxcgsOrderXuehao(Teacher teacher) {

		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String hql = "select * from tb_jdxcg j where j.va_xs in ( select va_number from tb_student s where s.va_teacher = '"
				+ teacher.getNumber() + "' and s.va_year = " + year.getYear() + " ) order by j.va_xs ";
		Session session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createSQLQuery(hql).addEntity(Jdxcg.class);
		transaction.commit();
		@SuppressWarnings("unchecked")
		List<Jdxcg> jdxcgs = query.list();
		session.close();
		return jdxcgs;
	}

	@Override
	public List<Lw> getTeachersStudentLwsOrderXuehao(Teacher teacher) {

		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String hql = "select * from tb_lw l where l.va_xsxh in ( select va_number from tb_student s where s.va_teacher = '"
				+ teacher.getNumber() + "' and s.va_year = " + year.getYear() + " ) order by l.va_xsxh ";
		Session session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createSQLQuery(hql).addEntity(Lw.class);
		transaction.commit();
		@SuppressWarnings("unchecked")
		List<Lw> lws = query.list();
		session.close();
		return lws;
	}

	@Override
	public Lw TeacherGetStudentLw(Student student) {

		Lw lw = DaoFactory.getLwDaoInstance().get(Lw.class, student.getBs());
		return lw;
	}

	@Override
	public List<Lw> teacherGetStudentLwsOrderXuehao(Teacher teacher) {

		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String hql = "select * from tb_lw l where l.va_zdls = '" + teacher.getNumber() + "' and l.va_year = "
				+ year.getYear() + " order by l.va_xsxh ";
		Session session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createSQLQuery(hql).addEntity(Lw.class);
		transaction.commit();
		@SuppressWarnings("unchecked")
		List<Lw> lws = query.list();
		session.close();
		return lws;
	}

	@Override
	public List<Lwcg> teacherGetStudentLwcgsOrderXuehao(Teacher teacher) {

		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String hql = "select * from tb_lwcg l where l.va_zdls = '" + teacher.getNumber() + "' and l.va_year = "
				+ year.getYear() + " order by l.va_xsxh ";
		Session session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createSQLQuery(hql).addEntity(Lwcg.class);
		transaction.commit();
		@SuppressWarnings("unchecked")
		List<Lwcg> lws = query.list();
		session.close();
		return lws;
	}

	@Override
	public Zhuanye teacherGetZy(int id) {

		Zhuanye zy = DaoFactory.getZhuanyeDaoInstance().get(Zhuanye.class, id);
		return zy;
	}

	@Override
	public Kt teacherGetKt(int id) {

		Kt kt = DaoFactory.getKtDaoInstance().get(Kt.class, id);
		return kt;
	}

	@Override
	public Yuanxi teacherGetYx(int id) {

		Yuanxi yx = DaoFactory.getYuanxiDaoInstance().get(Yuanxi.class, id);
		return yx;
	}

	@Override
	public void teacherAddZdlspsb(List<Object> forms, Teacher teacher, Student student) {

		List<Zdlspsbx> zdlspsbxs = getZdlspsb();
		int isdb = (int) forms.get(zdlspsbxs.size());
		for (int i = 0, l = zdlspsbxs.size(); i < l; i++) {
			Zdlspsb psb = new Zdlspsb(teacher.getNumber(), student.getNumber(), zdlspsbxs.get(i).getId(), 0, "");
			if (zdlspsbxs.get(i).getStatus() == 0) {
				psb.setFs((int) forms.get(i));
			} else {
				psb.setText((String) forms.get(i));
			}
			DaoFactory.getZdlspsbDaoInstance().save(psb);
		}
		if (isdb == 0) {
			student.setAnswer(1);
			DaoFactory.getStudentDaoInstance().update(student);
		}
	}

	@Override
	public List<Byfs> teacherGeaAllStudentByfs(List<Student> students) {

		List<Byfs> byfs = new ArrayList<Byfs>();
		for (Student student : students) {
			Byfs byfs2 = new Byfs();
			if (student.getType() == -1 || student.getType() == 0)
				byfs2 = ServiceFactory.getStudentServiceInstance().getStudentByfs(-1);
			else
				byfs2 = DaoFactory.getByfsDaoInstance().get(Byfs.class, student.getType());
			byfs.add(byfs2);
		}
		return byfs;
	}

	@Override
	public Dbpsb teacherGetStudentDbpsb(Teacher teacher, String xuehao) {

		String hql = " from Dbpsb d where d.gonghao = ? AND d.xuehao = ? ";
		Object[] param = { teacher.getNumber(), xuehao };
		Dbpsb dbpsb = DaoFactory.getDbpsbDaoInstance().get(hql, param);
		return dbpsb;
	}

	@Override
	public Pypsb teacherGetStudentPypsb(Teacher teacher, String xuehao) {

		String hql = " from Pypsb p where p.gonghao = ? AND p.xuehao = ? ";
		Object[] param = { teacher.getNumber(), xuehao };
		Pypsb pypsb = DaoFactory.getPypsbDaoInstance().get(hql, param);
		return pypsb;
	}

	@Override
	public List<Dbjsz> getDbjsById(int bdz) {

		if (bdz == 0)
			return null;
		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String hql = " from Dbjsz d where d.zh = ? AND d.year = ? ";
		Object[] param = { bdz, year.getYear() };
		List<Dbjsz> dbjs = DaoFactory.getDbjszDaoInstance().find(hql, param);
		return dbjs;
	}

	@Override
	public void addDbpsb(List<Object> forms, Teacher teacher, String xuehao) {

		List<Dbpsbx> dbpsbxs = getDbpsbxOrderId();
		for (int i = 0, l = dbpsbxs.size(); i < l; i++) {
			Dbpsb dbpsb = new Dbpsb(teacher.getNumber(), xuehao, dbpsbxs.get(i).getId(), 0, "");
			if (dbpsbxs.get(i).getStatus() == 0) {
				dbpsb.setFs((float) forms.get(i));
			} else {
				dbpsb.setText((String) forms.get(i));
			}
			DaoFactory.getDbpsbDaoInstance().save(dbpsb);
		}
	}

	@Override
	public void addPypsb(List<Object> forms, Teacher teacher, String xuehao) {

		List<Pypsbx> pypsbxs = getPypsbxOrderId();
		for (int i = 0, l = pypsbxs.size(); i < l; i++) {
			Pypsb pypsb = new Pypsb(teacher.getNumber(), xuehao, pypsbxs.get(i).getId(), 0, "");
			if (pypsbxs.get(i).getStatus() == 0) {
				pypsb.setFs((float) forms.get(i));
			} else {
				pypsb.setText((String) forms.get(i));
			}
			DaoFactory.getPypsbDaoInstance().save(pypsb);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Dbzl> teacherGetAllDbz(Teacher teacher) {

		List<Dbzl> dbz = new ArrayList<>();
		String hql = "from Dbzl d where d.id in (select zh from Dbjsz j where j.gonghao = ? ) ";
		Session session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createQuery(hql);
		query.setString(0, teacher.getNumber());
		transaction.commit();
		dbz = query.list();
		return dbz;
	}

	public Teacher teacherGetDbzz(int zh) {
		String hql = " from Dbjsz d where d.zh = ? AND d.zz = 1 ";
		Object[] param = { zh };
		Dbjsz js = DaoFactory.getDbjszDaoInstance().get(hql, param);
		Teacher zz = getTeacherByAccount(js.getGonghao());
		return zz;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Teacher> teacherGetCommonDbzTeacher(int zh) {

		List<Teacher> teachers = new ArrayList<>();
		String hql = "from Teacher t where t.number in (select gonghao from Dbjsz j where j.zh = ? ) ";
		Session session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createQuery(hql);
		query.setInteger(0, zh);
		transaction.commit();
		teachers = query.list();
		return teachers;
	}

	@Override
	public List<Student> teacherGetDbzStudents(int zh) {

		String hql = " from Student s where s.dbz = ? AND s.year = ? ";
		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		Object[] param = { zh, year.getYear() };
		List<Student> students = DaoFactory.getStudentDaoInstance().find(hql, param);
		return students;
	}

	@Override
	public void shKtbg(int id) {

		Ktbg ktbg = DaoFactory.getKtbgDaoInstance().get(Ktbg.class, id);
		if (ktbg == null)
			return;
		ktbg.setSh(1);
		DaoFactory.getKtbgDaoInstance().update(ktbg);
		Student student = ServiceFactory.getStudentServiceInstance().getStudentByAccount(ktbg.getXuehao());
		if (student.getStage() == 6 || student.getStage() == 7) {
			int stage = SetStudentStage.setSan(student);
			student.setStage(stage);// 更新学生状态信息，因为后来要加入老师审核，
			DaoFactory.getStudentDaoInstance().update(student);
		}
	}

	@Override
	public List<Kt> zyfzrGetAllKts(int zy) {

		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String hql = "select * from tb_kt k where k.va_zy = " + zy + " AND k.va_zdlss = 1 AND k.va_year = "
				+ year.getYear() + " order by k.id ";
		Session session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createSQLQuery(hql).addEntity(Kt.class);
		transaction.commit();
		@SuppressWarnings("unchecked")
		List<Kt> kts = query.list();
		session.close();
		return kts;
	}

	@Override
	public void zyfzrUpdateKt(int id, int sh) {

		Kt kt = DaoFactory.getKtDaoInstance().get(Kt.class, id);
		kt.setFzrsh(sh);
		DaoFactory.getKtDaoInstance().update(kt);
	}

	@Override
	public Zpjl zyfzrGetZpjl(Teacher teacher) {

		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String hql = " from Zpjl z where z.gonghao = ? AND z.year = ? ";
		Object[] param = { teacher.getNumber(), year.getYear() };
		Zpjl zpjl = DaoFactory.getZpjlDaoInstance().get(hql, param);
		return zpjl;
	}

	@Override
	public void addZpjl(Zpjl zpjl) {

		DaoFactory.getZpjlDaoInstance().save(zpjl);
	}

	@Override
	public void calcuScoreFirst(Teacher teacher) {

		List<Student> students = new ArrayList<Student>();
		String hql = " from Student s where s.zy = ? AND s.year = ? ";
		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		Object[] param = { teacher.getFzr(), year.getYear() };
		students = DaoFactory.getStudentDaoInstance().find(hql, param);
		for (Student student : students) {
			if (student.getAnswer() == 0) {// 因为前面有些人已经被指导老师设置为第二次答辩了
				calculateOneStudent(student, 2);// 第一次计算
			}
		}
	}

	@Override
	public int calculateOneStudent(Student student, int answer) {

		float pycj = 0, dbcj = 0, zdcj = 0, lwcj = 0, sxcj = 0, zf = 0;
		List<Pypsbx> pyx = getPypsbxOrderId();
		List<Dbpsbx> dbx = getDbpsbxOrderId();

		if (pyx == null || pyx.size() <= 0) {
			return 0;
		}
		List<Pypsb> pypsbs = getStudentPypsbs(student, pyx.get(0).getId());// 得到分别打分的老师和人数
		if (pypsbs == null || pypsbs.size() < 1)
			return 0;
		for (Pypsb pypsb : pypsbs) {
			List<Pypsb> pypsbs2 = getOnePypsbOrderXId(pypsb.getGonghao(), student.getNumber());
			for (Pypsb pypsb2 : pypsbs2) {
				pycj += pypsb2.getFs();
			}
		}
		pycj = pycj / pypsbs.size();

		List<Dbpsb> dbpsbs = getStudentDbpsbs(student, dbx.get(0).getId());// 得到分别打分的老师和人数
		if (dbpsbs == null || dbpsbs.size() < 1)
			return 0;
		for (Dbpsb dbpsb : dbpsbs) {
			List<Dbpsb> dbpsbs2 = getOneDbpsbOrderXId(dbpsb.getGonghao(), student.getNumber());
			for (Dbpsb dbpsb2 : dbpsbs2) {
				dbcj += dbpsb2.getFs();
			}
		}
		dbcj = dbcj / dbpsbs.size();

		List<Zdlspsb> zdlspsbs = getTeacherZdlspsb(student.getTeacher(), student.getNumber());
		if (zdlspsbs == null || zdlspsbs.size() < 1)
			return 0;
		for (Zdlspsb zdlspsb : zdlspsbs) {
			zdcj += zdlspsb.getFs();
		}

		List<Sxcj> sxcjs = getSxcjpsb(student.getTeacher(), student.getNumber());
		if (sxcjs == null || sxcjs.size() > 1)
			return 0;
		for (Sxcj sxcj2 : sxcjs) {
			sxcj += sxcj2.getFs();
		}

		lwcj = (float) (zdcj * 0.3 + pycj * 0.3 + dbcj * 0.4);
		zf = (float) (sxcj * 0.4 + lwcj * 0.6);

		lwcj = FloatUtil.floatSaveTwoD(lwcj);
		zf = FloatUtil.floatSaveTwoD(zf);
		if (zf >= 60)
			student.setAnswer(4);
		else
			student.setAnswer(answer);// 没过的
		updateStudent(student);
		String hql1 = " FROM Zf z where z.xuehao = ? ";
		Object[] param1 = { student.getNumber() };
		Zf zfR = DaoFactory.getZfDaoInstance().get(hql1, param1);
		int dj = DengJi.getFsToDengJi(zf);

		if (zfR != null) {
			zfR.setZf(zf);
			zfR.setYear(student.getYear());
			zfR.setDj(dj);
			zfR.setYx(student.getYx());
			zfR.setZy(student.getZy());
			DaoFactory.getZfDaoInstance().update(zfR);
		} else {
			zfR = new Zf(student.getNumber(), student.getYear(), student.getYx(), student.getZy(), zf, dj, lwcj);
			DaoFactory.getZfDaoInstance().save(zfR);
		}
		if (zf >= 60)
			return 1;
		return 0;
	}

	@Override
	public JSONObject calcuStudentSecond(Teacher teacher) {

		List<Student> students = new ArrayList<Student>();
		String hql = " from Student s where s.zy = ? AND s.year = ? ";
		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		Object[] param = { teacher.getFzr(), year.getYear() };
		students = DaoFactory.getStudentDaoInstance().find(hql, param);
		int sum = 0, jige = 0;
		for (Student student : students) {
			if (student.getAnswer() != 3 && student.getAnswer() != 4) {
				jige += calculateOneStudent(student, 3);
				sum++;
			}
		}
		JSONObject json = new JSONObject();
		json.put("sum", sum);
		json.put("jg", jige);
		return json;
	}

	public List<Pypsb> getStudentPypsbs(Student student, int pyxId) {
		String hql = " from Pypsb p where p.xuehao = ? AND p.pypsbx = ? ";
		Object[] param = { student.getNumber(), pyxId };
		List<Pypsb> pypsbs = DaoFactory.getPypsbDaoInstance().find(hql, param);
		return pypsbs;
	}

	public List<Dbpsb> getStudentDbpsbs(Student student, int dbxId) {
		String hql = " from Dbpsb d where d.xuehao = ? AND d.dbpsbx = ? ";
		Object[] param = { student.getNumber(), dbxId };
		List<Dbpsb> dbpsbs = DaoFactory.getDbpsbDaoInstance().find(hql, param);
		return dbpsbs;
	}

	public Zdlspsb getStudentZdlspsb(Student student) {
		String hql = " from Zdlspsb z where z.xuehao = ? ORDER BY id ";
		Object[] param = { student.getNumber() };
		Zdlspsb zdlspsb = DaoFactory.getZdlspsbDaoInstance().get(hql, param);
		return zdlspsb;
	}

	public Sxcj getStudentSxcj(Student student) {
		String hql = " from Sxcj s where s.xuehao = ? ORDER BY id ";
		Object[] param = { student.getNumber() };
		Sxcj sxcj = DaoFactory.getSxcjDaoInstance().get(hql, param);
		return sxcj;
	}

	@Override
	public List<Yuanxi> getAllYx() {

		String hql = " from Yuanxi y where y.status = ? ";
		Object[] param = { 1 };
		List<Yuanxi> yx = DaoFactory.getYuanxiDaoInstance().find(hql, param);
		return yx;
	}

	@Override
	public List<Yuanxi> getNowAllYuanxi() {

		String hql = " from Yuanxi y where y.status = ? and y.year = ? order by y.id ";
		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		Object[] param = { 1, year.getYear() };
		List<Yuanxi> yx = DaoFactory.getYuanxiDaoInstance().find(hql, param);
		return yx;
	}

	@Override
	public List<Gzzj> getNowGzzj() {

		String hql = " from Gzzj g where g.year = ? and g.status = ? ORDER BY g.yx ";
		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		Object[] param = { year.getYear(), 1 };
		List<Gzzj> gzzjs = DaoFactory.getGzzjDaoInstance().find(hql, param);
		return gzzjs;
	}

	@Override
	public List<Zqxj> getNowZqxj() {

		String hql = " from Zqxj z where z.year = ? and z.status = ? ORDER BY z.yx ";
		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		Object[] param = { year.getYear(), 1 };
		List<Zqxj> gzzjs = DaoFactory.getZqxjDaoInstance().find(hql, param);
		return gzzjs;
	}

	@Override
	public List<Zhuanye> getZys(int yx) {

		String hql = " from Zhuanye z where z.yx = ? ";
		Object[] param = { yx };
		List<Zhuanye> zys = DaoFactory.getZhuanyeDaoInstance().find(hql, param);
		return zys;
	}

	@Override
	public List<Student> getZyStudents(int zy) {

		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String hql = " from Student s where s.zy = ? AND s.year = ? ";
		Object[] param = { zy, year.getYear() };
		List<Student> students = DaoFactory.getStudentDaoInstance().find(hql, param);
		return students;
	}

	@Override
	public List<Zhuanye> getAllZys() {

		String hql = " from Zhuanye z  ";
		List<Zhuanye> zys = DaoFactory.getZhuanyeDaoInstance().find(hql);
		return zys;
	}

	@Override
	public List<Student> searchStudentByNumberLike(String key) {

		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String hql = " from Student s where s.number LIKE ? AND s.year = ? ";
		Object[] param = { key, year.getYear() };
		List<Student> students = DaoFactory.getStudentDaoInstance().find(hql, param);
		return students;
	}

	@Override
	public Sxcj teacherGetSxPsb(Teacher teacher, String xuehao) {

		String hql = " from Sxcj z where z.gonghao = ? AND z.xuehao = ? ";
		Object[] param = { teacher.getNumber(), xuehao };
		Sxcj psb = DaoFactory.getSxcjDaoInstance().get(hql, param);
		return psb;
	}

	@Override
	public void addSxcj(List<Object> forms, Teacher teacher, Student student) {

		List<Sxcjx> sxcjxs = getSxcjx();
		for (int i = 0, l = sxcjxs.size(); i < l; i++) {
			Sxcj sxcj = new Sxcj(teacher.getNumber(), student.getNumber(), sxcjxs.get(i).getId(), 0, "");
			if (sxcjxs.get(i).getStatus() == 0) {
				sxcj.setFs((int) forms.get(i));
			} else {
				sxcj.setText((String) forms.get(i));
			}
			DaoFactory.getSxcjDaoInstance().save(sxcj);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Student> getZyStudentOrderByNumber(int zy) {

		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String hql = "select * from tb_student s where s.va_zyid = " + zy + " AND s.va_year = " + year.getYear()
				+ " ORDER BY s.va_number";
		Session session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createSQLQuery(hql).addEntity(Student.class);
		transaction.commit();
		List<Student> students = query.list();
		session.close();
		return students;
	}

	@Override
	public List<Zf> getZyStudentZfOrderByNumber(int zy) {

		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String hql = " from Zf z where z.zy = ? AND z.year = ? ORDER BY xuehao ";
		Object[] param = { zy, year.getYear() };
		List<Zf> zfs = DaoFactory.getZfDaoInstance().find(hql, param);
		return zfs;
	}

	@Override
	public List<Sxcjfs> getZyStudentSxcjOrderByNumber(List<Student> students) {

		List<Sxcjfs> sxcjfs = new ArrayList<Sxcjfs>();
		for (Student student : students) {
			Teacher zdls = getTeacherByAccount(student.getTeacher());
			if (checkSxcj(zdls, student.getNumber())) {
				List<Sxcj> sxcjs = getSxcjpsb(student.getTeacher(), student.getNumber());
				float fs = 0;
				for (Sxcj sxcj : sxcjs) {
					fs += sxcj.getFs();
				}
				sxcjfs.add(new Sxcjfs(student.getNumber(), FloatUtil.floatSaveTwoD(fs)));
			}
		}
		return sxcjfs;
	}

	@Override
	public Teacher getTeacherBYStudentYear(String account) {

		String hql = " from Teacher t where t.number = ? ";
		Object[] param = { account };
		Teacher teacher = DaoFactory.getTeacherDaoInstance().get(hql, param);
		return teacher;
	}

	@Override
	public void zyfzrUpdateSxcj(List<Object> forms, Teacher teacher, String xuehao) {

		List<Sxcjx> sxcjxs = getSxcjx();
		List<Sxcj> sxcjs = getSxcjpsb(teacher.getNumber(), xuehao);
		for (int i = 0, l = sxcjs.size(); i < l; i++) {
			if (sxcjxs.get(i).getStatus() == 0) {
				sxcjs.get(i).setFs((float) forms.get(i));
			} else
				sxcjs.get(i).setText((String) forms.get(i));
			DaoFactory.getSxcjDaoInstance().update(sxcjs.get(i));
		}
	}

	@Override
	public void addCjrz(Cjrz cjrz) {

		DaoFactory.getCjrzDaoInstance().save(cjrz);
	}

	@Override
	public List<Yxlw> getYxlwsOrderXuehaoInBd(Teacher teacher) {

		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String hql = " from Yxlw y where y.status = 1 and y.yx in ( select yx from Pybd p where p.gonghao = ? AND p.year = ? ) ORDER BY y.xuehao ";
		Object[] param = { teacher.getNumber(), year.getYear() };
		List<Yxlw> yxlws = DaoFactory.getYxlwDaoInstance().find(hql, param);
		return yxlws;
	}

	@Override
	public List<Yxlwz> getZyxlwsOrderXuehaoInBd(Teacher teacher) {

		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String hql = " from Yxlwz y where y.status = 1 and y.yx in ( select yx from Pybd p where p.gonghao = ? AND p.year = ? ) ORDER BY y.zuhao ";
		Object[] param = { teacher.getNumber(), year.getYear() };
		List<Yxlwz> zyxlws = DaoFactory.getYxlwzDaoInstance().find(hql, param);
		return zyxlws;
	}

	@Override
	public List<Yxlw> getYxSYxlwsORderXuehao(Teacher teacher) {

		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String hql = " from Yxlw y where y.yx = ? and y.year = ? ORDER BY y.xuehao ";
		Object[] param = { teacher.getYx(), year.getYear() };
		List<Yxlw> yxlws = DaoFactory.getYxlwDaoInstance().find(hql, param);
		return yxlws;
	}

	@Override
	public List<Yxlwz> getYxsYxlwzsOrderZuhao(Teacher teacher) {

		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String hql = " from Yxlwz y where y.yx = ? and y.year = ? ORDER BY y.zuhao ";
		Object[] param = { teacher.getYx(), year.getYear() };
		List<Yxlwz> yxlwzs = DaoFactory.getYxlwzDaoInstance().find(hql, param);
		return yxlwzs;
	}

	@Override
	public List<Pyrz> getTeacherPyrz(Teacher teacher) {

		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String hql = "select * from tb_pyrz p where p.va_gonghao = '" + teacher.getNumber()
				+ "' and p.va_gx = 0 and p.va_xy = 1 and p.va_year = " + year.getYear() + " order by p.va_xuehao ";
		Session session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createSQLQuery(hql).addEntity(Pyrz.class);
		transaction.commit();
		@SuppressWarnings("unchecked")
		List<Pyrz> rzs = query.list();
		session.close();
		return rzs;
	}

	@Override
	public List<Pyrz> getTeacherZpyrz(Teacher teacher) {

		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String hql = "select * from tb_pyrz p where p.va_gonghao = '" + teacher.getNumber()
				+ "' and p.va_gx = 1 and p.va_xy = 1 and p.va_year = " + year.getYear() + " order by p.va_zuhao ";
		Session session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createSQLQuery(hql).addEntity(Pyrz.class);
		transaction.commit();
		@SuppressWarnings("unchecked")
		List<Pyrz> rzs = query.list();
		session.close();
		return rzs;
	}

	@Override
	public List<Pyrz> getYxTeacherPyrzOrderbyXuehao(Teacher teacher) {

		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String hql = "select * from tb_pyrz p where p.va_gonghao = '" + teacher.getNumber()
				+ "' and p.va_gx = 0 and p.va_xy = 0 and p.va_year = " + year.getYear() + " order by p.va_xuehao ";
		Session session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createSQLQuery(hql).addEntity(Pyrz.class);
		transaction.commit();
		@SuppressWarnings("unchecked")
		List<Pyrz> rzs = query.list();
		session.close();
		return rzs;
	}

	@Override
	public List<Pyrz> getYxTeacherPyrzOrderZuhao(Teacher teacher) {

		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String hql = "select * from tb_pyrz p where p.va_gonghao = '" + teacher.getNumber()
				+ "' and p.va_gx = 1 and p.va_xy = 0 and p.va_year = " + year.getYear() + " order by p.va_zuhao ";
		Session session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createSQLQuery(hql).addEntity(Pyrz.class);
		transaction.commit();
		@SuppressWarnings("unchecked")
		List<Pyrz> rzs = query.list();
		session.close();
		return rzs;
	}

	@Override
	public Yxlw checkStudent(String xuehao) {

		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String hql = " from Yxlw y where y.status != 0 and y.xuehao = ? AND y.year = ? ";
		Object[] param = { xuehao, year.getYear() };
		Yxlw re = DaoFactory.getYxlwDaoInstance().get(hql, param);
		return re;
	}

	@Override
	public Yxlw checkYxStudent(String xuehao) {

		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String hql = " from Yxlw y where y.xuehao = ? AND y.year = ? ";
		Object[] param = { xuehao, year.getYear() };
		Yxlw re = DaoFactory.getYxlwDaoInstance().get(hql, param);
		return re;
	}

	@Override
	public Pyrz checkPyed(String xuehao, String gonghao) {

		String hql = " from Pyrz c where p.gonghao = ? and p.xy = 1 and p.gx = 0 and p.xuehao = ? ";
		Object[] param = { gonghao, xuehao };
		Pyrz rz = DaoFactory.getPyrzDaoInstance().get(hql, param);
		return rz;
	}

	@Override
	public Pyrz checkYxPred(String xuehao, String gonghao) {

		String hql = " from Pyrz c where p.gonghao = ? and p.xy = 0 and p.gx = 0 and p.xuehao = ? ";
		Object[] param = { gonghao, xuehao };
		Pyrz rz = DaoFactory.getPyrzDaoInstance().get(hql, param);
		return rz;
	}

	@Override
	public Pyrz getOneZuXiaoRz(int zuhao, String gonghao) {

		String hql = " from Pyrz c where p.gonghao = ? and p.xy = 1 and p.gx = 1 and p.zuhao = ? ";
		Object[] param = { gonghao, zuhao };
		Pyrz rz = DaoFactory.getPyrzDaoInstance().get(hql, param);
		return rz;
	}

	@Override
	public Pyrz getOnezuYxRz(int zuhao, String gonghao) {

		String hql = " from Pyrz c where p.gonghao = ? and p.xy = 0 and p.gx = 1 and p.zuhao = ? ";
		Object[] param = { gonghao, zuhao };
		Pyrz rz = DaoFactory.getPyrzDaoInstance().get(hql, param);
		return rz;
	}

	@Override
	public Cjrz checkCjed(String xuehao, String gonghao) {

		String hql = " from Cjrz c where c.xuehao = ? AND c.gonghao = ? ";
		Object[] param = { xuehao, gonghao };
		Cjrz rz = DaoFactory.getCjrzDaoInstance().get(hql, param);
		return rz;
	}

	@Override
	public void addXiaoGePyrz(Pyrz rz) {

		DaoFactory.getPyrzDaoInstance().save(rz);
		Yxlw yxlw = checkStudent(rz.getXuehao());
		String hql = " from Pyrz p where p.xy = 1 and p.gx = 0 and p.xuehao = ? and p.year = ? ";
		Object[] param = { rz.getXuehao(), rz.getYear() };
		List<Pyrz> rzs = DaoFactory.getPyrzDaoInstance().find(hql, param);
		float fs = 0;
		for (Pyrz rz1 : rzs) {
			fs += rz1.getFs();
		}
		fs = fs / rzs.size();
		fs = FloatUtil.floatSaveTwoD(fs);
		yxlw.setXfs(fs);
		DaoFactory.getYxlwDaoInstance().update(yxlw);
	}

	@Override
	public void addXiaoZuPyrz(Pyrz rz) {

		DaoFactory.getPyrzDaoInstance().save(rz);
		Yxlwz yxlwz = getXiaoYxlwzByZh(rz.getZuhao());
		String hql = " from Pyrz p where p.xy = 1 and p.gx = 1 and p.zuhao = ? and p.year= ? ";
		Object[] param = { rz.getZuhao(), rz.getYear() };
		List<Pyrz> rzs = DaoFactory.getPyrzDaoInstance().find(hql, param);
		float fs = 0;
		for (Pyrz rz1 : rzs) {
			fs += rz1.getFs();
		}
		fs = fs / rzs.size();
		fs = FloatUtil.floatSaveTwoD(fs);
		yxlwz.setXfs(fs);
		DaoFactory.getYxlwzDaoInstance().update(yxlwz);
	}

	@Override
	public void addYxGePyrz(Pyrz rz) {

		DaoFactory.getPyrzDaoInstance().save(rz);
		Yxlw yxlw = checkYxStudent(rz.getXuehao());
		String hql = " from Pyrz p where p.xy = 0 and p.gx = 0 and p.xuehao = ? and p.year = ? ";
		Object[] param = { rz.getXuehao(), rz.getYear() };
		List<Pyrz> rzs = DaoFactory.getPyrzDaoInstance().find(hql, param);
		float fs = 0;
		for (Pyrz rz1 : rzs) {
			fs += rz1.getFs();
		}
		fs = fs / rzs.size();
		fs = FloatUtil.floatSaveTwoD(fs);
		yxlw.setYfs(fs);
		DaoFactory.getYxlwDaoInstance().update(yxlw);
	}

	@Override
	public void addYxZuPyrz(Pyrz rz) {

		DaoFactory.getPyrzDaoInstance().save(rz);
		Yxlwz yxlwz = getYxYxlwzByZh(rz.getZuhao());
		String hql = " from Pyrz p where p.xy = 0 and p.gx = 1 and p.zuhao = ? and p.year = ? ";
		Object[] param = { rz.getZuhao(), rz.getYear() };
		List<Pyrz> rzs = DaoFactory.getPyrzDaoInstance().find(hql, param);
		float fs = 0;
		for (Pyrz rz1 : rzs) {
			fs += rz1.getFs();
		}
		fs = fs / rzs.size();
		fs = FloatUtil.floatSaveTwoD(fs);
		yxlwz.setYfs(fs);
		DaoFactory.getYxlwzDaoInstance().update(yxlwz);
	}

	@Override
	public Yuanxi getYuanxiByTeacher(Teacher teacher) {

		String hql = " FROM Yuanxi y where y.id = ? ";
		Object[] param = { teacher.getYx() };
		Yuanxi yuanxi = DaoFactory.getYuanxiDaoInstance().get(hql, param);
		return yuanxi;
	}

	@Override
	public List<Student> getYuanxiStudents(int yx) {

		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String hql = " FROM Student s where s.yx = ? AND s.year = ? ";
		Object[] param = { yx, year.getYear() };
		List<Student> students = DaoFactory.getStudentDaoInstance().find(hql, param);
		return students;
	}

	@Override
	public void updateSxcj(List<Object> forms, Student student) {

		List<Sxcjx> sxcjxs = getSxcjx();
		List<Sxcj> sxcjs = getSxcjpsb(student.getTeacher(), student.getNumber());
		for (int i = 0, l = sxcjs.size(); i < l; i++) {
			if (sxcjxs.get(i).getStatus() == 0) {
				sxcjs.get(i).setFs((float) forms.get(i));
			} else {
				sxcjs.get(i).setText((String) forms.get(i));
			}
			DaoFactory.getSxcjDaoInstance().update(sxcjs.get(i));
		}
	}

	@Override
	public void updateStudentZf(Student student) {

		calculateOneStudent(student, student.getAnswer());
	}

	public void updateStudent(Student student) {
		DaoFactory.getStudentDaoInstance().update(student);
	}

	@Override
	public void updateZdlspsb(List<Object> forms, Student student) {

		List<Zdlspsbx> zdlspsbxs = getZdlspsb();
		List<Zdlspsb> zdlspsbs = getTeacherZdlspsb(student.getTeacher(), student.getNumber());
		for (int i = 0, l = zdlspsbs.size(); i < l; i++) {
			if (zdlspsbxs.get(i).getStatus() == 0)
				zdlspsbs.get(i).setFs((float) forms.get(i));
			else
				zdlspsbs.get(i).setText((String) forms.get(i));
			DaoFactory.getZdlspsbDaoInstance().update(zdlspsbs.get(i));
		}
	}

	@Override
	public Dbpsb getPsb(int id) {

		Dbpsb dbpsb = DaoFactory.getDbpsbDaoInstance().get(Dbpsb.class, id);
		return dbpsb;
	}

	@Override
	public void updateDbpsb(List<Object> forms, Teacher dbls, Student student) {

		List<Dbpsbx> dbpsbxs = getDbpsbxOrderId();
		List<Dbpsb> dbpsbs = getOneDbpsbOrderXId(dbls.getNumber(), student.getNumber());
		for (int i = 0, l = dbpsbxs.size(); i < l; i++) {
			if (dbpsbxs.get(i).getStatus() == 0) {
				dbpsbs.get(i).setFs((float) forms.get(i));
			} else
				dbpsbs.get(i).setText((String) forms.get(i));
			DaoFactory.getDbpsbDaoInstance().update(dbpsbs.get(i));
		}
	}

	@Override
	public Pypsb getPyPsb(int id) {

		Pypsb pypsb = DaoFactory.getPypsbDaoInstance().get(Pypsb.class, id);
		return pypsb;
	}

	@Override
	public void updatePypsb(List<Object> forms, Teacher dbls, Student student) {

		List<Pypsbx> pypsbxs = getPypsbxOrderId();
		List<Pypsb> pypsbs = getOnePypsbOrderXId(dbls.getNumber(), student.getNumber());
		for (int i = 0, l = pypsbxs.size(); i < l; i++) {
			if (pypsbxs.get(i).getStatus() == 0) {
				pypsbs.get(i).setFs((float) forms.get(i));
			} else
				pypsbs.get(i).setText((String) forms.get(i));
			DaoFactory.getPypsbDaoInstance().update(pypsbs.get(i));
		}
	}

	@Override
	public List<Notice> getYxNotices(Teacher teacher) {

		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String hql = " from Notice n where n.year = ? AND n.type = ? AND n.yx = ? ORDER BY n.id DESC ";
		Object[] param = { year.getYear(), 2, teacher.getYx() };
		List<Notice> notices = DaoFactory.getNoticeDaoInstance().find(hql, param);
		return notices;
	}

	@Override
	public List<Notice> getZyNotices(Teacher teacher) {

		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		List<Zhuanye> zhuanyes = zdlsGetStudentAllZy(teacher);
		List<Notice> notices = new ArrayList<Notice>();
		String hql = " from Notice n where n.year = ? AND n.type = ? AND n.zy = ? ORDER BY n.id DESC ";
		for (int i = 0, l = zhuanyes.size(); i < l; i++) {
			Object[] param = { year.getYear(), 1, zhuanyes.get(i).getId() };
			notices.addAll(DaoFactory.getNoticeDaoInstance().find(hql, param));
		}
		return notices;
	}

	@Override
	public List<Notice> getZdNotices(Teacher teacher) {
		Year year = ServiceFactory.getYearServiceInstance().getNowYear();

		String hql = " from Notice n where n.year = ? AND n.type = ? AND n.gonghao = ? ORDER BY n.id DESC ";
		Object[] param = { year.getYear(), 0, teacher.getNumber() };
		List<Notice> notices = DaoFactory.getNoticeDaoInstance().find(hql, param);
		return notices;
	}

	@Override
	public void releaseNotice(Notice notice) {

		DaoFactory.getNoticeDaoInstance().save(notice);
	}

	@Override
	public void teacherUpdateDbpsb(List<Object> forms, Teacher teacher, String xuehao) {

		List<Dbpsbx> dbpsbxs = getDbpsbxOrderId();
		List<Dbpsb> dbpsb = getOneDbpsbOrderXId(teacher.getNumber(), xuehao);
		for (int i = 0, l = dbpsbxs.size(); i < l; i++) {
			if (dbpsbxs.get(i).getStatus() == 0) {
				dbpsb.get(i).setFs((float) forms.get(i));
			} else {
				dbpsb.get(i).setText((String) forms.get(i));
			}
			DaoFactory.getDbpsbDaoInstance().update(dbpsb.get(i));
		}
	}

	@Override
	public void teacherupdatePypsb(List<Object> forms, Teacher teacher, String xuehao) {

		List<Pypsbx> pypsbxs = getPypsbxOrderId();
		List<Pypsb> pypsbs = getOnePypsbOrderXId(teacher.getNumber(), xuehao);
		for (int i = 0, l = pypsbxs.size(); i < l; i++) {
			if (pypsbxs.get(i).getStatus() == 0) {
				pypsbs.get(i).setFs((float) forms.get(i));
			} else {
				pypsbs.get(i).setText((String) forms.get(i));
			}
			DaoFactory.getPypsbDaoInstance().update(pypsbs.get(i));
		}
	}

	@Override
	public Zf checkZfRecord(String xuehao) {

		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String hql = " from Zf z where z.xuehao = ? AND z.year = ? ";
		Object[] param = { xuehao, year.getYear() };
		Zf zfR = DaoFactory.getZfDaoInstance().get(hql, param);
		return zfR;
	}

	@Override
	public int teacherupdateZdlspsb(List<Object> forms, String gonghao, Student student) {// 验证改为：参加答辩看有无答辩组，改为否；看是否已添加进答辩组

		int result = 1;
		Zf zf = ServiceFactory.getTeacherServiceInstance().checkZfRecord(student.getNumber());
		if (zf != null) {
			return -3;
		}
		List<Zdlspsbx> zdlspsbxs = getZdlspsb();
		List<Zdlspsb> zdlspsbs = getTeacherZdlspsb(gonghao, student.getNumber());
		int isdb = (int) forms.get(zdlspsbxs.size());
		if (isdb != student.getAnswer()) {// answer为0是正常，isdb为1是正常
			for (int i = 0, l = zdlspsbxs.size(); i < l; i++) {
				if (zdlspsbxs.get(i).getStatus() == 0) {
					zdlspsbs.get(i).setFs((int) forms.get(i));
				} else {
					zdlspsbs.get(i).setText((String) forms.get(i));
				}
				DaoFactory.getZdlspsbDaoInstance().update(zdlspsbs.get(i));
			}
			return result;
		} else {// 是否参加答辩修改了，要验证可不可改
			Year year = ServiceFactory.getYearServiceInstance().getNowYear();
			if (isdb == 1) {// 想让他参加首次答辩的
				String hql = " from Dbjsz d where d.year = ? ";
				Object[] param = { year.getYear() };
				List<Dbjsz> dbjszs = DaoFactory.getDbjszDaoInstance().find(hql, param);
				if (dbjszs != null && dbjszs.size() > 0) {// 已经分配过了，不行哦！
					result = -2;
				} else {// 全校答辩组还未分配，那就正常
					student.setAnswer(0);// 那就回归正常
					updateStudent(student);
				}
				for (int i = 0, l = zdlspsbxs.size(); i < l; i++) {
					if (zdlspsbxs.get(i).getStatus() == 0) {
						zdlspsbs.get(i).setFs((int) forms.get(i));
					} else {
						zdlspsbs.get(i).setText((String) forms.get(i));
					}
					DaoFactory.getZdlspsbDaoInstance().update(zdlspsbs.get(i));
				}
			} else {// 不想让他参加首次答辩
				if (student.getDbz() == 0) {
					student.setAnswer(1);
					updateStudent(student);
				} else {
					result = -1;
				}
				for (int i = 0, l = zdlspsbxs.size(); i < l; i++) {
					if (zdlspsbxs.get(i).getStatus() == 0) {
						zdlspsbs.get(i).setFs((int) forms.get(i));
					} else {
						zdlspsbs.get(i).setText((String) forms.get(i));
					}
					DaoFactory.getZdlspsbDaoInstance().update(zdlspsbs.get(i));
				}
			}
			return result;
		}
	}

	@Override
	public int teacherUpdateSxcj(List<Object> forms, Teacher teacher, Student student) {

		Zf zf = checkZfRecord(student.getNumber());
		if (zf != null)
			return 0;
		List<Sxcjx> sxcjxs = getSxcjx();
		List<Sxcj> sxcj = getSxcjpsb(teacher.getNumber(), student.getNumber());
		for (int i = 0, l = sxcjxs.size(); i < l; i++) {
			if (sxcjxs.get(i).getStatus() == 0) {
				sxcj.get(i).setFs((int) forms.get(i));
			} else {
				sxcj.get(i).setText((String) forms.get(i));
			}
			DaoFactory.getSxcjDaoInstance().update(sxcj.get(i));
		}
		return 1;
	}

	@Override
	public void cjryUpdateCjrz(Cjrz cjrz) {

		DaoFactory.getCjrzDaoInstance().update(cjrz);
	}

	@Override
	public Rws teacherGetRwsById(int id) {

		Rws rws = DaoFactory.getRwsDaoInstance().get(Rws.class, id);
		return rws;
	}

	@Override
	public List<Jdxcgs> teacherStudentJdxcgss(String xuehao) {

		String hql = " from Jdxcgs j where j.xuehao = ? AND j.status = ? ";
		Object[] param = { xuehao, 1 };
		List<Jdxcgs> jdxcgs = DaoFactory.getJdxcgsDaoInstance().find(hql, param);
		return jdxcgs;
	}

	@Override
	public List<Lws> getStudentLws(String xuehao) {

		String hql = " from Lws l where l.xuehao = ? AND l.status = ? ";
		Object[] param = { xuehao, 1 };
		List<Lws> lws = DaoFactory.getLwsDaoInstance().find(hql, param);
		return lws;
	}

	@Override
	public int countStudentjdxcgWj(String xuehao) {

		String hql = " select count(*) from Jdxcgs j where j.xuehao = ? AND j.status = ? ";
		Object[] param = { xuehao, 1 };
		int count = DaoFactory.getJdxcgsDaoInstance().count(hql, param);
		return count + 1;
	}

	public int countStudentLwWj(String xuehao) {

		String hql = " select count(*) from Lws l where l.xuehao = ? AND l.status = ? ";
		Object[] param = { xuehao, 1 };
		int count = DaoFactory.getLwsDaoInstance().count(hql, param);
		return count + 1;
	}

	@Override
	public int addNotice(Notice notice) {

		DaoFactory.getNoticeDaoInstance().save(notice);
		// String hql = " from Notice n where n.gonghao = ? ";
		// Object[] param = { notice.getGonghao() };
		// List<Notice> notices = DaoFactory.getNoticeDaoInstance().find(hql, param);
		// return notices.get(notices.size() - 1).getId();
		return notice.getId();
	}

	@Override
	public int addNotices(Notices notices) {

		DaoFactory.getNoticesDaoInstance().save(notices);
		return notices.getId();
	}

	@Override
	public void updateNotices(Notices notices) {

		DaoFactory.getNoticesDaoInstance().update(notices);
	}

	@Override
	public void updateNotice(Notice notice) {

		DaoFactory.getNoticeDaoInstance().update(notice);
	}

	@Override
	public void updateKtInfo(Kt kt) {

		DaoFactory.getKtDaoInstance().update(kt);
	}

	@Override
	public List<Boolean> checkNewMessage(List<Student> students, Teacher teacher) {

		List<Boolean> res = new ArrayList<Boolean>();
		Object object;
		for (Student student : students) {
			String hql = " from Ssjl s where s.writer = ? and s.reader = ? and s.status = 1";
			Object[] param = { student.getNumber(), teacher.getNumber() };
			object = DaoFactory.getSsjlDaoInstance().get(hql, param);
			if (object == null)
				res.add(false);
			else
				res.add(true);
		}
		return res;
	}

	@Override
	public List<Ssjl> getSsjls(int page, String xuehao) {

		String hql = " select * from tb_ssjl s where s.va_writer = '" + xuehao + "' or s.va_reader = '" + xuehao
				+ "' order by id DESC limit " + (page - 1) * 10 + ",10 ";
		Session session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createSQLQuery(hql).addEntity(Ssjl.class);
		transaction.commit();
		@SuppressWarnings("unchecked")
		List<Ssjl> ssjls = query.list();
		session.close();
		for (Ssjl ssjl : ssjls) {// 设置为已读
			if (ssjl.getStatus() == 1) {
				ssjl.setStatus(0);
				DaoFactory.getSsjlDaoInstance().update(ssjl);
			}
		}
		return ssjls;
	}

	@Override
	public String addSsjl(String gonghao, String xuehao, String text) {

		Timestamp time = new Timestamp(System.currentTimeMillis());
		Ssjl ssjl = new Ssjl(gonghao, xuehao, text, time, 3);
		DaoFactory.getSsjlDaoInstance().save(ssjl);
		return time.toString();
	}

	@Override
	public List<Zdlspsbx> getZdlspsb() {

		String hql = " from Zdlspsbx ORDER BY id ";
		List<Zdlspsbx> zdlspsbxs = DaoFactory.getZdlspsbxDaoInstance().find(hql);
		if (zdlspsbxs == null)
			return new ArrayList<Zdlspsbx>();
		return zdlspsbxs;
	}

	@Override
	public List<Sxcjx> getSxcjx() {

		String hql = " from Sxcjx ORDER BY id ";
		List<Sxcjx> sxcjxs = DaoFactory.getSxcjxDaoInstance().find(hql);
		if (sxcjxs == null)
			return new ArrayList<Sxcjx>();
		return sxcjxs;
	}

	@Override
	public boolean checkZdlspsb(Teacher teacher, String xuehao) {

		List<Zdlspsbx> zdlspsbxs = getZdlspsb();
		List<Zdlspsb> zdlspsbs = getTeacherZdlspsb(teacher.getNumber(), xuehao);
		if (zdlspsbs != null && zdlspsbxs != null && zdlspsbs.size() == zdlspsbxs.size())
			return true;
		return false;
	}

	@Override
	public boolean checkSxcj(Teacher teacher, String xuehao) {

		List<Sxcjx> sxcjxs = getSxcjx();
		List<Sxcj> sxcjs = getSxcjpsb(teacher.getNumber(), xuehao);
		if (sxcjs != null && sxcjxs != null && sxcjs.size() == sxcjxs.size())
			return true;
		return false;
	}

	@Override
	public List<Zdlspsb> getTeacherZdlspsb(String gonghao, String xuehao) {

		String hql = " from Zdlspsb z where z.gonghao = ? and z.xuehao = ? ORDER BY zdlspsbx ";
		Object[] param = { gonghao, xuehao };
		List<Zdlspsb> zdlspsbs = DaoFactory.getZdlspsbDaoInstance().find(hql, param);
		return zdlspsbs;
	}

	@Override
	public List<Sxcj> getSxcjpsb(String gonghao, String xuehao) {

		String hql = " from Sxcj s where s.gonghao = ? and s.xuehao = ? ORDER BY sxcjx ";
		Object[] param = { gonghao, xuehao };
		List<Sxcj> sxcjs = DaoFactory.getSxcjDaoInstance().find(hql, param);
		return sxcjs;
	}

	@Override
	public List<Pypsbx> getPypsbxOrderId() {

		String hql = " from Pypsbx ORDER BY id ";
		List<Pypsbx> pypsbxs = DaoFactory.getPypsbxDaoInstance().find(hql);
		if (pypsbxs == null)
			return new ArrayList<Pypsbx>();
		return pypsbxs;
	}

	@Override
	public List<Dbpsbx> getDbpsbxOrderId() {

		String hql = " from Dbpsbx ORDER BY id ";
		List<Dbpsbx> dbpsbxs = DaoFactory.getDbpsbxDaoInstance().find(hql);
		if (dbpsbxs == null)
			return new ArrayList<Dbpsbx>();
		return dbpsbxs;
	}

	@Override
	public boolean checkPypsb(Teacher teacher, String xuehao) {

		List<Pypsbx> pypsbxs = getPypsbxOrderId();
		List<Pypsb> pypsbs = getOnePypsbOrderXId(teacher.getNumber(), xuehao);
		if (pypsbs != null && pypsbxs != null && pypsbs.size() == pypsbxs.size())
			return true;
		return false;
	}

	@Override
	public boolean checkDbpsb(Teacher teacher, String xuehao) {

		List<Dbpsbx> dbpsbxs = getDbpsbxOrderId();
		List<Dbpsb> dbpsbs = getOneDbpsbOrderXId(teacher.getNumber(), xuehao);
		if (dbpsbxs != null && dbpsbs != null && dbpsbs.size() == dbpsbxs.size())
			return true;
		return false;
	}

	@Override
	public List<Pypsb> getOnePypsbOrderXId(String gonghao, String xuehao) {

		String hql = " from Pypsb p where p.gonghao = ? and p.xuehao = ? ORDER BY pypsbx ";
		Object[] param = { gonghao, xuehao };
		List<Pypsb> pypsbs = DaoFactory.getPypsbDaoInstance().find(hql, param);
		return pypsbs;
	}

	@Override
	public List<Dbpsb> getOneDbpsbOrderXId(String gonghao, String xuehao) {

		String hql = " from Dbpsb d where d.gonghao = ? and d.xuehao = ? ORDER BY dbpsbx ";
		Object[] param = { gonghao, xuehao };
		List<Dbpsb> dbpsbs = DaoFactory.getDbpsbDaoInstance().find(hql, param);
		return dbpsbs;
	}

	@Override
	public Gzzj getYxGzzj(int yx) {

		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String hql = " from Gzzj g where g.yx = ? AND g.year = ? ";
		Object[] param = { yx, year.getYear() };
		Gzzj gzzj = DaoFactory.getGzzjDaoInstance().get(hql, param);
		return gzzj;
	}

	@Override
	public int yzUploadGzzjGetId(Teacher teacher) {

		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String hql = " from Gzzj g where g.yx = ? AND g.year = ? ";
		Object[] param = { teacher.getYear(), year.getYear() };
		Gzzj gzzj = DaoFactory.getGzzjDaoInstance().get(hql, param);
		if (gzzj == null) {
			gzzj = new Gzzj(teacher.getNumber(), teacher.getYx(), year.getYear(),
					new Timestamp(System.currentTimeMillis()), "", "", 0);
			DaoFactory.getGzzjDaoInstance().save(gzzj);
			return gzzj.getId();
		} else
			return gzzj.getId();
	}

	@Override
	public void updateGzzj(Gzzj gzzj) {

		DaoFactory.getGzzjDaoInstance().update(gzzj);
	}

	@Override
	public List<Cjbd> getOneTeacherCjBd(Teacher teacher) {

		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String hql = " from Cjbd c where c.gonghao = ? AND c.year = ? ";
		Object[] param = { teacher.getNumber(), year.getYear() };
		List<Cjbd> cjbds = DaoFactory.getCjbdDaoInstance().find(hql, param);
		return cjbds;
	}

	@Override
	public List<Student> getOneZyCjStudents(int zy) {

		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String hql = " from Student s where s.zy = ? and s.number in ( select xuehao from Cjk c where c.year = ? AND c.zy = ? ) ";
		Object[] param = { zy, year.getYear(), zy };
		List<Student> students = DaoFactory.getStudentDaoInstance().find(hql, param);
		return students;
	}

	@Override
	public Cjk getOneCjObject(String xuehao) {

		String hql = " from Cjk c where c.xuehao = ? AND c.year = ? ";
		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		Object[] param = { xuehao, year.getYear() };
		Cjk cjk = DaoFactory.getCjkDaoInstance().get(hql, param);
		return cjk;
	}

	@Override
	public Yxlwz getXiaoYxlwzByZh(int zuhao) {

		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String hql = " from Yxlwz y where y.zuhao = ? and y.status != 0 and y.year = ? ";
		Object[] param = { zuhao, year.getYear() };
		Yxlwz lw = DaoFactory.getYxlwzDaoInstance().get(hql, param);
		return lw;
	}

	@Override
	public Yxlwz getYxYxlwzByZh(int zuhao) {

		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String hql = " from Yxlwz y where y.zuhao = ? and y.year = ? ";
		Object[] param = { zuhao, year.getYear() };
		Yxlwz lw = DaoFactory.getYxlwzDaoInstance().get(hql, param);
		return lw;
	}

	@Override
	public List<Xsz> getOneGroups(int zuhao) {

		String hql = " from Xsz x where x.zh = ? ";
		Object[] param = { zuhao };
		List<Xsz> xszs = DaoFactory.getXszDaoInstance().find(hql, param);
		return xszs;
	}

	@Override
	public boolean isYxlw(Student student) {

		if (student.getIsgroup() == 0) {
			Yxlw yxlw = checkYxStudent(student.getNumber());
			if (yxlw != null)
				return true;
			return false;
		} else {
			int zuhao = ServiceFactory.getStudentServiceInstance().getStudentZuhao(student);
			if (zuhao == 0)
				return false;
			return true;
		}
	}

	@Override
	public void addGeYxlw(Yxlw yxlw) {

		DaoFactory.getYxlwDaoInstance().save(yxlw);
	}

	@Override
	public void addZuYxlw(Yxlwz yxlwz) {

		DaoFactory.getYxlwzDaoInstance().save(yxlwz);
	}

	@Override
	public List<Integer> getYxsZrs(List<Yuanxi> yxs) {

		List<Integer> zrs = new ArrayList<Integer>();
		for (Yuanxi yuanxi : yxs) {
			zrs.add(getYxZrs(yuanxi));
		}
		return zrs;
	}

	@Override
	public int getYxZrs(Yuanxi yuanxi) {

		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String hql = " select count(*) from Student s where s.yx = ? and s.year = ? ";
		Object[] param = { yuanxi.getId(), year.getYear() };
		int count = DaoFactory.getStudentDaoInstance().count(hql, param);
		return count;
	}

	@Override
	public List<Integer> getYxsJsRws(List<Yuanxi> yxs) {

		List<Integer> zrs = new ArrayList<Integer>();
		for (Yuanxi yuanxi : yxs) {
			zrs.add(getyxJsRws(yuanxi));
		}
		return zrs;
	}

	@Override
	public int getyxJsRws(Yuanxi yuanxi) {

		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String hql = " select count(*) from Student s where s.yx = ? and s.year = ? and s.stage >= 7 ";
		Object[] param = { yuanxi.getId(), year.getYear() };
		int count = DaoFactory.getStudentDaoInstance().count(hql, param);
		return count;
	}

	@Override
	public List<Integer> getYxsTjKtbg(List<Yuanxi> yxs) {

		List<Integer> zrs = new ArrayList<Integer>();
		for (Yuanxi yuanxi : yxs) {
			zrs.add(getYxTjKtbg(yuanxi));
		}
		return zrs;
	}

	@Override
	public int getYxTjKtbg(Yuanxi yuanxi) {

		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String hql = " select count(*) from Student s where s.yx = ? and s.year = ? and s.stage >= 8 ";
		Object[] param = { yuanxi.getId(), year.getYear() };
		int count = DaoFactory.getStudentDaoInstance().count(hql, param);
		return count;
	}

	@Override
	public List<Integer> getYxsTjLw(List<Yuanxi> yxs) {

		List<Integer> zrs = new ArrayList<Integer>();
		for (Yuanxi yuanxi : yxs) {
			zrs.add(getYxTjLw(yuanxi));
		}
		return zrs;
	}

	@Override
	public int getYxTjLw(Yuanxi yuanxi) {

		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String hql = " select count(*) from Lw l where l.yx = ? and l.year = ? and l.status = 1 ";
		Object[] param = { yuanxi.getId(), year.getYear() };
		int count = DaoFactory.getLwDaoInstance().count(hql, param);
		return count;
	}

	@Override
	public List<Integer> getYxsDb(List<Yuanxi> yxs) {

		List<Integer> zrs = new ArrayList<Integer>();
		for (Yuanxi yuanxi : yxs) {
			zrs.add(getYxDb(yuanxi));
		}
		return zrs;
	}

	@Override
	public int getYxDb(Yuanxi yuanxi) {

		List<Student> students = getYuanxiStudents(yuanxi.getId());
		int count = 0;
		List<Dbpsbx> dbpsbxs = getDbpsbxOrderId();
		if (dbpsbxs == null || dbpsbxs.size() <= 0) {
			return 0;
		}
		for (Student student : students) {
			List<Dbpsb> dbpsbs = getStudentDbpsbs(student, dbpsbxs.get(0).getId());

			if (dbpsbs != null && dbpsbs.size() > 0)
				count++;
		}
		return count;
	}

	@Override
	public List<Integer> getYxsXt(List<Yuanxi> yxs) {

		List<Integer> zrs = new ArrayList<Integer>();
		for (Yuanxi yuanxi : yxs) {
			zrs.add(getYxXt(yuanxi));
		}
		return zrs;
	}

	@Override
	public int getYxXt(Yuanxi yuanxi) {

		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String hql = " select count(*) from Student s where s.yx = ? and s.year = ? and s.stage >= 6 ";
		Object[] param = { yuanxi.getId(), year.getYear() };
		int count = DaoFactory.getStudentDaoInstance().count(hql, param);
		return count;
	}

	@Override
	public List<Integer> getOneYxCj(int yx) {

		List<Integer> cj = new ArrayList<Integer>();
		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String hql = " from Zf z where z.year = ? and z.yx = ? and z.dj = ? ";
		int bjg = DaoFactory.getZfDaoInstance().count(hql, new Object[] { year.getYear(), yx, 0 });
		cj.add(bjg);
		int jg = DaoFactory.getZfDaoInstance().count(hql, new Object[] { year.getYear(), yx, 1 });
		cj.add(jg);
		int zd = DaoFactory.getZfDaoInstance().count(hql, new Object[] { year.getYear(), yx, 2 });
		cj.add(zd);
		int lh = DaoFactory.getZfDaoInstance().count(hql, new Object[] { year.getYear(), yx, 3 });
		cj.add(lh);
		int yxc = DaoFactory.getZfDaoInstance().count(hql, new Object[] { year.getYear(), yx, 4 });
		cj.add(yxc);
		return cj;
	}

	@Override
	public Gzzj getOneYxGzzj(int yx) {

		String hql = " from Gzzj g where g.yx = ? and g.year = ? and g.status = 1 ";
		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		Object[] param = { yx, year.getYear() };
		Gzzj gzzj = DaoFactory.getGzzjDaoInstance().get(hql, param);
		return gzzj;
	}

	@Override
	public Zqxj getOneYxZqxj(int yx) {

		String hql = " from Zqxj z where z.yx = ? and z.year = ? and z.status = 1 ";
		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		Object[] param = { yx, year.getYear() };
		Zqxj gzzj = DaoFactory.getZqxjDaoInstance().get(hql, param);
		return gzzj;
	}

	@Override
	public List<Yxlw> getAllYxlwOrderFs() {

		String hql = " from Yxlw y where y.status != 0 and y.year = ? order by y.xfs DESC ";
		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		Object[] param = { year.getYear() };
		List<Yxlw> yxlws = DaoFactory.getYxlwDaoInstance().find(hql, param);
		return yxlws;
	}

	@Override
	public List<Yxlwz> getAllYxlwzOrderFs() {

		String hql = " from Yxlwz y where y.status != 0 and y.year = ? order by y.xfs DESC ";
		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		Object[] param = { year.getYear() };
		List<Yxlwz> yxlwzs = DaoFactory.getYxlwzDaoInstance().find(hql, param);
		return yxlwzs;
	}

	@Override
	public int count() {
		return teacherDao.count();
	}

	@Override
	public List<Teacher> pageSearch(int pageNum, int rowCount) {
		return teacherDao.findInPage(pageNum, rowCount);
	}

	@Override
	public List<Pybd> getOneTeacherPyBd(Teacher teacher) {
		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String hql = " from Pybd p where p.gonghao = ? AND p.year = ? ";
		Object[] param = { teacher.getNumber(), year.getYear() };
		List<Pybd> pybds = DaoFactory.getPybdDaoInstance().find(hql, param);
		return pybds;
	}

	@Override
	public List<Teacher> findTeacherByYuanxi(int YuanxiID) {
		return teacherDao.findTeacherByYuanxi(YuanxiID);
	}

	@Override
	public Teacher findTeacherByNuber(String number) {
		return teacherDao.findTeacherByNum(number);
	}

	@Override
	public List<Teacher> findTeacherByJsId(int jsId) {

		String sql = "SELECT * FROM tb_teacher AS tec,tb_jsgx AS jsgx WHERE tec.va_number=jsgx.va_number AND jsgx.va_jsid="
				+ jsId + " ORDER BY tec.va_yxid";
		Session session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createSQLQuery(sql).addEntity(Teacher.class);
		transaction.commit();
		@SuppressWarnings("unchecked")
		List<Teacher> teachers = query.list();
		session.close();
		return teachers;
	}

	@Override
	public List<Teacher> findUnGroupedTeacherByYuanxiId(int yuanxiId) {

		String hql = "SELECT * FROM tb_teacher WHERE va_number NOT IN (SELECT va_jsgh FROM tb_dbjsz) AND va_yxid = "
				+ yuanxiId + " ";
		Session session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createSQLQuery(hql).addEntity(Teacher.class);
		transaction.commit();
		@SuppressWarnings("unchecked")
		List<Teacher> teachers = query.list();
		session.close();

		return teachers;
	}

	@Override
	public boolean isGroupedByNumber(String number) {
		boolean result = true;
		String hql = "SELECT * FROM tb_teacher WHERE va_number IN (SELECT va_jsgh FROM tb_dbjsz) AND va_number = "
				+ number + " ";
		Session session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createSQLQuery(hql).addEntity(Teacher.class);
		transaction.commit();
		Teacher teacher = (Teacher) query.uniqueResult();
		session.close();
		if (teacher == null) {
			result = false;
		}
		return result;
	}

	@Override
	public void updateTeacher(Teacher teacher) {
		teacherDao.updateTeacher(teacher);

	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Teacher> pageFindTeacherByTeacherAndJsgx(int yuanxiId, int jsId, int nowPage) {
		String sql = "SELECT * FROM  tb_teacher AS tec WHERE tec.va_yxid = " + yuanxiId
				+ " AND  tec.va_number NOT IN (SELECT tb_jsgx.va_number FROM  tb_jsgx  WHERE va_jsid = " + jsId
				+ " ) LIMIT " + (nowPage - 1) * 10 + " ,10";
		Session session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createSQLQuery(sql).addEntity(Teacher.class);
		transaction.commit();
		List<Teacher> teachers = new ArrayList<>();
		teachers = query.list();
		session.close();
		return teachers;

	}

	@Override
	public int CountTeacherByTeacherAndJsgx(int yuanxiId, int jsId) {
		int result = 0;
		String sql = "SELECT COUNT(*) FROM  tb_teacher AS tec WHERE tec.va_yxid = " + yuanxiId
				+ " AND  tec.va_number NOT IN (SELECT tb_jsgx.va_number FROM  tb_jsgx  WHERE va_jsid = " + jsId + " )";
		Session session = IuimSesssionFactory.getSessionFactory().openSession();
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
	public int CountPageFindTeacherByTeacherAndJsgx(int yuanxiId, int jsId) {
		int result = 0;
		String sql = "SELECT COUNT(*) FROM  tb_teacher AS tec WHERE tec.va_yxid = " + yuanxiId
				+ " AND  tec.va_number NOT IN (SELECT tb_jsgx.va_number FROM  tb_jsgx  WHERE va_jsid = " + jsId + " ) ";
		Session session = IuimSesssionFactory.getSessionFactory().openSession();
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
	public List<Teacher> findTeacherByYuanxiAndJs(int yuanxiId, int jsId, int nowPage) {
		String sql = "SELECT * FROM  tb_teacher AS tec WHERE tec.va_yxid = " + yuanxiId
				+ " AND  tec.va_number  IN (SELECT tb_jsgx.va_number FROM  tb_jsgx  WHERE va_jsid = " + jsId
				+ " ) LIMIT " + (nowPage - 1) * 10 + " ,10";
		Session session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createSQLQuery(sql).addEntity(Teacher.class);
		transaction.commit();
		List<Teacher> teachers = new ArrayList<>();
		teachers = query.list();
		session.close();
		return teachers;
	}

	@Override
	public int CountfindTeacherByYuanxiAndJs(int yuanxiId, int jsId) {
		int result = 0;
		String sql = "SELECT COUNT(*) FROM  tb_teacher AS tec WHERE tec.va_yxid = " + yuanxiId
				+ " AND  tec.va_number  IN (SELECT tb_jsgx.va_number FROM  tb_jsgx  WHERE va_jsid = " + jsId + " ) ";
		Session session = IuimSesssionFactory.getSessionFactory().openSession();
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
	public List<Teacher> findTeacherByYuanxiAndJs(int yuanxiId, int jsId) {
		String sql = "SELECT * FROM tb_teacher AS tec,tb_jsgx AS jsgx  WHERE tec.va_number=jsgx.va_number AND jsgx.va_jsid="
				+ jsId + " AND  tec.va_yxid =" + yuanxiId + " ORDER BY tec.va_yxid";
		Session session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createSQLQuery(sql).addEntity(Teacher.class);
		transaction.commit();
		List<Teacher> teachers = new ArrayList<>();
		teachers = query.list();
		session.close();
		return teachers;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Teacher> pageFindTeacherByYuanxi(int yuanxiId, int nowPage) {
		String sql = "SELECT * FROM tb_teacher AS tec WHERE tec.va_yxid =" + yuanxiId
				+ "  ORDER BY tec.va_number LIMIT " + (nowPage - 1) * 10 + ",10";
		Session session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createSQLQuery(sql).addEntity(Teacher.class);
		transaction.commit();
		List<Teacher> teachers = new ArrayList<>();
		teachers = query.list();
		session.close();
		return teachers;
	}

	@Override
	public int CountTeacherByYuanxi(int yuanxiId) {
		int result = 0;
		String sql = "SELECT COUNT(*) FROM tb_teacher AS tec WHERE tec.va_yxid =" + yuanxiId
				+ "  ORDER BY tec.va_number ";
		Session session = IuimSesssionFactory.getSessionFactory().openSession();
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
	public boolean checkZdlspsb() {
		List<Zdlspsbx> pList = getZdlspsb();
		if (pList == null || pList.size() < 1)
			return false;
		return true;
	}

	@Override
	public boolean checkSxcjX() {
		List<Sxcjx> sxcjxs = getSxcjx();
		if (sxcjxs == null || sxcjxs.size() < 1)
			return false;
		return true;
	}

	@Override
	public boolean checkDbPyPsb() {
		List<Dbpsbx> dbpsbxs = getDbpsbxOrderId();
		List<Pypsbx> pypsbxs = getPypsbxOrderId();
		if (dbpsbxs == null || dbpsbxs.size() < 1 || pypsbxs == null || pypsbxs.size() < 1)
			return false;
		return true;
	}

	@Override
	public int countStudentOfTeacher(String number) {
		int result = 0;
		String sql = "SELECT COUNT(*) FROM tb_student AS stu WHERE stu.va_teacher = " + number + " ";
		Session session = IuimSesssionFactory.getSessionFactory().openSession();
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
	public boolean newMessage(Teacher teacher) {
		// TODO Auto-generated method stub
		List<Student> students = getTeachersStudentOrderXuehao(teacher);
		Ssjl object;
		for (Student student : students) {
			String hql = " from Ssjl s where s.writer = ? and s.reader = ? and s.status = 1";
			Object[] param = { student.getNumber(), teacher.getNumber() };
			object = DaoFactory.getSsjlDaoInstance().get(hql, param);
			if (object != null)
				return true;
		}
		return false;
	}

	@Override
	public List<Zf> getZyZfs(int yx) {
		// TODO Auto-generated method stub
		String hql = " from Zf z where z.zy = ? ";
		Object[] param = { yx };
		List<Zf> zfs = DaoFactory.getZfDaoInstance().find(hql, param);
		return zfs;
	}

}
