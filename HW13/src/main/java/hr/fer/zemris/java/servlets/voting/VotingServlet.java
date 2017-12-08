package hr.fer.zemris.java.servlets.voting;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@link HttpServlet} which reads information about the offered bands from
 * the specified file. That information is shown on the web by JSP page. Each
 * band represents can be voted for by clicking on it.
 * 
 * @author Vjeran
 */
@WebServlet("/voting")
public class VotingServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = request.getServletContext();
		String fileName = context.getRealPath("/WEB-INF/glasanje-definicija.txt");
		Map<String, MapEntry> bands = getBends(Paths.get(fileName));
		context.setAttribute("bands", bands);
		
		request.getRequestDispatcher("/WEB-INF/pages/votingIndex.jsp").forward(request, response);
	}

	/**
	 * Helper method for reading from the file and loading information
	 * about the offered bends into memory.
	 *
	 * @param path the path
	 * @return the offered bends
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private Map<String, MapEntry> getBends(Path path) throws IOException {
		Map<String, MapEntry> map = new HashMap<>();
		
		List<String> lines = Files.readAllLines(path, Charset.forName("UTF-8"));
		for(String line : lines){
			String[] elements = line.split("\\t");
			map.put(elements[0], new MapEntry(elements[0], elements[1], elements[2]));
		}
		
		return map;
	}
	
	/**
	 * The class {@code MapEntry} which represent one entry for voting, 
	 * in this case, it contains bend information.
	 * 
	 * @author Vjeran
	 */
	public static class MapEntry {
		
		/** The ID. */
		private String id;
		
		/** The name. */
		private String name;
		
		/** The link. */
		private String link;
		
		/**
		 * Instantiates a new map entry.
		 *
		 * @param id the ID
		 * @param name the name
		 * @param link the link
		 */
		public MapEntry(String id, String name, String link) {
			this.id = id;
			this.name = name;
			this.link = link;
		}

		/**
		 * Gets the ID.
		 *
		 * @return the ID
		 */
		public String getId() {
			return id;
		}

		/**
		 * Gets the name.
		 *
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * Gets the link.
		 *
		 * @return the link
		 */
		public String getLink() {
			return link;
		}
	}
}
