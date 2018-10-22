package account;

import static org.junit.Assert.*;

import org.junit.Test;

import dao.TeacherDao;
import dao.TeacherDaoImp;
import domain.Teacher;
import factory.TeacherDaoFactory;

public class TeacherTest {

	@Test
	public void test() {
		Teacher teacher=new Teacher();
		teacher.setEmail("adsf");
		teacher.setFzr(1);
		teacher.setIdentityN("132");
		teacher.setName("ad");
		teacher.setNumber("13");
		teacher.setSex(3);
		teacher.setTel("34");
		teacher.setYear(2018);
		teacher.setYx(7);
		Teacher teacher2=new Teacher();
		teacher2=teacher;
		System.out.println(teacher2);
		TeacherDao teacherDao=TeacherDaoFactory.getTeacherDaoInstance();
		teacherDao.addTeacher(teacher);
	}

}
