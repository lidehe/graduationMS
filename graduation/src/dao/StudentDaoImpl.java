package dao;

import java.util.List;

import domain.Student;

public class StudentDaoImpl extends BaseDAO<Student> implements StudentDao {

	@Override
	public void add(Student student) {
        super.save(student);
	}

	@Override
	public void Delete(Student student) {
       super.delete(student);
	}

	@Override
	public void update(Student student) {
      super.update(student);
	}

	@Override
	public List<Student> findByYuanxi(int yuanxiID) {
		String hql="from Student where yx=? ";
		Object[] param=new Object[]{yuanxiID};
		return super.find(hql, param);
	}

	@Override
	public List<Student> findByClass(String bj) {
		String hql="from Student where bj=? ";
		Object[] param=new Object[]{bj};
		return super.find(hql, param);
	}

	@Override
	public List<Student> findByZY(int zy) {
		String hql="from Student where zy=? ";
		Object[] param=new Object[]{zy};
		return super.find(hql, param);
	}

	@Override
	public Student findById(int id) {
		String hql="from Student where id=? ";
		Object[] param=new Object[]{id};
		return super.get(hql, param);
	}

	@Override
	public Student findByNumber(String number) {
		String hql="from Student where number=? ";
		Object[] param=new Object[]{number};
		return super.get(hql, param);
	}

	@Override
	public List<Student> findAll() {
		String hql="from Student";
		return super.find(hql);
	}

	@Override
	public int count() {
		String hql="select count(*) from Student";
		return super.count(hql);
	}

	@Override
	public List<Student> findInPage(int pageNum, int rowCount) {
		int total=count();
		String hql="from Student";
		return super.findInPage(hql,total, pageNum, rowCount);
	}

	@Override
	public List<Student> findByTeacherNumber(String teacherNumber) {
		String hql="from Student where teacher = ? ";
		Object[] param=new Object[]{teacherNumber};
		return super.find(hql, param);
	}

	@Override
	public List<Student> vagueSearchByName(String name) {
		String hql="from Student where name like '%"+name+"%' ";
	    //	Object [] param={name};
		//return super.find(hql, param);
		return super.find(hql);
	}

}
