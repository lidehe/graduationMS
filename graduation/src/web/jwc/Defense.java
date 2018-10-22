package web.jwc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Student;
import factory.JWCFactory;
import net.sf.json.JSONArray;
import service.JWCService;

/**
 * 答辩管理
 * Servlet implementation class Defense
 */
@WebServlet("/Defense.do")
public class Defense extends HttpServlet {
	private static final long serialVersionUID = 1L;
    JWCService jwcService=JWCFactory.getJwcService();
    public Defense() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("答辩统计");
		JSONArray jsonArray = null;
		PrintWriter writer = response.getWriter();
		
		List<Student> students=new ArrayList<>();
		students=jwcService.findAllStudent();
		List<Integer> result=new ArrayList<>();
		result.add(0);
		result.add(0);
		result.add(0);
		result.add(0);
		result.add(0);
        for(int i=0;i<students.size();i++){
        	if (students.get(i).getAnswer()==0) {
        		int n=result.get(0);
        		result.set(0, n+1);
			}
        	if (students.get(i).getAnswer()==1) {
        		int n=result.get(1);
        		result.set(1, n+1);
			}
        	if (students.get(i).getAnswer()==2) {
        		int n=result.get(2);
        		result.set(2, n+1);
			}
        	if (students.get(i).getAnswer()==3) {
        		int n=result.get(3);
        		result.set(3, n+1);
			}
        	if (students.get(i).getAnswer()==4) {
        		int n=result.get(4);
        		result.set(4, n+1);
			}
        }
        jsonArray=JSONArray.fromObject(result);
        writer.print(jsonArray);
        writer.close();
	}

}
