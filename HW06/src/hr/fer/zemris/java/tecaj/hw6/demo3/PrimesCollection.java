package hr.fer.zemris.java.tecaj.hw6.demo3;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The class used for working with collection of primes numbers.
 * 
 * @author Vjeco
 */
public class PrimesCollection implements Iterable<Integer>{
	
	/** The number of first n primes. */
	private int noOfPrimes;
	
	/**
	 * Instantiates a new primes collection with number of primes 
	 * set to given number of primes.
	 *
	 * @param noOfPrimes the number of first n primes
	 */
	public PrimesCollection(int noOfPrimes) {
		this.noOfPrimes = noOfPrimes;
	}

	/*
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<Integer> iterator() {
		return new IteratorImpl();
	}
	
	/**
	 * The implementation of the {@link Iterator} class for this specified {@link PrimesCollection} class.
	 * The class that "knows" how to iterate the internal collection.
	 * 
	 * @author Vjeco
	 */
	private class IteratorImpl implements Iterator<Integer>{
		
		/** The elements processed. */
		private int elementsProcessed;
		
		/** The current prime number. */
		private int currentPrime;
		
		/**
		 * Instantiates a new iterator implementation.
		 */
		public IteratorImpl() {
			currentPrime = 2;
		}
		
		/**
		 * @see java.util.Iterator#hasNext()
		 */
		@Override
		public boolean hasNext() {
			return elementsProcessed < noOfPrimes;
		}

		/**
		 * @see java.util.Iterator#next()
		 */
		@Override
		public Integer next() {
			if(!hasNext()){
				throw new NoSuchElementException(
					"Iterator has reached end of the prime collection."
				);
			}
			
			int primeForReturn = currentPrime;
			currentPrime = calculateNextPrime(currentPrime);
			elementsProcessed++;
			
			return primeForReturn;
		}

		/**
		 * Calculates the next prime number bigger than current prime.
		 *
		 * @param currentPrime the current prime number
		 * @return the next prime number
		 */
		private int calculateNextPrime(int currentPrime) {			
			int nextPrime = currentPrime + 1;
			while(true){
				if(isPrime(nextPrime)){
					return nextPrime;
				} 
				nextPrime++;
			}
		}

		/**
		 * Checks if number is prime.
		 *
		 * @param number the tested number
		 * @return {@code true}, if is prime, {@false} otherwise
		 */
		private boolean isPrime(int number) {
			if (number == 2) return true;
			if(number % 2 == 0) return false;
			for (int i = 3; i*i <= number; i += 2){
				if (number % i == 0) return false;
			}
			return true;
		}		
	}
}
