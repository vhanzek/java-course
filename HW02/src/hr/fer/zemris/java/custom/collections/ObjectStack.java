package hr.fer.zemris.java.custom.collections;

import hr.fer.zemris.java.custom.collections.EmptyStackException;

/**
 * The Class ObjectStack representing class for working with stack. Each instance of this class
 * will create and manage its own private instance of ArrayIndexedCollection and use it for actual element
 * storage. This way, the methods of ObjectStack will be the methods user expects to exist in stack, and those
 * methods will implement its functionality by calling (i.e. delegating) methods of its internal collection of
 * type ArrayIndexedCollection.
 * 
 * @author Vjeco
 */
public class ObjectStack {

	/** The inner instance of the ArrayIndexedCollection class used as actual element storage. */
	private ArrayIndexedCollection collectionInstance;
	
	/**
	 * Instantiates a new object stack.
	 *
	 * @param collectionInstance the internal instance of type ArrayIndexedCollection
	 */
	public ObjectStack(ArrayIndexedCollection collectionInstance){
		this.collectionInstance = collectionInstance;
	}
	
	/**
	 * Checks if stack contains no objects.
	 *
	 * @return true, if is empty, false otherwise
	 */
	public boolean isEmpty(){
		return size() <= 0;
	}

	/**
	 * Returns number of objects in a stack.
	 *
	 * @return the number of objects in a stack
	 */
	public int size() {
		return collectionInstance.size();
	}
	
	/**
	 * Puts given value to the top of the stack (at the end of an array).
	 *
	 * @param value the value of the element
	 */
	public void push(Object value){
		collectionInstance.add(value);
	}
	
	/**
	 * Returns  and removes last element placed on stack (from the end of an array).
	 * If stack contains no elements method throws @see {EmptyStackException}.
	 * 
	 * @return the object from the top of the stack
	 */
	public Object pop(){
		if(isEmpty()){
			throw new EmptyStackException(
				"Stack is empty."
			);
		}
		
		int len = size() - 1; 
		Object value = collectionInstance.get(len);
		collectionInstance.remove(len);
		return value;
	}
	
	/**
	 * Similar as <code>pop();</code>, returns last element placed on stack but does not delete it from stack.
	 *
	 * @return the last placed object on the stack
	 */
	public Object peek(){
		return collectionInstance.get(size());
	}
	
	/**
	 * Removes all elements from the stack.
	 */
	public void clear(){
		collectionInstance.clear();
	}
}
