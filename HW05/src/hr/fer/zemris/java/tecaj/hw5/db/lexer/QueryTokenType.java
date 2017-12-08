package hr.fer.zemris.java.tecaj.hw5.db.lexer;

/**
 * The enumeration which represents type of the {@link QueryToken}. Every type has its 
 * {@link String} value - pattern used in regular expressions.
 * 
 * @author Vjeco
 */
public enum QueryTokenType {
	
	/** The attribute of the student (last name, first name and JMBAG). */
	ATTRIBUTE("lastName|firstName|jmbag"),
	
	/** The comparison operator. */
	COMPARISON_OPERATOR("<=|>=|<|>|=|!=|LIKE"),
	
	/** The logical operator, only AND is supported. */
	LOGICAL_OPERATOR("(?i)and(?-i)"),
	
	/** The string literal. */
	STRING("\"([^\"]+)\"");
	
	/** 
	 * The pattern of token type. 
	 * Used for recognition of the token types in input text.
	 */
	private final String regex;
		
	/**
	 * Instantiates a new token type.
	 *
	 * @param regex the pattern of query token type
	 */
	private QueryTokenType(String regex) {
		this.regex = regex;
	}

	/**
	 * Gets the pattern of query token type.
	 *
	 * @return the pattern of query token type
	 */
	public String getRegex() {
		return regex;
	}
}
