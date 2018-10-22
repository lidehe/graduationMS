package web.student;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Student;
import utils.SetStudentStage;

/**
 * Servlet implementation class Jdxcg
 */
@WebServlet("/jdxcg.do")
public class JdxcgW extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JdxcgW() {
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
		String method = request.getParameter("meth");
		Student student = (Student)request.getSession().getAttribute("student");
		int stage = SetStudentStage.setSan(student);
		if(stage != 8){
			response.sendRedirect("ktbg.do");
			return;
		}
		if(student.getStage() < 8)
			response.sendRedirect("ktbg.do");
		if (method == null) {
			method = "ind";
		}
		switch (method) {
		case "ind":
			request.getRequestDispatcher("student/working/jdxcg.jsp").forward(request, response);
			break;
		default:
			break;
		}
	}
	
	

}
