package hr.fer.zemris.java.webapps.blog.web.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.webapps.blog.BlogUser;
import hr.fer.zemris.java.webapps.blog.dao.DAOProvider;


/**
 * The {@link HttpServlet} which does the job of registering new user i.e. creating
 * new account. If an error occurs during the registration, (for example, empty string was
 * provided), page reloads and shown appropriate error message above corresponding field. 
 * Once the registration has ended the user is redirected main page of web application.
 * 
 * @author Vjeran
 */
@WebServlet("/servleti/registration")
public class RegistrationServlet extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BlogUser user = new BlogUser();
		user.fillFromRequest(req);
		req.setAttribute("user", user);
		
		if(user.hasErrors()){
			req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
		} else {
			DAOProvider.getDAO().insertBlogUser(user);
		}
		
		resp.sendRedirect(req.getContextPath() + "/servleti/main");
	}
}
