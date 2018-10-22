package zdls;

import java.sql.Timestamp;
import java.util.List;

import org.junit.Test;

import domain.Ktbg;
import domain.Sxcjx;
import domain.Teacher;
import domain.Yuanxi;
import domain.Yxlw;
import domain.Zdlspsbx;
import factory.ServiceFactory;

public class JavaTest {
//	public static void main(String[] args) {
//		int i = 1 == 2 ? 3: 4;
//		System.out.println(i);
//	}
	
	@Test
	public void test3() {
		Yuanxi yuanxi = ServiceFactory.getTeacherServiceInstance().teacherGetYx(1);
		int zrs = ServiceFactory.getTeacherServiceInstance().getYxZrs(yuanxi);
		System.out.println(zrs);
	}
	
}
