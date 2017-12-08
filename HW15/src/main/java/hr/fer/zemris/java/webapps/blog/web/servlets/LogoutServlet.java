package hr.fer.zemris.java.webapps.blog.web.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * The {@link HttpServlet} which does the job of logging currently logged user out.
 * Once the logging out has ended the user is redirected main page of web application.
 * 
 * @author Vjeran
 */
@WebServlet("/servleti/logout")
public class LogoutServlet extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().invalidate();
		resp.sendRedirect(req.getContextPath() + "/servleti/main");
	}
}
