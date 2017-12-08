package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * <p>The class representing abstraction of a bar chart.</p>
 * 
 * <p>It contains all needed informations for drawing {@link BarChartComponent}.
 * <ul>
 * 		<li>{@linkplain #values} with information how tall bars are in a chart and how many bars there are.</li>
 * 		<li>{@linkplain #xDesc} is a description under x-axis.</li>
 * 		<li>{@linkplain #yDesc} is a description under y-axis.</li>
 * 		<li>{@linkplain #fromY} is the lowest number in y-axis.</li>
 * 		<li>{@linkplain #fromY} is the highest number in y-axis.</li>
 * 		<li>{@linkplain #yGap} is a difference between two points on y-axis.</li>
 * </ul>
 * 		
 */
public class BarChart {
	
	/** The values with bar information. */
	private List<XYValue> values;
	
	/** The description under x-axis. */
	private String xDesc;
	
	/** The description under y-axis. */
	private String yDesc;
	
	/** The lowest margin on y-axis. */
	private int fromY;
	
	/** The highest margin on y-axis. */
	private int toY;
	
	/** The difference between two points on y-axis. */
	private final int yGap;
	
	/**
	 * Instantiates a new bar chart.
	 *
	 * @param values values with bar information
	 * @param xDesc description of x-axis
	 * @param yDesc description of y-axis
	 * @param fromY lowest margin on y-axis
	 * @param toY highest margin on y-axis
	 * @param yGap difference between two points on y-axis
	 */
	public BarChart(List<XYValue> values, String xDesc, String yDesc, int fromY, int toY, int yGap) {
		this.values = values;
		this.xDesc = xDesc;
		this.yDesc = yDesc;
		this.fromY = fromY;
		this.toY = toY;
		this.yGap = yGap;
	}

	/**
	 * Gets the {@link XYValue} list.
	 *
	 * @return the xy values
	 */
	public List<XYValue> getValues() {
		return values;
	}

	/**
	 * Gets the description of x-axis.
	 *
	 * @return the description of x-axis
	 */
	public String getxDesc() {
		return xDesc;
	}

	/**
	 * Gets the description of y-axis.
	 *
	 * @return the description of y-axis
	 */
	public String getyDesc() {
		return yDesc;
	}

	/**
	 * Gets the lowest margin on y-axis.
	 *
	 * @return the lowest margin on y-axis
	 */
	public int getFromY() {
		return fromY;
	}

	/**
	 * Gets the highest margin on y-axis
	 *
	 * @return the highest margin on y-axis
	 */
	public int getToY() {
		return toY;
	}

	/**
	 * Gets the difference between two points on y-axis.
	 *
	 * @return the difference between two points on y-axis
	 */
	public int getyGap() {
		return yGap;
	}
}
