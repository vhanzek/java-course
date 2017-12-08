package hr.fer.zemris.webapps.webapp_baza.dao;
import java.util.Collection;
import java.util.List;

import hr.fer.zemris.webapps.webapp_baza.poll.Poll;
import hr.fer.zemris.webapps.webapp_baza.poll.PollOption;


/**
 * Interface toward data persistence subsystem. It is used for retrieving
 * data from the database and returning it as an instance of, for example, 
 * {@link Collection} class. This way, application has separate data-
 * access layer.
 * 
 * @author Vjeran
 */
public interface DAO{

	/**
	 * Gets all the entries from the <code>Polls</code> table.
	 *
	 * @return the <code>Polls</code> table entries
	 * @throws DAOException the DAO exception if an error occurs
	 */
	public List<Poll> getPollsEntries() throws DAOException;
	
	/**
	 * Gets all the entries from the <code>PollOptions</code> table
	 * with given <code>pollID</code>.
	 *
	 * @param pollID the poll ID
	 * @return the <code>PollOptions</code> table entries
	 * @throws DAOException the DAO exception if an error occurs
	 */
	public List<PollOption> getPollOptionsEntries(long pollID) throws DAOException;
	
	/**
	 * Gets the {@link Poll} entry from the <code>Polls</code> table 
	 * with given <code>id</code>.
	 *
	 * @param id the poll ID
	 * @return the poll with given ID
	 * @throws DAOException the DAO exception if an error occurs
	 */
	public Poll getPoll(long id) throws DAOException;
	
	/**
	 * Gets the {@link PollOption} entry from the <code>PollsOptions</code> 
	 * table with given <code>id</code>.
	 *
	 * @param id the id
	 * @return the poll option
	 * @throws DAOException the DAO exception if an error occurs
	 */
	public PollOption getPollOption(long id) throws DAOException;
	
	/**
	 * Increments votes count for the <code>PollsOption</code> entry
	 * with given <code>id</code>.
	 *
	 * @param id the {@link PollOption} ID
	 * @throws DAOException the DAO exception if an error occurs
	 */
	public void incrementVotesCount(long id) throws DAOException;
	
	/**
	 * Gets the <code>pollID</code> of the entry with given 
	 * <code>id</code> from <code>PollOptions</code> table.
	 *
	 * @param id the ID
	 * @return the <code>pollID</code>
	 * @throws DAOException the DAO exception
	 */
	public long getPollForID(long id) throws DAOException;
	
	/**
	 * Gets the <code>PollOptions</code> table entries with most
	 * <code>votesCount</code>.
	 *
	 * @param pollID the <code>pollID</code>
	 * @return the poll winners
	 * @throws DAOException the DAO exception if an error occurs
	 */
	public List<PollOption> getPollWinners(long pollID) throws DAOException;

	
	
}