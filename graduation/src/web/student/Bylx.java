package web.student;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Byfs;
import domain.Student;
import factory.ServiceFactory;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class Byls
 */
@WebServlet("/bylx.do")
public class Bylx extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Bylx() {
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
		String method = request.getParameter("ser");
		if(null == method)
			method = "ind";
		switch (method) {
		case "ind":
			ind(request, response);
			break;
		case "cho":
			cho(request, response);
			break;
		default:
			ind(request, response);
			break;
		}
	}
	
	protected void ind(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		request.getRequestDispatcher("student/preparation/bylx.jsp").forward(request, response);
	}
	
	protected void cho(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
		JSONObject json = new JSONObject();
		Student student = (Student)request.getSession().getAttribute("student");
		int type = student.getType();
		if(type != -1){//检测是否已经选择过毕业方式了，是则主动跳转
			//request.getRequestDispatcher("").forward(request, response);
			json.put("status", 202);//已经选过了
		}else{
			int id = Integer.valueOf(request.getParameter("id") == null ? "0" : request.getParameter("id"));
			Byfs byfs = ServiceFactory.getStudentServiceInstance().getStudentByfs(id);
			if(byfs == null)
				json.put("status", 500);//不存在的毕业方式
			else{
				json.put("status", 200);//正常通过
				student.setType(id);//确定了毕业方式
				student.setStage(3);//修改了学生的进度态，当前可以进入课题选择页面
				ServiceFactory.getStudentServiceInstance().updateStudent(student);
				request.getSession().setAttribute("student", student);
			}
		}
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(json);
		out.close();
	}

}
