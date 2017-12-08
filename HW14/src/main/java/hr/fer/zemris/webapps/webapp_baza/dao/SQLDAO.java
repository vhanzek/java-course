package hr.fer.zemris.webapps.webapp_baza.dao;

import java.sql.ResultSet;
import java.util.List;

import hr.fer.zemris.webapps.webapp_baza.dao.DAOUtil.DAOGetter;
import hr.fer.zemris.webapps.webapp_baza.poll.Poll;
import hr.fer.zemris.webapps.webapp_baza.poll.PollOption;

/**
 * <p>Implementation of the {@link DAO} interface which offers method for communicating
 * with the database. In this case, the communication is established with two tables -
 * <code>Polls</code> and <code>PollOptions</code>. 
 * 
 * <p>The first table (<code>Polls</code>) models concrete polls. Each row represents 
 * a different poll. On the other side, table <code>PollOptions</code> contains options 
 * for each defined poll.
 * 
 * @author Vjeran
 */
public class SQLDAO implements DAO {

	@Override
	public List<Poll> getPollsEntries() throws DAOException {
		return DAOUtil.getEntries(
			"SELECT * FROM Polls ORDER BY id", 
			pollEntryGetter
		);
	}

	@Override
	public List<PollOption> getPollOptionsEntries(long pollID) throws DAOException {
		return DAOUtil.getEntries(
			"SELECT * FROM PollOptions WHERE pollID=" + pollID + " ORDER BY votesCount DESC", 
			pollOptionsEntryGetter
		);
	}

	@Override
	public Poll getPoll(long id) throws DAOException {
		return DAOUtil.getEntry(
			"SELECT * FROM Polls WHERE id=" + id, 
			pollEntryGetter
		);
	}

	@Override
	public PollOption getPollOption(long id) throws DAOException {
		return DAOUtil.getEntry(
			"SELECT * FROM PollOptions WHERE id=" + id, 
			pollOptionsEntryGetter
		);
	}

	@Override
	public void incrementVotesCount(long id) throws DAOException {
		DAOUtil.updateAttribute(
			"UPDATE PollOptions SET votesCount = votesCount + 1 WHERE id=" + id
		);
	}

	@Override
	public long getPollForID(long id) throws DAOException {
		return DAOUtil.getEntry(
			"SELECT pollID FROM PollOptions WHERE id=" + id, 
			pollIDGetter
		);
	}

	@Override
	public List<PollOption> getPollWinners(long pollID) throws DAOException {
		return DAOUtil.getEntries(
			"SELECT * FROM PollOptions " +
			"WHERE votesCount=(SELECT MAX(votesCount) FROM PollOptions WHERE pollID=" + pollID + ")",
			pollOptionsEntryGetter
		);
	}
	
	
	/*--------------------- DAO Getters --------------------- */
	
	/** 
	 * The implementation of interface {@link DAOGetter}.
	 * It returns instance of {@link Poll} class retrieved from
	 * the given {@link ResultSet} object.
	 */
	private DAOGetter<Poll> pollEntryGetter = 
			rs -> new Poll(
					rs.getLong(1), 		//id
					rs.getString(2), 	//title
					rs.getString(3) 	//message
				  );

	
	/** 
	 * The implementation of interface {@link DAOGetter}. 
	 * It returns instance of {@link PollOption} class retrieved from
	 * the given {@link ResultSet} object.
	 */
	private DAOGetter<PollOption> pollOptionsEntryGetter = 
			rs -> new PollOption(
					rs.getLong(1), 		//id
					rs.getString(2), 	//optionTitle
					rs.getString(3), 	//optionLink
					rs.getLong(4), 		//pollID
					rs.getLong(5) 		//votesCount
				  );
	
	/** 
	 * The implementation of interface {@link DAOGetter}.
	 * It returns instance of {@link Long} class retrieved from
	 * the given {@link ResultSet} object. Returned number represents
	 * <code>pollID</code> in the current row of the table.
	 */
	private DAOGetter<Long> pollIDGetter = rs -> rs.getLong(1);
}
