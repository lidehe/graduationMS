package jwc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.junit.Test;

import dao.StudentDao;
import dao.StudentDaoImpl;
import dao.TeacherDao;
import dao.TeacherDaoImp;
import domain.Byfs;
import domain.Student;
import domain.Teacher;
import factory.JWCFactory;
import service.JWCService;

public class JwcTest {
	private JWCService jwcservice = JWCFactory.getJwcService();

	@SuppressWarnings({ "deprecation", "static-access" })
	@Test
	public void testExportExcel() {
		/*StudentDao studentDao=new StudentDaoImpl();
        TeacherDao teacherDao=new TeacherDaoImp();
        List<Teacher> teachers=new ArrayList<>();
        teachers=teacherDao.vagueSearchByName("*");
        List<Student> students=new ArrayList<>();
        for(Student student:students){
        	System.out.println(student.getName());
        }*/
		List<Student> students=new ArrayList<>();
		System.out.println(students.isEmpty());
		
		
//		String text="c王c";
//		String regex="[\u4e00-\u9fa5]";//这种方式仅支持一个一个匹配
//		char c[] = text.toCharArray();
//		int count=0;
//		for(int i=0;i<c.length;i++){
//			if (String.valueOf(c[i]).matches(regex)) {
//				break;
//			}
//			count++;
//		}
//		if (count==c.length) {
//			System.out.println("字母数字混合");
//		}else{
//			System.out.println("汉字");
//		}
//		List<Integer> list = new ArrayList<>();
//		for (int i = 0; i < 8; i++) {
//			list.add(i );
//		}
//		list.forEach(s->{
//			System.out.println("这是来自lambda表达式内部的输出信息");
//			if (s>4) {
//				System.out.println("大于4的数字"+s);
//			}else{
//				System.out.println("小于4的数字"+s);
//			}
//		});
//		
//	    Student student=new Student();
//	    List<Student> students=new ArrayList<>();
//	    students.add(student);
//	    students.forEach(stu->{
//	    	stu.getBj();
//	    });
		
		/*
		 * List<String> list2=new ArrayList<>(); for(int i=0;i<8;i++){
		 * list2.add(i+"个元素"); } //strings.addAll(string2); for
		 * (Iterator<String> iterator = list.iterator(); iterator.hasNext();) {
		 * iterator.next();//这句要是不执行，线程就会卡在这里 } System.out.println("hello world"
		 * );
		 *//**
			 * 两个list，如果元素不仅顺序相同，且对应元素的值也相同的，则hashcode值也相同
			 * 对应的值或者顺序不同的话，hashcode值不同 也就是说，list是有序的
			 */
		/*
		 * System.out.println("strings:"+list.hashCode()+"  string2:"
		 * +list2.hashCode()); System.out.println(list.equals(list2));
		 * System.out.println(list==list2);
		 * 
		 * Map<Integer,String> map=new HashMap<>(); map.put(1,"1");
		 * map.put(2,"2"); map.put(3,"3"); Map<Integer,String> map2=new
		 * HashMap<>(); map2.put(1,"1"); map2.put(2,"2"); map2.put(3,"4");
		 *//**
			 * 两个map，如果元素值相同，无论对应顺序是否相同，hashcode都相同 也就是说，map是无序的
			 *//*
			 * System.out.println("map:"+map.hashCode()+"   map2:"
			 * +map2.hashCode());
			 * 
			 */
		// Byfs byfs=jwcservice.findByfsByID(2);
		// System.out.println(byfs==null);

	}

}
