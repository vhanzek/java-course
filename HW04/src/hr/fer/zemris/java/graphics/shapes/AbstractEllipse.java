package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;
import static java.lang.Math.*;

/**
 * The Class AbstractEllipse representing every shape similar to ellipse (ellipse, circle).
 * 
 * @author Vjeco
 */
public abstract class AbstractEllipse extends GeometricShape {
	
	/** The horizontal radius of the ellipse. */
	private int horizontalRadius;
	
	/** The vertical radius of the ellipse. */
	private int verticalRadius;
	
	/** The x coordinate of the center of the ellipse. */
	private int centerX;
	
	/** The y coordinate of the center of the ellipse. */
	private int centerY;
	
	/** The x coordinate of the first ellipse focus point. */
	private double xf1;
	
	/** The y coordinate of the first ellipse focus point. */
	private double yf1; 
	
	/** The x coordinate of the second ellipse focus point. */
	private double xf2;
	
	/** The y coordinate of the second ellipse focus point. */
	private double yf2;
	   
	/** The major axis of the ellipse. */
	private double a;

	/**
	 * Instantiates a new abstract ellipse.
	 * 
	 * @throws IllegalArgumentException if horizontal radius or vertical radius are 0 or less.
	 * @param centerX the x coordinate of the center of the ellipse
	 * @param centerY the y coordinate of the center of the ellipse
	 * @param horizontalRadius the horizontal radius
	 * @param verticalRadius the vertical radius
	 */
	public AbstractEllipse(int centerX, int centerY, int horizontalRadius, int verticalRadius) {
		if(horizontalRadius < 1){
			throw new IllegalArgumentException(
				"Horizontal radius is invalid."
			);
		}
		if(verticalRadius < 1){
			throw new IllegalArgumentException(
				"Vertical radius is invalid."
			);
		}
		
		this.horizontalRadius = horizontalRadius;
		this.verticalRadius = verticalRadius;
		this.centerX = centerX;
		this.centerY = centerY;
		
		update();
	}

	/**
	 * Method for setting coordinates of the ellipse's focus points and value of the ellipse's major axis.
	 */
	private void update() {
		a = max(horizontalRadius - 1, verticalRadius - 1);
		
		if (horizontalRadius > verticalRadius) {
			double e = sqrt(pow(horizontalRadius - 1,2) - pow(verticalRadius - 1,2));
	        yf1 = yf2 = centerY;
	        xf1 = centerX - e;
	        xf2 = centerX + e;
		} else {
	        double e = sqrt(pow(verticalRadius - 1,2) - pow(horizontalRadius - 1,2));
	        xf1 = xf2 = centerX;
	        yf1 = centerY - e;
	        yf2 = centerY + e;
	    }
	}

	/**
	 * Gets the horizontal radius of the ellipse.
	 *
	 * @return the horizontal radius of the ellipse
	 */
	public int getHorizontalRadius() {
		return horizontalRadius;
	}

	/**
	 * Sets the horizontal radius of the ellipse.
	 *
	 * @param horizontalRadius the new horizontal radius of the ellipse
	 */
	public void setHorizontalRadius(int horizontalRadius) {
		this.horizontalRadius = horizontalRadius;
	}

	/**
	 * Gets the vertical radius of the ellipse.
	 *
	 * @return the vertical radius of the ellipse
	 */
	public int getVerticalRadius() {
		return verticalRadius;
	}

	/**
	 * Sets the vertical radius of the ellipse.
	 *
	 * @param verticalRadius the new vertical radius of the ellipse
	 */
	public void setVerticalRadius(int verticalRadius) {
		this.verticalRadius = verticalRadius;
	}

	/**
	 * Gets the x coordinate of the center of the ellipse.
	 *
	 * @return the x coordinate of the center of the ellipse
	 */
	public int getcenterX() {
		return centerX;
	}

	/**
	 * Sets the x coordinate of the center of the ellipse.
	 *
	 * @param centerX the new x coordinate of the center of the ellipse
	 */
	public void setcenterX(int centerX) {
		this.centerX = centerX;
	}

	/**
	 * Gets the y coordinate of the center of the ellipse.
	 *
	 * @return the y coordinate of the center of the ellipse
	 */
	public int getcenterY() {
		return centerY;
	}

	/**
	 * Sets the y coordinate of the center of the ellipse.
	 *
	 * @param centerY the new y coordinate of the center of the ellipse
	 */
	public void setcenterY(int centerY) {
		this.centerY = centerY;
	}
	
	/**
	 * {@inheritDoc}
	 * <p>More efficient method for drawing ellipse on the raster than the method in {@link GeometricShape} class.
	 * Does not check every pixel of the raster, but checks every pixel in domain x € [-horizontalRadius, horizontalRadius] 
	 * and y € [-verticalRadius, verticalRadius].</p>
	 */
	@Override
	public void draw(BWRaster r) {
		if (centerX - horizontalRadius >= r.getWidth() || centerX + horizontalRadius <= 0 ||
			centerY - verticalRadius >= r.getHeight() || centerY + verticalRadius <= 0)
			return;

		int fromX = Math.max(0, centerX - horizontalRadius + 1);
		int toX = Math.min(r.getWidth(), centerX + horizontalRadius - 1);
		int fromY = Math.max(0, centerY - verticalRadius + 1);
		int toY = Math.min(r.getHeight(), centerY + verticalRadius - 1);
		
		for(int x = fromX; x <= toX; x++){
			for(int y = fromY; y <= toY; y++){
				if(containsPoint(x, y)){
					r.turnOn(x, y);
				}
			}
		}
	}
	
	/*@see hr.fer.zemris.java.graphics.shapes.GeometricShape#containsPoint(int, int)*/
	@Override
	public boolean containsPoint(int x, int y) {
		return(distance(x, y, xf1, yf1) + distance(x, y, xf2, yf2) <= 2*a);      
	}	
	
	/**
	 * Method for calculating distance between two points in Cartesian coordinate system.
	 *
	 * @param x the x coordinate of the first point
	 * @param y the y coordinate of the first point
	 * @param xf1 the x coordinate of the second point
	 * @param yf1 the y coordinate of the second point
	 * @return the result distance
	 */
	private double distance(double x, double y, double xf1, double yf1) {
        return sqrt(pow((xf1 - x),2) + pow((yf1 - y),2));
    }
}
