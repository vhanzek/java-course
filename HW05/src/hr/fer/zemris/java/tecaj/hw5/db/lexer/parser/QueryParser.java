package hr.fer.zemris.java.tecaj.hw5.db.lexer.parser;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.tecaj.hw5.db.ConditionalExpression;
import hr.fer.zemris.java.tecaj.hw5.db.fields.FirstNameValueGetter;
import hr.fer.zemris.java.tecaj.hw5.db.fields.IFieldValueGetter;
import hr.fer.zemris.java.tecaj.hw5.db.fields.JmbagValueGetter;
import hr.fer.zemris.java.tecaj.hw5.db.fields.LastNameValueGetter;
import hr.fer.zemris.java.tecaj.hw5.db.lexer.QueryLexer;
import hr.fer.zemris.java.tecaj.hw5.db.lexer.QueryToken;
import hr.fer.zemris.java.tecaj.hw5.db.lexer.QueryTokenType;
import hr.fer.zemris.java.tecaj.hw5.db.operators.EqualsOperator;
import hr.fer.zemris.java.tecaj.hw5.db.operators.GreaterThanOperator;
import hr.fer.zemris.java.tecaj.hw5.db.operators.GreaterThanOrEqualOperator;
import hr.fer.zemris.java.tecaj.hw5.db.operators.IComparisonOperator;
import hr.fer.zemris.java.tecaj.hw5.db.operators.LessThanOperator;
import hr.fer.zemris.java.tecaj.hw5.db.operators.LessThanOrEqualOperator;
import hr.fer.zemris.java.tecaj.hw5.db.operators.LikeOperator;
import hr.fer.zemris.java.tecaj.hw5.db.operators.NotEqualOperator;

import static hr.fer.zemris.java.tecaj.hw5.db.lexer.QueryTokenType.*;

/**
 * The class used for parsing {@link QueryToken}s got from the lexical analyser {@link QueryLexer}.
 * 
 * @author Vjeco
 */
public class QueryParser {
	
	/** The list of conditional expressions given in a query. */
	private List<ConditionalExpression> expressions;
	
	/**
	 * Instantiates a new query parser. This constructor uses {@link QueryLexer} 
	 * to get list of {@link QueryToken} from the given input text.
	 *
	 * @param queryInput the query input (everything after query command)
	 */
	public QueryParser(String queryInput) {
		QueryLexer lexer = new QueryLexer(queryInput);
		this.expressions = new LinkedList<>();
		
		parse(lexer);
	}

	/**
	 * Method for parsing tokens got from given lexical analyser. 
	 * Method checks is tokens are properly ordered, and if there are not, throws an exception.
	 * For every {@link ConditionalExpression} looking like [attribute,comparison operator,string literal] 
	 * that appears in input text, method adds that expression in a list of {@code expressions}.
	 *
	 * @param lexer the {@link QueryLexer} 
	 * @throws QueryParserException if tokens are not properly ordered
	 */
	private void parse(QueryLexer lexer) {
		List<QueryToken> tokens = lexer.generateTokens();
		
		Stack<QueryToken> condExp = new Stack<>();
		QueryToken lastToken = new QueryToken("and", LOGICAL_OPERATOR);
		
		for(QueryToken token : tokens){
			QueryTokenType lastTokenType = lastToken.getType();
			
			switch(token.getType()){
			case ATTRIBUTE:
				if(lastTokenType != LOGICAL_OPERATOR){
					throwException();
				} else {
					condExp.push(token);
					lastToken = token;
					if(condExp.size() != 1){
						throwException();
					}
				}
				break;
			case COMPARISON_OPERATOR:
				if(lastTokenType != ATTRIBUTE){
					throwException();
				} else {
					condExp.push(token);
					lastToken = token;
					if(condExp.size() != 2){
						throwException();
					}
				}
				break;
			case LOGICAL_OPERATOR:
				if(lastTokenType != STRING){
					throwException();
				} else {
					condExp.clear();
					lastToken = token;
				}
				break;
			case STRING:
				if(lastTokenType != COMPARISON_OPERATOR){
					throwException();
				} else {
					condExp.push(token);
					expressions.add(new ConditionalExpression(
											checkAttribute(condExp.get(0)), 
											checkOperator(condExp.get(1)), 
											condExp.get(2).getValue())
					);
					lastToken = token;
				}
				break;
			}
		}
	}

	/**
	 * Method determines which {@link IComparisonOperator} implementation needs to be used in
	 * a conditional expression depending on type of the operator.
	 *
	 * @param queryToken the current token
	 * @return the {@link IComparisonOperator} implementation
	 */
	private IComparisonOperator checkOperator(QueryToken queryToken) {
		switch(queryToken.getValue()){
		case ">":
			return new GreaterThanOperator();
		case ">=":
			return new GreaterThanOrEqualOperator();
		case "<":
			return new LessThanOperator();
		case "<=":
			return new LessThanOrEqualOperator();
		case "=":
			return new EqualsOperator();
		case "!=":
			return new NotEqualOperator();
		case "LIKE":
			return new LikeOperator();
		default:
			return null;
		}
	}

	/**
	 * Method determines which {@link IFieldValueGetter} implementation needs to be used in
	 * a conditional expression depending on type of the attribute.
	 *
	 * @param queryToken the current token
	 * @return the {@link IFieldValueGetter} implementation
	 */
	private IFieldValueGetter checkAttribute(QueryToken queryToken) {
		switch(queryToken.getValue()){
		case "lastName":
			return new LastNameValueGetter();
		case "firstName":
			return new FirstNameValueGetter();
		case "jmbag":
			return new JmbagValueGetter();
		default:
			return null;
		}
	}

	/**
	 * Method for throwing {@link QueryParserException}.
	 */
	private void throwException() {
		throw new  QueryParserException(
			"Illegal order of tokens in a query."
		);
	}

	/**
	 * Gets the list of conditional expressions used in a query.
	 *
	 * @return the list of conditional expressions used in a query.
	 */
	public List<ConditionalExpression> getExpressions() {
		return expressions;
	}
	
	
}
