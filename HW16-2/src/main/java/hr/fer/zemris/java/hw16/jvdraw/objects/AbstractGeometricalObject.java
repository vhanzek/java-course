package hr.fer.zemris.java.hw16.jvdraw.objects;

import java.awt.Color;
import java.awt.Point;

/**
 * <p>The abstract class implementing {@link GeometricalObject}. It offers five
 * object properties that classes extending this class inherit. 
 * 
 * <ul>Those are:
 * 		<li>{@link #getStartingPoint()} - point of the first user's click</li>
 * 		<li>{@link #endingPoint} - point of the second and final user click.</li>
 * 		<li>{@link #foregroundColor} - color of the object's foreground</li>
 * 		<li>{@link #backgroundColor} - color of the object's background</li>
 * 		<li>{@link #objectNo} - every object has its number</li>
 * </ul>
 * 
 *  @author Vjeran
 */
public abstract class AbstractGeometricalObject implements GeometricalObject{
	
	/** The starting point. */
	protected Point startingPoint;
	
	/** The ending point. */
	protected Point endingPoint;
	
	/** The foreground color. */
	protected Color foregroundColor;
	
	/** The background color. */
	protected Color backgroundColor;
	
	/** The object number. */
	protected int objectNo;
	
	/**
	 * Protected constructor used in classes that extend this class.
	 *
	 * @param startingPoint the starting point
	 * @param endingPoint the ending point
	 * @param foregroundColor the foreground color
	 * @param backgroundColor the background color
	 */
	protected AbstractGeometricalObject(Point startingPoint, Point endingPoint, 
									 Color foregroundColor, Color backgroundColor){
		this.startingPoint = startingPoint;
		this.endingPoint = endingPoint;
		this.foregroundColor = foregroundColor;
		this.backgroundColor = backgroundColor;
	}
	
	/**
	 * Empty protected constructor.
	 */
	protected AbstractGeometricalObject(){
	}
	
	/**
	 * Increments object count depending on the object type.
	 */
	public void incrementObjectCount(){
		if(this instanceof Line){
			Line.lineCount++;
		} else if (this instanceof Circle){
			Circle.circleCount++;
		} else {
			FilledCircle.filledCircleCount++;
		}
	}
	
	/**
	 * Static method for reseting objects count.
	 */
	public static void resetObjectsCount(){
		Line.lineCount = 0;
		Circle.circleCount = 0;
		FilledCircle.filledCircleCount = 0;
	}
	
	/**
	 * Gets the objects name used in .JVD files.
	 *
	 * @return the name
	 */
	public abstract String getName();

	/**
	 * Gets the starting point.
	 *
	 * @return the starting point
	 */
	public Point getStartingPoint() {
		return startingPoint;
	}

	/**
	 * Sets the starting point.
	 *
	 * @param startingPoint the new starting point
	 */
	public void setStartingPoint(Point startingPoint) {
		this.startingPoint = startingPoint;
	}

	/**
	 * Gets the ending point.
	 *
	 * @return the ending point
	 */
	public Point getEndingPoint() {
		return endingPoint;
	}

	/**
	 * Sets the ending point.
	 *
	 * @param endingPoint the new ending point
	 */
	public void setEndingPoint(Point endingPoint) {
		this.endingPoint = endingPoint;
	}

	/**
	 * Gets the foreground color.
	 *
	 * @return the foreground color
	 */
	public Color getForegroundColor() {
		return foregroundColor;
	}

	/**
	 * Sets the foreground color.
	 *
	 * @param foregroundColor the new foreground color
	 */
	public void setForegroundColor(Color foregroundColor) {
		this.foregroundColor = foregroundColor;
	}

	/**
	 * Gets the background color.
	 *
	 * @return the background color
	 */
	public Color getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * Sets the background color.
	 *
	 * @param backgroundColor the new background color
	 */
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
}
