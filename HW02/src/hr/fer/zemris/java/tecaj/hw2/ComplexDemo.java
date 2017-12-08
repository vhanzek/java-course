package hr.fer.zemris.java.tecaj.hw2;

/**
 * Example for working with ComplexNumber class.
 * 
 * @author Vjeco
 */
public class ComplexDemo {

	/**
	 * Method that starts with the beginning of the program.
	 * 
	 * @param args command line arguments. Unused in this example.
	 */
	public static void main(String[] args) {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		ComplexNumber c2 = ComplexNumber.parse("2.5-3i");
		ComplexNumber c3 = c1.add(ComplexNumber.fromMagnitudeAndAngle(2, 1.57))
							 .div(c2).power(3).root(2)[1];
		System.out.println(c3);
	}

}
