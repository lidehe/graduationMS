package web.zdls;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Byfs;
import domain.Jdxcg;
import domain.Jsgx;
import domain.Student;
import domain.Teacher;
import domain.Year;
import factory.ServiceFactory;

/**
 * Servlet implementation class TJdxcg
 */
@WebServlet("/tjdxcg.do")
public class TJdxcg extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TJdxcg() {
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
		if(method == null)
			method = "ind";
		Teacher teacher = (Teacher)request.getSession().getAttribute("teacher");
		List<Jsgx> gxs = ServiceFactory.getTeacherServiceInstance().getTeacherJses(teacher);
		boolean js = false;
		for (Jsgx gx : gxs) {
			if(gx.getJs() == 1){
				js = true;
				break;
			}
		}
		if(!js){//身份不对，直接自动退出
			response.sendRedirect("login.do?method=logout");
			return;
		}
		switch (method) {
		case "ind":
			request.getRequestDispatcher("zdls/working/tjdxcg.jsp").forward(request, response);
			break;
		case "detail":
			jdxcgDetail(request, response);
			break;
		case "preview":
			previewJdxcg(request, response);
			break;
		case "load":
			loadJdxcg(request, response);
			break;
		default:
			break;
		}
	}
	
	protected void jdxcgDetail(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int result = validateJdxcg(request, response);
		if(result == 1){
			request.getRequestDispatcher("zdls/working/jdxcgDetail.jsp").forward(request, response);
		}
		return;
	}
	
	protected void previewJdxcg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int result = validateJdxcg(request, response);
		if(result == 1){
			request.getRequestDispatcher("zdls/working/jdxcgPreview.jsp").forward(request, response);
		}
		return;
	}
	
	protected void loadJdxcg(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int result = validateJdxcg(request, response);
		if (result == 1) {
			String xuehao = request.getParameter("number");
			Student student = ServiceFactory.getStudentServiceInstance().getStudentByAccount(xuehao);
			Jdxcg jdxcg = ServiceFactory.getStudentServiceInstance().getStudentJdxcg(student);
			response.setContentType("application/pdf");
			Year year = ServiceFactory.getYearServiceInstance().getNowYear();
			request.getRequestDispatcher("WEB-INF/upload/" + year.getYear() + "/student/jdxcg/" + jdxcg.getId() + ".pdf").forward(request, response);;
		}
		return;
	}
	
	protected int validateJdxcg(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Teacher teacher = (Teacher)request.getSession().getAttribute("teacher");
		String xuehao = request.getParameter("number");
		if(xuehao == null){
			response.sendRedirect("tjdxcg.do");
			return 0;
		}
		Student student = ServiceFactory.getStudentServiceInstance().getStudentByAccount(xuehao);
		Byfs byfs = ServiceFactory.getStudentServiceInstance().getStudentByfs(student.getType());
		if(byfs.getIsPass2() == 0){
			response.sendRedirect("tjdxcg.do");
			return 0;
		}
		if(student == null || !student.getTeacher().equals(teacher.getNumber())){
			response.sendRedirect("tjdxcg.do");
			return 0;
		}
		Jdxcg jdxcg = ServiceFactory.getStudentServiceInstance().getStudentJdxcg(student);
		if(jdxcg == null || jdxcg.getStatus() == 0){
			response.sendRedirect("tjdxcg.do");
			return 0;
		}
		return 1;
	}

}
