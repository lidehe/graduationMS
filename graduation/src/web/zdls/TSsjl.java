package web.zdls;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Jsgx;
import domain.Ssjl;
import domain.Student;
import domain.Teacher;
import factory.ServiceFactory;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class TSsjl
 */
@WebServlet("/tssjl.do")
public class TSsjl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TSsjl() {
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
		if(!ServiceFactory.getTeacherServiceInstance().checkSxcjX()) {
			String retUrl = request.getHeader("Referer");
			response.sendRedirect(retUrl);
			return;
		}
		// TODO Auto-generated method stub
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
			request.getRequestDispatcher("zdls/working/tssjl.jsp").forward(request, response);
			break;
		case "detail":
			ssjlDetail(request, response);
			break;
		case "require":
			requireMessage(request, response);
			break;
		case "add":
			addjs(request, response);
			break;
		default:
			break;
		}
	}

	protected void addjs(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JSONObject json = new JSONObject();
		String text = request.getParameter("text");
		if (text.length() > 400) {
			json.put("status", 202);// 长度超过
			json.put("msg", "消息太长！");
		} else {
			String xuehao = request.getParameter("xuehao");
			Student student = ServiceFactory.getStudentServiceInstance().getStudentByAccount(xuehao);
			Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
			if (student.getTeacher().equals(teacher.getNumber())) {
				String time = ServiceFactory.getTeacherServiceInstance().addSsjl(teacher.getNumber(), student.getNumber(),
						text);
				json.put("status", 200);
				json.put("time", time.substring(0, 18));
			} else {
				json.put("status", 500);// 无权
				json.put("msg", "您无权回复此学生！");
			}

		}
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(json);
		out.close();
	}

	protected void requireMessage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		JSONObject json = new JSONObject();
		String xuehao = request.getParameter("xuehao");
		if (xuehao == null) {
			response.sendRedirect("login.do?method=logout");
			return;
		}
		Student student = ServiceFactory.getStudentServiceInstance().getStudentByAccount(xuehao);
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if (!student.getTeacher().equals(teacher.getNumber())) {
			json.put("status", 500);
			json.put("count", 1);
			json.put("msg", "您无权查看此学生的交流记录！");
		} else {
			String page = request.getParameter("page");
			int p = 0;
			if (page != null && !page.equals(""))
				p = Integer.valueOf(page);
			int count = ServiceFactory.getStudentServiceInstance().caculateMessage(student.getNumber());
			int all = 0;
			all = (int) count / 10;
			if (count % 10 != 0)
				all++;
			if (all == 0)
				all++;
			if (p < 0 || p > all) {
				json.put("status", 400);
				json.put("count", all);
				json.put("msg", "没有更多了！");
			} else {
				List<Ssjl> ssjls = ServiceFactory.getTeacherServiceInstance().getSsjls(p, student.getNumber());
				json.put("status", 200);
				json.put("count", all);
				json.put("content", ssjls);
			}
		}

		PrintWriter out = response.getWriter();
		out.print(json);
		out.close();
	}

	protected void ssjlDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String xuehao = request.getParameter("number");
		if (xuehao != null && !xuehao.equals("")) {
			Student student = ServiceFactory.getStudentServiceInstance().getStudentByAccount(xuehao);
			Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
			if (student != null && student.getTeacher().equals(teacher.getNumber()))
				request.getRequestDispatcher("zdls/working/ssjldetail.jsp").forward(request, response);

		}
	}

}
