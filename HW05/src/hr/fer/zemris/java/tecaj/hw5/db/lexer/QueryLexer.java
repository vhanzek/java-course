package hr.fer.zemris.java.tecaj.hw5.db.lexer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.tecaj.hw5.db.QueryException;

import static hr.fer.zemris.java.tecaj.hw5.db.lexer.QueryTokenType.*;

/**
 * The class representing lexical analyser for query input. This lexical analysis can recognize 4 {@link QueryTokenType}:
 * Attribute, comparison operator and logical operator.
 * 
 * @author Vjeco
 */
public class QueryLexer {
	
	/** The input text. */
	private String text;
	
	/** The generated tokens from the text. */
	private List<QueryToken> tokens;

	/**
	 * Instantiates a new query lexical analyser.
	 *
	 * @param text the input text
	 */
	public QueryLexer(String text) {
		this.text = text;
		this.tokens = new ArrayList<>();
	}
	
	/**
	 * Method for generating {@link QueryToken} from the input text.
	 *
	 * @return the list of generated {@link QueryToken}s
	 */
	public List<QueryToken> generateTokens(){
		String attribute = prepareRegex(ATTRIBUTE.getRegex());
		String compOperator = prepareRegex(COMPARISON_OPERATOR.getRegex());
		String logOperator = prepareRegex(LOGICAL_OPERATOR.getRegex());
		String string = STRING.getRegex();
		String illegal = "([^\\s]+)";
		
		Matcher matcher = Pattern.compile(
							attribute + "|" + compOperator + "|" + logOperator + "|" + string + "|" + illegal).matcher(text);
		
		while(matcher.find()){
			if(matcher.group(1) != null){
				tokens.add(new QueryToken(matcher.group(1), ATTRIBUTE));
			} else if (matcher.group(2) != null){
				tokens.add(new QueryToken(matcher.group(2), COMPARISON_OPERATOR));
			} else if (matcher.group(3) != null){
				tokens.add(new QueryToken(matcher.group(3), LOGICAL_OPERATOR));
			} else if (matcher.group(4) != null){
				tokens.add(new QueryToken(matcher.group(4), STRING));
			} else if (matcher.group(5) != null){
				throw new QueryException(
					"Syntax error.");
			}
		}
		
		return tokens;
	}

	/**
	 * Method prepares regex by putting parentheses around it.
	 *
	 * @param regex the regular expression
	 * @return the result string
	 */
	private String prepareRegex(String regex) {
		return "(" + regex + ")";
	}
}
