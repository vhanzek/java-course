package hr.fer.zemris.java.tecaj.hw1;

/**
 * The Class HofstadterQ used for calculating Hofstadter Q sequence.
 * @author Vjeco
 */
public class HofstadterQ {

	/**
	 * Method that starts the program.
	 *
	 * @param args only one argument is used. Represents n-th number of Hofstadter's Q sequence
	 */
	public static void main(String[] args) {
		int length = args.length;
		if(length == 0){
			System.err.println("No arguments.");
		} else if (length == 1){
			long number = Long.parseLong(args[0]);
			if (number < 0L){
				System.err.println("Argument is negative.");
				return;
			} else {
				System.out.printf("You requested calculation of %d. number of Hofstadter's Q-sequence. The requested number is %d.%n",
									number, hofstadterQ(number));
			}
		} else {
			System.err.println("Too much arguments.");
		}
	}

	/**
	 * Recursive method for calculating n-th number of Hofstadter's Q sequence.
	 *
	 * @param n n-th number of Hofstadter's Q sequence.
	 * @return value of n-th number
	 */
	private static int hofstadterQ(long n) {
		int result;
		if (n < 3) result = 1;
		else result = hofstadterQ(n- hofstadterQ(n - 1)) + hofstadterQ(n - hofstadterQ(n- 2));
		
		return result;
		
	}
}
