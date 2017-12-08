package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartUtilities;

import hr.fer.zemris.java.chart.OSPieChart;
import hr.fer.zemris.java.chart.PieChart;

/**
 * The helper {@link HttpServlet} used for rendering {@link OSPieChart} image
 * with OS usage information on the web.
 * 
 * @author Vjeran
 */
@WebServlet("/renderImage")
public class RenderImageServlet extends HttpServlet{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("image/png");
		PieChart chart = new OSPieChart("PieChart", "Which operating system are you using?");
		ChartUtilities.writeChartAsPNG(
			response.getOutputStream(), 
			chart.getChart(), 
			chart.getWidth(), 
			chart.getHeight()
		);
	}
}
