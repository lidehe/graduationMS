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
 * 初始化 导入信息页面
 */
/**
 * Servlet implementation class InitialPage
 */
@WebServlet("/InitialPage.do")
public class InitialPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private JWCService jwcService=JWCFactory.getJwcService();
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InitialPage() {
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
		String requestFrom;// 请求源
		requestFrom = request.getParameter("requestFrom");
		System.out.println("请求源是"+requestFrom);
		//请求新的数据之前，先销毁旧的数据
		HttpSession session = request.getSession();
		session.removeAttribute("yxInfors");
		session.removeAttribute("zyInfors");
		session.removeAttribute("pageNum");
		session.removeAttribute("totalPage");
		session.removeAttribute("yuanxiMap");
		session.removeAttribute("zhuanyeMap");
		//设置院系map，在页面上把院系id转换成院系名字
		List<Yuanxi> yuanxis = jwcService.findAllYuanxi();
		Map<Integer, String> yuanxiMap = new HashMap<>();
		for (int j = 0; j < yuanxis.size(); j++) {
			yuanxiMap.put(yuanxis.get(j).getId(),yuanxis.get(j).getName());
		}
		session.setAttribute("yuanxiMap", yuanxiMap);
		
		//设置专业map，在页面上把专业转换成专业名字
		List<Zhuanye> zhuanyes = jwcService.findAllZhuanye();
		Map<Integer, String> zhuanyeMap = new HashMap<>();
		for (int i = 0; i < zhuanyes.size(); i++) {
			zhuanyeMap.put( zhuanyes.get(i).getId(),zhuanyes.get(i).getName());
		}
		session.setAttribute("zhuanyeMap", zhuanyeMap);
		
		int rowCount=0;//每页显示条数
		int total=0;//数据总数
		switch (requestFrom) {
		case "initial_yuanxi":
			rowCount=10;
			total=jwcService.yxcount();
			List<Yuanxi> yuanxisss = jwcService.findInPage(1, rowCount);
			session.setAttribute("yxInfors", yuanxisss);
			session.setAttribute("pageNum", 1);
			if (total % rowCount == 0) {
				session.setAttribute("totalPage", total/ rowCount);
			} else {
				session.setAttribute("totalPage",total / rowCount + 1);
			}
			response.sendRedirect("jwc/work_import/yx.jsp");
			break;
		case "initial_zhuanye":
			rowCount=4;
			total=jwcService.zycount();
			List<Zhuanye> zhuanyess=jwcService.pageSearchZhuanYe(1, rowCount);
			session.setAttribute("zyInfors", zhuanyess);
			session.setAttribute("pageNum", 1);
			if (total % rowCount == 0) {
				session.setAttribute("totalPage", total / rowCount);
			} else {
				session.setAttribute("totalPage", total / rowCount + 1);
			}
			response.sendRedirect("jwc/work_import/zy.jsp");
			break;

		default:
			break;
		}
	}

}
