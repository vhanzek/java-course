package hr.fer.zemris.java.gui.charts;

/**
 * The class with two read-only properties - x value and y value. Can be interpretated
 * as a point but instead defines bar height and position in {@link BarChart}.
 * 
 * @author Vjeco
 */
public class XYValue {
	
	/** The x value. */
	private int x;
	
	/** The y value. */
	private int y;
	
	/**
	 * Instantiates a new XY value.
	 *
	 * @param x the x value
	 * @param y the y value
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Gets the x value.
	 *
	 * @return the x value
	 */
	public int getX() {
		return x;
	}

	/**
	 * Gets the y value. 
	 *
	 * @return the y value
	 */
	public int getY() {
		return y;
	}
}
