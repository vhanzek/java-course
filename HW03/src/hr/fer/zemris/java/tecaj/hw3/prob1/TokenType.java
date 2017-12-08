package hr.fer.zemris.java.tecaj.hw3.prob1;

/**
 * The Enumeration TokenType represent type of class {@link TokenType}. There are 4 token types.
 * 
 * @author Vjeco
 */
public enum TokenType {
	
	/** Representing end of the string. */
	EOF,
	
	/** Represents consecutive char sequence of letters (can be digits if escape \ is used). */
	WORD, 
	
	/** Represents consecutive char sequence of digits. */
	NUMBER,
	
	/** Representing any character that is not letter, digit or whitespace. */
	SYMBOL;
}
