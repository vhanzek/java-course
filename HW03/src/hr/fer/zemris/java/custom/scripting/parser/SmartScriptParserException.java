package hr.fer.zemris.java.custom.scripting.parser;

/**
 * The Class SmartScriptParserException used when error occurs in {@link SmartScriptParser} during the parsing of document.
 */
public class SmartScriptParserException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new smart script parser exception.
	 *
	 * @param message the exception message
	 */
	public SmartScriptParserException(String message){
		super(message);
	}
}
