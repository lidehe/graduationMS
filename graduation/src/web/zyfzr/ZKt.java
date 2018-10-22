package web.zyfzr;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Jsgx;
import domain.Kt;
import domain.Teacher;
import factory.ServiceFactory;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class Zkt
 */
@WebServlet("/zkt.do")
public class ZKt extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ZKt() {
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
			request.getRequestDispatcher("zyfzr/working/zkt.jsp").forward(request, response);
			break;
		case "sh1":
			shKt(request, response);
			break;
		case "sh2":
			rfkt(request, response);
			break;
		default:
			break;
		}
	}

	protected void shKt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int result = validateKt(request, response);
		JSONObject json = new JSONObject();
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		if(result == 1){
			String ktString = request.getParameter("id");
			int id = Integer.valueOf(ktString);
			json.put("status", 200);
			ServiceFactory.getTeacherServiceInstance().zyfzrUpdateKt(id, 1);
		}else{
			json.put("status", 202);
			json.put("msg", "没有操作权限！");
		}
		out.print(json);
		out.close();
	}
	
	protected void rfkt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int result = validateKt(request, response);
		JSONObject json = new JSONObject();
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		if(result == 1){
			String ktString = request.getParameter("id");
			int id = Integer.valueOf(ktString);
			json.put("status", 200);
			ServiceFactory.getTeacherServiceInstance().zyfzrUpdateKt(id, 2);
		}else{
			json.put("status", 202);
			json.put("msg", "没有操作权限！");
		}
		out.print(json);
		out.close();
	}
	
	private int validateKt(HttpServletRequest request, HttpServletResponse response) {
		Teacher teacher = (Teacher)request.getSession().getAttribute("teacher");
		String ktString = request.getParameter("id");
		if(ktString == null)
			return 0;
		int kt = Integer.valueOf(ktString);
		Kt k = ServiceFactory.getTeacherServiceInstance().teacherGetKt(kt);
		if(k != null && k.getFzrsh() == 0 && k.getZy() == teacher.getFzr())
			return 1;
		return 0;
	}
}
