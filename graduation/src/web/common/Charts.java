package web.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import factory.ServiceFactory;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class Charts
 */
@WebServlet("/charts.do")
public class Charts extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Charts() {
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
		switch (method) {
		case "yxcj":
			getYxCj(request, response);
			break;

		default:
			break;
		}
	}

	protected void getYxCj(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		JSONObject json = new JSONObject();
		PrintWriter out = response.getWriter();
		String yxString = request.getParameter("yx");
		if (yxString == null || yxString.equals("")) {
			json.put("status", 500);
			json.put("msg", "院系有误！");
		} else {
			int yx = Integer.valueOf(yxString);
			List<Integer> cjs = ServiceFactory.getTeacherServiceInstance().getOneYxCj(yx);
			if (cjs.get(0) + cjs.get(1) + cjs.get(2) + cjs.get(3) + cjs.get(4) == 0) {
				json.put("status", 202);
				json.put("msg", "成绩暂未统计出！");
			} else {
				json.put("status", 200);
				json.put("bjg", cjs.get(0));
				json.put("jg", cjs.get(1));
				json.put("zd", cjs.get(2));
				json.put("lh", cjs.get(3));
				json.put("yx", cjs.get(4));
			}
		}
		out.print(json);
		out.close();
	}

}
