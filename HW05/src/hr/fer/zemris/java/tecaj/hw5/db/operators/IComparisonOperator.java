package hr.fer.zemris.java.tecaj.hw5.db.operators;

import java.text.Collator;
import java.util.Locale;

/**
 * The strategy IComparisonOperator which has 7 implementations.
 * Each of them, returns different result depending on the operator's type.
 * Because of the characters used in croatian language, instance of {@link Collator} class
 * is used for comparing two string instance of {@link Collator}.
 * 
 * <ol>All of the implementations are listed below:
 * 		<li>{@link EqualsOperator}</li>
 * 		<li>{@link NotEqualOperator}</li>
 * 		<li>{@link GreaterThanOperator}</li>
 * 		<li>{@link GreaterThanOrEqualOperator}</li> 
 * 		<li>{@link LessThanOperator}</li>
 * 		<li>{@link LessThanOrEqualOperator}</li>
 * 		<li>{@link LikeOperator}</li>
 * </ol>
 * 
 * @author Vjeco
 */
public interface IComparisonOperator {
	
	/**
	 * The collator for croatian locale used in the implementations of this interface.
	 */
	static Collator croatianCollator = Collator.getInstance(new Locale("hr", "HR"));
	
	/**
	 * Method returns <code>true</code> if relation between two 
	 * string parameters is satisfied. Relation between them changes
	 * depending on the implementation.
	 *
	 * @param value1 the first string value
	 * @param value2 the second string value
	 * @return <code>true</code>, if relation is satisfied
	 */
	public boolean satisfied(String value1, String value2);
}
