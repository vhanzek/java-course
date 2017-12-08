package hr.fer.zemris.java.tecaj.hw5.db.fields;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * The class used for returning last name of the student record. 
 * This is one (of three) implementation of the strategy {@link IFieldValueGetter}.
 * 
 * @author Vjeco
 */
public class LastNameValueGetter implements IFieldValueGetter {

	/**
	 * Method returns student's last name.
	 * 
	 * @param record the student record
	 * @return the last name of the given student
	 */
	@Override
	public String get(StudentRecord record) {
		return record.getLastName();
	}

}
