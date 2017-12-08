package hr.fer.zemris.java.custom.collections;

/**
 * The Class Collection representing structure for storing and working with data.
 * 
 * @author Vjeco
 */
public class Collection {
	
	/**
	 * Checks if collection contains no elements.
	 *
	 * @return true, if is empty, false otherwise
	 */
	public boolean isEmpty(){
		return size() <= 0;
	}

	/**
	 * Returns number of stored values in collection.
	 *
	 * @return the size of collection
	 */
	public int size() {
		return 0;
	}
	
	/**
	 * Adds new value into collection. Size of the collection is increased by 1.
	 *
	 * @param value the value that needs to be added
	 */
	public void add(Object value){
		
	}
	
	/**
	 * Checks if collection contains certain value.
	 *
	 * @param value the test value
	 * @return true, if successful, false otherwise
	 */
	public boolean contains(Object value){
		return false;
	}
	
	/**
	 * Removes certain value from collection.
	 *
	 * @param value the value for removal
	 * @return true, if successful, false otherwise
	 */
	public boolean remove(Object value){
		return false;	
	}
	
	/**
	 * Returns values from collection as an array of objects.
	 *
	 * @return the object[] array with values from collection
	 */
	public Object[] toArray(){
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Starts method <code>void process();</code> from given processor for each element in collection.
	 *
	 * @param processor the processor used as functional interface
	 */
	public void forEach(Processor processor){
		
	}
	
	/**
	 * Adds all values from given collection to this one.
	 *
	 * @param other the other collection
	 */
	public void addAll(Collection other){
		
		/**
		 * 
		 * Local class with one method for adding given value to a collection.
		 * 
		 * @author Vjeco
		 */

		class LocalProcessor extends Processor {

			@Override
			public void process(Object value) {
				add(value);
			}
		}
		
		Processor locProc = new LocalProcessor();
		other.forEach(locProc);
	}
	
	/**
	 * Removes all values from collection. Empties it.
	 */
	public void clear(){
		
	}
	
}
