package hr.fer.zemris.java.webapps.blog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.servlet.http.HttpServletRequest;

/**
 * The class {@code BlogEntry} representing one entry on the blog.
 * It has seven properties describing it. Every entry has {@link #title},
 * corresponding {@link #text}, time of creation {@link #createdAt},
 * modified time {@link #lastModifiedAt} and an entry creator, {@link #creator}.
 * Also, this class represents one row in the {@code blog_entries} table.
 * 
 * @author Vjeran
 */
@Entity
@Table(name="blog_entries")
@Cacheable(true)
@NamedQueries({
	@NamedQuery(
		name="BlogEntry.entriesQuery",
		query="SELECT be FROM BlogEntry AS be WHERE be.creator=:c"
	)
})
public class BlogEntry {

	/** The id. */
	@Id @GeneratedValue
	private Long id;
	
	/** The comments of the entry. */
	@OneToMany(mappedBy="blogEntry",fetch=FetchType.LAZY,cascade=CascadeType.PERSIST, orphanRemoval=true)
	@OrderBy("postedOn")
	private List<BlogComment> comments = new ArrayList<>();
	
	/** The time of creation. */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date createdAt;
	
	/** The time when entry was last modified. */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=true)
	private Date lastModifiedAt;
	
	/** The title of the entry. */
	@Column(nullable=false,length=60)
	private String title;
	
	/** The text i.e. the body of the blog entry. */
	@Column(nullable=false,length=4*1024)
	private String text;
	
	/** The creator of the entry. */
	@ManyToOne
	private BlogUser creator;
	
	
	/*------------------ HELPER METHODS ------------------*/
	
	/**
	 * Helper method for initializing entry's properties from the
	 * {@link HttpServletRequest}.
	 *
	 * @param req the {@link HttpServletRequest}
	 * @param creator the creator of the entry
	 */
	public void fillFromRequest(HttpServletRequest req, BlogUser creator){
		this.createdAt = new Date();
		this.lastModifiedAt = null;
		this.title = prepare(req.getParameter("title"));
		this.text = prepare(req.getParameter("text"));
		this.creator = creator;
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
	 * Gets the comments.
	 *
	 * @return the comments
	 */
	public List<BlogComment> getComments() {
		return comments;
	}
	
	/**
	 * Sets the comments.
	 *
	 * @param comments the new comments
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}


	/**
	 * Gets the time of creation.
	 *
	 * @return the created at
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Gets the last modified at.
	 *
	 * @return the last modified at
	 */
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	/**
	 * Sets the last modified time.
	 *
	 * @param lastModifiedAt the new last modified time
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the text.
	 *
	 * @param text the new text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Gets the creator.
	 *
	 * @return the creator
	 */
	public BlogUser getCreator() {
		return creator;
	}

	/**
	 * Sets the creator.
	 *
	 * @param creator the new creator
	 */
	public void setCreator(BlogUser creator) {
		this.creator = creator;
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
		BlogEntry other = (BlogEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}