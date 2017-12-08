package hr.fer.zemris.java.tecaj.hw6.observer2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The class that contains internal {@link Integer} value and offers feature
 * for observing every change of this value. When values changes, every registered 
 * observer is informed about the change.
 * 
 * @author Vjeco
 */
public class IntegerStorage {
	
	/** The internal integer value. */
	private int value;
	
	/** The registered observers. */
	private List<IntegerStorageObserver> observers;

	/**
	 * Instantiates a new integer storage.
	 *
	 * @param initialValue the initial value
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
		this.observers = new ArrayList<>();
	}

	/**
	 * Registers given observer to this integer storage.
	 *
	 * @param observer the observer to be registered
	 */
	public void addObserver(IntegerStorageObserver observer) {
		Objects.requireNonNull(observer, "Given observer must not be null.");
		if(!observers.contains(observer)){
			observers.add(observer);
		}
	}

	/**
	 * Unregisters given observer, i.e. removes it from the internal list of observers.
	 *
	 * @param observer the observer to be unregistered
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		Objects.requireNonNull(observer, "Given observer must not be null.");
		if(observers.contains(observer)){
			observers.remove(observer);
		}
	}

	/**
	 * Unregisters all of the observers.
	 */
	public void clearObservers() {
		observers.clear();
	}

	/**
	 * Gets the internal integer value.
	 *
	 * @return the integer value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets the value and informs all of the registered observers that value has changed.
	 *
	 * @param value the new value
	 */
	public void setValue(int value) {
		if (this.value != value) {
			IntegerStorageChange istorageChange = new IntegerStorageChange(this, this.value, value);
			this.value = value;
			
			if (observers != null) {
				for (int i = 0; i < observers.size(); i++) {
					int sizeBefore = observers.size();
					observers.get(i).valueChanged(istorageChange);
					if (sizeBefore != observers.size()) { i--; }
				}
			}
		}
	}
}
