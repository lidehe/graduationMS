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


import domain.Dbpsbx;
import domain.Pypsbx;
import domain.Sxcjx;
import domain.Zdlspsbx;
import factory.JWCFactory;
import net.sf.json.JSONArray;
import service.JWCService;

/**
 * Servlet implementation class ScoreTable
 */
@WebServlet("/ScoreTable.do")
public class ScoreTable extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JWCService jwcService = JWCFactory.getJwcService();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ScoreTable() {
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
		case "addPart":
			addPart(request, response);
			break;
		case "loadPart":
			loadPart(request, response);
			break;
		case "deletePart":
			deletePart(request, response);
			break;
		}
	}

	public void addPart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String witchTable = request.getParameter("witchTable");
		String name = request.getParameter("name");
		String type = request.getParameter("type");
		String mfz = request.getParameter("mfz");
		PrintWriter writer = response.getWriter();
		boolean result = false;
		if (type.equals("num")) {// 如果新增项目是数值型的
			switch (witchTable) {
			case "pypsb":
				Pypsbx pypsbx = new Pypsbx(name, Integer.valueOf(mfz), 0);
				result = jwcService.addPypsbx(pypsbx);
				break;
			case "dbpsb":
				Dbpsbx dbpsbx = new Dbpsbx(name, Integer.valueOf(mfz), 0);
				result = jwcService.addDbpsbx(dbpsbx);
				break;
			case "zdlspsb":
				Zdlspsbx zdlspsbx = new Zdlspsbx(name, Integer.valueOf(mfz), 0);
				result = jwcService.addZdlspsbx(zdlspsbx);
				break;
			case "sxcj":
				Sxcjx sxcjx = new Sxcjx(name, Integer.valueOf(mfz), 0);
				result = jwcService.addSxcjx(sxcjx);
				break;
			}
		} else if (type.equals("text")) {// 新增项目未文本型
			switch (witchTable) {
			case "pypsb":
				Pypsbx pypsbx = new Pypsbx(name, 0, 1);
				result = jwcService.addPypsbx(pypsbx);
				break;
			case "dbpsb":
				Dbpsbx dbpsbx = new Dbpsbx(name, 0, 1);
				result = jwcService.addDbpsbx(dbpsbx);
				break;
			case "zdlspsb":
				Zdlspsbx zdlspsbx = new Zdlspsbx(name, 0, 1);
				result = jwcService.addZdlspsbx(zdlspsbx);
				break;
			case "sxcj":
				Sxcjx sxcjx = new Sxcjx(name, 0, 1);
				result = jwcService.addSxcjx(sxcjx);
				break;
			}
		}
		if (result) {
			writer.print("11");
		} else {
			writer.print("00");
		}
		writer.close();

	}

	public void loadPart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String witchTable = request.getParameter("witchTable");
		JSONArray jsonArray = null;
		PrintWriter writer = response.getWriter();
		String jString = "";
		switch (witchTable) {
		case "pypsb":
			List<Pypsbx> pypsbxs = new ArrayList<>();
			pypsbxs = jwcService.findAllPypsbx();
			if (pypsbxs.size() > 0) {
				jString = "[";
				for (Pypsbx pypsbx : pypsbxs) {
					String tempStr = "";
					if (pypsbx.getStatus() == 1) {
						tempStr += "{ name:'" + pypsbx.getText() + "',type:'文本',mfz:'" + ""
								+ "'},";
					} else {
						tempStr += "{ name:'" + pypsbx.getText() + "',type:'分值',mfz:'"
								+ pypsbx.getMf() + "'},";
					}
					jString += tempStr;
				}
				jString = jString.substring(0, jString.length() - 1);
				jString += "]";
				jsonArray = JSONArray.fromObject(jString);
			}
			break;
		case "dbpsb":
			List<Dbpsbx> dbpsbxs = new ArrayList<>();
			dbpsbxs = jwcService.findAllDbpsbx();
			if (dbpsbxs.size() > 0) {
				jString = "[";
				for (Dbpsbx dbpsbx : dbpsbxs) {
					String tempStr = "";
					if (dbpsbx.getStatus() == 1) {
						tempStr += "{ name:'" + dbpsbx.getText() + "',type:'文本',mfz:'" + ""
								+ "'},";
					} else {
						tempStr += "{ name:'" + dbpsbx.getText() + "',type:'分值',mfz:'"
								+ dbpsbx.getMf() + "'},";
					}
					jString += tempStr;
				}
				jString = jString.substring(0, jString.length() - 1);
				jString += "]";
				jsonArray = JSONArray.fromObject(jString);
			}
			break;
		case "zdlspsb":
			List<Zdlspsbx> zdlspsbxs = new ArrayList<>();
			zdlspsbxs = jwcService.findAllZdlspsbx();
			if (zdlspsbxs.size() > 0) {
				jString = "[";
				for (Zdlspsbx zdlspsbx : zdlspsbxs) {
					String tempStr = "";
					if (zdlspsbx.getStatus() == 1) {
						tempStr += "{ name:'" + zdlspsbx.getText() + "',type:'文本',mfz:'" + ""
								+ "'},";
					} else {
						tempStr += "{ name:'" + zdlspsbx.getText() + "',type:'分值',mfz:'"
								+ zdlspsbx.getMf() + "'},";
					}
					jString += tempStr;
				}
				jString = jString.substring(0, jString.length() - 1);
				jString += "]";
				jsonArray = JSONArray.fromObject(jString);
			}
			break;
		case "sxcj":
			List<Sxcjx> sxcjxs = new ArrayList<>();
			sxcjxs = jwcService.findAllSxcjx();
			if (sxcjxs.size() > 0) {
				jString = "[";
				for (Sxcjx sxcjx : sxcjxs) {
					String tempStr = "";
					if (sxcjx.getStatus() == 1) {
						tempStr += "{ name:'" + sxcjx.getText() + "',type:'文本',mfz:'" + ""
								+ "'},";
					} else {
						tempStr += "{ name:'" + sxcjx.getText() + "',type:'分值',mfz:'"
								+ sxcjx.getMf() + "'},";
					}
					jString += tempStr;
				}
				jString = jString.substring(0, jString.length() - 1);
				jString += "]";
				jsonArray = JSONArray.fromObject(jString);
			}
			break;
		}

		if (jString.equals("")) {
			writer.print("00");
		} else {
			writer.print(jsonArray);
		}
		writer.close();

	}

	public void deletePart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String witchTable = request.getParameter("witchTable");
		String name = request.getParameter("name");
		switch (witchTable) {
		case "pypsb":
			Pypsbx pypsbx = jwcService.findPypsbxByName(name);
			jwcService.deletePypsbx(pypsbx);
			break;
		case "dbpsb":
			Dbpsbx dbpsbx = jwcService.findDbpsbxByName(name);
			jwcService.deleteDbpsbx(dbpsbx);
			break;
		case "zdlspsb":
			Zdlspsbx zdlspsbx = jwcService.findZdlspsbxByName(name);
			jwcService.deleteZdlspsbx(zdlspsbx);
			break;
		case "sxcj":
			Sxcjx sxcjx = jwcService.findSxcjxByName(name);
			jwcService.deleteSxcjx(sxcjx);
			break;
		}
	}

}
