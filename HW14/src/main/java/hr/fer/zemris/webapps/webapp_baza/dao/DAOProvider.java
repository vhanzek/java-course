package hr.fer.zemris.webapps.webapp_baza.dao;

/**
 * The singleton class used for getting the {@link DAO} implementation. Its functionality
 * is used for getting wanted data from the connected database.
 * 
 * @author Vjeran
 */
public class DAOProvider {
	
	/** The {@link DAO} implementation. */
	private static DAO dao = new SQLDAO();
	
	/**
	 * Gets the {@link DAO} implementation.
	 *
	 * @return the {@code DAO}
	 */
	public static DAO getDAO() {
		return dao;
	}
}