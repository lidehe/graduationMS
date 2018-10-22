package web.jwc;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Byfs;
import factory.JWCFactory;
import factory.ServiceFactory;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import service.JWCService;
import service.WorkControlService;
import utils.TempClass;
import utilsOffice.GetSortedMethodFromClass;

/**
 * 毕业方式管理
 * @author DaMoTou
 *
 */
@WebServlet("/Byfs.do")
public class ByfsWeb extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private JWCService jwcService=JWCFactory.getJwcService();
    private WorkControlService workControlService=ServiceFactory.getWorkcontrolInstance();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ByfsWeb() {
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
		String method=request.getParameter("method");
		String  byfs_name=request.getParameter("byfs_name");
		String byfsId=request.getParameter("byfsId");//对一条毕业方式操作时使用其Id
		String[] keys;//新增的毕设方式的字段号，与数据库相差2
		keys=request.getParameterValues("keys");
		String[] vals;//新增的毕设方式字段的值，字段号与数据库相差2
		vals=request.getParameterValues("vals");
		String  byfsName=request.getParameter("byfsName");
		String[] dates=request.getParameterValues("dates");
		switch(method){
		case "add":
			List<Method> methods=GetSortedMethodFromClass.getSortedMethods(Byfs.class);
			Object b = null;
			Class byfs;
			try {
				byfs = Class.forName("domain.Byfs");
				Method method2;
				b=byfs.newInstance();
				method2=methods.get(1);
				method2.invoke(b, byfs_name);
				for(int i=0;i<keys.length;i++){
				//	System.out.println(keys[i]);
					method2=methods.get(Integer.valueOf(keys[i])+2);
				//	System.out.println("第"+keys[i]+"个方法名字是："+method2.getName()+",它的值是："+vals[i]);
					method2.invoke(b, vals[i]);
				}
				//设置起止日期，模块一和五肯定有，二三四则根据下面判断模块是否有填写。
				//Date date=null;
				SimpleDateFormat smdf=new SimpleDateFormat("yyyy-MM-dd");
				//date=smdf.parse("2017-9-5");
				//模块一起止
				method2=methods.get(19);
				method2.invoke(b,smdf.parse(dates[0]));
				method2=methods.get(20);
				method2.invoke(b, smdf.parse(dates[1]));
				//模块五起止
				method2=methods.get(27);
				method2.invoke(b, smdf.parse(dates[8]));
				method2=methods.get(28);
				method2.invoke(b, smdf.parse(dates[9]));
				//下面判断可选模块是否有填写
				for(int i=0;i<keys.length;i++){
					if (keys[i].equals("4")) {
						//System.out.println("模块二填写了");
						method2=methods.get(16);
						method2.invoke(b, 0);
						//模块2起止日期
						method2=methods.get(21);
						method2.invoke(b, smdf.parse(dates[2]));
						method2=methods.get(22);
						method2.invoke(b, smdf.parse(dates[3]));
					}
					if (keys[i].equals("7")) {
						//System.out.println("模块三填写了");
						method2=methods.get(17);
						method2.invoke(b, 0);
						//模块3起止日期
						method2=methods.get(23);
						method2.invoke(b,smdf.parse(dates[4]));
						method2=methods.get(24);
						method2.invoke(b,smdf.parse(dates[5]));
					}
					if (keys[i].equals("10")) {
						//System.out.println("模块四填写了");
						method2=methods.get(18);
						method2.invoke(b, 0);
						//模块4起止日期
						method2=methods.get(25);
						method2.invoke(b, smdf.parse(dates[6]));
						method2=methods.get(26);
						method2.invoke(b, smdf.parse(dates[7]));
					}
				}
				jwcService.addByfs((Byfs)b);
			} catch (Exception e) {
				throw new RuntimeException("自动初始化毕业方式出错！");
			} 
			break;
		case "findById"://根据名字找毕业方式
			Byfs byfs2=null;
			byfs2=jwcService.findByfsByID(Integer.valueOf(byfsId));
			PrintWriter writerfindOne1=response.getWriter();
			if (byfs2!=null) {
				System.out.println(byfs2.toString());
				JSONObject tempfindOne1=JSONObject.fromObject(byfs2);
				writerfindOne1.println(tempfindOne1);
			}else{
				writerfindOne1.print("00");
			}
			writerfindOne1.close();
			break;
		case "findOne":
			Byfs byfss=new Byfs();
			byfss=jwcService.findByfsByID(Integer.valueOf(byfsId));
			PrintWriter writerfindOne=response.getWriter();
			if (byfss!=null) {
				//System.out.println(byfss.toString());
				JSONArray tempfindOne=JSONArray.fromObject(byfss);
				writerfindOne.println(tempfindOne);
			}else{
				writerfindOne.print("00");
			}
			writerfindOne.close();
			break;
		case "findAll":
			//因为在毕业方式列表页面不需要显示毕业方式的所有信息，所以只去id和name两个属性传递到页面
			List<TempClass> tempList=new ArrayList<>();
			List<Byfs> byfsList=new ArrayList<>();
			byfsList=jwcService.findAllByfs();
			if(byfsList.size()>0)
			for(int i=0;i<byfsList.size();i++){
				TempClass tempClass=new TempClass();
				tempClass.setId(byfsList.get(i).getId());
				tempClass.setParam1(byfsList.get(i).getName());
				tempList.add(tempClass);
			}
			JSONArray tempfindAll=JSONArray.fromObject(tempList);
			PrintWriter writerfindAll=response.getWriter();
			writerfindAll.println(tempfindAll);
			break;
		case "deleteByfs":
           deleteByfs(request, response);
			break;
		}
	}

	/**
	 * 删除毕业方式，后台检查时候有学生使用了该毕业方式，如果有，则不允许删除;此外，模板也是不允许删除的
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void deleteByfs(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer=response.getWriter();
		
		String byfsIdStr=request.getParameter("byfsId");
		int byfsId=Integer.valueOf(byfsIdStr);
		if (byfsId==1) {//如果是模板，就不给删除
			writer.print("01");
		}else{
			boolean isByfsUsed=workControlService.isByfsUsed(byfsId);
			if (!isByfsUsed) {//没用使用，则可以删除
				Byfs byfs=jwcService.findByfsByID(byfsId);
				jwcService.deleteByfs(byfs);
				writer.print("11");
			}else{//使用过了，就不能删除
				writer.print("00");
			}
		}
		writer.close();
	}
}
