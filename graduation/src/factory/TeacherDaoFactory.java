package factory;

import dao.TeacherDaoImp;

public class TeacherDaoFactory {

	public static TeacherDaoImp getTeacherDaoInstance(){
		return new TeacherDaoImp();
	}
}
