package web.student;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Ssjl;
import domain.Student;
import factory.ServiceFactory;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class Gtjl
 */
@WebServlet("/ssjl.do")
public class Gtjl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Gtjl() {
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
		Student student = (Student) request.getSession().getAttribute("student");
		if (student == null)
			response.sendRedirect("login.do?method=logout");
		if (method == null) {
			method = "stu";
		}
		switch (method) {
		case "stu":
			request.getRequestDispatcher("student/working/ssjl.jsp").forward(request, response);
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
		if(text.length() > 400){
			json.put("status", 202);//长度超过
			json.put("msg", "消息太长！");
		}else{
			Student student = (Student)request.getSession().getAttribute("student");
			String time = ServiceFactory.getStudentServiceInstance().addjl(student, text);
			json.put("status", 200);
			json.put("time", time.substring(0, 18));
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
		Student student = (Student)request.getSession().getAttribute("student");
		String page = request.getParameter("page");
		int p = 0;
		if(page != null && !page.equals(""))
			p = Integer.valueOf(page);
		int count = ServiceFactory.getStudentServiceInstance().caculateMessage(student.getNumber());
		int all = 0;
		all = (int)count/10;
		if(count%10 != 0)
			all++;
		if(all == 0)
			all++;
		if(p < 0 || p > all) {
			json.put("status", 400);
			json.put("count", all);
			json.put("msg", "没有更多了！");
		}else {
			List<Ssjl> ssjls = ServiceFactory.getStudentServiceInstance().getSsjls(p, student.getNumber());
			json.put("status", 200);
			json.put("count", all);
			json.put("content", ssjls);
		}
		PrintWriter out = response.getWriter();
		out.print(json);
		out.close();
	}

}
