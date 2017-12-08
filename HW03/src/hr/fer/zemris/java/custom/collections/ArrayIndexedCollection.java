package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * The Class ArrayIndexedCollection representing collection implemented with indexed array.
 * 
 * @author Vjeco
 */
public class ArrayIndexedCollection extends Collection {
	
	/** The constant representing default capacity of the collection. */
	private final static int DEFAULT_CAPACITY = 16;
	
	/** The number of elements in an array. */
	private int size;
	
	/** The capacity of the array. */
	private int capacity;
	
	/** The array of stored elements. */
	private Object[] elements;
	
	/**
	 * Instantiates a new array indexed collection with default capacity.
	 */
	public ArrayIndexedCollection(){
		this(DEFAULT_CAPACITY);
	}
	
	/**
	 * Instantiates a new array indexed collection with specified capacity.
	 *
	 * @param initialCapacity the initial capacity of the array
	 */
	public ArrayIndexedCollection(int initialCapacity){
		if(initialCapacity < 1){
			throw new IllegalArgumentException(
				"Initial capacity is less than 1."
			);
		}
		this.capacity = initialCapacity;
		elements = new Object[initialCapacity];
		size = 0;
	}
	
	/**
	 * Instantiates a new array indexed collection filled with elements from the other collection.
	 *
	 * @param other the other collection whose elements are added in this array
	 */
	public ArrayIndexedCollection(Collection other){
		this(DEFAULT_CAPACITY);
		addAll(other);
	}
	
	/**
	 * Instantiates a new array indexed collection filled with elements from the other collection with specified capacity.
	 *
	 * @param other the other collection
	 * @param initialCapacity the initial capacity of the array
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity){
		this(initialCapacity);
		addAll(other);
	}

	/* @see hr.fer.zemris.java.custom.collections.Collection#size()
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * {@inheritDoc}
	 * <p>Adds it at the end of the array. Complexity of the method is O(1).
	 * If given value is null method throws IllegalArgumentException.</p>
	 */
	@Override
	public void add(Object value){
		if (Objects.isNull(value)){
			throw new IllegalArgumentException(
				"Given value is null."
			);
		}
		
		if(size + 1 > capacity){
			reallocate();
		}
		
		elements[size++] = value;	
	}

	/* @see hr.fer.zemris.java.custom.collections.Collection#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(Object value){
		for(Object obj : elements){
			if(value.equals(obj)) {
				return true;
			}
		};
		return false;
	}
	
	/**
	 * Gets the element at the position given by parameter.
	 * If index is out of bounds [0,size-1] method throws IndexOutOfBoundsException.
	 *
	 * @param index the index of the searched element
	 * @return the searched element
	 */
	public Object get(int index){
		if (index < 0 || index > size - 1){
			throw new IndexOutOfBoundsException(
				"Index is out of bounds."
			);
		}
		
		return elements[index];
	}
	
	/**
	 * Inserts specified value at the position given by parameter <code>position</code>.
	 * If position is out of bounds [0,size] method throws IllegalArgumentException.
	 *
	 * @param value the value of the inserted element
	 * @param position the position where element needs to be inserted
	 */
	public void insert(Object value, int position){
		if (position < 0 || position > size){
			throw new IllegalArgumentException(
				"Position for insertion is out of bounds."
			);
		}
		
		if(size + 1 > capacity){
			reallocate();
		}
		
		for (int i = size; i > position; i--){
			elements[i] = elements[i - 1];
		}
		
		elements[position] = value;	
		size++;
	}
	
	/**
	 * Returns index of the specified value.
	 *
	 * @param value the searched value
	 * @return the index of the searched value
	 */
	public int indexOf(Object value){
		for(int i = 0; i < size; i++){
			if(elements[i].equals(value)){
				return i;
			}
		}
		return -1;
		
	}
	
	/**
	 * Removes the element and the position given by parameter.
	 * If index is out of bounds [0,size-1] method throws IndexOutOfBoundsException.
	 *
	 * @param index the index of the element for removal
	 */
	public void remove(int index){
		if (index < 0 || index > size - 1){
			throw new IndexOutOfBoundsException(
				"Index is out of bounds."
			);
		}
		
		for(int i = index; i < size - 1; i++){
			elements[i] = elements[i+1];
		}
		
		elements[--size] = null;
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * <p>Complexity of the method is O(1).</p>
	 */
	@Override
	public boolean remove(Object value){
		int index = indexOf(value);
		if(index > -1){
			remove(index);
			return true;
		} else {
			return false;
		}
	}

	/* @see hr.fer.zemris.java.custom.collections.Collection#toArray()
	 */
	@Override
	public Object[] toArray(){
		Object[] dest = new Object[size];
	    System.arraycopy(elements, 0, dest, 0, size);
	    return dest;
	}
	
	/* @see hr.fer.zemris.java.custom.collections.Collection#forEach(hr.fer.zemris.java.custom.collections.Processor)
	 */
	@Override
	public void forEach(Processor processor){
		for(int i = 0; i < size; i++){
			processor.process(elements[i]);
		}
	}
	
	/* @see hr.fer.zemris.java.custom.collections.Collection#clear()
	 */
	@Override
	public void clear(){
		for(int i = 0; i < size; i++){
			elements[i] = null;
		}
		size = 0;
	}
	
	/**
	 * Reallocates the array by doubling its size.
	 */
	private void reallocate() {
		capacity = size * 2;
		Object[] newElements = new Object[capacity];
		for(int i = 0; i < size; i++){
			newElements[i] = elements[i]; 
		}
		elements = newElements;
	}
	
	
	
	
	
	
}
