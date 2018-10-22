package web.secretary;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Notice;
import domain.Notices;
import domain.Year;
import factory.JWCFactory;
import factory.ServiceFactory;
import net.sf.json.JSONArray;
import service.JWCService;
import service.StudentService;
import service.YearService;
import utils.GetMime;

/**
 * Servlet implementation class NoticePart
 */
@WebServlet("/YuanNotice.do")
public class NoticePart extends HttpServlet {
	private static final long serialVersionUID = 1L;
	JWCService jwcService=JWCFactory.getJwcService();
    YearService yearService=ServiceFactory.getYearServiceInstance();  
    StudentService studentService=ServiceFactory.getStudentServiceInstance();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticePart() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		switch (method) {
		case "addNotice":
			//添加公告
             addNotice(request, response);
			break;
		case "findAllNotice":
			//查找所有公告
            findAllNotice(request, response);
			break;
		case "findOneNoticeById":
			//根据id查公告
             findOnteNoticeById(request, response);
			break;
		case "deleteNoticeById":
			//删除公告
             deleteNoticeById(request, response);
			break;
		case "viewAttch":
			//查看公告详情。同时会展示附件
			viewAttch(request,response);
			break;
		default:
			break;
		}
	}
	public void viewAttch(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String attchId = request.getParameter("attchId");
		Notices notices=studentService.getOneNotices(Integer.valueOf(attchId));
		if (notices != null) {
			String ext = notices.getUrl();
			response.setContentType(GetMime.returnMimeFromExt(ext)+";charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(notices.getFileName(), "UTF-8"));
			request.getRequestDispatcher("WEB-INF/upload/" + notices.getYear() + "/teacher/notices/"+notices.getId()+"."+notices.getUrl()).forward(request, response);
		} else {
			System.out.println("没有找到文件");
		}
		
	}
	public void addNotice(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		String title=request.getParameter("title");
		String content=request.getParameter("content");
		String yuanxiId=request.getParameter("yuanxiId");
		Notice notice=new Notice();
		notice.setTitle(title);
		notice.setText(content);
		notice.setType(2);
		notice.setYear( yearService.getNowYear().getYear());
		notice.setTime(new Date());
		notice.setYx(Integer.valueOf(yuanxiId));
		jwcService.saveNotice(notice);
		writer.print("11");
		writer.close();
	}
	
	public void findAllNotice(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String yuanxiId=request.getParameter("yuanxiId");
		JSONArray jsonArray = null;
		PrintWriter writer = response.getWriter();
		List<Notice> notices =new ArrayList<>();
		notices=jwcService.findAllYuanxiNoticeByYuanxiId(Integer.valueOf(yuanxiId));
		if (notices.size()>0) {
			String jString = "[";
			for (Notice notice : notices) {
				String tempString = "";
				tempString += "{title:'" + notice.getTitle() + "',year:'" + notice.getYear()
						+ "',id:'" + notice.getId() + "'},";
				jString += tempString;
			}
			jString = jString.substring(0, jString.length() - 1);
			jString += "]";
			// 结果反馈
			jsonArray = JSONArray.fromObject(jString);
			writer.print(jsonArray);
			writer.close();
		}else{
			writer.print("00");
			writer.close();
		}
		
	}
	
	public void findOnteNoticeById(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JSONArray jsonArray = null;
		PrintWriter writer = response.getWriter();
		String noticeId=request.getParameter("noticeId");
		Notice notice=null;
		if (noticeId!=null) {
			notice=jwcService.findNoticeById(Integer.valueOf(noticeId));
			if (notice!=null) {
				List<Notices> notices=new ArrayList<>();
				notices=studentService.getNoticesAll(notice.getId());
				String jString = "[{title:'"+notice.getTitle()+"',content:'"+notice.getText()+"',time:'"+notice.getYear()+"'},";
				if(notices.size()>0)
				for(int i=0;i<notices.size();i++){
					jString+="{attchName:'"+notices.get(i).getFileName()+"',attchId:'"+notices.get(i).getId()+"'},";
				}
				jString=jString.substring(0,jString.length()-1);
				jString+="]";
				jsonArray = JSONArray.fromObject(jString);
				writer.print(jsonArray);
				writer.close();
			}else{
				writer.print("00");
				writer.close();
			}
		}else{
			writer.print("01");
			writer.close();
		}
	}
	
	public void deleteNoticeById(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		String noticeId=request.getParameter("noticeId");
		if (noticeId!=null) {
			Notice notice=jwcService.findNoticeById(Integer.valueOf(noticeId));
			if (notice!=null) {
				jwcService.deleteNotice(notice);
				List<Notices> noticess=new ArrayList<>();
				noticess=studentService.getNoticesAll(notice.getId());
				if(noticess.size()>0)
				for(Notices notices:noticess){
					Year year = yearService.getNowYear();
					String path = request.getSession().getServletContext()
							.getRealPath("WEB-INF/upload/" + year.getYear() +"/teacher/notices/"+notices.getId()+"."+notices.getUrl());
					// 拼接文件路径，用于读取上传的文件
					File file=new File(path);
					file.delete();
					file.deleteOnExit();
					studentService.deleteNotices(notices);
				}
				
				writer.print("11");
				writer.close();
			}else{
				writer.print("00");
				writer.close();
			}
		}else{
			writer.print("01");
			writer.close();
		}
	}
}
