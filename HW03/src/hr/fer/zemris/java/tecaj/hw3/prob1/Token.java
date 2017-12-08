package hr.fer.zemris.java.tecaj.hw3.prob1;

/**
 * The Class Token represents text element i.e. one part of the input text. There are 4 {@link TokenType}.
 * Type of token's value for WORD is a string, for NUMBER is integer and for SYMBOL is character.
 * EOF token always has <code>value = null</code>.
 * 
 * @author Vjeco
 */
public class Token {
	
	/** The type of the token. */
	private TokenType type;
	
	/** The value of the token. */
	private Object value;
	
	/**
	 * Instantiates a new token and initialize it with given type and value.
	 *
	 * @param type the type
	 * @param value the value
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Gets the value of the token.
	 *
	 * @return the value of the token
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Gets the type of the token.
	 *
	 * @return the type of the token
	 */
	public TokenType getType() {
		return type;
	}
	
	/* @see java.lang.Object#toString() */
	@Override
	public String toString(){
		return ("(" + getType() + ", " + getValue() + ")");
	}
}
