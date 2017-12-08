package hr.fer.zemris.java.webapps.blog.dao;

import hr.fer.zemris.java.webapps.blog.dao.jpa.JPADAOImpl;

/**
 * The singleton class used for getting the {@link DAO} implementation. 
 * Its functionality is used for getting wanted data from the connected 
 * database.
 * 
 * @author Vjeran
 */
public class DAOProvider {

	/** The {@code DAO} implementation. */
	private static DAO dao = new JPADAOImpl();
	
	/**
	 * Gets the {@code DAO} implementation.
	 *
	 * @return the {@code DAO}
	 */
	public static DAO getDAO() {
		return dao;
	}
	
}