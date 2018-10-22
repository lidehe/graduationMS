package web.jwc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Student;
import domain.Teacher;
import domain.Yuanxi;
import factory.JWCFactory;
import factory.ServiceFactory;
import net.sf.json.JSONArray;
import service.JWCService;
import service.StudentService;
import service.TeacherService;

/**
 * Servlet implementation class BaseInformation
 */
@WebServlet("/BaseInformation.do")
public class BaseInformation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JWCService jwcService = JWCFactory.getJwcService();
	private TeacherService teacherService = ServiceFactory.getTeacherServiceInstance();
	private StudentService studentService = ServiceFactory.getStudentServiceInstance();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BaseInformation() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method= request.getParameter("method");
		
		switch (method) {
		case "searchStudentByNumber":
			searchStudentByNumber(request, response);
			break;
		case "pageFindTeacher":
			pageFindTeacher(request, response);
			break;
		case "pageFindStudent":
			pageFindStudent(request, response);
			break;
		default:
			break;
		}
	}
	
	public void searchStudentByNumber(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String key=request.getParameter("number");

		JSONArray jsonArray = null;
		PrintWriter writer = response.getWriter();
		
		String regex="[\u4e00-\u9fa5]";//这种方式仅支持一个一个匹配
		char c[] = key.toCharArray();
		int count=0;
		for(int i=0;i<c.length;i++){
			if (String.valueOf(c[i]).matches(regex)) {
				break;
			}
			count++;
		}
		if (count==c.length) {//如果是字母+数字，就按工号搜索
			Student student=studentService.findStudentByNumber(key);
			if (student != null) {
				String jString="[{xuehao:'"+student.getNumber()+"',xingming:'"+student.getName()+"',banji:'"+student.getBj()+"'}]";
				jsonArray=JSONArray.fromObject(jString);
				writer.print(jsonArray);
			}
		}else{//汉字，就是按名字搜索
			List<Student> students=studentService.vagueSearchByName(key);
			if (students.size()>0) {
				String jString="[";
				for(Student student:students){
					String tempstr="{xuehao:'"+student.getNumber()+"',xingming:'"+student.getName()+"',banji:'"+student.getBj()+"'},";
					jString+=tempstr;
				}
				jString=jString.substring(0,jString.length()-1);
				jString+="]";
				jsonArray=JSONArray.fromObject(jString);
				writer.print(jsonArray);
			}
			
		}
	}
	
	public void  pageFindTeacher(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String yuanxiIdStr = request.getParameter("yuanxiId");
		String option = request.getParameter("option");// 翻页选项 上一页、下一页
		String nowPagestr = request.getParameter("nowPage");// 当前页码

		JSONArray jsonArray = null;
		PrintWriter writer = null;

		int nowPage = Integer.valueOf(nowPagestr);
		int yuanxiId = Integer.valueOf(yuanxiIdStr);
		List<Teacher> teachers = new ArrayList<>();
		System.out.println("当前操作:" + option + ";   当前页码:" + nowPagestr);
		String jsonString = "";
		// 当查到的数据大于0
		if (option.equals("上一页")) {
			if (nowPage <= 1) {// 如果已经是第一页，就不操作
				teachers = teacherService.pageFindTeacherByYuanxi(yuanxiId, nowPage);
			} else {// 如果不是第一页，就查询前一页
				nowPage -= 1;
				teachers = teacherService.pageFindTeacherByYuanxi(yuanxiId, nowPage);
			}
		} else if (option.equals("下一页")) {
			++nowPage;
			teachers = teacherService.pageFindTeacherByYuanxi(yuanxiId, nowPage);
			if (teachers.size() == 0) {
				writer = response.getWriter();
				writer.print("lastPage");
				writer.close();
				return;
			}

		}
		if (teachers.size() > 0) {
			jsonString = "[";
			int all=0;
			all=teacherService.CountTeacherByYuanxi(yuanxiId);
			int totalPage=0;
			if (all%10==0) {
				totalPage=all/10;
			}else{
				totalPage=all/10+1;
			}
			String pageString = "{nowPage:'" + nowPage + "',totalPage:'"+totalPage+"'},";
			
			jsonString += pageString;
			for (Teacher teacher : teachers) {
				String tempString = "";
				tempString += "{gonghao:'" + teacher.getNumber() + "',xingming:'" + teacher.getName() +  "'},";
				jsonString += tempString;
			}
			jsonString = jsonString.substring(0, jsonString.length() - 1);
			jsonString += "]";
			System.out.println(jsonString);
			jsonArray = JSONArray.fromObject(jsonString);
			writer = response.getWriter();
			writer.println(jsonArray);
			writer.close();
		}else{
			writer = response.getWriter();
			writer.print("00");
			writer.close();
		}
	}
	
	public void  pageFindStudent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String yuanxiIdStr = request.getParameter("yuanxiId");
		String zhuanyeIdStr = request.getParameter("zhuanyeId");
		String option = request.getParameter("option");// 翻页选项 上一页、下一页
		String nowPagestr = request.getParameter("nowPage");// 当前页码

		JSONArray jsonArray = null;
		PrintWriter writer = null;

		int nowPage = Integer.valueOf(nowPagestr);
		int yuanxiId = Integer.valueOf(yuanxiIdStr);
		int zhuanyeId=Integer.valueOf(zhuanyeIdStr);
		List<Student> students=new ArrayList<>();
		System.out.println("当前操作:" + option + ";   当前页码:" + nowPagestr);
		String jsonString = "";
		// 当查到的数据大于0
		if (option.equals("上一页")) {
			if (nowPage <= 1) {// 如果已经是第一页，就不操作
				students=studentService.pageFindStudentByYuanxi(yuanxiId, zhuanyeId, nowPage);
			} else {// 如果不是第一页，就查询前一页
				nowPage -= 1;
				students=studentService.pageFindStudentByYuanxi(yuanxiId, zhuanyeId, nowPage);
			}
		} else if (option.equals("下一页")) {
			++nowPage;
			students=studentService.pageFindStudentByYuanxi(yuanxiId, zhuanyeId, nowPage);
			if (students.size() == 0) {
				writer = response.getWriter();
				writer.print("lastPage");
				writer.close();
				return;
			}

		}
		jsonString = "[";
		if (students.size() > 0) {
			int all=0;
			all=studentService.CountStudentByYuanxi(yuanxiId, zhuanyeId);
			int totalPage=0;
			if (all%10==0) {
				totalPage=all/10;
			}else{
				totalPage=all/10+1;
			}
			String pageString = "{nowPage:'" + nowPage + "',totalPage:'"+totalPage+"'},";
			jsonString += pageString;
			for (Student student : students) {
				String tempString = "";
				tempString += "{xuehao:'" + student.getNumber() + "',xingming:'" + student.getName() + "',banji:'"
						+student.getBj() + "'},";
				jsonString += tempString;
			}
			jsonString = jsonString.substring(0, jsonString.length() - 1);
			jsonString += "]";
			System.out.println(jsonString);
			jsonArray = JSONArray.fromObject(jsonString);
			writer = response.getWriter();
			writer.println(jsonArray);
			writer.close();
		}else{
			writer = response.getWriter();
			writer.print("00");
			writer.close();
		}
	}
	public Map<Integer, String> yuanxiMap() {
		List<Yuanxi> yuanxis = jwcService.findAllYuanxi();
		Map<Integer, String> yuanxiMap = new HashMap<>();
		for (int j = 0; j < yuanxis.size(); j++) {
			yuanxiMap.put(yuanxis.get(j).getId(), yuanxis.get(j).getName());
		}
		return yuanxiMap;
	}
}
