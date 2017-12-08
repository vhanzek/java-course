package hr.fer.zemris.java.tecaj.hw6.observer2;

/**
 * The class used for storing old value and new value of the class {@link IntegerStorage}.
 * With this approach, it is possible to set value to, as an example, {@code value = oldValue*newValue}.
 * 
 * @author Vjeco
 */
public class IntegerStorageChange {
	
	/** The instance of {@link IntegerStorage}. */
	private IntegerStorage istorage;
	
	/** The old value. */
	private Integer oldValue;
	
	/** The new value. */
	private Integer newValue;
	
	/**
	 * Instantiates a new integer storage change with three parameters.
	 *
	 * @param istorage the integer storage
	 * @param oldValue the old value
	 * @param newValue the new value
	 */
	public IntegerStorageChange(IntegerStorage istorage, Integer oldValue, Integer newValue) {
		this.istorage = istorage;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	/**
	 * Gets the internal instance of {@link IntegerStorage}
	 *
	 * @return the integer storage
	 */
	public IntegerStorage getIstorage() {
		return istorage;
	}

	/**
	 * Gets the old value.
	 *
	 * @return the old value
	 */
	public Integer getOldValue() {
		return oldValue;
	}

	/**
	 * Gets the new value.
	 *
	 * @return the new value
	 */
	public Integer getNewValue() {
		return newValue;
	}
}
