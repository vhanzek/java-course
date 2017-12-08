package hr.fer.zemris.webapps.webapp_baza.poll;

/**
 * The class {@code Poll} represents database <code>Polls</code> table entry.
 * It has three properties - {@link #pollID} which is a key, {@link #title} -
 * title of the poll and {@link #message}.
 * 
 * @author Vjeran
 */
public class Poll {
	
	/** The poll ID. */
	private long pollID;
	
	/** The title. */
	private String title;
	
	/** The message. */
	private String message;
	
	/**
	 * Instantiates a new poll.
	 *
	 * @param pollID the poll ID
	 * @param title the title
	 * @param message the message
	 */
	public Poll(long pollID, String title, String message) {
		this.pollID = pollID;
		this.title = title;
		this.message = message;
	}

	/**
	 * Gets the poll ID.
	 *
	 * @return the poll ID
	 */
	public long getPollID() {
		return pollID;
	}

	/**
	 * Sets the poll ID.
	 *
	 * @param pollID the new poll ID
	 */
	public void setPollID(long pollID) {
		this.pollID = pollID;
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
}
