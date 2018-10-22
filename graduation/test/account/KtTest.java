package account;

import java.util.List;

import org.junit.Test;

import domain.Kt;
import domain.Student;
import domain.Teacher;
import domain.Year;
import factory.ServiceFactory;

public class KtTest {

	@Test
	public void test() {
		//Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		//System.out.println(year.getYear());
		Student student = ServiceFactory.getStudentServiceInstance().getStudentByAccount("1302443112");
		List<Kt> kts = ServiceFactory.getStudentServiceInstance().getTeacherKt(student);
	}

}
