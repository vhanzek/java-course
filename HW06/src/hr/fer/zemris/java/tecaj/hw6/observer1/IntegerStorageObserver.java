package hr.fer.zemris.java.tecaj.hw6.observer1;

/**
 * An asynchronous update interface for receiving notifications
 * about IntegerStorage information as the IntegerStorage is constructed.
 */
public interface IntegerStorageObserver {
	
	/**
	 * This method is called when information about an {@link IntegerStorage}
	 * which was previously requested using an asynchronous interface becomes 
	 * available. It does certain actions, depending on the implementation.
	 *
	 * @param istorage the integer storage
	 */
	public void valueChanged(IntegerStorage istorage);
}
