package hr.fer.zemris.java.tecaj.hw6.observer2;

import hr.fer.zemris.java.tecaj.hw6.observer2.IntegerStorage;
import hr.fer.zemris.java.tecaj.hw6.observer2.IntegerStorageObserver;

/**
 * The implementation of the interface {@link IntegerStorageObserver} that returns square value
 * of the current {@link IntegerStorage} value.
 * 
 * @author Vjeco
 */
public class SquareValue implements IntegerStorageObserver {

	/**
	 * @see hr.fer.zemris.java.tecaj.hw6.observer2.IntegerStorageObserver#valueChanged(hr.fer.zemris.java.tecaj.hw6.observer2.IntegerStorageChange)
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorageChange) {
		int value = istorageChange.getNewValue();
		System.out.println("Provided new value: " + value + ", square is " + value*value);

	}

}
