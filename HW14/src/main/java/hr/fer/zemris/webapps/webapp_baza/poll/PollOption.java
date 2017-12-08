package hr.fer.zemris.webapps.webapp_baza.poll;

/**
 * The class {@code PollOption} represents database <code>PollOptions</code> table entry.
 * It has five properties - {@link #id} which is a key, {@link #optionTitle} -
 * title of the poll option, {@link #optionLink} which leads to site that describes that
 * option, {@link #pollID} which separates poll options from different polls and {@link #votesCount}.
 * 
 * @author Vjeran
 */
public class PollOption {
	
	/** The ID. */
	private long id;
	
	/** The option title. */
	private String optionTitle;
	
	/** The option link. */
	private String optionLink;
	
	/** The poll ID. */
	private long pollID;
	
	/** The votes count. */
	private long votesCount;
	
	/**
	 * Instantiates a new poll option.
	 */
	public PollOption(){
	}
	
	/**
	 * Instantiates a new poll option.
	 *
	 * @param id the ID
	 * @param name the name
	 * @param link the link
	 * @param pollID the poll id
	 * @param votesCount the votes count
	 */
	public PollOption(long id, String name, String link, long pollID, long votesCount) {
		this.id = id;
		this.optionTitle = name;
		this.optionLink = link;
		this.pollID = pollID;
		this.votesCount = votesCount;
	}

	/**
	 * Gets the ID.
	 *
	 * @return the ID
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the ID.
	 *
	 * @param id the new ID
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets the option title.
	 *
	 * @return the option title
	 */
	public String getOptionTitle() {
		return optionTitle;
	}

	/**
	 * Sets the option title.
	 *
	 * @param optionTitle the new option title
	 */
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}

	/**
	 * Gets the option link.
	 *
	 * @return the option link
	 */
	public String getOptionLink() {
		return optionLink;
	}

	/**
	 * Sets the option link.
	 *
	 * @param optionLink the new option link
	 */
	public void setOptionLink(String optionLink) {
		this.optionLink = optionLink;
	}

	/**
	 * Gets the poll id.
	 *
	 * @return the poll id
	 */
	public long getPollID() {
		return pollID;
	}

	/**
	 * Sets the poll id.
	 *
	 * @param pollID the new poll id
	 */
	public void setPollID(long pollID) {
		this.pollID = pollID;
	}

	/**
	 * Gets the votes count.
	 *
	 * @return the votes count
	 */
	public long getVotesCount() {
		return votesCount;
	}

	/**
	 * Sets the votes count.
	 *
	 * @param votesCount the new votes count
	 */
	public void setVotesCount(long votesCount) {
		this.votesCount = votesCount;
	}
}
