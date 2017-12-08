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
import hr.fer.zemris.webapps.webapp_baza.poll.PollOption;

/**
 * The {@link HttpServlet} which retrieves information about the offered poll options 
 * from the database. That information is shown on the web by JSP page. Each
 * poll option represents link and can be voted for by clicking on it.
 * 
 * @author Vjeran
 */
@WebServlet("/voting")
public class VotingServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long pollID = Long.parseLong(request.getParameter("pollID"));
		Poll poll = DAOProvider.getDAO().getPoll(pollID);
		List<PollOption> options = DAOProvider.getDAO().getPollOptionsEntries(pollID);
		
		request.setAttribute("poll", poll);
		request.setAttribute("options", options);
		
		request.getRequestDispatcher("/WEB-INF/pages/votingIndex.jsp").forward(request, response);
	}
}
