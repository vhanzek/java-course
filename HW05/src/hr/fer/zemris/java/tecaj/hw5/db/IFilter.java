package hr.fer.zemris.java.tecaj.hw5.db;

/**
 * The functional interface with only one method {@code accepts}.
 * 
 * @author Vjeco
 */
public interface IFilter {
	
	/**
	 * Method returns <code>true</code> if tested student record
	 * satisfies necessary criteria.
	 *
	 * @param record the tested student record
	 * @return <code>true</code>, if record satisfies criteria, 
	 * 		   <code>false</code> otherwise
	 */
	public boolean accepts(StudentRecord record);
}
