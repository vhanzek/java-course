package hr.fer.zemris.java.tecaj.hw1;

/**
 * The Class NumberDecomposition used for separating number to prime factors.
 */
public class NumberDecomposition {
	
	/**
	 * Method that starts the program.
	 *
	 * @param args one argument is used. Represents number that needs to be separated into prime factors.
	 */
	public static void main(String[] args) {
		int number = Integer.parseInt(args[0]);
		int noOfPrimeNum = 0;
		
		System.out.println("You requested decomposition of number " + number + " into prime factors. Here they are:");
		for (int i = 2; number != 1; i++){
			if (isPrime(i)){
				while (number % i == 0){
					number /= i;
					System.out.printf("%d. %d%n", ++noOfPrimeNum, i);
				}
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
