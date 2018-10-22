package web.jwc;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import domain.Account;
import domain.Student;
import domain.Teacher;
import domain.Year;
import domain.Yuanxi;
import domain.Zhuanye;
import factory.JWCFactory;
import factory.ServiceFactory;
import service.AccountService;
import service.JWCService;
import service.YearService;
import utilsOffice.ReadExcel;
import utilsOffice.WriteExcel;

/**
 * 信息导入
 * 
 * @author DaMoTou
 *
 */
@WebServlet("/ImportInfor.do")
public class ImportInfor extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JWCService jwcService = JWCFactory.getJwcService();
    private AccountService accountService=ServiceFactory.getAccountServiceInstance();
    private YearService yearService=ServiceFactory.getYearServiceInstance();
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ImportInfor() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@SuppressWarnings("rawtypes")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String inforSource = "";// 要导入的信息的来源，有院系、专业、老师、学生
		String docName = "";// 上传的文件名字
		// 文件上传的路径
		String path = request.getSession().getServletContext().getRealPath("/OfficeFile");
		// 上传结果反馈
		PrintWriter writer = response.getWriter();

		String pageNum=request.getParameter("pageNum");
		String option=request.getParameter("option");
		String inforFrom=request.getParameter("inforFrom");
		System.out.println(pageNum+"**********"+option+"***********"+inforFrom);
		Year year=yearService.getNowYear();
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			List items = upload.parseRequest(request);
			Iterator itr = items.iterator();
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				if (item.isFormField()) {
					// System.out.println("表单参数名:" + item.getFieldName() +
					// "，表单参数值:" + item.getString("UTF-8"));
					// 接受文件来源信息
					inforSource = item.getString("UTF-8");
				} else {
					if (item.getName() != null && !item.getName().equals("")) {
						docName = item.getName();
						File tempFile = new File(item.getName());

						File file = new File(path, tempFile.getName());
						item.write(file);
					} else {
						writer.print("文件上传失败!未选择文件!");
						System.out.println("upload.message没有选择上传文件！");
					}
				}
			}
		} catch (Exception e) {
			request.setAttribute("upload.message", "上传文件失败！");
			e.printStackTrace();
		}
		
		// 拼接文件路径，用于读取上传的文件
		File file = null;
		String pathTail = "/OfficeFile/" + docName;// 用于拼接成完整的文件路径名字
		// 院系信息-备用
		List<Yuanxi> yuanxis = jwcService.findAllYuanxi();
		Map<String, Integer> yuanxiMap = new HashMap<>();
		for (int j = 0; j < yuanxis.size(); j++) {
			yuanxiMap.put(yuanxis.get(j).getName(), yuanxis.get(j).getId());
		}
		// 专业信息-备用
		List<Zhuanye> zhuanyes = jwcService.findAllZhuanye();
		Map<String, Integer> zhuanyeMap = new HashMap<>();
		for (int i = 0; i < zhuanyes.size(); i++) {
			zhuanyeMap.put(zhuanyes.get(i).getName(), zhuanyes.get(i).getId());
		}
		// 文件导入结果
		boolean importResult = true;

		switch (inforSource) {   ///返回测代码有四种，11表示全部导入成功，01表示需要先导入院系，00表示要先导入专业，非以上三种表示部分信息导入失败
		// 导入院系信息
		case "院系信息":
			if (true) {
				file = new File(request.getSession().getServletContext().getRealPath(pathTail));
				// 取到从excel里读取来的数据
				Map<Integer, List<String>> yuanxiInforMap = ReadExcel.getDataFromExcel(file);
				List<String> data;
				// 循环读取行
				List<Integer> errorRecord = new ArrayList<>();
				for (int i = 0; i < yuanxiInforMap.size(); i++) {
					try {
						data = yuanxiInforMap.get(i);
						// 循环读取列，对应的是院系的字段
						Yuanxi yuanxi = new Yuanxi();
						// 院系名称
						yuanxi.setName(data.get(0));
						// 院系年份
						yuanxi.setYear(year.getYear());
						// 院系状态
						yuanxi.setStatus(1);

						jwcService.addYuanxi(yuanxi);
					} catch (Exception e) {//遇到错误，就停止导入，并指出错误所在的行
						importResult = false;
						errorRecord.add(i + 1);
						e.printStackTrace();
						break;
					}
				}
				if (importResult) {//如果导入成功，就跳转刷新
					//request.getRequestDispatcher("InitialPage.do?requestFrom=initial_yuanxi").forward(request, response);
					writer.print("11");
					writer.close();
				} else {//导入失败，就不跳转，直接json返回错误信息
					writer.print("信息导入有缺失!第" + errorRecord.toString() + "行数据错误");
					writer.close();
				}
			}
			
			break;
		// 导入专业信息
		case "专业信息":
			if (yuanxis.size() > 0) {
				file = new File(request.getSession().getServletContext().getRealPath(pathTail));
				// 取到从excel里读取来的数据
				Map<Integer, List<String>> zhuanyeInforMap = ReadExcel.getDataFromExcel(file);
				List<String> data;
				//错误list，用于存放错误行，让后导出到excel表
				List<String[]> errorRows=new ArrayList<>();
				for (int i = 0; i < zhuanyeInforMap.size(); i++) {
					data = zhuanyeInforMap.get(i);
					//先加到错误数组里，如果导入异常，就加到错误list里，然后导出，否则不加入错误list里
					String[]  errorRow ={(data.get(0)).toString(),data.get(1)};
					try {
						// 循环读取列，对应的是 专业的字段
						Zhuanye zhuanye = new Zhuanye();
						// 专业名称
						zhuanye.setName(data.get(0));
						// 院系ID
						zhuanye.setYx(yuanxiMap.get(data.get(1)));
						//专业代码
						zhuanye.setDm(data.get(2));
						// 专业年份 
						zhuanye.setYear(year.getYear());

						jwcService.addZhuanye(zhuanye);
					} catch (Exception e) {
						importResult = false;
						errorRows.add(errorRow);
						e.printStackTrace();
					}
				}
				if (importResult) {
					writer.print("11");
					writer.close();
				} else {
					String excelPath = request.getSession().getServletContext().getRealPath("FileCache/");
					WriteExcel.makeNewExcel(excelPath, errorRows, "专业和院系", "导入失败专业信息");
					writer.print("导入失败专业信息.xlsx");
				}
			} else {//导入专业信息之前，请先导入院系信息
				writer.print("01");
				writer.close();
			}
			//request.getRequestDispatcher("InitialPage.do?requestFrom=initial_zhuanye").forward(request, response);
			break;
		// 导入教师信息
		case "教师信息":
			if (yuanxis.size() > 0) {
				//错误list，用于存放错误行，让后导出到excel表
				List<String[]> errorRows=new ArrayList<>();
				file = new File(request.getSession().getServletContext().getRealPath(pathTail));
				// 取到从excel里读取来的数据
				Map<Integer, List<String>> teacherInforMap = ReadExcel.getDataFromExcel(file);
				List<String> data;
				// 循环读取行
				for (int i = 0; i < teacherInforMap.size(); i++) {
					data = teacherInforMap.get(i);
					//先加到错误数组里，如果导入异常，就加到错误list里，然后导出，否则不加入错误list里
					String[]  errorRow ={(data.get(0)).toString(),data.get(1)};
					try {
						// 循环读取列，对应的是教师的字段
						Teacher teacher = new Teacher();
						// 工号
						teacher.setNumber(data.get(0));
						// 姓名
						teacher.setName(data.get(1));
						// 性别
						if (data.get(2).equals("男")) {
							teacher.setSex(1);
						} else {
							teacher.setSex(0);
						}
						System.out.println("身份证号："+data.get(3));
						// 身份证号
						teacher.setIdentityN(data.get(3));
						// 院系
						int yxId=0;
						if (data.get(4)!=null) {
							try{
								yxId=yuanxiMap.get(data.get(4));
							}catch(NullPointerException e){
								System.out.println("导入的教职工没有院系信息，默认重置为0");
							}
						}
						teacher.setYx(yxId);
						// 手机号
						teacher.setTel(data.get(5));
						// 邮箱
						teacher.setEmail(data.get(6));
						//年份
						teacher.setYear(year.getYear());
						
						jwcService.addTeacher(teacher);
						
					} catch (Exception e) {
						importResult = false;
						errorRows.add(errorRow);
						e.printStackTrace();
					}
				}
				if (importResult) {
					writer.print("11");
					writer.close();
				} else {
					String excelPath = request.getSession().getServletContext().getRealPath("FileCache/");
					WriteExcel.makeNewExcel(excelPath, errorRows, "工号和姓名", "导入失败教职工信息");
					writer.print("导入失败教职工信息.xlsx");
				}
			} else {//导入教师信息之前，请先导入院系信息!
				writer.print("01");
				writer.close();
			}
			break;
		// 导入学生信息
		case "学生信息":
			if (yuanxis.size() > 0) {
				if (zhuanyes.size() > 0) {
					//错误list，用于存放错误行，让后导出到excel表
					List<String[]> errorRows=new ArrayList<>();
					
					file = new File(request.getSession().getServletContext().getRealPath(pathTail));
					// 取到从excel里读取来的数据
					Map<Integer, List<String>> studentInforMap = ReadExcel.getDataFromExcel(file);
					List<String> data;
					// 循环读取行
					for (int i = 0; i < studentInforMap.size(); i++) {
						data = studentInforMap.get(i);
						//先把学号和姓名加到错误数组里，如果导入异常，就加到错误list里，然后导出，否则不加入错误list里
						String[]  errorRow ={(data.get(0)).toString(),data.get(1)};
						try {
							// 循环读取列，对应的是学生的字段
							Student student = new Student();
							// 学号
							student.setNumber(data.get(0));
							// 姓名
							student.setName(data.get(1));
							// 性别
							if (data.get(2).equals("男")) {
								student.setSex(1);
							} else {
								student.setSex(0);
							}
							// 身份证号
							student.setIdentityN(data.get(3));
							// 院系
							student.setYx(yuanxiMap.get(data.get(4)));
							// 专业
							student.setZy(zhuanyeMap.get(data.get(5)));
							// 班级
							student.setBj(data.get(6));
							// 手机号
							student.setTel(data.get(7));
							// 年份
							student.setYear(year.getYear());
							
							jwcService.addStudent(student);
							//账号信息放到tb_account里，这样才能登陆。学生类型为1
							Account account=new Account(student.getNumber(),student.getNumber() , 1);
							accountService.saveAccount(account);
						} catch (Exception e) {
							importResult = false;
							errorRows.add(errorRow);
							//e.printStackTrace();
						}
					}
					if (importResult) {
						writer.print("11");
					} else {
						String excelPath = request.getSession().getServletContext().getRealPath("FileCache/");
						WriteExcel.makeNewExcel(excelPath, errorRows, "学号和姓名", "导入失败学生信息");
						writer.print("导入失败学生信息.xlsx");
					}
				} else {//导入学生信息之前，请先导入专业信息!
					writer.print("00");
					writer.close();
				}
			} else {//导入学生信息之前，请先导入院系信息!
				writer.print("01");
				writer.close();
			}
			break;
		}

	}
}
