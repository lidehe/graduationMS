package web.zdls;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Byfs;
import domain.Jsgx;
import domain.Lw;
import domain.Lwcg;
import domain.Student;
import domain.Teacher;
import domain.Year;
import factory.ServiceFactory;

/**
 * Servlet implementation class TLwcg
 */
@WebServlet("/tlwcg.do")
public class TLwcg extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TLwcg() {
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
			request.getRequestDispatcher("zdls/working/tlwcg.jsp").forward(request, response);
			break;
		case "preview":
			previewLw(request, response);
			break;
		case "load":
			loadLwcg(request, response);
			break;
		default:
			break;
		}
	}
	
	protected void previewLw(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int result = validateLw(request, response);
		if(result == 1){
			request.getRequestDispatcher("zdls/working/lwcgPreview.jsp").forward(request, response);
		}
		return;
	}
	
	protected void loadLwcg(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int result = validateLw(request, response);
		if (result != 0) {
			String xuehao = request.getParameter("number");
			Student student = ServiceFactory.getStudentServiceInstance().getStudentByAccount(xuehao);
			Lwcg lwcg = ServiceFactory.getStudentServiceInstance().getStudentLwcg(student);
			response.setContentType("application/pdf");
			Year year = ServiceFactory.getYearServiceInstance().getNowYear();
			request.getRequestDispatcher("WEB-INF/upload/" + year.getYear() + "/student/lwcg/" + lwcg.getId() + ".pdf").forward(request, response);;
		}
		return;
	}
	
	protected int validateLw(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Teacher teacher = (Teacher)request.getSession().getAttribute("teacher");
		String xuehao = request.getParameter("number");
		if(xuehao == null){
			response.sendRedirect("tlwcg.do");
			return 0;
		}
		Student student = ServiceFactory.getStudentServiceInstance().getStudentByAccount(xuehao);
		Byfs byfs = ServiceFactory.getStudentServiceInstance().getStudentByfs(student.getType());
		if(byfs.getIsPass3() == 0){
			response.sendRedirect("tlwcg.do");
			return 0;
		}
		if(student == null || !student.getTeacher().equals(teacher.getNumber())){
			response.sendRedirect("tlwcg.do");
			return 0;
		}
		Lw lw = ServiceFactory.getTeacherServiceInstance().getStudentLw(student);
		if(lw == null || lw.getStatus() == 0){
			response.sendRedirect("tlwcg.do");
			return 0;
		}else if(lw.getStatus() == 3){
			return 3;
		}
		return 1;
	}


}
