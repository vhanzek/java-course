package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.Objects;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * The Class Lexer used for separating input text to two {@link TokenType}s - TEXT and TAG. 
 * Type EOF is used when lexer reaches end of the input text.
 * 
 * @author Vjeco
 */
public class Lexer {

	/** The input text. */
	private String text; 
	
	/** The current index (character) in input text. */
	private int currentIndex;
	
	/** The current token type, can be interpreted as lexer current state. */
	private TokenType currentTokenType;
	
	/** The variable for checking if lexer has reached end of the string. */
	private boolean finished;
	
	/**
	 * The Enum TokenType represents 3 different types of tokens that can appear in a text.
	 * 
	 * @author Vjeco 
	 */
	public enum TokenType{
		
		/** Document text, everything outside of tags. */
		TEXT,
		
		/** Part of the string that begins with {$ and ends with $} */
		TAG,
		
		/** Used when end of the text is reached. */
		EOF;
	}
	
	/**
	 * The Class Token represents one part of the text that lexical analyser generated.
	 * 
	 * @author Vjeco
	 */
	public static class Token{
		
		/** The value of the token. */
		String value;
		
		/** The type of the token. */
		TokenType type;
		
		/**
		 * Instantiates a new token token with given value and type.
		 *
		 * @param value the value of the token
		 * @param type the type of the token
		 */
		public Token(String value, TokenType type) {
			this.value = value;
			this.type = type;
		}
		
		/**
		 * Gets the string value of the token.
		 *
		 * @return the value of the token
		 */
		public String getValue(){
			return value;
		}
		
		/**
		 * Gets the type of the token.
		 *
		 * @return the type of the token
		 */
		public TokenType getType(){
			return type;
		}
	}

	/**
	 * Instantiates a new lexical analyser.
	 *
	 * @throws IllegalArgumentException is text is <code>null</code>
	 * @param text the input text
	 */
	public Lexer(String text) { 
		if(Objects.isNull(text)){
			throw new IllegalArgumentException(
				"Input must not be null."
			);
		}
		this.text = text;
		currentIndex = 0;
		finished = false;
		
		if(text.isEmpty()){
			currentTokenType = TokenType.EOF;
			finished = true;
		} else if(text.startsWith("{$")){
			currentTokenType = TokenType.TAG;
		} else {
			currentTokenType = TokenType.TEXT;
		}
		
	}

	/**
	 * Returns next generated token.
	 * Method for separating tags from text in input string. 
	 * Method checks character by character and, depending on read character, does certain actions.
	 * 
	 * @throws SmartScriptParserException if text has unclosed tag
	 * @return the next generated token
	 */
	public Token nextToken(){
		StringBuilder sb = new StringBuilder();

		if(currentTokenType == TokenType.TEXT){
			while(true){
				if(islastCharacter()){
					setCurrentTokenType(TokenType.EOF);
					sb.append(text.charAt(currentIndex++));
					return new Token(sb.toString(), TokenType.TEXT);
				} else if(text.charAt(currentIndex) == '\\'){
					checkEscapeSequence();
				} else if (isTagStart()){
					setCurrentTokenType(TokenType.TAG);
					return new Token(sb.toString(), TokenType.TEXT);
				}
				sb.append(text.charAt(currentIndex++));
			}
		} else if (currentTokenType == TokenType.TAG){
			while(true){
				if(islastCharacter()){
					throw new SmartScriptParserException(
						"Unclosed tag."
					);
				} else if (isTagEnd()){
					currentIndex += 2;
					if(currentIndex != text.length()){
						setCurrentTokenType(TokenType.TEXT);
					} else {
						setCurrentTokenType(TokenType.EOF);
					}
					return new Token(sb.toString().replaceAll("\\\\\\\\", "\\\\") + "$}", TokenType.TAG);
				}
				sb.append(text.charAt(currentIndex++));
			}
		} else {
			return checkEOF();
		}
	}

	/**
	 * Checks if lexer has already reached end of the text or is this the first time and depending on that return Token or throws exception.
	 * 
	 * @throws SmartScriptParserException if lexer has already reached end of the text
	 * @return	EOF token if this is the first time that this method is called
	 */
	private Token checkEOF() {
		if(!finished){
			finished = true;
			return new Token(null, TokenType.EOF);
		} else {
			throw new SmartScriptParserException(
				"End of string already reached."
			);
		}
	}

	/**
	 * Checks if is last character in input text.
	 *
	 * @return true, if is last character, false otherwise
	 */
	private boolean islastCharacter() {
		return (currentIndex + 1 == text.length());
	}

	/**
	 * Checks if current char sequence fits as tag start.
	 *
	 * @return true, if is tag start, false otherwise
	 */
	private boolean isTagStart() {
		return text.charAt(currentIndex) == '{' && text.charAt(currentIndex + 1) == '$';
	}
	
	/**
	 * Checks if current char sequence fits as tag end.
	 *
	 * @return true, if is tag end, false otherwise
	 */
	private boolean isTagEnd() {
		return text.charAt(currentIndex) == '$' && text.charAt(currentIndex + 1) == '}';
	}

	/**
	 * Checks if character after \ is legal escape sequence.
	 * Legal escape sequences in text are \ and {.
	 */
	private void checkEscapeSequence() {
		char sequence = text.charAt(currentIndex + 1);
		if(sequence != '\\' && sequence != '{'){
			throw new SmartScriptParserException(
				"Illegal escape sequence."
			);
		} else { 
			currentIndex++;
		}
	}
	
	/**
	 * Sets the current token type.
	 *
	 * @param newType the new current token type
	 */
	public void setCurrentTokenType(TokenType newType){
		currentTokenType = newType;
	}
}
