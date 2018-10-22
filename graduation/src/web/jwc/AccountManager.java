package web.jwc;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Account;
import factory.ServiceFactory;
import service.AccountService;

/**
 * Servlet implementation class AccountManager
 */
@WebServlet("/AccountManager.do")
public class AccountManager extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AccountService accountService = ServiceFactory.getAccountServiceInstance();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AccountManager() {
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
		String number = request.getParameter("number");
		Account account  = accountService.getAccountBYNumber(number);
		account.setPassword(number);
		accountService.updateAccount(account);
		
	}

}
