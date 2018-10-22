package account;

import static org.junit.Assert.*;

import org.junit.Test;

import domain.Year;
import factory.ServiceFactory;
import net.sf.json.JSONObject;

public class LoginTest {

	/*@Test
	public void test() {
		int json = ServiceFactory.getAccountServiceInstance().login("admin", "456");
		System.out.println(json);
	}*/
	
//	@Test
//	public void test1(){
//		ServiceFactory.getStudentServiceInstance().getStudentByAccount("1302443112");
//	}
	
	@Test
	public void test(){
		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		System.out.println(year.getYear());
	}

}
