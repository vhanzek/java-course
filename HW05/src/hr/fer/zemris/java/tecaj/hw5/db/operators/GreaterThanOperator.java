package hr.fer.zemris.java.tecaj.hw5.db.operators;

/**
 * The Class used for checking one {@link String} parameter is greater than the other.
 * This is one implementation of {@link IComparisonOperator} strategy.
 * 
 * @author Vjeco
 */
public class GreaterThanOperator implements IComparisonOperator {

	/**
	 * Method returns <code>true</code> {@code value1} is 
	 * greater than {@code value2}.
	 *
	 * @param value1 the first string value
	 * @param value2 the second string value
	 * @return <code>true</code>, if {@code value1} is greater
	 */
	@Override
	public boolean satisfied(String value1, String value2) {
		return croatianCollator.compare(value1, value2) > 0;
	}

}
