package hr.fer.zemris.java.tecaj.hw5.db.operators;

import hr.fer.zemris.java.tecaj.hw5.db.QueryException;

/**
 * The Class used for checking if one {@link String} parameter matches the pattern of the 
 * second one. In a second parameter character * is used to substitute zero or more characters 
 * in a string. More than one use of * character is illegal and exception is thrown.
 * This is one implementation of {@link IComparisonOperator} strategy.
 * 
 * @author Vjeco
 */
public class LikeOperator implements IComparisonOperator {

	/**
	 * Method returns <code>true</code> {@code value1} matches
	 * the pattern of {@code value2}.
	 *
	 * @param value1 the first string value
	 * @param value2 the second string value, represents pattern
	 * @return <code>true</code>, if matches, <code>false</code> otherwise
	 * @throws QueryException if character * is used more than once
	 */
	@Override
	public boolean satisfied(String value1, String value2) {
		boolean satisfied = croatianCollator.equals(value1, value2);
		
		if(value2.contains("*")){
			 String[] parts = value2.split("\\*");

			 if(parts.length > 2){
				 throw new QueryException(
					"Illegal number of * characters in a string."
				);
			 } else if(value2.equals("*")){
				satisfied = value1.matches(".*");
			 } else if(value2.startsWith("*")){
				 satisfied = value1.matches(".*" + parts[1]);
			 } else if(value2.endsWith("*")){
				 satisfied = value1.matches(parts[0] + ".*");
			 } else {
				 satisfied = value1.matches(parts[0] + ".*" + parts[1]);
			 }
		} 
		
		return satisfied;
	}

}
