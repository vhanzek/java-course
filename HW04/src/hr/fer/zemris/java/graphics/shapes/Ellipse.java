package hr.fer.zemris.java.graphics.shapes;

/**
 * The Class Ellipse represents implementation of the ellipse in Cartesian coordinate system.
 * 
 * @author Vjeco
 */
public class Ellipse extends AbstractEllipse {
	
	/**
	 * Instantiates a new ellipse.
	 * 
	 * @throws IllegalArgumentException if horizontal radius or vertical radius are 0 or less.
	 * @param centerX the x coordinate of the center of the ellipse
	 * @param centerY the y coordinate of the center of the ellipse
	 * @param horizontalRadius the horizontal radius of the ellipse
	 * @param verticalRadius the vertical radius of the ellipse
	 */
	public Ellipse(int centarX, int centarY, int horizontalRadius, int verticalRadius){
		super(centarX, centarY, horizontalRadius, verticalRadius);
	}
}
