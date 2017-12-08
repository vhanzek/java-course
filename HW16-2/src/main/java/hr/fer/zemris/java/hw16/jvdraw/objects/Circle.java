package hr.fer.zemris.java.hw16.jvdraw.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;

/**
 * The class {@code Circle} represents circle as geometric object that can
 * be drawn in {@link JVDraw}'s drawing area. The color of the circle is
 * defined by {@link AbstractGeometricalObject#foregroundColor}.
 * 
 * @author Vjeran
 */
public class Circle extends AbstractCircle{
	
	/** The circle count. */
	public static int circleCount;

	/**
	 * Instantiates a new circle.
	 *
	 * @param startingPoint the starting point
	 * @param endingPoint the ending point
	 * @param foregroundColor the foreground color
	 */
	public Circle(Point startingPoint, Point endingPoint, Color foregroundColor) {
		super(startingPoint, endingPoint, foregroundColor, null);
		objectNo = circleCount;
	}
	
	/**
	 * Instantiates a new circle.
	 *
	 * @param centerPoint the center point
	 * @param radius the radius
	 * @param foregroundColor the foreground color
	 */
	public Circle(Point centerPoint, int radius, Color foregroundColor){
		super(centerPoint, radius, foregroundColor, null);
		objectNo = circleCount;
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(foregroundColor);
		g.drawOval(
			(int) centerPoint.getX() - radius, 
			(int) centerPoint.getY() - radius,
			radius * 2, radius * 2
		);
	}
	
	@Override
	public String toString() {
		return String.format("circle %d", objectNo);
	}

	@Override
	public String getName() {
		return "CIRCLE";
	}
}
