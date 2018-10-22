package utils;

import domain.Byfs;
import domain.Student;
import factory.ServiceFactory;

public class SetStudentStage {
	
	public static int setEr(Student student){
		Byfs byfs = new Byfs();
		byfs = ServiceFactory.getStudentServiceInstance().getStudentByfs(student.getType());
		if(byfs.getIsPass1() == 0){
			if(byfs.getIsPass2() == 0)
				if(byfs.getIsPass3() == 0)
					return 14;
				else
					return 11;
			else
				return 8;
		}else
			return 7;
	}
	
	public static int setSan(Student student){
		Byfs byfs = new Byfs();
		byfs = ServiceFactory.getStudentServiceInstance().getStudentByfs(student.getType());
		if(byfs.getIsPass2() == 0){
			if(byfs.getIsPass3() == 0)
				return 14;
			else
				return 11;
		}else
			return 8;
	}
	
	public static int setSi(Student student){
		Byfs byfs = new Byfs();
		byfs = ServiceFactory.getStudentServiceInstance().getStudentByfs(student.getType());
		if(byfs.getIsPass3() == 0)
			return 14;
		else
			return 11;
	}
	
}
