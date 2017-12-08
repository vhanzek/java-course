package hr.fer.zemris.java.webapps.blog.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.webapps.blog.BlogEntry;
import hr.fer.zemris.java.webapps.blog.BlogUser;
import hr.fer.zemris.java.webapps.blog.dao.DAOProvider;

/**
 * <p>The {@link HttpServlet} which is mapped to any web site starting with 
 * servleti/author. There are multiple extensions that this servlet can process.
 * 
 * <p>If extensions has one part (/userNick), the site showing to user 
 * is account of the user with given user name. If there are two parts
 * (/userNick/EID), the second part represents ID of the entry and the first
 * part shows the creator of that entry. Mentioned entry is showed to user
 * with all of the posted comments and possibility to post a comment for logged
 * user and for user without an account. However, if the second part of the 
 * extension is not a number, then it represents a form for editing existing 
 * entry or adding a new one.
 * 
 * @author Vjeran
 */
@WebServlet("/servleti/author/*")
public class AuthorServlet extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The Constant NEW. */
	private static final String NEW = "new";
	
	/** The Constant EDIT. */
	private static final String EDIT = "edit";
	
	/** The Constant ERROR. */
	private static final String ERROR = "error";

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
						 throws ServletException, IOException {
		String pathInfo = req.getPathInfo().substring(1);
		String[] pathParts = pathInfo.split("/");
		
		if(pathParts.length == 1){
			processBlogAuthor(req, resp, pathParts[0]);
		} else if (pathParts.length == 2){
			processBlogEntry(req, resp, pathParts);
		} else if (pathParts.length == 3){
		} else {
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
		}
	}

	/**
	 * Helper method which processes two part paths, example .../userNick/new.
	 *
	 * @param req the {@link HttpServletRequest}
	 * @param resp the {@link HttpServletResponse}
	 * @param pathParts the path parts
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void processBlogEntry(HttpServletRequest req, HttpServletResponse resp, String[] pathParts) 
										throws ServletException, IOException {
		String fileName = "";
		if(pathParts[1].equals(NEW)){
			fileName = NEW;
		} else if (pathParts[1].equals(EDIT)){
			String id = req.getParameter("id");
			if(id == null){
				fileName = ERROR;
			} else {
				Long EID = Long.parseLong(id);
				BlogEntry entry = DAOProvider.getDAO().getBlogEntry(EID);
				req.setAttribute("entry", entry);
				fileName = EDIT;
			}
		} else {
			Long EID = null;
			try{
				EID = Long.parseLong(pathParts[1]);
			} catch (NumberFormatException nfe){
				fileName = ERROR;
			}
			
			if(!fileName.equals(ERROR)){
				BlogEntry entry = DAOProvider.getDAO().getBlogEntry(EID);
				BlogUser author = DAOProvider.getDAO().getBlogUser(pathParts[0]);
				req.setAttribute("entry", entry);
				req.setAttribute("author", author);
				req.getRequestDispatcher("/WEB-INF/pages/entry.jsp").forward(req, resp);
			}
		}
		
		if(!pathParts[0].equals(req.getSession().getAttribute("current.user.nick"))){
			fileName = ERROR;
		}
			
		req.getRequestDispatcher("/WEB-INF/pages/" + fileName + ".jsp").forward(req, resp);
	}

	/**
	 * Helper method which processes one part paths that look like
	 * .../userNick. It forwards request to {@code jsp} file.
	 *
	 * @param req the {@link HttpServletRequest}
	 * @param resp the {@link HttpServletResponse}
	 * @param nick the nick
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void processBlogAuthor(HttpServletRequest req, HttpServletResponse resp, String nick) throws ServletException, IOException {
		BlogUser author = DAOProvider.getDAO().getBlogUser(nick);
		List<BlogEntry> entries = DAOProvider.getDAO().getBlogEntries(author);
		
		req.setAttribute("author", author);
		req.setAttribute("entries", entries);
		req.getRequestDispatcher("/WEB-INF/pages/entries.jsp").forward(req, resp);
	}
}
