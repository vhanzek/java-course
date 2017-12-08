package hr.fer.zemris.java.webapps.blog;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.servlet.http.HttpServletRequest;

/**
 * The Class {@code BlogComment} representing one comment on the 
 * blog entry ({@link BlogEntry}). Email of the user which posted comment
 * will be visible only if he is logged in, otherwise it will show 'Anonymous'.
 * Also, this class represents one row in the {@code blog_comments} table.
 * 
 * @author Vjeran
 */
@Entity
@Table(name="blog_comments")
public class BlogComment {

	/** The id. */
	@Id @GeneratedValue
	private Long id;
	
	/** Blog entry. */
	@ManyToOne
	@JoinColumn(nullable=false)
	private BlogEntry blogEntry;
	
	/** User's e-mail. */
	@Column(length=100,nullable=false)
	private String usersEMail;
	
	/** The message. */
	@Column(length=4096,nullable=false)
	private String message;
	
	/** The time of posting. */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date postedOn;
	
	
	/*------------------ HELPER METHODS ------------------*/
	
	/**
	 * Helper method for initializing entry's properties from the
	 * {@link HttpServletRequest}.
	 *
	 * @param req the {@link HttpServletRequest}
	 * @param entry the blog entry
	 * @param email the email of the user that posted comment
	 */
	public void fillBlogComment(HttpServletRequest req, BlogEntry entry, String email){
		this.blogEntry = entry;
		this.usersEMail = email;
		this.message = prepare(req.getParameter("comment"));
		this.postedOn = new Date();
	}
	
	/**
	 * Helper method which prepares parameter got from the request by
	 * changing it to empty string if parameter is <code>null</code>.
	 * If input is not <code>null</code>, method returns trimmed input.
	 *
	 * @param s the parameter
	 * @return the result string
	 */
	private String prepare(String s) {
		if(s == null) return "";
		return s.trim();
	}
	
	
	/*------------------ GETTERS AND SETTERS ------------------*/
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the blog entry.
	 *
	 * @return the blog entry
	 */
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}
	
	/**
	 * Sets the blog entry.
	 *
	 * @param blogEntry the new blog entry
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * Gets the users e-mail.
	 *
	 * @return the users e-mail
	 */
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 * Sets the users e-mail.
	 *
	 * @param usersEMail the new users e-mail
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the time of posting.
	 *
	 * @return the time of posting
	 */
	public Date getPostedOn() {
		return postedOn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}