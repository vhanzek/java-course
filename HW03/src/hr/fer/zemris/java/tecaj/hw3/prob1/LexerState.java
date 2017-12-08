package hr.fer.zemris.java.tecaj.hw3.prob1;

/**
 * The Enumeration LexerState represents two {@link Lexer} states: BASIC and EXTENDED.
 * 
 * @author Vjeco
 */
public enum LexerState {
	
	/** The basic state. Lexer is initially in this state. */
	BASIC,
	
	/** The extended state. Begins and ends with '#' */
	EXTENDED;

}
