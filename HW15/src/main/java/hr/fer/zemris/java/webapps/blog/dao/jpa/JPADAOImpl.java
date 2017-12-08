package hr.fer.zemris.java.webapps.blog.dao.jpa;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import hr.fer.zemris.java.webapps.blog.BlogComment;
import hr.fer.zemris.java.webapps.blog.BlogEntry;
import hr.fer.zemris.java.webapps.blog.BlogUser;
import hr.fer.zemris.java.webapps.blog.dao.DAO;
import hr.fer.zemris.java.webapps.blog.dao.DAOException;

/**
 * <p>Implementation of the {@link DAO} interface which offers method for 
 * communicating with the database.
 * 
 * <p>There are methods for retrieving single results (for example, finding
 * entity by ID - {@link #getBlogEntry(Long)}), list of results with given property
 * (for example, {@link #getBlogEntries(BlogUser)} and methods for inserting new
 * entities ({@link #insertBlogEntry(BlogEntry)} and updating it 
 * ({@link #updateBlogEntry(Long, String, String)}.
 * 
 * @author Vjeran
 */
public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		return JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
	}
	
	@Override
	public List<BlogEntry> getBlogEntries(BlogUser creator) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		
		List<BlogEntry> entries = 
			(List<BlogEntry>) em.createNamedQuery("BlogEntry.entriesQuery", BlogEntry.class)
								.setParameter("c", creator)
								.getResultList();
		
		return entries;
	}
	
	@Override
	public void updateBlogEntry(Long id, String title, String text) throws DAOException {
		BlogEntry entry = getBlogEntry(id);
		entry.setTitle(title);
		entry.setText(text);
		entry.setLastModifiedAt(new Date());
	}

	@Override
	public void insertBlogEntry(BlogEntry entry) throws DAOException {
		JPAEMProvider.getEntityManager().persist(entry);
	}

	@Override
	public BlogUser getBlogUser(String nick) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		BlogUser user = null;
		
		try{
			user = em.createNamedQuery("BlogUser.loginQuery", BlogUser.class)
					 .setParameter("nick", nick)
					 .getSingleResult();
		} catch (NoResultException ex){}
		
		return user;
	}

	@Override
	public BlogUser getBlogUser(Long id) throws DAOException {
		return JPAEMProvider.getEntityManager().find(BlogUser.class, id);
	}

	@Override
	public void insertBlogUser(BlogUser user) throws DAOException {
		JPAEMProvider.getEntityManager().persist(user);
	}

	@Override
	public List<BlogUser> getBlogUsers() throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		
		List<BlogUser> users = 
			(List<BlogUser>) em.createNamedQuery("BlogUser.selectionQuery", BlogUser.class)
							   .getResultList();
		
		return users;
	}

	@Override
	public void insertBlogComment(BlogComment comment) throws DAOException {
		JPAEMProvider.getEntityManager().persist(comment);
	}
}