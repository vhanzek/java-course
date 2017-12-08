package hr.fer.zemris.java.hw16.jvdraw.drawing;

/**
 * The listener interface for receiving {@link DrawingModel} events.
 * The class that is interested in processing a drawingModel event 
 * implements this interface, and the object created with that class 
 * is registered with a component using the component's 
 * <code>addDrawingModelListener<code> method. When the 
 * {@code DrawingModel} event occurs, that object's appropriate
 * method is invoked.
 *
 *
 *@author Vjeran
 */
public interface DrawingModelListener {
	
	/**
	 * Method invoked when objects has been added to internal list of 
	 * geometric objects in implementation of {@link DrawingModel}
	 * interface.
	 *
	 * @param source the source
	 * @param index0 the starting index
	 * @param index1 the ending index
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);

	/**
	 * Method invoked when objects has been remove from the internal list 
	 * of geometric objects in implementation of {@link DrawingModel}
	 * interface.
	 *
	 * @param source the source
	 * @param index0 the starting index
	 * @param index1 the ending index
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);

	/**
	 * Method invoked when objects has been changed in internal list of 
	 * geometric objects in implementation of {@link DrawingModel}
	 * interface.
	 *
	 * @param source the source
	 * @param index0 the starting index
	 * @param index1 the ending index
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);
}