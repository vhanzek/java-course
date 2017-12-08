package hr.fer.zemris.java.chart;

import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 * This class is derived from the class {@link PieChart}. It represents frame
 * with pie chart information. It is used in a web application showing which
 * operating system is used the most.
 * 
 * @author Vjeran
 */
public class OSPieChart extends PieChart {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new OS pie chart.
	 *
	 * @param applicationTitle the application title
	 * @param chartTitle the chart title
	 */
	public OSPieChart(String applicationTitle, String chartTitle) {
		super(applicationTitle, chartTitle);
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.chart.PieChart#createDataset()
	 */
	@Override
	public PieDataset createDataset() {
		DefaultPieDataset result = new DefaultPieDataset();
		result.setValue("Linux", 29);
		result.setValue("Mac", 20);
		result.setValue("Windows", 51);
		
		return result;
	}

}
