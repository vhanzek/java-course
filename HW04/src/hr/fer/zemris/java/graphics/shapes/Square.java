package hr.fer.zemris.java.graphics.shapes;

/**
 * The Class Square represents implementation of the square in Cartesian coordinate system.
 * 
 * @author Vjeco
 */
public class Square extends AbstractRectangle {
	
	/**
	 * Instantiates a new square.
	 *
	 * @param sx the x coordinate of the left upper top
	 * @param sy the y coordinate of the left upper top
	 * @param size the length of the square's side
	 */
	public Square(int sx, int sy, int size) {
		super(sx, sy, size, size);
	}

	/**
	 * Sets the length of the square's side by setting both width and height of the shape.
	 *
	 * @param size the new length of the square's side.
	 */
	public void setSize(int size) {
		super.setWidth(size);
		super.setHeight(size);
	}

	/**
	 * Sets the length of the square's side by setting both width and height of the shape.
	 *
	 * @param size the new length of the square's side.
	 */
	@Override
	public void setWidth(int size){
		setSize(size);
	}
	
	/**
	 * Sets the length of the square's side by setting both width and height of the shape.
	 *
	 * @param size the new length of the square's side.
	 */
	@Override
	public void setHeight(int size){
		setSize(size);
	}

}
