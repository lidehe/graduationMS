package zdls;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Test;

import domain.Kt;
import domain.Ktbg;
import domain.Lw;
import domain.Student;
import domain.Sxcjx;
import domain.Teacher;
import domain.Zdlspsbx;
import factory.DaoFactory;
import factory.ServiceFactory;
import service.StudentService;
import utils.FloatUtil;

public class Zhuanye {

//	@Test
//	public void test() {
//		Teacher teacher = ServiceFactory.getTeacherServiceInstance().getTeacherByAccount("B14041525");
//		List<domain.Zhuanye> zy = ServiceFactory.getTeacherServiceInstance().zdlsGetStudentAllZy(teacher);
//		System.out.println(zy.get(0).getName());
//		//fail("Not yet implemented");
//	}

	
//	@Test
//	public void test1(){
//		Teacher teacher = ServiceFactory.getTeacherServiceInstance().getTeacherByAccount("B14041525");
//		List<Student> students = ServiceFactory.getTeacherServiceInstance().getTeacherStudentsEsc(teacher);
//	}
	
//	@Test
//	public void test2(){
//		Kt kt = ServiceFactory.getStudentServiceInstance().getStudentKt(3);
//	}
	
//	@Test
//	public void test3(){
//		int i = 0;
//		for(;i < 3&&i++ < 3;){
//			
//		}
//		System.out.println(i);
//	}
	
//	@Test
//	public void test4(){
//		Teacher teacher = ServiceFactory.getTeacherServiceInstance().getTeacherByAccount("B14041525");
//		List<Student> students = ServiceFactory.getTeacherServiceInstance().getTeachersStudentOrderXuehao(teacher);
//		List<Ktbg> ktbgs = ServiceFactory.getTeacherServiceInstance().getTeachersStudentKtbgOrderXuehao(teacher);
//		System.out.println(students.get(0).getNumber() + "..." + ktbgs.get(0).getXuehao() + "..." + ktbgs.get(0).getStatus());
//	}
	
//	@Test
//	public void test5(){
//		Student student = new Student();
//		student.setNumber("123156");
//		student.setName("4564");
//		DaoFactory.getStudentDaoInstance().save(student);
//		student = ServiceFactory.getStudentServiceInstance().getStudentByAccount("123156");
//		System.out.println(student.getType());
//	}
	
//	@Test
//	public void test6(){
//		Timestamp time1 = new Timestamp(System.currentTimeMillis());
//		SimpleDateFormat sy1 = new SimpleDateFormat("yyyy");
//		SimpleDateFormat sm1 = new SimpleDateFormat("MM");
//		SimpleDateFormat sd1 = new SimpleDateFormat("dd");
//		System.out.println(sd1.format(time1));
//	}
	
	
//	@Test
//	public void test7(){
//		Student student = ServiceFactory.getStudentServiceInstance().getStudentByAccount("1302443113");
//		Lw lw = ServiceFactory.getTeacherServiceInstance().getStudentLw(student);
//		System.out.println(student.getBs());
//		System.out.println(lw == null);
//		if (lw == null || lw.getStatus() == 0)
//			System.out.println(1);// 学生论文未提交，不能评
//		else
//			System.out.println(0);
//	}
	
	@Test
	public void test11() {
		//System.out.println(FloatUtil.floatSaveTwoD(19.888));
		StudentService service=ServiceFactory.getStudentServiceInstance();
		Student student=null;
		student=service.getStudentByAccount("312");
		service.addjl(student, "测试股添加阶段交流");
	}
}
