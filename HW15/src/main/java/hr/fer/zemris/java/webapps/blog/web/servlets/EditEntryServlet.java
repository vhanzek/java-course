package hr.fer.zemris.java.webapps.blog.web.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.webapps.blog.BlogEntry;
import hr.fer.zemris.java.webapps.blog.dao.DAOProvider;

/**
 * The {@link HttpServlet} which does the job of editing, i.e. updating chosen blog 
 * entry comment in the database. Once the editing has ended the user is redirected 
 * to site showing edited entry.
 * 
 * @author Vjeran
 */
@WebServlet("/servleti/edit")
public class EditEntryServlet extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long id = Long.parseLong(req.getParameter("EID"));
		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);
		String newTitle = req.getParameter("title");
		String newText = req.getParameter("text");
		
		DAOProvider.getDAO().updateBlogEntry(id, newTitle, newText);
		resp.sendRedirect(
			req.getContextPath() + "/servleti/author/" + entry.getCreator().getNick() + "/" + id
		);
	}
}
