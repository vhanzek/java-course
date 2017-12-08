package hr.fer.zemris.java.custom.scripting.parser;

/**
 * The class ParserConstants offers a few string constants and each of them 
 * represents some pattern.
 * 
 * @author Vjeco
 */
public class ParserConstants {
	
	/** 
	 * The Constant INTEGER. 
	 * Represents pattern for number (but not decimal number) in a text.
	 */
	public static final String INTEGER = "-?\\d+";
	
	/** 
	 * The Constant DOUBLE. 
	 * Represents pattern for decimal number in a text.
	 */
	public static final String DOUBLE = "-?\\d+[\\.]\\d+";
	
	/** 
	 * The Constant VARIABLE. 
	 * Represents pattern for valid variable name. Starts with the letter followed 
	 * by letters, numbers or _. 
	 */
	public static final String VARIABLE = "[a-zA-Z][\\w]*";
	
	/** 
	 * The Constant FUNCTION. 
	 * Represents pattern for valid function name. Valid name is same as variable.
	 */
	public static final String FUNCTION = VARIABLE;
	
	/** 
	 * The Constant OPERATOR. 
	 * Represents pattern for any legal operator: +, -, /, *, ^
	 */
	public static final String OPERATOR = "\\+|\\-|\\*|\\/|\\^";
	
	/** 
	 * The Constant STRING. 
	 * Represents pattern for any string. String begin and and with ".
	 */
	public static final String STRING = "\"([^\"]+)\"";
	
	/** 
	 * The Constant INVALID. 
	 * Represents pattern for any invalid character that can occur in tag text.
	 */
	public static final String INVALID = "[^\\w\\s\\+\\-\\*\\/\\^@\"]";
		
	/** 
	 * The Constant VALID. 
	 * Represents pattern for any character that can "break" name of the variable 
	 * or function in tag text.
	 */
	public static final String VALID = "[^@$\"\\s\\+\\-\\*\\/\\^]";
}
