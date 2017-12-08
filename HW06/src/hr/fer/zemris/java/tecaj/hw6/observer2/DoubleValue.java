package hr.fer.zemris.java.tecaj.hw6.observer2;

import hr.fer.zemris.java.tecaj.hw6.observer2.IntegerStorage;
import hr.fer.zemris.java.tecaj.hw6.observer2.IntegerStorageObserver;

/**
 * The implementation of the interface {@link IntegerStorageObserver} that returns double value
 * of the current {@link IntegerStorage} value but only first {@code n} times where n is given in a
 * constructor. After n times, observer automatically unregisters itself from the subject.
 * 
 * @author Vjeco
 */
public class DoubleValue implements IntegerStorageObserver {
	
	/** The number of times observer returns double value. */
	private int n;

	/**
	 * Instantiates a new double value with one parameter,
	 * the number of times observer returns double value.
	 *
	 * @param n the n
	 */
	public DoubleValue(int n) {
		this.n = n;
	}
	
	/**
	 * @see hr.fer.zemris.java.tecaj.hw6.observer2.IntegerStorageObserver#valueChanged(hr.fer.zemris.java.tecaj.hw6.observer2.IntegerStorageChange)
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorageChange) {
		int value = istorageChange.getNewValue();
		System.out.println("Double value: " + 2 * value);
		if(--n == 0){
			istorageChange.getIstorage().removeObserver(this);
		}
	}

}
