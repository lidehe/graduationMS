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

import domain.Yuanxi;
import domain.Yxlw;
import domain.Yxlwx;
import domain.Yxlwz;
import factory.JWCFactory;
import net.sf.json.JSONArray;
import service.JWCService;

/**
 * 
 * 优秀论文管理
 *  Servlet implementation class Appraising
 */
@WebServlet("/Appraising.do")
public class Appraising extends HttpServlet {
	private static final long serialVersionUID = 1L;
	JWCService jwcService = JWCFactory.getJwcService();

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

		switch (method) {
		case "getYxPaperCount":// 各院系论文数量设定页面--获取院系优秀论文数量
			getYxPaperCount(request, response);
			break;
		case "savePaperNewCount":// 各院系论文数量设定页面--保存更改后的院系优秀论文数量
			savePaperNewCount(request, response);
			break;
		case "findXiaoPersonalYxlw":// 校级优秀论文页面---获取校级-个人-优秀论文
			findXiaoPersonalYxlw(request, response);
			break;
		case "findXiaogroupYxlw":// 校级优秀论文页面---获取校级-小组-优秀论文
			findXiaogroupYxlw(request, response);
			break;
		case "findShengYxlw":// 省级优秀论文页面---获取省级-个人-优秀论文
			findShengYxlw(request, response);
			break;
		case "setShengYxlw":// 校级优秀论文页面---设置论文为省级优秀论文推荐
			setShengYxlw(request, response);
			break;
		case "cancelShengYxlw":// 省级优秀论文页面---使论文从省级优秀论文推荐中删除
			cancelShengYxlw(request, response);
			break;

		}

	}

	// 保存更新后的各院系论文数量限制
	public void savePaperNewCount(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获取院系Map
		Map<String, Integer> yuanxiMap = getYuanxiMap();
		// 传过来的信息是：计算机学院，19，1：文理学院，8，2： 这样的，所以要先按：号分割成多个院系，然后按照，号获取每个院系里每个信息
		String yuanxiAndCount = request.getParameter("yuanxiAndCount");

		if (yuanxiAndCount != null) {
			String[] oneYuanxiCount = yuanxiAndCount.split(":");
			for (int i = 0; i < oneYuanxiCount.length; i++) {
				String[] detail = oneYuanxiCount[i].split(",");
				int yxId = yuanxiMap.get(detail[0]);
				// 个人论文数量限制
				int personalSL = Integer.valueOf(detail[1]);
				// 个人论文数量限制
				int groupSL = Integer.valueOf(detail[2]);
				Yxlwx personalYxlwx = new Yxlwx(yxId, personalSL, 0);
				jwcService.update(personalYxlwx);
				Yxlwx groupYxlwx = new Yxlwx(yxId, groupSL, 1);
				jwcService.update(groupYxlwx);
			}
			simpleStringWriter("更新完成", request, response);
		}

	}

	// 获取院系优秀论文限制数量信息
	public void getYxPaperCount(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<Yuanxi> yuanxis = new ArrayList<>();
		yuanxis = jwcService.findAllYuanxi();
		String jsonStr = "[";
		if (yuanxis.size() > 0) {
			for (Yuanxi yuanxi : yuanxis) {
				String tempStr = "{ yuanxi:'" + yuanxi.getName() + "',";
				// 某院系个人论文数量限制
				Yxlwx yxlwxPersional = null;
				yxlwxPersional = jwcService.findByYxAndType(yuanxi.getId(), 0);
				if (yxlwxPersional == null) {
					tempStr += "personalCount:'" + 0 + "',";
				} else {
					tempStr += "personalCount:'" + yxlwxPersional.getSl() + "',";
				}

				// 某院系小组论文数量限制
				Yxlwx yxlwxGroup = null;
				yxlwxGroup = jwcService.findByYxAndType(yuanxi.getId(), 1);
				if (yxlwxGroup == null) {
					tempStr += "groupCount:'" + 0 + "'},";
				} else {
					tempStr += "groupCount:'" + yxlwxGroup.getSl() + "'},";
				}
				jsonStr += tempStr;
			}
			jsonStr = jsonStr.substring(0, jsonStr.length() - 1);
			jsonStr += "]";
			jsonArryWriter(jsonStr, request, response);
		}

	}

	// 校级优秀论文页面---获取校级-个人-优秀论文
	public void findXiaoPersonalYxlw(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<Yxlw> yxlws = new ArrayList<>();
		yxlws = jwcService.findYxlwByStatus(1);//状态为1代表是校级

		if (yxlws.size() > 0) {// 如果有记录
			String jsonStr = "[";
			for (Yxlw yxlw : yxlws) {
				String tempStr = "";
				tempStr += "{xuehao:'" + yxlw.getXuehao() + "',yuanxi:'" + getYuanxiMap().get(yxlw.getYx())
						+ "',lunwen:'" + jwcService.findLwById(yxlw.getLw()).getFileName() + "',xiaofenshu:'"
						+ yxlw.getXfs() + "',yuanfenshu:'" + yxlw.getYfs() + "'},";
				jsonStr += tempStr;
			}
			jsonStr = jsonStr.substring(0, jsonStr.length() - 1);
			simpleStringWriter(jsonStr, request, response);

		} else {
			simpleStringWriter("00", request, response);
		}
	}

	// 校级优秀论文页面---获取校级-小组-优秀论文
	public void findXiaogroupYxlw(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<Yxlwz> yxlwzs = new ArrayList<>();
		yxlwzs = jwcService.findYxlwzByStatus(1);
		
		if (yxlwzs.size() > 0) {
			String jsonStr = "[";
			for (Yxlwz yxlwz : yxlwzs) {
				String tempStr = "";
				tempStr += "{zuhao:'" + yxlwz.getZuhao() + "',yuanxi:'" + getYuanxiMap().get(yxlwz.getYx())+ "',xiaofenshu:'"
						+ yxlwz.getXfs() + "',yuanfenshu:'" + yxlwz.getYfs() + "'},";
				jsonStr += tempStr;
			}
			jsonStr = jsonStr.substring(0, jsonStr.length() - 1);
			simpleStringWriter(jsonStr, request, response);
		} else {
			simpleStringWriter("00", request, response);
		}
	}

	// 省级优秀论文页面---获取省级-个人-优秀论文
	public void findShengYxlw(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<Yxlw> yxlws = new ArrayList<>();
		yxlws = jwcService.findYxlwByStatus(3);
		if (yxlws.size() > 0) {
			String jsonStr = "[";
			for (Yxlw yxlw : yxlws) {
				String tempStr = "";
				tempStr += "{xuehao:'" + yxlw.getXuehao() + "',yuanxi:'" + getYuanxiMap().get(yxlw.getYx())
						+ "',lunwen:'" + jwcService.findLwById(yxlw.getLw()).getFileName() +"'},";
				jsonStr += tempStr;
			}
			jsonStr = jsonStr.substring(0, jsonStr.length() - 1);
			simpleStringWriter(jsonStr, request, response);

		} else {
			simpleStringWriter("00", request, response);
		}
	}

	// 校级优秀论文页面---设置论文为省级优秀论文推荐
	public void setShengYxlw(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String xuehao = request.getParameter("xuehao");
		if (xuehao != null) {
			jwcService.changePersonalYxlwStatus(xuehao, 3);
			simpleStringWriter("11", request, response);
		}
	}

	// 省级优秀论文页面---使论文从省级优秀论文推荐中删除
	public void cancelShengYxlw(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String xuehao = request.getParameter("xuehao");
		if (xuehao != null) {
			jwcService.changePersonalYxlwStatus(xuehao, 1);
			simpleStringWriter("11", request, response);
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
