package hr.fer.zemris.java.webapps.blog.dao.jpa;
import javax.persistence.EntityManagerFactory;

/**
 * The class {@code JPAEMFProvider} used for getting {@code EntityManagerFactory}.
 * Also, it has method for setting a new {@code EntityManagerFactory}.
 * 
 * @author Vjeran
 */
public class JPAEMFProvider {

	/** The instance of {@code EntityManagerFactory}. */
	private static EntityManagerFactory emf;
	
	/**
	 * Gets the {@code EntityManagerFactory}.
	 *
	 * @return the {@code EntityManagerFactory}
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}
	
	/**
	 * Sets the {@code EntityManagerFactory}.
	 *
	 * @param emf the new {@code EntityManagerFactory}
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}