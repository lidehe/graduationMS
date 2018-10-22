package web.zdls;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Jsgx;
import domain.Student;
import domain.Teacher;
import domain.Yxlw;
import domain.Yxlwz;
import domain.Zf;
import factory.ServiceFactory;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class TYxlw
 */
@WebServlet("/tyxlw.do")
public class TYxlw extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TYxlw() {
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
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
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
		switch (method) {
		case "addg":
			addGeYx(request, response);
			break;
		case "addz":
			addZuYx(request, response);
			break;
		default:
			break;
		}
	}

	protected void addGeYx(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		JSONObject json = new JSONObject();
		PrintWriter out = response.getWriter();
		String xuehao = request.getParameter("xuehao");
		if (xuehao == null || xuehao.equals("")) {
			json.put("status", 400);
			json.put("msg", "不存在的学生！");
		} else {
			Student student = ServiceFactory.getStudentServiceInstance().getStudentByAccount(xuehao);
			if (student == null) {
				json.put("status", 400);
				json.put("msg", "不存在的学生！");
			} else {
				Yxlw yxlw = ServiceFactory.getTeacherServiceInstance().checkYxStudent(xuehao);
				if (yxlw == null) {
					Zf zf = ServiceFactory.getTeacherServiceInstance().checkZfRecord(xuehao);
					if (zf == null) {
						json.put("status", 500);
						json.put("msg", "需要等到总成绩出来！");
					} else {
						if (student.getIsgroup() == 0) {
							yxlw = new Yxlw(xuehao, student.getBs(), student.getYx(), student.getZy(), 0, 0, 0,
									student.getYear(), 0);
							ServiceFactory.getTeacherServiceInstance().addGeYxlw(yxlw);
							json.put("status", 200);
							json.put("msg", "推荐成功！");
						}else {
							json.put("status", 500);
							json.put("msg", "该同学非个人！");
						}
					}
				} else {
					json.put("status", 500);
					json.put("msg", "已推荐！");
				}
			}
		}
		out.print(json);
		out.close();
	}

	protected void addZuYx(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		JSONObject json = new JSONObject();
		PrintWriter out = response.getWriter();
		String xuehao = request.getParameter("xuehao");
		if (xuehao == null || xuehao.equals("")) {
			json.put("status", 400);
			json.put("msg", "不存在的学生！");
		} else {
			Student student = ServiceFactory.getStudentServiceInstance().getStudentByAccount(xuehao);
			if (student == null) {
				json.put("status", 400);
				json.put("msg", "不存在的学生！");
			} else {
				int zuhao = ServiceFactory.getStudentServiceInstance().getStudentZuhao(student);
				Yxlwz yxlwz = ServiceFactory.getTeacherServiceInstance().getYxYxlwzByZh(zuhao);
				if (yxlwz == null) {
					Zf zf = ServiceFactory.getTeacherServiceInstance().checkZfRecord(xuehao);
					if (zf == null) {
						json.put("status", 500);
						json.put("msg", "需要等到总成绩出来！");
					} else {
						if(student.getIsgroup() != 0) {
							yxlwz = new Yxlwz(zuhao, student.getYx(), student.getZy(), 0, 0, 0, student.getYear(), 0);
							ServiceFactory.getTeacherServiceInstance().addZuYxlw(yxlwz);
							json.put("status", 200);
							json.put("msg", "推荐成功！");
						}else {
							json.put("status", 500);
							json.put("msg", "该同学非小组！");
						}
					}
				} else {
					json.put("status", 500);
					json.put("msg", "已推荐！");
				}
			}
		}
		out.print(json);
		out.close();
	}

}
