package hr.fer.zemris.webapps.webapp_baza.dao;

/**
 * This class extends {@link RuntimeException} and is thrown if some error occurs
 * using {@link DAO} interface implementations
 * 
 * @author Vjeran
 */
public class DAOException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new DAO exception.
	 */
	public DAOException() {
	}

	/**
	 * Instantiates a new DAO exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 * @param enableSuppression the enable suppression
	 * @param writableStackTrace the writable stack trace
	 */
	public DAOException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Instantiates a new DAO exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new DAO exception.
	 *
	 * @param message the message
	 */
	public DAOException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new DAO exception.
	 *
	 * @param cause the cause
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}
}