package hr.fer.zemris.java.tecaj.hw5.db;

import java.util.List;

import hr.fer.zemris.java.tecaj.hw5.db.lexer.parser.QueryParser;

/**
 * The class used for filtering {@link StudentDatabase} depending on given query.
 * 
 * @author Vjeco
 */
public class QueryFilter implements IFilter {
	
	/** The list of conditional expressions given in a query. */
	private List<ConditionalExpression> expressions;
	
	/**
	 * Instantiates a new query filter. Uses {@link QueryParser} to parse given query 
	 * string to {@link ConditionalExpression}.
	 *
	 * @param queryString the query string (everything after the query word)
	 */
	public QueryFilter(String queryString) {
		QueryParser parser = new QueryParser(queryString);
		this.expressions = parser.getExpressions();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>This method checks one or more {@link ConditionalExpression}s and if, and only if, certain 
	 * student record satisfies all of the given expressions returns <code>true</code>.</p>
	 */
	@Override
	public boolean accepts(StudentRecord record) {
		for(ConditionalExpression expr : expressions){
			boolean recordSatisfies = expr.getComparisonOperator().satisfied(
											expr.getFieldValueGetter().get(record),
											expr.getStringLiteral()
									  );
			
			if(!recordSatisfies) return false;
		}
		
		return true;
	}
}
