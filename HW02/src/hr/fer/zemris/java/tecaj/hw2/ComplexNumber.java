package hr.fer.zemris.java.tecaj.hw2;

import static java.lang.Math.*;
import static java.lang.Double.*;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Class ComplexNumber used for working with complex numbers, mostly operations between two complex numbers. 
 * This class supports normal and polar form of the complex number.
 * 
 * @author Vjeco
 */
public class ComplexNumber {
	
	/** The real part of complex number. */
	private double real;
	
	/** The imaginary part of complex number. */
	private double imaginary;
	
	/** The magnitude of the complex number */
	private double magnitude;
	
	/** The angle of the complex number. */
	private double angle;
	
	/**
	 * Instantiates a new complex number with real and imaginary part given by parameters. 
	 * Also, calculates magnitude and angle of the complex number.
	 *
	 * @param real the real part of complex number
	 * @param imaginary the imaginary part of complex number
	 */
	public ComplexNumber(double real, double imaginary){
		this.real = real;
		this.imaginary = imaginary;
		magnitude = sqrt(pow(real,2) + pow(imaginary, 2));
		angle = imaginary != 0 ? atan2(imaginary, real) : 0;
	}
	
	/**
	 * Returns new complex number instance with imaginary part set to 0 and real part set to value given by parameter.
	 *
	 * @param real the real part of the complex number
	 * @return the instance of the complex number
	 */
	public static ComplexNumber fromReal(double real){
		return new ComplexNumber(real, 0);
	}
	
	/**
	 * Returns new complex number instance with imaginary part set to  value given by parameter and real part set to 0.
	 *
	 * @param imaginary the imaginary part of the complex number
	 * @return the instance of the complex number
	 */
	public static ComplexNumber fromImaginary(double imaginary){
		return new ComplexNumber(0, imaginary);
	}
	
	/**
	 * Returns new ComplexNumber instance with given values of magnitude and angle.
	 * Angle is in range of [0, 2*PI].
	 *
	 * @param magnitude the magnitude of the number
	 * @param angle the angle the number
	 * @return the complex number with given values
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle){		
		while(angle < 0){ angle += 2*PI; }
		while(angle > 2*PI){ angle -= 2*PI; }
		
		return new ComplexNumber(magnitude * cos(angle), magnitude * sin(angle));
	}
	
	/**
	 * Parses given string to real and imaginary part of the complex number.
	 *
	 * @param s the string for parsing
	 * @return the complex number obtained with parsing the given string
	 */
	public static ComplexNumber parse(String s){
		double real = 0.0;
		double imaginary = 0.0;
		
		s = s.trim();
		String[] plusMinusSplit = s.split("\\-|\\+", 0);
		
		//removes empty string at the start
		if(plusMinusSplit[0].equals("")){
			plusMinusSplit = Arrays.copyOfRange(plusMinusSplit, 1, plusMinusSplit.length);
		}
		
		int len = plusMinusSplit.length;
		
		if(len > 2 || (!s.contains("+") && !s.contains("-"))){
			throw new IllegalArgumentException(
				"Illegal string for parsing."
			);
		} else if(len == 2){		//checks if string has both real and imaginary part
			Matcher matcher = Pattern.compile("(-?\\d+[\\.]?\\d*)([+-]?\\d+[\\.]?\\d*)i?").matcher(s);
			
			if(matcher.matches()){
				real = parseDouble(matcher.group(1));
				imaginary = parseDouble(matcher.group(2));
			} 
		} else if(s.contains("i")){	//checks if string is imaginary part
			if(s.equals("i")){
				imaginary = 1;
			} else if (s.equals("-i")){
				imaginary = -1;
			} else {
				imaginary = parseDouble(s.substring(0, s.length()-1));
			}
		} else {					//string represents real part
			real = parseDouble(s);
		}
		
		return new ComplexNumber(real, imaginary);
	}
	
	/**
	 * Operation for adding two complex numbers.
	 *
	 * @param c the the other member in this operation
	 * @return the complex number got as a result of this operation
	 */
	public ComplexNumber add(ComplexNumber c){
		return new ComplexNumber(real + c.getReal(), imaginary + c.getImaginary());
	}
	
	/**
	 * Operation for subtracting two complex numbers.
	 *
	 * @param c the other member in this operation
	 * @return the complex number got as a result of this operation
	 */
	public ComplexNumber sub(ComplexNumber c){
		return new ComplexNumber(real - c.getReal(), imaginary - c.getImaginary());
	}
	
	/**
	 * Operation for multiplying two complex numbers.
	 *
	 * @param c the other member in this operation
	 * @return the complex number got as a result of this operation
	 */
	public ComplexNumber mul(ComplexNumber c){
		double cReal = c.getReal();
		double cImaginary = c.getImaginary();
		
		double realMul = real*cReal - imaginary*cImaginary;
		double imaginaryMul = real*cImaginary + imaginary*cReal;
		
		return new ComplexNumber(realMul, imaginaryMul);
	}
	
	/**
	 * Operation for dividing two complex numbers.
	 *
	 * @param c the other member in this operation
	 * @return the complex number got as a result of this operation
	 */
	public ComplexNumber div(ComplexNumber c){
		double cReal = c.getReal();
		double cImaginary = c.getImaginary();
		double denominator = cReal*cReal + cImaginary*cImaginary;
		
		double realDiv = (real*cReal + imaginary*cImaginary)/denominator;
		double imaginaryDiv = (imaginary*cReal - real*cImaginary)/denominator;
		
		return new ComplexNumber(realDiv, imaginaryDiv);
	}
	
	/**
	 * Returns complex number raised to a specified power given by parameter.
	 * Method throws IllegalArgumentException if power is negative.
	 *
	 * @param n the n-th power
	 * @return the complex number raised to a power of n
	 */
	public ComplexNumber power(int n){
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
	 * @return ComplexNumber[] array filled with calculated roots of the complex number
	 */
	public ComplexNumber[] root(int n){
		if (n <= 0){
			throw new IllegalArgumentException(
				"Root is negative."
			);
		}
		
		ComplexNumber[] roots = new ComplexNumber[n];
		
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
		String complex = String.format("%s %s %s",real != 0 ? df.format(real) : "", 
												  imaginary > 0 ? "+" : imaginary == 0 ? "" : "-", 
												  imaginary != 0 ? (imaginary == 1 || imaginary == -1 ? "i" : df.format(abs(imaginary)) + "i") : "");
		
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
	
}
