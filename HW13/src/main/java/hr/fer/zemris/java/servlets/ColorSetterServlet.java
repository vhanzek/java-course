package hr.fer.zemris.java.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@link HttpServlet} which sets new background color, i.e. in its
 * method {@link #doGet(HttpServletRequest, HttpServletResponse)}, information
 * which describes wanted color (parameter {@code color}) is put into session
 * container so it can be accessed in JSP which will eventually change the 
 * background color.
 * 
 * @author Vjeran
 */
@WebServlet("/setcolor")
public class ColorSetterServlet extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
       
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String color = request.getParameter("color");		
		request.getSession().setAttribute("bgcolor", color);
		request.getRequestDispatcher("/colors.jsp").forward(request, response);
	}
}
