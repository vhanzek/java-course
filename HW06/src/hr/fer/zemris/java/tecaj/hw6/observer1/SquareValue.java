package hr.fer.zemris.java.tecaj.hw6.observer1;

/**
 * The implementation of the interface {@link IntegerStorageObserver} that returns square value
 * of the current {@link IntegerStorage} value.
 * 
 * @author Vjeco
 */
public class SquareValue implements IntegerStorageObserver {

	/**
	 * @see hr.fer.zemris.java.tecaj.hw6.observer1.IntegerStorageObserver#valueChanged(hr.fer.zemris.java.tecaj.hw6.observer1.IntegerStorage)
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {
		int value = istorage.getValue();
		System.out.println("Provided new value: " + value + ", square is " + value*value);

	}

}
