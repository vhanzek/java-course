package hr.fer.zemris.webapps.webapp_baza.connection;

import java.sql.Connection;

/**
 * This class represents storage of database connections in {@link ThreadLocal} object.
 * {@code ThreadLocal} is a map whose keys are IDs of a thread doing the operation in 
 * the map.
 * 
 * @author Vjeran
 */
public class SQLConnectionProvider {

	/** The database connections. */
	private static ThreadLocal<Connection> connections = new ThreadLocal<>();
	
	/**
	 * Sets the connection for current thread, or removes it from
	 * the map is <code>con</code> is <code>null</code>.
	 * 
	 * @param con database connection
	 */
	public static void setConnection(Connection con) {
		if(con == null) connections.remove();
		else connections.set(con);
	}
	
	/**
	 * Gets the connection which current thread can use.
	 * 
	 * @return database connection
	 */
	public static Connection getConnection() {
		return connections.get();
	}
	
}