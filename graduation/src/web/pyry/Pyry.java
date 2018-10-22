package web.pyry;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Jsgx;
import domain.Pybd;
import domain.Pyrz;
import domain.Student;
import domain.Teacher;
import domain.Yxlw;
import domain.Yxlwz;
import factory.ServiceFactory;
import net.sf.json.JSONObject;
import utils.FloatUtil;

/**
 * Servlet implementation class Pyry
 */
@WebServlet("/pyry.do")
public class Pyry extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Pyry() {
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
			method = "ind";
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		List<Jsgx> gxs = ServiceFactory.getTeacherServiceInstance().getTeacherJses(teacher);
		boolean js = false;
		for (Jsgx gx : gxs) {
			if (gx.getJs() == 6) {
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
			request.getRequestDispatcher("pyry/working/py.jsp").forward(request, response);
			break;
		case "detail":
			String xuehao = request.getParameter("number");
			if (xuehao == null) {
				request.getRequestDispatcher("pyry/working/py.jsp").forward(request, response);
				return;
			} else {
				Yxlw lwR = ServiceFactory.getTeacherServiceInstance().checkStudent(xuehao);
				if (lwR == null) {
					request.getRequestDispatcher("pyry/working/py.jsp").forward(request, response);
					return;
				} else {
					List<Pybd> bPybds = ServiceFactory.getTeacherServiceInstance().getOneTeacherPyBd(teacher);
					boolean r = false;
					for (Pybd bd : bPybds) {
						if (bd.getYx() == lwR.getYx())
							r = true;
					}
					if (r)
						request.getRequestDispatcher("pyry/working/detail.jsp").forward(request, response);
					else
						response.sendRedirect("pyry.do");
				}
			}
			break;
		case "detailz":
			String zuhao = request.getParameter("zuhao");
			if(zuhao == null || zuhao.equals("")) {
				request.getRequestDispatcher("pyry/working/py.jsp").forward(request, response);
				return;
			}else {
				int zh = Integer.valueOf(zuhao);
				Yxlwz lwz = ServiceFactory.getTeacherServiceInstance().getXiaoYxlwzByZh(zh);
				if(lwz == null) {
					request.getRequestDispatcher("pyry/working/py.jsp").forward(request, response);
					return;
				}else {
					List<Pybd> bPybds = ServiceFactory.getTeacherServiceInstance().getOneTeacherPyBd(teacher);
					boolean r = false;
					for (Pybd bd : bPybds) {
						if (bd.getYx() == lwz.getYx())
							r = true;
					}
					if (r)
						request.getRequestDispatcher("pyry/working/zdetail.jsp").forward(request, response);
					else
						response.sendRedirect("pyry.do");
				}
			}
			break;
		case "add":
			addPy(request, response);
			break;
		case "addz":
			addZPy(request, response);
			break;
		default:
			break;
		}
	}
	
	protected void addZPy(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		JSONObject json = new JSONObject();
		PrintWriter out = response.getWriter();
		String zuhao = request.getParameter("zuhao");
		if (zuhao == null) {
			json.put("status", 202);
			json.put("msg", "组号有误！");
		} else {
			Yxlwz yxlwz = ServiceFactory.getTeacherServiceInstance().getXiaoYxlwzByZh(Integer.valueOf(zuhao));
			if (yxlwz == null) {
				json.put("status", 202);
				json.put("msg", "该组的论文未被推选为优秀论文！");
			} else {
				Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
				List<Pybd> bPybds = ServiceFactory.getTeacherServiceInstance().getOneTeacherPyBd(teacher);
				boolean r = false;
				for (Pybd bd : bPybds) {
					if (bd.getYx() == yxlwz.getYx())
						r = true;
				}
				if (!r) {
					json.put("status", 500);
					json.put("msg", "您无权限为此组同学评优！");
					out.print(json);
					out.close();
					return;
				}
				Pyrz rz = ServiceFactory.getTeacherServiceInstance().getOneZuXiaoRz(Integer.valueOf(zuhao), teacher.getNumber());
				if (rz != null) {
					json.put("status", 202);
					json.put("msg", "不可重复添加！");
				} else {
					String text = request.getParameter("text");
					String fString = request.getParameter("fs");
					float fs = (fString == null || "".equals(fString)) ? 0 : Float.valueOf(fString);
					if (text == null || "".equals(text) || fs <= 0 || fs > 100) {
						json.put("status", 202);
						json.put("msg", "请填写合法的内容！");
					} else {
						fs = FloatUtil.floatSaveTwoD(fs);
						rz = new Pyrz(0, 1, "", Integer.valueOf(zuhao), teacher.getNumber(), new Timestamp(System.currentTimeMillis()), text, fs, teacher.getYear(), 1);
						ServiceFactory.getTeacherServiceInstance().addXiaoZuPyrz(rz);
						json.put("status", 200);
						json.put("msg", "添加成功！");
					}
				}
			}
		}
		out.print(json);
		out.close();
	}

	protected void addPy(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		JSONObject json = new JSONObject();
		PrintWriter out = response.getWriter();
		String xuehao = request.getParameter("number");
		if (xuehao == null) {
			json.put("status", 202);
			json.put("msg", "学号有误！");
		} else {
			Yxlw lwR = ServiceFactory.getTeacherServiceInstance().checkStudent(xuehao);
			if (lwR == null) {
				json.put("status", 202);
				json.put("msg", "该学生论文未被推选为优秀论文！");
			} else {
				Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
				List<Pybd> bPybds = ServiceFactory.getTeacherServiceInstance().getOneTeacherPyBd(teacher);
				boolean r = false;
				for (Pybd bd : bPybds) {
					if (bd.getYx() == lwR.getYx())
						r = true;
				}
				if (!r) {
					json.put("status", 500);
					json.put("msg", "您无权限为此学生评优！");
					out.print(json);
					out.close();
					return;
				}
				Pyrz rz1 = ServiceFactory.getTeacherServiceInstance().checkPyed(xuehao, teacher.getNumber());
				if (rz1 != null) {
					json.put("status", 202);
					json.put("msg", "不可重复添加！");
				} else {
					String text = request.getParameter("text");
					String fString = request.getParameter("fs");
					float fs = (fString == null || "".equals(fString)) ? 0 : Float.valueOf(fString);
					if (text == null || "".equals(text) || fs <= 0 || fs > 100) {
						json.put("status", 202);
						json.put("msg", "请填写合适的内容！");
					} else {
						fs = FloatUtil.floatSaveTwoD(fs);
						Student student = ServiceFactory.getStudentServiceInstance().getStudentByAccount(xuehao);
						Pyrz rz = new  Pyrz(student.getBs(), 0, xuehao, 0, teacher.getNumber(), new  Timestamp(System.currentTimeMillis()), text, fs, student.getYear(), 1);
						ServiceFactory.getTeacherServiceInstance().addXiaoGePyrz(rz);
						json.put("status", 200);
						json.put("msg", "添加成功！");
					}
				}
			}
		}
		out.print(json);
		out.close();
	}

}
