package web.jwc;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.Yuanxi;
import domain.Zhuanye;
import factory.JWCFactory;
import service.JWCService;

/**
 * 分页查询院系、专业、教职工、学生信息
 */

/**
 * Servlet implementation class PageSearch
 */
@WebServlet("/PageSearch.do")
public class PageSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JWCService jwcService = JWCFactory.getJwcService();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PageSearch() {
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
		// TODO Auto-generated method stub
		String requestFrom = request.getParameter("requestFrom");
		String pageNum = request.getParameter("pageNum");
		String option = request.getParameter("option");

		// 请求新的数据之前，先销毁旧的数据
		HttpSession session = request.getSession();
		session.removeAttribute("yxInfors");
		session.removeAttribute("zyInfors");
		session.removeAttribute("jsInfors");
		session.removeAttribute("xsInfors");
		session.removeAttribute("pageNum");
		session.removeAttribute("totalPage");

		session.removeAttribute("yuanxiMap");
		session.removeAttribute("zhuanyeMap");
		// 设置院系map，在页面上把院系id转换成院系名字
		List<Yuanxi> yuanxis = jwcService.findAllYuanxi();
		Map<Integer, String> yuanxiMap = new HashMap<>();
		for (int j = 0; j < yuanxis.size(); j++) {
			yuanxiMap.put(yuanxis.get(j).getId(), yuanxis.get(j).getName());
		}
		session.setAttribute("yuanxiMap", yuanxiMap);

		// 设置专业map，在页面上把专业转换成专业名字
		List<Zhuanye> zhuanyes = jwcService.findAllZhuanye();
		Map<Integer, String> zhuanyeMap = new HashMap<>();
		for (int i = 0; i < zhuanyes.size(); i++) {
			zhuanyeMap.put(zhuanyes.get(i).getId(), zhuanyes.get(i).getName());
		}
		session.setAttribute("zhuanyeMap", zhuanyeMap);

		int rowCount = 0;// 每页显示条数
		int total = 0;// 数据总数
		int nowPage = Integer.valueOf(pageNum);// 当前页码
		int totalPage = 0;// 总页数

		switch (requestFrom) {
		case "pageYx":
			rowCount = 13;// 每页显示条数
			total = jwcService.yxcount();// 总数据数
			if (total % rowCount == 0) {
				totalPage = total / rowCount;
			} else {
				totalPage = total / rowCount + 1;
			}
			if (option.equals("pre")) {// 前一页，
				if (nowPage <= 1) {// 如果已经是第一页，就不操作
					List<Yuanxi> yuanxisss = jwcService.findInPage(1, rowCount);
					session.setAttribute("yxInfors", yuanxisss);
				} else {// 如果不是第一页，就查询前一页
					nowPage -= 1;
					List<Yuanxi> yuanxisss = jwcService.findInPage(nowPage, rowCount);
					session.setAttribute("yxInfors", yuanxisss);
				}
			} else if (option.equals("next")) {// 后一页
				if (nowPage >= totalPage) {// 如果已经是最后一页，就不操作
					List<Yuanxi> yuanxisss = jwcService.findInPage(totalPage, rowCount);
					session.setAttribute("yxInfors", yuanxisss);
				} else {// 如果不是最后一页，就查询下一页
					nowPage += 1;
					List<Yuanxi> yuanxisss = jwcService.findInPage(nowPage, rowCount);
					session.setAttribute("yxInfors", yuanxisss);
				}
			}

			session.setAttribute("pageNum", nowPage);
			session.setAttribute("totalPage", totalPage);
			// request.getRequestDispatcher("jwc/work_initial/yx.jsp").forward(request,
			// response);
			response.sendRedirect("jwc/work_import/yx.jsp");
			break;
		case "pageZy":
			rowCount = 3;// 每页显示条数
			total = jwcService.zycount();// 总数据数
			nowPage = Integer.valueOf(pageNum);// 当前页码
			totalPage = 0;// 总页数
			if (total % rowCount == 0) {
				totalPage = total / rowCount;
			} else {
				totalPage = total / rowCount + 1;
			}
			if (option.equals("pre")) {// 前一页，
				if (nowPage <= 1) {// 如果已经是第一页，就不操作
					List<Zhuanye> zhuanyess = jwcService.pageSearchZhuanYe(nowPage, rowCount);
					session.setAttribute("zyInfors", zhuanyess);
				} else {// 如果不是第一页，就查询前一页
					nowPage -= 1;
					List<Zhuanye> zhuanyess = jwcService.pageSearchZhuanYe(nowPage, rowCount);
					session.setAttribute("zyInfors", zhuanyess);
				}
			} else if (option.equals("next")) {// 后一页
				if (nowPage >= totalPage) {// 如果已经是最后一页，就不操作
					List<Zhuanye> zhuanyess = jwcService.pageSearchZhuanYe(nowPage, rowCount);
					session.setAttribute("zyInfors", zhuanyess);
				} else {// 如果不是最后一页，就查询下一页
					nowPage += 1;
					List<Zhuanye> zhuanyess = jwcService.pageSearchZhuanYe(nowPage, rowCount);
					session.setAttribute("zyInfors", zhuanyess);
				}
			}
			session.setAttribute("pageNum", nowPage);
			session.setAttribute("totalPage", totalPage);
			// request.getRequestDispatcher("jwc/work_initial/yx.jsp").forward(request,
			// response);
			response.sendRedirect("jwc/work_import/zy.jsp");
			break;

		default:
			break;
		}
	}

}
