package web.workControll;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Provider.Service;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import factory.ServiceFactory;
import service.WorkControlService;

/**
 * Servlet implementation class WorkControll
 */
@WebServlet("/WorkControll.do")
public class WorkControll extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private WorkControlService workControlService = ServiceFactory.getWorkcontrolInstance();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WorkControll() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getParameter("method");
		switch (method) {
		case "canCheckPaper":// 能否开启抽检工作
            canCheckPaper(request, response);
			break;

		default:
			break;
		}

	}

	public void canCheckPaper(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		boolean result = true;
		result = workControlService.canPapercheckStart();
		if (result) {
			writer.print("11");
		} else {
			writer.print("00");
		}
		writer.close();
	}
}
