package hr.fer.zemris.java.custom.scripting.exec;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This class represents a "special" kind of {@link Map}. It allows the user to store 
 * multiple values for same key and values are stored in a stack-like abstraction.
 * 
 * @author Vjeco
 */
public class ObjectMultistack {
	
	/** The stack map. */
	private Map<String, MultistackEntry> stackMap;
	
	/**
	 * Instantiates a new {@link ObjectMultistack}.
	 */
	public ObjectMultistack() {
		stackMap = new HashMap<>();
	}
	
	/**
	 * Puts the {@link MultistackEntry} with given value mapped to a given key at the end (the top) 
	 * of the linked list representing stack abstraction.
	 *
	 * @param name the key
	 * @param valueWrapper the value to be pushed
	 */
	public void push(String name, ValueWrapper valueWrapper) {
		Objects.requireNonNull(name);
		MultistackEntry newEntry = new MultistackEntry(valueWrapper, null);
		
		if(!stackMap.containsKey(name)){
			stackMap.put(name, newEntry);
		} else {
			MultistackEntry temp = stackMap.get(name);
			if(temp == null){
				temp = newEntry;
			} else {
				temp = stackMap.remove(name);
				stackMap.put(name, newEntry);
				newEntry.next = temp;
			}
		}
	}
	
	/**
	 * Removes and returns the value of the last pushed {@link MultistackEntry} to stack 
	 * mapped to a given key.
	 *
	 * @param name the key
	 * @return the value of the entry
	 */
	public ValueWrapper pop(String name) {
		Objects.requireNonNull(name);
		if(stackMap.containsKey(name)){
			if(isEmpty(name)){
				throw new EmptyStackException();
			} else {
				MultistackEntry temp = stackMap.get(name);
				ValueWrapper poppedValue = temp.getValueWrapper();
				
				if(temp.next != null){
					stackMap.compute(name, (k,v) -> v = v.next);
				} else {
					stackMap.replace(name, null);
				}
				
				return poppedValue;
			}
		} else {
			return null;
		}
		
	}

	/**
	 * Similar to {@code pop()} method, but leaves stack unchanged, i.e. does not remove 
	 * last pushed entry, just returns its value.
	 *
	 * @param name the key
	 * @return the value of the entry
	 */
	public ValueWrapper peek(String name) {
		Objects.requireNonNull(name);
		if(stackMap.containsKey(name)){
			if(isEmpty(name)){
				throw new EmptyStackException();
			} else {
				return stackMap.get(name).getValueWrapper();
			}
		} else {
			return null;
		}
		
	}
	
	/**
	 * Removes mapped value with given key.
	 * 
	 * @param name the key
	 */
	public void remove(String name){
		if(stackMap.containsKey(name)){
			stackMap.remove(name);
		}
	}
	
	/**
	 * Checks if the stack mapped to a given key is empty.
	 *
	 * @param name the key
	 * @return {@code true}, if is empty, {@code false} otherwise
	 */
	public boolean isEmpty(String name) {
		Objects.requireNonNull(name);
		return stackMap.get(name) == null;
	}
	
	/**
	 * The nested class representing one entry in a stack-like abstraction.
	 * 
	 * @author Vjeco
	 */
	private static class MultistackEntry{
		
		/** The value. */
		private ValueWrapper valueWrapper;
		
		/** The reference to next entry. */
		private MultistackEntry next;
		
		/**
		 * Instantiates a new multistack entry.
		 *
		 * @param valueWrapper the value of the entry
		 * @param next the next entry
		 */
		private MultistackEntry(ValueWrapper valueWrapper, MultistackEntry next) {
			this.valueWrapper = valueWrapper;
			this.next = next;
		}

		/**
		 * Gets the value of the entry.
		 *
		 * @return the value of the entry
		 */
		public ValueWrapper getValueWrapper() {
			return valueWrapper;
		}
	}
}
