package hr.fer.zemris.java.webapps.blog.web.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.webapps.blog.BlogEntry;
import hr.fer.zemris.java.webapps.blog.BlogUser;
import hr.fer.zemris.java.webapps.blog.dao.DAOProvider;

/**
 * The {@link HttpServlet} which does the job of persisting new blog entry to
 * database. Once the inserting has ended the user is redirected to site with 
 * entries of corresponding user.
 * 
 * @author Vjeran
 */
@WebServlet("/servleti/add")
public class AddEntryServlet extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long id = (Long) req.getSession().getAttribute("current.user.id");
		BlogUser creator = DAOProvider.getDAO().getBlogUser(id);
		
		BlogEntry entry = new BlogEntry();
		entry.fillFromRequest(req, creator);
		DAOProvider.getDAO().insertBlogEntry(entry);
		
		String loggedUserNick = (String) req.getSession().getAttribute("current.user.nick");
		resp.sendRedirect(req.getContextPath() + "/servleti/author/" + loggedUserNick);
	}
}
