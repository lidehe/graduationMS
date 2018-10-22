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
 * Servlet implementation class Yuanxi
 * 院系管理的控制层
 */
@WebServlet("/Yuanxi.do")
public class YuanxiWeb extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private JWCService jwcService=JWCFactory.getJwcService();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public YuanxiWeb() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		String method="";
		method=request.getParameter("method");
		String yuanxiId="";
		yuanxiId=request.getParameter("yuanxiId");
		String zhuanyeId=request.getParameter("zhuanyeId");
		JSONArray jarray=null;
		PrintWriter writer =response.getWriter();
		switch(method){
		//添加院系
		case "add": System.out.println("添加院系信息");
		break;
		//删除院系
		case "delete": System.out.println("删除");
		break;
		//更新院系信息
		case "update": System.out.println("更新");
		break;
		//根据名字模糊院系查询
		case "find": System.out.println("按名字模糊查询院系信息");
		break;
		case "getAllYuanxi": 
			//System.out.println("查找所有院系信息");
		List<domain.Yuanxi> yuanxiList=new ArrayList<>();
		yuanxiList=jwcService.findAllYuanxi();
		List<domain.Yuanxi> yuanxisObj=new ArrayList<>();
		for(int i=0;i<yuanxiList.size();i++){
			domain.Yuanxi yuanxi=yuanxiList.get(i);//new domain.Yuanxi(yuanxiList.get(i).getId(),yuanxiList.get(i).getName(),yuanxiList.get(i).getYear(),yuanxiList.get(i).getStatus());
			yuanxisObj.add(yuanxi);
		}
		//System.out.println("查到的院系个数是："+yuanxisObj.size());
		jarray=JSONArray.fromObject(yuanxisObj);
		writer.print(jarray);
		writer.close();
		break;
        case "findProfessionByYuanXiId"://根据院系查找专业
			List<Zhuanye> zhuanyes=new ArrayList<>();
			zhuanyes=jwcService.findByYuanxiId(Integer.valueOf(yuanxiId));
			jarray=JSONArray.fromObject(zhuanyes);
			if (zhuanyes.size()>0) {
				writer.print(jarray);
				writer.close();
			}else{
				writer.print("00");
				writer.close();
			}
			break;
        case "findClassByZhuanyeId"://根据院系查找专业
			List<String> banji=new ArrayList<>();
			banji=jwcService.findBanjiByZhuanyeId(Integer.valueOf(zhuanyeId));
			if (banji.size()>0) {
				String jString="[";
				for(String s:banji){
					String temp="";
					temp+="{name:'"+s+"'},";
					jString+=temp;
				}
				jString=jString.substring(0,jString.length()-1);
				jString+="]";
				jarray=JSONArray.fromObject(jString);
				writer.print(jarray);
				writer.close();
			}else{
				writer.print("00");
				writer.close();
			}
			break;
		}
	}

}
