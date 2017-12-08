package hr.fer.zemris.java.tecaj.hw5.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Class representing hash table implementation. This class does not guarantee that 
 * order of the stored key-value mappings will remain the constant over the time.
 * It is possible to store <code>null</code> as a value in a key-value mapping, but that
 * does not apply for key.
 * 
 * <p>This implementation supports having more that one entry inside of the table's 
 * slot, each entry linked in a list. That means that iteration over collection views 
 * requires time proportional to the "capacity" of the table (the number of slots) plus 
 * its size (the number of key-value mappings). However, assuming the hash function disperses 
 * the entries properly among the table slots, this implementation provides constant-time 
 * performance for the basic operations like <code>get</code> and <code>put</code>, 
 * assuming the hash function disperses the elements properly among the table slots.</p>
 * 
 * <p>An instance of <code>SimpleHashtable</code> has two parameters that affect its 
 * performance: capacity and load factor. Capacity is the number of slots in the table and
 * the load factor is a measure of how full the hash table is allowed to get before its 
 * capacity is automatically increased.</p>
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 * 
 * @author Vjeco
 */
public class SimpleHashtable<K,V> implements Iterable<SimpleHashtable.TableEntry<K,V>>{
	
	/** The default capacity used when none specified in constructor. */
	private final static int DEFAULT_TABLE_SIZE = 16;
	
	/** The load factor of this table */
	private final static double LOAD_FACTOR = 0.75;
	
	/**
	 * Class representing key-value entry in this hash table.
	 *
	 * @param <K> the type of keys in this table
	 * @param <V> the type of mapped values
	 * 
	 * @author Vjeco
	 */
	public static class TableEntry<K,V> {
		
		/** The key of the table entry. */
		private K key;
		
		/** The value of the table entry. */
		private V value;
		
		/** The next entry in a linked list. */
		public TableEntry<K,V> next;
		
		/**
		 * Instantiates a new key-value entry.
		 *
		 * @param key the key of the table entry
		 * @param value the value of the table entry.
		 * @param next the next entry in a list
		 */
		public TableEntry(K key, V value, TableEntry<K,V> next){
			this.key = key;
			this.value = value;
			this.next = next;
		}
		
		/**
		 * Gets the key.
		 *
		 * @return the key
		 */
		public K getKey(){
			return key;
		}
		
		/**
		 * Gets the value.
		 *
		 * @return the value
		 */
		public V getValue(){
			return value;
		}
		
		/**
		 * Sets the value.
		 *
		 * @param value the new value
		 */
		public void setValue(V value){
			this.value = value;
		}
		
		/**
		 * Returns key and value as a string.
		 * 
		 * @return "key=value" string
		 */
		@Override
		public String toString(){
			return key + "=" + value;
		}
	}
	
	/** The number of key-value mappings contained in this table. */
	private int size;
	
	/** The number of used slots in this table. */
	private int usedSlots;
	
	/** The number of times that this table has been modified. */
	private int modificationCount;
	
	/** The table with key-value entries. */
	protected TableEntry<K,V>[] table;
	
	/**
	 * Instantiates a new simple hash table. If <code>capacity</code> is not power of 2, 
	 * it is increased until it becomes the first number that is power of two.
	 *
	 * @param capacity the initial capacity of the table
	 * 
	 * @throws IllegalArgumentException if <code>capacity</code> is not positive
	 */
	
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity){
		if(capacity < 1){
			throw new IllegalArgumentException(
				"Table capacity must be positive."
			);
		}
		
		while ((capacity & (capacity - 1)) != 0){
			capacity++;
		}
		table = (TableEntry<K, V>[]) new TableEntry[capacity];
	}
	
	/**
	 * Instantiates a new simple hash table with default capacity.
	 */
	public SimpleHashtable(){
		this(DEFAULT_TABLE_SIZE);
	}
	
	/**
	 * Method for putting key-value mappings in table. Entry is put in the specified slot
	 * according to its hash code. If slot contains more than one entry, new entry is put
	 * at the end of that list. If given key already exists in table, its value is replaced
	 * with a new one. Also, this action increases <code>modificationCount</code> because
	 * it changes structure of the table.
	 *
	 * @param key the key to be put
	 * @param value the value to be put
	 * 
	 * @throws IllegalArgumentException if key is <code>null</code>
	 */
	public void put(K key, V value){
		if(Objects.isNull(key)){
			throw new IllegalArgumentException(
				"Key must not be null."
			);
		}
		int hash = Math.abs(key.hashCode()) % table.length;
		TableEntry<K,V> entry = table[hash];
		TableEntry<K,V> newElement = new TableEntry<>(key, value, null);
		
		if (entry == null){
			table[hash] = newElement;
			usedSlots++;
			modificationCount++;
		} else {
			while(true){
				if(entry.getKey().equals(key)){
					entry.setValue(value);
					return;
				} else if(entry.next == null){
					break;
				} else {
					entry = entry.next;
				}
			}
			entry.next = newElement;
			modificationCount++;
		}	
		size++;
		
		double ratio = (double)usedSlots/(double)table.length;
		if(ratio >= LOAD_FACTOR){
			resize();
		}
	}	

	/**
	 * Method for resizing table's capacity. It is called if number of non empty slot
	 * in a table reaches its threshold (capacity * load factor). All of the entries in
	 * the "old" table are rehashed and put in a new table which capacity is doubled 
	 * according to old capacity. This action increases <code>modificationCount</code>.
	 */
	private void resize() {
		int newCapacity = table.length * 2;
		TableEntry<K,V>[] oldTable = table;
		
		@SuppressWarnings("unchecked")
		TableEntry<K,V>[] newTable = new TableEntry[newCapacity];
		table = newTable;
		usedSlots = 0;
		size = 0;
		
		for (int i = 0; i < oldTable.length; i++){
			if(oldTable[i] != null){
				TableEntry<K,V> entry = oldTable[i];
				while(entry != null){
					put(entry.getKey(), entry.getValue());
					entry = entry.next;
				}
			}
		}
		
		modificationCount++;
	}

	/**
	 * Gets the value of the given key. If the hash function disperses 
	 * the entries properly among the table slots, this method is done in 
	 * O(1) complexity.
	 *
	 * @param key the key
	 * @return the value of the given key
	 */
	
	public V get(Object key){
		int hash = Math.abs(key.hashCode()) % table.length;
		TableEntry<K,V> entry = table[hash];
		
		while(entry != null){
			if (entry.getKey().equals(key)){
				return entry.getValue();
			}
			entry = entry.next;
		}
		return null;
	}
	
	/**
     * Returns the number of key-value mappings in this map.
     *
     * @return the number of key-value mappings in this map
     */
	public int size(){
		return size;
	}
	
	/**
	 * Checks if this table contains given key in a complexity 
	 * O(n) where n is number of key-value entries in a <b>certain slot.</b>
	 *
	 * @param key the searched key
	 * @return <code>true</code>, if table contains given key
	 */
	public boolean containsKey(Object key){
		int hash = Math.abs(key.hashCode()) % table.length;
		TableEntry<K,V> entry = table[hash];
		
		while(entry != null){
			if(entry.getKey().equals(key)){
				return true;
			}
			entry = entry.next;
		}
		return false;
	}
	
	/**
	 * Checks if this table contains given value in a complexity
	 * O(n) where n is number of key-value entries in <b>whole table</b>.
	 *
	 * @param value the searched value
	 * @return <code>true</code>, if successful
	 */
	public boolean containsValue(Object value){
		int currentPosition = 0;
		TableEntry<K,V> entry = table[currentPosition];
		
		while(true){
			while(entry != null){
				if(entry.getValue() == value){
					return true;
				} else {
					entry = entry.next;
				}
			}
			if(++currentPosition < table.length){
				entry = table[currentPosition];
			} else break;
		}
		
		return false;
	}
		
	/**
	 * Removes the entry with given key. If table does not contain given key,
	 * method returns <code>null</code>. Note that there is difference between
	 * table not containing key and returning key's value that is <code>null</code>.
	 * This action increases <code>modificationCount</code> because it changes
	 * structure of the table.
	 *
	 * @param key the key, if exists in a table, <code>null</code> otherwise
	 */
	public void remove(Object key){
		int hash = Math.abs(key.hashCode()) % table.length;
		TableEntry<K,V> entry = table[hash];
		
		if (entry.getKey().equals(key)){
			table[hash] = entry.next;
			size--;
			if(table[hash] == null){ usedSlots--; }
		} else {
			while(entry != null){
				if (entry.next.getKey().equals(key)){
					entry.next = (entry.next).next;
					size--;
					break;
				} else {
					entry = entry.next;
				}
			}
		}
		
		modificationCount++;
	}
	
	/**
     * Returns <code>true</code> if this table contains no key-value mappings.
     *
     * @return <code>true</code> if this table contains no key-value mappings
     */
	public boolean isEmpty(){
		return size == 0;
	}
	
	/**
	 * Removes all of the mappings from this table.
	 */
	public void clear(){
		if(!isEmpty()){
			size = 0;
			for(int i = 0; i < table.length; i++){
				table[i] = null;
			}
		}
	}
	
	/**
	 * Returns entries from the table as a string.
	 */
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		sb.append("[");
		for(TableEntry<K,V> entry : this){
			sb.append(entry.toString() + ", ");
		}
		String hashTable = sb.toString();
		hashTable = hashTable.substring(0, hashTable.length() - 2);
		
		return hashTable + "]";
	}
	
	/**
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<SimpleHashtable.TableEntry<K, V>> iterator(){
		return new IteratorImpl(modificationCount);
	}
	
	/**
	 * The class that "knows" how to iterate the elements of this collection.
	 * <p>This iterator is a fast-fail iterator. In other words, if the table is 
	 * structurally modified at any time after the iterator is created, in any 
	 * way except through the iterator's own <code>remove</code> method, 
	 * the iterator will throw a {@link ConcurrentModificationException}.</p>
	 * 
	 * @author Vjeco
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K,V>>{

		/** The number of elements left in an iteration. */
		private int elementsLeft; 
		
		/** The current position of the iteration. */
		private int currentPosition;
		
		/** The current element of the iteration. */
		private TableEntry<K,V> currentElement;
		
		/** 
		 * The last returned element by method <code>next()</code>.
		 * Initially is set to <code>null</code>.
		 */
		private TableEntry<K,V> lastReturnedElement;
		
		/** 
		 * Number of modifications in a table at the moment 
		 * when this iterator is created. Used for checking if
		 * structure of the table has changed.
		 */
		private int iteratorModCount;
		
		/**
		 * Instantiates a new simple hash table iterator.
		 * 
		 * @param modicifationCount 
		 * 				number of modifications in a table at the moment 
		 * 				when this iterator is created
		 */
		public IteratorImpl(int modificationCount){
			this.elementsLeft = size;
			this.currentElement = table[currentPosition];
			this.iteratorModCount = modificationCount;
		}
		
		/**
		 * {@inheritDoc}
		 * 
		 * @throws ConcurrentModificationException 
		 * 				If the structure of the table has changed during the iteration.
		 */
		@Override
		public boolean hasNext() {
			if(modificationCount != iteratorModCount){
				throw new ConcurrentModificationException(
					"Internal collection has changed."
				);
			}
			return elementsLeft > 0;
		}
		
		/**
		 * {@inheritDoc}
		 * 
		 * @throws ConcurrentModificationException 
		 * 				If the structure of the table has changed during the iteration.
		 */
		@Override
		public TableEntry<K,V> next() {
			if(modificationCount != iteratorModCount){
				throw new ConcurrentModificationException(
					"Internal collection has changed."
				);
			}
			if(!hasNext()){
				throw new NoSuchElementException(
					"Iterator has reached end of the collection."
				);
			}
			
			TableEntry<K,V> entry = currentElement;
			
			while(true){
				if(entry != null){
					currentElement = entry.next;
					elementsLeft--;
					lastReturnedElement = entry;
					return entry;
				} else {
					currentElement = table[++currentPosition];
					entry = currentElement;
				}
			}
		}
		
		/**
		 * {@inheritDoc}
		 * 
		 * @throws ConcurrentModificationException 
		 * 				If the structure of the table has changed during the iteration.
		 */
		@Override
		public void remove() {
			if(modificationCount != iteratorModCount){
				throw new ConcurrentModificationException(
					"Internal collection has changed."
				);
			}
			if(lastReturnedElement == null){
				throw new IllegalStateException(
					"Unable to remove specified element."
				);
			}
			
			SimpleHashtable.this.remove(lastReturnedElement.getKey());
			iteratorModCount++;
			lastReturnedElement = null;
		}
	}
}
