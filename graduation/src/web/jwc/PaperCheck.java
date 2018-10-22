package web.jwc;

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
import javax.servlet.http.HttpSession;

import domain.Cjbd;
import domain.Cjk;
import domain.Cjrz;
import domain.Jsgx;
import domain.Kt;
import domain.Student;
import domain.Teacher;
import domain.Yuanxi;
import domain.Zhuanye;
import factory.JWCFactory;
import factory.ServiceFactory;
import net.sf.json.JSONArray;
import service.JWCService;
import service.StudentService;
import service.TeacherService;
import service.YearService;

/**
 * Servlet implementation class PaperCheck
 */
@WebServlet("/PaperCheck.do")
public class PaperCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JWCService jwcService = JWCFactory.getJwcService();
	private TeacherService teacherService = ServiceFactory.getTeacherServiceInstance();
	private YearService yearService = ServiceFactory.getYearServiceInstance();
	private StudentService studentService = ServiceFactory.getStudentServiceInstance();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PaperCheck() {
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

		// 请求新的数据之前，先销毁旧的数据
		HttpSession session = request.getSession();
		session.removeAttribute("cjkInfors");
		session.removeAttribute("pageNum");
		session.removeAttribute("totalPage");
		session.removeAttribute("yuanxiMap");
		session.removeAttribute("zhuanyeMap");
		session.removeAttribute("ktMap");
		session.removeAttribute("chengjiMap");
		// 设置院系map，在页面上把院系id转换成院系名字
		session.setAttribute("yuanxiMap", yuanxiMap());

		// 设置专业map，在页面上把专业转换成专业名字
		List<Zhuanye> zhuanyes = jwcService.findAllZhuanye();
		Map<Integer, String> zhuanyeMap = new HashMap<>();
		for (int i = 0; i < zhuanyes.size(); i++) {
			zhuanyeMap.put(zhuanyes.get(i).getId(), zhuanyes.get(i).getName());
		}
		session.setAttribute("zhuanyeMap", zhuanyeMap);

		// 设置成绩等级map,在页面上把等级转换成等级名字
		Map<Integer, String> chengjiMap = new HashMap<>();
		chengjiMap.put(1, "优秀");
		chengjiMap.put(2, "良好");
		chengjiMap.put(3, "中等");
		chengjiMap.put(4, "及格");
		chengjiMap.put(5, "不及格");
		session.setAttribute("chengjiMap", chengjiMap);

		// 设置课题map，在页面上把课题转换成课题名字
		Map<Integer, String> ktMap = new HashMap<>();
		List<Kt> kts = new ArrayList<>();
		kts = jwcService.findAllKt();
		for (Kt kt : kts) {
			ktMap.put(kt.getId(), kt.getName());
		}
		session.setAttribute("ktMap", ktMap);

		// 按照method请求的处理，分别处理请求 ...
		switch (method) {
		case "confirmBind":// 抽检教师权限设置页面--执行绑定操作
			confirmBind(request, response);
			break;
		case "isBinded":// 抽检教师权限设置页面-=查询教师和院系是否已经绑定
			isBinded(request, response);
			break;
		case "findAllCjJs":// 抽检教师权限设置页面-查询所有抽检教师，以便于为他们绑定角色
			findAllCjJs(request, response);
			break;

		case "findChengJi":// 论文抽检结果页面--根据学号找成绩，也就是抽检日志(cjrz)里的成绩
			findAllCjJs(request, response);
			break;
		case "pageSearch":// 论文抽检结果页面--分页搜索抽检结果
			pageSearch(request, response);
			break;
		case "paperCheckInitial":// 论文抽检结果页面--初始化抽检结果页面
			paperCheckInitial(request, response);
			break;
		case "testRule":// 抽检规则设置页面-- 测试抽检规则，返回根据抽检规则获取到的数据分配
			testRule(request, response);
			break;
		case "confirmRule"://// 抽检规则设置页面-- 确认抽检规则，开始向抽检库写入数据
			confirmRule(request, response);
			break;
		default:
			break;
		}
	}

	/**
	 * 确定抽检规则
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void confirmRule(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		// 传递过来的抽检规则设置的尾号
		String tailNumber = request.getParameter("tailNumbers");
		List<Yuanxi> yuanxis = jwcService.findAllYuanxi();
		String[] tails = new String[tailNumber.length()];
		tails = new String[tailNumber.length()];// tailNumber.split("-");
		for (int i = 0; i < tailNumber.length(); i++) {
			tails[i] = tailNumber.substring(i, i + 1);
		}

		for (Yuanxi yuanxi : yuanxis) {
			List<Student> students = new ArrayList<>();
			students = studentService.findStudentByYuanxiAndTailNumber(yuanxi.getId(), tails);
			if (students.size() > 0) {
				for (Student student : students) {
					Cjk cjk = new Cjk();
					cjk.setXuehao(student.getNumber());
					cjk.setYx(student.getYx());
					cjk.setZy(student.getZy());
					cjk.setKt(student.getKt());
					cjk.setYear(yearService.getNowYear().getYear());
					jwcService.save(cjk);
				}
			}
		}
		writer = response.getWriter();
		writer.println("规则生效，已成功按规则筛选论文");

	}

	/**
	 * 测试抽检规则产生的结果
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void testRule(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JSONArray jsonArray = null;
		PrintWriter writer = response.getWriter();
		// 传递过来的抽检规则设置的尾号
		String tailNumber = request.getParameter("tailNumbers");
		List<Yuanxi> yuanxis = jwcService.findAllYuanxi();
		String[] tails = new String[tailNumber.length()];
		for (int i = 0; i < tailNumber.length(); i++) {
			tails[i] = tailNumber.substring(i, i + 1);
		}
		if (yuanxis.size() > 0) {
			String jsonStr = "[";
			for (Yuanxi yuanxi : yuanxis) {
				String tempStr = "{";
				int count = 0;
				count = studentService.countStudentByYuanxiAndTailNumber(yuanxi.getId(), tails);
				tempStr += "yuanxi:'" + yuanxi.getName() + "',count:'" + count + "'},";
				jsonStr += tempStr;
			}
			jsonStr = jsonStr.substring(0, jsonStr.length() - 1);
			jsonStr += "]";
			jsonArray = JSONArray.fromObject(jsonStr);
			writer = response.getWriter();
			writer.println(jsonArray);
		} else {
			writer.print("00");
		}
		writer.close();

	}

	/**
	 * 论文抽检结果初始化页面
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void paperCheckInitial(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 请求新的数据之前，先销毁旧的数据
		HttpSession session = request.getSession();
		// 分页开始***************************************************
		// 每页显示条数
		int rowCount = 0;
		// 数据总数
		int total = 0;
		// 当前页码
		int totalPage = 0;// 总页数
		rowCount = 10;// 每页显示条数
		total = jwcService.countCjk();// 总数据数
		if (total % rowCount == 0) {
			totalPage = total / rowCount;
		} else {
			totalPage = total / rowCount + 1;
		}
		List<Cjk> cjks = jwcService.findCjkInPage(1, rowCount);
		session.setAttribute("cjkInfors", cjks);
		session.setAttribute("pageNum", 1);
		session.setAttribute("totalPage", totalPage);
		// request.getRequestDispatcher("jwc/work_initial/yx.jsp").forward(request,
		response.sendRedirect("jwc/work_paperCheck/checkResult.jsp");
	}

	/**
	 * 分页查询抽检结果
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void pageSearch(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 请求新的数据之前，先销毁旧的数据
		HttpSession session = request.getSession();
		// 分页开始***************************************************
		String pageNum;// 当前页码
		pageNum = request.getParameter("pageNum");
		String option;// 操作，前一页还是后一页
		option = request.getParameter("option");
		// 每页显示条数
		int rowCount = 0;
		// 数据总数
		int total = 0;
		// 当前页码
		int nowPage = 0;
		if (!("".equals(pageNum) || pageNum == null))
			nowPage = Integer.valueOf(pageNum);
		int totalPage = 0;// 总页数

		rowCount = 10;// 每页显示条数
		total = jwcService.countCjk();// 总数据数
		if (total % rowCount == 0) {
			totalPage = total / rowCount;
		} else {
			totalPage = total / rowCount + 1;
		}
		if (option.equals("pre")) {// 前一页，
			if (nowPage <= 1) {// 如果已经是第一页，就不操作
				List<Cjk> cjk = jwcService.findCjkInPage(1, rowCount);
				session.setAttribute("cjkInfors", cjk);
			} else {// 如果不是第一页，就查询前一页
				nowPage -= 1;
				List<Cjk> cjk = jwcService.findCjkInPage(nowPage, rowCount);
				session.setAttribute("cjkInfors", cjk);
			}
		} else if (option.equals("next")) {// 后一页
			if (nowPage >= totalPage) {// 如果已经是最后一页，就不操作
				List<Cjk> cjk = jwcService.findCjkInPage(nowPage, rowCount);
				session.setAttribute("cjkInfors", cjk);
			} else {// 如果不是最后一页，就查询下一页
				nowPage += 1;
				List<Cjk> cjk = jwcService.findCjkInPage(nowPage, rowCount);
				session.setAttribute("cjkInfors", cjk);
			}
		}

		session.setAttribute("pageNum", nowPage);
		session.setAttribute("totalPage", totalPage);
		// request.getRequestDispatcher("jwc/work_initial/yx.jsp").forward(request,
		// response);
		response.sendRedirect("jwc/work_paperCheck/checkResult.jsp");

	}

	/**
	 * 抽检成绩查询
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void findChengJi(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 传递过来的工号
		String number = request.getParameter("number");
		JSONArray jsonArray = null;
		PrintWriter writer = response.getWriter();
		List<Cjrz> cjrzs = new ArrayList<>();
		cjrzs = jwcService.findByXuehao(number);
		String jsonString = "[";
		for (Cjrz cjrz : cjrzs) {
			Teacher teacher = teacherService.findTeacherByNuber(cjrz.getGonghao());
			String tempString = "{";
			tempString += "teacher:'" + teacher.getName() + "',chengJi:'" + cjrz.getDj() + "',pingyu:'" + cjrz.getText()
					+ "'},";
			jsonString += tempString;
		}
		jsonString = jsonString.substring(0, jsonString.length() - 1);
		jsonString += "]";
		jsonArray = JSONArray.fromObject(jsonString);
		writer = response.getWriter();
		writer.println(jsonArray);
		writer.close();

	}

	/**
	 * 查询所有抽检教师
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void findAllCjJs(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JSONArray jsonArray = null;
		PrintWriter writer = response.getWriter();
		List<Jsgx> jsgxs = new ArrayList<>();
		// 根据角色关系为抽检教师（id为7），查到该角色的所有教师,以json形式返回到前端
		jsgxs = jwcService.findJsgxByJsID(7);
		if (jsgxs.size() > 0) {
			String jString = "[";
			for (Jsgx jsgx : jsgxs) {
				Teacher teacher = teacherService.findTeacherByNuber(jsgx.getGonghao());
				String tempString = "{";
				tempString += "yuanxi:'" + yuanxiMap().get(teacher.getYx()) + "',gonghao:'" + teacher.getNumber()
						+ "',xingming:'" + teacher.getName() + "'},";
				jString += tempString;
			}
			jString = jString.substring(0, jString.length() - 1);
			jString += "]";
			jsonArray = JSONArray.fromObject(jString);
			writer = response.getWriter();
			writer.println(jsonArray);
			writer.close();
		} else {
			writer = response.getWriter();
			writer.println("尚未分配抽检人员，请先分配");
			writer.close();
		}

	}

	/**
	 * 抽检教师是否已经绑定院系
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void isBinded(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		// 传递过来的工号
		String number = request.getParameter("number");
		List<Cjbd> bindeResult = new ArrayList<>();
		bindeResult = jwcService.findCjbdByGonghao(number);
		String bindedYxId = "";
		if (bindeResult.size() > 0) {
			for (Cjbd cjbd : bindeResult) {
				bindedYxId += cjbd.getYx() + ":";
			}
			writer = response.getWriter();
			writer.print(bindedYxId);
			writer.close();
		} else {
			writer = response.getWriter();
			writer.print("未绑定任何院系");
			writer.close();
		}

	}

	/**
	 * 确认绑定抽检教师和院系
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void confirmBind(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 传递过来的工号
		String number = request.getParameter("number");
		String yuanxiId = request.getParameter("yuanxiId");

		String[] yxIdStr = yuanxiId.split(":");// 获取传来的院系ID串
		jwcService.batchDeleteCjbdByGonghao(number);
		// 如果院系ID有效，
		if (yxIdStr.length > 0 && !yuanxiId.equals("")) {
			for (int i = 0; i < yxIdStr.length; i++) { // 转换成int数组
				Cjbd cjbd = new Cjbd();
				cjbd.setGonghao(number);
				cjbd.setYx(Integer.valueOf(yxIdStr[i]));
				cjbd.setYear(yearService.getNowYear().getYear());
				jwcService.saveCjbd(cjbd);
			}
		}
		jwcService.findCjbdByGonghao(number);
	}

	public Map<Integer, String> yuanxiMap() {
		List<Yuanxi> yuanxis = jwcService.findAllYuanxi();
		Map<Integer, String> yuanxiMap = new HashMap<>();
		for (int j = 0; j < yuanxis.size(); j++) {
			yuanxiMap.put(yuanxis.get(j).getId(), yuanxis.get(j).getName());
		}
		return yuanxiMap;

	}
}
