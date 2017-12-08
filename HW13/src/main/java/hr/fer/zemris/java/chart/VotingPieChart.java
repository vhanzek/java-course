package hr.fer.zemris.java.chart;

import java.util.Map;

import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;


/**
 * This class is derived from the class {@link PieChart}. It represents frame
 * with pie chart information. It is used on the web representing number of votes
 * for each offered band.
 * 
 * @author Vjeran
 */
public class VotingPieChart extends PieChart{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The voting results. */
	private Map<String, Integer> votingResults;

	/**
	 * Instantiates a new pie chart representing distribution of 
	 * votes between the bands.
	 *
	 * @param applicationTitle the application title
	 * @param chartTitle the chart title
	 * @param votingResults the voting results
	 */
	public VotingPieChart(String applicationTitle, String chartTitle, Map<String, Integer> votingResults) {
		super(applicationTitle);
		this.votingResults = votingResults;
		initGUI(chartTitle);
	}

	@Override
	public PieDataset createDataset() {
		DefaultPieDataset result = new DefaultPieDataset();
		for(Map.Entry<String, Integer> entry : votingResults.entrySet()){
			result.setValue(entry.getKey(), entry.getValue());
		}		
		return result;
	}
}
