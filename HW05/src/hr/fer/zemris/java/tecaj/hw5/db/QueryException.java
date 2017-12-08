package hr.fer.zemris.java.tecaj.hw5.db;

/**
 * The class representing exception when some error occurs in {@link QueryParser} class.
 * 
 * @author Vjeco
 */
public class QueryException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new query exception.
	 */
	public QueryException(){
		super();
	}
	
	/**
	 * Instantiates a new query exception and prints given message to standard output.
	 *
	 * @param message the message to be printed
	 */
	public QueryException(String message){
		super(message);
	}

}
