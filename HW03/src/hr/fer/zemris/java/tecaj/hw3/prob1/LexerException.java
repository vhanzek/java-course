package hr.fer.zemris.java.tecaj.hw3.prob1;


/**
 * The Class LexerException used when error occurs in {@link Lexer} class during the lexical analysis of input text.
 * 
 * @author Vjeco
 */
public class LexerException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new lexer exception.
	 *
	 * @param message the exception message
	 */
	public LexerException(String message){
		super(message);
	}

}
