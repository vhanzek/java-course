package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;
/**
 * Example for working with ArrayIndexedCollection class and LinkedListIndexedCollection.
 * 
 * @author Vjeco
 */
public class Main {
	/**
	 * Method that starts with the beginning of the program.
	 * 
	 * @param args command line arguments. Unused in this example.
	 */
	public static void main(String[] args) {
		
		/**
		 * Local class with one method which prints given object to a standard output.
		 * @author Vjeco
		 *
		 */
		class P extends Processor {
			public void process(Object o) {
				System.out.println(o);
			}
		};
		
		ArrayIndexedCollection col = new ArrayIndexedCollection(2);
		col.add(new Integer(20));
		col.add("New York");
		col.add("San Francisco"); // here the internal array is reallocated to 4
		System.out.println(col.contains("New York")); // writes: true
		col.remove(1); // removes "New York"; shifts "San Francisco" to position 1
		System.out.println(col.get(1)); // writes: "San Francisco"
		System.out.println(col.size()); // writes: 2
		col.add("Los Angeles");
		col.insert("Miami", 1);
		System.out.println(Arrays.toString(col.toArray())); //writes: [20, Miami, San Francisco, Los Angeles]
		System.out.println(col.indexOf("Los Angeles")); //writes: 3
		
		System.out.println();
		
		LinkedListIndexedCollection col1 = new LinkedListIndexedCollection(col);
		System.out.println("col1 elements:");
		col1.forEach(new P());
		System.out.println("col1 elements again:");
		System.out.println(Arrays.toString(col1.toArray()));
		System.out.println(col1.contains(col.get(1))); // true
		col1.remove(new Integer(20)); // removes 20 from collection (at position 0).
		col1.insert("Zagreb", 2);
		System.out.println(Arrays.toString(col1.toArray()));//writes: [Miami, San Francisco, Zagreb, Los Angeles]
		
	}
}
