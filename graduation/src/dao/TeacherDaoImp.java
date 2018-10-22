package dao;

import java.util.List;

import domain.Teacher;

public class TeacherDaoImp extends BaseDAO<Teacher> implements TeacherDao {

	@Override
	public void addTeacher(Teacher teacher) {
		// TODO Auto-generated method stub
		super.save(teacher);

	}

	@Override
	public void deleteTeacher(Teacher teacher) {
		// TODO Auto-generated method stub
		super.delete(teacher);

	}

	@Override
	public void updateTeacher(Teacher teacher) {
		// TODO Auto-generated method stub
        super.update(teacher);
	}

	@Override
	public List<Teacher> findTeacherByYuanxi(int YuanxiID) {
		// TODO Auto-generated method stub
		String hql="from Teacher where yx=? ";
		Object[] param=new Object[]{YuanxiID};
		return super.find(hql, param);
	}

	@Override
	public Teacher findTeacherByNum(String num) {
		String hql="from Teacher where number=? ";
		Object[] param=new Object[]{num};
		return super.get(hql, param);
	}

	@Override
	public Teacher findTeahcerById(int id) {
		String hql="from Teacher where id=? ";
		Object[] param=new Object[]{id};
		return super.get(hql, param);
	}

	@Override
	public int count() {
		String hql="select count(*) from Teacher";
		return super.count(hql);
	}

	@Override
	public List<Teacher> findInPage(int pageNum, int rowCount) {
		int total=count();
		String hql="from Teacher";
		return super.findInPage(hql,total, pageNum, rowCount);
	}

	@Override
	public List<Teacher> findInPageByParam(int pageNum,int rowCount,int yuanxiId) {
		int total=count();
		String hql="from Teacher where yxid = ?";
		Object [] param=new  Object[]{yuanxiId};
		return super.findInPageByParam(hql, total, pageNum, rowCount, param);
	}

	@Override
	public List<Teacher> vagueSearchByName(String name) {
		// SELECT * FROM tb_teacher AS teacher WHERE teacher.va_name LIKE '%管理%'
		String hql="from Teacher where name like '%"+name+"%' ";
	    //	Object [] param={name};
		//return super.find(hql, param);
		return super.find(hql);
	}

}
