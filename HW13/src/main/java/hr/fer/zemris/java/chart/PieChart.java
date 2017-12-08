package hr.fer.zemris.java.chart;

import java.awt.Dimension;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

/**
 * The class representing pie chart abstraction. It contains only one abstract method
 * {@link #createDataset()} which needs to be implemented by classes which extend this 
 * class. It also offers possibility to change the design chart by overriding method 
 * {@link #createChart(PieDataset, String)}.
 * 
 * @author Vjeran
 */
public abstract class PieChart extends JFrame {

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
	public PieChart(String applicationTitle, String chartTitle) {
		super(applicationTitle);
		initGUI(chartTitle);
	}
	
	/**
	 * Protected constructor which is used for creating only frame without 
	 * any information, i.e. without method {@link #initGUI(String)} whereby
	 * allows classes derived from this one to initialize their class variables.
	 *
	 * @param applicationTitle the application title
	 */
	protected PieChart(String applicationTitle) {
		super(applicationTitle);
	}

	/**
	 * Method for initializing GUI in this frame. It creates new {@link JFreeChart} and
	 * new {@link PieDataset}. Those two classes are used to create and show to user
	 * pie chart whit given information from {@link PieDataset} class.
	 *
	 * @param chartTitle the chart title
	 */
	protected void initGUI(String chartTitle) {
		PieDataset dataset = createDataset();
		chart = createChart(dataset, chartTitle);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(500, 270));
		setSize(chartPanel.getPreferredSize());

		setContentPane(chartPanel);
	}

	/**
	 * Method for creating new chart, i.e. for designing chart.
	 * This method can be overridden, depending on the purpose
	 * of the chart.
	 *
	 * @param dataset the set with chart information
	 * @param title the title of the chart
	 * @return the chart
	 */
	protected JFreeChart createChart(PieDataset dataset, String title) {
		JFreeChart chart = ChartFactory.createPieChart3D(title, dataset, true, true, false);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		
		return chart;
	}
	
	/**
	 * Gets the chart.
	 *
	 * @return the chart
	 */
	public JFreeChart getChart() {
		return chart;
	}
	
	/**
	 * Abstract method which is implemented by classes derived from this.
	 * It creates a new {@link PieDataset} with all needed information 
	 * about the chart.
	 *
	 * @return the class with chart information
	 */
	protected abstract PieDataset createDataset();
}
