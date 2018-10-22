package web.cjry;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Cjbd;
import domain.Cjk;
import domain.Jdxcg;
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
 * Servlet implementation class CjPreview
 */
@WebServlet("/cjp.do")
public class CjPreview extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CjPreview() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String method = request.getParameter("meth");
		if (method == null){
			response.sendRedirect("cjry.do");
			return ;
		}
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		List<Jsgx> gxs = ServiceFactory.getTeacherServiceInstance().getTeacherJses(teacher);
		boolean js = false;
		for (Jsgx gx : gxs) {
			if (gx.getJs() == 7) {
				js = true;
				break;
			}
		}
		if (!js) {// 身份不对，直接自动退出
			response.sendRedirect("login.do?method=logout");
			return;
		}
		Student student = validateNumber(request);
		switch (method) {
		case "rwsP":
			if(student == null){
				response.sendRedirect("cjry.do");
			}else{
				if(validateBD(student, teacher)) {
					Rws rws = ServiceFactory.getStudentServiceInstance().getKtRwsByStudent(student);
					if(rws == null || rws.getStatus() == 0){
						response.sendRedirect("cjry.do");
					}else{
						request.getRequestDispatcher("cjry/working/rwsPreview.jsp").forward(request, response);
					}
				}else
					response.sendRedirect("cjry.do");
			}
			break;
		case "rwsL":
			if(student == null){
				response.sendRedirect("cjry.do");
			}else{
				if(validateBD(student, teacher)) {
					Rws rws = ServiceFactory.getStudentServiceInstance().getKtRwsByStudent(student);
					if(rws == null || rws.getStatus() == 0){
						response.sendRedirect("cjry.do");
					}else{
						response.setContentType("application/pdf");
						response.setHeader("Content-Disposition",
								"attachment;filename=" + URLEncoder.encode(rws.getFileName(), "UTF-8"));
						request.getRequestDispatcher("WEB-INF/upload/" + student.getYear() + "/teacher/rws/" + rws.getId() + ".pdf").forward(request, response);
					}
				}else
					response.sendRedirect("cjry.do");
			}
			break;
		case "ktbgP":
			if(student == null){
				response.sendRedirect("cjry.do");
			}else{
				if(validateBD(student, teacher)) {
					Ktbg ktbg = ServiceFactory.getStudentServiceInstance().getStudentKtbg(student);
					if(ktbg == null || ktbg.getStatus() == 0){
						response.sendRedirect("cjry.do");
					}else{
						request.getRequestDispatcher("cjry/working/ktbgPreview.jsp").forward(request, response);
					}
				}else
					response.sendRedirect("cjry.do");
			}
			break;
		case "ktbgL":
			if(student == null){
				response.sendRedirect("cjry.do");
			}else{
				if(validateBD(student, teacher)) {
					Ktbg ktbg = ServiceFactory.getStudentServiceInstance().getStudentKtbg(student);
					if(ktbg == null || ktbg.getStatus() == 0){
						response.sendRedirect("cjry.do");
					}else{
						response.setContentType("application/pdf");
						response.setHeader("Content-Disposition",
								"attachment;filename=" + URLEncoder.encode(ktbg.getFileName(), "UTF-8"));
						request.getRequestDispatcher("WEB-INF/upload/" + student.getYear() + "/student/ktbg/" + ktbg.getId() + ".pdf").forward(request, response);
					}
				}else
					response.sendRedirect("cjry.do");
			}
			break;
		case "jdxcgP":
			if(student == null){
				response.sendRedirect("cjry.do");
			}else{
				if(validateBD(student, teacher)) {
					Jdxcg jdxcg = ServiceFactory.getStudentServiceInstance().getStudentJdxcg(student);
					if(jdxcg == null || jdxcg.getStatus() == 0){
						response.sendRedirect("cjry.do");
					}else{
						request.getRequestDispatcher("cjry/working/jdxcgPreview.jsp").forward(request, response);
					}
				}else
					response.sendRedirect("cjry.do");
			}
			break;
		case "jdxcgL":
			if(student == null){
				response.sendRedirect("cjry.do");
			}else{
				if(validateBD(student, teacher)) {
					Jdxcg jdxcg = ServiceFactory.getStudentServiceInstance().getStudentJdxcg(student);
					if(jdxcg == null || jdxcg.getStatus() == 0){
						response.sendRedirect("cjry.do");
					}else{
						response.setContentType("application/pdf");
						response.setHeader("Content-Disposition",
								"attachment;filename=" + URLEncoder.encode(jdxcg.getFileName(), "UTF-8"));
						request.getRequestDispatcher("WEB-INF/upload/" + student.getYear() + "/student/jdxcg/" + jdxcg.getId() + ".pdf").forward(request, response);
					}
				}else
					response.sendRedirect("cjry.do");
			}
			break;
		case "lwsP":
				int id = Integer.valueOf(request.getParameter("id") == null ? "0" :request.getParameter("id"));
				Lws lws1 = ServiceFactory.getStudentServiceInstance().getStudentLws(id);
				if(lws1 == null || lws1.getStatus() == 0){
					response.sendRedirect("cjry.do");
				}else{
					Student student2 = ServiceFactory.getStudentServiceInstance().getStudentByAccount(lws1.getXuehao());
					if(validateBD(student2, teacher)) {
						if(lws1.getUrl().equals("pdf"))
							request.getRequestDispatcher("cjry/working/lwsPreview.jsp").forward(request, response);
						else {
							response.setContentType(GetMime.returnMimeFromExt(lws1.getUrl())+";charset=utf-8");
							response.setHeader("Content-Disposition",
									"attachment;filename=" + URLEncoder.encode(lws1.getFileName(), "UTF-8"));
							request.getRequestDispatcher("WEB-INF/upload/" + student.getYear() + "/student/lws/"
									+ lws1.getId() + "." + lws1.getUrl()).forward(request, response);
						}
					}else {
						response.sendRedirect("cjry.do");
					}
				}
			break;
		case "lwsL":
				int id1 = Integer.valueOf(request.getParameter("id") == null ? "0" :request.getParameter("id"));
				Lws lws = ServiceFactory.getStudentServiceInstance().getStudentLws(id1);
				if(lws == null || lws.getStatus() == 0){
					response.sendRedirect("cjry.do");
				}else{
					Student student3 = ServiceFactory.getStudentServiceInstance().getStudentByAccount(lws.getXuehao());
					if(validateBD(student3, teacher) && lws.getUrl().equals("pdf")) {
						response.setContentType("application/pdf");
						response.setHeader("Content-Disposition",
								"attachment;filename=" + URLEncoder.encode(lws.getFileName(), "UTF-8"));
						request.getRequestDispatcher("WEB-INF/upload/" + student.getYear() + "/student/lws/" + lws.getId() + ".pdf").forward(request, response);
					}else
						response.sendRedirect("cjry.do");
				}
			break;
		case "lwP":
			if(student == null){
				response.sendRedirect("cjry.do");
			}else{
				Lw lw = ServiceFactory.getStudentServiceInstance().getStudentLw(student);
				if(lw == null || lw.getStatus() == 0){
					response.sendRedirect("cjry.do");
				}else{
					request.getRequestDispatcher("cjry/working/lwPreview.jsp").forward(request, response);
				}
			}
			break;
		case "lwL":
			if(student == null){
				response.sendRedirect("cjry.do");
			}else{
				Lw lw = ServiceFactory.getStudentServiceInstance().getStudentLw(student);
				if(lw == null || lw.getStatus() == 0){
					response.sendRedirect("cjry.do");
				}else{
					response.setContentType("application/pdf");
					response.setHeader("Content-Disposition",
							"attachment;filename=" + URLEncoder.encode(lw.getFileName(), "UTF-8"));
					request.getRequestDispatcher("WEB-INF/upload/" + student.getYear() + "/student/lw/" + lw.getId() + ".pdf").forward(request, response);
				}
			}
			break;
		default:
			response.sendRedirect("cjry.do");
			break;
		}
	}
	
	private Student validateNumber(HttpServletRequest request)
	{
		String xuehao = request.getParameter("number");
		if(xuehao == null || xuehao.equals(""))
			return null;
		Student student = ServiceFactory.getStudentServiceInstance().getStudentByAccount(xuehao);
		
		return student;
	}
	
	private boolean validateBD(Student student, Teacher teacher) {
		if (student == null) {
			return false;
		} else {
			Cjk cjObject = ServiceFactory.getTeacherServiceInstance().getOneCjObject(student.getNumber());
			if(cjObject == null)
				return false;
			List<Cjbd> bds = ServiceFactory.getTeacherServiceInstance().getOneTeacherCjBd(teacher);
			boolean r = false;
			for (Cjbd bd : bds) {
				if (bd.getYx() == student.getYx())
					r = true;
			}
			if (r)
				return true;
			else
				return false;
		}
	}

}
