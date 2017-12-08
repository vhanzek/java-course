package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * The Class GeometricShape representing geometric shapes as general.
 * 
 * @author Vjeco
 */
public abstract class GeometricShape {
	
	/**
	 * Method for drawing certain geometric shape to given raster.
	 *
	 * @param r the raster for drawing
	 */
	public void draw(BWRaster r){
		for(int x = 0; x < r.getWidth(); x++){
			for(int y = 0; y < r.getHeight(); y++){
				if(containsPoint(x, y)){
					r.turnOn(x, y);
				}
			}
		}
	}
	
	/**
	 * Checks if certain geometric shape contains specified coordinates.
	 *
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @return true, if it contains, false otherwise
	 */
	public abstract boolean containsPoint(int x, int y);
}
