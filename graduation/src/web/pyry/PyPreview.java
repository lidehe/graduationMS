package web.pyry;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Jdxcg;
import domain.Jsgx;
import domain.Ktbg;
import domain.Lw;
import domain.Lws;
import domain.Pybd;
import domain.Rws;
import domain.Student;
import domain.Teacher;
import domain.Yxlw;
import domain.Yxlwz;
import factory.ServiceFactory;
import utils.GetMime;

/**
 * Servlet implementation class PyPreview
 */
@WebServlet("/pyp.do")
public class PyPreview extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PyPreview() {
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
			if (gx.getJs() == 6 || gx.getJs() == 10) {
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
			response.sendRedirect("pyry.do");
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
			if (validateNumber(student)) {
				response.sendRedirect("pyry.do");
			} else {
				if (validateBD(student, teacher)) {
					Rws rws = ServiceFactory.getStudentServiceInstance().getKtRwsByStudent(student);
					if (rws == null || rws.getStatus() == 0) {
						response.sendRedirect("pyry.do");
					} else {
						request.getRequestDispatcher("pyry/working/rwsPreview.jsp").forward(request, response);
					}
				} else {
					response.sendRedirect("pyry.do");
				}
			}
			break;
		case "rwsL":
			if (validateNumber(student)) {
				response.sendRedirect("pyry.do");
			} else {
				if (validateBD(student, teacher)) {
					Rws rws = ServiceFactory.getStudentServiceInstance().getKtRwsByStudent(student);
					if (rws == null || rws.getStatus() == 0) {
						response.sendRedirect("pyry.do");
					} else {
						response.setContentType("application/pdf");
						response.setHeader("Content-Disposition",
								"attachment;filename=" + URLEncoder.encode(rws.getFileName(), "UTF-8"));
						request.getRequestDispatcher(
								"WEB-INF/upload/" + student.getYear() + "/teacher/rws/" + rws.getId() + ".pdf")
								.forward(request, response);
					}
				} else {
					response.sendRedirect("pyry.do");
				}
			}
			break;
		case "ktbgP":
			if (validateNumber(student)) {
				response.sendRedirect("pyry.do");
			} else {
				if (validateBD(student, teacher)) {
					Ktbg ktbg = ServiceFactory.getStudentServiceInstance().getStudentKtbg(student);
					if (ktbg == null || ktbg.getStatus() == 0) {
						response.sendRedirect("pyry.do");
					} else {
						request.getRequestDispatcher("pyry/working/ktbgPreview.jsp").forward(request, response);
					}
				} else {
					response.sendRedirect("pyry.do");
				}
			}
			break;
		case "ktbgL":
			if (validateNumber(student)) {
				response.sendRedirect("pyry.do");
			} else {
				if (validateBD(student, teacher)) {
					Ktbg ktbg = ServiceFactory.getStudentServiceInstance().getStudentKtbg(student);
					if (ktbg == null || ktbg.getStatus() == 0) {
						response.sendRedirect("pyry.do");
					} else {
						response.setContentType("application/pdf");
						response.setHeader("Content-Disposition",
								"attachment;filename=" + URLEncoder.encode(ktbg.getFileName(), "UTF-8"));
						request.getRequestDispatcher(
								"WEB-INF/upload/" + student.getYear() + "/student/ktbg/" + ktbg.getId() + ".pdf")
								.forward(request, response);
					}
				} else {
					response.sendRedirect("pyry.do");
				}
			}
			break;
		case "jdxcgP":
			if (validateNumber(student)) {
				response.sendRedirect("pyry.do");
			} else {
				if (validateBD(student, teacher)) {
					Jdxcg jdxcg = ServiceFactory.getStudentServiceInstance().getStudentJdxcg(student);
					if (jdxcg == null || jdxcg.getStatus() == 0) {
						response.sendRedirect("pyry.do");
					} else {
						request.getRequestDispatcher("pyry/working/jdxcgPreview.jsp").forward(request, response);
					}
				} else {
					response.sendRedirect("pyry.do");
				}
			}
			break;
		case "jdxcgL":
			if (validateNumber(student)) {
				response.sendRedirect("pyry.do");
			} else {
				if (validateBD(student, teacher)) {
					Jdxcg jdxcg = ServiceFactory.getStudentServiceInstance().getStudentJdxcg(student);
					if (jdxcg == null || jdxcg.getStatus() == 0) {
						response.sendRedirect("pyry.do");
					} else {
						response.setContentType("application/pdf");
						response.setHeader("Content-Disposition",
								"attachment;filename=" + URLEncoder.encode(jdxcg.getFileName(), "UTF-8"));
						request.getRequestDispatcher(
								"WEB-INF/upload/" + student.getYear() + "/student/jdxcg/" + jdxcg.getId() + ".pdf")
								.forward(request, response);
					}
				} else {
					response.sendRedirect("pyry.do");
				}
			}
			break;
		case "lwP":
			if (validateNumber(student)) {
				response.sendRedirect("pyry.do");
			} else {
				if (validateBD(student, teacher)) {
					Lw lw = ServiceFactory.getStudentServiceInstance().getStudentLw(student);
					if (lw == null || lw.getStatus() == 0) {
						response.sendRedirect("pyry.do");
					} else {
						request.getRequestDispatcher("pyry/working/lwPreview.jsp").forward(request, response);
					}
				} else {
					response.sendRedirect("pyry.do");
				}
			}
			break;
		case "lwL":
			if (validateNumber(student)) {
				response.sendRedirect("pyry.do");
			} else {
				if (validateBD(student, teacher)) {
					Lw lw = ServiceFactory.getStudentServiceInstance().getStudentLw(student);
					if (lw == null || lw.getStatus() == 0) {
						response.sendRedirect("pyry.do");
					} else {
						response.setContentType("application/pdf");
						response.setHeader("Content-Disposition",
								"attachment;filename=" + URLEncoder.encode(lw.getFileName(), "UTF-8"));
						request.getRequestDispatcher(
								"WEB-INF/upload/" + student.getYear() + "/student/lw/" + lw.getId() + ".pdf")
								.forward(request, response);
					}
				} else {
					response.sendRedirect("pyry.do");
				}
			}
			break;
		case "lwsP":
			int id = Integer.valueOf(request.getParameter("id") == null ? "0" : request.getParameter("id"));
			Lws lws1 = ServiceFactory.getStudentServiceInstance().getStudentLws(id);
			if (lws1 == null || lws1.getStatus() == 0) {
				response.sendRedirect("pyry.do");
			} else {
				Student student2 = ServiceFactory.getStudentServiceInstance().getStudentByAccount(lws1.getXuehao());
				if (validateBD(student2, teacher)) {
					if (lws1.getUrl().equals("pdf"))
						request.getRequestDispatcher("pyry/working/lwsPreview.jsp").forward(request, response);
					else {
						response.setContentType(GetMime.returnMimeFromExt(lws1.getUrl()) + ";charset=utf-8");
						response.setHeader("Content-Disposition",
								"attachment;filename=" + URLEncoder.encode(lws1.getFileName(), "UTF-8"));
						request.getRequestDispatcher("WEB-INF/upload/" + student.getYear() + "/student/lws/"
								+ lws1.getId() + "." + lws1.getUrl()).forward(request, response);
					}
				} else {
					response.sendRedirect("pyry.do");
				}
			}
			break;
		case "lwsL":
			int id1 = Integer.valueOf(request.getParameter("id") == null ? "0" : request.getParameter("id"));
			Lws lws = ServiceFactory.getStudentServiceInstance().getStudentLws(id1);
			if (lws == null || lws.getStatus() == 0) {
				response.sendRedirect("pyry.do");
			} else {
				Student student3 = ServiceFactory.getStudentServiceInstance().getStudentByAccount(lws.getXuehao());
				if (validateBD(student3, teacher) && lws.getUrl().equals("pdf")) {
					response.setContentType("application/pdf");
					response.setHeader("Content-Disposition",
							"attachment;filename=" + URLEncoder.encode(lws.getFileName(), "UTF-8"));
					request.getRequestDispatcher(
							"WEB-INF/upload/" + student.getYear() + "/student/lws/" + lws.getId() + ".pdf")
							.forward(request, response);
				} else
					response.sendRedirect("pyry.do");
			}
			break;
		default:
			response.sendRedirect("pyry.do");
			break;
		}
	}

	private boolean validateNumber(Student student) {
		if (student == null)
			return true;
		Yxlw yxlw = ServiceFactory.getTeacherServiceInstance().checkStudent(student.getNumber());
		if (yxlw == null && student.getIsgroup() == 0)
			return true;
		return false;
	}

	private boolean validateBD(Student student, Teacher teacher) {
		if (student == null) {
			return false;
		} else {
			Yxlw yxlw = ServiceFactory.getTeacherServiceInstance().checkStudent(student.getNumber());
			int zuhao = 0;
			if (student.getIsgroup() == 1) {
				zuhao = ServiceFactory.getStudentServiceInstance().getStudentZuhao(student);
			}
			Yxlwz yxlwz = ServiceFactory.getTeacherServiceInstance().getXiaoYxlwzByZh(zuhao);
			if (yxlw == null && yxlwz == null)
				return false;
			List<Pybd> bds = ServiceFactory.getTeacherServiceInstance().getOneTeacherPyBd(teacher);
			boolean r = false;
			for (Pybd bd : bds) {
				if (bd.getYx() == student.getYx())
					r = true;
			}
			if (student.getYx() == teacher.getYx())
				return true;
			if (r)
				return true;
			if (!r && yxlwz == null)
				return false;
			for (Pybd bd : bds) {
				if (bd.getYx() == yxlwz.getYx())
					r = true;
			}
			if (r)
				return true;
			else
				return false;
		}
	}
}
