package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * The {@link HttpServlet} for creating new XLS file under these rules:
 * 
 * <p>There are 3 parameters - <code>a</code>, <code>b</code> and <code>n</code>.
 * <ul>
 * 	<li></li>
 * 	<li>First represents lower limit of numbers.</li>
 * 	<li>Second represents upper limit of numbers.</li>
 * 	<li>Third represents number of sheets in XLS file.</li>
 * <ul>
 * 
 * <p>In XLS table in every defined sheet first column represents numbers in 
 * range from <code>a</code> to <code>b</code> and second column represents
 * that number to the i-th power where {@code i} is cardinal number of the sheet.
 * 
 * <p>First two parameters can be integers in range from -100 to 100, while third
 * parameter can be between 1 and 5 including. If parameter is given out of the
 * defined range, application i redirected to error page.
 * 
 * @author Vjeran
 */
@WebServlet("/powers")
public class PowersServlet extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int[] params = new int[3];
		boolean allValid = checkParameters(
							  params,
							  request.getParameter("a"),
							  request.getParameter("b"),
							  request.getParameter("n")
						   );
		
		if(!allValid){
			request.getRequestDispatcher("/WEB-INF/pages/error.jsp")
				   .forward(request, response);
		} else {
			response.setContentType("application/vnd.ms-excel");
			HSSFWorkbook hwb = createWorkbook(params);
			hwb.write(response.getOutputStream());
			hwb.close();
		}
	}
	
	/**
	 * Helper method for creating new {@link HSSFWorkbook}.
	 * How workbook will be created is described in documentation 
	 * of this {@link PowersServlet} class.
	 *
	 * @param params the parameters
	 * @return the HSSF workbook
	 */
	private HSSFWorkbook createWorkbook(int[] params) {
		HSSFWorkbook hwb = new HSSFWorkbook();
		
		for(int i = 1; i <= params[2]; i++){
			HSSFSheet sheet =  hwb.createSheet("N^" + i);
			
			HSSFRow rowhead = sheet.createRow(0);
			rowhead.createCell(0).setCellValue("N");
			rowhead.createCell(1).setCellValue("N^" + i);
			
			for(int j = params[0]; j <= params[1]; j++){
				HSSFRow row = sheet.createRow((j+1)-params[0]);
				row.createCell(0).setCellValue(j);
				row.createCell(1).setCellValue(Math.pow(j, i));
			}
		}
		return hwb;
		
	}

	/**
	 * Helper method for checking if parameters are valid.
	 *
	 * @param params the parameters
	 * @param parA the parameter a
	 * @param parB the parameter b
	 * @param parN the parameter n
	 * @return <code>true</code>, if all are valid,
	 * 		   <code>false</code> otherwise
	 */
	private boolean checkParameters(int[] params, String parA, String parB, String parN) {
		boolean allValid = false;
		if(parA != null && parB != null && parN != null){
			params[0] = Integer.parseInt(parA);
			params[1] = Integer.parseInt(parB);
			params[2] = Integer.parseInt(parN);
			allValid = checkBounds(params[0], params[1], params[2]);
		}
		return allValid;
	}

	/**
	 * Helper method for checking is parameter are in valid bounds.
	 *
	 * @param a the a
	 * @param b the b
	 * @param n the n
	 * @return <code>true</code>, if all are inside the defined bounds,
	 * 		   <code>false</code> otherwise
	 */
	private boolean checkBounds(int a, int b, int n) {
		return ((a <= 100 && a >= -100) &&
				(b <= 100 && b >= -100) &&
				(n <= 5 && n >= 1));
	}
}
