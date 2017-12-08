package hr.fer.zemris.java.graphics.shapes;

/**
 * The Class Rectangle represents implementation of the rectangle in Cartesian coordinate system.
 * 
 * @author Vjeco
 */
public class Rectangle extends AbstractRectangle {
	
	/**
	 * Instantiates a new rectangle.
	 *
	 * @throws IllegalArgumentException if width of height are 0 or less.
	 * @param upLeftX the x coordinate of the left upper top
	 * @param upLeftY the y coordinate of the left upper top
	 * @param width the width of the rectangle
	 * @param height the height of the rectangle
	 */
	public Rectangle (int upLeftX, int upLeftY, int width, int height){
		super(upLeftX, upLeftY, width, height);
	}

}
