package hr.fer.zemris.java.webapps.blog.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.webapps.blog.BlogUser;
import hr.fer.zemris.java.webapps.blog.crypto.Digester;
import hr.fer.zemris.java.webapps.blog.dao.DAOProvider;

/**
 * The {@link HttpServlet} which does the job of logging user in. If an error occurs
 * during the logging in, for example, invalid user name or password is provided,
 * appropriate error is shown above the log in form.
 * 
 * @author Vjeran
 */
@WebServlet("/servleti/login")
public class LoginServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nick = req.getParameter("nick");
		String pass = req.getParameter("password");
		String passHash = Digester.digest(pass);
		
		BlogUser user = DAOProvider.getDAO().getBlogUser(nick);
		
		if(user == null){
			showLoginError(req, resp, nick);
		} else {
			if(user.getPasswordHash().equals(passHash)){
				req.setAttribute("user", user);
				storeUserIntoSession(req, user);
				resp.sendRedirect(req.getContextPath() + "/servleti/author/" + user.getNick());
			} else {
				showLoginError(req, resp, nick);
			}
		}
	}

	/**
	 * Store current user properties into current session.
	 *
	 * @param req the {@link HttpServletRequest}
	 * @param user the user
	 */
	private void storeUserIntoSession(HttpServletRequest req, BlogUser user) {
		req.getSession().setAttribute("current.user.id", user.getId());
		req.getSession().setAttribute("current.user.fn", user.getFirstName());
		req.getSession().setAttribute("current.user.ln", user.getLastName());
		req.getSession().setAttribute("current.user.nick", user.getNick());
		req.getSession().setAttribute("current.user.email", user.getEmail());
	}

	/**
	 * Method for preparing error message to be rendered to log in page.
	 *
	 * @param req the {@link HttpServletRequest}
	 * @param resp the {@link HttpServletResponse}
	 * @param nick the nick
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void showLoginError(HttpServletRequest req, HttpServletResponse resp, String nick) 
								throws ServletException, IOException {
		req.setAttribute("nick", nick);
		req.setAttribute("errorMessage", "Invalid username or password!");
		resp.sendRedirect(req.getContextPath() + "/servleti/main");
	}

}
