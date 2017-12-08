package hr.fer.zemris.java.graphics.raster;

/**
 * The Interface BWRaster is an abstraction for all raster devices of fixed width and height for which each pixel can be painted with only 
 * two colors: black (when pixel is turned off) and white (when pixel is turned on). Cartesian coordinate system begins at the upper left corner.
 * 
 * @author Vjeco
 */
public interface BWRaster {
	
	/**
	 * Gets the width of the raster.
	 *
	 * @return the width of the raster.
	 */
	int getWidth();
	
	/**
	 * Gets the height of the raster.
	 *
	 * @return the height of the raster.
	 */
	int getHeight();
	
	/**
	 * Clears the raster. Turns off every pixel of the raster.
	 */
	void clear();
	
	/**
	 * Turn on the pixel at (x,y) coordinates.
	 *
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	void turnOn(int x, int y);
	
	/**
	 * Turn off the pixel at (x,y) coordinates.
	 *
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	void turnOff(int x, int y);
	
	/**
	 * Enables flip mode. 
	 * If flipping mode is enabled, then the call of the turnOn method flips the pixel at the specified location.
	 */
	void enableFlipMode();
	
	/**
	 * Disables flip mode.
	 * If flipping mode of raster is disabled, then the call of the turnOn method turns on the pixel at specified location.
	 */
	void disableFlipMode();
	
	/**
	 * Checks if pixel at (x,y) coordinates is turned on
	 *
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @return true, if is turned on
	 */
	boolean isTurnedOn(int x, int y);
	
	/**
	 * Checks if flip mode is enabled.
	 *
	 * @return true, if is flip mode enabled
	 */
	boolean isFlipModeEnabled();
}
