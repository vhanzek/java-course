package hr.fer.zemris.java.hw16.jvdraw.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;

/**
 * The class {@code Line} represents line as geometric object that can
 * be drawn in {@link JVDraw}'s drawing area. The color of the line is
 * defined by {@link AbstractGeometricalObject#foregroundColor}.
 * 
 * @author Vjeran
 */
public class Line extends AbstractGeometricalObject{
	
	/** The line count. */
	public static int lineCount;

	/**
	 * Instantiates a new line.
	 *
	 * @param startingPoint the starting point
	 * @param endingPoint the ending point
	 * @param foregroundColor the foreground color
	 */
	public Line(Point startingPoint, Point endingPoint, Color foregroundColor) {
		super(startingPoint, endingPoint, foregroundColor, null);
		objectNo = lineCount;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(foregroundColor);
		g.drawLine(
			(int) startingPoint.getX(), (int) startingPoint.getY(), 
			(int) endingPoint.getX(), (int) endingPoint.getY()
		);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("line %d", objectNo);
	}
	
	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.hw16.jvdraw.objects.AbstractGeometricalObject#getName()
	 */
	@Override
	public String getName(){
		return "LINE";
	}
}
