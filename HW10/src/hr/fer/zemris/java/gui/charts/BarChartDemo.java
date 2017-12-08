package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * The demonstration of using {@link BarChartComponent} in a frame.
 * 
 * @author Vjeco
 */
public class BarChartDemo extends JFrame {
	
	/** The constant serialVersionUID. */
	private static final long serialVersionUID = -2497509898427381803L;
	
	/** The chart with all needed information. */
	@SuppressWarnings("unused")
	private BarChart chart;
	
	/**
	 * The main method, program starts here. Program expects one command
	 * line argument - path to the file with chart's information.
	 *
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		if(args.length != 1){
			throw new IllegalArgumentException("Illegal number of arguments.");
		}
		BarChart chart = getBarChart(args[0]);
		SwingUtilities.invokeLater(() -> {
			new BarChartDemo(chart, args[0]).setVisible(true);
		});
	}
	
	/**
	 * Instantiates a new bar chart demonstration.
	 *
	 * @param chart the chart with all needed information
	 * @param file the file for reading chart's information
	 */
	public BarChartDemo(BarChart chart, String file){
		this.chart = chart;
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Bar Chart");
		setBounds(350, 350, 600, 500);
		initGUI(chart, file);
	}
	
	/**
	 * Method for getting instance of {@link BarChart} by reading from
	 * given file destination.
	 *
	 * @param file the file for reading
	 * @return the bar chart with all needed information
	 */
	private static BarChart getBarChart(String file) {
		BarChart chart = null;
		try (BufferedReader reader = 
				new BufferedReader(
					new InputStreamReader(
						new FileInputStream(file)))){
			String xDesc = reader.readLine();
			String yDesc = reader.readLine();
			List<XYValue> values = processLine(reader.readLine());
			int fromY = Integer.parseInt(reader.readLine());
			int toY = Integer.parseInt(reader.readLine());
			int yGap = Integer.parseInt(reader.readLine());
			chart = new BarChart(values, xDesc, yDesc, fromY, toY, yGap);
			
		} catch (IOException e) {
			System.err.println("Unable to read from given file.");
			System.exit(-1);
		}
		
		return chart; 
	}

	/**
	 * Helper method for processing line with (x,y) values in a file.
	 *
	 * @param line the observed line
	 * @return the list of {@link XYValue}
	 */
	private static List<XYValue> processLine(String line) {
		List<XYValue> list = new ArrayList<>();
		String[] elements = line.split(" ");
		for(String el : elements){
			String[] values = el.split(",");
			list.add(new XYValue(Integer.parseInt(values[0]), Integer.parseInt(values[1])));
		}
		return list;
	}

	/**
	 * /**
	 * Method for initializing GUI (Graphical User Interface) i.e.
	 * setting and processing components inside a frame.
	 *
	 * @param chart the class with all needed information
	 * @param file the specified file for retrieving information
	 */
	private void initGUI(BarChart chart, String file) {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		JLabel fileTitle = new JLabel(file, SwingConstants.CENTER);
		cp.add(fileTitle, BorderLayout.PAGE_START);
		
		BarChartComponent barChart = new BarChartComponent(chart);
		cp.add(barChart, BorderLayout.CENTER);
		
		setMinimumSize(barChart.getMinimumSize());
	}
}
