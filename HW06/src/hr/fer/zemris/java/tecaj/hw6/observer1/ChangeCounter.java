package hr.fer.zemris.java.tecaj.hw6.observer1;

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
	 * @see hr.fer.zemris.java.tecaj.hw6.observer1.IntegerStorageObserver#valueChanged(hr.fer.zemris.java.tecaj.hw6.observer1.IntegerStorage)
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {
		System.out.println("Number of value changes since tracking: " + ++valueChangeCount);
	}

}
