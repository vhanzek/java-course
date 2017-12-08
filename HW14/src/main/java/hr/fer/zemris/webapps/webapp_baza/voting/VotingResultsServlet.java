package hr.fer.zemris.webapps.webapp_baza.voting;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.webapps.webapp_baza.dao.DAOProvider;
import hr.fer.zemris.webapps.webapp_baza.poll.PollOption;

/**
 * The {@link HttpServlet} whose job is to retrieve necessary data from the database
 * and forward that information to <code>votingRes.jsp</code> file so it can be shown
 * to user on the specified web site.
 * 
 * @author Vjeran
 */
@WebServlet("/voting-results")
public class VotingResultsServlet extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long id = Long.parseLong(request.getParameter("id"));
		long pollID = DAOProvider.getDAO().getPollForID(id);
		
		DAOProvider.getDAO().incrementVotesCount(id);
		List<PollOption> options = DAOProvider.getDAO().getPollOptionsEntries(pollID);
		List<PollOption> winners = DAOProvider.getDAO().getPollWinners(pollID);
		
		request.setAttribute("pollID", pollID);
		request.setAttribute("options", options);
		request.setAttribute("winners", winners);
		
		request.getRequestDispatcher("/WEB-INF/pages/votingRes.jsp").forward(request, response);
	}
}
