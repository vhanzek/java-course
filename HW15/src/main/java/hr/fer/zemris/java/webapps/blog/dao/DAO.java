package hr.fer.zemris.java.webapps.blog.dao;

import java.util.Collection;
import java.util.List;

import hr.fer.zemris.java.webapps.blog.BlogComment;
import hr.fer.zemris.java.webapps.blog.BlogEntry;
import hr.fer.zemris.java.webapps.blog.BlogUser;

/**
 * Interface toward data persistence subsystem. It is used for retrieving
 * data from the database and returning it as an instance of, for example, 
 * {@link Collection} class. This way, application has separate data-
 * access layer.
 * 
 * @author Vjeran
 */
public interface DAO {
	
	/**
	 * Gets the blog entry with given id.
	 *
	 * @param id the blog entry id
	 * @return the blog entry
	 * @throws DAOException if an error occurs
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;
	
	/**
	 * Gets the blog entries.
	 *
	 * @param creator the creator
	 * @return the blog entries
	 * @throws DAOException if an error occurs
	 */
	public List<BlogEntry> getBlogEntries(BlogUser creator) throws DAOException;
	
	/**
	 * Update blog entry.
	 *
	 * @param id the id
	 * @param title the title
	 * @param text the text
	 * @throws DAOException if an error occurs
	 */
	public void updateBlogEntry(Long id, String title, String text) throws DAOException;
	
	/**
	 * Insert blog entry.
	 *
	 * @param entry the entry
	 * @throws DAOException if an error occurs
	 */
	public void insertBlogEntry(BlogEntry entry) throws DAOException;
	
	/**
	 * Gets the blog user.
	 *
	 * @param nick the nick
	 * @return the blog user
	 * @throws DAOException if an error occurs
	 */
	public BlogUser getBlogUser(String nick) throws DAOException;
	
	/**
	 * Gets the blog user.
	 *
	 * @param id the id
	 * @return the blog user
	 * @throws DAOException if an error occurs
	 */
	public BlogUser getBlogUser(Long id) throws DAOException;
	
	/**
	 * Gets the blog users.
	 *
	 * @return the blog users
	 * @throws DAOException if an error occurs
	 */
	public List<BlogUser> getBlogUsers() throws DAOException;
	
	/**
	 * Insert blog user.
	 *
	 * @param user the user
	 * @throws DAOException the DAO exception
	 */
	public void insertBlogUser(BlogUser user) throws DAOException;
	
	/**
	 * Insert blog comment.
	 *
	 * @param comment the comment
	 * @throws DAOException if an error occurs
	 */
	public void insertBlogComment(BlogComment comment) throws DAOException;
	
}