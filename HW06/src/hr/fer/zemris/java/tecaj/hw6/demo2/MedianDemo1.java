package hr.fer.zemris.java.tecaj.hw6.demo2;

import java.util.Optional;

/**
 * The first example for working with {@link LikeMedian} class.
 * 
 * @author Vjeco
 */
public class MedianDemo1 {
	
	/**
	 * The main method. Program starts here.
	 *
	 * @param args the arguments. Command line arguments. Unused in this example.
	 */
	public static void main(String[] args) {
		LikeMedian<Integer> likeMedian = new LikeMedian<Integer>();
		likeMedian.add(new Integer(10));
		likeMedian.add(new Integer(5));
		likeMedian.add(new Integer(3));
		Optional<Integer> result = likeMedian.get();
		System.out.println(result);
		
		for (Integer elem : likeMedian) {
			System.out.println(elem);
		}
	}
}
