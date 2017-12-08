package hr.fer.zemris.java.custom.scripting.elems;

/**
 * The Class ElementOperator represents mathematical operator in the text. There are 5 legal operators: +, -, /, *, ^.
 * 
 * @author Vjeco
 */
public class ElementOperator extends Element {
	
	/** The symbol of the operator. */
	private String symbol;

	/**
	 * Instantiates a new operator element.
	 *
	 * @param symbol the symbol of the operator
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.custom.scripting.elems.Element#asText()
	 */
	@Override
	public String asText(){
		return symbol;
	}

	/**
	 * Gets the symbol of the operator.
	 *
	 * @return the symbol of the operator
	 */
	public String getSymbol() {
		return symbol;
	}
}
