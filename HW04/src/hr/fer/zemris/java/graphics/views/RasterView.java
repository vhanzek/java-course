package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * The Interface RasterView represents interface which produces geometric shapes to some output stream.
 * 
 * @author Vjeco
 */
public interface RasterView {
	
	/**
	 * Produces geometric shape drawn on raster to some output stream.
	 *
	 * @param raster the used raster
	 * @return the object null or <code>String</code>, depending on implementation
	 */
	Object produce(BWRaster raster);
}
