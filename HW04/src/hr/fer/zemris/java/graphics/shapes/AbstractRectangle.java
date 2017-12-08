package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * The Class AbstractRectangle represents every "recatanglish" shape (rectangle, square).
 * 
 * @author Vjeco
 */
public abstract class AbstractRectangle extends GeometricShape {

	/** The width of the shape. */
	private int width;
	
	/** The height pf the shape. */
	private int height;
	
	/** The x coordinate of the left upper top. */
	private int centerX;
	
	/** The y coordinate of the left upper top. */
	private int centerY;
	
	/**
	 * Instantiates a new abstract rectangle.
	 *
	 * @throws IllegalArgumentException if width of height are 0 or less.
	 * @param upLeftX the x coordinate of the left upper top
	 * @param upLeftY the y coordinate of the left upper top
	 * @param width the width of the shape
	 * @param height the height of the shape
	 */
	public AbstractRectangle(int upLeftX, int upLeftY, int width, int height){
		if(width < 1){
			throw new IllegalArgumentException(
				"Witdh is invalid."
			);
		}
		if(height < 1){
			throw new IllegalArgumentException(
				"Height is invalid."
			);
		}
		
		centerX = upLeftX;
		centerY = upLeftY;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Gets the width of the shape.
	 *
	 * @return the width of the shape
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Gets the height of the shape.
	 *
	 * @return the height of the shape
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Gets the x coordinate of the left upper top.
	 *
	 * @return the x coordinate of the left upper top
	 */
	public int getcenterX() {
		return centerX;
	}

	/**
	 * Sets the x coordinate of the left upper top.
	 *
	 * @param centerX the new x coordinate of the left upper top
	 */
	public void setcenterX(int centerX) {
		this.centerX = centerX;
	}

	/**
	 * Gets the y coordinate of the left upper top.
	 *
	 * @return the y coordinate of the left upper top
	 */
	public int getcenterY() {
		return centerY;
	}

	/**
	 * Sets the y coordinate of the left upper top.
	 *
	 * @param centerY the new y coordinate of the left upper top
	 */
	public void setcenterY(int centerY) {
		this.centerY = centerY;
	}

	/**
	 * Sets the width of the shape.
	 *
	 * @param width the new width of the shape
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Sets the height of the shape.
	 *
	 * @param height the new height of the shape
	 */
	public void setHeight(int height) {
		this.height = height;
	}	
	
	/**
	 * {@inheritDoc}
	 * <p>More efficient method for drawing rectangle on the raster than the method in {@link GeometricShape} class.
	 * Does not check every pixel of the raster, but turns on every pixel in domain [width, height] 
	 * beginning from the left upper top of the rectangle.</p>
	 */
	@Override
	public void draw(BWRaster r) {
		if (centerX >= r.getWidth() || centerX + width <= 0 
			|| centerY >= r.getHeight() || centerY + height <= 0)
			return;
			
		int fromX = Math.max(0, centerX);
		int toX = Math.min(r.getWidth(), centerX + width);
		int fromY = Math.max(0, centerY);
		int toY = Math.min(r.getHeight(), centerY + height);
		
		for (int x = fromX; x < toX; x++){
			for (int y = fromY; y < toY; y++){
				r.turnOn(x, y);
			}
		}
	}

	/*@see hr.fer.zemris.java.graphics.shapes.GeometricShape#containsPoint(int, int)*/
	@Override
	public boolean containsPoint(int x, int y) {
		return(x >= centerX && 
				x <= centerX + width && 
				 y >= centerY && 
				  y <= centerY + height);
	}


}
