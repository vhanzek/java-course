package hr.fer.zemris.java.tecaj.hw5.db.operators;

/**
 * The Class used for checking equality of two {@link String} parameters.
 * This is one implementation of {@link IComparisonOperator} strategy.
 * 
 * @author Vjeco
 */
public class EqualsOperator implements IComparisonOperator {
	/**
	 * Method returns <code>true</code> {@code value1} is equal
	 * to {@code value2}.
	 *
	 * @param value1 the first string value
	 * @param value2 the second string value
	 * @return <code>true</code>, if are equal
	 */
	@Override
	public boolean satisfied(String value1, String value2) {
		return croatianCollator.equals(value1, value2);
	}

}
