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

import domain.Account;
import domain.Js;
import domain.Jsgx;
import domain.Teacher;
import domain.Yuanxi;
import factory.JWCFactory;
import factory.ServiceFactory;
import net.sf.json.JSONArray;
import service.AccountService;
import service.JWCService;
import service.TeacherService;
import utilsOffice.WriteExcel;

/**
 * Servlet implementation class RoleSet
 */
@WebServlet("/RoleSet.do")
public class RoleSet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JWCService jwcService = JWCFactory.getJwcService();
	private TeacherService teacherService = ServiceFactory.getTeacherServiceInstance();
	private AccountService accountService = ServiceFactory.getAccountServiceInstance();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RoleSet() {
		super();
		// TODO Auto-generated constructor stub
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

		// 设置院系map，在页面上把院系id转换成院系名字
		HttpSession session = request.getSession();
		session.removeAttribute("yuanxiMap");
		session.removeAttribute("zhuanyeMap");
		session.setAttribute("yuanxiMap", yuanxiMap());

		String method = request.getParameter("method");

		switch (method) {
		case "pageFindTearcherByYuanxiId":// 根据院系查找专业,分页查找
			pageFindTearcherByYuanxiId(request, response);
			break;
		case "searchByNumber":// 按照工号查询教师
			searchByNumber(request, response);
			break;
		case "setRoles":// 设定角色
			setRole(request, response);
			break;
		case "searchTeacherByJsId":// 根据角色ID找教师
			searchTeacherByJsId(request, response);
			break;
		case "deleteTeacherFromRole":// 删除某角色下的一个教职工
			deleteTeacherFromRole(request, response);
			break;
		case "exportTeacherOfRules":// 导出该角色下的教师
            exportTeacherOfRules(request, response);
			break;
		}
	}

	public void exportTeacherOfRules(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String jsID = request.getParameter("JsID");// 角色ID
        Js js=jwcService.findJsById(Integer.valueOf(jsID));
		String fileName="全校"+js.getName()+"表";
		PrintWriter writer = response.getWriter();
		
		List<Teacher> teacherList = new ArrayList<>();
		teacherList = teacherService.findTeacherByJsId(Integer.valueOf(jsID));
		System.out.println("根据角色导出教师的数量是：" + teacherList.size());
		// 当查到的数据大于0
		boolean result=true;
		if (teacherList.size() > 0) {
			List<String[]> list=new ArrayList<>();
			for (Teacher teach : teacherList) {
				//按照院系、工号、姓名的方式给出
				String [] infors={ yuanxiMap().get(teach.getYx()),teach.getNumber(),teach.getName()};
				list.add(infors);
			}
			
			String path = request.getSession().getServletContext()
					.getRealPath("FileCache/");
			result=WriteExcel.makeNewExcel(path, list, js.getName(), fileName);
		}else {
			result=false;
		}
		
		if (result) {
			writer.print(fileName+".xlsx");
		}else {
			writer.print("00");
		}
        writer.close();
	}

	public void pageFindTearcherByYuanxiId(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String yuanxiIdStr = request.getParameter("yuanxiId");
		String option = request.getParameter("option");// 翻页选项、initial表示第一次请求，上一页、下一页
		String nowPagestr = request.getParameter("nowPage");// 当前页码
		String jsIDStr = request.getParameter("JsID");// 角色ID

		JSONArray jsonArray = null;
		PrintWriter writer = null;

		int nowPage = Integer.valueOf(nowPagestr);
		int yuanxiId = Integer.valueOf(yuanxiIdStr);
		int jsID = Integer.valueOf(jsIDStr);
		List<Teacher> teachers = new ArrayList<>();

		
		if (option.endsWith("上一页")) {
			if (nowPage <= 1) {// 如果已经是第一页，就不操作
				teachers = teacherService.pageFindTeacherByTeacherAndJsgx(yuanxiId, jsID, nowPage);
			} else {// 如果不是第一页，就查询前一页
				nowPage -= 1;
				teachers = teacherService.pageFindTeacherByTeacherAndJsgx(yuanxiId, jsID, nowPage);
			}
		} else if (option.endsWith("下一页")) {
			++nowPage;
			teachers = teacherService.pageFindTeacherByTeacherAndJsgx(yuanxiId, jsID, nowPage);
			if (teachers.size() == 0) {
				--nowPage;
				writer = response.getWriter();
				writer.print("lastPage");
				writer.close();
				return;
			}

		}
		if (teachers.size() > 0) {
			String jsonString  = "[";
			int all=0;
			all=teacherService.CountTeacherByTeacherAndJsgx(yuanxiId, jsID);
			int totalPage=0;
			if (all%10==0) {
				totalPage=all/10;
			}else{
				totalPage=all/10+1;
			}
			String pageString = "{nowPage:'" + nowPage + "',totalPage:'"+totalPage+"'},";
			jsonString += pageString;
			for (Teacher teacher : teachers) {
				String tempString = "";
				tempString += "{gonghao:'" + teacher.getNumber() + "',name:'" + teacher.getName() + "',yuanxi:'"
						+ yuanxiMap().get(teacher.getYx()) + "'},";
				jsonString += tempString;
			}
			jsonString = jsonString.substring(0, jsonString.length() - 1);
			jsonString += "]";
			jsonArray = JSONArray.fromObject(jsonString);
			writer = response.getWriter();
			writer.println(jsonArray);
			writer.close();
		}else{
			writer = response.getWriter();
			writer.print("00");
			writer.close();
		}

	}

	public void searchByNumber(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String key = request.getParameter("number");// 教师工号

		JSONArray jsonArray = null;
		PrintWriter writer = null;
		String regex="[\u4e00-\u9fa5]";//这种方式仅支持一个一个匹配
		char c[] = key.toCharArray();
		int count=0;
		for(int i=0;i<c.length;i++){
			if (String.valueOf(c[i]).matches(regex)) {
				break;
			}
			count++;
		}
		if (count==c.length) {//如果是字母+数字，就按工号搜索
			Teacher teacher = new Teacher();
			teacher = teacherService.findTeacherByNuber(key);
			if (teacher != null) {
				String jString = "[{gonghao:'" + teacher.getNumber() + "',name:'" + teacher.getName() + "',yuanxi:'"+ yuanxiMap().get(teacher.getYx()) + "'}]";
				jsonArray = JSONArray.fromObject(jString);
				writer = response.getWriter();
				writer.println(jsonArray);
				writer.close();
			}
		}else{//汉字，就是按名字搜索
			List<Teacher> teachers=teacherService.vagueSearchByName(key);
			if (teachers.size()>0) {
				String jString = "[";
				for(Teacher teacher:teachers){
					String tempstr="{gonghao:'" + teacher.getNumber() + "',name:'" + teacher.getName() + "',yuanxi:'"+ yuanxiMap().get(teacher.getYx()) + "'},";
					jString+=tempstr;
				}
				jString=jString.substring(0,jString.length()-1);
				jString+="]";
				jsonArray = JSONArray.fromObject(jString);
				writer = response.getWriter();
				writer.println(jsonArray);
				writer.close();
			}
		}
	}

	public void setRole(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = null;
		String numberStr = request.getParameter("numberStr");// 教职工工号集合字串
		String jsID = request.getParameter("JsID");// 角色ID
		int jsId = Integer.valueOf(jsID);
		String[] numbers = numberStr.split(":");
		for (String s : numbers) {
			System.out.println("工号：" + s);
			Jsgx jsgx = new Jsgx(s, jsId);
			jwcService.addJsgx(jsgx);
			// 账号信息放到tb_account里，这样才教师能登陆。教师类型为0
			Account account = new Account(s, s, 0);
			accountService.saveAccount(account);
		}
		writer = response.getWriter();
		writer.println("角色添加成功！");
		writer.close();

	}

	public void searchTeacherByJsId(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String jsID = request.getParameter("JsID");// 角色ID

		JSONArray jsonArray = null;
		PrintWriter writer = null;

		List<Teacher> teacherList = new ArrayList<>();
		teacherList = teacherService.findTeacherByJsId(Integer.valueOf(jsID));
		System.out.println("根据角色ID找教师" + teacherList.size());
		// 当查到的数据大于0
		if (teacherList.size() > 0) {
			String jsonString = "[";
			for (Teacher teach : teacherList) {
				String tempString = "";
				tempString += "{gonghao:'" + teach.getNumber() + "',name:'" + teach.getName() + "',yuanxi:'"
						+ yuanxiMap().get(teach.getYx()) + "'},";
				jsonString += tempString;
			}
			// System.out.println(jsonString);
			int strLength = jsonString.length();
			jsonString = jsonString.substring(0, strLength - 1);
			jsonString += "]";
			System.out.println(jsonString);
			jsonArray = JSONArray.fromObject(jsonString);
			writer = response.getWriter();
			writer.println(jsonArray);
			writer.close();
		}
	}

	public void deleteTeacherFromRole(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String number = request.getParameter("number");// 教师工号
		String jsID = request.getParameter("JsID");// 角色ID

		PrintWriter writer = null;

		System.out.println("解除角色" + number + ":" + jsID);
		Jsgx jsgx = null;
		jsgx = jwcService.findJsgxByNumberAndJsId(number, Integer.valueOf(jsID));
		if (jsgx != null) {
			jwcService.deleteJsgx(jsgx);
			// 如果这个教师已经不担任任何角色，就从tb_account表里删除
			List<Jsgx> jsgxs = new ArrayList<>();
			jsgxs = jwcService.findJsgxByTeacherNumber(number);
			if (jsgxs.size() <= 0) {
				Account account = accountService.getAccountBYNumber(number);
				if (account != null)
					accountService.deleteAccount(account);
			}

			writer = response.getWriter();
			writer.println("成功解除角色绑定");
			writer.close();
		} else {
			writer = response.getWriter();
			writer.println("解除角色绑定失败！");
			writer.close();
		}
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
