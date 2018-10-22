package web.student;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Rws;
import domain.Student;
import factory.ServiceFactory;
import utils.SetStudentStage;

/**
 * Servlet implementation class Ktbg
 */
@WebServlet("/ktbg.do")
public class Ktbg extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Ktbg() {
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
		if (student.getStage() < 6) {
			response.sendRedirect("kt.do");
			return;
		}
		switch (method) {
		case "ind":
			request.getRequestDispatcher("student/working/ktbg.jsp").forward(request, response);
			break;
		case "preview":
			previewRws(request, response);
			break;
		case "load":
			load(request, response);
			break;
		default:
			request.getRequestDispatcher("student/working/ktbg.jsp").forward(request, response);
			break;
		}
	}

	protected void previewRws(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Student student = (Student) request.getSession().getAttribute("student");
		Rws rws = ServiceFactory.getStudentServiceInstance().getKtRwsByStudent(student);
		if (rws != null) {
			if (student.getStage() == 6) {
				int stage = SetStudentStage.setEr(student);
				student.setStage(stage);
				ServiceFactory.getStudentServiceInstance().updateStudent(student);
				// request.getSession().removeAttribute("student");
				request.getSession().setAttribute("student", student);
			}
			request.getRequestDispatcher("student/preparation/rws.jsp").forward(request, response);
		}
	}

	protected void load(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setContentType("application/pdf");
		Student student = (Student) request.getSession().getAttribute("student");
		Rws rws = ServiceFactory.getStudentServiceInstance().getKtRwsByStudent(student);
		if (rws != null && rws.getStatus() != 0) {
			if (student.getStage() == 6) {
				int stage = SetStudentStage.setEr(student);
				student.setStage(stage);
				ServiceFactory.getStudentServiceInstance().updateStudent(student);
				// request.getSession().removeAttribute("student");
				request.getSession().setAttribute("student", student);
			}
			response.setHeader("Content-Disposition",
					"attachment;filename=" + URLEncoder.encode(rws.getFileName(), "UTF-8"));
			request.getRequestDispatcher("WEB-INF/upload/" + student.getYear() + "/teacher/rws/" + rws.getId() + ".pdf")
					.forward(request, response);
		}
	}
}
