package hr.fer.zemris.java.gui.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.util.List;

import javax.swing.JComponent;

/**
 * The class representing component with bar chart that can be used in a frame.
 * A chart that plots bars indicating data values for a category. This component
 * is build by using information about the graph from {@link BarChart} class.
 * 
 * @author Vjeco
 */
@SuppressWarnings("serial")
public class BarChartComponent extends JComponent {
	
	/** The minimal width of the component. */
	private static final int MIN_WIDTH = 400;
	
	/** The minimal height of the component. */
	private static final int MIN_HEIGHT = 300;
	
	/** The color of bars. */
	private static final Color BAR_COLOR = new Color(244, 119, 72);
	
	/** The instance of {@link BarChart} with all needed information for drawing. */
	private BarChart barChart;
	
	/**
	 * Instantiates a new bar chart component.
	 *
	 * @param barChart instance of {@link BarChart} with all needed information for drawing
	 */
	public BarChartComponent(BarChart barChart){
		this.barChart = barChart;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;
		Dimension size = getSize();
		Insets graphInsets = new Insets(40, 50, 50, 50);
		Dimension axis = new Dimension(size.width - (graphInsets.left + graphInsets.right),
									   size.height - (graphInsets.top + graphInsets.bottom));		
		//origination of the graph
		Point origination = getOriginationPoint(g, size.height, graphInsets);
		
		//spaces between number and lines
		int noXValues = barChart.getValues().size();
		int gapX = axis.width/noXValues;
		int noYValues = (barChart.getToY() - barChart.getFromY())/barChart.getyGap();
		int gapY = axis.height/noYValues;
		
		//axis lines tops
		Point yAxisTop = new Point(origination.x, origination.y - axis.height - 3);
		Point xAxisTop = new Point(origination.x + axis.width + 3, origination.y);
		
		//drawing methods
		drawGraph(g2d, origination, axis, xAxisTop, yAxisTop);		
		drawGraphDescriptions(g2d, axis, size, origination);
		drawNumbers(g2d, origination, noXValues, gapX, gapY);
		drawLines(g2d, origination, axis, noXValues, noYValues, gapX, gapY, xAxisTop, yAxisTop);
		drawBars(g2d, origination, gapX, gapY);
	}

	/**
	 * Helper method for drawing bars in a bar chart. Needed information for
	 * drawing is retrieved from method {@linkplain BarChart#getValues()} which
	 * returns list of {@link XYValue}.
	 *
	 * @param g the graphics used for drawing
	 * @param origination the origination point of the graph
	 * @param gapX the gap x
	 * @param gapY the gap y
	 */
	private void drawBars(Graphics2D g, Point origination, int gapX, int gapY) {
		g.setColor(BAR_COLOR);

		List<XYValue> values = barChart.getValues();
		int x = 0;
		int y = 0;
		int height = 0;
		
		for(XYValue value : values){
			height = (int)(((double)value.getY()/barChart.getyGap())*gapY);
			x = origination.x + (value.getX() - 1)*gapX + 1;
			y = origination.y - height;
			g.fillRect(x, y, gapX - 1, height - 1);
		}
	}

	/**
	 * Helper method for drawing lines in a bar chart. Method draws both horizontal 
	 * and vertical lines.
	 *
	 * @param g the graphics used for drawing
	 * @param origination the origination point of the graph
	 * @param axis the axis line sizes
	 * @param noXValues the number of values plotted to x-axis
	 * @param noYValues the number of values plotted to y-axis
	 * @param gapX the gap in pixels between two x-axis values
	 * @param gapY the gap in pixels between two y-axis values
	 * @param xAxisTop the x axis top
	 * @param yAxisTop the y axis top
	 */
	private void drawLines(Graphics2D g, Point origination, Dimension axis, int noXValues, int noYValues, 
						   int gapX, int gapY, Point xAxisTop, Point yAxisTop) {
		//initialize graphics
		g.setColor(Color.LIGHT_GRAY);
		Graphics2D gTemp = (Graphics2D) g.create();
		gTemp.setStroke(new BasicStroke(2));
		gTemp.setColor(Color.GRAY);
		
		//draw horizontal lines
		int currentY = origination.y - gapY;	
		for(int i = 1; i <= noYValues; i++){
			gTemp.drawLine(origination.x - 4, currentY, origination.x, currentY);
			g.drawLine(origination.x, currentY, xAxisTop.x , currentY);
			currentY -= gapY;
		}
		
		//draw vertical lines
		int currentX = origination.x + gapX;
		for(int i = 1; i <= noXValues; i++){
			gTemp.drawLine(currentX, origination.y, currentX, origination.y + 4);
			g.drawLine(currentX, origination.y, currentX , yAxisTop.y);
			currentX += gapX;
		}
		
		//dispose temporary graphics
		gTemp.dispose();
	}

	/**
	 * Helper method for drawing x-axis and y-axis values. If frame is
	 * resized, gaps between values changes appropriately.
	 *
	 * @param g the graphics used for drawing
	 * @param origination the origination point of the graph
	 * @param noXValues the number of values plotted to x-axis
	 * @param gapX the gap in pixels between two x-axis values
	 * @param gapY the gap in pixels between two y-axis values
	 */
	private void drawNumbers(Graphics2D g, Point origination, int noXValues, int gapX, int gapY) {	
		FontMetrics fm = g.getFontMetrics();
		
		//draw y-axis numbers
		int currentY = origination.y + 4;		
		for(int i = barChart.getFromY(); i <= barChart.getToY(); i += barChart.getyGap()){
			String num = String.valueOf(i);
			g.drawString(num, origination.x - (15 + fm.stringWidth(num)), currentY);
			currentY -= gapY;
		}
		
		//draw x-axis numbers
		int currentX = origination.x + gapX/2;		
		for(int i = 1; i <= noXValues; i++){
			g.drawString(String.valueOf(i), currentX, origination.y + 20);
			currentX += gapX;
		}
	}

	/**
	 * Helper method for drawing structure of the graph i.e. x-axis and y-axis.
	 * It draws two perpendicular lines with higher thickness than regular
	 * lines it a graph.
	 *
	 * @param g the graphics used for drawing
	 * @param origination the origination point of the graph
	 * @param axis the axis lines measures
	 * @param xAxisTop the point of x-axis top
	 * @param yAxisTop the point of y-axis top
	 */
	private void drawGraph(Graphics2D g, Point origination, Dimension axis, Point xAxisTop, Point yAxisTop) {	
		//set graphics properties
		Stroke defaultStroke = g.getStroke();
		g.setStroke(new BasicStroke(2));
		g.setColor(Color.GRAY);
		
		//draw graph lines
		g.drawLine(origination.x, origination.y, xAxisTop.x, xAxisTop.y);
		g.drawLine(origination.x, origination.y, yAxisTop.x, yAxisTop.y);
		
		//draw pointers
		int[] xPointsY = new int[] {yAxisTop.x - 5, yAxisTop.x + 5, yAxisTop.x};
		int[] yPointsY = new int[] {yAxisTop.y, yAxisTop.y, yAxisTop.y - 8};
		g.fillPolygon(xPointsY, yPointsY, 3);
		int[] xPointsX = new int[] {xAxisTop.x, xAxisTop.x, xAxisTop.x + 8};
		int[] yPointsX = new int[] {xAxisTop.y + 5, xAxisTop.y - 5, xAxisTop.y};
		g.fillPolygon(xPointsX, yPointsX, 3);
		
		//reset graphics properties
		g.setColor(getForeground());
		g.setStroke(defaultStroke);
	}

	/**
	 * Helper method for drawing centered x-axis and y-axis descriptions.
	 * If the frame is resized description stays centered.
	 *
	 * @param g the graphics used for drawing
	 * @param axis the axis lines sizes
	 * @param size the size of the component without insets
	 * @param origination the origination point of the graph
	 */
	private void drawGraphDescriptions(Graphics2D g, Dimension axis, Dimension size, Point origination) {
		FontMetrics fm = g.getFontMetrics();
		
		AffineTransform defaultAt = g.getTransform();
		AffineTransform at = new AffineTransform();
		
		at.rotate(-Math.PI / 2);
		g.setTransform(at);
		String yDesc = barChart.getyDesc();
		g.drawString(yDesc, (int) (-origination.y + axis.height/2 - fm.stringWidth(yDesc)/2), 30);
		
		g.setTransform(defaultAt);
		String xDesc = barChart.getxDesc();
		g.drawString(xDesc, (int) (origination.x + axis.width/2 - fm.stringWidth(xDesc)/2), size.height - 25);
	}
	
	/**
	 * Helper method for getting origination point of the bar chart.
	 *
	 * @param g the graphics used for drawing
	 * @param height the height of the component without specified insets
	 * @param ins the insets
	 * @return the origination point of the graph
	 */
	private Point getOriginationPoint(Graphics g, int height, Insets ins) {
		FontMetrics fm = g.getFontMetrics();
		int x0 = ins.left + fm.stringWidth(String.valueOf(Math.max(barChart.getToY(), barChart.getFromY()))) + 5;
		int y0 = height - (ins.bottom + fm.getHeight());
		return new Point(x0, y0);
	}
	
	@Override
	public Dimension getMinimumSize() {
		return new Dimension(MIN_WIDTH, MIN_HEIGHT);
	}
}
