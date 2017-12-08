package hr.fer.zemris.java.tecaj.hw5.db.fields;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * The strategy IFieldValueGetter which has 3 implementations and every each of them returns different student attribute.
 * <ol>
 * 		<li>{@link FirstNameValueGetter}</li> 
 * 		<li>{@link LastNameValueGetter}</li> 
 * 		<li>{@link JmbagValueGetter}</li>
 * </ol>
 * 
 * @author Vjeco
 */
public interface IFieldValueGetter {
	
	/**
	 * Method returns one of the student's attributes, depending on the implementation.
	 *
	 * @param record the student record
	 * @return the attribute of the given student
	 */
	public String get(StudentRecord record);
}
