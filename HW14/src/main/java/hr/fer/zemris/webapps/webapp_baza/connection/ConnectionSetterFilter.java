package hr.fer.zemris.webapps.webapp_baza.connection;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;

import hr.fer.zemris.webapps.webapp_baza.poll.PollOption;
import hr.fer.zemris.webapps.webapp_baza.poll.PollUtil;

/**
 * <p>This class is responsible for obtaining database connection from pool 
 * and for returning it. 
 * 
 * <p>Every time client sends a request method 
 * {@link #doFilter(ServletRequest, ServletResponse, FilterChain)} is called
 * to retrieve database connection for requested {@link HttpServlet}. After
 * the service ends, connection is removed for the {@link DataSource} pool.
 * 
 * @author Vjeran 
 */
@WebFilter(filterName = "f", urlPatterns = {"/*"})
public class ConnectionSetterFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		DataSource dataSource = (DataSource) request.getServletContext().getAttribute("dbpool");
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e) {
			throw new IOException("Database can not be accessed.", e);
		}
		
		processTables(request, connection);
		SQLConnectionProvider.setConnection(connection);
		
		try {
			chain.doFilter(request, response);
		} finally {
			SQLConnectionProvider.setConnection(null);
			try {
				connection.close();
			} catch (SQLException ignorable) {
			}
		}
	}
	
	

	/*---------------------------------------- TABLE PROCESSING ----------------------------------------*/

	/**
	 * Process tables.
	 *
	 * @param request the request
	 * @param conn the conn
	 */
	private void processTables(ServletRequest request, Connection conn) {
		Statement statement = null;

		try {
			try {
				// create SQL statement
				statement = conn.createStatement();

				// create Polls table
				createTableIfNotExist(
					statement,
					"CREATE TABLE Polls" + 
					"(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY," + 
					"title VARCHAR(150) NOT NULL," + 
					"message CLOB(2048) NOT NULL)"
				);

				// create PollOptions table
				createTableIfNotExist(
					statement,
					"CREATE TABLE PollOptions" + 
					"(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY," + 
					"optionTitle VARCHAR(100) NOT NULL," + "optionLink VARCHAR(150) NOT NULL," +
					"pollID BIGINT," + "votesCount BIGINT," +
					"FOREIGN KEY (pollID) REFERENCES Polls(id))"
				);

				// populate tables with data
				populateTables(statement, conn, request);

			} finally { statement.close(); }
		} catch (Exception e) {
			throw new RuntimeException("Error creating tables.", e);
		}
	}

	/**
	 * Creates the table if not exist.
	 *
	 * @param s the s
	 * @param sql the sql
	 * @throws SQLException the SQL exception
	 */
	/* Table helper methods */
	private void createTableIfNotExist(Statement s, String sql) throws SQLException {
		try{
			s.executeUpdate(sql);
		} catch (SQLException e){
			if (!e.getSQLState().equals("X0Y32")){ 
		        throw e; 
		    } 
		}
	}
	
	/**
	 * Checks if is table empty.
	 *
	 * @param s the s
	 * @param table the table
	 * @return true, if is table empty
	 * @throws SQLException the SQL exception
	 */
	private boolean isTableEmpty(Statement s, String table) throws SQLException {
		ResultSet rs = null;
		boolean empty = false;
		try {
			rs = s.executeQuery("SELECT * FROM " + table);
			empty = !rs.next();
		} finally {
			rs.close();
		}
		
		return empty;
	}
	

	/*---------------------------------------- TABLE INSERTION ----------------------------------------*/
	
	/**
	 * Populate tables.
	 *
	 * @param s the s
	 * @param conn the conn
	 * @param request the request
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws SQLException the SQL exception
	 */
	private void populateTables(Statement s, Connection conn, ServletRequest request) throws IOException, SQLException {
		PreparedStatement pst = null;
		long bandPollID = 0L;
		long clubPollID = 0L;
		
		//populate Polls table if is empty
		if (isTableEmpty(s, "Polls")) {
			bandPollID = insertIntoPollsTable(
				pst, conn,
				"Vote for your favorite band",
				"Which band is your favorite? Click on the link to vote!"
			);
			clubPollID = insertIntoPollsTable(
				pst, conn,
				"Vote for your favorite NBA club",
				"Which NBA club is your favorite? Click on the link to vote!"
			);
		}

		//populate PollOptions table if is empty
		if (isTableEmpty(s, "PollOptions")) {
			insertIntoPollOptionsTable(
				pst, conn, request, bandPollID,
				"/WEB-INF/glasanje-definicija.txt",
				"/WEB-INF/glasanje-rezultati.txt"
			);
			insertIntoPollOptionsTable(
				pst, conn, request, clubPollID,
				"/WEB-INF/nba-definicija.txt",
				"/WEB-INF/nba-rezultati.txt"
			);
		}
	}

	/**
	 * Insert into poll options table.
	 *
	 * @param pst the pst
	 * @param conn the conn
	 * @param request the request
	 * @param pollID the poll id
	 * @param pollOptionsPath the poll options path
	 * @param votesCountPath the votes count path
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws SQLException the SQL exception
	 */
	private void insertIntoPollOptionsTable(PreparedStatement pst, Connection conn, ServletRequest request, 
			long pollID, String pollOptionsPath, String votesCountPath) throws IOException, SQLException {
		
		//get poll options
		ServletContext context = request.getServletContext();
		List<PollOption> options = PollUtil.getPollOptions(
			Paths.get(context.getRealPath(pollOptionsPath)),
			Paths.get(context.getRealPath(votesCountPath)), 
			pollID
		);
		
		//insert into PollsOptions table
		pst = conn.prepareStatement(
			"INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) VALUES(?,?,?,?)"
		);
		for (PollOption opt : options) {
			pst.setString(1, opt.getOptionTitle());
			pst.setString(2, opt.getOptionLink());
			pst.setLong(3, opt.getPollID());
			pst.setLong(4, opt.getVotesCount());
			pst.executeUpdate();
		}
	}

	/**
	 * Insert into polls table.
	 *
	 * @param pst the pst
	 * @param conn the conn
	 * @param title the title
	 * @param message the message
	 * @return the long
	 * @throws SQLException the SQL exception
	 */
	private long insertIntoPollsTable(PreparedStatement pst, Connection conn, String title, String message) throws SQLException {
		//insert into Polls table
		pst = conn.prepareStatement(
			"INSERT INTO Polls (title, message) VALUES(?,?)",
			Statement.RETURN_GENERATED_KEYS
		);
		pst.setString(1, title);
		pst.setString(2, message);
		pst.executeUpdate();
		
		//get generated key (pollID)
		ResultSet rset = pst.getGeneratedKeys();
		long pollID = 1L;
		try{
			if(rset != null && rset.next()) {
				pollID = rset.getLong(1);
			}
		} finally { rset.close(); }
		
		return pollID;
	}
}