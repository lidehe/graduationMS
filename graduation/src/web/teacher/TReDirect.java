package web.teacher;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Jsgx;
import domain.Lw;
import domain.Student;
import domain.Teacher;
import factory.ServiceFactory;

/**
 * Servlet implementation class TReDirect
 */
@WebServlet("/teacherDirect.do")
public class TReDirect extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TReDirect() {
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
		String position = request.getParameter("pt");
		Teacher teacher = (Teacher)request.getSession().getAttribute("teacher");
		if(position == null){//登陆之后，依据角色关系，查询这位老师的第一个角色，赋值角色，以便接下来进行跳转
			List<Jsgx> jses = ServiceFactory.getTeacherServiceInstance().getTeacherJses(teacher);
			switch (jses.get(0).getJs()) {
			case 1:
				position = "zdls";
				break;
			case 2:
				position = "zyfzr";
				break;
			case 3:
				position = "jxms";
				break;
			case 4:
				position = "yxfzr";
				break;
			case 5:
				position = "admin";
				break;
			case 6:
				position = "pyry";
				break;
			case 7:
				position = "cjry";
				break;
			case 8:
				position = "dbzcy";
				break;
			case 9:
				position = "dcld";
				break;
			case 10:
				position = "ypyry";
				break;
			default:
				break;
			}
		}
		switch (position) {
		case "zdls"://以指导老师身份进行下一步跳转，依据第一个学生的工作进度默认进入相应工作页面
			request.getSession().setAttribute("js", "指导老师");
			List<Student> students = ServiceFactory.getTeacherServiceInstance().getTeacherStudentsEsc(teacher);
			int stage = 0;
			if(students != null && students.size() > 1)
				stage = students.get(0).getStage();
			switch (stage) {
			case 0:
				response.sendRedirect("tkt.do");
				break;
			case 3:
				response.sendRedirect("tkt.do");
				break;
			case 6:
				response.sendRedirect("trws.do");
				break;
			case 8:
				response.sendRedirect("tktbg.do");
				break;
			case 11:
				response.sendRedirect("tjdxcg.do");
				break;
			case 14:
				Lw lw = ServiceFactory.getTeacherServiceInstance().getStudentLw(students.get(0));
				if(lw.getStatus() == 3)
					response.sendRedirect("lw.do");
				else {
					response.sendRedirect("tlwcg.do");
				}
				break;
			default:
				response.sendRedirect("tkt.do");
				break;
			}
			break;
			
		case "zyfzr"://以专业负责人的身份进行下一步跳转
			request.getSession().setAttribute("js", "专业负责人");
			response.sendRedirect("zkt.do");
			break;
				
		case "jxms":
			request.getSession().setAttribute("js", "教学秘书");
			response.sendRedirect("secretary/work_roleSet/roleSet.jsp");
			break;
			
		case "yxfzr":
			request.getSession().setAttribute("js", "院系负责人");
			response.sendRedirect("yz.do?meth=charts");
			break;
			
		case "admin":
			request.getSession().setAttribute("js", "教务处管理员");
			response.sendRedirect("jwc/work_initial/initial.jsp");
			break;
			
		case "pyry":
			request.getSession().setAttribute("js", "校级评优老师");
			response.sendRedirect("pyry.do");
			break;
			
		case "cjry":
			request.getSession().setAttribute("js", "抽检老师");
			response.sendRedirect("cjry.do");
			break;
			
		case "dbzcy":
			request.getSession().setAttribute("js", "答辩组老师");
			response.sendRedirect("dbz.do");
			break;
			
		case "dcld":
			request.getSession().setAttribute("js", "校领导、督导");
			response.sendRedirect("dcld.do");
			break;
			
		case "ypyry":
			request.getSession().setAttribute("js", "院系评优老师");
			response.sendRedirect("ypyry.do");
			break;
			
		default:
			
			break;
		}
	}

}
