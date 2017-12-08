package hr.fer.zemris.webapps.webapp_baza.poll;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The utility class which offers static methods for retrieving information about the certain 
 * poll from the file with specified path.
 * 
 * @author Vjeran
 */
public class PollUtil {
	
	/**
	 * Method for getting list of {@link PollOption} objects with all needed information 
	 * from the file with specified path
	 *
	 * @param path the path to the file with information
	 * @param votesPath the path to the file wiith votes count information
	 * @param pollID the poll ID
	 * @return the list of {@link PollOption} objects
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static List<PollOption> getPollOptions(Path path, Path votesPath, long pollID) throws IOException {
		List<PollOption> options = new ArrayList<>();
		Map<String, Long> votes = getVotesCount(votesPath);
		
		List<String> lines = Files.readAllLines(path, Charset.forName("UTF-8"));
		for(String line : lines){
			String[] elements = line.split("\\t");
			long votesCount = votes.get(elements[0]);
			options.add(
				new PollOption(Long.parseLong(elements[0]), elements[1], elements[2], pollID, votesCount)
			);
		}
		
		return options;
	}
	
	/**
	 * Method for getting map with {@link PollOption} IDs as keys and
	 * number of votes for the specified option.
	 *
	 * @param path the path to the file with votes count information
	 * @return the votes count map
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Map<String, Long> getVotesCount(Path path) throws IOException{
		Map<String, Long> votes = new HashMap<>();
		
		List<String> lines = Files.readAllLines(path, Charset.forName("UTF-8"));
		for(String line : lines){
			String[] elements = line.split("\\t");
			votes.put(elements[0], Long.parseLong(elements[1]));
		}
		
		return votes;
	}
}
