package hr.fer.zemris.java.tecaj.hw5.db;

/**
 * The class representing one entry in a {@link StudentDatabase}. It has four attributes: JMBAG, last name, 
 * first name and final grade. It is digital implementation of one real student.
 * 
 * @author Vjeco
 */
public class StudentRecord {
	
	/** The JMBAG of the student. */
	private final String jmbag;
	
	/** The last name of the student. */
	private final String lastName;
	
	/** The first name of the student. */
	private final String firstName;
	
	/** The final grade of the student. */
	private final int finalGrade;
	
	/**
	 * Instantiates a new student record.
	 *
	 * @param jmbag the JMBAG of the student
	 * @param lastName the last name of the student
	 * @param firstName the first name of the student
	 * @param finalGrade the final grade of the student
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		super();
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}

	/**
	 * Gets the JMBAG of the student.
	 *
	 * @return the JMBAG of the student
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Gets the last name of the student.
	 *
	 * @return the last name of the student
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Gets the first name of the student.
	 *
	 * @return the first name of the student
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Gets the final grade of the student.
	 *
	 * @return the final grade of the student
	 */
	public int getFinalGrade() {
		return finalGrade;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof StudentRecord))
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}
	
	
	
	
}
