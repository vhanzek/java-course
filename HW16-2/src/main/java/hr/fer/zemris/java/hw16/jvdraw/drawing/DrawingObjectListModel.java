package hr.fer.zemris.java.hw16.jvdraw.drawing;

import javax.swing.AbstractListModel;
import javax.swing.JList;

import hr.fer.zemris.java.hw16.jvdraw.components.JDrawingList;
import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;

/**
 * The class {@code DrawingObjectListModel} representing list model.
 * In this program, {@link JDrawingList} uses this model. It behaves
 * as subject in observer design pattern to list, but it is also listener
 * of implementation of {@link DrawingModel} {@link GeoObjectsDrawingModel}.
 * 
 * @author Vjeran
 */
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject> 
											implements DrawingModelListener{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2322699070719336233L;
	
	/** The model. */
	private GeoObjectsDrawingModel model;
	
	/**
	 * Instantiates a new drawing object list model.
	 *
	 * @param model the model
	 */
	public DrawingObjectListModel(GeoObjectsDrawingModel model) {
		this.setModel(model);
		model.addDrawingModelListener(this);
	}
	
	@Override
	public int getSize() {
		return model.getSize();
	}

	@Override
	public GeometricalObject getElementAt(int index) {
		return model.getObject(index);
	}
	
	/**
	 * Method that informs all registered listeners (instances of class
	 * {@link JList} that given object has been added.
	 *
	 * @param object the object
	 */
	public void add(GeometricalObject object){
		int pos = model.getSize();		
		fireIntervalAdded(this, pos, pos);
	}
	
	/**
	 * Method that informs all registered listeners (instances of class
	 * {@link JList} that given object has been removed.
	 *
	 * @param object the object
	 */
	public void remove(GeometricalObject object){
		int pos = model.indexOf(object);
		fireIntervalRemoved(this, pos, pos);
	}
	
	/**
	 * Method that informs all registered listeners (instances of class
	 * {@link JList} that given object has been changed.
	 *
	 * @param object the object
	 */
	public void change(GeometricalObject object){
		int pos = model.indexOf(object);
		fireContentsChanged(this, pos, pos);
	}
	
	/**
	 * Gets the model.
	 *
	 * @return the model
	 */
	public GeoObjectsDrawingModel getModel() {
		return model;
	}
	
	/**
	 * Sets the model.
	 *
	 * @param model the new model
	 */
	public void setModel(GeoObjectsDrawingModel model) {
		this.model = model;
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		for(int i = index0; i <= index1; i++){
			GeometricalObject object = source.getObject(i);
			add(object);
		}
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		for(int i = index0; i <= index1; i++){
			GeometricalObject object = source.getObject(i);
			remove(object);
		}
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		for(int i = index0; i <= index1; i++){
			GeometricalObject object = source.getObject(i);
			change(object);
		}
	}
}
