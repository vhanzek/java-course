package hr.fer.zemris.java.webapps.blog.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.webapps.blog.BlogUser;
import hr.fer.zemris.java.webapps.blog.dao.DAOProvider;

/**
 * The {@link HttpServlet} which represents main page of the web application.
 * It shows list of registered users and offers to user to log in if he has an
 * account or to register if he does not.
 * 
 * @author Vjeran
 */
@WebServlet("/servleti/main")
public class MainServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<BlogUser> users = DAOProvider.getDAO().getBlogUsers();
		req.setAttribute("users", users);
		req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
	}
}
