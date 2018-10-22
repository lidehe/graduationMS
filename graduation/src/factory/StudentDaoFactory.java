package factory;

import dao.StudentDaoImpl;

public class StudentDaoFactory {

	public static StudentDaoImpl getStudentDao(){
		return new StudentDaoImpl();
	}
}
