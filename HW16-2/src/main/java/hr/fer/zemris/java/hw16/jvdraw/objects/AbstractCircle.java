package hr.fer.zemris.java.hw16.jvdraw.objects;

import java.awt.Color;
import java.awt.Point;

/**
 * <p>The abstract class implementing {@link AbstractGeometricalObject}. It 
 * offers two new object (specifically {@link FilledCircle} and {@link Circle}) 
 * properties that classes extending this class inherit. Those two properties are 
 * {@link #radius} - radius of the circle and {@link #centerPoint} - center point 
 * of the circle.
 * 
 * @author Vjeran
 */
public abstract class AbstractCircle extends AbstractGeometricalObject{
	
	/** The radius. */
	protected int radius;
	
	/** The center point. */
	protected Point centerPoint;
	
	/**
	 * Protected constructor used in classes that extend this class.
	 *
	 * @param startingPoint the starting point
	 * @param endingPoint the ending point
	 * @param foregroundColor the foreground color
	 * @param backgroundColor the background color
	 */
	protected AbstractCircle(Point startingPoint, Point endingPoint, Color foregroundColor, Color backgroundColor) {
		super(startingPoint, endingPoint, foregroundColor, backgroundColor);
		this.radius = (int) startingPoint.distance(endingPoint);
		this.centerPoint = startingPoint;
	}

	/**
	 * Protected constructor used in classes that extend this class.
	 *
	 * @param centerPoint the center point
	 * @param radius the radius
	 * @param foregroundColor the foreground color
	 * @param backgroundColor the background color
	 */
	protected AbstractCircle(Point centerPoint, int radius, Color foregroundColor, Color backgroundColor){
		super();
		this.centerPoint = centerPoint;
		this.radius = radius;
		super.foregroundColor = foregroundColor;
		super.backgroundColor = backgroundColor;
	}

	/**
	 * Gets the radius.
	 *
	 * @return the radius
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * Sets the radius.
	 *
	 * @param radius the new radius
	 */
	public void setRadius(int radius) {
		this.radius = radius;
	}

	/**
	 * Gets the center point.
	 *
	 * @return the center point
	 */
	public Point getCenterPoint() {
		return centerPoint;
	}

	/**
	 * Sets the center point.
	 *
	 * @param centerPoint the new center point
	 */
	public void setCenterPoint(Point centerPoint) {
		this.centerPoint = centerPoint;
	}
	
	/**
	 * Gets the diameter.
	 *
	 * @return the diameter
	 */
	public int getDiameter(){
		return radius * 2;
	}
}
