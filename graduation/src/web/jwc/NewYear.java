package web.jwc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.Account;
import domain.Byfs;
import domain.Jsgx;
import domain.Teacher;
import domain.Year;
import factory.JWCFactory;
import factory.ServiceFactory;
import history.DBManage;
import service.AccountService;
import service.JWCService;
import service.TeacherService;
import service.YearService;

/**
 * 开启新一届的工作 Servlet implementation class NewYear
 */
@WebServlet("/NewYear.do")
public class NewYear extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private YearService yearService = ServiceFactory.getYearServiceInstance();
	private JWCService jwcService = JWCFactory.getJwcService();
	private AccountService accountService = ServiceFactory.getAccountServiceInstance();
	private TeacherService teacherService = ServiceFactory.getTeacherServiceInstance();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NewYear() {
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
		case "startNewYear":// 开启新一届毕设工作，新建一个数据库（graduation_即将开启的届数），同时把hibernate配置文件里的链接参数指向新建的数据库
			startNewYear(request, response);
			break;
		default:
			break;
		}
	}

	public void getNewYearNumber(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/**
	 * 开启新的一届工作，涉及的操作有：历史数据转移处理，管理员账号初始化，
	 * 
	 * 首先，新建三个数据库，graduation_1，graduation_2，graduation_3,如果数据库已经存在，原有数据不会被覆盖，
	 * 其次，复制数据库信息，graduation_2复制到graduation_3,graduation_1复制到graduation_2，
	 * graduation_db复制到graduation_1 然后清空当前数据库graduation_db
	 * 
	 * 年份的处理 首先设定当前年份 其次初始化年份，如果超过4年，则删除最小的一年
	 * 
	 * 管理员账号初始化 现在tb_accunt里添加一个账号，tb_teachher里添加相应的账号，角色管理表里添加对应关系
	 */
	public void startNewYear(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		PrintWriter writer = response.getWriter();
		//HttpSession session = request.getSession();
		// 获取传过来的年份，如果年份小于当前届数，提示要求输入更大的年份
		String yearString = request.getParameter("year");
		Year year = yearService.getNowYear();
		if (year.getYear() >= Integer.valueOf(yearString)) {
			// 输入的届数等于或小于当前届数，怎不能初始化
			writer.print("01");
			writer.close();
		} else {
			try {
				// 历史数据转移处理*******************************************************************************************************************************************
				// 首先，新建三个数据库，graduation_1，graduation_2，graduation_3,如果数据库已经存在，原有数据不会被覆盖，
				if (DBManage.makeNewDb("graduation_1")) {
					if (DBManage.makeNewDb("graduation_2")) {
						if (DBManage.makeNewDb("graduation_3")) {
							if (DBManage.copyDB("graduation_2", "graduation_3")) {
								if (DBManage.copyDB("graduation_1", "graduation_2")) {
									if (DBManage.copyDB("graduation_db", "graduation_1")) {
										DBManage.clearDb();
									} else {
										System.out.println("graduation_db, graduation_1失败");
									}
								} else {
									System.out.println("graduation_1,graduation_2失败");
								}
							} else {
								System.out.println("graduation_2, graduation_3失败");
							}
						} else {
							System.out.println("new graduation_3 失败");
						}
					} else {
						System.out.println("new graduation_2 失败");
					}
				} else {
					System.out.println("new graduation_1 失败");
				}

				// 年份初始化，删除从后往前数第四个年份的数据
				yearService.initialYear();
				// 然后保存本届设定的年份
				Year year2 = new Year(Integer.valueOf(yearString));
				yearService.setNowYear(year2);

				// 管理员账号初始化***************************************************************************************************************************************************
				// tb_account里添加一个账号
				Account account = new Account();
				account.setAccount("admin");
				account.setPassword("admin123");
				account.setType(0);
				accountService.saveAccount(account);
				// tb_teachher里添加相应的账号，角色管理表里添加对应关系
				Teacher teacher = new Teacher();
				teacher.setNumber("admin");
				teacher.setName("超级管理员");
				teacherService.addTeacher(teacher);
				// tb_jsgx角色管理表里添加对应关系
				Jsgx jsgx = new Jsgx("admin", 5);
				jwcService.addJsgx(jsgx);

				// 更新毕业方式的时间控制
				byfsInitial();

				//session.removeAttribute("teacher");
				writer.print("11");
				writer.close();
			} catch (Exception e) {
				writer.print("00");
				writer.close();
				e.printStackTrace();
			}
		}

	}

	// 毕业方式初始化，包括删除去年新建的毕业方式，模板里阶段截至时间年份+1
	@SuppressWarnings("deprecation")
	public void byfsInitial() {
		Byfs byfs = jwcService.findByfsByID(1);
		Date date = null;
		date = byfs.getStart1();
		date.setYear(date.getYear() + 1);
		byfs.setStart1(date);

		date = byfs.getStart2();
		date.setYear(date.getYear() + 1);
		byfs.setStart2(date);

		date = byfs.getStart3();
		date.setYear(date.getYear() + 1);
		byfs.setStart3(date);

		date = byfs.getStart4();
		date.setYear(date.getYear() + 1);
		byfs.setStart4(date);

		date = byfs.getStart5();
		date.setYear(date.getYear() + 1);
		byfs.setStart5(date);

		date = byfs.getEnd1();
		date.setYear(date.getYear() + 1);
		byfs.setEnd1(date);

		date = byfs.getEnd2();
		date.setYear(date.getYear() + 1);
		byfs.setEnd2(date);

		date = byfs.getEnd3();
		date.setYear(date.getYear() + 1);
		byfs.setEnd3(date);

		date = byfs.getEnd4();
		date.setYear(date.getYear() + 1);
		byfs.setEnd4(date);

		date = byfs.getEnd5();
		date.setYear(date.getYear() + 1);
		byfs.setEnd5(date);

		jwcService.updateByfs(byfs);
		List<Byfs> byfss = new ArrayList<>();
		byfss = jwcService.findAllByfs();
		Iterator<Byfs> iterator = byfss.iterator();
		while (iterator.hasNext()) {
			Byfs byfs2 = iterator.next();
			if (byfs2.getId() != 1) {
				jwcService.deleteByfs(byfs2);
			}

		}

	}

}
