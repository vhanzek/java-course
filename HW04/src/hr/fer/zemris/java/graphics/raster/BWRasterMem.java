package hr.fer.zemris.java.graphics.raster;

/**
 * The Class BWRasterMem represents implementation of interface {@link BWRaster}.
 * 
 * @author Vjeco
 */
public class BWRasterMem implements BWRaster {
	
	/** The width of the raster. */
	private int width;
	
	/** The height of the raster. */
	private int height;
	
	/** The array pixels in a raster. */
	private boolean pixels[][];
	
	/** The flip mode. */
	private boolean flipMode;
	
	/**
	 * Instantiates a new {@link BWRaster} implementation.
	 * 
	 * @throws IllegalArgumentException if width or height is 0 or less.
	 *
	 * @param width the width of the raster
	 * @param height the height of the raster
	 */
	public BWRasterMem(int width, int height) {
		if(width < 1 || height < 1){
			throw new IllegalArgumentException(
				"Width or height are invalid."
			);
		}
		this.width = width;
		this.height = height;
		this.pixels = new boolean[width][height];
		this.flipMode = false;
	}

	/* @see hr.fer.zemris.java.graphics.raster.BWRaster#getWidth()*/
	@Override
	public int getWidth() {
		return width;
	}

	/* @see hr.fer.zemris.java.graphics.raster.BWRaster#getHeight()*/
	@Override
	public int getHeight() {
		return height;
	}

	/* @see hr.fer.zemris.java.graphics.raster.BWRaster#clear()*/
	@Override
	public void clear() {
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				turnOff(x, y);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * <p> If flip mode is enabled , then the call of this method flips the pixel at the specified location.
	 * Otherwise, call of this method turns on the pixel at specified location.</p>
	 * 
	 * @throws IllegalArgumentException if x coordinate or y coordinate are invalid.
	 */
	@Override
	public void turnOn(int x, int y) {
		if(x < 0 || x > width){
			throw new IllegalArgumentException(
				"X coordinate is invalid."
			);
		}
		if(y < 0 || y > height){
			throw new IllegalArgumentException(
				"Y coordinate is invalid."
			);
		}
		
		if(flipMode){
			pixels[x][y] = pixels[x][y] ? false : true;
		} else {
			pixels[x][y] = true;
		}

	}

	/**
	 * {@inheritDoc}
	 *  
	 * @throws IllegalArgumentException if x coordinate or y coordinate are invalid.
	 */
	@Override
	public void turnOff(int x, int y) {
		if(x < 0 || x > width){
			throw new IllegalArgumentException(
				"X coordinate is invalid."
			);
		}
		if(y < 0 || y > height){
			throw new IllegalArgumentException(
				"Y coordinate is invalid."
			);
		}
		
		pixels[x][y] = false;

	}

	/* @see hr.fer.zemris.java.graphics.raster.BWRaster#enableFlipMode()*/
	@Override
	public void enableFlipMode() {
		flipMode = true;

	}

	/* @see hr.fer.zemris.java.graphics.raster.BWRaster#disableFlipMode()*/
	@Override
	public void disableFlipMode() {
		flipMode = false;

	}

	/* @see hr.fer.zemris.java.graphics.raster.BWRaster#isTurnedOn(int, int)*/
	@Override
	public boolean isTurnedOn(int x, int y) {
		return pixels[x][y];
	}
	
	/* @see hr.fer.zemris.java.graphics.raster.BWRaster#isFlipModeEnabled()*/
	public boolean isFlipModeEnabled(){
		return flipMode;
	}
}
