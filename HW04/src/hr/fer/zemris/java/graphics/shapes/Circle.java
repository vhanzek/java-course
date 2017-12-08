package hr.fer.zemris.java.graphics.shapes;

/**
 * The Class Circle represents implementation of the circle in Cartesian coordinate system.
 * 
 * @author Vjeco
 */
public class Circle extends AbstractEllipse {

	/**
	 * Instantiates a new circle.
	 *
	 * @param centarX the x coordinate of the center of the circle
	 * @param centarY the y coordinate of the center of the circle
	 * @param radius the radius of the circle
	 */
	public Circle(int centarX, int centarY, int radius) {
		super(centarX, centarY, radius, radius);
	}

	/**
	 * Sets the circle's radius by setting both horizontal and vertical radiuses.
	 *
	 * @param radius the new radius
	 */
	public void setRadius(int radius) {
    	super.setHorizontalRadius(radius);
        super.setVerticalRadius(radius);
    }
	
	/**
	 * Method sets both horizontal and vertical radiuses.
	 * 
	 * @param radius the new radius
	 */
	@Override
    public void setHorizontalRadius(int radius) {
        setRadius(radius);
    }
   
	/**
	 * Method sets both horizontal and vertical radiuses.
	 *
	 * @param radius the new radius
	 */
    @Override
    public void setVerticalRadius(int radius) {
       setRadius(radius);
    }
   
    

	
}
