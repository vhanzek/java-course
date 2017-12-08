package hr.fer.zemris.webapps.webapp_baza.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.webapps.webapp_baza.connection.SQLConnectionProvider;

/**
 * The utility class which offers some helper static methods for
 * working with {@link DAO} interface implementations.
 * 
 * @author Vjeran
 */
public abstract class DAOUtil {
	
	/**
	 * Parameterized method which gets entry requested in <code>selectionSql</code>
	 * which represents SQL query for communicating with the database.
	 *
	 * @param <T> the generic type
	 * @param selectionSql the SQL query
	 * @param getter the {@link DAOGetter} implementation
	 * @return the requested entry
	 */
	public static <T> T getEntry(String selectionSql, DAOGetter<T> getter){
		T entry = null;
		Connection conn = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(selectionSql);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					if(rs != null && rs.next()) {
						entry = getter.get(rs);
					}
				} finally {
					try { rs.close(); } catch (Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch (Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException(
				"Error getting database entry.", ex
			);
		}
		return entry;
	}
	
	/**
	 * Parameterized method for getting list of entries from the table specified in 
	 * <code>selectionSql</code> which represents SQL query for communicating with the database.
	 *
	 * @param <T> the generic type
	 * @param selectionSql the SQL query
	 * @param getter the {@link DAOGetter} implementation
	 * @return the requested entries
	 */
	public static <T> List<T> getEntries(String selectionSql, DAOGetter<T> getter){
		List<T> entries = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(selectionSql);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while(rs != null && rs.next()) {
						T entry = getter.get(rs);
						entries.add(entry);
					}
				} finally {
					try { rs.close(); } catch (Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch (Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException(
				"Error getting list of database entries.", ex
			);
		}
		return entries;
	}
	
	/**
	 * Helper method for updating database attribute specified in <code>updateSql</code> 
	 * which represents SQL query for communicating with the database.
	 *
	 * @param updateSql the SQL update query
	 */
	public static void updateAttribute(String updateSql){
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(updateSql);
			try {
				pst.executeUpdate();
			} finally {
				try { pst.close(); } catch (Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException(
				"Error updating votes count.", ex
			);
		}
	}
	
	/**
	 * The interface {@code DAOGetter} used in methods for communicating
	 * with the database. It defines different ways of retrieving wanted 
	 * results from the {@link ResultSet} object got from the database.
	 *
	 * @param <T> the generic type
	 */
	public interface DAOGetter<T> {
		
		/**
		 * Gets wanted entry or attribute from the database.
		 *
		 * @param rs the {@link ResultSet} object
		 * @return the entry or attribute from the database
		 * @throws SQLException the SQL exception
		 */
		T get(ResultSet rs) throws SQLException;
	}
}
