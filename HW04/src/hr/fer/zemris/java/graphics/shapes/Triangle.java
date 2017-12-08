package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;
import static java.lang.Math.*;

/**
 * The Class Triangle represents implementation of the triangle in Cartesian coordinate system.
 * 
 * @author Vjeco
 */
public class Triangle extends GeometricShape {
	
	/** The x coordinate of the first triangle point. */
	private int t0x;
	
	/** The y coordinate of the first triangle point. */
	private int t0y;
	
	/** The x coordinate of the second triangle point. */
	private int t1x;
	
	/** The y coordinate of the second triangle point. */
	private int t1y;
	
	/** The x coordinate of the third triangle point. */
	private int t2x;
	
	/** The y coordinate of the third triangle point. */
	private int t2y;
	
	/**
	 * Instantiates a new triangle.
	 *
	 * @param t0x the x coordinate of the first triangle point
	 * @param t0y the y coordinate of the first triangle point
	 * @param t1x the x coordinate of the second triangle point
	 * @param t1y the y coordinate of the second triangle point
	 * @param t2x the x coordinate of the third triangle point
	 * @param t2y the y coordinate of the third triangle point
	 */
	public Triangle(int t0x, int t0y, int t1x, int t1y, int t2x, int t2y) {
		this.t0x = t0x;
		this.t0y = t0y;
		this.t1x = t1x;
		this.t1y = t1y;
		this.t2x = t2x;
		this.t2y = t2y;
	}
	
	/**
	 * Gets the x coordinate of the first triangle point.
	 *
	 * @return the x coordinate of the first triangle point
	 */
	public int getT0x() {
		return t0x;
	}

	/**
	 * Sets the x coordinate of the first triangle point.
	 *
	 * @param t0x the new x coordinate of the first triangle point.
	 */
	public void setT0x(int t0x) {
		this.t0x = t0x;
	}

	/**
	 * Gets the y coordinate of the first triangle point.
	 *
	 * @return the y coordinate of the first triangle point
	 */
	public int getT0y() {
		return t0y;
	}

	/**
	 * Sets the y coordinate of the first triangle point.
	 *
	 * @param t0y the new y coordinate of the first triangle point
	 */
	public void setT0y(int t0y) {
		this.t0y = t0y;
	}

	/**
	 * Gets the x coordinate of the second triangle point.
	 *
	 * @return the x coordinate of the second triangle point
	 */
	public int getT1x() {
		return t1x;
	}

	/**
	 * Sets the x coordinate of the second triangle point.
	 *
	 * @param t1x the new x coordinate of the second triangle point
	 */
	public void setT1x(int t1x) {
		this.t1x = t1x;
	}

	/**
	 * Gets the y coordinate of the second triangle point.
	 *
	 * @return the y coordinate of the second triangle point
	 */
	public int getT1y() {
		return t1y;
	}

	/**
	 * Sets the y coordinate of the second triangle point.
	 *
	 * @param t1y the new y coordinate of the second triangle point
	 */
	public void setT1y(int t1y) {
		this.t1y = t1y;
	}

	/**
	 * Gets the x coordinate of the third triangle point.
	 *
	 * @return the x coordinate of the third triangle point
	 */
	public int getT2x() {
		return t2x;
	}

	/**
	 * Sets the x coordinate of the third triangle point.
	 *
	 * @param t2x the new x coordinate of the third triangle point
	 */
	public void setT2x(int t2x) {
		this.t2x = t2x;
	}

	/**
	 * Gets the y coordinate of the third triangle point.
	 *
	 * @return the y coordinate of the third triangle point
	 */
	public int getT2y() {
		return t2y;
	}

	/**
	 * Sets the y coordinate of the third triangle point.
	 *
	 * @param t2y the new y coordinate of the third triangle point
	 */
	public void setT2y(int t2y) {
		this.t2y = t2y;
	}

	
	/**
	 * {@inheritDoc}
	 * <p>More efficient method for drawing triangle on the raster than the method in {@link GeometricShape} class.
	 * Does not check every pixel of the raster, but checks every pixel in domain x € [min(tx0, tx1, tx2), max(tx0, tx1, tx2)] 
	 * and y € [min(ty0, ty1, ty2), max(ty0, ty1, ty2)].</p>
	 */
	@Override
	public void draw(BWRaster r){
		int fromX = max(0, min(t0x, min(t1x, t2x)));
		int fromY = max(0, min(t0y, min(t1y, t2y)));
		int toX = min(r.getWidth(), max(t0x, max(t1x, t2x)));
		int toY = min(r.getHeight(), max(t0y, max(t1y, t2y)));
		
		for(int x = fromX; x <= toX; x++){
			for(int y = fromY; y <= toY; y++){
				if(containsPoint(x, y)){
					r.turnOn(x, y);
				}
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 * <p>Method checks if sum of the area between current tested point and every two vertices of the triangle is lesser that are of the triangle.
	 * In other words, if previously mentioned claim is true, current tested point is in the triangle.</p>
	 */
	@Override
	public boolean containsPoint(int x, int y) {
		double triangleArea = area(t0x, t0y, t1x, t1y, t2x, t2y);
		double innerArea = area(x, y, t1x, t1y, t2x, t2y) + area(t0x, t0y, x, y, t2x, t2y) + 
							area(t0x, t0y, t1x, t1y, x, y);
		
		return (innerArea <= triangleArea);	
	}
	
	/**
	 * Method for calculating are between three points in Cartesian coordinate system.
	 * 
	 * @param t0x the x coordinate of the first point
	 * @param t0y the y coordinate of the first point
	 * @param t1x the x coordinate of the second point
	 * @param t1y the y coordinate of the second point
	 * @param t2x the x coordinate of the third point
	 * @param t2y the y coordinate of the third point
	 * @return result area
	 */
	private double area(int t0x, int t0y, int t1x, int t1y, int t2x, int t2y){
		double area = abs(0.5 * (t0x * (t1y - t2y) + t1x * (t2y - t0y) + t2x * (t0y - t1y)));
		
		return area;
	}

}
