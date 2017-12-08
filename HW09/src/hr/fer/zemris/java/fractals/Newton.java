package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.zemris.java.complex.Complex;
import hr.zemris.java.complex.ComplexPolynomial;
import hr.zemris.java.complex.ComplexRootedPolynomial;

/**
 * The class is used for calculating fractals using Newton-Rapshon iteration. All 
 * of the work is divided into smaller pieces, and they are forwarded to an 
 * implementation of the {@link ExecutorService} interface. 
 * 
 * @see http://www.chiark.greenend.org.uk/~sgtatham/newton/
 * 
 * @author Vjeco
 */
public class Newton {
	
	/** The root treshold. */
	private final static double ROOT_TRESHOLD = 2E-3;
	
	/** The convergence treshold. */
	private final static double CONVERGENCE_TRESHOLD = 1E-3;
	
	/** The rooted complex polynomial. */
	private static ComplexRootedPolynomial polynomial;
	
	/** The derived complex polynomial. */
	private static ComplexPolynomial derived;

	/**
	 * The main method. Program starts here. At the beginning of the program,
	 * user is asked for arbitrary number of roots of the complex polynomial.
	 * Based on that polynomial, using Newton-Rapshon iterative method, fractal
	 * is drawn and shown to user.
	 *
	 * @param args the command line arguments. Unused in this example.
	 */
	public static void main(String[] args) {
		Complex[] roots = getRoots();
		FractalViewer.show(new NewtonFractalProducer(new ComplexRootedPolynomial(roots)));
	}

	/**
	 * The Class CalculationWork.
	 */
	public static class CalculationWork implements Callable<Void> {
		
		/** The minimal real part of the complex number. */
		double reMin;
		
		/** The maximal real part of the complex number. */
		double reMax;
		
		/** The minimal imaginary part of the complex number. */
		double imMin;
		
		/** The maximal imaginary part of the complex number. */
		double imMax;
		
		/** The width of the frame. */
		int width;
		
		/** The height of the frame. */
		int height;
		
		/** The minimal y-coordinate. */
		int yMin;
		
		/** The maximal y-coordinate. */
		int yMax;
		
		/** The maximal number of iterations. */
		int m;
		
		/** The data containing every pixel. */
		short[] data;

		/**
		 * Instantiates a new fractal calculation work
		 *
		 * @param reMin the minimal real part of the complex number
		 * @param reMax the maximal real part of the complex number
		 * @param imMin the minimal imaginary part of the complex number
		 * @param imMax the maximal imaginary part of the complex number
		 * @param width the width of the frame
		 * @param height the height of the frame
		 * @param yMin the minimal y-coordinate
		 * @param yMax the maximal y-coordinate
		 * @param m the maximal number of iterations
		 * @param data the data with pixels
		 */
		public CalculationWork(double reMin, double reMax, double imMin, double imMax, int width, 
							   int height, int yMin, int yMax, int m, short[] data) {
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
		}
		
		/**
		 * @see java.util.concurrent.Callable#call()
		 */
		@Override
		public Void call() {
			calculate(reMin, reMax, imMin, imMax, width, height, m, yMin, yMax, data);
			return null;
		}
	}
	
	/**
	 * The implementation of the interface {@link IFractalProducer} which knows how to
	 * generate data for visualization of the fractal
	 */
	public static class NewtonFractalProducer implements IFractalProducer{
		
		/** The thread pool. */
		private ExecutorService pool;
		
		/**
		 * Instantiates a new fractal producer.
		 *
		 * @param poly the complex polynomial
		 */
		public NewtonFractalProducer(ComplexRootedPolynomial poly) {
			pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 8, 
												new DaemonicThreadFactory());
			polynomial = poly;
			derived = polynomial.toComplexPolynom().derive();
		}
		
		/**
		 * @see hr.fer.zemris.java.fractals.viewer.IFractalProducer#produce(double, double, double, double, int, int, long, hr.fer.zemris.java.fractals.viewer.IFractalResultObserver)
		 */
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height,
							long requestNo, IFractalResultObserver observer) {
			int m = (int) Math.pow(16, 2);
			short[] data = new short[width * height];
			final int numberOfTracks = Runtime.getRuntime().availableProcessors() * 8;
			int YByTrack = height/numberOfTracks;
			
			List<Future<Void>> results = new ArrayList<>();
			
			for(int i = 0; i < numberOfTracks; i++){
				int yMin = i * YByTrack; 
				int yMax = (i+1) * YByTrack - 1;
				if(i == numberOfTracks - 1) {
					yMax = height - 1;
				}
				
				CalculationWork work = new CalculationWork(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data);
				results.add(pool.submit(work));
			}
			
			for (Future<Void> result : results){
				try {
					result.get();
				} catch (InterruptedException | ExecutionException e) {
				}
			}
			
			observer.acceptResult(data, (short)(polynomial.toComplexPolynom().order() + 1), requestNo);
		}
	}
	
	/**
	 * Method used for calculating color for every pixel in a frame
	 * using Newton-Rapshon iteration.
	 *
	 * @param reMin the minimal real part of the complex number
	 * @param reMax the maximal real part of the complex number
	 * @param imMin the minimal imaginary part of the complex number
	 * @param imMax the maximal imaginary part of the complex number
	 * @param width the width of the frame
	 * @param height the height of the frame
	 * @param yMin the minimal y-coordinate
	 * @param yMax the maximal y-coordinate
	 * @param m the maximal number of iterations
	 * @param data the data with pixels
	 */
	public static void calculate(double reMin, double reMax, double imMin, double imMax, int width, int height, int m, int ymin, int ymax, short[] data) {		
	    int offset = ymin * width;
	    for (int y = ymin; y <= ymax; y++) {
	    	for (int x = 0; x < width; x++) {
	    		Complex c = mapToComplexPlain(x, y, width, ymin, height, reMin, reMax, imMin, imMax);
	    		Complex zn = c;
	    		Complex zn1;
	            
	    		int iter = 0;
	            double module = 0.0;
	            
				do {
	        		Complex numerator = polynomial.apply(zn);
	        		Complex denominator = derived.apply(zn);
	        		Complex fraction = numerator.divide(denominator);
	        		zn1 = zn.sub(fraction);
	        		module = zn1.sub(zn).module();
	        		zn = zn1;
	            	iter++;
	            } while (module > CONVERGENCE_TRESHOLD && iter < m);
				
				int index = polynomial.indexOfClosestRootFor(zn1, ROOT_TRESHOLD);
				data[offset++] = (short) (index == -1 ? 0 : index);
	    	}
	    }
	}
	
	/**
	 * Method for getting current complex number based on coordinates x and y.
	 *
	 * @param x the current x-coordinate
	 * @param y the current y-coordinate
	 * @param width the width of the frame
	 * @param ymin the minimal y-coordinate
	 * @param height the height of the frame
	 * @param reMin the minimal real part of the complex number
	 * @param reMax the maximal real part of the complex number
	 * @param imMin the minimal imaginary part of the complex number
	 * @param imMax the maximal imaginary part of the complex number
	 * @return the current complex number
	 */
	private static Complex mapToComplexPlain(int x, int y, int width, int ymin, int height, double reMin, double reMax, double imMin, double imMax){
		double cre = x / (width - 1.0D) * (reMax - reMin) + reMin;
		double cim = (height - 1 - y) / (height - 1.0D) * (imMax - imMin) + imMin;
		return new Complex(cre, cim);
	}

	/**
	 * Private helper method for input of the complex polynomial roots.
	 *
	 * @return the roots of the complex polynomial
	 */
	private static Complex[] getRoots() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer. "
				         + "Please enter at least two roots, one root per line. Enter 'done' when done.");
		
		List<Complex> roots = new ArrayList<>();
		int counter = 1;
		String input = "";
		
		while(true) {
			System.out.print("Root " + counter++ + "> ");
			input = sc.nextLine().trim();
			if(input.isEmpty()){ counter--; continue; }
			if(input.toLowerCase().equals("done"))  break; 
			else roots.add(Complex.parse(input));	
		} 
		
		System.out.println("Image of fractal will appear shortly. Thank you.");
		sc.close();
		
		Complex[] rootsArray = new Complex[roots.size()];
		rootsArray = roots.toArray(rootsArray);
		return rootsArray;
	}
}

