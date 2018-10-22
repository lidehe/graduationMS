package web.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Zhuanye;
import factory.JWCFactory;
import net.sf.json.JSONArray;
import service.JWCService;

/**
 * Servlet implementation class ZhuanyeWeb
 */
@WebServlet("/ZhuanyeWeb.do")
public class ZhuanyeWeb extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private JWCService jwcService=JWCFactory.getJwcService();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ZhuanyeWeb() {
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
		String method="";
		method=request.getParameter("method");
		String yuanxiId="";
		yuanxiId=request.getParameter("yuanxiId");
		
		
		JSONArray jsonArray=null;
		PrintWriter writer=response.getWriter();
		
		switch (method) {
		case "findByYuanxiId":
			List<Zhuanye> zhuanyes=new ArrayList<>();
			zhuanyes=jwcService.findByYuanxiId(Integer.valueOf(yuanxiId));
			jsonArray=JSONArray.fromObject(zhuanyes);
			writer.println(jsonArray);
			writer.close();
			break;

		default:
			break;
		}
	}
	
	public void jsonArrPrint(JSONArray jsonArray,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		
	}

}
