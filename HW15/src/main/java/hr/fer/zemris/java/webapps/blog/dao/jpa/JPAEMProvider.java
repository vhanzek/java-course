package hr.fer.zemris.java.webapps.blog.dao.jpa;
import javax.persistence.EntityManager;

import hr.fer.zemris.java.webapps.blog.dao.DAOException;

/**
 * This class represents storage of {@link EntityManager} instances in {@link ThreadLocal} 
 * object. {@code ThreadLocal} is a map whose keys are IDs of a thread doing the operation in 
 * the map.
 * 
 * @author Vjeran
 */
public class JPAEMProvider {

	/** The locals. */
	private static ThreadLocal<EntityManager> locals = new ThreadLocal<>();

	/**
	 * Gets the entity manager.
	 *
	 * @return the entity manager
	 */
	public static EntityManager getEntityManager() {
		EntityManager em = locals.get();
		if(em == null) {
			em = JPAEMFProvider.getEmf().createEntityManager();
			em.getTransaction().begin();
			locals.set(em);
		}
		return em;
	}

	/**
	 * Method for closing {@code EntityManager}.
	 *
	 * @throws DAOException the DAO exception if an error occurs
	 */
	public static void close() throws DAOException {
		EntityManager em = locals.get();
		if(em == null) return;
		
		DAOException dex = null;
		try {
			em.getTransaction().commit();
		} catch(Exception ex) {
			dex = new DAOException("Unable to commit transaction.", ex);
		}
		try {
			em.close();
		} catch(Exception ex) {
			if(dex!=null) {
				dex = new DAOException("Unable to close entity manager.", ex);
			}
		}
		
		locals.remove();
		if(dex != null) throw dex;
	}	
}