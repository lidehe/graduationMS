package web.secretary;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import domain.Jsgx;
import domain.Student;
import domain.Teacher;
import domain.Yuanxi;
import factory.JWCFactory;
import factory.ServiceFactory;
import net.sf.json.JSONArray;
import service.JWCService;
import service.StudentService;
import service.TeacherService;
import utilsOffice.ReadExcel;
import utilsOffice.WriteExcel;

/**
 * Servlet implementation class StudentDistibute
 */
@WebServlet("/StudentDistibute.do")
public class StudentDistibute extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JWCService jwcService = JWCFactory.getJwcService();
	private TeacherService teacherService = ServiceFactory.getTeacherServiceInstance();
	private StudentService studentService = ServiceFactory.getStudentServiceInstance();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StudentDistibute() {
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
		// 设置院系map，在页面上把院系id转换成院系名字
		HttpSession session = request.getSession();
		session.removeAttribute("yuanxiMap");
		session.removeAttribute("zhuanyeMap");
		List<Yuanxi> yuanxis = jwcService.findAllYuanxi();
		Map<Integer, String> yuanxiMap = new HashMap<>();
		for (int j = 0; j < yuanxis.size(); j++) {
			yuanxiMap.put(yuanxis.get(j).getId(), yuanxis.get(j).getName());
		}
		session.setAttribute("yuanxiMap", yuanxiMap);

		String method = request.getParameter("method");
		String number = request.getParameter("number");// 教师工号/学生学号

		if (method != null) {
			switch (method) {
			case "pageFindTearcherByYuanxiId":// 根据院系查找教师,分页查找
				pageFindTearcherByYuanxiId(request, response);
				break;
			case "searchByNumber":// 按照工号查询教师
				searchTeacherByNumber(request, response);
				break;
			case "pageFindStudentByZhuanyeId":// 根据院系查找学生,分页查找还未分配指导老师的学生
				pageFindStudentByZhuanyeId(request, response);
				break;
			case "findUnbindedStudentBynumber":// 根据学号查找还未分配指导老师的学生
				findUnbindedStudentBynumber(request, response);
				break;
			case "setBind":// 确认绑定
				setBind(request, response);
				break;
			case "findBindedStudent":// 查询与某教师绑定的所有学生
				findBindedStudent(request, response);
				break;
			case "unBindStudentByNumber":// 删除某教师下的一个学生
				Student student2 = studentService.findStudentByNumber(number);
				student2.setTeacher(null);
				studentService.updateStudent(student2);
				break;
			case "exportTeacherAndHisStudent":// 删除某教师下的一个学生
				exportTeacherAndHisStudent(request, response);
				break;
			}
		} else {// 当method为空的时候，就是通过导入师生匹配文件来进行匹配
			PrintWriter writer = response.getWriter();
			System.out.println("导入配置文件进行匹配");
			String docName = "";// 上传的文件名字
			// 文件上传的路径
			String path = request.getSession().getServletContext().getRealPath("/OfficeFile");
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			try {
				List items = upload.parseRequest(request);
				Iterator itr = items.iterator();
				while (itr.hasNext()) {
					FileItem item = (FileItem) itr.next();
					if (item.isFormField()) {
						// "，表单参数值:" + item.getString("UTF-8"));
					} else {
						if (item.getName() != null && !item.getName().equals("")) {
							docName = item.getName();
							File tempFile = new File(item.getName());
							File file = new File(path, tempFile.getName());
							item.write(file);
							System.out.println("upload.message上传文件成功！");
						} else {
							writer.print("00");
							System.out.println("upload.message没有选择上传文件！");
							writer.close();
						}
					}
				}
			} catch (FileUploadException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("upload.message", "上传文件失败！");
			}
			// 拼接文件路径，用于读取上传的文件
			File file = null;
			String pathTail = "/OfficeFile/" + docName;// 用于拼接成完整的文件路径名字
			file = new File(request.getSession().getServletContext().getRealPath(pathTail));
			// 取到从excel里读取来的多行数据
			Map<Integer, List<String>> distributeInfo = ReadExcel.getDataFromExcel(file);
			boolean result = true;
			List<String[]> errors = new ArrayList<>();
			// 一行里的多列数据
			List<String> data;
			// 循环读取行
			for (int i = 0; i < distributeInfo.size(); i++) {
				List<String>  data1 = distributeInfo.get(i);
				try {
					data = distributeInfo.get(i);
					// 读取列数据
					String gonghao = data.get(2);
					String xuehao = data.get(0);
					Teacher teacher = teacherService.findTeacherByNuber(gonghao);
					if (teacher == null) {// 老师不存在系统数据库异常
						String[] error = { data.get(0), data.get(1), data.get(2), data.get(3), "教师信息不在数据库中" };
						errors.add(error);
						result = false;
						continue;
					} else {// 老师存在，但还没分配指导教师角色异常
						List<Jsgx> jsgxs = jwcService.findJsgxByTeacherNumber(gonghao);
						boolean isZDLS = false;
						for (Jsgx jsgx : jsgxs) {
							if (jsgx.getJs() == 1) {
								isZDLS = true;
							}
						}
						if (!isZDLS) {
							String[] error = { data.get(0), data.get(1), data.get(2), data.get(3),"教师还没有分配指导教师角色，请分配后尝试导入" };
							errors.add(error);
							result = false;
							continue;
						}
					}
					Student student = studentService.findStudentByNumber(xuehao);
					if (student == null) {
						String[] error = { data.get(0), data.get(1), data.get(2), data.get(3),"学生信息不在数据库中" };
						errors.add(error);
						result = false;
						continue;
					} else {
						// 进行师生绑定
						student.setTeacher(gonghao);
						// 将绑定信息存储到数据库
						studentService.updateStudent(student);
					}
				} catch (Exception e) {// 遇到错误，就停止导入，并指出错误所在的行
					String[] error = { data1.get(0), data1.get(1), data1.get(2), data1.get(3),"未知异常，可能是表格格式错误" };
					errors.add(error);
					result = false;
					e.printStackTrace();
				}
			}
			if (!result) {
				String exportPath = request.getSession().getServletContext().getRealPath("FileCache/");
				result = WriteExcel.makeNewExcel(exportPath, errors, "异常师生信息", "师生绑定导入错误信息表");
				writer.print("师生绑定导入错误信息表.xlsx");
			}
		}
	}

	public void exportTeacherAndHisStudent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String number = request.getParameter("number");// 教师工号
		Teacher teacher = teacherService.findTeacherByNuber(number);
		String fileName = teacher.getName() + "指导的学生表";
		PrintWriter writer = response.getWriter();
		List<Student> students = new ArrayList<>();
		students = studentService.findBindedByTeacherNumber(number);
		// 当查到的数据大于0
		boolean result = true;
		if (students.size() > 0) {
			List<String[]> list = new ArrayList<>();
			String[] title = { "所在院系", "学生学号", "学生姓名", "所在班级" };
			list.add(title);
			for (Student student : students) {
				// 按照院系、工号、姓名的方式给出
				String[] infors = { yuanxiMap().get(student.getYx()), student.getNumber(), student.getName(),
						student.getBj() };
				list.add(infors);
			}

			String path = request.getSession().getServletContext().getRealPath("FileCache/");
			result = WriteExcel.makeNewExcel(path, list, "学生信息", fileName);
		} else {
			result = false;
		}
		if (result) {
			writer.print(fileName + ".xlsx");
		} else {
			writer.print("00");
		}
		writer.close();
	}

	public void setBind(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String numberStr = request.getParameter("numberStr");// 学生学号集合字串
		String number = request.getParameter("number");// 教师工号
		PrintWriter writer = response.getWriter();
		String[] numbers = numberStr.split(":");
		for (String s : numbers) {
			Student student2 = studentService.findStudentByNumber(s);
			System.out.println("学号：" + s);
			student2.setTeacher(number);
			studentService.updateStudent(student2);
		}
		writer = response.getWriter();
		writer.print("匹配成功！");
		writer.close();
	}

	public void findBindedStudent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String number = request.getParameter("number");// 教师工号/学生学号
		JSONArray jsonArray = null;
		PrintWriter writer = response.getWriter();
		List<Student> students2 = new ArrayList<>();
		students2 = studentService.findBindedByTeacherNumber(number);
		if (students2.size() > 0) {
			String jsonString = "[";
			for (Student st : students2) {
				String tempString = "";
				tempString += "{xuehao:'" + st.getNumber() + "',name:'" + st.getName() + "',banji:'" + st.getBj()
						+ "'},";
				jsonString += tempString;
			}
			int strLength = jsonString.length();
			jsonString = jsonString.substring(0, strLength - 1);
			jsonString += "]";
			jsonArray = JSONArray.fromObject(jsonString);
			writer = response.getWriter();
			writer.println(jsonArray);
			writer.close();
		}
	}

	public void findUnbindedStudentBynumber(HttpServletRequest request, HttpServletResponse response)
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
			Student student = studentService.findStudentByNumber(key);
			if (student == null) {
				writer = response.getWriter();
				writer.print("00");
				writer.close();
			} else if (student.getTeacher() != null) {
				writer = response.getWriter();
				writer.print("11");
				writer.close();
			} else {
				String jString = "[{xuehao:'" + student.getNumber() + "',name:'" + student.getName() + "',banji:'"
						+ student.getBj() + "'}]";
				jsonArray = JSONArray.fromObject(jString);
				writer.print(jsonArray);
				writer.close();
			}
		}else{//汉字，就是按名字搜索
			List<Student> students=studentService.vagueSearchByName(key);
			if (students.size()>0) {
				String jString="[";
				for(Student student:students){
					if (student.getTeacher() != null) {
						String tempstr="{xuehao:'" + student.getNumber() + "',name:'" + student.getName() + "',banji:'"
						+ student.getBj() + "'},";
						jString+=tempstr;
					}
				}
				jString=jString.substring(0,jString.length()-1);
				jString+="]";
				jsonArray=JSONArray.fromObject(jString);
				writer.print(jsonArray);
			}
			
		}
	}

	public void searchTeacherByNumber(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String key = request.getParameter("number");// 教师工号
		JSONArray jsonArray = null;
		PrintWriter writer = null;
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
			Teacher teacher = new Teacher();
			teacher = teacherService.findTeacherByNuber(key);
			if (teacher != null) {
				int studentCount=teacherService.countStudentOfTeacher(teacher.getNumber());
				String jString = "[{gonghao:'" + teacher.getNumber() + "',name:'" + teacher.getName() +"',stuCount:'" + studentCount + "',yuanxi:'"+ yuanxiMap().get(teacher.getYx()) + "'}]";
				jsonArray = JSONArray.fromObject(jString);
				writer = response.getWriter();
				writer.println(jsonArray);
				writer.close();
			}
		}else{//汉字，就是按名字搜索
			List<Teacher> teachers=teacherService.vagueSearchByName(key);
			if (teachers.size()>0) {
				String jString = "[";
				for(Teacher teacher:teachers){
					int studentCount=teacherService.countStudentOfTeacher(teacher.getNumber());
					String tempstr="{gonghao:'" + teacher.getNumber() + "',name:'" + teacher.getName()+"',stuCount:'" + studentCount + "',yuanxi:'"+ yuanxiMap().get(teacher.getYx()) + "'},";
					jString+=tempstr;
				}
				jString=jString.substring(0,jString.length()-1);
				jString+="]";
				jsonArray = JSONArray.fromObject(jString);
				writer = response.getWriter();
				writer.println(jsonArray);
				writer.close();
			}
		}
		
	}

	public void pageFindTearcherByYuanxiId(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String yuanxiIdStr = request.getParameter("yuanxiId");
		String option = request.getParameter("option").trim();// 翻页选项、initial表示第一次请求，上一页、下一页
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
				// 因为查的是指导老师，所以jsid是1
				teachers = teacherService.findTeacherByYuanxiAndJs(yuanxiId, 1, nowPage);
			} else {// 如果不是第一页，就查询前一页
				nowPage -= 1;
				teachers = teacherService.findTeacherByYuanxiAndJs(yuanxiId, 1, nowPage);
			}
		} else if (option.equals("下一页")) {
			++nowPage;
			teachers = teacherService.findTeacherByYuanxiAndJs(yuanxiId, 1, nowPage);
			if (teachers.size() == 0) {
				writer = response.getWriter();
				writer.print("lastPage");
				writer.close();
				return;
			}

		}
		jsonString = "[";
		if (teachers.size() > 0) {
			int all = 0;
			all = teacherService.CountfindTeacherByYuanxiAndJs(yuanxiId, 1);
			int totalPage = 0;
			if (all % 10 == 0) {
				totalPage = all / 10;
			} else {
				totalPage = all / 10 + 1;
			}

			String pageString = "{nowPage:'" + nowPage + "',totalPage:'" + totalPage + "'},";
			jsonString += pageString;
			for (Teacher teacher : teachers) {
			    List<Student> hisStudents=new ArrayList<>();
			    hisStudents=studentService.findBindedByTeacherNumber(teacher.getNumber());
				String tempString = "";
				tempString += "{gonghao:'" + teacher.getNumber() + "',name:'" + teacher.getName() + "',yuanxi:'"
						+ yuanxiMap().get(teacher.getYx()) +"',studentCount:'"+hisStudents.size() + "'},";
				jsonString += tempString;
			}
			jsonString = jsonString.substring(0, jsonString.length() - 1);
			jsonString += "]";
			System.out.println(jsonString);
			jsonArray = JSONArray.fromObject(jsonString);
			writer = response.getWriter();
			writer.println(jsonArray);
			writer.close();
		} else {
			writer = response.getWriter();
			writer.print("00");
			writer.close();
		}

	}

	public void pageFindStudentByZhuanyeId(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String zhuanyeIdStr = request.getParameter("zhuanyeId");
		String option = request.getParameter("option").trim();// 翻页选项、initial表示第一次请求，上一页、下一页
		String nowPagestr = request.getParameter("nowPage");// 当前页码
        String banjiStr=request.getParameter("banji");
		int nowPage = Integer.valueOf(nowPagestr);
		int zhuanyeId = Integer.valueOf(zhuanyeIdStr);
        int banji=Integer.valueOf(banjiStr);
		JSONArray jsonArray = null;
		PrintWriter writer = null;

		List<Student> result = new ArrayList<>();
		// 当查到的数据大于0
		if (option.equals("上一页")) {
			if (nowPage <= 1) {// 如果已经是第一页，就不操作
				result = studentService.findUnbindedStudentByZhuanyeId(zhuanyeId,banji, nowPage);
			} else {// 如果不是第一页，就查询前一页
				nowPage -= 1;
				result = studentService.findUnbindedStudentByZhuanyeId(zhuanyeId,banji , nowPage);
			}
		} else if (option.equals("下一页")) {
			++nowPage;
			result = studentService.findUnbindedStudentByZhuanyeId(zhuanyeId,banji , nowPage);
			if (result.size() <= 0) {// 如果已经是最后一页
				writer = response.getWriter();
				writer.print("lastPage");
				writer.close();
				return;
			}
		}
		String jsonString = "[";
		if (result.size() > 0) {
			int all = 0;
			all = studentService.CountUnbindedStudentByZhuanyeId(zhuanyeId,banji);
			int totalPage = 0;
			if (all % 10 == 0) {
				totalPage = all / 10;
			} else {
				totalPage = all / 10 + 1;
			}

			String pageString = "{nowPage:'" + nowPage + "',totalPage:'" + totalPage + "'},";
			jsonString += pageString;
			for (Student student : result) {
				String tempString = "";
				tempString += "{xuehao:'" + student.getNumber() + "',name:'" + student.getName() + "',banji:'"
						+ student.getBj() + "'},";
				jsonString += tempString;
			}
			jsonString = jsonString.substring(0, jsonString.length() - 1);
			jsonString += "]";
			jsonArray = JSONArray.fromObject(jsonString);
			writer = response.getWriter();
			writer.println(jsonArray);
			writer.close();
		} else {
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
