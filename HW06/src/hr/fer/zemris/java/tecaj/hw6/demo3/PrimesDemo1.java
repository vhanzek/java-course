package hr.fer.zemris.java.tecaj.hw6.demo3;

/**
 * The first example for working with {@link PrimesCollection} class.
 * 
 * @author Vjeco
 */
public class PrimesDemo1 {

	/**
	 * The main method. Program starts here.
	 *
	 * @param args the arguments. Command line arguments. Unused in this example.
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(5); // 5: how many of them
		
		for(Integer prime : primesCollection) {
			System.out.println("Got prime: "+prime);
		}

	}

}
