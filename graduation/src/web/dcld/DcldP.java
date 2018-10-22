package web.dcld;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Jsgx;
import domain.Ktbg;
import domain.Lw;
import domain.Lws;
import domain.Rws;
import domain.Student;
import domain.Teacher;
import factory.ServiceFactory;
import utils.GetMime;

/**
 * Servlet implementation class DcldP
 */
@WebServlet("/dcldp.do")
public class DcldP extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DcldP() {
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
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		List<Jsgx> gxs = ServiceFactory.getTeacherServiceInstance().getTeacherJses(teacher);
		boolean js = false;
		for (Jsgx gx : gxs) {
			if (gx.getJs() == 9 || gx.getJs() == 5) {
				js = true;
				break;
			}
		}
		if (!js) {// 身份不对，直接自动退出
			response.sendRedirect("login.do?method=logout");
			return;
		}
		String xuehao = request.getParameter("number");
		if ((xuehao == null || xuehao.equals("")) && !method.equals("lwsP") && !method.equals("lwsL")) {
			response.sendRedirect("dcld.do");
			return;
		}
		Student student;
		if (xuehao != null && !xuehao.equals("")) {
			student = ServiceFactory.getStudentServiceInstance().getStudentByAccount(xuehao);
		} else {
			student = new Student();
		}
		switch (method) {
		case "rwsP":
			Rws rws = ServiceFactory.getStudentServiceInstance().getKtRwsByStudent(student);
			if (rws == null || rws.getStatus() == 0) {
				response.sendRedirect("dcld.do");
			} else {
				request.getRequestDispatcher("dcld/rwsPreview.jsp").forward(request, response);
			}
			break;
		case "rwsL":
			Rws rws1 = ServiceFactory.getStudentServiceInstance().getKtRwsByStudent(student);
			if (rws1 == null || rws1.getStatus() == 0) {
				response.sendRedirect("dcld.do");
			} else {
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition",
						"attachment;filename=" + URLEncoder.encode(rws1.getFileName(), "UTF-8"));
				request.getRequestDispatcher(
						"WEB-INF/upload/" + student.getYear() + "/teacher/rws/" + rws1.getId() + ".pdf")
						.forward(request, response);
			}
			break;
		case "ktbgP":
			Ktbg ktbg = ServiceFactory.getStudentServiceInstance().getStudentKtbg(student);
			if (ktbg == null || ktbg.getStatus() == 0) {
				response.sendRedirect("dcld.do");
			} else {
				request.getRequestDispatcher("dcld/ktbgPreview.jsp").forward(request, response);
			}
			break;
		case "ktbgL":
			Ktbg ktbg1 = ServiceFactory.getStudentServiceInstance().getStudentKtbg(student);
			if (ktbg1 == null || ktbg1.getStatus() == 0) {
				response.sendRedirect("dcld.do");
			} else {
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition",
						"attachment;filename=" + URLEncoder.encode(ktbg1.getFileName(), "UTF-8"));
				request.getRequestDispatcher(
						"WEB-INF/upload/" + student.getYear() + "/student/ktbg/" + ktbg1.getId() + ".pdf")
						.forward(request, response);
			}
			break;
		case "lwP":
			Lw lw = ServiceFactory.getStudentServiceInstance().getStudentLw(student);
			if (lw == null || lw.getStatus() == 0) {
				response.sendRedirect("dcld.do");
			} else {
				request.getRequestDispatcher("dcld/lwPreview.jsp").forward(request, response);
			}
			break;
		case "lwL":
			Lw lw1 = ServiceFactory.getStudentServiceInstance().getStudentLw(student);
			if (lw1 == null || lw1.getStatus() == 0) {
				response.sendRedirect("dcld.do");
			} else {
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition",
						"attachment;filename=" + URLEncoder.encode(lw1.getFileName(), "UTF-8"));
				request.getRequestDispatcher(
						"WEB-INF/upload/" + student.getYear() + "/student/lw/" + lw1.getId() + ".pdf")
						.forward(request, response);
			}
			break;
		case "lwsP":
			int id = Integer.valueOf(request.getParameter("id") == null ? "0" : request.getParameter("id"));
			Lws lws1 = ServiceFactory.getStudentServiceInstance().getStudentLws(id);
			if (lws1 == null || lws1.getStatus() == 0) {
				response.sendRedirect("dcld.do");
			} else {
				if (lws1.getUrl().equals("pdf"))
					request.getRequestDispatcher("dcld/lwsPreview.jsp").forward(request, response);
				else {
					response.setContentType(GetMime.returnMimeFromExt(lws1.getUrl()) + ";charset=utf-8");
					response.setHeader("Content-Disposition",
							"attachment;filename=" + URLEncoder.encode(lws1.getFileName(), "UTF-8"));
					request.getRequestDispatcher("WEB-INF/upload/" + student.getYear() + "/student/lws/" + lws1.getId()
							+ "." + lws1.getUrl()).forward(request, response);
				}
			}
			break;
		case "lwsL":
			int id1 = Integer.valueOf(request.getParameter("id") == null ? "0" : request.getParameter("id"));
			Lws lws = ServiceFactory.getStudentServiceInstance().getStudentLws(id1);
			if (lws == null || lws.getStatus() == 0) {
				response.sendRedirect("dcld.do");
			} else {
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition",
						"attachment;filename=" + URLEncoder.encode(lws.getFileName(), "UTF-8"));
				request.getRequestDispatcher(
						"WEB-INF/upload/" + student.getYear() + "/student/lws/" + lws.getId() + ".pdf")
						.forward(request, response);
			}
			break;
		default:
			response.sendRedirect("dcld.do");
			break;
		}
	}

}
