package hr.fer.zemris.java.custom.collections;

/**
 * The Class EmptyStackException representing exception when there are no elements on stack.
 * 
 * @author Vjeco
 */
public class EmptyStackException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new empty stack exception.
	 *
	 * @param message the message shown to a user
	 */
	public EmptyStackException(String message){
		super(message);
	}
	
	/*  @see java.lang.Throwable#getMessage()
	 */
	public String getMessage(){
		return super.getMessage();
	}

}
