package web.student;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Student;

/**
 * Servlet implementation class Zlw
 */
@WebServlet("/zzlw.do")
public class Zlw extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Zlw() {
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
		if(student.getStage() < 14)
			response.sendRedirect("lwcg.do");
		if(method == null)
			method = "ind";
		switch (method) {
		case "ind":
			request.getRequestDispatcher("student/working/lw.jsp").forward(request, response);
			break;
		default:
			break;
		}
		
	}

}
