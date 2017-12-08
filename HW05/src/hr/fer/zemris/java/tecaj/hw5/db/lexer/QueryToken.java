package hr.fer.zemris.java.tecaj.hw5.db.lexer;

/**
 * The class representing one token that {@link QueryLexer} produces in its lexical analysis.
 * There are 4 {@link QueryTokenType}s. This token has two variables, {@code value} and {@code type}.
 * 
 * @author Vjeco
 */
public class QueryToken {
	
	/** The string value of the token. */
	private final String value;
	
	/** The type of the token. */
	private final QueryTokenType type;
	
	/**
	 * Instantiates a new query token.
	 *
	 * @param value the value of the token
	 * @param type the type of the token
	 */
	public QueryToken(String value, QueryTokenType type) {
		this.value = value;
		this.type = type;
	}

	/**
	 * Gets the value of the token.
	 *
	 * @return the value of the token
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Gets the type of the token.
	 *
	 * @return the type of the token
	 */
	public QueryTokenType getType() {
		return type;
	}	
}
