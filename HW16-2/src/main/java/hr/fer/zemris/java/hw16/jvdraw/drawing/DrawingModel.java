package hr.fer.zemris.java.hw16.jvdraw.drawing;

import java.util.function.Consumer;

import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;

/**
 * The interface {@code DrawingModel} representing model of the drawing
 * editor, i.e. instance of class {@link JDrawingCanvas}. Implementation 
 * of this class is {@link GeoObjectsDrawingModel}.
 * 
 * @see GeoObjectsDrawingModel
 * 
 * @author Vjeran
 */
public interface DrawingModel {
	
	/**
	 * Gets the number of drawn objects.
	 *
	 * @return the size
	 */
	public int getSize();

	/**
	 * Gets the object at given index.
	 *
	 * @param index the index
	 * @return the object
	 */
	public GeometricalObject getObject(int index);

	/**
	 * Adds new object to the model. Implementations of this class
	 * in this method inform all registered users that object has 
	 * been added to internal list of objects.
	 *
	 * @param object the object
	 */
	public void add(GeometricalObject object);
	
	/**
	 * Removes given object from the model. Implementations of this 
	 * class in this method inform all registered users that object 
	 * has been removed from internal list of objects.
	 *
	 * @param object the object
	 */
	public void remove(GeometricalObject object);
	
	/**
	 * Removes all objects from the internal objects list. Implementations 
	 * of this class in this method inform all registered users internal 
	 * object list has been cleared.
	 *
	 * @param object the object
	 */
	public void clear();
	
	/**
	 * Changes given object in the model. Implementations of this 
	 * class in this method inform all registered users that object 
	 * has been changed from internal list of objects.
	 *
	 * @param object the object
	 */
	public void change(GeometricalObject object);
	
	/**
	 * Returns index of the given object.
	 *
	 * @param object the object
	 * @return the index of the given object
	 */
	public int indexOf(GeometricalObject object);
	
	/**
	 * Performs the given action for each element in internal
	 * objects list.
	 *
	 * @param action the action
	 */
	public void forEach(Consumer<GeometricalObject> action);

	/**
	 * Adds the drawing model listener.
	 *
	 * @param l the listener
	 */
	public void addDrawingModelListener(DrawingModelListener l);

	/**
	 * Removes the drawing model listener.
	 *
	 * @param l the listener
	 */
	public void removeDrawingModelListener(DrawingModelListener l);
}
