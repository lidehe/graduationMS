package web.student;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Student;

/**
 * Servlet implementation class ReDirect
 */
@WebServlet("/student.do")
public class ReDirect extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReDirect() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Student student = (Student)request.getSession().getAttribute("student");
		int stage = student.getStage();
		switch (stage) {
		case 0://学生的阶段值为0，就跳转到选择毕业类型的页面
		case 1:
		case 2:
			response.sendRedirect("bylx.do?ser=ind");
			break;
		case 3:
		case 4:
		case 5:
			response.sendRedirect("kt.do");
			break;
		case 6:
		case 7:
			response.sendRedirect("ktbg.do");
			break;
		case 8:
		case 9:
		case 10:
			response.sendRedirect("jdxcg.do");
			break;
		case 11:
		case 12:
		case 13:
			response.sendRedirect("lwcg.do");
			break;
		case 14:
			response.sendRedirect("zzlw.do");
			break;
		default:
			response.sendRedirect("bylx.do?ser=ind");
			break;
		}
	}

}
