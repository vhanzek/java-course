package hr.fer.zemris.java.tecaj.hw1;

import static java.lang.Math.*;

/**
 * The Class Roots used for calculating complex number's root(s).
 * @author Vjeco
 */
public class Roots {

	/**
	 * Method that starts the program.
	 *
	 * @param args three arguments are used. 
	 * First two represents real and imaginary part of the imaginary number. 
	 * The third represents which root of the imaginary number needs to be calculated.
	 */
	public static void main(String[] args) {
		double real = Double.parseDouble(args[0]);
		double imaginary = Double.parseDouble(args[1]);
		int root = Integer.parseInt(args[2]);
		int noOfRoots = 0;
		
		double r = sqrt(pow(real,2) + pow(imaginary, 2));
		double alfa = atan(imaginary/real);
		
		System.out.format("You requested calculation of %d. roots. Solutions are:%n", root); 
		for (int k = 0; k < root; k++){
			real = pow(r, (double) 1 / root) * cos((alfa + 2 * k * PI)/root);
			imaginary = pow(r, (double) 1 / root) * sin((alfa + 2 * k * PI)/root);
			System.out.printf("%d) %.2f %s %.2fi%n", ++noOfRoots, real, imaginary > 0 ? " + " : " - ", abs(imaginary));
		}		
	}
}
