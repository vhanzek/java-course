package hr.fer.zemris.java.hw16.jvdraw.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;

/**
 * The class {@code Circle} represents filled circle as {@link GeometricalObject}
 * that can be drawn in {@link JVDraw}'s drawing area. The color of the circle is
 * defined by {@link AbstractGeometricalObject#foregroundColor} and
 * {@link AbstractGeometricalObject#backgroundColor}, i.e. first represents outline
 * color of the circle and the second one is the color of the circle's interior.
 * 
 * @author Vjeran
 */
public class FilledCircle extends AbstractCircle{
	
	/** The filled circle count. */
	public static int filledCircleCount;

	/**
	 * Instantiates a new filled circle.
	 *
	 * @param startingPoint the starting point
	 * @param endingPoint the ending point
	 * @param foregroundColor the foreground color
	 * @param backgroundColor the background color
	 */
	public FilledCircle(Point startingPoint, Point endingPoint, Color foregroundColor, Color backgroundColor) {
		super(startingPoint, endingPoint, foregroundColor, backgroundColor);
		objectNo = filledCircleCount;
	}
	
	/**
	 * Instantiates a new filled circle.
	 *
	 * @param centerPoint the center point
	 * @param radius the radius
	 * @param foregroundColor the foreground color
	 * @param backgroundColor the background color
	 */
	public FilledCircle(Point centerPoint, int radius, Color foregroundColor, Color backgroundColor){
		super(centerPoint, radius, foregroundColor, backgroundColor);
		objectNo = filledCircleCount;
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setColor(backgroundColor);
		g2d.fillOval(
			(int) centerPoint.getX() - radius, 
			(int) centerPoint.getY() - radius, 
			radius * 2, radius * 2
		);		
		g2d.setColor(foregroundColor);
		g2d.drawOval(
			(int) centerPoint.getX() - radius, 
			(int) centerPoint.getY() - radius, 
			radius * 2, radius * 2
		);
	}
	
	@Override
	public String toString() {
		return String.format("filled circle %d", objectNo);
	}

	@Override
	public String getName() {
		return "FCIRCLE";
	}
}
