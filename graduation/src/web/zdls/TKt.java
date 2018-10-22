package web.zdls;

import java.io.File;
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
import domain.Year;
import factory.ServiceFactory;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class TKt
 */
@WebServlet("/tkt.do")
public class TKt extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TKt() {
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
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if(teacher == null || teacher.getNumber() == null) {
			response.sendRedirect("login.do?method=logout");
			return;
		}
		List<Jsgx> gxs = ServiceFactory.getTeacherServiceInstance().getTeacherJses(teacher);
		boolean js = false;
		for (Jsgx gx : gxs) {
			if (gx.getJs() == 1) {
				js = true;
				break;
			}
		}
		if (!js) {// 身份不对，直接自动退出
			response.sendRedirect("login.do?method=logout");
			return;
		}
		if (teacher == null)
			return;
		if (method == null)
			method = "ind";
		switch (method) {
		case "ind":
			request.getRequestDispatcher("zdls/working/tkt.jsp").forward(request, response);
			break;
		case "sh":
			shKt(request, response);
			break;
		case "delete":
			deleteKt(request, response);
			break;
		case "add":
			addKt(request, response);
			break;
		case "setxzrs":
			setXzrs(request, response);
			break;
		default:
			request.getRequestDispatcher("zdls/working/tkt.jsp").forward(request, response);
			break;
		}
	}
	
	protected void setXzrs(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String idString = request.getParameter("id");
		String rsString = request.getParameter("xzrs");
		response.setContentType("application/json;charset=utf-8");
		JSONObject json = new JSONObject();
		if(idString == null || rsString == null || idString.equals("") || rsString.equals("")) {
			json.put("status", 500);
			json.put("msg", "数据有误！");
		}else {
			int id = Integer.valueOf(idString);
			int xzrs = Integer.valueOf(rsString);
			Kt kt = ServiceFactory.getStudentServiceInstance().getStudentKt(id);
			int rs = ServiceFactory.getTeacherServiceInstance().getOneKtStudents(id);
			if(rs < xzrs) {
				json.put("status", 500);
				json.put("msg", "错误，已有"+rs+"位学生选择该课题！");
			}else {
				json.put("status", 200);
				json.put("msg", "设置成功！");
				kt.setXzrs(xzrs);
				ServiceFactory.getTeacherServiceInstance().updateKtInfo(kt);
			}
			PrintWriter out = response.getWriter();
			out.print(json);
			out.close();
		}
	}

	protected void shKt(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		JSONObject json = new JSONObject();
		if (request.getParameter("id") == null) {
			json.put("status", 400);
			json.put("msg", "error！");
		} else {
			int id = Integer.valueOf(request.getParameter("id"));
			int result = ServiceFactory.getTeacherServiceInstance().shKt(teacher, id);
			if (result == -1) {
				json.put("status", 202);// 不是这个老师的
				json.put("msg", "您没有权限修改此课题！");
			} else if (result == 0) {
				json.put("status", 500);// 课题不存在
				json.put("msg", "您选择的课题不存在！");
			} else {
				json.put("status", 200);// 审核通过
				json.put("msg", "审核通过");
			}
		}
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(json);
		out.close();
	}

	protected void deleteKt(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		JSONObject json = new JSONObject();
		if (request.getParameter("id") == null) {
			json.put("status", 400);
			json.put("msg", "error！");
		} else {
			int id = Integer.valueOf(request.getParameter("id"));
			int result = ServiceFactory.getTeacherServiceInstance().deleteKt(teacher, id);
			if (result == -1) {
				json.put("status", 202);// 不是这个老师的
				json.put("msg", "您没有权限修改此课题！");
			} else if (result == -2) {
				json.put("status", 500);// 课题不存在
				json.put("msg", "您选择的课题不存在！");
			} else if (result == 0) {
				json.put("status", 200);// 删除成功
				json.put("msg", "删除成功！");
			} else if (result == -3) {
				json.put("status", 400);
				json.put("msg", "当前有学生选择此课题，无法删除！");
			} else {
				json.put("status", 200);// 删除成功
				json.put("msg", "审核通过");
				Year year = ServiceFactory.getYearServiceInstance().getNowYear();
				File file = new File("WEB-INF/upload/" + year.getYear() + "/teacher/rws/" + result + ".pdf");
				if (file.exists() && file.isFile())
					file.delete();
			}
		}
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(json);
		out.close();
	}

	protected void addKt(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		JSONObject json = new JSONObject();
		int zyId = Integer.valueOf(request.getParameter("ktzy") == null ? "0" : request.getParameter("ktzy"));
		String name = request.getParameter("kName");
		String text = request.getParameter("kText");
		String xzString = request.getParameter("Kxzrs");
		String byString = request.getParameter("ktbylx");
		int xzrs = Integer.valueOf(xzString == null ? "0" : xzString);
		int bylx = Integer.valueOf(byString == null ? "0" : byString);
		if (name.length() > 40 || text.length() > 200 || zyId == 0) {
			json.put("status", 400);
			json.put("msg", "字数超出或者专业没选！");
		} else {
			int result = ServiceFactory.getTeacherServiceInstance().teacherAddKt(teacher, zyId, name, text, xzrs, bylx);
			if (result == -1) {
				json.put("status", 500);
				json.put("msg", "专业负责人不存在！");
			} else {
				json.put("status", 200);
				json.put("msg", "成功添加课题！");
			}
		}
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(json);
		out.close();
	}

}
