package hr.fer.zemris.java.tecaj.hw6.demo3;

/**
 * The second example for working with {@link PrimesCollection} class.
 * 
 * @author Vjeco
 */
public class PrimesDemo2 {

	/**
	 * The main method. Program starts here.
	 *
	 * @param args the arguments. Command line arguments. Unused in this example.
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(2);
		
		for (Integer prime : primesCollection) {
			for (Integer prime2 : primesCollection) {
				System.out.println("Got prime pair: " + prime + ", " + prime2);
			}
		}

	}

}
