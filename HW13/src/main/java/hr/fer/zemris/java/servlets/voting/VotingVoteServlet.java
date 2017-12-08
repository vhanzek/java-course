package hr.fer.zemris.java.servlets.voting;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.servlets.voting.VotingServlet.MapEntry;

/**
 * The {@link HttpServlet} which creates or updates file with votes information.
 * If specified file does not exist, file is created, all votes are set to 0 except
 * the one band that has been voted for. This class is used after the vote has been
 * submitted. If file exists, it is updated by incrementing number of votes of
 * voted band.
 * 
 * @author Vjeran 
 */
@WebServlet("/voting-vote")
public class VotingVoteServlet extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = request.getServletContext();
		String fileName = context.getRealPath("/WEB-INF/glasanje-rezultati.txt");
		Map<String, MapEntry> bands = (Map<String, MapEntry>) context.getAttribute("bands");
		String id = request.getParameter("id");
		
		Path path = Paths.get(fileName);
		if(Files.notExists(path)){
			createFile(path, id, bands.values());
		} else {
			updateFile(path, id);
		}
		
		response.sendRedirect(request.getContextPath() + "/voting-results");
	}

	/**
	 * Helper method for updating file with votes information.
	 *
	 * @param path the path to the file
	 * @param id the voted band ID
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void updateFile(Path path, String id) throws IOException {
		List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
		List<String> newLines = new ArrayList<>();
		for(String line : lines){
			String[] el = line.split("\\t");
			if(el[0].equals(id)){
				int noVotes = Integer.parseInt(el[1]) + 1;
				String newLine = el[0] + "\t" + noVotes;
				newLines.add(newLine);
			} else {
				newLines.add(line);
			}
		}
		Files.write(path, newLines, StandardCharsets.UTF_8);
	}

	/**
	 * Helper method for creating file with votes information
	 *
	 * @param path the path to the file
	 * @param id the voted band ID
	 * @param bands the offered bands
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void createFile(Path path, String id, Collection<MapEntry> bands) throws IOException{
		List<String> lines = new ArrayList<>();
		for(MapEntry entry : bands){
			String entryId = entry.getId();
			lines.add(entryId + (entryId.equals(id) ? "\t1" : "\t0"));
		}
		Files.write(path, lines);
	}
}
