package web.jwc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 用于页面上预览文档，所有涉及到页面上预览上传过的文档的操作都可以调用这个工具
 * Servlet implementation class DocumentView
 */
@WebServlet("/DocumentView.do")
public class DocumentView extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DocumentView() {
        super();
        // TODO Auto-generated constructor stub
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

		HttpSession session=request.getSession();
		session.removeAttribute("documentName");
		//文档年份
		String year=request.getParameter("year");
		//文档级别，教师、学生
		String documentLevel=request.getParameter("documentLevel");
		//文档类型，开题报告，论文
		String documentType=request.getParameter("documentType");
		//文档名字
		String documentName=request.getParameter("documentName");
		if (year!=null&&documentLevel!=null&&documentName!=null&&documentType!=null) {
			String path = request.getSession().getServletContext()
					.getRealPath("WEB-INF/upload/" + year+ "/"+documentLevel + "/"+documentType+"/"+documentName);
			InputStream in = new FileInputStream(path);
			String outPath = "/OfficeFile/"+documentName;
			System.out.println(outPath);
			File file=new File(request.getSession().getServletContext().getRealPath(outPath));
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
			session.setAttribute("documentName", documentName);
			//response.sendRedirect("/jwc/reportShow.jsp");
			request.getRequestDispatcher("/jwc/reportShow.jsp").forward(request, response);
		}else{
			response.getWriter().print("00");
		}
		
	}

}
