package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * The Class LinkedListIndexedCollection representing collection implemented with linked indexed list.
 * 
 * @author Vjeco
 */
public class LinkedListIndexedCollection extends Collection {
	
	/** The number of elements in a list. */
	private int size;
	
	/** The reference to first node in a list. */
	private ListNode first;
	
	/** The reference to last node in a list. */
	private ListNode last;
	
	/**
	 * The class representing one node in a list.
	 */
	private static class ListNode{
		
		/** The next node. */
		ListNode next;
		
		/** The previous node. */
		ListNode previous;
		
		/** The value of the node. */
		Object value;
		
		/**
		 * Instantiates a new node with given value.
		 *
		 * @param value the value of the node
		 */
		public ListNode(Object value){
			this.value = value;
			this.next = null;
			this.previous = null;
		}
	}
	
	/**
	 * Instantiates a new empty linked list indexed collection.
	 */
	public LinkedListIndexedCollection(){
		first = last = null;
		size = 0;
	}
	
	/**
	 * Instantiates a new linked list indexed collection filled with all elements from other collection.
	 *
	 * @param other the other
	 */
	public LinkedListIndexedCollection(Collection other){
		addAll(other);
	}
	
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * {@inheritDoc}
	 * <p>Adds it at the end of the list. Complexity of the method is O(1).
	 * If given value is null method throws IllegalArgumentException.</p>
	 */
	@Override
	public void add(Object value){
		if(Objects.isNull(value)){
			throw new IllegalArgumentException(
				"Given value is null."
			);
		}
		
		ListNode newNode = new ListNode(value);
		
		if(first == null){
			first = last = newNode;
		} else {
			last.next = newNode;
			newNode.previous = last;
			last = newNode;
		}
		size++;
		
		
	}
	
	/* @see hr.fer.zemris.java.custom.collections.Collection#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(Object value){
		ListNode head = first;
		
		while(head != null){
			if(head.value.equals(value)){
				return true;
			}
			head = head.next;
		}
		
		return false;
	}
	
	/* @see hr.fer.zemris.java.custom.collections.Collection#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object value){
		int index = indexOf(value);
		
		if(index > - 1){
			remove(index);
			return true;
		} else {
			return false;
		}		
	}
	
	/**
	 * Removes the list node at the position given by parameter.
	 * If index is out of bounds [0,size-1] method throws IndexOutOfBoundsException.
	 *
	 * @param index the index of the node for removal
	 */
	public void remove(int index){
		if (index < 0 || index > size - 1){
			throw new IndexOutOfBoundsException(
				"Index is out of bounds."
			);
		}

		if(first == null) {
			return;
		} else if(index == 0){
			first = first.next;
			first.previous = null;
		} else if(index == size){
			last = last.previous;
			last.next = null;
		} else {
			ListNode head = first;
			int counter = 0;
			
			while(head != null){
				if(counter++ == index){
					head.previous.next = head.next;
					head.next.previous = head.previous;
					break;
				}
				head = head.next;
			}
		}
		
		size--;
	}
	
	/* @see hr.fer.zemris.java.custom.collections.Collection#toArray()
	 */
	@Override
	public Object[] toArray(){
		ListNode head = first;
		Object[] array = new Object[size];
		int i = 0;
		
		while(head != null){
			array[i++] = head.value;
			head = head.next;
		}
		
		return array;
		
	}
	
	/* @see hr.fer.zemris.java.custom.collections.Collection#forEach(hr.fer.zemris.java.custom.collections.Processor)
	 */
	@Override
	public void forEach(Processor processor){
		ListNode head = first;
		
		while(head != null){
			processor.process(head.value);
			head = head.next;
		}
	}
	
	/* @see hr.fer.zemris.java.custom.collections.Collection#clear()
	 */
	@Override
	public void clear(){
		first = first.next = null;
		last = last.previous = null;
		size = 0;
	}
	
	/**
	 * Gets the value of the node at the position given by parameter index.
	 * If index is out of bounds [0,size-1] method throws IndexOutOfBoundsException.
	 *
	 * @param index the index of the
	 * @return the object
	 */
	public Object get(int index){
		if (index < 0 || index > size - 1){
			throw new IndexOutOfBoundsException(
				"Index is out of bounds."
			);
		}
		
		ListNode head = first;
		ListNode tail = last;
		int counterHead = 0;
		int counterTail = size;
		
		while(true){
			if(counterHead++ == index){
				return head.value;
			} else if(counterTail-- == index){
				return tail.value;
			}
			
			head = head.next;
			tail = tail.previous;
		}		
	}
	
	/**
	 * Inserts new node with value at the certain position given by parameter.
	 * 
	 * @param value the value of the node
	 * @param position the position for inserting node
	 */
	public void insert(Object value, int position){
		if(position < 0 || position > size){
			throw new IllegalArgumentException(
				"Position is out of range."
			);
		}

		ListNode head = first;
		int counter = 0;
		
		ListNode newNode = new ListNode(value);
		
		if(position == 0){
			newNode.next = first;
			first.previous = newNode;
			first = newNode;
		} else if(position == size){
			add(value);
		} else {
			while(head != null){
				if(counter++ == position){
					head.previous.next = newNode;
					newNode.next = head;
					newNode.previous = head.previous;
					head.previous = newNode;
					break;
				}
				head = head.next;
			}
		}
		
		size++;
	}
	
	/**
	 * Returns the index of the node with value given by parameter.
	 *
	 * @param value the value of the searched node
	 * @return the index of the searched node
	 */
	public int indexOf(Object value){
		ListNode head = first;
		int counter = 0;
		
		while(head != null){
			if (head.value.equals(value)){
				return counter;
			}
			head = head.next;
			counter++;
		}
		
		return -1;
	}
}
