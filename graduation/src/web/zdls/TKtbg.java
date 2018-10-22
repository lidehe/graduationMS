package web.zdls;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Byfs;
import domain.Jsgx;
import domain.Ktbg;
import domain.Student;
import domain.Teacher;
import domain.Year;
import factory.ServiceFactory;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class TKtbg
 */
@WebServlet("/tktbg.do")
public class TKtbg extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TKtbg() {
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
			request.getRequestDispatcher("zdls/working/tktbg.jsp").forward(request,response);
			break;
		case "preview":
			previewKtbg(request, response);
			break;
		case "load":
			loadKtbg(request, response);
			break;
		case "sh":
			shKtbg(request, response);
			break;
		default:
			request.getRequestDispatcher("zdls/working/tktbg.jsp").forward(request,response);
			return;
		}
	}
	
	protected void shKtbg(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String idString = request.getParameter("id");
		JSONObject json = new JSONObject();
		if(idString == null){
			json.put("status", 202);
			json.put("msg", "id不能为空！");
		}else{
			int id = Integer.valueOf(idString);
			ServiceFactory.getTeacherServiceInstance().shKtbg(id);
			json.put("status", 200);
			json.put("msg", "审核通过！");
		}
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(json);
		out.close();
	}
	
	protected void previewKtbg(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Teacher teacher = (Teacher)request.getSession().getAttribute("teacher");
		String xuehao = request.getParameter("number");
		if(xuehao == null){
			response.sendRedirect("tktbg.do");
			return;
		}
		Student student = ServiceFactory.getStudentServiceInstance().getStudentByAccount(xuehao);
		Byfs byfs = ServiceFactory.getStudentServiceInstance().getStudentByfs(student.getType());
		if(byfs.getIsPass1() == 0){
			response.sendRedirect("tktbg.do");
			return;
		}
		if(student == null || !student.getTeacher().equals(teacher.getNumber())){
			response.sendRedirect("tktbg.do");
			return;
		}
		request.getRequestDispatcher("zdls/working/ktbgPreview.jsp").forward(request, response);;
	}
	
	protected void loadKtbg(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Teacher teacher = (Teacher)request.getSession().getAttribute("teacher");
		String xuehao = request.getParameter("number");
		if(xuehao == null){
			response.sendRedirect("tktbg.do");
			return;
		}
		Student student = ServiceFactory.getStudentServiceInstance().getStudentByAccount(xuehao);
		Byfs byfs = ServiceFactory.getStudentServiceInstance().getStudentByfs(student.getType());
		if(byfs.getIsPass1() == 0){
			response.sendRedirect("tktbg.do");
			return;
		}
		if(student == null || !student.getTeacher().equals(teacher.getNumber())){
			response.sendRedirect("tktbg.do");
			return;
		}
		Ktbg ktbg = ServiceFactory.getStudentServiceInstance().getStudentKtbg(student);
		if(ktbg == null || ktbg.getStatus() == 0){
			response.sendRedirect("tktbg.do");
			return;
		}
		response.setContentType("application/pdf");
		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		request.getRequestDispatcher("WEB-INF/upload/" + year.getYear() + "/student/ktbg/" + ktbg.getId() + ".pdf").forward(request, response);
	}

}
