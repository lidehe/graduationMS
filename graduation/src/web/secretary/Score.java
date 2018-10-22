package web.secretary;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import factory.ServiceFactory;
import net.sf.json.JSONArray;
import service.StudentService;
import utilsOffice.WriteExcel;

/**
 * Servlet implementation class Score
 */
@WebServlet("/Score.do")
public class Score extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private StudentService studentService = ServiceFactory.getStudentServiceInstance();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Score() {
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
		case "getBSZF":// 查询学生毕设总分数
			getBSZF(request, response);
			break;
		case "exportToExcel":// 导出到excel
			exportToExcel(request, response);
			break;

		default:
			break;
		}
	}

	public void exportToExcel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String yuanxiIdStr = request.getParameter("yuanxiId");
		String zhuanyeIdStr = request.getParameter("zhuanyeId");
		String banji = request.getParameter("banji");
        
		PrintWriter writer = response.getWriter();
		int yuanxiId = Integer.valueOf(yuanxiIdStr);
		int zhuanyeId = Integer.valueOf(zhuanyeIdStr);
		List<String[]> list=new ArrayList<>();
		list=studentService.ExportStudentFinalScoreByYuanxiZhuanyeBanji(yuanxiId, zhuanyeId, banji);
		if (list.size()>0) {
			String path = request.getSession().getServletContext()
					.getRealPath("FileCache/");
			WriteExcel.makeNewExcel(path, list, "学生成绩信息", "学生成绩信息表");
			writer.print("学生成绩信息表"+".xlsx");
		}else{
			writer.print("00");
		}
		writer.close();
	}
	public void getBSZF(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nowPageStr = request.getParameter("nowPage");
		String option = request.getParameter("option");
		String yuanxiIdStr = request.getParameter("yuanxiId");
		String zhuanyeIdStr = request.getParameter("zhuanyeId");
		String banji = request.getParameter("banji");

		JSONArray result = null;
		PrintWriter writer = response.getWriter();
		int yuanxiId = Integer.valueOf(yuanxiIdStr);
		int zhuanyeId = Integer.valueOf(zhuanyeIdStr);
		int nowPage = Integer.valueOf(nowPageStr);
		result = studentService.pageFindStudentFinalScoreByYuanxiZhuanyeBanji(yuanxiId, zhuanyeId, banji, nowPage);
		if (result.size() <= 0) {
			writer.print("00");
			writer.close();
		} else {
			if (option.equals("上一页")) {
				if (nowPage <= 1) {
					result = studentService.pageFindStudentFinalScoreByYuanxiZhuanyeBanji(yuanxiId, zhuanyeId, banji,
							nowPage);
					writer.println(result);
					writer.close();
				} else {
					nowPage -= 1;
					result = studentService.pageFindStudentFinalScoreByYuanxiZhuanyeBanji(yuanxiId, zhuanyeId, banji,
							nowPage);
					writer.println(result);
					writer.close();
				}
			} else if (option.equals("下一页")) {
				nowPage += 1;
				result = studentService.pageFindStudentFinalScoreByYuanxiZhuanyeBanji(yuanxiId, zhuanyeId, banji,
						nowPage);
				if (result.size() <= 0) {
					writer.print("最后一页");
					writer.close();
				} else {
					result = studentService.pageFindStudentFinalScoreByYuanxiZhuanyeBanji(yuanxiId, zhuanyeId, banji,
							nowPage);
				}
			}
		}

	}

}
