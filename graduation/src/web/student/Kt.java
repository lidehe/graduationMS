package web.student;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Student;
import factory.ServiceFactory;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class Kt
 */
@WebServlet("/kt.do")
public class Kt extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Kt() {
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
		String method = request.getParameter("meth");
		if (method == null)
			method = "ind";
		Student student = (Student) request.getSession().getAttribute("student");
		if (student.getStage() < 3) {
			response.sendRedirect("bylx.do");
			return;
		}
		switch (method) {
		case "ind":
			request.getRequestDispatcher("student/preparation/kt.jsp").forward(request, response);
			break;
		case "check":
			checkKt(request, response);
			break;
		case "choose":
			chooseKt(request, response);
			break;
		case "add":
			addKt(request, response);
			break;
		default:
			break;
		}
	}

	protected void checkKt(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int ktId = Integer.valueOf(request.getParameter("kt"));
		Student student = (Student) request.getSession().getAttribute("student");
		JSONObject json = new JSONObject();
		json.put("status", 200);
		if (student.getIsgroup() == 1) {// 有小组，检测一下是否选择的课题不一样
			List<Student> groups = ServiceFactory.getStudentServiceInstance().getGroupStudents(student.getNumber());
			for (int i = 0, l = groups.size(); i < l; i++) {
				if (groups.get(i).getKt() != 0 && groups.get(i).getKt() != ktId) {
					json.remove("status");
					json.put("status", 400);
					break;
				}
			}
		}
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(json);
		out.close();
	}

	protected void chooseKt(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Student student = (Student) request.getSession().getAttribute("student");
		int ktId = Integer.valueOf(request.getParameter("kt") == null ? "0" : request.getParameter("kt"));
		domain.Kt kt = ServiceFactory.getStudentServiceInstance().getStudentKt(ktId);
		JSONObject json = new JSONObject();
		if (student.getKt() != 0) {
			json.put("status", 202);
		} else {
			if (kt == null || ktId == 0) {
				json.put("status", 500);
			} else {
				int result = ServiceFactory.getStudentServiceInstance().chooseKt(ktId, student);
				if (result == 1) {
					student.setStage(6);
					student.setKt(ktId);
					request.getSession().setAttribute("student", student);
					json.put("status", 200);
				} else
					json.put("status", 400);
			}

		}
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(json);
		out.close();
	}

	protected void addKt(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String name = request.getParameter("name");
		String text = request.getParameter("text");
		Student student = (Student) request.getSession().getAttribute("student");
		JSONObject json = new JSONObject();
		if (name.length() > 40 || text.length() > 100) {
			json.put("status", 400);
		} else {
			if (student.getKt() != 0) {
				json.put("status", 202);
			} else {
				int x = ServiceFactory.getStudentServiceInstance().addKtFromStudent(name, text, student);
				if (x == 0)
					json.put("status", 500);
				else
					json.put("status", 200);
			}
		}
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(json);
		out.close();
	}
}
