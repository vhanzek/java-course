package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * The Class StringRasterView, subclass of class {@link SimpleRasterView}. 
 * Difference in in method <code>produce(...)</code> which returns string of drawn geometric shapes.
 * 
 * @author Vjeco
 */
public class StringRasterView extends SimpleRasterView {
	
	/**
	 * Instantiates a new string raster view.
	 *
	 * @param turnedOnPixel the character representing turned on pixel
	 * @param turnedOffPixel the character representing turned off pixel
	 */
	public StringRasterView(char turnedOnPixel, char turnedOffPixel) {
		super(turnedOnPixel, turnedOffPixel);
	}
	
	/**
	 * Instantiates a new string raster view with <code>turnedOnPixel</code> character set to '*' and 
	 * <code>turnedOffPixel</code> character set to '.'.
	 */
	public StringRasterView() {
		super();
	}

	/**
	 * {@inheritDoc}
	 * <p>Returns instance of class <code>String</code> and prints geometric shape to standard output.</p>
	 */
	@Override
	public Object produce(BWRaster raster) {
		StringBuilder sb = new StringBuilder();
		
		for(int y = 0; y < raster.getHeight(); y++){
			for(int x = 0; x < raster.getWidth(); x++){
				if(raster.isTurnedOn(x, y)){
					sb.append(turnedOnPixel);
				} else {
					sb.append(turnedOffPixel);
				}
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}

}
