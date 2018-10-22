package web.common;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Gzzj;
import domain.Jdxcg;
import domain.Jdxcgs;
import domain.Ktbg;
import domain.Lw;
import domain.Lwcg;
import domain.Lws;
import domain.Rws;
import domain.Student;
import domain.Teacher;
import domain.Year;
import domain.Zqxj;
import factory.ServiceFactory;
import utils.GetMime;

/**
 * Servlet implementation class Preview
 */
@WebServlet("/preview.do")
public class Preview extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Preview() {
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
			method = "";
		switch (method) {
		case "rwsP":
			rwsPreivew(request, response);
			break;
		case "rwsLoad":
			rwsLoad(request, response);
			break;
		case "ktbg":
			ktbgPreview(request, response);
			break;
		case "ktbgLoad":
			ktbgLoad(request, response);
			break;
		case "jdxcg":
			jdxcgPreview(request, response);
			break;
		case "jdxcgLoad":
			jdxcgLoad(request, response);
			break;
		case "lw":
			lwPreview(request, response);
			break;
		case "lwLoad":
			lwLoad(request, response);
			break;
		case "jdxcgs":
			jdxcgsPreview(request, response);
			break;
		case "jdxcgsLoad":
			jdxcgsLoad(request, response);
			break;
		case "lws":
			lwsPreview(request, response);
			break;
		case "lwsLoad":
			lwsLoad(request, response);
			break;
		case "lwcg":
			lwcgPreview(request, response);
			break;
		case "lwcgLoad":
			lwcgLoad(request, response);
			break;
		case "gzzjP":
			String yxString = request.getParameter("yx");
			if(yxString == null || yxString.equals("")) {
				return;
			}
			int yx = Integer.valueOf(yxString);
			Gzzj gzzj = ServiceFactory.getTeacherServiceInstance().getOneYxGzzj(yx);
			if(gzzj == null)
				return;
			request.getRequestDispatcher("preview/jsp/gzzj.jsp").forward(request, response);
			break;
		case "gzzjLoad":
			String yxString1 = request.getParameter("yx");
			if(yxString1 == null || yxString1.equals("")) {
				return;
			}
			int yx1 = Integer.valueOf(yxString1);
			Gzzj gzzj1 = ServiceFactory.getTeacherServiceInstance().getOneYxGzzj(yx1);
			if(gzzj1 == null)
				return;
			response.setContentType("application/pdf;charset=utf-8");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + URLEncoder.encode(gzzj1.getFileName(), "UTF-8"));
			request.getRequestDispatcher("WEB-INF/upload/" + gzzj1.getYear() + "/teacher/gzzj/"
					+ gzzj1.getId() + ".pdf").forward(request, response);
			break;
		case "zqxjP":
			String yxString2 = request.getParameter("yx");
			if(yxString2 == null || yxString2.equals("")) {
				return;
			}
			int yx2 = Integer.valueOf(yxString2);
			Zqxj zqxj = ServiceFactory.getTeacherServiceInstance().getOneYxZqxj(yx2);
			if(zqxj == null)
				return;
			request.getRequestDispatcher("preview/jsp/zqxj.jsp").forward(request, response);
			break;
		case "zqxjLoad":
			String yxString3 = request.getParameter("yx");
			if(yxString3 == null || yxString3.equals("")) {
				return;
			}
			int yx3= Integer.valueOf(yxString3);
			Zqxj zqxj1 = ServiceFactory.getTeacherServiceInstance().getOneYxZqxj(yx3);
			if(zqxj1 == null)
				return;
			response.setContentType("application/pdf;charset=utf-8");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + URLEncoder.encode(zqxj1.getFileName(), "UTF-8"));
			request.getRequestDispatcher("WEB-INF/upload/" + zqxj1.getYear() + "/teacher/zqxj/"
					+ zqxj1.getId() + ".pdf").forward(request, response);
			break;
		default:
			break;
		}
	}
	
	protected void lwsPreview(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idString = request.getParameter("id");
		if (idString != null && !idString.equals("")) {
			int id = Integer.valueOf(idString);
			Student student = (Student) request.getSession().getAttribute("student");
			if (student != null) {
				Lws lws = ServiceFactory.getStudentServiceInstance().getStudentLws(id);
				if (lws != null && lws.getXuehao().equals(student.getNumber()) && lws.getStatus() == 1) {
					if (!lws.getUrl().equals("pdf")) {
						response.setContentType(GetMime.returnMimeFromExt(lws.getUrl()) + ";charset=utf-8");
						response.setHeader("Content-Disposition",
								"attachment;filename=" + URLEncoder.encode(lws.getFileName(), "UTF-8"));
						request.getRequestDispatcher("WEB-INF/upload/" + student.getYear() + "/student/lws/"
								+ lws.getId() + "." + lws.getUrl()).forward(request, response);
					} else {
						request.getRequestDispatcher("preview/jsp/lws.jsp").forward(request, response);
					}
				}
			} else {
				Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
				if (teacher != null) {
					Lws lws = ServiceFactory.getStudentServiceInstance().getStudentLws(id);
					Student student2 = ServiceFactory.getStudentServiceInstance()
							.getStudentByAccount(lws.getXuehao());
					if (lws != null && lws.getStatus() == 1
							&& student2.getTeacher().equals(teacher.getNumber())) {
						if (!lws.getUrl().equals("pdf")) {
							response.setContentType(GetMime.returnMimeFromExt(lws.getUrl()) + ";charset=utf-8");
							response.setHeader("Content-Disposition",
									"attachment;filename=" + URLEncoder.encode(lws.getFileName(), "UTF-8"));
							request.getRequestDispatcher("WEB-INF/upload/" + student2.getYear() + "/student/lws/"
									+ lws.getId() + "." + lws.getUrl()).forward(request, response);
						} else {
							request.getRequestDispatcher("preview/jsp/lws.jsp").forward(request, response);
						}
					}
				}
			}
		} else {
			response.sendRedirect("login.do?method=logout");
		}
	}

	protected void jdxcgsPreview(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idString = request.getParameter("id");
		if (idString != null && !idString.equals("")) {
			int id = Integer.valueOf(idString);
			Student student = (Student) request.getSession().getAttribute("student");
			if (student != null) {
				Jdxcgs jdxcgs = ServiceFactory.getStudentServiceInstance().getJdxcgsById(id);
				if (jdxcgs != null && jdxcgs.getXuehao().equals(student.getNumber()) && jdxcgs.getStatus() == 1) {
					if (!jdxcgs.getUrl().equals("pdf")) {
						response.setContentType(GetMime.returnMimeFromExt(jdxcgs.getUrl()) + ";charset=utf-8");
						response.setHeader("Content-Disposition",
								"attachment;filename=" + URLEncoder.encode(jdxcgs.getFileName(), "UTF-8"));
						request.getRequestDispatcher("WEB-INF/upload/" + student.getYear() + "/student/jdxcgs/"
								+ jdxcgs.getId() + "." + jdxcgs.getUrl()).forward(request, response);
					} else {
						request.getRequestDispatcher("preview/jsp/jdxcgs.jsp").forward(request, response);
					}
				}
			} else {
				Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
				if (teacher != null) {
					Jdxcgs jdxcgs = ServiceFactory.getStudentServiceInstance().getJdxcgsById(id);
					Student student2 = ServiceFactory.getStudentServiceInstance()
							.getStudentByAccount(jdxcgs.getXuehao());
					if (jdxcgs != null && jdxcgs.getStatus() == 1
							&& student2.getTeacher().equals(teacher.getNumber())) {
						if (!jdxcgs.getUrl().equals("pdf")) {
							response.setContentType(GetMime.returnMimeFromExt(jdxcgs.getUrl()) + ";charset=utf-8");
							response.setHeader("Content-Disposition",
									"attachment;filename=" + URLEncoder.encode(jdxcgs.getFileName(), "UTF-8"));
							request.getRequestDispatcher("WEB-INF/upload/" + student2.getYear() + "/student/jdxcgs/"
									+ jdxcgs.getId() + "." + jdxcgs.getUrl()).forward(request, response);
						} else {
							request.getRequestDispatcher("preview/jsp/jdxcgs.jsp").forward(request, response);
						}
					}
				}
			}
		} else {
			response.sendRedirect("login.do?method=logout");
		}
	}

	protected void jdxcgsLoad(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idString = request.getParameter("id");
		if (idString != null && !idString.equals("")) {
			int id = Integer.valueOf(idString);
			Student student = (Student) request.getSession().getAttribute("student");
			response.setContentType("application/pdf");
			Year year = ServiceFactory.getYearServiceInstance().getNowYear();
			if (student != null) {
				Jdxcgs jdxcgs = ServiceFactory.getStudentServiceInstance().getJdxcgsById(id);
				if (jdxcgs != null && jdxcgs.getXuehao().equals(student.getNumber()) && jdxcgs.getStatus() == 1) {
					if (jdxcgs.getUrl().equals("pdf")) {
						response.setHeader("Content-Disposition",
								"attachment;filename=" + URLEncoder.encode(jdxcgs.getFileName(), "UTF-8"));
						request.getRequestDispatcher(
								"WEB-INF/upload/" + year.getYear() + "/student/jdxcgs/" + jdxcgs.getId() + ".pdf")
								.forward(request, response);
					}
				} 
			}else {
				Jdxcgs jdxcgs = ServiceFactory.getStudentServiceInstance().getJdxcgsById(id);
				Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
				if (teacher != null) {
					Student student2 = ServiceFactory.getStudentServiceInstance()
							.getStudentByAccount(jdxcgs.getXuehao());
					if (jdxcgs != null && jdxcgs.getStatus() == 1
							&& student2.getTeacher().equals(teacher.getNumber())) {
						if (jdxcgs.getUrl().equals("pdf")) {
							response.setHeader("Content-Disposition",
									"attachment;filename=" + URLEncoder.encode(jdxcgs.getFileName(), "UTF-8"));
							request.getRequestDispatcher("WEB-INF/upload/" + year.getYear() + "/student/jdxcgs/"
									+ jdxcgs.getId() + ".pdf").forward(request, response);
						}
					}
				}
			}
		} else {
			response.sendRedirect("login.do?method=logout");
		}
	}
	
	protected void lwsLoad(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idString = request.getParameter("id");
		if (idString != null && !idString.equals("")) {
			int id = Integer.valueOf(idString);
			Student student = (Student) request.getSession().getAttribute("student");
			response.setContentType("application/pdf");
			Year year = ServiceFactory.getYearServiceInstance().getNowYear();
			if (student != null) {
				Lws lws = ServiceFactory.getStudentServiceInstance().getStudentLws(id);
				if (lws != null && lws.getXuehao().equals(student.getNumber()) && lws.getStatus() == 1) {
					if (lws.getUrl().equals("pdf")) {
						response.setHeader("Content-Disposition",
								"attachment;filename=" + URLEncoder.encode(lws.getFileName(), "UTF-8"));
						request.getRequestDispatcher(
								"WEB-INF/upload/" + year.getYear() + "/student/lws/" + lws.getId() + ".pdf")
								.forward(request, response);
					}
				} 
			}else {
				Lws lws = ServiceFactory.getStudentServiceInstance().getStudentLws(id);
				Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
				if (teacher != null) {
					Student student2 = ServiceFactory.getStudentServiceInstance()
							.getStudentByAccount(lws.getXuehao());
					if (lws != null && lws.getStatus() == 1
							&& student2.getTeacher().equals(teacher.getNumber())) {
						if (lws.getUrl().equals("pdf")) {
							response.setHeader("Content-Disposition",
									"attachment;filename=" + URLEncoder.encode(lws.getFileName(), "UTF-8"));
							request.getRequestDispatcher("WEB-INF/upload/" + year.getYear() + "/student/lws/"
									+ lws.getId() + ".pdf").forward(request, response);
						}
					}
				}
			}
		} else {
			response.sendRedirect("login.do?method=logout");
		}
	}

	protected void lwPreview(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Student student = (Student) request.getSession().getAttribute("student");
		if (student != null) {
			Lw lw = ServiceFactory.getStudentServiceInstance().getStudentLw(student);
			if (lw != null && lw.getStatus() != 0 && lw.getXuehao().equals(student.getNumber())) {
				request.getRequestDispatcher("preview/jsp/lw.jsp").forward(request, response);
			}
		}
	}

	protected void lwLoad(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Student student = (Student) request.getSession().getAttribute("student");
		if (student != null) {
			Lw lw = ServiceFactory.getStudentServiceInstance().getStudentLw(student);
			if (lw != null && lw.getStatus() != 0 && lw.getXuehao().equals(student.getNumber())) {
				response.setContentType("application/pdf");
				Year year = ServiceFactory.getYearServiceInstance().getNowYear();
				response.setHeader("Content-Disposition",
						"attachment;filename=" + URLEncoder.encode(lw.getFileName(), "UTF-8"));
				request.getRequestDispatcher("WEB-INF/upload/" + year.getYear() + "/student/lw/" + lw.getId() + ".pdf")
						.forward(request, response);
			}
		}
	}

	protected void lwcgPreview(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Student student = (Student) request.getSession().getAttribute("student");
		if (student != null) {
			Lwcg lwcg = ServiceFactory.getStudentServiceInstance().getStudentLwcg(student);
			if (lwcg != null && lwcg.getStatus() != 0 && lwcg.getXuehao().equals(student.getNumber())) {
				request.getRequestDispatcher("preview/jsp/lwcg.jsp").forward(request, response);
			}
		}
	}

	protected void lwcgLoad(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Student student = (Student) request.getSession().getAttribute("student");
		if (student != null) {
			Lwcg lwcg = ServiceFactory.getStudentServiceInstance().getStudentLwcg(student);
			if (lwcg != null && lwcg.getStatus() != 0 && lwcg.getXuehao().equals(student.getNumber())) {
				response.setContentType("application/pdf");
				Year year = ServiceFactory.getYearServiceInstance().getNowYear();
				response.setHeader("Content-Disposition",
						"attachment;filename=" + URLEncoder.encode(lwcg.getFileName(), "UTF-8"));
				request.getRequestDispatcher(
						"WEB-INF/upload/" + year.getYear() + "/student/lwcg/" + lwcg.getId() + ".pdf")
						.forward(request, response);
			}
		}
	}

	protected void jdxcgPreview(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Student student = (Student) request.getSession().getAttribute("student");
		if (student != null) {
			Jdxcg jdxcg = ServiceFactory.getStudentServiceInstance().getStudentJdxcg(student);
			if (jdxcg != null && jdxcg.getStatus() == 1 && jdxcg.getXuehao().equals(student.getNumber())) {
				request.getRequestDispatcher("preview/jsp/jdxcg.jsp").forward(request, response);
			}
		}
	}

	protected void jdxcgLoad(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Student student = (Student) request.getSession().getAttribute("student");
		if (student != null) {
			Jdxcg jdxcg = ServiceFactory.getStudentServiceInstance().getStudentJdxcg(student);
			if (jdxcg != null && jdxcg.getStatus() == 1 && jdxcg.getXuehao().equals(student.getNumber())) {
				response.setContentType("application/pdf");
				Year year = ServiceFactory.getYearServiceInstance().getNowYear();
				response.setHeader("Content-Disposition",
						"attachment;filename=" + URLEncoder.encode(jdxcg.getFileName(), "UTF-8"));
				request.getRequestDispatcher(
						"WEB-INF/upload/" + year.getYear() + "/student/jdxcg/" + jdxcg.getId() + ".pdf")
						.forward(request, response);
			}
		}
	}

	protected void ktbgPreview(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Student student = (Student) request.getSession().getAttribute("student");
		if (student != null) {
			Ktbg ktbg = ServiceFactory.getStudentServiceInstance().getStudentKtbg(student);
			if (ktbg != null && ktbg.getStatus() == 1 && ktbg.getXuehao().equals(student.getNumber())
					&& ktbg.getStatus() == 1) {
				request.getRequestDispatcher("preview/jsp/ktbg.jsp").forward(request, response);
			}
		}
	}

	protected void ktbgLoad(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Student student = (Student) request.getSession().getAttribute("student");
		if (student != null) {
			Ktbg ktbg = ServiceFactory.getStudentServiceInstance().getStudentKtbg(student);
			if (ktbg != null && ktbg.getStatus() == 1 && ktbg.getXuehao().equals(student.getNumber())
					&& ktbg.getStatus() == 1) {
				response.setContentType("application/pdf");
				Year year = ServiceFactory.getYearServiceInstance().getNowYear();
				response.setHeader("Content-Disposition",
						"attachment;filename=" + URLEncoder.encode(ktbg.getFileName(), "UTF-8"));
				request.getRequestDispatcher(
						"WEB-INF/upload/" + year.getYear() + "/student/ktbg/" + ktbg.getId() + ".pdf")
						.forward(request, response);
			}
		}
	}

	protected void rwsPreivew(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idString = request.getParameter("id");
		if (idString != null && !idString.equals("")) {
			int id = Integer.valueOf(idString);
			Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
			if (teacher != null) {
				Rws rws = ServiceFactory.getTeacherServiceInstance().teacherGetRwsById(id);
				if (rws != null && rws.getGonghao().equals(teacher.getNumber()) && rws.getStatus() == 1) {
					request.getRequestDispatcher("preview/jsp/rws.jsp").forward(request, response);
				}
			}
		}
	}

	protected void rwsLoad(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/pdf");
		String idString = request.getParameter("id");
		if (idString != null && !idString.equals("")) {
			int id = Integer.valueOf(idString);
			Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
			if (teacher != null) {
				Rws rws = ServiceFactory.getTeacherServiceInstance().teacherGetRwsById(id);
				if (rws != null && rws.getGonghao().equals(teacher.getNumber()) && rws.getStatus() == 1) {
					Year year = ServiceFactory.getYearServiceInstance().getNowYear();
					response.setHeader("Content-Disposition",
							"attachment;filename=" + URLEncoder.encode(rws.getFileName(), "UTF-8"));
					request.getRequestDispatcher("WEB-INF/upload/" + year.getYear() + "/teacher/rws/" + rws.getId() + ".pdf")
							.forward(request, response);
				}
			}
		}
	}

}
