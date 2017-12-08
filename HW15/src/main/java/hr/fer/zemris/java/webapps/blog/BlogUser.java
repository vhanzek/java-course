package hr.fer.zemris.java.webapps.blog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.webapps.blog.crypto.Digester;
import hr.fer.zemris.java.webapps.blog.dao.DAOProvider;

/**
 * The Class {@code BlogUser} representing one blog user. It has several 
 * properties describing it. The basic characteristics {@link #firstName} 
 * and {@link #lastName}. Also, there are {@link #nick} which representing 
 * user's name and {@link #passwordHash} which is digested password of the 
 * user. Reason for digesting original password is security, so that normal 
 * database user can not reach any password, only its hash. User also has 
 * list of posted {@link #entries} and its {@link #email}. This class 
 * represents one row in the {@code blog_users} table.
 * 
 * @author Vjeran
 */
@Entity
@Table(name="blog_users")
@NamedQueries({
	@NamedQuery(
		name="BlogUser.loginQuery",
		query="SELECT b FROM BlogUser as b WHERE b.nick=:nick"
		
	),
	@NamedQuery(
		name="BlogUser.selectionQuery",
		query="SELECT bu FROM BlogUser AS bu"
	)
})
public class BlogUser {
	
	/** The Constant EMPTY_STRING_HASH. */
	private final static String EMPTY_STRING_HASH = 
		"e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";
	
	/** The id. */
	@Id @GeneratedValue
	private long id;
	
	/** The first name. */
	@Column(length=50,nullable=false)
	private String firstName;
	
	/** The last name. */
	@Column(length=50,nullable=false)
	private String lastName;
	
	/** The nick. */
	@Column(length=30,unique=true,nullable=false)
	private String nick;
	
	/** The email. */
	@Column(length=50,nullable=false)
	private String email;
	
	/** The password hash. */
	@Column(length=4096,nullable=false)
	private String passwordHash;
	
	/** The entries. */
	@OneToMany(mappedBy="creator", fetch=FetchType.LAZY,cascade=CascadeType.PERSIST, orphanRemoval=true)
	private List<BlogEntry> entries;
	
	/** The errors. */
	@Transient
	private Map<String, String> errors = new HashMap<>();
	
	
	
	/*------------------ HELPER METHODS ------------------*/
	
	/**
	 * Helper method for initializing entry's properties from the
	 * {@link HttpServletRequest}.
	 *
	 * @param req the {@link HttpServletRequest}
	 */
	public void fillFromRequest(HttpServletRequest req){
		this.firstName = prepare(req.getParameter("firstName"));
		this.lastName = prepare(req.getParameter("lastName"));
		this.nick = prepare(req.getParameter("nick")); 
		this.passwordHash = prepare(Digester.digest(req.getParameter("password")));
		this.email = req.getParameter("email");
		validate();
	}
	
	/**
	 * Helper method for checking if there was any errors during initialization
	 * of the user's properties i.e. during the registration of the user.
	 *
	 * @return <code>true</code>, if there was errors
	 * 		   <code>false</code> otherwise
	 */
	public boolean hasErrors(){
		return !errors.isEmpty();
	}
	
	/**
	 * Helper method for checking if there was and error in the particular
	 * form field.
	 *
	 * @param field the form field
	 * @return <code>true</code>, if there is an error
	 * 		   <code>false</code> otherwise
	 */
	public boolean hasError(String field){
		return errors.get(field) != null;
	}

	/**
	 * Helper method for validating user's properties. If any of the
	 * properties represent empty string, error message is put into
	 * {@link #errors} map with field identifier as a key.
	 */
	public void validate() {
		errors.clear();

		if (firstName.isEmpty()) {
			errors.put("fn", "First name is mandatory!");
		}
		if (lastName.isEmpty()) {
			errors.put("ln", "Last name is mandatory!");
		}
		if (passwordHash.equals(EMPTY_STRING_HASH)) {
			errors.put("pass", "Password is mandatory!");
		}
		if (email.isEmpty()) {
			errors.put("email", "EMail is mandatory!");
		} else {
			int l = email.length();
			int p = email.indexOf('@');
			if (l < 3 || p == -1 || p == 0 || p == l - 1) {
				errors.put("email", "EMail has illegal format!");
			}
		}
		if (nick.isEmpty()) {
			errors.put("nick", "Username is mandatory!");
		} else if(DAOProvider.getDAO().getBlogUser(nick) != null) {
			errors.put("nick", "User with given username already exists!");
		}
	}
	
	/**
	 * Gets the error message for given field.
	 *
	 * @param field the field
	 * @return the error message
	 */
	public String getErrorMessage(String field){
		return errors.get(field);
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
	public long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the nick.
	 *
	 * @return the nick
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Sets the nick.
	 *
	 * @param nick the new nick
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the password hash.
	 *
	 * @return the password hash
	 */
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * Sets the password hash.
	 *
	 * @param passwordHash the new password hash
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	/**
	 * Gets the blog entries.
	 *
	 * @return the blog entries
	 */
	public List<BlogEntry> getEntries() {
		return entries;
	}

	/**
	 * Sets the blog entries.
	 *
	 * @param entries the new blog entries
	 */
	public void setEntries(List<BlogEntry> entries) {
		this.entries = entries;
	}
}
