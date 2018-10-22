package web.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Account;
import domain.Student;
import domain.Teacher;
import factory.ServiceFactory;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login.do")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
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
		String method = request.getParameter("method");
		if (method != null) {
			if (method.equals("logout")) {
				request.getSession().invalidate();
				response.sendRedirect("index.html");
				return;
			} else if (method.equals("change")) {
				response.setContentType("appliction/json;charset=utf-8");
				PrintWriter out = response.getWriter();
				JSONObject json = new JSONObject();
				String oldP = request.getParameter("oldP");
				String newP = request.getParameter("newP");
				Student student = (Student) request.getSession().getAttribute("student");
				if (student != null) {
					if (oldP == null || oldP.equals("") || newP == null || newP.equals("")) {
						json.put("status", 500);
						json.put("msg", "非法密码！");
					} else {
						Account account = ServiceFactory.getAccountServiceInstance()
								.getAccountBYNumber(student.getNumber());
						if (account != null && account.getPassword().equals(oldP)) {
							account.setPassword(newP);
							ServiceFactory.getAccountServiceInstance().updateAccount(account);
							json.put("status", 200);
							json.put("msg", "修改成功！");
						} else {
							json.put("status", 500);
							json.put("msg", "原密码有错！");
						}
					}
				} else {
					Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
					if (teacher != null) {
						if (oldP == null || oldP.equals("") || newP == null || newP.equals("")) {
							json.put("status", 500);
							json.put("msg", "非法密码！");
						} else {
							Account account = ServiceFactory.getAccountServiceInstance()
									.getAccountBYNumber(teacher.getNumber());
							if (account != null && account.getPassword().equals(oldP)) {
								account.setPassword(newP);
								ServiceFactory.getAccountServiceInstance().updateAccount(account);
								json.put("status", 200);
								json.put("msg", "修改成功！");
							} else {
								json.put("status", 500);
								json.put("msg", "原密码有错！");
							}
						}
					}
				}
				out.print(json);
				out.close();
				return;
			}
		} else {
			String account = request.getParameter("account");
			String password = request.getParameter("password");
			int result = ServiceFactory.getAccountServiceInstance().login(account, password);
			JSONObject json = new JSONObject();
			json.put("status", result);
			String url = "";
			if (result == 1) {// 学生登录成功
				Student student = ServiceFactory.getStudentServiceInstance().getStudentByAccount(account);

				// 接下里要处理学生小组是否为一个人的情况了
				if (student.getIsgroup() != 0) {
					List<Student> groups = ServiceFactory.getStudentServiceInstance()
							.getGroupStudents(student.getNumber());
					if (groups.size() == 1) {
						ServiceFactory.getStudentServiceInstance().deleteGroup(student.getNumber());
						student.setIsgroup(0);
						ServiceFactory.getStudentServiceInstance().updateStudent(student);
					}
				}
				request.getSession().setAttribute("student", student);
				request.getSession().setAttribute("teacher", null);
				// Teacher teacher =
				// ServiceFactory.getTeacherServiceInstance().getTeacherByAccountYear(student.getTeacher(),
				// student.getYear());
				// request.getSession().setAttribute("teacher", teacher);
				// json.put("url", "home.do");
				url = "student.do";
			} else if (result == 0) {// 老师登录成功
				Teacher teacher = ServiceFactory.getTeacherServiceInstance().getTeacherByAccount(account);
				request.getSession().setAttribute("teacher", teacher);
				request.getSession().setAttribute("student", null);
				// json.put("url", "home.do");
				url = "teacherDirect.do";
			} else if (result == 5) {// 教务处管理员
				request.getSession().setAttribute("admin", "admin");
				url = "admin.do";
			}
			String url1 = "";
			if ((result == 1 || result == 5 || result == 0) && request.getSession().getAttribute("url") != null) {
				url1 = request.getSession().getAttribute("url").toString();
				json.put("url1", url1);
			}
			json.put("url", url);
			response.setContentType("application/json; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(json);
			out.close();
		}
	}

}
