package account;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import factory.ServiceFactory;

public class Byfs {

	@Test
	public void test() {
		try {
			String string=null;
			System.out.println(string.equals("hello"));
			
		} catch (Exception e) {
			System.out.println("1");
			e.printStackTrace();
			System.out.println("2");
		}
	}

}
