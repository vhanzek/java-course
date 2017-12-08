package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@link HttpServlet} which dynamically generates R, G and B color component. That information
 * is forwarded to JSP which shows some funny story on the web whit color of the font set to this
 * randomly generated color.
 * 
 * @author Vjeran
 */
@WebServlet("/stories")
public class StoriesServlet extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Random rand = new Random();
		int[] rgb = new int[3];
		for(int i = 0; i < 3; i++){
			rgb[i] = rand.nextInt(255);
		}
		
		request.setAttribute("rgb", rgb);
		request.getRequestDispatcher("/WEB-INF/pages/stories/funny.jsp").forward(request, response);;
	}
}
