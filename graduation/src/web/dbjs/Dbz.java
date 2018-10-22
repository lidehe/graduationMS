package web.dbjs;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Dbjsz;
import domain.Dbpsb;
import domain.Dbpsbx;
import domain.Dbzl;
import domain.Jsgx;
import domain.Kt;
import domain.Lw;
import domain.Pypsb;
import domain.Pypsbx;
import domain.Student;
import domain.Teacher;
import domain.Zf;
import domain.Zhuanye;
import factory.ServiceFactory;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class Dbz
 */
@WebServlet("/dbz.do")
public class Dbz extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Dbz() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(!ServiceFactory.getTeacherServiceInstance().checkDbPyPsb()) {
			String retUrl = request.getHeader("Referer");
			response.sendRedirect(retUrl);
			return;
		}
		String method = request.getParameter("meth");
		if (method == null)
			method = "ind";
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		List<Jsgx> gxs = ServiceFactory.getTeacherServiceInstance().getTeacherJses(teacher);
		boolean js = false;
		for (Jsgx gx : gxs) {
			if (gx.getJs() == 8) {
				js = true;
				break;
			}
		}
		if (!js) {// 身份不对，直接自动退出
			response.sendRedirect("login.do?method=logout");
			return;
		}
		switch (method) {
		case "ind":
			request.getRequestDispatcher("dbps/working/dbps.jsp").forward(request, response);
			break;
		case "load":
			loadStudents(request, response);
			break;
		case "loadDb":
			loadInfoDb(request, response);
			break;
		case "addDb":
			addDbCj(request, response);
			break;
		case "loadPy":
			loadInfoPy(request, response);
			break;
		case "addPy":
			addPyCj(request, response);
			break;
		default:
			response.sendRedirect("dbz.do");
			break;
		}
	}

	protected void loadStudents(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int zh = Integer.valueOf(request.getParameter("zh"));
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		List<Dbzl> dbzs = ServiceFactory.getTeacherServiceInstance().teacherGetAllDbz(teacher);
		boolean limits = false;
		for (Dbzl z : dbzs) {
			if (z.getId() == zh) {
				limits = true;
				break;
			}
		}
		JSONObject json = new JSONObject();
		if (limits) {
			List<Student> students = ServiceFactory.getTeacherServiceInstance().teacherGetDbzStudents(zh);
			json.put("status", 200);
			json.put("students", students);
			List<Teacher> teachers = ServiceFactory.getTeacherServiceInstance().teacherGetCommonDbzTeacher(zh);
			String teachersName = "";
			for (Teacher teacher2 : teachers) {
				teachersName += teacher2.getName() + "  ";
			}
			json.put("teacherName", teachersName);
		} else {
			json.put("status", 202);// 你不是这个小组的
			json.put("msg", "您不在此小组内！");
		}
		response.setContentType("application/json;chartset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(json);
		out.close();
	}

	protected void loadInfoDb(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String xuehao = request.getParameter("number");
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		int result = validateStudentDb(xuehao, teacher);
		JSONObject json = new JSONObject();
		response.setContentType("application/json;charset=utf-8");
		switch (result) {
		case 0:
			json.put("status", 202);
			json.put("msg", "学生不存在！");
			break;
		case -1:
			json.put("status", 202);
			json.put("msg", "您没有权限查看此学生！");
			break;
		case -2:
			json.put("status", 202);
			json.put("msg", "此学生暂不能参与评审成绩！");
			break;
		case -3:
			Student student = ServiceFactory.getStudentServiceInstance().getStudentByAccount(xuehao);
			Zhuanye zy = ServiceFactory.getTeacherServiceInstance().teacherGetZy(student.getZy());
			Kt kt = ServiceFactory.getTeacherServiceInstance().teacherGetKt(student.getKt());
			Teacher dbzz = ServiceFactory.getTeacherServiceInstance().teacherGetDbzz(student.getDbz());
			List<Dbpsb> dbpsbs = ServiceFactory.getTeacherServiceInstance().getOneDbpsbOrderXId(teacher.getNumber(),
					xuehao);
			json = setJsonDb(json, teacher, dbpsbs, student, zy, kt, dbzz);
			break;
		case 1:
			Student student1 = ServiceFactory.getStudentServiceInstance().getStudentByAccount(xuehao);
			Timestamp time1 = new Timestamp(System.currentTimeMillis());
			SimpleDateFormat sy1 = new SimpleDateFormat("yyyy");
			SimpleDateFormat sm1 = new SimpleDateFormat("MM");
			SimpleDateFormat sd1 = new SimpleDateFormat("dd");
			Zhuanye zy1 = ServiceFactory.getTeacherServiceInstance().teacherGetZy(student1.getZy());
			Kt kt1 = ServiceFactory.getTeacherServiceInstance().teacherGetKt(student1.getKt());
			Teacher dbzz1 = ServiceFactory.getTeacherServiceInstance().teacherGetDbzz(student1.getDbz());
			json.put("status", 200);
			json.put("dxsxm", student1.getName());
			json.put("dzy", zy1.getName());
			json.put("dbj", student1.getBj());
			json.put("dktm", kt1.getName());
			json.put("dzdjs", dbzz1.getName());
			json.put("dyear", sy1.format(time1));
			json.put("dmonth", sm1.format(time1));
			json.put("dday", sd1.format(time1));
			break;
		}
		PrintWriter out = response.getWriter();
		out.print(json);
		out.close();
	}

	protected void addDbCj(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String xuehao = request.getParameter("number");
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		int result = validateStudentDb(xuehao, teacher);
		JSONObject json = new JSONObject();
		response.setContentType("application/json;charset=utf-8");
		switch (result) {
		case 0:
			json.put("status", 202);
			json.put("msg", "学生不存在！");
			break;
		case -1:
			json.put("status", 202);
			json.put("msg", "您没有权限查看此学生！");
			break;
		case -2:
			json.put("status", 202);
			json.put("msg", "此学生暂不能参与评审成绩！");
			break;
		case -3:
			List<Object> forms = checkDbpsbForm(request, response);
			if (forms != null) {
				Zf zfR = ServiceFactory.getTeacherServiceInstance().checkZfRecord(xuehao);
				if (zfR != null) {
					json.put("status", 500);
					json.put("msg", "总成绩已出，无法更新成绩！");
				} else {
					ServiceFactory.getTeacherServiceInstance().teacherUpdateDbpsb(forms, teacher, xuehao);
					json.put("status", 200);
					json.put("msg", "成功更新成绩！");
				}
			} else {
				json.put("status", 500);
				json.put("msg", "成绩非法！");
			}
			break;
		case 1:
			List<Object> forms1 = checkDbpsbForm(request, response);
			if (forms1 != null) {
				ServiceFactory.getTeacherServiceInstance().addDbpsb(forms1, teacher, xuehao);
				json.put("status", 200);
				json.put("msg", "成功添加成绩！");
			} else {
				json.put("status", 500);
				json.put("msg", "成绩非法！");
			}
			break;
		}
		PrintWriter out = response.getWriter();
		out.print(json);
		out.close();
	}

	private int validateStudentDb(String xuehao, Teacher teacher) {
		if (xuehao == null) {
			return 0;
		}
		Student student = ServiceFactory.getStudentServiceInstance().getStudentByAccount(xuehao);
		if (student == null)
			return 0;// 不存在的学生
		List<Dbjsz> jszs = ServiceFactory.getTeacherServiceInstance().getDbjsById(student.getDbz());
		boolean result = false;
		for (Dbjsz dbjs : jszs) {
			if (dbjs.getGonghao().equals(teacher.getNumber())) {
				result = true;
				break;
			}
		}
		if (!result)
			return -1;// 没哟权限
		Lw lw = ServiceFactory.getTeacherServiceInstance().getStudentLw(student);
		if (lw == null || lw.getStatus() == 0)
			return -2;// 学生论文未提交，不能评
		boolean ed = ServiceFactory.getTeacherServiceInstance().checkDbpsb(teacher, xuehao);
		if (ed)
			return -3;// 已经评过了
		return 1;// 可以评
	}

	private List<Object> checkDbpsbForm(HttpServletRequest request, HttpServletResponse response) {
		List<Object> forms = new ArrayList<Object>();
		List<Dbpsbx> dbpsbxs = ServiceFactory.getTeacherServiceInstance().getDbpsbxOrderId();
		for (int i = 0, l = dbpsbxs.size(); i < l; i++) {
			forms.add(request.getParameter("dbpsb" + i));
		}
		for (int i = 0, l = dbpsbxs.size(); i < l; i++) {
			if (forms.get(i) == null)
				return null;
			if (dbpsbxs.get(i).getStatus() == 0) {
				float val = (float) forms.get(i);
				if (val < 0 || val > dbpsbxs.get(i).getMf())
					return null;
			} else {
				if (forms.get(i).toString().length() > 400)
					return null;
			}
		}
		return forms;
	}

	private JSONObject setJsonDb(JSONObject json, Teacher teacher, List<Dbpsb> dbpsbs, Student student, Zhuanye zy,
			Kt kt, Teacher dbzz) {
		json.put("status", 400);
		json.put("dxsxm", student.getName());
		json.put("dzy", zy.getName());
		json.put("dbj", student.getBj());
		json.put("dktm", kt.getName());
		json.put("content", dbpsbs);
		return json;
	}

	protected void loadInfoPy(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String xuehao = request.getParameter("number");
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		int result = validateStudentPy(xuehao, teacher);
		JSONObject json = new JSONObject();
		response.setContentType("application/json;charset=utf-8");
		switch (result) {
		case 0:
			json.put("status", 202);
			json.put("msg", "学生不存在！");
			break;
		case -1:
			json.put("status", 202);
			json.put("msg", "您没有权限查看此学生！");
			break;
		case -2:
			json.put("status", 202);
			json.put("msg", "此学生暂不能参与评审成绩！");
			break;
		case -3:
			Student student = ServiceFactory.getStudentServiceInstance().getStudentByAccount(xuehao);
			List<Pypsb> pypsbs = ServiceFactory.getTeacherServiceInstance().getOnePypsbOrderXId(teacher.getNumber(),
					xuehao);
			Zhuanye zy = ServiceFactory.getTeacherServiceInstance().teacherGetZy(student.getZy());
			Kt kt = ServiceFactory.getTeacherServiceInstance().teacherGetKt(student.getKt());
			json = setJsonPy(json, teacher, pypsbs, student, zy, kt);
			break;
		case 1:
			json.put("status", 200);
			Student student1 = ServiceFactory.getStudentServiceInstance().getStudentByAccount(xuehao);
			Timestamp time1 = new Timestamp(System.currentTimeMillis());
			SimpleDateFormat sy1 = new SimpleDateFormat("yyyy");
			SimpleDateFormat sm1 = new SimpleDateFormat("MM");
			SimpleDateFormat sd1 = new SimpleDateFormat("dd");
			Zhuanye zy1 = ServiceFactory.getTeacherServiceInstance().teacherGetZy(student1.getZy());
			Kt kt1 = ServiceFactory.getTeacherServiceInstance().teacherGetKt(student1.getKt());
			json.put("pxsxm", student1.getName());
			json.put("pzy", zy1.getName());
			json.put("pbj", student1.getBj());
			json.put("pktm", kt1.getName());
			json.put("pzdjs", teacher.getName());
			json.put("pyear", sy1.format(time1));
			json.put("pmonth", sm1.format(time1));
			json.put("pday", sd1.format(time1));
			json.put("pdb", "是");
			break;
		}
		PrintWriter out = response.getWriter();
		out.print(json);
		out.close();
	}

	protected void addPyCj(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String xuehao = request.getParameter("number");
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		int result = validateStudentPy(xuehao, teacher);
		JSONObject json = new JSONObject();
		response.setContentType("application/json;charset=utf-8");
		switch (result) {
		case 0:
			json.put("status", 202);
			json.put("msg", "学生不存在！");
			break;
		case -1:
			json.put("status", 202);
			json.put("msg", "您没有权限查看此学生！");
			break;
		case -2:
			json.put("status", 202);
			json.put("msg", "此学生暂不能参与评审成绩！");
			break;
		case -3:
			List<Object> forms = checkPypsbForm(request, response);
			if (forms != null) {
				Zf zfR = ServiceFactory.getTeacherServiceInstance().checkZfRecord(xuehao);
				if (zfR != null) {
					json.put("status", 500);
					json.put("msg", "总成绩已出，无法更新成绩！");
				} else {
					ServiceFactory.getTeacherServiceInstance().teacherupdatePypsb(forms, teacher, xuehao);
					json.put("status", 200);
					json.put("msg", "成功更新成绩！");
				}
			} else {
				json.put("status", 500);
				json.put("msg", "成绩非法！");
			}
			break;
		case 1:
			List<Object> forms1 = checkPypsbForm(request, response);
			if (forms1 != null) {
				json.put("status", 200);
				json.put("msg", "成功添加成绩！");
				ServiceFactory.getTeacherServiceInstance().addPypsb(forms1, teacher, xuehao);
			} else {
				json.put("status", 500);
				json.put("msg", "成绩非法！");
			}
			break;
		}
		PrintWriter out = response.getWriter();
		out.print(json);
		out.close();
	}

	private List<Object> checkPypsbForm(HttpServletRequest request, HttpServletResponse response) {
		List<Object> forms = new ArrayList<Object>();
		List<Pypsbx> pypsbxs = ServiceFactory.getTeacherServiceInstance().getPypsbxOrderId();
		for (int i = 0, l = pypsbxs.size(); i < l; i++) {
			forms.add(request.getParameter("pypsb" + i));
		}
		for (int i = 0, l = pypsbxs.size(); i < l; i++) {
			if (forms.get(i) == null)
				return null;
			if (pypsbxs.get(i).getStatus() == 0) {
				float val = (float) forms.get(i);
				if (val < 0 || val > pypsbxs.get(i).getMf())
					return null;
			} else {
				if (forms.get(i).toString().length() > 400)
					return null;
			}
		}
		return forms;
	}

	private int validateStudentPy(String xuehao, Teacher teacher) {
		if (xuehao == null) {
			return 0;
		}
		Student student = ServiceFactory.getStudentServiceInstance().getStudentByAccount(xuehao);
		if (student == null)
			return 0;// 不存在的学生
		List<Dbjsz> jszs = ServiceFactory.getTeacherServiceInstance().getDbjsById(student.getDbz());
		boolean result = false;
		for (Dbjsz dbjs : jszs) {
			if (dbjs.getGonghao().equals(teacher.getNumber())) {
				result = true;
				break;
			}
		}
		if (!result)
			return -1;// 没哟权限
		Lw lw = ServiceFactory.getTeacherServiceInstance().getStudentLw(student);
		if (lw == null || lw.getStatus() == 0)
			return -2;// 学生论文未提交，不能评
		Pypsb psb = ServiceFactory.getTeacherServiceInstance().teacherGetStudentPypsb(teacher, xuehao);
		if (psb != null)
			return -3;// 已经评过了
		return 1;// 可以评
	}

	private JSONObject setJsonPy(JSONObject json, Teacher teacher, List<Pypsb> psb, Student student, Zhuanye zy,
			Kt kt) {
		json.put("status", 400);
		json.put("pxsxm", student.getName());
		json.put("pzy", zy.getName());
		json.put("pbj", student.getBj());
		json.put("pktm", kt.getName());
		json.put("content", psb);
		return json;
	}

	protected String floatToStringNoZero(float score) {
		String s = String.valueOf(score);
		if (s.indexOf(".") > 0) {
			s = s.replaceAll("0+?$", "");// 去掉后面无用的零
			s = s.replaceAll("[.]$", "");// 如小数点后面全是零则去掉小数点
		}
		return s;
	}
}
