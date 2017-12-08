package hr.fer.zemris.java.tecaj.hw6.observer2;

/**
 * An asynchronous update interface for receiving notifications
 * about IntegerStorage information as the IntegerStorage is constructed.
 * 
 * @author Vjeco
 */
public interface IntegerStorageObserver {
	
	/**
	 * This method is called when information about an IntegerStorage
	 * which was previously requested using an asynchronous
	 * interface becomes available.
	 *
	 * @param istorageChange the instance of {@link IntegerStorageChange}
	 */
	public void valueChanged(IntegerStorageChange istorageChange);
}
