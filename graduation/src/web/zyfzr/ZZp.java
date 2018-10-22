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
import domain.Teacher;
import domain.Year;
import domain.Zf;
import domain.Zpjl;
import factory.ServiceFactory;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class ZZp
 */
@WebServlet("/zzp.do")
public class ZZp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ZZp() {
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
			request.getRequestDispatcher("zyfzr/working/zzp.jsp").forward(request, response);
			break;
		case "add":
			addZp(request, response);
			break;
		case "hui":
			huiF(request, response);
			break;
		default:
			break;
		}
	}
	
	protected void huiF(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		List<Zf> zfs = ServiceFactory.getTeacherServiceInstance().getZyStudentZfOrderByNumber(teacher.getFzr());
		JSONObject json = new JSONObject();
		if(zfs != null && zfs.size() > 0) {//第二次
			json = ServiceFactory.getTeacherServiceInstance().calcuStudentSecond(teacher);
			json.put("status", 200);
		}else {//第一次汇分
			ServiceFactory.getTeacherServiceInstance().calcuScoreFirst(teacher);
			json.put("msg", "汇分成功！");
			json.put("status", 201);
		}
		out.print(json);
		out.close();
	}
	
	protected void addZp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		response.setContentType("application/json;charset=utf-8");
		JSONObject json = new JSONObject();
		PrintWriter out = response.getWriter();
		Zpjl zpjl = ServiceFactory.getTeacherServiceInstance().zyfzrGetZpjl(teacher);
		if(zpjl != null){
			json.put("status", 202);
			json.put("msg", "不能重复提交！");
			json.put("text", zpjl.getText());
		}else{
			String text = request.getParameter("text");
			if(text == null ||"".equals(text)){
				json.put("status", 500);
				json.put("msg", "请填写正确的内容！");
			}else{
				Year year = ServiceFactory.getYearServiceInstance().getNowYear();
				zpjl = new Zpjl(teacher.getNumber(), teacher.getFzr(), text, year.getYear());
				ServiceFactory.getTeacherServiceInstance().addZpjl(zpjl);
				ServiceFactory.getTeacherServiceInstance().calcuScoreFirst(teacher);
				json.put("status", 200);
				json.put("msg", "提交成功！");
			}
		}
		out.print(json);
		out.close();
	}

}
