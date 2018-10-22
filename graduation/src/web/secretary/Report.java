package web.secretary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import domain.Year;
import domain.Zqxj;
import factory.JWCFactory;
import factory.ServiceFactory;
import service.JWCService;
import service.YearService;
import utils.DateTimeUtil;

/**
 * Servlet implementation class Report
 */
@WebServlet("/Report.do")
public class Report extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JWCService jwcService = JWCFactory.getJwcService();
	private YearService yearService = ServiceFactory.getYearServiceInstance();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Report() {
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
		String method = request.getParameter("method");
		if (method == null) {
			uploadFile(request, response);
		} else if (method.equals("getMid")) {
			getMid(request, response);
		} else if (method.equals("getFinal")) {
			getFinal(request, response);
		} else if (method.equals("showZqxj")) {
			showZqxj(request, response);
		}else if (method.equals("loadZqxj")){
			loadZqxj(request, response);
		}else if (method.equals("showZhzj")) {
			showZhzj(request, response);
		}else if(method.equals("loadZhzj")){
			loadZhzj(request, response);
		}else if (method.equals("deleteReportById")) {
			deleteReportById(request, response);
		}

	}

	/**
	 * 上传总结
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public void uploadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String inforSource = "";// 要导入的信息的来源，有院系、专业、老师、学生
		int yuanxiId = 0;
		String gonghao = "";
		String docName = "";// 上传的文件名字
		String url = "";// 文件类型，是中期小结（zqxj)还是最后总结(zhzj)
		// 文件上传的路径
		String path = "";
		// 上传结果反馈
		PrintWriter writer = response.getWriter();
		// 年份
		Year year = yearService.getNowYear();
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			List items = upload.parseRequest(request);
			Iterator itr = items.iterator();
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				if (item.isFormField()) {
					// "，表单参数值:" + item.getString("UTF-8"));
					// 接受文件来源信息
					switch (item.getFieldName()) {
					case "inforSource":
						inforSource = item.getString("UTF-8");
						break;
					case "yuanxiId":
						yuanxiId = Integer.valueOf(item.getString("UTF-8"));
						break;
					case "gonghao":
						gonghao = item.getString("UTF-8");
						break;
					}
				} else {
					if (item.getName() != null && !item.getName().equals("")) {
						switch (inforSource) {
						case "mid":
							path = request.getSession().getServletContext()
									.getRealPath("WEB-INF/upload/" + year.getYear() + "/teacher/zqxj/");
							url = "zqxj";
							break;
						case "final":
							path = request.getSession().getServletContext()
									.getRealPath("WEB-INF/upload/" + year.getYear() + "/teacher/zhzj/");
							url = "zhzj";
							break;
						default:
							break;
						}
						File saveFileDir = new File(path);
						if (!saveFileDir.exists()) {
							// 创建目录
							saveFileDir.mkdirs();
						}
						// item.getName()返回上传文件在客户端的完整路径名称
						docName = gonghao+item.getName();
						File tempFile = new File(item.getName());
						File file = new File(path, docName);
						item.write(file);
						Timestamp timestamp=new Timestamp(System.currentTimeMillis());
						timestamp=Timestamp.valueOf(DateTimeUtil.getCurrentDateTime());
						Zqxj zqxj = new Zqxj();
						zqxj.setFileName(docName);
						zqxj.setGonghao(gonghao);
						zqxj.setStatus(1);
						zqxj.setUrl(url);
						zqxj.setYx(yuanxiId);
						zqxj.setYear(year.getYear());
						zqxj.setTime(timestamp);
						jwcService.saveZqxj(zqxj);
						writer.print(url);
					} else {
						writer.print("文件上传失败!未选择文件!");
					}
				}
			}
		} catch (Exception e) {
			request.setAttribute("upload.message", "上传文件失败！");
			e.printStackTrace();
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

		String yuanxiId = request.getParameter("yuanxiId");
		PrintWriter writer = response.getWriter();
		Zqxj zqxj = null;
		zqxj = jwcService.findZqxjByYuanxiIdAndType(Integer.valueOf(yuanxiId), "zqxj");
		if (zqxj != null) {
			// 结果反馈
			writer.print(zqxj.getId()+ ":" +zqxj.getFileName() + ":" + yuanxiId);
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
		String yuanxiId = request.getParameter("yuanxiId");
		PrintWriter writer = response.getWriter();
		Zqxj zqxj = null;
		zqxj = jwcService.findZqxjByYuanxiIdAndType(Integer.valueOf(yuanxiId), "zhzj");
		if (zqxj != null) {
			// 结果反馈
			writer.print(zqxj.getId()+ ":" +zqxj.getFileName() + ":" + yuanxiId);
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
		String yuanxiId = request.getParameter("yuanxiId");
		HttpSession session=request.getSession();
		session.removeAttribute("yuanReportName");
		Zqxj zqxj = null;
		zqxj = jwcService.findZqxjByYuanxiIdAndType(Integer.valueOf(yuanxiId), "zqxj");
		if (zqxj != null) {
			// 年份
			Year year = yearService.getNowYear();
			String path = request.getSession().getServletContext()
					.getRealPath("WEB-INF/upload/" + year.getYear() + "/teacher/zqxj/");
			// 拼接文件路径，用于读取上传的文件
			// File file = null;
			 path = path + zqxj.getFileName();// 用于拼接成完整的文件路径名字
			// 开始跳转
			InputStream in = new FileInputStream(path);  
	        //OutputStream out = response.getOutputStream();  
			String pathTail = "/OfficeFile/"+zqxj.getFileName();
			File file=new File(request.getSession().getServletContext().getRealPath(pathTail));
			if (!file.exists()) {
				file.createNewFile();
			}
			OutputStream  out=new FileOutputStream(file);
	        //写文件  
	        int b;  
	        while((b=in.read())!= -1)  
	        {  
	            out.write(b);  
	        }  
	        in.close();  
	        out.flush();
	        out.close();  
	        session.setAttribute("yuanReportName", zqxj.getFileName());
			request.getRequestDispatcher("/secretary/reportPreview.jsp").forward(request, response);
		}else{
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
		String yuanxiId = request.getParameter("yuanxiId");
		Zqxj zqxj = null;
		zqxj = jwcService.findZqxjByYuanxiIdAndType(Integer.valueOf(yuanxiId), "zqxj");
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
		String yuanxiId = request.getParameter("yuanxiId");
		HttpSession session=request.getSession();
		session.removeAttribute("yuanReportName");
		Zqxj zqxj = null;
		zqxj = jwcService.findZqxjByYuanxiIdAndType(Integer.valueOf(yuanxiId), "zhzj");
		if (zqxj != null) {
			// 年份
			Year year = yearService.getNowYear();
			String path = request.getSession().getServletContext()
					.getRealPath("WEB-INF/upload/" + year.getYear() + "/teacher/zhzj/");
			// 拼接文件路径，用于读取上传的文件
			// File file = null;
			 path = path + zqxj.getFileName();// 用于拼接成完整的文件路径名字
			// 开始跳转
			InputStream in = new FileInputStream(path);  
	      //  OutputStream out = response.getOutputStream();  
			String pathTail = "/OfficeFile/"+zqxj.getFileName();
			File file=new File(request.getSession().getServletContext().getRealPath(pathTail));
			if (!file.exists()) {
				file.createNewFile();
			}
			
			OutputStream  out=new FileOutputStream(file);
	        //写文件  
	        int b;  
	        while((b=in.read())!= -1)  
	        {  
	            out.write(b);  
	        }  
	        in.close();  
	        out.flush();
	        out.close();  
	        session.setAttribute("yuanReportName", zqxj.getFileName());
			request.getRequestDispatcher("/secretary/reportPreview.jsp").forward(request, response);
		}else{
			System.out.println("没有找到文件");
		}
	}
	
	/**
	 * 下载最终总结
	 */
	public void loadZhzj(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String yuanxiId = request.getParameter("yuanxiId");
		Zqxj zqxj = null;
		zqxj = jwcService.findZqxjByYuanxiIdAndType(Integer.valueOf(yuanxiId), "zhzj");
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
	public void deleteReportById(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter writer=response.getWriter();
		String reportId=request.getParameter("reportId");
		Zqxj zqxj=jwcService.findZqxjById(Integer.valueOf(reportId));
		if (zqxj!=null) {
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
			
			jwcService.deleteZqxj(zqxj);
			writer.print("11");
		}else {
			writer.print("00");
		}
		writer.close();
	}

}
