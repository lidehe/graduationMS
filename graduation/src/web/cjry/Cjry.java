package web.cjry;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Cjbd;
import domain.Cjk;
import domain.Cjrz;
import domain.Jsgx;
import domain.Student;
import domain.Teacher;
import domain.Zf;
import domain.Zhuanye;
import factory.ServiceFactory;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class Cjry
 */
@WebServlet("/cjry.do")
public class Cjry extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Cjry() {
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
			if (gx.getJs() == 7) {
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
			request.getRequestDispatcher("cjry/working/cj.jsp").forward(request, response);
			break;
		case "loadzy":
			loadZy(request, response);
			break;
		case "loadstu":
			loadStu(request, response);
			break;
		case "loadstuK":
			searchStudent(request, response);
			break;
		case "detail":
			String number = request.getParameter("number");
			if (number == null || number.equals("")) {
				response.sendRedirect("cjry.do");
			} else {
				Student student = ServiceFactory.getStudentServiceInstance().getStudentByAccount(number);
				Cjk cjObject = ServiceFactory.getTeacherServiceInstance().getOneCjObject(number);
				if (student == null || cjObject == null) {
					response.sendRedirect("cjry.do");
				} else {
					List<Cjbd> bds = ServiceFactory.getTeacherServiceInstance().getOneTeacherCjBd(teacher);
					boolean r = false;
					for (Cjbd bd : bds) {
						if (bd.getYx() == student.getYx())
							r = true;
					}
					if (r)
						request.getRequestDispatcher("cjry/working/detail.jsp").forward(request, response);
					else
						response.sendRedirect("cjry.do");
				}
			}
			break;
		case "add":
			addCjrz(request, response);
			break;
		default:
			break;
		}
	}

	protected void loadZy(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		String yxString = request.getParameter("yx");
		if (yxString != null) {
			int yx = Integer.valueOf(yxString);
			if (yx == 0) {
				json.put("status", 202);
				json.put("msg", "不存在的院系！");
			} else {
				List<Zhuanye> zys = ServiceFactory.getTeacherServiceInstance().getZys(yx);
				json.put("status", 200);
				json.put("zys", zys);
			}
		} else {
			json.put("status", 202);
			json.put("msg", "不存在的院系！");
		}
		out.print(json);
		out.close();
	}

	protected void loadStu(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		String zyString = request.getParameter("zy");
		if (zyString != null) {
			int zy = Integer.valueOf(zyString);
			if (zy == 0) {
				json.put("status", 202);
				json.put("msg", "不存在的专业！");
			} else {
				List<Student> students = ServiceFactory.getTeacherServiceInstance().getOneZyCjStudents(zy);
				List<Map<String, String>> studentMap = getMap(students);
				json.put("status", 200);
				json.put("students", studentMap);
			}
		} else {
			json.put("status", 202);
			json.put("msg", "不存在的专业！");
		}
		out.print(json);
		out.close();
	}

	protected void searchStudent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		String key = request.getParameter("key");
		if (key != null) {
			List<Student> students = ServiceFactory.getTeacherServiceInstance().searchStudentByNumberLike(key);
			if (students.size() > 0) {
				List<Map<String, String>> studentMap = getMap(students);
				json.put("status", 200);
				json.put("students", studentMap);
			} else {
				json.put("status", 400);
				json.put("msg", "未查找到相关同学信息！");
			}

		} else {
			json.put("status", 202);
			json.put("msg", "请输入关键字！");
		}
		out.print(json);
		out.close();
	}

	private List<Map<String, String>> getMap(List<Student> students) {
		List<Zhuanye> zys = ServiceFactory.getTeacherServiceInstance().getAllZys();
		Map<Integer, String> zyMaps = new HashMap<Integer, String>();
		for (Zhuanye zym : zys) {
			zyMaps.put(zym.getId(), zym.getName());
		}
		List<Map<String, String>> studentMap = new ArrayList<Map<String, String>>();
		for (int i = 0, l = students.size(); i < l; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("number", students.get(i).getNumber());
			map.put("zy", zyMaps.get(students.get(i).getZy()));
			map.put("name", students.get(i).getName());
			studentMap.add(i, map);
		}
		return studentMap;
	}

	public void addCjrz(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String xuehao = request.getParameter("number");
		response.setContentType("application/json;charset=utf-8");
		JSONObject json = new JSONObject();
		if (xuehao == null || xuehao.equals("")) {
			Student student = ServiceFactory.getStudentServiceInstance().getStudentByAccount(xuehao);
			Cjk cjObject = ServiceFactory.getTeacherServiceInstance().getOneCjObject(xuehao);
			if (student == null || cjObject == null) {
				json.put("status", 202);
				json.put("msg", "学生不存在！");
			} else {
				Zf zf = ServiceFactory.getTeacherServiceInstance().checkZfRecord(xuehao);
				if (zf == null) {
					json.put("status", 500);
					json.put("msg", "学生暂不可被检查！");
				} else {
					Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
					List<Cjbd> bds = ServiceFactory.getTeacherServiceInstance().getOneTeacherCjBd(teacher);
					boolean r = false;
					for (Cjbd bd : bds) {
						if (bd.getYx() == student.getYx())
							r = true;
					}
					if (r) {
						String text = request.getParameter("text");
						String gString = request.getParameter("grade") == null ? "1" : request.getParameter("grade");
						int grade = Integer.valueOf(gString);
						if (text == null || text.equals("") || (grade != 0 && grade != 1 && grade != 2 && grade != 3)) {
							json.put("status", 202);
							json.put("msg", "请填入完整的内容！");
						} else {
							Cjrz rz1 = ServiceFactory.getTeacherServiceInstance().checkCjed(xuehao,
									teacher.getNumber());
							if (rz1 != null) {
								rz1.setText(text);
								rz1.setDj(grade);
								rz1.setTime(new Timestamp(System.currentTimeMillis()));
								ServiceFactory.getTeacherServiceInstance().cjryUpdateCjrz(rz1);
								json.put("status", 200);
								json.put("msg", "更新成功！");
							} else {
								Cjrz rz = new Cjrz(xuehao, teacher.getName(), new Timestamp(System.currentTimeMillis()),
										text, grade, teacher.getYear());
								ServiceFactory.getTeacherServiceInstance().addCjrz(rz);
								json.put("status", 200);
								json.put("msg", "添加成功！");
							}
						}
					} else {
						json.put("status", 500);
						json.put("msg", "您无权限抽检此同学");
					}
				}
			}
		} else {
			json.put("status", 202);
			json.put("msg", "学生不存在！");
		}
	}

}
