package hr.fer.zemris.java.tecaj.hw5.db.fields;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * The class used for returning first name of the student record. 
 * One implementation of the strategy {@link IFieldValueGetter}.
 * 
 * @author Vjeco
 */
public class FirstNameValueGetter implements IFieldValueGetter {

	/**
	 * Method returns student's first name.
	 * 
	 * @param record the student record
	 * @return the first name of the given student
	 */
	@Override
	public String get(StudentRecord record) {
		return record.getFirstName();
	}

}
