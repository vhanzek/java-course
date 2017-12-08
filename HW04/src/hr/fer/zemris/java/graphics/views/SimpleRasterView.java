package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * The Class SimpleRasterView represents class which produces geometric shapes to, for example, standard output.
 * 
 * @author Vjeco
 */
public class SimpleRasterView implements RasterView {

	/** The character representing turned on pixel. */
	protected char turnedOnPixel;
	
	/** The character representing turned off pixel. */
	protected char turnedOffPixel;

	/**
	 * Instantiates a new simple raster view.
	 *
	 * @param turnedOnPixel the character representing turned on pixel
	 * @param turnedOffPixel the character representing turned off pixel
	 */
	public SimpleRasterView(char turnedOnPixel, char turnedOffPixel) {
		this.turnedOnPixel = turnedOnPixel;
		this.turnedOffPixel = turnedOffPixel;
	}
	
	/**
	 * Instantiates a new simple raster view with <code>turnedOnPixel</code> character set to '*' and 
	 * <code>turnedOffPixel</code> character set to '.'.
	 */
	public SimpleRasterView() {
		this('*', '.');
	}

	/**
	 * {@inheritDoc}
	 * <p>Returns null and prints geometric shape to standard output.</p>
	 */
	@Override
	public Object produce(BWRaster raster) {		
		for(int y = 0; y < raster.getHeight(); y++){
			for(int x = 0; x < raster.getWidth(); x++){
				if(raster.isTurnedOn(x, y)){
					System.out.print(turnedOnPixel);
				} else {
					System.out.print(turnedOffPixel);
				}
			}
			System.out.println();
		}
		
		return null;
	}

}
