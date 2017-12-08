package hr.fer.zemris.cmdapps.trazilica.query;

/**
 * The class representing query results. It contains result file defined
 * by {@link #filePath} and measure of similarity to given query vector.
 * 
 * @author Vjeran
 */
public class QueryResult implements Comparable<QueryResult>{
	
	/** The file path. */
	private String filePath;
	
	/** The similarity to query vector. */
	private Double similarity;
	
	/**
	 * Instantiates a new query result.
	 *
	 * @param filePath the file path
	 * @param similarity the similarity
	 */
	public QueryResult(String filePath, double similarity) {
		this.filePath = filePath;
		this.similarity = similarity;
	}

	/**
	 * Gets the file path.
	 *
	 * @return the file path
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * Gets the similarity.
	 *
	 * @return the similarity
	 */
	public double getSimilarity() {
		return similarity;
	}
	
	@Override
	public String toString() {
		return String.format("(%.4f) %s", similarity, filePath);
	}

	@Override
	public int compareTo(QueryResult other) {
		return -this.similarity.compareTo(other.similarity);
	}
}
