package web.jwc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Year;
import factory.ServiceFactory;
import history.HistorySearch;
import net.sf.json.JSONArray;
import service.YearService;

/**
 * Servlet implementation class History
 */
@WebServlet("/History.do")
public class History extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HistorySearch historySearch = new HistorySearch();
	YearService yearService = ServiceFactory.getYearServiceInstance();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public History() {
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
		String year = request.getParameter("year");

		// 这个switch用于根据选择的届来链接相应的数据库
		// 目前的麻烦事如何动态变化case，因为年是变化的
		int yearOrder = -1;
		List<Year> years = new ArrayList<>();
		// 获取到的历史年份按照从小到大排序，如2017、2018、、、、
		years = yearService.getHistoryYear();
		if (years.size() > 0 && year != null) {
			for (Year y : years) {
				// 获取选中的年份在历史年份中的序号
				if (year.equals(String.valueOf(y.getYear()))) {
					yearOrder = years.indexOf(y);
				}
			}
		}
		// 这个switch用于根据请求年份调用不同的配置文件来加载对应的数据库
		switch (yearOrder) {
		case -1:// 表示还没有历史数据
			// 发送信息，没有历史数据提示
			break;
		case 0:// 表示大前年数据，对应的配置文件是hibernate3.cfg.xml，数据库是graduationdb_3
			historySearch.setConfigFile("graduation_3");
			break;
		case 1:// 表示前年数据，对应的配置文件是hibernate2.cfg.xml，数据库是graduationdb_2
			historySearch.setConfigFile("graduation_2");
			break;
		case 2:// 表示去年数据，对应的配置文件是hibernate1.cfg.xml，数据库是graduationdb_1
			historySearch.setConfigFile("graduation_1");
			break;
		default:
			break;
		}

		// 这个switch用于根据请求的方法调用查询方法
		switch (method) {
		case "findHistoryYear":
			findHistoryYear(request, response);
			break;
		case "findAllYuanxi":
			findAllYuanxi(request, response);
			break;
		case "pageSearchStudentByYuanxiId":
			pageSearchStudentByYuanxiId(request, response);
			break;
		case "graduationDetail":
			graduationDetail(request, response);
			break;
		case "findAppraisingCountInfo":
			findAppraisingCountInfo(request, response);
			break;
		case "findAppraisingShengjiInfo":
			findAppraisingShengjiInfo(request, response);
			break;
		case "findCheckRule":
            findCheckRule(request, response);
			break;
		case "findCheckResult":
            findCheckResult(request, response);
			break;
		case "findCheckTeacher":
            findCheckTeacher(request, response);
			break;

		default:
			break;
		}

	}

	/**
	 * 查询历史年份信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void findHistoryYear(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter writer = response.getWriter();
		JSONArray jsonArray = null;
		List<Year> years = new ArrayList<>();
		years = yearService.getHistoryYear();
		if (years.size() > 0) {
			String jsonStr = "[";
			for (Year year : years) {
				String tempStr = "{ year:'" + year.getYear() + "'},";
				jsonStr += tempStr;
			}
			jsonStr = jsonStr.substring(0, jsonStr.length() - 1);
			jsonStr += "]";
			jsonArray = JSONArray.fromObject(jsonStr);
			writer.println(jsonArray);

		} else {
			writer.print("00");
			writer.close();
		}

	}

	/**
	 * 查询所有院系
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void findAllYuanxi(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONArray jsonArray = null;
		PrintWriter writer = response.getWriter();
		jsonArray = historySearch.findAllYuanxi();
		System.out.println("查询到的信息" + jsonArray.toString());
		writer.print(jsonArray);
		writer.close();
	}

	/**
	 * 根据院系分页查询学生信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void pageSearchStudentByYuanxiId(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		PrintWriter writer = response.getWriter();
		JSONArray jsonArray = null;
		String nowPage = request.getParameter("nowPage");
		String yuanxiId = request.getParameter("yuanxiId");
		String preOrNext = request.getParameter("preOrNext");
		// 每页显示的数据数量，这里暂时定为15条
		int row = 5;
		jsonArray = historySearch.pageSearchStudent(Integer.valueOf(nowPage), row, Integer.valueOf(yuanxiId),
				preOrNext);
		// System.out.println("查询条件"+nowPage+yuanxiId+preOrNext+"查询到的信息"+jsonArray.toString());
		writer.print(jsonArray);
		writer.close();
	}

	/**
	 * 查询一个学生的毕设详细信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void graduationDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String studentId = request.getParameter("studentId");
		PrintWriter writer = response.getWriter();
		JSONArray jsonArray = null;
		jsonArray = historySearch.findgraduationDetail(Integer.valueOf(studentId));
		System.out.println("查询到的信息" + jsonArray.toString());
		writer.print(jsonArray);
		writer.close();
	}

	/**
	 * 查询优秀论文数量院系分配信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void findAppraisingCountInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter writer = response.getWriter();
		JSONArray jsonArray = null;
		jsonArray = historySearch.appraisingCount();
		System.out.println("查询到的信息" + jsonArray.toString());
		writer.print(jsonArray);
		writer.close();
	}

	/**
	 * 推送到省里优秀论文
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void findAppraisingShengjiInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter writer = response.getWriter();
		JSONArray jsonArray = null;
		jsonArray = historySearch.appraisingShengji();
		System.out.println("查询到的信息" + jsonArray.toString());
		writer.print(jsonArray);
		writer.close();
	}
	
	/**
	 * 查询抽检规则
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void findCheckRule(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter writer = response.getWriter();
		String rule=null;
		rule=historySearch.findCheckRule();
		if (rule!=null) {
			writer.print(rule);
		}else{
			writer.print("00");
		}
		writer.close();
	}
	
	/**
	 * 查询抽检结果
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void findCheckResult(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter writer = response.getWriter();
		JSONArray jsonArray = null;
		jsonArray = historySearch.findCheckResult();
		System.out.println("查询到的信息" + jsonArray.toString());
		writer.print(jsonArray);
		writer.close();
	}
	/**
	 * 查询抽检教师
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void findCheckTeacher(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
	}
}
