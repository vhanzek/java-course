package hr.fer.zemris.java.servlets.voting;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
 * The {@link HttpServlet} used for reading voting results from the file and forwarding
 * them to JSP which will show them on the web. Also, it calculates which bands have the most
 * number of votes.
 * 
 * @author Vjeran
 */
@WebServlet("/voting-results")
public class VotingResultsServlet extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = request.getServletContext();
		String fileName = context.getRealPath("/WEB-INF/glasanje-rezultati.txt");
		Map<String, MapEntry> bands = (Map<String, MapEntry>) context.getAttribute("bands");
		Map<String, Integer> votingResults = new HashMap<>();
		
		Path path = Paths.get(fileName);
		List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
		for(String line : lines){
			String[] el = line.split("\\t");
			MapEntry bandInf = bands.get(el[0]);
			votingResults.put(bandInf.getName(), Integer.parseInt(el[1]));
		}
		
		votingResults = sortByValue(votingResults);
		List<MapEntry> winners = getWinners(bands, votingResults);
		
		request.setAttribute("winners", winners);
		request.setAttribute("votingResults", votingResults);
		context.setAttribute("votingResults", votingResults);
		
		request.getRequestDispatcher("/WEB-INF/pages/votingRes.jsp").forward(request, response);
	}
	
	/**
	 * Helper method for getting bands with the most votes.
	 *
	 * @param bands the bands
	 * @param votingResults the voting results
	 * @return the winner bands
	 */
	private List<MapEntry> getWinners(Map<String, MapEntry> bands, Map<String, Integer> votingResults) {
		List<MapEntry> winners = new ArrayList<>();
		int max = 0;
		for(int noVotes : votingResults.values()){
			if(noVotes > max) max = noVotes;
		}	
		
		for(Map.Entry<String, Integer> entry : votingResults.entrySet()){
			if(entry.getValue() == max){
				winners.add(getBandForName(bands, entry.getKey()));
			}
		}	
		
		return winners;
	}

	/**
	 * Gets the information about the band with given name.
	 *
	 * @param bands the bands
	 * @param name the name
	 * @return the band with given name
	 */
	private MapEntry getBandForName(Map<String, MapEntry> bands, String name) {
		for(MapEntry band : bands.values()){
			if(band.getName().equals(name)){
				return band;
			}
		}
		return null;
	}

	/**
	 * Helper method for sorting map by value, descending.
	 *
	 * @param <K> the key type
	 * @param <V> the value type
	 * @param map the map
	 * @return the map
	 */
	private <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map){
	    Map<K, V> result = new LinkedHashMap<>();
	    map.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
	        				   .forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
	    return result;
	}
}
