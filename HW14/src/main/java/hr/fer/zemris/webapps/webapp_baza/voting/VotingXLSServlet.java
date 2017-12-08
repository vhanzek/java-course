package hr.fer.zemris.webapps.webapp_baza.voting;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.webapps.webapp_baza.dao.DAOProvider;
import hr.fer.zemris.webapps.webapp_baza.poll.PollOption;

/**
 * The {@link HttpServlet} for creating new XLS file with voting results.
 * Two columns are created, first one represents name of the poll option 
 * and the second one represents number of votes for corresponding option.
 * 
 * @author Vjeran
 */
@WebServlet("/voting-xls")
public class VotingXLSServlet extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long pollID = Long.parseLong(request.getParameter("pollID"));
		List<PollOption> options = DAOProvider.getDAO().getPollOptionsEntries(pollID);
		
		response.setContentType("application/vnd.ms-excel");
		HSSFWorkbook hwb = createWorkbook(options);
		hwb.write(response.getOutputStream());
		hwb.close();
	}

	/**
	 * Helper method for creating new {@link HSSFWorkbook}.
	 * How workbook will be created is described in documentation 
	 * of this {@link VotingXLServlet} class.
	 *
	 * @param map the map with voting results
	 * @return the HSSF workbook
	 */
	private HSSFWorkbook createWorkbook(List<PollOption> options) {
		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet =  hwb.createSheet("Voting Results");
			
		HSSFRow rowhead = sheet.createRow(0);
		rowhead.createCell(0).setCellValue("Poll Option");
		rowhead.createCell(1).setCellValue("Votes Count");
		
		int counter = 0;
		for(PollOption opt: options){
			HSSFRow row = sheet.createRow(++counter);
			row.createCell(0).setCellValue(opt.getOptionTitle());
			row.createCell(1).setCellValue(opt.getVotesCount());
			sheet.autoSizeColumn(0);
			sheet.autoSizeColumn(1);
		}
		
		return hwb;
	}
}
