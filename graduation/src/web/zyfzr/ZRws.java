package web.zyfzr;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Jsgx;
import domain.Rws;
import domain.Teacher;
import domain.Year;
import factory.ServiceFactory;

/**
 * Servlet implementation class ZRws
 */
@WebServlet("/zrws.do")
public class ZRws extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ZRws() {
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
		if (method == null)
			method = "ind";
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		List<Jsgx> gxs = ServiceFactory.getTeacherServiceInstance().getTeacherJses(teacher);
		boolean js = false;
		for (Jsgx gx : gxs) {
			if (gx.getJs() == 2) {
				js = true;
				break;
			}
		}
		if (!js) {// 身份不对，直接自动退出
			response.sendRedirect("login.do?method=logout");
			return;
		}
		switch (method) {
		case "ind":
			request.getRequestDispatcher("zyfzr/working/zrws.jsp").forward(request, response);
			break;
		case "preview":
			previewRws(request, response);
			break;
		case "load":
			loadRws(request, response);
			break;
		default:
			break;
		}
	}
	
	protected void previewRws(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		int result = validateRws(request, response);
		if(result == 1){
			request.getRequestDispatcher("zyfzr/working/rws.jsp").forward(request,response);
		}
	}
	
	protected void loadRws(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		int result = validateRws(request, response);
		if(result == 1){
			response.setContentType("application/pdf");
			String idString = request.getParameter("id");
			Rws rws = ServiceFactory.getTeacherServiceInstance().getOneRws(Integer.valueOf(idString));
			Year year = ServiceFactory.getYearServiceInstance().getNowYear();
			request.getRequestDispatcher("WEB-INF/upload/" + year.getYear() + "/teacher/rws/" + rws.getId() + ".pdf").forward(request, response);
		}
	}
	
	private int validateRws(HttpServletRequest request, HttpServletResponse response){
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		String idString = request.getParameter("id");
		if(idString == null)
			return 0;
		Rws rws = ServiceFactory.getTeacherServiceInstance().getOneRws(Integer.valueOf(idString));
		if(rws != null && rws.getStatus() == 1 && rws.getZy() == teacher.getFzr())
			return 1;
		return 0;
	}
}
