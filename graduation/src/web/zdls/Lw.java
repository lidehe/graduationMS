package web.zdls;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Jsgx;
import domain.Student;
import domain.Teacher;
import domain.Year;
import factory.ServiceFactory;

/**
 * Servlet implementation class Lw
 */
@WebServlet("/lw.do")
public class Lw extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Lw() {
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
			method = "detail";
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
		case "detail":
			detailLw(request, response);
			break;
		case "preview":
			String xuehao = request.getParameter("number");
			if (xuehao == null) {
				response.sendRedirect("tlwcg.do");
				return;
			}
			Student student = ServiceFactory.getStudentServiceInstance().getStudentByAccount(xuehao);
			if (student != null && student.getTeacher().equals(teacher.getNumber())) {
				request.getRequestDispatcher("zdls/working/lwPreview.jsp").forward(request, response);
			}
			break;
		case "load":
			loadLw(request, response);
			break;
		default:
			break;
		}
	}
	
	protected void loadLw(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		String xuehao = request.getParameter("number");
		if (xuehao == null) {
			response.sendRedirect("tlwcg.do");
			return;
		}
		Student student = ServiceFactory.getStudentServiceInstance().getStudentByAccount(xuehao);
		if (student != null && student.getTeacher().equals(teacher.getNumber())) {
			domain.Lw lw = ServiceFactory.getStudentServiceInstance().getStudentLw(student);
			response.setContentType("application/pdf");
			Year year = ServiceFactory.getYearServiceInstance().getNowYear();
			request.getRequestDispatcher("WEB-INF/upload/" + year.getYear() + "/student/lw/" + lw.getId() + ".pdf").forward(request, response);
		}
	}

	protected void detailLw(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		String xuehao = request.getParameter("number");
		if (xuehao == null) {
			response.sendRedirect("tlwcg.do");
			return;
		}
		Student student = ServiceFactory.getStudentServiceInstance().getStudentByAccount(xuehao);
		if (student != null && student.getTeacher().equals(teacher.getNumber())) {
			request.getRequestDispatcher("zdls/working/lwDetail.jsp").forward(request, response);
		}
	}
	
}
