package factory;

import service.AccountService;
import service.StudentService;
import service.TeacherService;
import service.WorkControlService;
import service.YearService;
import service.impl.common.AccountServiceImpl;
import service.impl.common.YearServiceImpl;
import service.impl.student.StudentServiceImpl;
import service.impl.teacher.TeacherServiceImpl;
import service.impl.workControl.WorkControlServiceImpl;

public class ServiceFactory {
	public static AccountService getAccountServiceInstance(){
		return new AccountServiceImpl();
	}
	
	public static StudentService getStudentServiceInstance(){
		return new StudentServiceImpl();
	}
	
	public static TeacherService getTeacherServiceInstance() {
		return new TeacherServiceImpl();
	}
	
	public static YearService getYearServiceInstance(){
		return new YearServiceImpl();
	}
	public static WorkControlService getWorkcontrolInstance(){
		return new WorkControlServiceImpl();
	}
}
