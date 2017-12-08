package hr.fer.zemris.cmdapps.trazilica.query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.cmdapps.trazilica.Vocabulary;
import hr.fer.zemris.cmdapps.trazilica.vector.Vector;
import hr.fer.zemris.cmdapps.trazilica.vector.VectorUtil;

/**
 * The class representing query command. {@link #executeQuery(Vocabulary)} method
 * is used for executing query. Also, it contains helper method for creating query 
 * vector {@link #createQueryVector(Vocabulary)}.
 * 
 * @author Vjeran
 */
public class QueryCommand {
	
	/** The query results. */
	private List<QueryResult> results;
	
	/** The list of valid query arguments. */
	private List<String> arguments;

	/**
	 * Instantiates a new query command.
	 *
	 * @param arguments the query arguments
	 */
	public QueryCommand(List<String> arguments) {
		this.results = new ArrayList<>();
		this.arguments = arguments;
	}

	/**
	 * Gets the query results.
	 *
	 * @return the query results
	 */
	public List<QueryResult> getResults() {
		return results;
	}

	/**
	 * Method for executing query by searching for the best matches in
	 * offered documents. It returns maximally 10 best results.
	 *
	 * @param voc the vocabulary
	 */
	public void executeQuery(Vocabulary voc) {
		long start = System.nanoTime();
		System.out.println("Query is: " + arguments);
		Vector queryVector = createQueryVector(voc);
		
		for(Map.Entry<String, Vector> entry : voc.getDocVectors().entrySet()){
			double sim = VectorUtil.calculateSimilarity(queryVector, entry.getValue());
			if(sim > 0){
				results.add(new QueryResult(entry.getKey(), sim));
			}
		}
		Collections.sort(results);
		long end = System.nanoTime();
		
		int n = Math.min(results.size(), 10);
		System.out.printf("Najboljih %d rezultata: (%.5f sek)%n" , n, (end - start)*10E-10);
		
		for(int i = 0; i < n; i++){
			System.out.printf("[%d] %s%n", i, results.get(i));
		}
	}

	/**
	 * Method for creating query vector.
	 *
	 * @param voc the vocabulary
	 * @return the result query vector
	 */
	private Vector createQueryVector(Vocabulary voc) {
		double[] components = VectorUtil.getVectorComponents(arguments, voc);
		return new Vector(components);
	}
			
}

