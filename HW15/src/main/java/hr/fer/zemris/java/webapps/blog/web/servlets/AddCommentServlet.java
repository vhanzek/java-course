package hr.fer.zemris.java.webapps.blog.web.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.webapps.blog.BlogComment;
import hr.fer.zemris.java.webapps.blog.BlogEntry;
import hr.fer.zemris.java.webapps.blog.dao.DAOProvider;


/**
 * The {@link HttpServlet} which does the job of persisting new blog entry comment to
 * database. Once the inserting has ended the user is redirected to site with 
 * corresponding entry.
 * 
 * @author Vjeran
 */
@WebServlet("/servleti/add_comment")
public class AddCommentServlet extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long EID = Long.parseLong(req.getParameter("EID"));
		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(EID);
		
		BlogComment comment = new BlogComment();
		String email = (String) req.getSession().getAttribute("current.user.email");
		if(email == null) email = "Anonymous";
		
		comment.fillBlogComment(req, entry, email);
		DAOProvider.getDAO().insertBlogComment(comment);
		
		req.setAttribute("author", entry.getCreator());
		resp.sendRedirect(
			req.getContextPath() + "/servleti/author/" + entry.getCreator().getNick() + "/" + EID
		);		
	}
}
