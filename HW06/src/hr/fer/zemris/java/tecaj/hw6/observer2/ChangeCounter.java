package hr.fer.zemris.java.tecaj.hw6.observer2;

import hr.fer.zemris.java.tecaj.hw6.observer2.IntegerStorage;
import hr.fer.zemris.java.tecaj.hw6.observer2.IntegerStorageObserver;

/**
 * The implementation of the interface {@link IntegerStorageObserver} that keeps track 
 * of the number of the times {@link IntegerStorage} value has changed.
 * 
 * @author Vjeco
 */
public class ChangeCounter implements IntegerStorageObserver {
	
	/** The value change count. */
	private int valueChangeCount;
	
	/**
	 * @see hr.fer.zemris.java.tecaj.hw6.observer2.IntegerStorageObserver#valueChanged(hr.fer.zemris.java.tecaj.hw6.observer2.IntegerStorageChange)
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorageChange) {
		System.out.println("Number of value changes since tracking: " + ++valueChangeCount);	
	}

}
