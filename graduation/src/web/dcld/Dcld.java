package web.dcld;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import domain.Zhuanye;
import factory.ServiceFactory;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class Dcld
 */
@WebServlet("/dcld.do")
public class Dcld extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Dcld() {
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
			if (gx.getJs() == 9 || gx.getJs() == 5) {
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
			request.getRequestDispatcher("dcld/working/chart.jsp").forward(request, response);
			break;
		case "gzzj":
			request.getRequestDispatcher("dcld/working/gzzj.jsp").forward(request, response);
			break;
		case "pyjg":
			request.getRequestDispatcher("dcld/working/pyjg.jsp").forward(request, response);
			break;
		case "pydetail":
			String number =request.getParameter("number");
			if(number == null || number.equals(""))
				return;
			Yxlw yxlw = ServiceFactory.getTeacherServiceInstance().checkStudent(number);
			if(yxlw == null)
				return;
			request.getRequestDispatcher("dcld/working/pydetail.jsp").forward(request, response);
			break;
		case "pyzdetail":
			String zuhao = request.getParameter("zuhao");
			if(zuhao == null || zuhao.equals(""))
				return;
			Yxlwz yxlwz = ServiceFactory.getTeacherServiceInstance().getXiaoYxlwzByZh(Integer.valueOf(zuhao));
			if(yxlwz == null)
				return;
			request.getRequestDispatcher("dcld/working/pyzdetail.jsp").forward(request, response);
			break;
		case "xsxq":
			request.getRequestDispatcher("dcld/working/xsxq.jsp").forward(request, response);
			break;
		case "loadzy":
			loadZy(request, response);
			break;
		case "loadstu":
			loadStu(request, response);
			break;
		case "xsdetail":
			String number4 = request.getParameter("number");
			if (number4 == null || number4.equals("")) {
				response.sendRedirect("dcld.do");
			} else {
				Student student = ServiceFactory.getStudentServiceInstance().getStudentByAccount(number4);
				if(student == null)
					return;
				else
					request.getRequestDispatcher("dcld/working/xsdetail.jsp").forward(request, response);
			}
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
				List<Student> students = ServiceFactory.getTeacherServiceInstance().getZyStudentOrderByNumber(zy);
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
	
	private List<Map<String, String>> getMap(List<Student> students) {
		//List<Zhuanye> zys = ServiceFactory.getTeacherServiceInstance().getAllZys();
//		Map<Integer, String> zyMaps = new HashMap<Integer, String>();
//		for (Zhuanye zym : zys) {
//			zyMaps.put(zym.getId(), zym.getName());
//		}
		List<Map<String, String>> studentMap = new ArrayList<Map<String, String>>();
		for (int i = 0, l = students.size(); i < l; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("number", students.get(i).getNumber());
			map.put("zy", students.get(i).getBj());
			map.put("name", students.get(i).getName());
			studentMap.add(i, map);
		}
		return studentMap;
	}

}
