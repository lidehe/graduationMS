package web.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import domain.Jdxcg;
import domain.Jdxcgs;
import domain.Ktbg;
import domain.Lw;
import domain.Lwcg;
import domain.Lws;
import domain.Student;
import factory.ServiceFactory;
import net.sf.json.JSONObject;
import utils.SetStudentStage;

/**
 * Servlet implementation class Upload
 */
@WebServlet("/upload.do")
public class Upload extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Upload() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String method = request.getParameter("meth");
		Student student = (Student) request.getSession().getAttribute("student");
		if (method == null)
			return;
		switch (method) {
		case "ktbg":
			if (student.getStage() < 6 || SetStudentStage.setEr(student) != 7)
				return;
			upload(request, response, 1);
			break;
		case "jdxcg":
			if (student.getStage() < 8 || SetStudentStage.setSan(student) != 8)
				return;
			upload(request, response, 2);
			break;
		case "jdxcgs":
			if (student.getStage() < 8 || SetStudentStage.setSan(student) != 8)
				return;
			jdxcgsUpload(request, response);
			break;
		case "lws":
			lwsUpload(request, response);
			break;
		case "lwcg":
			if (student.getStage() < 11 || SetStudentStage.setSi(student) != 11)
				return;
			upload(request, response, 3);
			break;
		case "lw":
			if (student.getStage() < 14)
				return;
			upload(request, response, 4);
			break;
		default:
			break;
		}
	}

	protected void lwsUpload(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JSONObject json = new JSONObject();
		response.setContentType("appliction/json;charset=utf-8");
		PrintWriter out1 = response.getWriter();
		Student student = (Student) request.getSession().getAttribute("student");
		int lwsNum = ServiceFactory.getStudentServiceInstance().getStudentLwsNum(student);
		boolean isOver = false;
		if (lwsNum >= 3) {
			isOver = true;
		}
		int id = 0;
		Lw lw = ServiceFactory.getStudentServiceInstance().getStudentLw(student);
		String message = "";
		if (lw == null || lw.getStatus() == 0) {
			json.put("msg", "暂时不可以上传附件！");
		} else {
			// 得到上传文件的保存目录，将上传文件存放在WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
			String savePath = this.getServletContext()
					.getRealPath("WEB-INF/upload/" + student.getYear() + "/student/lws");
			File saveFileDir = new File(savePath);
			if (!saveFileDir.exists()) {
				// 创建目录
				saveFileDir.mkdirs();
			}

			// 上传时生成临时文件保存目录
			String tmpPath = this.getServletContext().getRealPath("WEB-INF/tem");
			File tmpFile = new File(tmpPath);
			if (!tmpFile.exists()) {
				// 创建临时目录
				tmpFile.mkdirs();
			}

			try {
				// 使用Apache文件上传组件处理文件上传步骤：
				// 1.创建一个DiskFileItemFactory工厂
				DiskFileItemFactory factory = new DiskFileItemFactory();
				// 设置工厂的缓冲区的大小，当上传的文件大小超过缓冲区的大小时，就会生成一个临时文件存放到指定的临时目录当中
				// factory.setSizeThreshold(1024 * 10);//
				// 设置缓冲区的大小为100KB，如果不指定，那么默认缓冲区的大小是10KB
				// 设置上传时生成的临时文件的保存目录
				factory.setRepository(tmpFile);
				// 2.创建一个文件上传解析器
				ServletFileUpload upload = new ServletFileUpload(factory);
				// 监听文件上传进度
				upload.setProgressListener(new ProgressListener() {

					@Override
					public void update(long readedBytes, long totalBytes, int currentItem) {
						// TODO Auto-generated method stub
						// System.out.println("当前已处理：" + readedBytes +
						// "-----------文件大小为：" + totalBytes + "--" +
						// currentItem);
					}
				});
				// 解决上传文件名的中文乱码问题
				upload.setHeaderEncoding("UTF-8");
				// 3.判断提交上来的数据是否是上传表单的数据
				if (!ServletFileUpload.isMultipartContent(request)) {
					// 按照传统方式获取数据
					return;
				}

				// 设置上传单个文件的最大值
				upload.setFileSizeMax(1024 * 1024 * 20);// 20M
				// 设置上传文件总量的最大值，就是本次上传的所有文件的总和的最大值
				upload.setSizeMax(1024 * 1024 * 20);// 20M

				@SuppressWarnings("rawtypes")
				List items = upload.parseRequest(request);
				@SuppressWarnings("rawtypes")
				Iterator itr = items.iterator();

				FileItem item = null;
				while (itr.hasNext()) {
					FileItem item1 = (FileItem) itr.next();
					if (item1.isFormField()) {
						id = Integer.valueOf(item1.getString("utf-8"));
					} else
						item = item1;
				} // 遍历一遍先看看是否是更新
				if (id == 0) {// 这是新加的文件，做对应处理
					if (isOver) {// 如果已经三个了，直接返回！
						json.put("status", 400);
						json.put("msg", "已经上传三个，请覆盖原文件！");
						PrintWriter ou = response.getWriter();
						ou.print(json);
						ou.close();
						return;
					}
					// 后面的是为了处理正常上传新文件，js做出对应处理
					id = ServiceFactory.getStudentServiceInstance().getLwsId(student);
					json.put("k", 1);
					json.put("id", id);
				}
				Lws lws = ServiceFactory.getStudentServiceInstance().getStudentLws(id);
				if (lws == null || !lws.getXuehao().equals(student.getNumber())) {
					response.sendRedirect("login.do?method=logout");
					return;
				}

				// 得到上传文件的文件名
				String fileName = item.getName();
				// System.out.println("文件名：" + fileName);
				// 注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的
				// 如: C:\Users\H__D\Desktop\1.txt 而有些则是 ： 1.txt
				// 处理获取到的上传文件的文件名的路径部分，只保留文件名部分
				fileName = fileName.trim().toLowerCase();
				fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);

				// 得到上传文件的扩展名
				String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
				// 检查扩展名
				// 如果需要限制上传的文件类型，那么可以通过文件的扩展名来判断上传的文件类型是否合法
				// System.out.println("上传的文件的扩展名是：" + fileExt);
				if (!".pdf,.PDF,.zip,.ZIP,.rar,.RAR,.doc,.DOC,.docx,.DOCX".contains(fileExt)) {
					System.out.println("上传文件扩展名是不允许的扩展名：" + fileExt);
					message = message + "文件：" + fileName + "，上传文件扩展名是不允许的扩展名：" + fileExt;
				}

				// 检查文件大小
				if (item.getSize() > 1024 * 1024 * 20 || item.getSize() == 0) {
					// System.out.println("上传文件大小：" +
					// item.getSize());
					message = message + "文件：" + fileName + "，上传文件大小超过限制大小：" + upload.getFileSizeMax();
				}

				// 得到存文件的文件名
				String saveFileName = id + "." + fileExt;

				// 保存文件方法一// 获取item中的上传文件的输入流
				InputStream is = item.getInputStream();
				// 创建一个文件输出流
				FileOutputStream out = new FileOutputStream(savePath + "\\" + saveFileName);
				// 创建一个缓冲区
				byte buffer[] = new byte[1024];
				// 判断输入流中的数据是否已经读完的标致
				int len = 0;
				while ((len = is.read(buffer)) > 0) {
					out.write(buffer, 0, len);
				}
				// 关闭输出流
				out.close();
				// 关闭输入流
				is.close();
				// 删除临时文件
				item.delete();

				message = message + "文件：" + fileName + "，上传成功";
				// 文件上传成功，更新记录，让status成为1,表示文件已上传
				if (lws.getUrl() != null && !lws.getUrl().equals("")) {
					// 保留，为了删除多余文件的
				}
				json.put("name", fileName);
				lws.setFileName(fileName);
				lws.setUrl(fileExt);
				lws.setStatus(1);
				json.put("status", 200);
				json.put("msgs", "上传成功！");
				ServiceFactory.getStudentServiceInstance().updateLws(lws);

			} catch (SizeLimitExceededException e) {
				json.put("status", 500);
				json.put("msgs", "文件太大了！");
				// TODO: handle exception
			} catch (FileSizeLimitExceededException e) {
				// message = message + "上传文件大小超过限制";
				json.put("status", 500);
				e.printStackTrace();
			} catch (Exception e) {
				json.put("status", 500);
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				json.put("msg", message);
			}
		}
		out1.print(json);
		out1.close();
	}

	protected void jdxcgsUpload(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JSONObject json = new JSONObject();
		response.setContentType("appliction/json;charset=utf-8");
		PrintWriter out1 = response.getWriter();
		Student student = (Student) request.getSession().getAttribute("student");
		int jdxcgsNum = ServiceFactory.getStudentServiceInstance().getStudentJdxcgsNum(student);
		boolean isOver = false;
		if (jdxcgsNum >= 3) {
			isOver = true;
		}
		int id = 0;
		Jdxcg jdxcg = ServiceFactory.getStudentServiceInstance().getStudentJdxcg(student);
		String message = "";
		if (jdxcg == null || jdxcg.getStatus() == 0) {
			json.put("msg", "暂时不可以上传附件！");
		} else {
			// 得到上传文件的保存目录，将上传文件存放在WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
			String savePath = this.getServletContext()
					.getRealPath("WEB-INF/upload/" + student.getYear() + "/student/jdxcgs");
			File saveFileDir = new File(savePath);
			if (!saveFileDir.exists()) {
				// 创建目录
				saveFileDir.mkdirs();
			}

			// 上传时生成临时文件保存目录
			String tmpPath = this.getServletContext().getRealPath("WEB-INF/tem");
			File tmpFile = new File(tmpPath);
			if (!tmpFile.exists()) {
				// 创建临时目录
				tmpFile.mkdirs();
			}

			try {
				// 使用Apache文件上传组件处理文件上传步骤：
				// 1.创建一个DiskFileItemFactory工厂
				DiskFileItemFactory factory = new DiskFileItemFactory();
				// 设置工厂的缓冲区的大小，当上传的文件大小超过缓冲区的大小时，就会生成一个临时文件存放到指定的临时目录当中
				// factory.setSizeThreshold(1024 * 10);//
				// 设置缓冲区的大小为100KB，如果不指定，那么默认缓冲区的大小是10KB
				// 设置上传时生成的临时文件的保存目录
				factory.setRepository(tmpFile);
				// 2.创建一个文件上传解析器
				ServletFileUpload upload = new ServletFileUpload(factory);
				// 监听文件上传进度
				upload.setProgressListener(new ProgressListener() {

					@Override
					public void update(long readedBytes, long totalBytes, int currentItem) {
						// TODO Auto-generated method stub
						// System.out.println("当前已处理：" + readedBytes +
						// "-----------文件大小为：" + totalBytes + "--" +
						// currentItem);
					}
				});
				// 解决上传文件名的中文乱码问题
				upload.setHeaderEncoding("UTF-8");
				// 3.判断提交上来的数据是否是上传表单的数据
				if (!ServletFileUpload.isMultipartContent(request)) {
					// 按照传统方式获取数据
					return;
				}

				// 设置上传单个文件的最大值
				upload.setFileSizeMax(1024 * 1024 * 20);// 20M
				// 设置上传文件总量的最大值，就是本次上传的所有文件的总和的最大值
				upload.setSizeMax(1024 * 1024 * 20);// 20M

				@SuppressWarnings("rawtypes")
				List items = upload.parseRequest(request);
				@SuppressWarnings("rawtypes")
				Iterator itr = items.iterator();

				FileItem item = null;
				while (itr.hasNext()) {
					FileItem item3 = (FileItem) itr.next();
					if (item3.isFormField()) {
						id = Integer.valueOf(item3.getString("utf-8"));
					} else {
						item = item3;
					}
				} // 遍历一遍先看看是否是更新
				if (id == 0) {// 这是新加的文件，做对应处理
					if (isOver) {// 如果已经三个了，直接返回！
						json.put("status", 400);
						json.put("msg", "已经上传三个，请覆盖原文件！");
						PrintWriter ou = response.getWriter();
						ou.print(json);
						ou.close();
						return;
					}
					// 后面的是为了处理正常上传新文件，js做出对应处理
					id = ServiceFactory.getStudentServiceInstance().getJdxcgsId(student);
					json.put("k", 1);
					json.put("id", id);
				}
				Jdxcgs jdxcgs = ServiceFactory.getStudentServiceInstance().getJdxcgs(id);
				if (jdxcgs == null || !jdxcgs.getXuehao().equals(student.getNumber())) {
					response.sendRedirect("login.do?method=logout");
					return;
				}

				// 得到上传文件的文件名
				String fileName = item.getName();
				// System.out.println("文件名：" + fileName);
				// 注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的
				// 如: C:\Users\H__D\Desktop\1.txt 而有些则是 ： 1.txt
				// 处理获取到的上传文件的文件名的路径部分，只保留文件名部分
				fileName = fileName.trim().toLowerCase();
				fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);

				// 得到上传文件的扩展名
				String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
				// 检查扩展名
				// 如果需要限制上传的文件类型，那么可以通过文件的扩展名来判断上传的文件类型是否合法
				// System.out.println("上传的文件的扩展名是：" + fileExt);
				if (!".pdf,.PDF,.zip,.ZIP,.rar,.RAR,.doc,.DOC,.docx,.DOCX".contains(fileExt)) {
					System.out.println("上传文件扩展名是不允许的扩展名：" + fileExt);
					message = message + "文件：" + fileName + "，上传文件扩展名是不允许的扩展名：" + fileExt;
				}

				// 检查文件大小
				if (item.getSize() > 1024 * 1024 * 20 || item.getSize() == 0) {
					// System.out.println("上传文件大小：" +
					// item.getSize());
					message = message + "文件：" + fileName + "，上传文件大小超过限制大小：" + upload.getFileSizeMax();
				}

				// 得到存文件的文件名
				String saveFileName = id + "." + fileExt;

				// 保存文件方法一// 获取item中的上传文件的输入流
				InputStream is = item.getInputStream();
				// 创建一个文件输出流
				FileOutputStream out = new FileOutputStream(savePath + "\\" + saveFileName);
				// 创建一个缓冲区
				byte buffer[] = new byte[1024];
				// 判断输入流中的数据是否已经读完的标致
				int len = 0;
				while ((len = is.read(buffer)) > 0) {
					out.write(buffer, 0, len);
				}
				// 关闭输出流
				out.close();
				// 关闭输入流
				is.close();
				// 删除临时文件
				item.delete();

				message = message + "文件：" + fileName + "，上传成功";
				// 文件上传成功，更新记录，让status成为1,表示文件已上传
				if (jdxcgs.getUrl() != null && !jdxcgs.getUrl().equals("")) {
					// 保留，为了删除多余文件的
				}
				json.put("name", fileName);
				jdxcgs.setFileName(fileName);
				jdxcgs.setUrl(fileExt);
				json.put("status", 200);
				json.put("msgs", "上传成功！");
				ServiceFactory.getStudentServiceInstance().updateJdxcgs(jdxcgs);

			} catch (FileSizeLimitExceededException e) {
				// message = message + "上传文件大小超过限制";
				json.put("status", 500);
				e.printStackTrace();
			} catch (Exception e) {
				json.put("status", 500);
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				json.put("msg", message);
			}
		}
		out1.print(json);
		out1.close();
	}

	@SuppressWarnings("null")
	protected void upload(HttpServletRequest request, HttpServletResponse response, int ind)
			throws ServletException, IOException {
		// 消息提示
		String message = "";
		Student student = (Student) request.getSession().getAttribute("student");
		int id = 0;
		String p = "tmp";
		switch (ind) {// 是为了复用文件上传部分的代码，对path坐了switch操作；下面还有要更新此记录的操作，也需要switch
		case 1:
			id = ServiceFactory.getStudentServiceInstance().studentKtbg(student);
			student.setKtbg(id);
			Ktbg ktbg = ServiceFactory.getStudentServiceInstance().getStudentKtbg(student);
			if (ktbg != null && ktbg.getSh() == 1) {
				message = "审核已通过，不可再覆盖！";
				response.setContentType("application/json; charset=utf-8");
				JSONObject json1 = new JSONObject();
				json1.put("msg", message);
				PrintWriter out = response.getWriter();
				out.print(json1);
				out.close();
				return;
			}
			p = "ktbg";
			break;
		case 2:
			id = ServiceFactory.getStudentServiceInstance().studentJdxcg(student);
			student.setJdxcg(id);
			p = "jdxcg";
			break;
		case 3:
			id = ServiceFactory.getStudentServiceInstance().studentLwcg(student);
			p = "lwcg";
			break;
		case 4:
			id = ServiceFactory.getStudentServiceInstance().studentLw(student);
			student.setBs(id);
			p = "lw";
			break;
		default:
			break;
		}
		// 得到上传文件的保存目录，将上传文件存放在WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
		String savePath = this.getServletContext().getRealPath("WEB-INF/upload/" + student.getYear() + "/student/" + p);
		File saveFileDir = new File(savePath);
		if (!saveFileDir.exists()) {
			// 创建目录
			saveFileDir.mkdirs();
		}

		// 上传时生成临时文件保存目录
		String tmpPath = this.getServletContext().getRealPath("WEB-INF/tem");
		File tmpFile = new File(tmpPath);
		if (!tmpFile.exists()) {
			// 创建临时目录
			tmpFile.mkdirs();
		}
		response.setContentType("application/json; charset=utf-8");
		JSONObject json = new JSONObject();
		try {
			// 使用Apache文件上传组件处理文件上传步骤：
			// 1.创建一个DiskFileItemFactory工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 设置工厂的缓冲区的大小，当上传的文件大小超过缓冲区的大小时，就会生成一个临时文件存放到指定的临时目录当中
			// factory.setSizeThreshold(1024 * 10);//
			// 设置缓冲区的大小为100KB，如果不指定，那么默认缓冲区的大小是10KB
			// 设置上传时生成的临时文件的保存目录
			factory.setRepository(tmpFile);
			// 2.创建一个文件上传解析器
			ServletFileUpload upload = new ServletFileUpload(factory);
			// 监听文件上传进度
			upload.setProgressListener(new ProgressListener() {

				@Override
				public void update(long readedBytes, long totalBytes, int currentItem) {
					// TODO Auto-generated method stub
					// System.out.println("当前已处理：" + readedBytes +
					// "-----------文件大小为：" + totalBytes + "--" + currentItem);
				}
			});
			// 解决上传文件名的中文乱码问题
			upload.setHeaderEncoding("UTF-8");
			// 3.判断提交上来的数据是否是上传表单的数据
			if (!ServletFileUpload.isMultipartContent(request)) {
				// 按照传统方式获取数据
				return;
			}

			// 设置上传单个文件的最大值
			upload.setFileSizeMax(1024 * 1024 * 20);// 20M
			// 设置上传文件总量的最大值，就是本次上传的所有文件的总和的最大值
			upload.setSizeMax(1024 * 1024 * 20 * 5);// 100M

			@SuppressWarnings("rawtypes")
			List items = upload.parseRequest(request);
			@SuppressWarnings("rawtypes")
			Iterator itr = items.iterator();
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				// 如果fileitem中封装的是普通的输入想数据
				if (item.isFormField()) {
				} else// 如果fileitem中封装的是上传文件
				{
					// 得到上传文件的文件名
					String fileName = item.getName();
					// System.out.println("文件名：" + fileName);
					if (fileName == null && fileName.trim().length() == 0) {
						continue;
					}
					// 注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的
					// 如: C:\Users\H__D\Desktop\1.txt 而有些则是 ： 1.txt
					// 处理获取到的上传文件的文件名的路径部分，只保留文件名部分
					fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);

					fileName = fileName.trim().toLowerCase();
					// 得到上传文件的扩展名
					String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
					// 检查扩展名
					// 如果需要限制上传的文件类型，那么可以通过文件的扩展名来判断上传的文件类型是否合法
					// System.out.println("上传的文件的扩展名是：" + fileExt);
					if (!"pdf".contains(fileExt)) {
						System.out.println("上传文件扩展名是不允许的扩展名：" + fileExt);
						message = message + "文件：" + fileName + "，上传文件扩展名是不允许的扩展名：" + fileExt;
						break;
					}

					// 检查文件大小
					if (item.getSize() == 0)
						continue;
					if (item.getSize() > 1024 * 1024 * 20) {
						// System.out.println("上传文件大小：" + item.getSize());
						message = message + "文件：" + fileName + "，上传文件大小超过限制大小：" + upload.getFileSizeMax();
						break;
					}

					// 得到存文件的文件名
					String saveFileName = id + ".pdf";

					// 保存文件方法一// 获取item中的上传文件的输入流
					InputStream is = item.getInputStream();
					// 创建一个文件输出流
					FileOutputStream out = new FileOutputStream(savePath + "\\" + saveFileName);
					// 创建一个缓冲区
					byte buffer[] = new byte[1024];
					// 判断输入流中的数据是否已经读完的标致
					int len = 0;
					while ((len = is.read(buffer)) > 0) {
						out.write(buffer, 0, len);
					}
					// 关闭输出流
					out.close();
					// 关闭输入流
					is.close();
					// 删除临时文件
					item.delete();

					message = message + "文件：" + fileName + "，上传成功";
					int stage = 8;
					switch (ind) {// 文件上传成功，更新记录，让status成为1,表示文件已上传
					case 1:
						Ktbg ktbg = ServiceFactory.getStudentServiceInstance().getStudentKtbg(student);
						ktbg.setFileName(fileName);
						ktbg.setStatus(1);
						ServiceFactory.getStudentServiceInstance().updataKtbgStatus(ktbg);
						// stage = SetStudentStage.setSan(student);
						// student.setStage(stage);//更新学生状态信息，因为后来要加入老师审核，
						// 所以这个“开题报告”的状态更新放到，指导老师审核那里
						break;
					case 2:
						Jdxcg jdxcg = ServiceFactory.getStudentServiceInstance().getStudentJdxcg(student);
						jdxcg.setFileName(fileName);
						jdxcg.setStatus(1);
						ServiceFactory.getStudentServiceInstance().updateJdxcgStatus(jdxcg);
						if (student.getStage() == 8) {
							stage = SetStudentStage.setSi(student);
							student.setStage(stage);// 更新学生状态信息
						}
						break;
					case 3:
						Lwcg lwcg = ServiceFactory.getStudentServiceInstance().getStudentLwcg(student);
						lwcg.setFileName(fileName);
						lwcg.setStatus(1);
						ServiceFactory.getStudentServiceInstance().updateLwcgStatus(lwcg);
						if (student.getStage() == 11)
							student.setStage(14);
						break;
					case 4:
						Lw lw = ServiceFactory.getStudentServiceInstance().getStudentLw(student);
						lw.setFileName(fileName);
						lw.setStatus(1);
						ServiceFactory.getStudentServiceInstance().updateLwStatus(lw);
						break;
					default:
						break;
					}
					ServiceFactory.getStudentServiceInstance().updateStudent(student);
					request.getSession().setAttribute("student", student);

				}

			}

		} catch (SizeLimitExceededException e) {
			json.put("status", 500);
			json.put("msgs", "文件太大了！");
			// TODO: handle exception
		} catch (FileSizeLimitExceededException e) {
			// message = message + "上传文件大小超过限制";
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			json.put("msg", message);
			PrintWriter out = response.getWriter();
			out.print(json);
			out.close();
		}
	}

}
