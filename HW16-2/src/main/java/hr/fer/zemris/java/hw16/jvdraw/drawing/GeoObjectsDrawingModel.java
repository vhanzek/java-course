package hr.fer.zemris.java.hw16.jvdraw.drawing;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import hr.fer.zemris.java.hw16.jvdraw.objects.AbstractGeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;

/**
 * The implementation of the {@link DrawingModel}. It contains internal
 * list of geometric objects {@link #objects} and when some change in 
 * that list occurs all of the registered listeners in {@link #listeners} 
 * are informed about that change.
 * 
 * @author Vjeran
 */
public class GeoObjectsDrawingModel implements DrawingModel {
	
	/** The listeners. */
	private List<DrawingModelListener> listeners;
	
	/** The objects. */
	private List<GeometricalObject> objects;
	
	/**
	 * Instantiates a new geometric objects drawing model.
	 */
	public GeoObjectsDrawingModel() {
		this.listeners = new ArrayList<>();
		this.objects = new ArrayList<>();
	}

	@Override
	public int getSize() {
		return objects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		return objects.get(index);
	}

	@Override
	public void add(GeometricalObject object) {
		int pos = objects.size();
		objects.add(object);
		
		for(DrawingModelListener l : listeners){
			l.objectsAdded(this, pos, pos);
		}
	}

	@Override
	public void remove(GeometricalObject object) {
		int pos = objects.indexOf(object);
		for(DrawingModelListener l : listeners){
			l.objectsRemoved(this, pos, pos);
		}
		objects.remove(object);
	}

	@Override
	public void clear() {
		AbstractGeometricalObject.resetObjectsCount();
		objects.clear();
	}

	@Override
	public void change(GeometricalObject object) {
		int pos = objects.indexOf(object);
		for(DrawingModelListener l : listeners){
			l.objectsChanged(this, pos, pos);
		}
	}
	
	@Override
	public int indexOf(GeometricalObject object) {
		return objects.indexOf(object);
	}
	
	@Override
	public void forEach(Consumer<GeometricalObject> action) {
		objects.forEach(action);
	}
	
	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		listeners.add(l);
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listeners.remove(l);
	}
}
