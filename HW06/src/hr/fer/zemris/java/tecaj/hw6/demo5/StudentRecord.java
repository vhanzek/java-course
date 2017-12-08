package hr.fer.zemris.java.tecaj.hw6.demo5;

// TODO: Auto-generated Javadoc
/**
 * The Class StudentRecord.
 */
public class StudentRecord {
	
	/** The jmbag. */
	private String jmbag;
	
	/** The prezime. */
	private String prezime;
	
	/** The ime. */
	private String ime;
	
	/** The br bod mi. */
	private double brBodMI;
	
	/** The br bod zi. */
	private double brBodZI;
	
	/** The br bod lv. */
	private double brBodLV;
	
	/** The ocjena. */
	private int ocjena;
	
	/**
	 * Instantiates a new student record.
	 *
	 * @param jmbag the jmbag
	 * @param prezime the prezime
	 * @param ime the ime
	 * @param brBodMI the br bod mi
	 * @param brBodZI the br bod zi
	 * @param brBodLV the br bod lv
	 * @param ocjena the ocjena
	 */
	public StudentRecord(String jmbag, String prezime, String ime, double brBodMI, double brBodZI, 
			double brBodLV, int ocjena) {
		this.jmbag = jmbag;
		this.prezime = prezime;
		this.ime = ime;
		this.brBodMI = brBodMI;
		this.brBodZI = brBodZI;
		this.brBodLV = brBodLV;
		this.ocjena = ocjena;
	}

	/**
	 * Gets the jmbag.
	 *
	 * @return the jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Gets the prezime.
	 *
	 * @return the prezime
	 */
	public String getPrezime() {
		return prezime;
	}

	/**
	 * Gets the ime.
	 *
	 * @return the ime
	 */
	public String getIme() {
		return ime;
	}

	/**
	 * Gets the br bod mi.
	 *
	 * @return the br bod mi
	 */
	public double getBrBodMI() {
		return brBodMI;
	}

	/**
	 * Gets the br bod zi.
	 *
	 * @return the br bod zi
	 */
	public double getBrBodZI() {
		return brBodZI;
	}

	/**
	 * Gets the br bod lv.
	 *
	 * @return the br bod lv
	 */
	public double getBrBodLV() {
		return brBodLV;
	}

	/**
	 * Gets the ocjena.
	 *
	 * @return the ocjena
	 */
	public int getOcjena() {
		return ocjena;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("%s\t%s %s\t%.2f\t%.2f\t%.2f\t%d", jmbag, prezime, ime, brBodMI, brBodZI, brBodLV, ocjena);
	}
}
