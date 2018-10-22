package web.zdls;

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

import domain.Jsgx;
import domain.Kt;
import domain.Lw;
import domain.Student;
import domain.Sxcj;
import domain.Sxcjx;
import domain.Teacher;
import domain.Zdlspsb;
import domain.Zdlspsbx;
import domain.Zhuanye;
import factory.ServiceFactory;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class Tzdlspsb
 */
@WebServlet("/zdlspsb.do")
public class Tzdlspsb extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Tzdlspsb() {
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
		if(!ServiceFactory.getTeacherServiceInstance().checkZdlspsb()) {
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
			if (gx.getJs() == 1) {
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
			request.getRequestDispatcher("zdls/working/tzdlspsb.jsp").forward(request, response);
			break;
		case "load":
			loadStudentInfo(request, response);
			break;
		case "addpsb":
			addPsb(request, response);
			break;
		case "loadS":
			loadStuSx(request, response);
			break;
		case "addSx":
			addSxcj(request, response);
			break;
		default:
			break;
		}
	}

	protected void addPsb(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String xuehao = request.getParameter("number");
		Student student = ServiceFactory.getStudentServiceInstance().getStudentByAccount(xuehao);
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		int result = validateStudent(xuehao, teacher);
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
		case -5:
		case -3:
			List<Object> forms = checkForms(request, response);
			if(forms != null) {
				int res = ServiceFactory.getTeacherServiceInstance().teacherupdateZdlspsb(forms,teacher.getNumber(),student);
				if(res == -1){
					json.put("status", 202);
					json.put("msg", "成功更新成绩，学生已参与答辩，不可修改为‘否’！");
				}else if(res == -2){
					json.put("status", 201);
					json.put("msg", "成功更新成绩，第一批学生已答辩，不可修改为‘是’！");
				}else if(res == 1){
					json.put("status", 200);
					json.put("msg", "成功更新成绩！");
				}else {
					json.put("status", 500);
					json.put("msg", "学生已有总成绩，无法更新！");
				}
			}else {
				json.put("status", 500);
				json.put("msg", "成绩非法！");
			}
			break;
		case -4:
		case 1:
			List<Object> forms1 = checkForms(request, response);
			if(forms1 != null) {
				ServiceFactory.getTeacherServiceInstance().teacherAddZdlspsb(forms1,teacher,student);
				json.put("status", 200);
				json.put("msg", "成功添加成绩！");
			}else {
				json.put("status", 500);
				json.put("msg", "成绩非法！");
			}
			break;
		}
		PrintWriter out = response.getWriter();
		out.print(json);
		out.close();
	}

	protected void addSxcj(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String xuehao = request.getParameter("number");
		Student student = ServiceFactory.getStudentServiceInstance().getStudentByAccount(xuehao);
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		int result = validateStudent(xuehao, teacher);
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
		case -5:
		case -4:
			List<Object> forms = checkSxcjForm(request, response);
			if(forms != null) {
				int res = ServiceFactory.getTeacherServiceInstance().teacherUpdateSxcj(forms, teacher, student);
				if( res == 1) {
					json.put("status", 200);
					json.put("msg", "更新成功！");
				}else {
					json.put("status", 500);
					json.put("msg", "总成绩已出，无法更新！");
				}
			}else {
				json.put("status", 500);
				json.put("msg", "成绩非法！");
			}
			break;
		case -3:
		case 1:
			List<Object> forms1 = checkSxcjForm(request, response);
			if(forms1 != null) {
				ServiceFactory.getTeacherServiceInstance().addSxcj(forms1,teacher,student);
				json.put("status", 200);
				json.put("msg", "成功添加成绩！");
			}else {
				json.put("status", 500);
				json.put("msg", "非法成绩！");
			}
			break;
		}
		PrintWriter out = response.getWriter();
		out.print(json);
		out.close();
	}

	protected void loadStudentInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String xuehao = request.getParameter("number");
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		int result = validateStudent(xuehao, teacher);
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
		case -5:
		case -3:
			Student student = ServiceFactory.getStudentServiceInstance().getStudentByAccount(xuehao);
			List<Zdlspsb> zdlspsbs = ServiceFactory.getTeacherServiceInstance().getTeacherZdlspsb(teacher.getNumber(),
					xuehao);
			Zhuanye zy = ServiceFactory.getTeacherServiceInstance().teacherGetZy(student.getZy());
			Kt kt = ServiceFactory.getTeacherServiceInstance().teacherGetKt(student.getKt());
			json = get400StatusJson(json, teacher, zdlspsbs, student, zy, kt);
			break;
		case -4:
		case 1:
			json.put("status", 200);
			Student student1 = ServiceFactory.getStudentServiceInstance().getStudentByAccount(xuehao);
			Zhuanye zy1 = ServiceFactory.getTeacherServiceInstance().teacherGetZy(student1.getZy());
			Kt kt1 = ServiceFactory.getTeacherServiceInstance().teacherGetKt(student1.getKt());
			json.put("zxsxm", student1.getName());
			json.put("zzy", zy1.getName());
			json.put("zbj", student1.getBj());
			json.put("zktm", kt1.getName());
			json.put("zzdjs", teacher.getName());
			json.put("zdb", 1);
			break;
		}
		PrintWriter out = response.getWriter();
		out.print(json);
		out.close();
	}

	protected void loadStuSx(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String xuehao = request.getParameter("number");
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		int result = validateStudent(xuehao, teacher);
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
			json.put("msg", "需等到学生提交最终成果后！");
			break;
		case -5:
		case -4:
			Student student = ServiceFactory.getStudentServiceInstance().getStudentByAccount(xuehao);
			List<Sxcj> sxcjs = ServiceFactory.getTeacherServiceInstance().getSxcjpsb(teacher.getNumber(), xuehao);
			json = get400StatusJsonSx(json, sxcjs, teacher, student);
			break;
		case -3:
		case 1:
			json.put("status", 200);
			Student student1 = ServiceFactory.getStudentServiceInstance().getStudentByAccount(xuehao);
			Timestamp date = new Timestamp(System.currentTimeMillis());
			Teacher zd = ServiceFactory.getTeacherServiceInstance().getProfessionPrincipal(student1.getZy());
			SimpleDateFormat sy1 = new SimpleDateFormat("yyyy-MM-dd");
			json.put("szdls", teacher.getName());
			json.put("sdate", sy1.format(date));
			json.put("sfzr", zd.getName());
			json.put("sdate1", sy1.format(date));
			break;
		}
		PrintWriter out = response.getWriter();
		out.print(json);
		out.close();
	}

	protected int validateStudent(String xuehao, Teacher teacher) {
		if (xuehao == null) {
			return 0;
		}
		Student student = ServiceFactory.getStudentServiceInstance().getStudentByAccount(xuehao);
		if (student == null)
			return 0;// 不存在的学生
		if (!student.getTeacher().equals(teacher.getNumber()))
			return -1;// 没哟权限
		Lw lw = ServiceFactory.getTeacherServiceInstance().getStudentLw(student);
		if (lw == null || lw.getStatus() == 0)
			return -2;// 学生论文未提交，不能评
		boolean psb = ServiceFactory.getTeacherServiceInstance().checkZdlspsb(teacher, xuehao);
		boolean sx = ServiceFactory.getTeacherServiceInstance().checkSxcj(teacher, xuehao);
		if (psb && sx)
			return -5;
		if (psb)
			return -3;// 已经评过了
		if (sx)
			return -4;// 已经 评过了
		return 1;// 可以评
	}

	private JSONObject get400StatusJson(JSONObject json, Teacher teacher, List<Zdlspsb> psb, Student student,
			Zhuanye zy, Kt kt) {
		json.put("status", 400);
		json.put("zxsxm", student.getName());
		json.put("zzy", zy.getName());
		json.put("zbj", student.getBj());
		json.put("zktm", kt.getName());
		json.put("content", psb);
		json.put("zdb", (student.getAnswer() == 1 ? 0 : 1));
		json.put("zzdjs", teacher.getName());
		return json;
	}

	// private String floatToStringNoZero(float score) {
	// String s = String.valueOf(score);
	// if (s.indexOf(".") > 0) {
	// s = s.replaceAll("0+?$", "");// 去掉后面无用的零
	// s = s.replaceAll("[.]$", "");// 如小数点后面全是零则去掉小数点
	// }
	// return s;
	// }

	private JSONObject get400StatusJsonSx(JSONObject json, List<Sxcj> sx, Teacher teacher, Student student) {
		json.put("status", 400);
		json.put("szdls", teacher.getName());
		Teacher zd = ServiceFactory.getTeacherServiceInstance().getProfessionPrincipal(student.getZy());
		json.put("sfzr", zd.getName());
		json.put("content", sx);
		return json;
	}

	private List<Object> checkForms(HttpServletRequest request, HttpServletResponse response) {
		List<Zdlspsbx> zdlspsbxs = ServiceFactory.getTeacherServiceInstance().getZdlspsb();
		List<Object> forms = new ArrayList<Object>();
		for (int i = 0, l = zdlspsbxs.size(); i < l; i++) {
			forms.add(request.getParameter("zdlspsb" + i));
		}
		forms.add(request.getParameter("isdb"));
		for (int i = 0, l = zdlspsbxs.size(); i < l; i++) {
			if (forms.get(i) == null)
				return null;
			if (zdlspsbxs.get(i).getStatus() == 0) {
				float val = (float) forms.get(i);
				if (val < 0 || val > zdlspsbxs.get(i).getMf())
					return null;
			} else {
				if (forms.get(i).toString().length() > 400)
					return null;
			}
		}
		int isdb = (int) forms.get(zdlspsbxs.size());
		if (isdb != 0 && isdb != 1)
			return null;
		return forms;
	}
	
	private List<Object> checkSxcjForm(HttpServletRequest request, HttpServletResponse response) {
		List<Sxcjx> sxcjxs = ServiceFactory.getTeacherServiceInstance().getSxcjx();
		List<Object> forms = new ArrayList<Object>();
		for (int i = 0, l = sxcjxs.size(); i < l; i++) {
			forms.add(request.getParameter("sxcj" + i));
		}
		for (int i = 0, l = sxcjxs.size(); i < l; i++) {
			if (forms.get(i) == null)
				return null;
			if (sxcjxs.get(i).getStatus() == 0) {
				float val = (float) forms.get(i);
				if (val < 0 || val > sxcjxs.get(i).getMf())
					return null;
			} else {
				if (forms.get(i).toString().length() > 400)
					return null;
			}
		}
		return forms;
	}
}
