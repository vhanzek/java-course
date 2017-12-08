package hr.fer.zemris.java.tecaj.hw6.observer1;

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
		super();
		this.n = n;
	}
	
	/**
	 * @see hr.fer.zemris.java.tecaj.hw6.observer1.IntegerStorageObserver#valueChanged(hr.fer.zemris.java.tecaj.hw6.observer1.IntegerStorage)
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {
		int value = istorage.getValue();
		System.out.println("Double value: " + 2 * value);
		if(--n == 0){
			istorage.removeObserver(this);
		}
	}	
}
