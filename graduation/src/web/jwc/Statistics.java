package web.jwc;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import factory.JWCFactory;
import net.sf.json.JSONArray;
import service.JWCService;

/**
 * Servlet implementation class Statistics
 */
@WebServlet("/Statistics.do")
public class Statistics extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JWCService jwcService = JWCFactory.getJwcService();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Statistics() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getParameter("method");
		switch (method) {
		case "getKtDistribute":
			getKtDistribute(request, response);
			break;
		case "getSchedule":
			getSchedule(request, response);
			break;
		case "getAnswerState":
			getAnswerState(request, response);
			break;
		case "getBSZF":
			getBSZF(request, response);
			break;
		case "getCheckResult":
			getCheckResult(request, response);
			break;
		case "getShengYXLW":
			getShengYXLW(request, response);
			break;
		case "pageFindeStudent":
			pageFindeStudent(request, response);
			break;
		case "FindeInteractRecord":
			FindeInteractRecord(request, response);
			break;
		default:
			break;
		}
	}

	/**
	 * 分页查询学生信息
	 */
	public void pageFindeStudent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		JSONArray jsonArray = null;
		String nowPage = request.getParameter("nowPage");
		String yuanxiId = request.getParameter("yuanxiId");
		String preOrNext = request.getParameter("preOrNext");
		// 每页显示的数据数量，这里暂时定为15条
		int row = 5;
		jsonArray = jwcService.pageFindStudent(Integer.valueOf(nowPage), row, Integer.valueOf(yuanxiId), preOrNext);
		// System.out.println("查询条件"+nowPage+yuanxiId+preOrNext+"查询到的信息"+jsonArray.toString());
		writer.print(jsonArray);
		writer.close();
	}
	
	/**
	 * 查找师生交流记录
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void FindeInteractRecord(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		JSONArray jsonArray = null;
		String studentNumber=request.getParameter("stuNumber");
		jsonArray = jwcService.findSSJL(studentNumber);
		writer.print(jsonArray);
		writer.close();
	}

	/**
	 * 课题分布
	 */
	public void getKtDistribute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int yuanxiId = 0;
		String id = request.getParameter("yuanxiId");
		if (id != null && id.length() > 0) {
			yuanxiId = Integer.valueOf(id);
		}
		JSONArray jsonArray = null;
		PrintWriter writer = response.getWriter();
		jsonArray = jwcService.getKtDistribute(yuanxiId);
		writer.println(jsonArray);
		writer.close();
	}

	/**
	 * 进度分布
	 */
	public void getSchedule(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int yuanxiId = 0;
		String id = request.getParameter("yuanxiId");
		if (id != null && id.length() > 0) {
			yuanxiId = Integer.valueOf(id);
		}
		JSONArray jsonArray = null;
		PrintWriter writer = response.getWriter();
		jsonArray = jwcService.getSchedule(yuanxiId);
		writer.println(jsonArray);
		writer.close();
	}

	/**
	 * 答辩状态
	 */
	public void getAnswerState(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int yuanxiId = 0;
		String id = request.getParameter("yuanxiId");
		if (id != null && id.length() > 0) {
			yuanxiId = Integer.valueOf(id);
		}
		JSONArray jsonArray = null;
		PrintWriter writer = response.getWriter();
		jsonArray = jwcService.getAnswerState(yuanxiId);
		writer.println(jsonArray);
		writer.close();
	}

	/**
	 * 毕设总分
	 */
	public void getBSZF(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int yuanxiId = 0;
		String id = request.getParameter("yuanxiId");
		if (id != null && id.length() > 0) {
			yuanxiId = Integer.valueOf(id);
		}
		JSONArray jsonArray = null;
		PrintWriter writer = response.getWriter();
		jsonArray = jwcService.getBSZF(yuanxiId);
		writer.println(jsonArray);
		writer.close();
	}

	/**
	 * 抽检结果
	 */
	public void getCheckResult(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int yuanxiId = 0;
		String id = request.getParameter("yuanxiId");
		if (id != null && id.length() > 0) {
			yuanxiId = Integer.valueOf(id);
		}
		JSONArray jsonArray = null;
		PrintWriter writer = response.getWriter();
		jsonArray = jwcService.getCheckResult(yuanxiId);
		writer.println(jsonArray);
		writer.close();
	}

	/**
	 * 省优秀论文
	 */
	public void getShengYXLW(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int yuanxiId = 0;
		String id = request.getParameter("yuanxiId");
		if (id != null && id.length() > 0) {
			yuanxiId = Integer.valueOf(id);
		}
		JSONArray jsonArray = null;
		PrintWriter writer = response.getWriter();
		jsonArray = jwcService.getShengYXLW(yuanxiId);
		writer.println(jsonArray);
		writer.close();
	}

}
