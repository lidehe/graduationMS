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

import dao.TeacherDao;
import domain.Js;
import domain.Jsgx;
import domain.Teacher;
import factory.JWCFactory;
import factory.TeacherDaoFactory;
import net.sf.json.JSONArray;
import service.JWCService;
import utils.TempClass;

/**
 * Servlet implementation class Jsgx 角色关系控制层
 */
@WebServlet("/Jsgx.do")
public class JsgxWeb extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JWCService jwcService = JWCFactory.getJwcService();
	private TeacherDao teacherDao = TeacherDaoFactory.getTeacherDaoInstance();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public JsgxWeb() {
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
		String YuanxiID = request.getParameter("yuanxiId");
		String method = request.getParameter("method");
		String JsID = request.getParameter("JsID");
		String[] ids;// 教师ID
		ids = request.getParameterValues("ids");

		String requireFrom = request.getParameter("requireFrom");
		switch (method) {
		case "add":
			for (int i = 0; i < ids.length; i++) {
				Teacher teacher = teacherDao.findTeahcerById(Integer.valueOf(ids[i]));
				Jsgx jsgx = new Jsgx(teacher.getNumber(), Integer.valueOf(JsID));
				jwcService.addJsgx(jsgx);
			}
			break;
		case "findAllJs":
			List<Js> jsss = new ArrayList<>();
			jsss = jwcService.findAllJs();
			PrintWriter writerJs = response.getWriter();
			JSONArray jsonArray = JSONArray.fromObject(jsss);
			writerJs.println(jsonArray);
			break;
		case "findJsByRequireFrom":
			List<Js> jsList = new ArrayList<>();
			jsList = jwcService.findAllJs();
			List<Js> jsList2 = new ArrayList<>();
			for (int i = 0; i < jsList.size(); i++) {
				Js js = new Js();
				js = jsList.get(i);
				if (requireFrom == null) {
					jsList2.add(js);
				} else if (requireFrom.equals("jwc")) {// 教务处可设置的角色只包括：教学秘书、分院长、抽检、评优
					if (js.getName().equals("院系评优人员") || js.getName().equals("专业负责人") || js.getName().equals("指导老师")
							|| js.getName().equals("答辩组成员")) {
					} else {
						jsList2.add(js);
					}
				} else {// 教学秘书可以设置的角色“不可以”包括：教学秘书、分院长、抽检、评优
					if (js.getName().equals("院系评优人员") || js.getName().equals("专业负责人") || js.getName().equals("指导老师")
							|| js.getName().equals("答辩组成员")) {
						jsList2.add(js);
					} else {

					}
				}
			}
			PrintWriter writerJss = response.getWriter();
			JSONArray jsonArrays = JSONArray.fromObject(jsList2);
			writerJss.println(jsonArrays);
			break;
		case "findAllTeacher":
			List<Teacher> teachers = new ArrayList<>();
			teachers = teacherDao.findTeacherByYuanxi(Integer.valueOf(YuanxiID));
			List<TempClass> tempList = new ArrayList<>();
			for (int i = 0; i < teachers.size(); i++) {
				List<Js> jss = new ArrayList<>();
				TempClass tempClass = new TempClass();
				tempClass.setId(teachers.get(i).getId());
				tempClass.setParam1(teachers.get(i).getNumber());
				tempClass.setParam2(teachers.get(i).getName());
				jss = jwcService.findJsByTeacherNumber(teachers.get(i).getNumber());
				String position = "";
				if (jss.size() == 0) {
					position += "暂无职位";
				} else {
					for (int j = 0; j < jss.size(); j++) {
						// System.out.println("职务是："+j+jss.get(j).getName());
						position += jss.get(j).getName() + ":";
					}
				}
				tempClass.setParam3(position);
				tempList.add(tempClass);
			}
			PrintWriter writerteacher = response.getWriter();
			JSONArray jsonArrayteacherList = JSONArray.fromObject(tempList);
			writerteacher.println(jsonArrayteacherList);
			break;
		default:
			break;
		}

	}

}
