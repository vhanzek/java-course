package hr.fer.zemris.java.tecaj.hw5.db;

import hr.fer.zemris.java.tecaj.hw5.db.fields.IFieldValueGetter;
import hr.fer.zemris.java.tecaj.hw5.db.operators.IComparisonOperator;

/**
 * The class representing one conditional expression in a query like 'firstName="Ada"'.
 * One or more conditional expressions are used to filter the database.
 * 
 * <ol>There are three parameters: 
 * 		<li>{@code fieldValueGetter}: implements {@link IFieldValueGetter}. 
 * 									  There are 4 implementations, depending on attribute's type.</li>
 * 		<li>{@code comparisonOperator}: implements {@link IComparisonOperator}</li>
 * 										There are 7 implementations, depending on operator's type.</li> 
 * 		<li>{@code stringLiteral} : instance of {@link String} class.</li>
 * </ol>
 * 
 * @author Vjeco
 */
public class ConditionalExpression {
	
	/** The field value getter. */
	private IFieldValueGetter fieldValueGetter;
	
	/** The comparison operator. */
	private IComparisonOperator comparisonOperator;
	
	/** The string literal. */
	private String stringLiteral;
	
	/**
	 * Instantiates a new conditional expression.
	 *
	 * @param fieldValueGetter the field value getter
	 * @param comparisonOperator the comparison operator
	 * @param stringLiteral the string literal
	 */
	public ConditionalExpression(IFieldValueGetter fieldValueGetter, IComparisonOperator comparisonOperator,
			String stringLiteral) {
		this.fieldValueGetter = fieldValueGetter;
		this.comparisonOperator = comparisonOperator;
		this.stringLiteral = stringLiteral;
	}

	/**
	 * Gets the field value getter.
	 *
	 * @return the field value getter
	 */
	public IFieldValueGetter getFieldValueGetter() {
		return fieldValueGetter;
	}

	/**
	 * Gets the comparison operator.
	 *
	 * @return the comparison operator
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}

	/**
	 * Gets the string literal.
	 *
	 * @return the string literal
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}	
	
}
