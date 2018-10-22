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
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.Notice;
import domain.Notices;
import domain.Year;
import domain.Zqxj;
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
@WebServlet("/XiaoNotice.do")
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
		System.out.println("请求的方法"+method);
		switch (method) {
		case "addNotice":
             addNotice(request, response);
			break;
		case "findAllNotice":
            findAllNotice(request, response);
			break;
		case "findOneNoticeById":
             findOnteNoticeById(request, response);
			break;
		case "deleteNoticeById":
             deleteNoticeById(request, response);
			break;
		case "viewAttch":
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
		Notice notice=new Notice();
		notice.setTitle(title);
		notice.setText(content);
		notice.setType(3);
		notice.setYear( yearService.getNowYear().getYear());
		notice.setTime(new Date());
		jwcService.saveNotice(notice);
		writer.print("11");
		writer.close();
	}
	
	public void findAllNotice(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JSONArray jsonArray = null;
		PrintWriter writer = response.getWriter();
		List<Notice> notices =new ArrayList<>();
		notices=jwcService.findAllXiaoNotice();
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
				//找到该公告的附件
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
