package account;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import domain.Student;
import factory.ServiceFactory;

public class LH {

	@Test
	public void test() {
		List<Student> students = ServiceFactory.getStudentServiceInstance().getGroupStudents("1302443112");
		System.out.println(students.get(0).getKt());
	}
	
//	@Test
//	public void test1(){
//		Student student = new Student();
//		int a= 1,b = 2;
//		if(a > b){
//			System.out.println(student.getName());
//		}else
//			System.out.println("ok");
//	}

	
//	@Test
//	public void test2() {
//		System.out.println("开始");
//		Student student = ServiceFactory.getStudentServiceInstance().getStudentByAccount("1302443112");
//		//System.out.println(student.getIsgroup());
//		if (student.getIsgroup() == 1) {
//			System.out.println("ok");
//		} else {
//			System.out.println(1);
//		}
//		System.out.println(3);
//	}
}
