package hr.fer.zemris.java.tecaj.hw5.db.fields;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * The class used for returning JMBAG of the student record. 
 * This is one implementation of the strategy {@link IFieldValueGetter}.
 * 
 * @author Vjeco
 */
public class JmbagValueGetter implements IFieldValueGetter {

	/**
	 * Method returns student's JMBAG.
	 * 
	 * @param record the student record
	 * @return the JMBAG of the given student
	 */
	@Override
	public String get(StudentRecord record) {
		return record.getJmbag();
	}

}
