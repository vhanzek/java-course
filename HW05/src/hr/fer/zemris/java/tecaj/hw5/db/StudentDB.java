package hr.fer.zemris.java.tecaj.hw5.db;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.tecaj.hw5.collections.SimpleHashtable;

/**
 * The class for working with {@link StudentDatabase} using two types of queries: <i>indexquery</i>, used for fast 
 * retrieving student record when JMBAG is known and <i>query</i>, used for filtering database depending on given 
 * {@link ConditionalExpression}s.
 * 
 * @author Vjeco
 */
public class StudentDB {
	
	/** The pattern representing correct indexquery input. */
	private final static String INDEX_QUERY = "jmbag[\\s]*\\=[\\s]*\"([\\d]+)\"";

	/**
	 * The main method. Program starts here.
	 *
	 * @param args the arguments. Command line arguments. Unused in this example.
	 */
	public static void main(String[] args) {
		List<String> lines = null;
		
		try {
			lines = Files.readAllLines(
						Paths.get("./database.txt"),
						StandardCharsets.UTF_8
					);
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		StudentDatabase database = new StudentDatabase(lines);
		
		BufferedReader reader = new BufferedReader(
									new InputStreamReader(
										new BufferedInputStream(System.in)));
		
		String line = null;
		try {		
			while(true){
				System.out.print(">");
				
				List<StudentRecord> tempDatabase;
				
				line = reader.readLine();
				String[] queryParts = line.split("\\s", 2);
				String queryString = queryParts[0].toLowerCase();
				if(queryString.equals("exit")){
					break;
				} else if (queryString.equals("query")){
					QueryFilter queryFilter = new QueryFilter(queryParts[1]);
					tempDatabase = database.filter(queryFilter);
					printDatabase(tempDatabase);
				} else if (queryString.equals("indexquery")){
					tempDatabase = processIndexQuery(database, queryParts[1]);
					printDatabase(tempDatabase);
				} else {
					System.err.println("Unknown operation.");
				}
			}
			
			System.out.println("Goodbye!");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Method for processing indexquery command. 
	 * Returns list with one student that has given JMBAG.
	 *
	 * @param database the student database
	 * @param query the indexquery input (one conditional expression)
	 * @return the list with one student with given JMBAG
	 * 
	 * @throws QueryException if indexquery form is illegal
	 */
	private static List<StudentRecord> processIndexQuery(StudentDatabase database, String query) {
		List<StudentRecord> student = new ArrayList<>(1);
		SimpleHashtable<String, StudentRecord> studentTable = database.getStudentTable();
		query = query.trim();
		
		if(query.matches(INDEX_QUERY)){
			Matcher m = Pattern.compile(INDEX_QUERY).matcher(query);
			if(m.find()){
				StudentRecord record;
				if((record = studentTable.get(m.group(1)))!= null){
					student.add(record);
				} 
			}
		} else {
			throw new QueryException(
				"Illegal indexquery form."
			);
		}
		
		return student;
	}

	/**
	 * Method for printing database to standard output.
	 *
	 * @param tempDatabase the list of students to be printed
	 */
	private static void printDatabase(List<StudentRecord> tempDatabase) {
		int recordsSelected = tempDatabase.size();
		StringBuilder sb = new StringBuilder();
		
		if(recordsSelected > 0) {
			StringBuilder framework;
			
			int[] prefferedWidths = getPrefferedColumnWidth(tempDatabase);
			framework = makeFramework(prefferedWidths);		
			
			sb.append(framework.toString()+ "\n");
			for(StudentRecord r : tempDatabase){
				sb.append("| " + r.getJmbag() + " | ");
				sb.append(r.getLastName());
				
				int spacesLeftLastName = prefferedWidths[0] - (r.getLastName().length() + 1);
				for(int i = 0; i < spacesLeftLastName; i++){
					sb.append(" ");
				}
				sb.append("| " + r.getFirstName());
				
				int spacesLeftFirstName = prefferedWidths[1] - (r.getFirstName().length() + 1);
				for(int i = 0; i < spacesLeftFirstName; i++){
					sb.append(" ");
				}
				sb.append("| " +  r.getFinalGrade() + " |\n");		 
			}
			sb.append(framework.toString()+ "\n");
		}
		
		sb.append("Records selected: " + recordsSelected);
		
		System.out.println(sb.toString());
		
	}

	/**
	 * Method for making a {@link StringBuilder} that contains necessary
	 * framework signs.
	 *
	 * @param prefferedWidths 
	 * 				the preffered widths of the last name and first name columns
	 * @return the string builder that contains framework as a string
	 */
	private static StringBuilder makeFramework(int[] prefferedWidths) {
		StringBuilder framework = new StringBuilder();
		
		framework.append("+============+");
		for(int i = 0; i < prefferedWidths[0]; i++){
			framework.append("=");
		}
		framework.append("+");
		for(int i = 0; i < prefferedWidths[1]; i++){
			framework.append("=");
		}
		framework.append("+===+");
		
		return framework;
	}

	/**
	 * Gets the preffered widths of the columns that represent last name and first name.
	 * Preffered widths are the length of longest last (or first) name + 2 (2 spaces).
	 *
	 * @param database the student database
	 * @return the preffered column widths
	 */
	private static int[] getPrefferedColumnWidth(List<StudentRecord> database) {
		int[] prefferedWidths = new int[2];
		
		for(StudentRecord r : database){
			int lastNameLen = r.getLastName().length() + 2;
			int firstNameLen = r.getFirstName().length() + 2; 
			
			if(lastNameLen > prefferedWidths[0]){
				prefferedWidths[0] = lastNameLen;
			}
			if(firstNameLen > prefferedWidths[1]){
				prefferedWidths[1] = firstNameLen;
			}
		}
		return prefferedWidths;	
		
	}
}
