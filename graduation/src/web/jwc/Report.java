package web.jwc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
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

import domain.Year;
import domain.Yuanxi;
import domain.Zqxj;
import factory.JWCFactory;
import factory.ServiceFactory;
import net.sf.json.JSONArray;
import service.JWCService;
import service.YearService;

/**
 * 中期小结和最后总结报告管理 Servlet implementation class Report
 */
@WebServlet("/JwcReport.do")
public class Report extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JWCService jwcService = JWCFactory.getJwcService();
	private YearService yearService = ServiceFactory.getYearServiceInstance();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Report() {
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
		if (method.equals("getMid")) {
			getMid(request, response);
		} else if (method.equals("getFinal")) {
			getFinal(request, response);
		} else if (method.equals("showZqxj")) {
			showZqxj(request, response);
		} else if(method.equals("loadZqxj")){
			loadZqxj(request, response);
		}else if (method.equals("showZhzj")) {
			showZhzj(request, response);
		}else if(method.equals("loadZhzj")){
			loadZhzj(request, response);
		}else if (method.equals("deleteReportById")) {
			deleteReportByid(request, response);
		}

	}

	/**
	 * 获取中期小结
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void getMid(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 通过设置这个，可以使用户在页面上预览，而不是直接就看内容
		// response.setHeader(arg0, arg1);
		JSONArray jsonArray = null;
		PrintWriter writer = response.getWriter();
		List<Zqxj> zqxjs = new ArrayList<>();
		zqxjs = jwcService.findAllZqxj();
		if (zqxjs.size() > 0) {
			String jString = "[";
			for (Zqxj zqxj : zqxjs) {
				String tempString = "";
				tempString += "{yuanxi:'" + getYuanxiMap().get(zqxj.getYx()) + "',name:'" + zqxj.getFileName()
						+ "',time:'" + zqxj.getTime() + "',id:'" + zqxj.getId() + "'},";
				jString += tempString;
			}
			jString = jString.substring(0, jString.length() - 1);
			jString += "]";
			System.out.println(jString);
			// 结果反馈
			jsonArray = JSONArray.fromObject(jString);
			writer.print(jsonArray);
			writer.close();
		} else {
			writer.print("00");
		}
	}

	/**
	 * 获取最终总结
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void getFinal(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONArray jsonArray = null;
		PrintWriter writer = response.getWriter();
		List<Zqxj> zqxjs = new ArrayList<>();
		zqxjs = jwcService.findAllZhzj();
		if (zqxjs.size() > 0) {
			String jString = "[";
			for (Zqxj zqxj : zqxjs) {
				String tempString = "";
				tempString += "{yuanxi:'" + getYuanxiMap().get(zqxj.getYx()) + "',name:'" + zqxj.getFileName()
						+ "',time:'" + zqxj.getTime() + "',id:'" + zqxj.getId() + "'},";
				jString += tempString;
			}
			jString = jString.substring(0, jString.length() - 1);
			jString += "]";
			System.out.println(jString);
			// 结果反馈
			jsonArray = JSONArray.fromObject(jString);
			writer.print(jsonArray);
			writer.close();
		} else {
			writer.print("00");
		}
	}

	/**
	 * 预览中期小结
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException 
	 */
	public void showZqxj(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String zqxjId = request.getParameter("zqxjId");
		//response.setContentType("application/msword");
		HttpSession session=request.getSession();
		session.removeAttribute("reportName");
		Zqxj zqxj = jwcService.findZqxjById(Integer.valueOf(zqxjId));
		if (zqxj != null) {
			// 年份
			Year year = yearService.getNowYear();
			String path = request.getSession().getServletContext()
					.getRealPath("WEB-INF/upload/" + year.getYear() + "/teacher/zqxj/");
			// 拼接文件路径，用于读取上传的文件
			path = path + zqxj.getFileName();// 用于拼接成完整的文件路径名字
			InputStream in = new FileInputStream(path);
			//OutputStream out = response.getOutputStream();
			String pathTail = "/OfficeFile/"+zqxj.getFileName();
			System.out.println(pathTail);
			File file=new File(request.getSession().getServletContext().getRealPath(pathTail));
			if (!file.exists()) {
				file.createNewFile();
			}
			
			OutputStream  out=new FileOutputStream(file);
			// 写文件
			int b;
			while ((b = in.read()) != -1) {
				out.write(b);
			}
			in.close();
			out.flush();
			out.close();
			session.setAttribute("reportName", zqxj.getFileName());
			request.getRequestDispatcher("/jwc/reportShow.jsp").forward(request, response);
		} else {
			System.out.println("没有找到文件");
		}
	}
	/**
	 * 下载中期小结
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void loadZqxj(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String zqxjId = request.getParameter("zqxjId");
		Zqxj zqxj = jwcService.findZqxjById(Integer.valueOf(zqxjId));
		if (zqxj != null) {
			response.setContentType("application/pdf;charset=utf-8");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + URLEncoder.encode(zqxj.getFileName(), "UTF-8"));
			// 年份
			Year year = yearService.getNowYear();
			// 拼接文件路径，用于读取上传的文件
			request.getRequestDispatcher("WEB-INF/upload/" + year.getYear() +  "/teacher/zqxj/"
					+ zqxj.getFileName()).forward(request, response);
		}else{
			System.out.println("没有找到文件");
		}
	}
	/**
	 * 预览最后总结
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException 
	 */
	public void showZhzj(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String zhzjId = request.getParameter("zhzjId");
		HttpSession session=request.getSession();
		session.removeAttribute("reportName");
		Zqxj zqxj = null;
		zqxj = jwcService.findZqxjById(Integer.valueOf(zhzjId));
		if (zqxj != null) {
			// 年份
			Year year = yearService.getNowYear();
			String path = request.getSession().getServletContext()
					.getRealPath("WEB-INF/upload/" + year.getYear() + "/teacher/zhzj/");
			// 拼接文件路径，用于读取上传的文件
			path = path + zqxj.getFileName();// 用于拼接成完整的文件路径名字
			InputStream in = new FileInputStream(path);
			//OutputStream out = response.getOutputStream();
			String pathTail = "/OfficeFile/"+zqxj.getFileName();
			System.out.println(pathTail);
			File file=new File(request.getSession().getServletContext().getRealPath(pathTail));
			if (!file.exists()) {
				file.createNewFile();
			}
			
			OutputStream  out=new FileOutputStream(file);
			// 写文件
			int b;
			while ((b = in.read()) != -1) {
				out.write(b);
			}
			in.close();
			out.flush();
			out.close();
			session.setAttribute("reportName", zqxj.getFileName());
			request.getRequestDispatcher("/jwc/reportShow.jsp").forward(request, response);
		} else {
			System.out.println("没有找到文件");
		}
	}
	/**
	 * 下载最终总结
	 */
	public void loadZhzj(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String zhzjId = request.getParameter("zhzjId");
		//response.setContentType("application/msword");
		Zqxj zqxj = null;
		zqxj = jwcService.findZqxjById(Integer.valueOf(zhzjId));
		if (zqxj != null) {
			response.setContentType("application/pdf;charset=utf-8");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + URLEncoder.encode(zqxj.getFileName(), "UTF-8"));
			// 年份
			Year year = yearService.getNowYear();
			// 拼接文件路径，用于读取上传的文件
			request.getRequestDispatcher("WEB-INF/upload/" + year.getYear() +  "/teacher/zhzj/"
					+ zqxj.getFileName()).forward(request, response);
		}else{
			System.out.println("没有找到文件");
		}
	}
	/**
	 * 根据id删除小结、报告
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void deleteReportByid(HttpServletRequest request,HttpServletResponse response) throws IOException{
		PrintWriter writer=response.getWriter();
		String reportId=request.getParameter("reportId");
		Zqxj zqxj=jwcService.findZqxjById(Integer.valueOf(reportId));
		if (zqxj!=null) {
			jwcService.deleteZqxj(zqxj);
			Year year = yearService.getNowYear();
			String path = request.getSession().getServletContext()
					.getRealPath("WEB-INF/upload/" + year.getYear() + "/teacher/zhzj/"+zqxj.getFileName());
			File file=new File(path);
			file.delete();
			path = request.getSession().getServletContext()
					.getRealPath("WEB-INF/upload/" + year.getYear() + "/teacher/zqxj/"+zqxj.getFileName());
			file=new File(path);
			file.delete();
			path = request.getSession().getServletContext()
					.getRealPath("OfficeFile/"+zqxj.getFileName());
			file=new File(path);
			file.delete();
			writer.print("11");
		}else {
			writer.print("00");
		}
		writer.close();
	}

	/**
	 * 设置院系map，上把院系id转换成院系名字配对
	 * 
	 * @return Map<String, Integer>
	 */
	public Map<Integer,String> getYuanxiMap() {
		List<Yuanxi> yuanxis = jwcService.findAllYuanxi();
		Map<Integer,String> yuanxiMap = new HashMap<>();
		for (int j = 0; j < yuanxis.size(); j++) {
			yuanxiMap.put(yuanxis.get(j).getId(),yuanxis.get(j).getName());
		}
		return yuanxiMap;
	}

}
