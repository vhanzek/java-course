package hr.fer.zemris.java.tecaj.hw6.demo2;

import java.util.Optional;

/**
 * The second example for working with {@link LikeMedian} class.
 * 
 * @author Vjeco
 */
public class MedianDemo2 {
	
	/**
	 * The main method. Program starts here.
	 *
	 * @param args the arguments. Command line arguments. Unused in this example.
	 */
	public static void main(String[] args) {
		LikeMedian<String> likeMedian = new LikeMedian<String>();
		likeMedian.add("Joe");
		likeMedian.add("Jane");
		likeMedian.add("Adam");
		likeMedian.add("Zed");
		Optional<String> result = likeMedian.get();
		System.out.println(result); // Writes: Jane
	}
}
