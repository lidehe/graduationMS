package web.secretary;

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

import domain.Account;
import domain.Dbjsz;
import domain.Dbzl;
import domain.Jsgx;
import domain.Teacher;
import domain.Yuanxi;
import factory.JWCFactory;
import factory.ServiceFactory;
import net.sf.json.JSONArray;
import service.AccountService;
import service.JWCService;
import service.TeacherService;
import service.YearService;
import service.secretary.SecretaryService;

/**
 * 答辩组 Servlet implementation class Group
 */
@WebServlet("/Group.do")
public class Group extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SecretaryService secretaryService = new SecretaryService();
	private JWCService jwcService = JWCFactory.getJwcService();
	private TeacherService teacherService = ServiceFactory.getTeacherServiceInstance();
    private YearService yearService=ServiceFactory.getYearServiceInstance();
    private AccountService accountService=ServiceFactory.getAccountServiceInstance();
	public Group() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		JSONArray jsonArray = null;
		PrintWriter writer = response.getWriter();

		String method = "";
		method = request.getParameter("method");

		String yuanxiId = request.getParameter("yuanxiId");
		String option = request.getParameter("option");// 翻页选项、initial表示第一次请求，上一页、下一页
		String nowPage = request.getParameter("nowPage");// 当前页码
		String number = request.getParameter("number");// 教师工号/学生学号

		
		int rowCount = 0;// 每页显示条数
		int total = 0;// 数据总数

		int newPage = 0;// 当前页码
		if (!("".equals(nowPage) || nowPage == null))
			newPage = Integer.valueOf(nowPage);
		int totalPage = 0;// 总页数

		switch (method) {
		case "pageFindTearcherByYuanxiId":// 根据院系查找未分组的教师,分页查找
			rowCount = 5;// 设置每页显示10条数据
			List<Teacher> teachers = new ArrayList<>();
			teachers = teacherService.findUnGroupedTeacherByYuanxiId(Integer.valueOf(yuanxiId));
			// 当查到的数据大于0
			if (teachers.size() > 0) {
				total = teachers.size();// 总数据数

				if (total % rowCount == 0) {
					totalPage = total / rowCount;
				} else {
					totalPage = total / rowCount + 1;
				}
				List<Teacher> result = new ArrayList<>();
				if (option.equals("initial")) {// 初始页面，也就是刚选完院系
					result = teachers.subList(0, 5);

				} else if (option.endsWith("上一页")) {
					if (newPage <= 1) {// 如果已经是第一页，就不操作
						result = teachers.subList(0, 5);
					} else {// 如果不是第一页，就查询前一页
						newPage -= 1;
						result = teachers.subList((newPage - 1) * rowCount, newPage * rowCount);
					}

				} else if (option.endsWith("下一页")) {
					newPage += 1;
					if (newPage >= totalPage) {// 如果已经是最后一页
						result = teachers.subList((totalPage - 1) * rowCount, teachers.size());
						newPage = totalPage;
					} else {// 如果不是最后一页，就查询下一页
						result = teachers.subList((newPage - 1) * rowCount, newPage * rowCount);
					}
				}
				String jsonString = "[";
				String pageString = "{nowPage:'" + newPage + "',totalPage:'" + totalPage + "'},";
				jsonString += pageString;
				if (result.size() > 0)
					for (Teacher teacher : result) {
						String tempString = "";
						tempString += "{gonghao:'" + teacher.getNumber() + "',name:'" + teacher.getName() + "',yuanxi:'"
								+ getYuanxiMap().get(teacher.getYx()) + "'},";
						jsonString += tempString;
					}
				// System.out.println(jsonString);
				int strLength = jsonString.length();
				jsonString = jsonString.substring(0, strLength - 1);
				jsonString += "]";
				// System.out.println(jsonString);
				jsonArray = JSONArray.fromObject(jsonString);
				writer = response.getWriter();
				writer.println(jsonArray);
				writer.close();
			}
			break;

		case "searchByNumber":// 按照工号查询教师
			Teacher teacher = null;
			teacher = teacherService.findTeacherByNuber(number);
			boolean isGrouped = false;
			if (teacher == null) {
				writer.print("00");
				writer.close();
			} else {
				isGrouped = teacherService.isGroupedByNumber(number);
				if (!isGrouped) {
					String jString = "[{gonghao:'" + teacher.getNumber() + "',name:'" + teacher.getName() + "',yuanxi:'"
							+ getYuanxiMap().get(teacher.getYx()) + "'}]";
					jsonArray = JSONArray.fromObject(jString);
					writer = response.getWriter();
					writer.println(jsonArray);
					writer.close();
				} else {
					writer.print("11");
					writer.close();
				}
			}
			break;
		case "newGroup":// 新建答辩组
			newGroup(request, response);
			break;
		case "findAllDbzl":// 查找本院系所有已经存在的答辩组
			findAllDbzl(request, response);
			break;
		case "findDbzlDtetail":// 查看答辩组详情，根据答辩组名字
			findByDbzlDetail(request, response);
			break;
		case "changeZZ"://更改组长，传来新组长工号和答辩组名字，根据答辩组名字找到所在答辩组，根据答辩组id找到答辩教师组，然后把里面zz为1的设置为0，把传来的这个人设置为1
            changeZZ(request, response);
			break;
		case "deleteGroup"://删除答辩组，同时要删除答辩教师组
			deleteGroup(request, response);
			break;
		default:
			break;
		}

	}

	public void findAllDbzl(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String yuanxiId = request.getParameter("yuanxiId");
		PrintWriter writer = response.getWriter();
		JSONArray jsonArray = null;
		List<Dbzl> dbzls = new ArrayList<>();
		// 找到所有答辩组
		dbzls = secretaryService.findDbzlByYuanxiId(Integer.valueOf(yuanxiId));
		if (dbzls.size() > 0) {
			String jsonString = "[";
			for (Dbzl dbzl : dbzls) {
				String tempString = "";
				tempString += "{name:'" + dbzl.getName() + "',id:'" + dbzl.getId() + "'},";
				jsonString += tempString;
			}
			int strLength = jsonString.length();
			jsonString = jsonString.substring(0, strLength - 1);
			jsonString += "]";
			// System.out.println(jsonString);
			jsonArray = JSONArray.fromObject(jsonString);
			writer = response.getWriter();
			writer.println(jsonArray);
			writer.close();
		} else {
			writer.print("00");
			writer.close();
		}
		
	}
	public void newGroup(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String groupMemberNumber = request.getParameter("groupMemberNumber");// 答辩组成员工号，用：分割，第一个为组长，如123：53：534其中工号为123的是组长
		String msNames = request.getParameter("msNames");// 答辩秘书的名字
		String yuanxiId = request.getParameter("yuanxiId");

		String[] gonghaos = groupMemberNumber.split(":");
		PrintWriter writer = response.getWriter();

		// 找到刚刚新建的答辩组的组号
		if (gonghaos.length > 0) {
			int dbzCount = 0;// 答辩组总数，用于给答辩组命名，如答辩组1、答辩组2
			dbzCount = secretaryService.findAllDbzl().size();
			List<Dbzl> dbzls = new ArrayList<>();
			dbzls = secretaryService.findDbzlByYuanxiId(Integer.valueOf(yuanxiId));
			dbzCount = dbzls.size();
			Dbzl dbzl = new Dbzl();
			dbzCount++;
			String dbzlName = getYuanxiMap().get(Integer.valueOf(yuanxiId)) + "答辩组" + dbzCount;// 答辩组名字
			dbzl.setName(dbzlName);
			// dbzl.setStart(start);
			dbzl.setType(0);
			dbzl.setMs(msNames);
			dbzl.setSj("");
			dbzl.setYx(Integer.valueOf(yuanxiId));
			// 新建一个答辩组
			secretaryService.add(dbzl);

			Dbzl dbzl2 = secretaryService.findByName(dbzlName);
			int zh = dbzl2.getId();// 答辩组号，也就是ID
			for (int i = 0; i < gonghaos.length; i++) {
				//不仅要把该教师添加到答辩教师组，还要给TA分配“答辩组成员”角色
				Teacher teacher = new Teacher();
				teacher = secretaryService.findTeacherByNumber(gonghaos[i]);
				Dbjsz dbjsz = new Dbjsz();
				dbjsz.setGonghao(gonghaos[i]);
				dbjsz.setZh(zh);
				dbjsz.setYear(yearService.getNowYear().getYear());// 应该是变量，此处暂用固定值***************************应该是变量******************************应该是变量*********
				dbjsz.setYx(teacher.getYx());
				if (i == 0) {// 把第一个人设置为组长
					dbjsz.setZz(1);
				} else {
					dbjsz.setZz(0);
				}
				secretaryService.add(dbjsz);
				//分配“答辩组成员”角色,该角色id是8
				Jsgx jsgx=new Jsgx(gonghaos[i], 8);
				jwcService.addJsgx(jsgx);
				//分配了角色之后，就该分配账号到account表里了
				Account account=new Account(gonghaos[i], gonghaos[i], 0);
				accountService.saveAccount(account);
			}
			writer.print("新建成功");
			writer.close();
		} else {
			writer.print("新建答辩组失败，工号获取错误");
			writer.close();
		}
	}

	public void changeZZ(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String gonghao=request.getParameter("gonghao");
		String dbzlName=request.getParameter("dbzlName");
		Dbzl dbzl=secretaryService.findByName(dbzlName);
		List<Dbjsz> dbjszs=new ArrayList<>();
		dbjszs=secretaryService.findByDbzId(dbzl.getId());
		for(Dbjsz dbjsz:dbjszs){
			if (dbjsz.getGonghao().equals(gonghao)) {
				dbjsz.setZz(1);
			}else {
				dbjsz.setZz(0);
			}
			secretaryService.updateDbjsz(dbjsz);
			
		}
		
	}
	
	
	public void findByDbzlDetail(HttpServletRequest request, HttpServletResponse response) throws IOException{
		JSONArray jsonArray = null;
		String dbzName = request.getParameter("dbzlName");
		PrintWriter writer = response.getWriter();
		Dbzl dbzl = secretaryService.findByName(dbzName);
		List<Dbjsz> dbjszs = new ArrayList<>();
		if (dbzl != null) {
			dbjszs = secretaryService.findByDbzId(dbzl.getId());
			System.out.println("答辩教师组："+dbjszs.size());
			if (dbjszs.size() > 0) {
				String jString = "[";
				for (Dbjsz dbjsz : dbjszs) {
					String tempString = "";
					Teacher teacher2 = teacherService.findTeacherByNuber(dbjsz.getGonghao());
					String job = "";
					if (dbjsz.getZz() == 1) {
						job = "组长";
					} else {
						job = "成员";
					}
					tempString += "{gonghao:'" + teacher2.getNumber() + "',name:'" + teacher2.getName() + "',job:'"
							+ job + "'},";
					jString += tempString;
				}
				jString += "{msName:'" + dbzl.getMs() + "'}";
				jString += "]";
				// System.out.println(jsonString);
				jsonArray = JSONArray.fromObject(jString);
				writer = response.getWriter();
				writer.println(jsonArray);
				writer.close();

			} else {
				writer = response.getWriter();
				writer.print("11");
				writer.close();
			}
		} else {// 根据名字没有找到答辩组
			writer = response.getWriter();
			writer.print("00");
			writer.close();
		}
	}
	
	public void deleteGroup(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String dbzName = request.getParameter("dbzlName");
		PrintWriter writer = response.getWriter();
		Dbzl dbzl = secretaryService.findByName(dbzName);
		List<Dbjsz> dbjszs = new ArrayList<>();
		if (dbzl != null) {
			dbjszs=secretaryService.findByDbzId(dbzl.getId());
			for(Dbjsz dbjsz:dbjszs){
				secretaryService.delete(dbjsz);
			}
			secretaryService.deleteDbzl(dbzl);
			writer = response.getWriter();
			writer.print("11");
			writer.close();
		} else {// 根据名字没有找到答辩组
			writer = response.getWriter();
			writer.print("00");
			writer.close();
		}
	}
	
	public Map<Integer, String> getYuanxiMap() {
		List<Yuanxi> yuanxis = jwcService.findAllYuanxi();
		Map<Integer, String> yuanxiMap = new HashMap<>();
		for (int j = 0; j < yuanxis.size(); j++) {
			yuanxiMap.put(yuanxis.get(j).getId(), yuanxis.get(j).getName());
		}
		return yuanxiMap;
	}

}
