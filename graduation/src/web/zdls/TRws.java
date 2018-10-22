package web.zdls;

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
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import domain.Jsgx;
import domain.Teacher;
import domain.Year;
import factory.ServiceFactory;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class TRws
 */
@WebServlet("/trws.do")
public class TRws extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TRws() {
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
		List<Jsgx> gxs = ServiceFactory.getTeacherServiceInstance().getTeacherJses(teacher);
		boolean js = false;
		for (Jsgx gx : gxs) {
			if (gx.getJs() == 1) {
				js = true;
				break;
			}
		}
		if (!js) {// 身份不对，直接自动退出
			response.sendRedirect("login.do?method=logout");
			return;
		}
		switch (method) {
		case "ind":
			request.getRequestDispatcher("zdls/working/trws.jsp").forward(request, response);
			break;
		case "upload":
			uploadRws(request, response);
			break;
		default:
			break;
		}
	}
	

	@SuppressWarnings({ "null", "finally" })
	protected void uploadRws(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json; charset=utf-8");
		JSONObject json = new JSONObject();
		PrintWriter writer = response.getWriter();
		// 消息提示
		String message = "";
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		int id = 0;
		int kt = 0;
		// 得到上传文件的保存目录，将上传文件存放在WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
		Year year = ServiceFactory.getYearServiceInstance().getNowYear();
		String savePath = this.getServletContext().getRealPath("WEB-INF/upload/" + year.getYear() + "/teacher/rws");
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
			// factory.setSizeThreshold(1024 * 10);// 设置缓冲区的大小为100KB，如果不指定，那么默认缓冲区的大小是10KB
			// 设置上传时生成的临时文件的保存目录
			factory.setRepository(tmpFile);
			// 2.创建一个文件上传解析器
			ServletFileUpload upload = new ServletFileUpload(factory);
			// 监听文件上传进度
			upload.setProgressListener(new ProgressListener() {

				@Override
				public void update(long readedBytes, long totalBytes, int currentItem) {
					// TODO Auto-generated method stub
					// System.out.println("当前已处理：" + readedBytes + "-----------文件大小为：" + totalBytes
					// + "--" + currentItem);
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
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				// 如果fileitem中封装的是普通的输入想数据
				if (item.isFormField()) {
					kt = Integer.valueOf(item.getString());
					id = ServiceFactory.getTeacherServiceInstance().createRwsRecordByTeacher(kt, teacher);
					switch (id) {
					case -1:
						json.put("msg", "课题错误！");
						break;
					case -2:
						json.put("msg", "您没有权限修改这个！");
						break;
					default:
						break;
					}
					if (id < 0) {
						writer.print(json);
						writer.close();
						return;
					}
				} else// 如果fileitem中封装的是上传文件
				{
					if (id == 0) {
						FileItem item1 = (FileItem) itr.next();
						kt = Integer.valueOf(item1.getString());
						id = ServiceFactory.getTeacherServiceInstance().createRwsRecordByTeacher(kt, teacher);
						switch (id) {
						case -1:
							json.put("msg", "课题错误！");
							break;
						case -2:
							json.put("msg", "您没有权限修改这个！");
							break;
						default:
							break;
						}
						if (id < 0) {
							writer.print(json);
							writer.close();
							return;
						}
					}

					// 得到上传文件的文件名
					String fileName = item.getName();
					fileName = fileName.trim().toLowerCase();
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
					if (!"pdf".contains(fileExt)) {
						System.out.println("上传文件扩展名是不允许的扩展名：" + fileExt);
						message = message + "文件：" + fileName + "，上传文件扩展名是不允许的扩展名：" + fileExt;
						break;
					}

					// 检查文件大小
					if (item.getSize() == 0)
						continue;
					if (item.getSize() > 1024 * 1024 * 10) {
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

					// 文件上传成功，更新记录，让status成为1,表示文件已上传
					ServiceFactory.getTeacherServiceInstance().updateRwsStatus(id, fileName);

				}

			}

		} catch (FileSizeLimitExceededException e) {
			// message = message + "上传文件大小超过限制";
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			json.put("msg", message);
			writer.print(json);
			writer.close();
			return;
		}
	}

}
