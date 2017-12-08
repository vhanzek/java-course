package hr.fer.zemris.java.servlets.voting;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartUtilities;

import hr.fer.zemris.java.chart.PieChart;
import hr.fer.zemris.java.chart.VotingPieChart;

/**
 * The helper {@link HttpServlet} used for rendering screenshot of the
 * {@link VotingPieChart} frame, i.e. image of chart showing vote distribution 
 * between the bands.
 * 
 * @author Vjeran
 */
@WebServlet("/voting-graphics")
public class VotingGraphicsServlet extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("image/png");
		Map<String, Integer> votingResults = 
			(Map<String, Integer>) request.getServletContext().getAttribute("votingResults");
		
		PieChart chart = new VotingPieChart("PieChart", "Rezultati glasanja", votingResults);
		ChartUtilities.writeChartAsPNG(
			response.getOutputStream(), 
			chart.getChart(), 
			chart.getWidth(), 
			chart.getHeight()
		);
	}
}
