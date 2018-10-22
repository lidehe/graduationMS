package web.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import domain.Jsgx;
import domain.Notice;
import domain.Notices;
import domain.Teacher;
import domain.Year;
import factory.JWCFactory;
import factory.ServiceFactory;
import net.sf.json.JSONObject;
import service.JWCService;
import utils.GetMime;

/**
 * Servlet implementation class Home
 */
@WebServlet("/home.do")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Home() {
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
		if (method == null)
			method = "ind";
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if (teacher == null && !"detail".equals(method)) {
			method = "ind";
		}
		switch (method) {
		case "ind":
			if (request.getSession().getAttribute("student") == null
					&& request.getSession().getAttribute("teacher") == null) {
				request.getRequestDispatcher("common/jsp/home1.jsp").forward(request, response);
			} else {
				if (request.getSession().getAttribute("student") != null)
					request.getRequestDispatcher("common/jsp/home2.jsp").forward(request, response);
				else
					request.getRequestDispatcher("common/jsp/home3.jsp").forward(request, response);
			}
			break;
		case "addZd":
			addZd(request, response);
			break;
		case "addZy":
			addZy(request, response);
			break;
		case "addYx":
			addYx(request, response);
			break;
		case "addXi":
			addXi(request, response);
			break;
		case "detail":
			noticeDetail(request, response);
			break;
		case "attach":
			downLoadAttach(request, response);
			break;
		case "delete":
			deleteNotice(request, response);
			break;
		default:
			break;
		}

	}

	private void deleteNotice(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		int id = Integer.valueOf(request.getParameter("id"));
		Notice notice = ServiceFactory.getStudentServiceInstance().getNotice(id);
		JSONObject json = new JSONObject();
		if (teacher != null && notice != null && teacher.getNumber().equals(notice.getGonghao())) {
			JWCService jwcService = JWCFactory.getJwcService();
			jwcService.deleteNotice(notice);
			json.put("status", 200);
			json.put("msg", "删除成功！<br/>即将关闭本页面");
		} else {
			json.put("status", 500);
			json.put("msg", "无权限删除此公告！");
		}
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(json);
		out.close();
	}

	protected void downLoadAttach(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boolean check = true;
		String idString = request.getParameter("id");
		if (idString == null || idString.equals(""))
			check = false;
		int id = 0;
		if (check)
			id = Integer.valueOf(idString);
		Notices attach = new Notices();
		if (id != 0)
			attach = ServiceFactory.getStudentServiceInstance().getOneNotices(id);
		if (id != 0 && attach != null) {
			String ext = attach.getUrl();
			response.setContentType(GetMime.returnMimeFromExt(ext) + ";charset=utf-8");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + URLEncoder.encode(attach.getFileName(), "UTF-8"));
			request.getRequestDispatcher(
					"WEB-INF/upload/" + attach.getYear() + "/teacher/notices/" + attach.getId() + "." + attach.getUrl())
					.forward(request, response);
		} else {
			response.sendRedirect("home.do");
		}

	}

	protected void noticeDetail(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String idString = request.getParameter("id");
		if (idString == null) {
			response.sendRedirect("home.do");
			return;
		} else {
			Notice notice = ServiceFactory.getStudentServiceInstance().getNotice(Integer.valueOf(idString));
			if (notice == null || notice.getType() == 4) {
				response.sendRedirect("home.do");
				return;
			} else {
				request.getRequestDispatcher("common/jsp/notice.jsp").forward(request, response);
			}
		}
	}

	protected void addZd(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		List<Jsgx> gxs = ServiceFactory.getTeacherServiceInstance().getTeacherJses(teacher);
		boolean js = false;
		for (Jsgx gx : gxs) {
			if (gx.getJs() == 1) {
				js = true;
				break;
			}
		}
		if (!js) {
			response.sendRedirect("login.do?method=logout");
			return;
		}
		addNotice(request, response, 0);
	}

	protected void addZy(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		List<Jsgx> gxs = ServiceFactory.getTeacherServiceInstance().getTeacherJses(teacher);
		boolean js = false;
		for (Jsgx gx : gxs) {
			if (gx.getJs() == 2) {
				js = true;
				break;
			}
		}
		if (!js) {
			response.sendRedirect("login.do?method=logout");
			return;
		}
		addNotice(request, response, 1);
	}

	protected void addYx(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		List<Jsgx> gxs = ServiceFactory.getTeacherServiceInstance().getTeacherJses(teacher);
		boolean js = false;
		for (Jsgx gx : gxs) {
			if (gx.getJs() == 3 || gx.getJs() == 4) {
				js = true;
				break;
			}
		}
		if (!js) {
			response.sendRedirect("login.do?method=logout");
			return;
		}
		addNotice(request, response, 2);
	}

	protected void addXi(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if (request.getSession().getAttribute("admin") == null) {
			List<Jsgx> gxs = ServiceFactory.getTeacherServiceInstance().getTeacherJses(teacher);
			boolean js = false;
			for (Jsgx gx : gxs) {
				if (gx.getJs() == 5) {
					js = true;
					break;
				}
			}
			if (!js) {
				response.sendRedirect("login.do?method=logout");
				return;
			}
		}
		addNotice(request, response, 3);
	}

	@SuppressWarnings("null")
	protected void addNotice(HttpServletRequest request, HttpServletResponse response, int type)
			throws IOException, ServletException {
		JSONObject json = new JSONObject();
		int status = 200;
		String ext = ".pdf,.PDF,.zip,.ZIP,.rar,.RAR,.doc,.DOC,.docx,.DOCX";
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		String title = "";
		String text = "";
		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		int yx = teacher.getYx();
		int zy = 0;
		if (type == 1) {
			zy = teacher.getFzr();
		}
		Notice notice = new Notice(title, text, teacher.getNumber(), yx, zy, type, year.getYear(), new Date());
		// 消息提示
		String message = "";
		String p = "notices";
		// 得到上传文件的保存目录，将上传文件存放在WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
		String savePath = this.getServletContext().getRealPath("WEB-INF/upload/" + year.getYear() + "/teacher/" + p);
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
		int noticeId = ServiceFactory.getTeacherServiceInstance().addNotice(notice);
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
					// getString()和getString("encoding");后者解决中文问题
					if ("title".equals(item.getFieldName())) {// 表单里的name属性
						title = item.getString("UTF-8");// 普通文本域的值，或者是文件的数据流值
					} else if ("text".equals(item.getFieldName())) {
						text = item.getString("UTF-8");
					}
				} else// 如果fileitem中封装的是上传文件
				{
					// 得到上传文件的文件名
					String fileName = item.getName().trim();
					// System.out.println("文件名：" + fileName);
					if (fileName == null && fileName.trim().length() == 0) {
						continue;
					}
					// 注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的
					// 如: C:\Users\H__D\Desktop\1.txt 而有些则是 ： 1.txt
					// 处理获取到的上传文件的文件名的路径部分，只保留文件名部分
					fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);

					// 得到上传文件的扩展名
					String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
					// 检查扩展名
					// 如果需要限制上传的文件类型，那么可以通过文件的扩展名来判断上传的文件类型是否合法
					// System.out.println("上传的文件的扩展名是：" + fileExt);
					if (!ext.contains(fileExt)) {
						// System.out.println("上传文件扩展名是不允许的扩展名：" + fileExt);
						message = message + "文件：" + fileName + "，上传文件扩展名是不允许的扩展名：" + fileExt + "\n";
						notice.setType(4);// 文件不允许，设置状态为错误
						status = 505;// 文件类型不允许
						break;
					}

					// 检查文件大小
					if (item.getSize() == 0)
						continue;
					if (item.getSize() > 1024 * 1024 * 10) {
						// System.out.println("上传文件大小：" + item.getSize());
						message = message + "文件：" + fileName + "，上传文件大小超过限制大小：" + upload.getFileSizeMax() + "\n";
						notice.setType(4);// 文件太大，设置状态为错误
						status = 506;// 文件太大了！
						break;
					}

					Notices notices = new Notices(noticeId, fileName.toLowerCase(), fileExt, year.getYear(), 0);
					int noticesId = ServiceFactory.getTeacherServiceInstance().addNotices(notices);

					// 得到存文件的文件名
					String saveFileName = noticesId + "." + fileExt;

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

					message = message + "文件：" + fileName + "，上传成功<br/>";
					notices.setId(noticesId);
					notices.setStatus(1);
					ServiceFactory.getTeacherServiceInstance().updateNotices(notices);
				}

			}
			notice.setId(noticeId);
			if (text == null || text.equals("") || title == null || title.equals("") || title.length() > 80) {
				message += "文本内容不完整或超常！\n";
				notice.setType(4);
				status = 500;// 文本域的内容有问题！
			} else {
				notice.setTitle(title);
				notice.setText(text);
				message += "发布成功！";
			}
			ServiceFactory.getTeacherServiceInstance().updateNotice(notice);
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
			response.setContentType("application/json;charset=utf-8");
			json.put("msg", message);
			json.put("status", status);
			PrintWriter out = response.getWriter();
			out.print(json);
			out.close();
		}
	}

}
