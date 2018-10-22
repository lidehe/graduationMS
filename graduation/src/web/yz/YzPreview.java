package web.yz;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Gzzj;
import domain.Jdxcg;
import domain.Jsgx;
import domain.Ktbg;
import domain.Lw;
import domain.Rws;
import domain.Student;
import domain.Teacher;
import factory.ServiceFactory;

/**
 * Servlet implementation class YzPreview
 */
@WebServlet("/yzp.do")
public class YzPreview extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public YzPreview() {
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
			if (gx.getJs() == 4) {
				js = true;
				break;
			}
		}
		if (!js) {// 身份不对，直接自动退出
			response.sendRedirect("login.do?method=logout");
			return;
		}
		String xuehao = request.getParameter("number");
		if (xuehao == null || xuehao.equals("")) {
			response.sendRedirect("yz.do");
			return;
		}
		Student student = ServiceFactory.getStudentServiceInstance().getStudentByAccount(xuehao);
		switch (method) {
		case "rwsP":
			if (validateNumber(student, teacher)) {
				response.sendRedirect("yz.do");
			} else {
				Rws rws = ServiceFactory.getStudentServiceInstance().getKtRwsByStudent(student);
				if (rws == null || rws.getStatus() == 0) {
					response.sendRedirect("yz.do");
				} else {
					request.getRequestDispatcher("yz/working/rwsPreview.jsp").forward(request, response);
				}
			}
			break;
		case "rwsL":
			if (validateNumber(student, teacher)) {
				response.sendRedirect("yz.do");
			} else {
				Rws rws = ServiceFactory.getStudentServiceInstance().getKtRwsByStudent(student);
				if (rws == null || rws.getStatus() == 0) {
					response.sendRedirect("yz.do");
				} else {
					response.setContentType("application/pdf");
					response.setHeader("Content-Disposition",
							"attachment;filename=" + URLEncoder.encode(rws.getFileName(), "UTF-8"));
					request.getRequestDispatcher(
							"WEB-INF/upload/" + student.getYear() + "/teacher/rws/" + rws.getId() + ".pdf")
							.forward(request, response);
				}
			}
			break;
		case "ktbgP":
			if (validateNumber(student, teacher)) {
				response.sendRedirect("yz.do");
			} else {
				Ktbg ktbg = ServiceFactory.getStudentServiceInstance().getStudentKtbg(student);
				if (ktbg == null || ktbg.getStatus() == 0) {
					response.sendRedirect("yz.do");
				} else {
					request.getRequestDispatcher("yz/working/ktbgPreview.jsp").forward(request, response);
				}
			}
			break;
		case "ktbgL":
			if (validateNumber(student, teacher)) {
				response.sendRedirect("yz.do");
			} else {
				Ktbg ktbg = ServiceFactory.getStudentServiceInstance().getStudentKtbg(student);
				if (ktbg == null || ktbg.getStatus() == 0) {
					response.sendRedirect("yz.do");
				} else {
					response.setContentType("application/pdf");
					response.setHeader("Content-Disposition",
							"attachment;filename=" + URLEncoder.encode(ktbg.getFileName(), "UTF-8"));
					request.getRequestDispatcher(
							"WEB-INF/upload/" + student.getYear() + "/student/ktbg/" + ktbg.getId() + ".pdf")
							.forward(request, response);
				}
			}
			break;
		case "jdxcgP":
			if (validateNumber(student, teacher)) {
				response.sendRedirect("yz.do");
			} else {
				Jdxcg jdxcg = ServiceFactory.getStudentServiceInstance().getStudentJdxcg(student);
				if (jdxcg == null || jdxcg.getStatus() == 0) {
					response.sendRedirect("yz.do");
				} else {
					request.getRequestDispatcher("yz/working/jdxcgPreview.jsp").forward(request, response);
				}
			}
			break;
		case "jdxcgL":
			if (validateNumber(student, teacher)) {
				response.sendRedirect("yz.do");
			} else {
				Jdxcg jdxcg = ServiceFactory.getStudentServiceInstance().getStudentJdxcg(student);
				if (jdxcg == null || jdxcg.getStatus() == 0) {
					response.sendRedirect("yz.do");
				} else {
					response.setContentType("application/pdf");
					response.setHeader("Content-Disposition",
							"attachment;filename=" + URLEncoder.encode(jdxcg.getFileName(), "UTF-8"));
					request.getRequestDispatcher(
							"WEB-INF/upload/" + student.getYear() + "/student/jdxcg/" + jdxcg.getId() + ".pdf")
							.forward(request, response);
				}
			}
			break;
		case "lwP":
			if (validateNumber(student, teacher)) {
				response.sendRedirect("yz.do");
			} else {
				Lw lw = ServiceFactory.getStudentServiceInstance().getStudentLw(student);
				if (lw == null || lw.getStatus() == 0) {
					response.sendRedirect("yz.do");
				} else {
					request.getRequestDispatcher("yz/working/lwPreview.jsp").forward(request, response);
				}
			}
			break;
		case "lwL":
			if (validateNumber(student, teacher)) {
				response.sendRedirect("yz.do");
			} else {
				Lw lw = ServiceFactory.getStudentServiceInstance().getStudentLw(student);
				if (lw == null || lw.getStatus() == 0) {
					response.sendRedirect("yz.do");
				} else {
					response.setContentType("application/pdf");
					response.setHeader("Content-Disposition",
							"attachment;filename=" + URLEncoder.encode(lw.getFileName(), "UTF-8"));
					request.getRequestDispatcher(
							"WEB-INF/upload/" + student.getYear() + "/student/lw/" + lw.getId() + ".pdf")
							.forward(request, response);
				}
			}
			break;
		case "gzzjP":
			Gzzj gzzj = ServiceFactory.getTeacherServiceInstance().getYxGzzj(teacher.getYx());
			if (gzzj != null && gzzj.getStatus() != 0) {
				request.getRequestDispatcher("yz/working/gzzjPreview.jsp").forward(request, response);
			} else {
				response.sendRedirect("yz.do");
			}
			break;
		case "gzzjL":
			Gzzj gzzj1 = ServiceFactory.getTeacherServiceInstance().getYxGzzj(teacher.getYx());
			if (gzzj1 != null && gzzj1.getStatus() != 0) {
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition",
						"attachment;filename=" + URLEncoder.encode(gzzj1.getFileName(), "UTF-8"));
				request.getRequestDispatcher(
						"WEB-INF/upload/" + teacher.getYear() + "/teacher/gzzj/" + gzzj1.getId() + ".pdf")
						.forward(request, response);
			} else {
				response.sendRedirect("yz.do");
			}
			break;
		default:
			response.sendRedirect("yz.do");
			break;
		}
	}

	private boolean validateNumber(Student student, Teacher teacher) {
		if (student == null)
			return true;
		if (student.getYx() != teacher.getYx())
			return true;
		return false;
	}

}
