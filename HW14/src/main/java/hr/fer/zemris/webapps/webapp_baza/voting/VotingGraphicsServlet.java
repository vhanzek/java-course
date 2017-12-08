package hr.fer.zemris.webapps.webapp_baza.voting;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartUtilities;

import hr.fer.zemris.webapps.webapp_baza.dao.DAOProvider;
import hr.fer.zemris.webapps.webapp_baza.poll.PollOption;

/**
 * The helper {@link HttpServlet} used for rendering screenshot of the
 * {@link VotingPieChart} frame, i.e. image of chart showing vote distribution 
 * between the poll options.
 * 
 * @author Vjeran
 */
@WebServlet("/voting-graphics")
public class VotingGraphicsServlet extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long pollID = Long.parseLong(request.getParameter("pollID"));
		List<PollOption> options = DAOProvider.getDAO().getPollOptionsEntries(pollID);
		
		response.setContentType("image/png");
		VotingPieChart chart = new VotingPieChart("Voting Pie Chart", "Voting Results", options);
		ChartUtilities.writeChartAsPNG(
			response.getOutputStream(), 
			chart.getChart(), 
			chart.getWidth(), 
			chart.getHeight()
		);
	}
}
