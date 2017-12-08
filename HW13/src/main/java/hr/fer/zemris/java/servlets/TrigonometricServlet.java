package hr.fer.zemris.java.servlets;

import static java.lang.Math.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@link HttpServlet} which calculates sinus and cosinus of numbers in given range.
 * Range is given by parameters <code>a</code> and <code>b</code>. That calculations are
 * forwarded to JSP which shoes them in a table.
 * 
 * @author Vjeran
 */
@WebServlet("/trigonometric")
public class TrigonometricServlet extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
       
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String stringA = request.getParameter("a");
		String stringB = request.getParameter("b");
		
		int a = stringA == null ? 0 : Integer.parseInt(stringA);
		int b = stringB == null ? 360 : Integer.parseInt(stringB);
		if(a > b) {
			int temp = a;
			a = b;
			b = temp;
		}
		if(b > a + 720) b = a + 720;
		
		List<Calculation> calculations = new ArrayList<>();
		for(int i = a; i <= b; i++){
			calculations.add(new Calculation(i));
		}
		
		request.setAttribute("trigCalculations", calculations);	
		request.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(request, response);		
	}
	
	/**
	 * The inner static class used for storing information about specified value.
	 * There are two calculated values - {@code sin(value)} and {@code cos(value)}.
	 * 
	 * @author Vjeran
	 */
	public static class Calculation{
		
		/** The value. */
		private int value;
		
		/** The sin value. */
		private double sinValue;
		
		/** The cos value. */
		private double cosValue;
		
		/**
		 * Instantiates a new {@link Calculation}.
		 *
		 * @param value the value
		 */
		public Calculation(int value) {
			this.value = value;
			this.sinValue = sin(toRadians(value));
			this.cosValue = cos(toRadians(value));
		}

		/**
		 * Gets the value.
		 *
		 * @return the value
		 */
		public int getValue() {
			return value;
		}

		/**
		 * Gets the sin value.
		 *
		 * @return the sin value
		 */
		public double getSinValue() {
			return sinValue;
		}

		/**
		 * Gets the cos value.
		 *
		 * @return the cos value
		 */
		public double getCosValue() {
			return cosValue;
		}
	}
}
