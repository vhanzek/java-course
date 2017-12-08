package hr.fer.zemris.java.tecaj.hw3.prob1;

import static java.lang.Character.*;

import java.util.Objects;

/**
 * The Class Lexer used for lexical analysis of the input text. Can be in two different {@link LexerState}s.
 * 
 * @author Vjeco
 */
public class Lexer {	
	
	/** The array of characters from input text. */
	private char[] data; 
	
	/** The token. */
	private Token token;
	
	/** The index of the first unread character from data. */
	private int currentIndex; 
	
	/** The current working lexer state. */
	private LexerState state;

	/**
	 * Instantiates a new lexical analyser with the task to analyse given string.
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
		data = text.toCharArray();
		currentIndex = 0;
		setState(LexerState.BASIC);
	}
	
	/**
	 * Method for generating next token from the input text.
	 * <p>Lexer changes states with occurrence of '#' character. 
	 * In BASIC {@link LexerState}, there are 4 {@link TokenType} - WORD, NUMBER, SYMBOL or EOF.
	 * On the other side in EXTENDED {@link LexerState}, every character is appended as word.</p>
	 * 
	 * @return the generated token
	 */
	public Token nextToken(){
		if(token != null && getToken().getType() == TokenType.EOF){
			throw new LexerException(
				"Lexer has already reached end of the string."
			);
		}
		
		StringBuilder sb = new StringBuilder();
		
		switch(checkCharacter(state)){
		case LETTER : 
			sb.append(data[currentIndex++]);
			while(checkCharacter(state) == CharType.LETTER){
				sb.append(data[currentIndex++]);
			}
				
			token = new Token(TokenType.WORD, sb.toString());
			break;
				
		case DIGIT :
			sb.append(data[currentIndex++]);
			while(checkCharacter(state) == CharType.DIGIT){
				sb.append(data[currentIndex++]);
			}
				
			long number;
			try{
				number = Long.parseLong(sb.toString());
			} catch (NumberFormatException nfe){
				throw new LexerException(
					"Given input is illegal"
				);
			}
			
			token = new Token(TokenType.NUMBER, number);
			break;
				
		case SYMBOL :
			token = new Token(TokenType.SYMBOL, data[currentIndex++]);
			break;
			
		case WHITESPACE :
			currentIndex++;
			nextToken();
			break;
			
		case EOF :
			token = new Token(TokenType.EOF, null);
			break;
		}
			
		return token;
	}

	/**
	 * Gets the current token.
	 *
	 * @return the current token
	 */
	public Token getToken() {
		return token;
	}
	
	/**
	 * Sets the current lexer state.
	 * 
	 * @throws IllegalArgumentException if new state is <code>null</code>.
	 * @param state the new lexer state
	 */
	public void setState(LexerState state){
		if(Objects.isNull(state)){
			throw new IllegalArgumentException(
				"Given state is null."
			);
		}
		this.state = state;
	}
	
	/**
	 * Method for checking next character in input string.
	 *
	 * @param state the state
	 * @return the char type
	 */
	private CharType checkCharacter(LexerState state){
		if(currentIndex == data.length){
			return CharType.EOF;
		}
		char ch = data[currentIndex];
		if(state == LexerState.BASIC){
			if(isLetter(ch)){
				return CharType.LETTER;
			} else if (ch == '\\'){
				isCharSequenceLegal();				
				return CharType.LETTER;
			} else if(isNumber(ch)){
				return CharType.DIGIT;
			} else if(isWhitespace(ch)){
				return CharType.WHITESPACE;
			} else if(ch == '#'){
				setState(LexerState.EXTENDED);
				return CharType.SYMBOL;
			} else {
				return CharType.SYMBOL;
			}
		} else {
			if(ch == '#'){
				setState(LexerState.BASIC);
				return CharType.SYMBOL;
			} else if(isWhitespace(ch)){
				return CharType.WHITESPACE;
			} else {
				return CharType.LETTER;	
			}
		}
	}

	/**
	 * Checks if char sequence is legal. 
	 * Char, i.e. escape sequence is legal if next character after \ is \ or a digit.
	 * 
	 * @throws LexerException if char sequence is not legal
	 */
	private void isCharSequenceLegal() {
		boolean exception = false;
		if(currentIndex + 1 == data.length){
			exception = true;
		}
		if(!exception){
			currentIndex++;
			if(!isNumber(data[currentIndex]) && data[currentIndex] != '\\'){
				exception = true;
			}
		}
		if(exception){
			throw new LexerException(
				"Character after \\ is not a number or \\."
			);
		}
	}

	/**
	 * Checks if character is a number.
	 *
	 * @param character the tested character
	 * @return true, if is number, false otherwise
	 */
	private boolean isNumber(char character){
		return (character >= '0' && character <= '9');
	}
}
