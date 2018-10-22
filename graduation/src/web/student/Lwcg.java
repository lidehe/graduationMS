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
 * Servlet implementation class Lwcg
 */
@WebServlet("/lwcg.do")
public class Lwcg extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Lwcg() {
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
		int stage = SetStudentStage.setSi(student);
		if(stage != 11){
			response.sendRedirect("jdxcg.do");
			return;
		}
		if(student.getStage() < 11)
			response.sendRedirect("jdxcg.do");
		if (method == null) {
			method = "ind";
		}
		switch (method) {
		case "ind":
			request.getRequestDispatcher("student/working/lwcg.jsp").forward(request, response);
			break;
		default:
			break;
		}
	}
	
}
