package hr.fer.zemris.webapps.webapp_baza.voting;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.webapps.webapp_baza.dao.DAOProvider;
import hr.fer.zemris.webapps.webapp_baza.poll.Poll;

/**
 * The {@link HttpServlet} used for showing all defined polls to user i.e. it retrieves 
 * poll table from the database and forwards client's request to <code>JSP</code> file.
 * Also, it represents welcome site of this server. There are two offered polls -
 * 'Vote for your favorite band' and 'Vote for your favorite NBA club'.
 * 
 * @author Vjeran
 */
@WebServlet("/index.html")
public class PollsServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Poll> polls = DAOProvider.getDAO().getPollsEntries();
		
		req.setAttribute("polls", polls);
		req.getRequestDispatcher("/WEB-INF/pages/pollIndex.jsp").forward(req, resp);
	}
}
