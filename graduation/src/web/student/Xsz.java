package web.student;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Student;
import factory.ServiceFactory;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class Xsz
 */
@WebServlet("/xsz.do")
public class Xsz extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Xsz() {
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
		if (method != null)
			switch (method) {
			case "create":
				createGroup(request, response);
				break;
			case "search":
				searchAdd(request, response);
				break;
			case "add":
				addToGroup(request,response);
				break;
			case "remove":
				removeFromGroup(request, response);
				break;
			default:
				break;
			}
	}

	protected void createGroup(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Student student = (Student) request.getSession().getAttribute("student");
		JSONObject json = new JSONObject();
		if(student.getIsgroup() != 1) {
			int result = ServiceFactory.getStudentServiceInstance().createGroup(student);
			student.setIsgroup(1);
			request.getSession().setAttribute("student", student);
			if (result == 1)
				json.put("status", 200);
			else
				json.put("status", 400);
		}else {
			json.put("status", 400);
		}
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(json);
		out.close();
	}

	protected void searchAdd(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String number = request.getParameter("number");
		Student student = ServiceFactory.getStudentServiceInstance().getStudentByAccount(number);
		JSONObject json = new JSONObject();
		if (student != null) {
			if (student.getIsgroup() == 1) {
				json.put("status", 500);// 已经有了小组
			} else {
				json.put("status", 200);// ok
			}
			json.put("name", student.getName());
			json.put("xsxh", student.getNumber());
		} else {
			json.put("status", 404);// 不存在
		}
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(json);
		out.close();
	}

	protected void addToGroup(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String number = request.getParameter("number");
		Student student = (Student) request.getSession().getAttribute("student");
		int result = ServiceFactory.getStudentServiceInstance().addStudentToGroup(number, student);
		JSONObject json = new JSONObject();
		if(result == 1)
			json.put("status", 200);
		else
			json.put("status", 400);
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(json);
		out.close();
	}
	
	protected void removeFromGroup(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String number = request.getParameter("number");
		JSONObject json = new JSONObject();
		int result = ServiceFactory.getStudentServiceInstance().removeFromGroup(number);
		if(result == 1)
			json.put("status", 200);
		else
			json.put("status", 400);
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(json);
		out.close();
	}

}
