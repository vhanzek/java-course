package hr.fer.zemris.java.tecaj.hw6.demo2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * The class used for storing elements that implement interface {@link Comparable},
 * i.e. has defined natural ordering. It offers method for calculating median of the 
 * currently stored elements.
 *
 * @param <T> the generic type
 * 
 * @author Vjeco
 */
@SuppressWarnings("rawtypes")
public class LikeMedian<T extends Comparable> implements Iterable<T> {
	
	/** The currently stored elements. */
	private List<T> elements;
	
	/** The modification count. */
	private int modificationCount;
	
	/**
	 * Empty constructor of this class.
	 */
	public LikeMedian() {
		elements = new ArrayList<T>();
	}
	
	/**
	 * Adds the element to internal collection.
	 * This method changes modification count.
	 *
	 * @param element the element to be stored
	 */
	public void add(T element){
		elements.add(element);
		modificationCount++;
	}
	
	/**
	 * Removes the element at the given index from internal collection.
	 * This method changes modification count.
	 *
	 * @param index the index of the element to be removed
	 */
	public void remove(int index){
		elements.remove(index);
		modificationCount++;
	}
	
	/**
	 * Removes the given element from internal collection.
	 * This method changes modification count.
	 *
	 * @param element the element to be removed
	 */
	public void remove(Object element){
		elements.remove(element);
		modificationCount++;
	}
	
	/**
	 * Gets the median of currently stored elements. If user adds an odd number of elements, 
	 * the method returns median element. If user provides an even number of elements, 
	 * the method returns the smaller from the two elements which would usually be used 
	 * to calculate median element.
	 *
	 * @return the result instance of {@link Optional} class
	 */
	@SuppressWarnings("unchecked")
	public Optional<T> get(){
		if(isEmpty()){
			return Optional.empty();
		}
		
		List<T> tempList = new ArrayList<>(elements);
		Collections.sort(tempList);
		
		int index;
		if(size() < 3){
			index = 0;
		} else if(size() % 2 != 0){
			index = size()/2;
		} else {
			index = size()/2 - 1;
		}
		
		return Optional.of(tempList.get(index));
	}
	
	/**
	 * Returns the size of the internal collection.
	 *
	 * @return the size of the internal collection
	 */
	public int size(){
		return elements.size();
	}
	
	/**
	 * Checks if the internal collection is empty.
	 *
	 * @return {@code}true, if is empty, {@code false} otherwise
	 */
	public boolean isEmpty(){
		return size() == 0;
	}

	/**
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<T> iterator() {
		return new IteratorImpl();
	}
	
	/**
	 * The implementation of the {@link Iterator} class for this specified {@link LikeMedian} class.
	 * The class that "knows" how to iterate the internal collection.
	 * 
	 * @author Vjeco
	 */
	private class IteratorImpl implements Iterator<T>{
		
		/** The current element. */
		private T currentElement;
		
		/** The index of the current element. */
		private int currentElementIndex;
		
		/** The last returned element. */
		private T lastReturnedElement;
		
		/** The elements left in a collection. */
		private int elementsLeft; 
		
		/** The iterator modification count. */
		private int iteratorModCount;
		
		/**
		 * Instantiates a new iterator implementation.
		 */
		private IteratorImpl() {
			currentElement = isEmpty() ? null : elements.get(0);
			currentElementIndex = 1;
			iteratorModCount = modificationCount;
			elementsLeft = size();
		}

		/**
		 * @see java.util.Iterator#hasNext()
		 */
		@Override
		public boolean hasNext() {
			if(iteratorModCount != modificationCount){
				throw new ConcurrentModificationException(
					"Internal list has cahnged."
				);
			}
			return elementsLeft > 0;
		}

		/**
		 * @see java.util.Iterator#next()
		 */
		@Override
		public T next() {
			if(iteratorModCount != modificationCount){
				throw new ConcurrentModificationException(
					"Internal list has changed."
				);
			}
			if(!hasNext()){
				throw new NoSuchElementException(
					"Iterator has reached end of the collection."
				);
			}
			
			lastReturnedElement = currentElement;
			currentElement = elements.get(currentElementIndex);
			elementsLeft--;
			
			if(currentElementIndex != size() - 1){
				currentElementIndex++;
			}
			
			return lastReturnedElement;
		}
		
		/**
		 * @see java.util.Iterator#remove()
		 */
		@Override
		public void remove(){
			if(iteratorModCount != modificationCount){
				throw new ConcurrentModificationException(
					"Internal list has changed."
				);
			}
			if(lastReturnedElement == null){
				throw new IllegalStateException(
					"Unable to remove specified element."
				);
			}
			
			LikeMedian.this.remove(lastReturnedElement);
			iteratorModCount++;
			lastReturnedElement = null;
		}		
	}
}
