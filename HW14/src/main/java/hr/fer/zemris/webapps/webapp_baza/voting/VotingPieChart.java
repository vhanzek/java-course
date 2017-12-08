package hr.fer.zemris.webapps.webapp_baza.voting;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

import hr.fer.zemris.webapps.webapp_baza.poll.PollOption;

/**
 * The class representing a pie chart with information about specified poll.
 * It shows the vote distribution between the poll options.
 * 
 * @author Vjeran
 */
public class VotingPieChart extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The chart. */
	private JFreeChart chart;

	/**
	 * Instantiates a new pie chart i.e. new frame with all necessary 
	 * information.
	 *
	 * @param applicationTitle the application title
	 * @param chartTitle the chart title
	 */
	public VotingPieChart(String applicationTitle, String chartTitle, List<PollOption> options) {
		super(applicationTitle);
		initGUI(chartTitle, options);
	}

	/**
	 * Method for initializing GUI in this frame. It creates new {@link JFreeChart} and
	 * new {@link PieDataset}. Those two classes are used to create and show to user
	 * pie chart whit given information from {@link PieDataset} class.
	 *
	 * @param chartTitle the chart title
	 */
	protected void initGUI(String chartTitle, List<PollOption> options) {
		PieDataset dataset = createDataset(options);
		chart = createChart(dataset, chartTitle);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(500, 270));
		setSize(chartPanel.getPreferredSize());

		setContentPane(chartPanel);
	}

	/**
	 * Method for creating new chart, i.e. for designing chart.
	 *
	 * @param dataset the set with chart information
	 * @param title the title of the chart
	 * @return the chart
	 */
	private JFreeChart createChart(PieDataset dataset, String title) {
		JFreeChart chart = ChartFactory.createPieChart3D(title, dataset, true, true, false);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		
		return chart;
	}
	
	/**
	 * Method for creating new {@link PieDataset} with all needed information 
	 * about the chart.
	 * 
	 * @param options list of the {@link PollOption} objects
	 * @return the class with chart information
	 */
	private PieDataset createDataset(List<PollOption> options) {
		DefaultPieDataset result = new DefaultPieDataset();
		for(PollOption opt : options){
			result.setValue(opt.getOptionTitle(), Double.valueOf(opt.getVotesCount()));
		}	
		return result;
	}
	
	/**
	 * Gets the chart.
	 *
	 * @return the chart
	 */
	public JFreeChart getChart() {
		return chart;
	}
}
