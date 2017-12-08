package hr.fer.zemris.java.servlets.voting;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.servlets.PowersServlet;

/**
 * The {@link HttpServlet} for creating new XLS file with voting results.
 * Two columns are created, first one represents name of the band and the
 * second one represents number of votes for corresponding band.
 * 
 * @author Vjeran
 */
@WebServlet("/voting-xls")
public class VotingXLSServlet extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Integer> votingResults = 
				(Map<String, Integer>) request.getServletContext().getAttribute("votingResults");
		
		response.setContentType("application/vnd.ms-excel");
		HSSFWorkbook hwb = createWorkbook(votingResults);
		hwb.write(response.getOutputStream());
		hwb.close();
	}

	/**
	 * Helper method for creating new {@link HSSFWorkbook}.
	 * How workbook will be created is described in documentation 
	 * of this {@link PowersServlet} class.
	 *
	 * @param map the map with voting results
	 * @return the HSSF workbook
	 */
	private HSSFWorkbook createWorkbook(Map<String, Integer> map) {
		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet =  hwb.createSheet("Rezultati glasanja");
			
		HSSFRow rowhead = sheet.createRow(0);
		rowhead.createCell(0).setCellValue("Bend");
		rowhead.createCell(1).setCellValue("Broj glasova");
		
		int counter = 0;
		for(Map.Entry<String, Integer> entry : map.entrySet()){
			HSSFRow row = sheet.createRow(++counter);
			row.createCell(0).setCellValue(entry.getKey());
			row.createCell(1).setCellValue(entry.getValue());
			sheet.autoSizeColumn(0);
			sheet.autoSizeColumn(1);
		}
		
		return hwb;
	}
}
