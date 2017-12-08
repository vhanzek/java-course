package hr.fer.zemris.java.custom.scripting.exec;

import java.util.function.BinaryOperator;

/**
 * The class used for wrapping instances of {@link Object} class. It allows the user to perform 
 * a few operations with internal {@link Object} value. Allowed values for current content of 
 * {@link ValueWrapper} object and for argument are {@code null} and instances of {@link Integer}, 
 * {@link Double} and {@link String} classes
 * 
 * <ul>There are four rules:
 * 		<li>if any of current value or argument is {@code null}, it is treated as {@link Integer} with value 0</li>
 * 		<li>if any of current value or argument is instance of {@link String} class, it is treated as {@link Double} 
 * 			if string contains '.' or 'E', otherwise, it is treated as {@link Integer}</li>
 *      <li>if any of current value or argument is instance of classes {@link Integer} or {@link Double}, they are 
 *      	treated as Integer, i.e. Double</li>
 * </ul>
 * 
 * @author Vjeco
 */
public class ValueWrapper {
	
	/** The current value. */
	private Object value;

	/**
	 * Instantiates a new value wrapper with current value 
	 * set to given value.
	 *
	 * @param value the current value
	 */
	public ValueWrapper(Object value) {
		this.value = prepareValue(value);
	}

	/**
	 * Operation for incrementing current value for given value.
	 *
	 * @param incValue the value for incrementing
	 */
	public void increment(Object incValue){
		value = doOperation(incValue, (v1, v2)-> v1 + v2);
	}

	/**
	 * Operation for decrementing current value for given value.
	 *
	 * @param decValue the value for decrementing
	 */
	public void decrement(Object decValue){
		value = doOperation(decValue, (v1, v2)-> v1 - v2);
	}
	
	/**
	 * Operation for multiplying current value with given value.
	 *
	 * @param decValue the value for multiplying
	 */
	public void multiply(Object mulValue){
		value = doOperation(mulValue, (v1, v2)-> v1 * v2);
	}
	
	/**
	 * Operation for dividing current value with given value.
	 *
	 * @param decValue the value for dividing
	 */
	public void divide(Object divValue){
		value = doOperation(divValue, (v1, v2)-> v1 / v2);
	}
	
	/**
	 * Method for comparing current value with given value.
	 *
	 * @param withValue the value for comparing
	 * @return the positive integer if current value is bigger than given value,
	 * 		   0 if they are equal and negative integer otherwise
	 */
	public int numCompare(Object withValue){
		return (value == null && withValue == null) ? 0 : 
				(int) doOperation(withValue,(v1, v2)-> Double.valueOf(v1.compareTo(v2)));
	}

	/**
	 * Method used for performing certain operation, given by parameter function.
	 *
	 * @param other the other operand
	 * @param function the operation to be performed
	 * @return the result, can be Double or Integer
	 */
	private Object doOperation(Object other, BinaryOperator<Double> function) {
		value = prepareValue(value);
		other = prepareValue(other);
		
		double firstOperand;
		double secondOperand;
		
		if(value instanceof Integer){
			firstOperand = (Integer) value;
			if(other instanceof Integer){
				secondOperand = (Integer) other;
				return Integer.valueOf(function.apply(firstOperand, secondOperand).intValue());
			} else {
				secondOperand = (Double) other;
			}
		} else {
			firstOperand = (Double) value;
			if(other instanceof Integer){
				secondOperand = (Integer) other;
			} else {
				secondOperand = (Double) other;
			}
		}
		
		return function.apply(firstOperand, secondOperand);
	}
	
	/**
	 * Method for preparing value. Rules for this method are written in javadoc of this class.
	 *
	 * @param value the value to be casted to Integer or Double class
	 * @return the prepared value
	 */
	private Object prepareValue(Object value) {
		if(value == null){
			value = Integer.valueOf(0);
		} else if (value instanceof String){
			if(((String) value).contains(".") || 
			   ((String) value).toUpperCase().contains("E")){
				try{
					value = Double.parseDouble((String) value);
				} catch (NumberFormatException nfe){
					throw new RuntimeException("Unable to parse Double.");
				}
			} else {
				try{
					value = Integer.parseInt((String) value);
				} catch (NumberFormatException nfe){
					throw new RuntimeException("Unable to parse Integer.");
				}
			}
		} else if (value instanceof Double){
			value = (Double) value;
		} else if (value instanceof Integer){
			value = (Integer) value;
		} else {
			throw new RuntimeException(
				"Given value is an object of illegal type."
			);
		}
		
		return value;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(Object value) {
		this.value = value;
	}	
}
