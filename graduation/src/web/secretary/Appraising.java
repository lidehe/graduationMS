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

import domain.Student;
import domain.Yuanxi;
import domain.Yxlw;
import domain.Yxlwx;
import domain.Yxlwz;
import domain.Zhuanye;
import factory.JWCFactory;
import factory.ServiceFactory;
import net.sf.json.JSONArray;
import service.JWCService;
import service.StudentService;
import service.WorkControlService;

/**
 * 
 * 优秀论文 Servlet implementation class Appraising
 */
@WebServlet("/YuanAppraising.do")
public class Appraising extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JWCService jwcService = JWCFactory.getJwcService();
	private StudentService studentService = ServiceFactory.getStudentServiceInstance();
	private WorkControlService workControlService=ServiceFactory.getWorkcontrolInstance();
	public Appraising() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String method = request.getParameter("method");

		System.out.println("请求的方法是：" + method);
		switch (method) {
		case "getYxPaperCount":// 根据院系ID获取该院系个人和小组优秀论文推荐数量限制信息
			getYxPaperCount(request, response);
			break;
		case "findYuanPersonalYxlw":// 获取院内为推荐-个人-优秀论文
			findYuanPersonalYxlw(request, response);
			break;
		case "findYuanPersonalRecommedYxlw":// -获取院内已经推荐到校-个人-优秀论文
			findYuanPersonalRecommendedYxlw(request, response);
			break;
		case "findYuangroupYxlw":// 校级优秀论文页面---获取校级未推荐-小组-优秀论文
			findYuangroupYxlw(request, response);
			break;
		case "findYuanGroupRecommedYxlw":// -获取院内已经推荐到校-小组-优秀论文
			findYuangroupRecommendedYxlw(request, response);
			break;
		case "setXiaoPersonalYxlw":// 设置论文为校级优秀论文
			setXiaoPersonalYxlw(request, response);
			break;
		case "setXiaoGrouplYxlw":// 设置论文为校级优秀论文
			setXiaoGrouplYxlw(request, response);
			break;
		case "cancelXiaoPersonalYxlw":// 取消设置个人论文为校级优秀论文
			cancelXiaoPersoanlYxlw(request, response);
			break;
		case "cancelXiaoGrouplYxlw":// 取消设置个人论文为校级优秀论文
			cancelXiaoGroupYxlw(request, response);
			break;
		}

	}

	// 根据院系ID获取该院系个人和小组优秀论文推荐数量限制信息
	public void getYxPaperCount(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String yuanxiId = request.getParameter("yuanxiId");
		String jsonStr = "[";
		String tempStr = "{ ";
		// 某院系个人论文数量限制
		Yxlwx yxlwxPersional = null;
		yxlwxPersional = jwcService.findByYxAndType(Integer.valueOf(yuanxiId), 0);
		if (yxlwxPersional == null) {
			tempStr += "personalCount:'0',";
		} else {
			tempStr += "personalCount:'" + yxlwxPersional.getSl() + "',";
		}

		// 某院系小组论文数量限制
		Yxlwx yxlwxGroup = null;
		yxlwxGroup = jwcService.findByYxAndType(Integer.valueOf(yuanxiId), 1);
		if (yxlwxGroup == null) {
			tempStr += "groupCount:'0'},";
		} else {
			tempStr += "groupCount:'" + yxlwxGroup.getSl() + "'}";
		}
		jsonStr += tempStr;
		jsonStr += "]";
		jsonArryWriter(jsonStr, request, response);

	}

	// 获取还 未推 荐的-个人-优秀论文
	public void findYuanPersonalYxlw(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String yuanxiId = request.getParameter("yuanxiId");
		List<Yxlw> yxlws = new ArrayList<>();
		yxlws = jwcService.findYxlwByYuanxiId(Integer.valueOf(yuanxiId));

		if (yxlws.size() > 0) {// 如果有记录
			String jsonStr = "[";
			for (Yxlw yxlw : yxlws) {
				Student student = studentService.findStudentByNumber(yxlw.getXuehao());
				String tempStr = "";
				tempStr += "{xuehao:'" + yxlw.getXuehao() + "',name:'" + student.getName() + "',zhuanye:'"
						+ getZhuanyeMap().get(student.getZy()) + "',lunwen:'"
						+ jwcService.findLwById(yxlw.getLw()).getFileName() + "',yuanfenshu:'" + yxlw.getYfs() + "'},";
				jsonStr += tempStr;
			}
			jsonStr = jsonStr.substring(0, jsonStr.length() - 1);
			simpleStringWriter(jsonStr, request, response);

		} else {
			simpleStringWriter("00", request, response);
		}
	}

	// 获取还 已经 推荐的-个人-优秀论文
	public void findYuanPersonalRecommendedYxlw(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String yuanxiId = request.getParameter("yuanxiId");
		List<Yxlw> yxlws = new ArrayList<>();
		yxlws = jwcService.findYxlwByYuanxiId(Integer.valueOf(yuanxiId));

		if (yxlws.size() > 0) {// 如果有记录
			String jsonStr = "[";
			for (Yxlw yxlw : yxlws) {
				Student student = studentService.findStudentByNumber(yxlw.getXuehao());
				String tempStr = "";
				tempStr += "{xuehao:'" + yxlw.getXuehao() + "',name:'" + student.getName() + "',zhuanye:'"
						+ getZhuanyeMap().get(student.getZy()) + "',lunwen:'"
						+ jwcService.findLwById(yxlw.getLw()).getFileName() + "',yuanfenshu:'" + yxlw.getYfs() + "'},";
				jsonStr += tempStr;
			}
			jsonStr = jsonStr.substring(0, jsonStr.length() - 1);
			simpleStringWriter(jsonStr, request, response);

		} else {
			simpleStringWriter("00", request, response);
		}
	}

	// 获取还 未推 荐的-小组-优秀论文
	public void findYuangroupYxlw(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String yuanxiId = request.getParameter("yuanxiId");
		List<Yxlwz> yxlwzs = new ArrayList<>();
		yxlwzs = jwcService.findYxlwzByYuanxiAndStatus(Integer.valueOf(yuanxiId), 0);// 0代表院内
		if (yxlwzs.size() > 0) {
			String jsonStr = "[";
			for (Yxlwz yxlwz : yxlwzs) {
				String tempStr = "";
				tempStr += "{id:'"+yxlwz.getId()+"',zuhao:'" + yxlwz.getZuhao() + "',yuanfenshu:'" + yxlwz.getYfs() + "'},";
				jsonStr += tempStr;
			}
			jsonStr = jsonStr.substring(0, jsonStr.length() - 1);
			simpleStringWriter(jsonStr, request, response);
		} else {
			simpleStringWriter("00", request, response);
		}
	}

	// 获取还 已经 荐的-小组-优秀论文
	public void findYuangroupRecommendedYxlw(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String yuanxiId = request.getParameter("yuanxiId");
		List<Yxlwz> yxlwzs = new ArrayList<>();
		yxlwzs = jwcService.findYxlwzByYuanxiAndStatus(Integer.valueOf(yuanxiId), 0);

		if (yxlwzs.size() > 0) {
			String jsonStr = "[";
			for (Yxlwz yxlwz : yxlwzs) {
				String tempStr = "";
				tempStr += "{id:'"+yxlwz.getId()+"',zuhao:'" + yxlwz.getZuhao() + "',yuanfenshu:'" + yxlwz.getYfs() + "'},";
				jsonStr += tempStr;
			}
			jsonStr = jsonStr.substring(0, jsonStr.length() - 1);
			simpleStringWriter(jsonStr, request, response);
		} else {
			simpleStringWriter("00", request, response);
		}
	}

	// 设置个人论文为校级优秀论文推荐
	public void setXiaoPersonalYxlw(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String xuehao = request.getParameter("xuehao");
		if (xuehao != null) {
			jwcService.changePersonalYxlwStatus(xuehao, 1);
			simpleStringWriter("11", request, response);
		}
	}

	// 设置小组论文为校级优秀论文推荐
	public void setXiaoGrouplYxlw(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		boolean workState=workControlService.isXiaoPingyouStart();//如果返回的是true,说明校级评优已经开始，院级不可操作
		if (!workState) {
			if (id != null) {
				jwcService.changeGroupYxlwStatus(Integer.valueOf(id), 1);
				simpleStringWriter("11", request, response);
			}
		}else{
			simpleStringWriter("22", request, response);
		}
	}

	// 取消设置个人论文为校级优秀论文推荐
	public void cancelXiaoPersoanlYxlw(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String xuehao = request.getParameter("xuehao");
		boolean workState=workControlService.isXiaoPingyouStart();//如果返回的是true,说明校级评优已经开始，院级不可操作
		if (!workState) {
			if (xuehao != null) {
				jwcService.changePersonalYxlwStatus(xuehao, 0);
				simpleStringWriter("11", request, response);
			}
		}else{
			simpleStringWriter("22", request, response);
		}
	}

	// 取消设置小组论文为校级优秀论文推荐
	public void cancelXiaoGroupYxlw(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		boolean workState=workControlService.isXiaoPingyouStart();//如果返回的是true,说明校级评优已经开始，院级不可操作
		if (!workState) {
			if (id != null) {
				jwcService.changeGroupYxlwStatus(Integer.valueOf(id), 0);
				simpleStringWriter("11", request, response);
			}
		}else{
			simpleStringWriter("22", request, response);
		}
		
	}

	/**
	 * 设置院系map，上把院系id转换成院系名字配对
	 * 
	 * @return Map<String, Integer>
	 */
	public Map<String, Integer> getYuanxiMap() {
		List<Yuanxi> yuanxis = jwcService.findAllYuanxi();
		Map<String, Integer> yuanxiMap = new HashMap<>();
		for (int j = 0; j < yuanxis.size(); j++) {
			yuanxiMap.put(yuanxis.get(j).getName(), yuanxis.get(j).getId());
		}
		return yuanxiMap;
	}

	/**
	 * 设置专业map，上把专业id转换成院系名字配对
	 * 
	 * @return Map<Integer,String>
	 */
	public Map<Integer, String> getZhuanyeMap() {
		List<Zhuanye> zhuanyes = jwcService.findAllZhuanye();
		Map<Integer, String> zMap = new HashMap<>();
		for (Zhuanye zhuanye : zhuanyes)
			zMap.put(zhuanye.getId(), zhuanye.getName());
		return zMap;
	}

	/**
	 * 输出--简单--字符串结果到页面，如“抱歉，未找到记录”，“操作成功等”
	 * 
	 * @param jsonString
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void simpleStringWriter(String jsonString, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		PrintWriter writer = response.getWriter();
		writer.print(jsonString);
		writer.close();
	}

	/**
	 * 输出--查询结果拼接的json格式的--字符串到页面，如[{name:"sam",age:'19'},{name:"huge",age:"34"}
	 * ,{name:"jingrui",age"22"}]
	 * 
	 * @param jsonStr
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void jsonArryWriter(String jsonStr, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		JSONArray jsonArray = null;
		PrintWriter writer = response.getWriter();
		jsonArray = JSONArray.fromObject(jsonStr);
		writer.print(jsonArray);
		writer.close();
	}

}
