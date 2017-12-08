package hr.fer.zemris.java.tecaj.hw1;

/**
 * The class for working with prime numbers.
 * @author Vjeco
 */
public class PrimeNumbers {

	/**
	 * Method that starts the program.
	 *
	 * @param args one argument is used. Represents number of first n prime number that need to be printed to standard output.
	 */
	public static void main(String[] args) {
		int noOfPrimeNumbers = 0;
		int input = Integer.parseInt(args[0]);
		
		System.out.println("You requested calculation of first " + input + " prime numbers. Here they are:");
		for (int i = 2; noOfPrimeNumbers < input; i++){
			if (isPrime(i)){
				System.out.printf("%d. %d%n", noOfPrimeNumbers+1, i);
				noOfPrimeNumbers++;	
			}
		}
	}
	
	/**
	 * Checks if number is prime.
	 *
	 * @param number checked number
	 * @return true, if is prime, false otherwise
	 */
	private static boolean isPrime(int number){
		if (number == 2) return true;
		if(number % 2 == 0) return false;
		for (int i = 3; i*i <= number; i += 2){
			if (number % i == 0) return false;
		}
		return true;
	}
}
