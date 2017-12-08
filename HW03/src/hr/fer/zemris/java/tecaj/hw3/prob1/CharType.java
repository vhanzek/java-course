package hr.fer.zemris.java.tecaj.hw3.prob1;

/**
 * The Enumeration CharType represents type of characters. There are 5 different types.
 * 
 * @author Vjeco
 */
public enum CharType {
	
	/** Representing a letter. */
	LETTER,
	
	/** Representing a digit. */
	DIGIT,
	
	/** Representing any character that is not letter, digit or whitespace. */
	SYMBOL,
	
	/** Representing all whitespaces. */
	WHITESPACE,
	
	/** Representing end of the string. */
	EOF;
}
