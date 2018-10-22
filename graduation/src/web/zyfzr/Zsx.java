package web.zyfzr;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Jsgx;
import domain.Lw;
import domain.Student;
import domain.Sxcj;
import domain.Sxcjx;
import domain.Teacher;
import factory.ServiceFactory;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class Zsx
 */
@WebServlet("/zsx.do")
public class Zsx extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Zsx() {
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
		if(!ServiceFactory.getTeacherServiceInstance().checkSxcjX()) {
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
			if (gx.getJs() == 2) {
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
			request.getRequestDispatcher("zyfzr/working/zsx.jsp").forward(request, response);
			break;
		case "load":
			loadStu(request, response);
			break;
		case "update":
			updateSx(request, response);
			break;
		default:
			break;
		}
	}

	protected void loadStu(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String xuehao = request.getParameter("number");
		Teacher zyfzr = (Teacher) request.getSession().getAttribute("teacher");
		int result = validateStudent(xuehao, zyfzr);
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
		case -3:
			json.put("status", 202);
			json.put("msg", "需等到指导老师先填写之后");
			break;
		case 1:
			Student student = ServiceFactory.getStudentServiceInstance().getStudentByAccount(xuehao);
			Teacher zdls = ServiceFactory.getTeacherServiceInstance().getTeacherBYStudentYear(student.getTeacher());
			List<Sxcj> sxcjs = ServiceFactory.getTeacherServiceInstance().getSxcjpsb(zdls.getNumber(), student.getNumber());
			json = get200StatusJsonSx(json, sxcjs, zdls, student, zyfzr);
			break;
		default:
			break;
		}
		PrintWriter out = response.getWriter();
		out.print(json);
		out.close();
	}
	
	protected void updateSx(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String xuehao = request.getParameter("xuehao");
		Teacher zyfzr = (Teacher) request.getSession().getAttribute("teacher");
		int result = validateStudent(xuehao, zyfzr);
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
		case -3:
			json.put("status", 202);
			json.put("msg", "需等到指导老师先填写之后！");
			break;
		case 1:
			Student student = ServiceFactory.getStudentServiceInstance().getStudentByAccount(xuehao);
			Teacher zdls = ServiceFactory.getTeacherServiceInstance().getTeacherBYStudentYear(student.getTeacher());
			List<Object> forms = checkSxcjForm(request, response);
			if(forms != null) {
				json.put("status", 200);
				json.put("msg", "成绩更新成功！");
				ServiceFactory.getTeacherServiceInstance().zyfzrUpdateSxcj(forms,zdls,xuehao);
			}else {
				json.put("status", 500);
				json.put("msg", "非法成绩！");
			}
			break;
		default:
			break;
		}
		PrintWriter out = response.getWriter();
		out.print(json);
		out.close();
	}

	private int validateStudent(String xuehao, Teacher zyfzr) {
		if (xuehao == null) {
			return 0;
		}
		Student student = ServiceFactory.getStudentServiceInstance().getStudentByAccount(xuehao);
		if (student == null)
			return 0;// 不存在的学生
		if (student.getZy() != zyfzr.getFzr())
			return -1;// 没哟权限
		Lw lw = ServiceFactory.getTeacherServiceInstance().getStudentLw(student);
		if (lw == null || lw.getStatus() == 0)
			return -2;// 学生论文未提交，不能评
		Teacher zdls = ServiceFactory.getTeacherServiceInstance().getTeacherBYStudentYear(student.getTeacher());
		Sxcj sx = ServiceFactory.getTeacherServiceInstance().teacherGetSxPsb(zdls, xuehao);
		if (sx == null)
			return -3;// 需要等指导老师填后才有权限的
		return 1;
	}
	
	private JSONObject get200StatusJsonSx(JSONObject json, List<Sxcj> sx, Teacher teacher, Student student,Teacher zyfzr) {
		json.put("status", 200);
		json.put("content", sx);
		return json;
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
