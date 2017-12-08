package hr.zemris.java.complex;

import static java.lang.Double.parseDouble;
import static java.lang.Math.*;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class representing implementation of complex number. It offers methods for various operations
 * with complex numbers. This class also supports polar form of a complex number.
 * 
 * @author Vjeco
 */
public class Complex {
	/** Complex number with both real and imaginary parts set to 0.*/
	public static final Complex ZERO = new Complex(0,0);
	
	/** Complex number with real part set to 1 and imaginary part set to 0.*/
	public static final Complex ONE = new Complex(1,0);
	
	/** Complex number with real part set to -1 and imaginary part set to 0.*/
	public static final Complex ONE_NEG = new Complex(-1,0);
	
	/** Complex number with real part set to 0 and imaginary part set to 1.*/
	public static final Complex IM = new Complex(0,1);
	
	/** Complex number with real part set to 0 and imaginary part set to -1.*/
	public static final Complex IM_NEG = new Complex(0,-1);
	
	/** The real part of complex number. */
	private double real;
	
	/** The imaginary part of complex number. */
	private double imaginary;
	
	/** The magnitude of the complex number */
	private double magnitude;
	
	/** The angle of the complex number. */
	private double angle;
	
	/**
	 * Instantiates a new complex number with real and imaginary parts set to 0.
	 */
	public Complex(){
		this(0, 0);
	}
	
	/**
	 * Instantiates a new complex number with real and imaginary part given by parameters. 
	 *
	 * @param real the real part of complex number
	 * @param imaginary the imaginary part of complex number
	 */
	public Complex(double real, double imaginary){
		this.real = real;
		this.imaginary = imaginary;
		magnitude = sqrt(pow(real,2) + pow(imaginary, 2));
		angle = imaginary != 0 ? atan2(imaginary, real) : 0;
	}
	
	/**
	 * Returns new ComplexNumber instance with given values of magnitude and angle.
	 * Angle is in range of [0, 2*PI].
	 *
	 * @param magnitude the magnitude of the number
	 * @param angle the angle the number
	 * @return the complex number with given values
	 */
	public static Complex fromMagnitudeAndAngle(double magnitude, double angle){		
		while(angle < 0){ angle += 2*PI; }
		while(angle > 2*PI){ angle -= 2*PI; }
		
		return new Complex(magnitude * cos(angle), magnitude * sin(angle));
	}
	
	/**
	 * Returns the module of this complex number.
	 * 
	 * @return the module of this complex number
	 */
	public double module(){
		return sqrt(real*real + imaginary*imaginary);
	}
	
	/**
	 * Parses given string to real and imaginary part of the complex number.
	 *
	 * @param s the string for parsing
	 * @return the complex number obtained with parsing the given string
	 */
	public static Complex parse(String s){
		double real = 0.0;
		double imaginary = 0.0;
		
		s = s.trim();
		String[] plusMinusSplit = s.split("\\-|\\+", 0);
		
		//removes empty string at the start
		if(plusMinusSplit[0].equals("")){
			plusMinusSplit = Arrays.copyOfRange(plusMinusSplit, 1, plusMinusSplit.length);
		}
		
		int len = plusMinusSplit.length;
		
		if(len > 2){
			throw new IllegalArgumentException(
				"Illegal string for parsing."
			);
		} else if(len == 2){		//checks if string has both real and imaginary part
			Matcher matcher = Pattern.compile("(-?\\d+[\\.]?\\d*)([+-]?)i?(\\d+[\\.]?\\d*)").matcher(s);
			
			if(matcher.matches()){
				real = parseDouble(matcher.group(1));
				imaginary = parseDouble(matcher.group(3));
				if(matcher.group(2).equals("-")){
					imaginary = -1*imaginary;
				}
			} 
		} else if(s.contains("i")){	//checks if string is imaginary part
			if(s.equals("i")){
				imaginary = 1;
			} else if (s.equals("-i")){
				imaginary = -1;
			} else {
				if (s.startsWith("-")){
					imaginary = -parseDouble(s.substring(2, s.length()));
				} else if (s.startsWith("+")){
					imaginary = parseDouble(s.substring(2, s.length()));
				} else {
					imaginary = parseDouble(s.substring(1, s.length()));
				}
				
			}
		} else {					
			real = parseDouble(s);	//string represents real part
		}
		
		return new Complex(real, imaginary);
	}
	
	/**
	 * Operation for adding two complex numbers.
	 *
	 * @param c the the other member in this operation
	 * @return the complex number got as a result of this operation
	 */
	public Complex add(Complex c){
		return new Complex(real + c.getReal(), imaginary + c.getImaginary());
	}
	
	/**
	 * Operation for subtracting two complex numbers.
	 *
	 * @param c the other member in this operation
	 * @return the complex number got as a result of this operation
	 */
	public Complex sub(Complex c){
		return new Complex(real - c.getReal(), imaginary - c.getImaginary());
	}
	
	/**
	 * Returns this complex number negated.
	 * 
	 * @return negated complex number
	 */
	public Complex negate(){
		return new Complex(-getReal(), -getImaginary());
	}
	
	/**
	 * Operation for multiplying two complex numbers.
	 *
	 * @param c the other member in this operation
	 * @return the complex number got as a result of this operation
	 */
	public Complex multiply(Complex c){
		double cReal = c.getReal();
		double cImaginary = c.getImaginary();
		
		double realMul = real*cReal - imaginary*cImaginary;
		double imaginaryMul = real*cImaginary + imaginary*cReal;
		
		return new Complex(realMul, imaginaryMul);
	}
	
	/**
	 * Operation for dividing two complex numbers.
	 *
	 * @param c the other member in this operation
	 * @return the complex number got as a result of this operation
	 */
	public Complex divide(Complex c){
		double cReal = c.getReal();
		double cImaginary = c.getImaginary();
		double denominator = cReal*cReal + cImaginary*cImaginary;
		
		double realDiv = (real*cReal + imaginary*cImaginary)/denominator;
		double imaginaryDiv = (imaginary*cReal - real*cImaginary)/denominator;
		
		return new Complex(realDiv, imaginaryDiv);
	}
	
	/**
	 * Returns complex number raised to a specified power given by parameter.
	 * Method throws IllegalArgumentException if power is negative.
	 *
	 * @param n the n-th power
	 * @return the complex number raised to a power of n
	 */
	public Complex power(int n){
		if(n < 0){
			throw new IllegalArgumentException(
				"Power is negative."
			);
		}
		return fromMagnitudeAndAngle(pow(getMagnitude(), n), getAngle()*n);
	}

	/**
	 * Returns n roots of the complex number
	 * Method throws IllegalArgumentException if root is negative or zero.
	 *
	 * @param n the n-th root
	 * @return Complex[] array filled with calculated roots of the complex number
	 */
	public Complex[] root(int n){
		if (n <= 0){
			throw new IllegalArgumentException(
				"Root is negative."
			);
		}
		
		Complex[] roots = new Complex[n];
		
		for(int k = 0; k < n; k++){
			roots[k] = fromMagnitudeAndAngle(pow(getMagnitude(), 1.0/n), (getAngle() + 2*k*PI)/n);
		}
		
		return roots;			
	}

	/**
	 * Returns complex number as a string.
	 * 
	 * @return string of the complex number
	 */
	@Override
	public String toString(){
		DecimalFormat df = new DecimalFormat();
		
		String complex = "";
		if(this.equals(Complex.ZERO)) {
			complex = "0";
		} else {
			complex = String.format("%s%s%s", real != 0 ? df.format(real) : "", 
					 						  imaginary > 0 ? "+" : imaginary == 0 ? "" : "-", 
					 						  imaginary != 0 ? (imaginary == 1 || imaginary == -1 ? "i" : df.format(abs(imaginary)) + "i") : "");
		}

		return complex;
	}
	
	/**
	 * Gets the real part of complex number.
	 *
	 * @return the real part of complex number
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Gets the imaginary part of complex number.
	 *
	 * @return the imaginary part of complex number
	 */
	public double getImaginary() {
		return imaginary;
	}
	
	/**
	 * Gets the magnitude of complex number.
	 *
	 * @return the magnitude of complex number
	 */
	public double getMagnitude() {
		return magnitude;
	}

	/**
	 * Gets the angle of complex number.
	 *
	 * @return the angle of complex number
	 */
	public double getAngle() {
		return angle;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(imaginary);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(real);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Complex))
			return false;
		Complex other = (Complex) obj;
		if (Double.doubleToLongBits(imaginary) != Double.doubleToLongBits(other.imaginary))
			return false;
		if (Double.doubleToLongBits(real) != Double.doubleToLongBits(other.real))
			return false;
		return true;
	}
}
