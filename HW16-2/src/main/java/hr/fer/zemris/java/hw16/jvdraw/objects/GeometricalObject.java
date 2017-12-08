package hr.fer.zemris.java.hw16.jvdraw.objects;

import java.awt.Graphics;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;

/**
 * <p>The interface {@code GeometricalObject} representing geometric object
 * that can be drawn in drawing area of {@link JVDraw} program. 
 * 
 * <p>
 * <ul>There are three defined implementations of this interface:
 * 		<li>{@link Line}</li>
 * 		<li>{@link Circle}</li>
 * 		<li>{@link FilledCircle}</li>
 * </ul>
 * 
 * @author Vjeran
 */
public interface GeometricalObject {
	
	/**
	 * Method that draws this geometric object using given
	 * instance of class {@link Graphics}.
	 *
	 * @param g the graphics
	 */
	public void draw(Graphics g);
}
